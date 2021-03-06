package org.genericConfig.admin.models

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.logic._
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.component.{ComponentConfigPropertiesDTO, ComponentDTO, ComponentParamsDTO, ComponentUserPropertiesDTO}
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.genericConfig.admin.shared.step.{SelectionCriterionDTO, StepDTO, StepParamsDTO, StepPropertiesDTO}
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import play.api.Logger
import play.api.libs.json.{JsResult, Json}

import scala.collection.JavaConverters._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 22.04.2018
  */
trait CommonFunction {

  def addUser(userPassword: String, webClient: WebClient): String = {
    val userParams = Json.obj(
      "action" -> Actions.ADD_USER
      , "params" -> Json.obj(
        "username" -> userPassword,
        "password" -> userPassword,
        "update" -> Json.obj(
          "newUsername" -> "",
          "oldUsername" -> "",
          "newPassword" -> "",
          "oldPassword" -> ""
        )
      ),
      "result" -> Json.obj(
        "userId" -> "",
        "username" -> "",
        "errors" -> Json.arr()
      )
    )
    val userResult = webClient.handleMessage(userParams)

    Logger.info("ADD_USER " + userParams)
    Logger.info("ADD_USER " + userResult)
    require((userResult \ "result" \ "username").asOpt[String].get == userPassword, s"Username: $userPassword")

    (userResult \ "result" \ "userId").asOpt[String].get
  }

  def createUser(username : String, webClient : WebClient) : String = {
    val graph: OrientGraph = Database.getFactory()._1.get.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$username'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    val count = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString.toInt).head
    if(count == 1 ) {
      val resultUserDTO = User.getUser(UserDTO(
        action = Actions.GET_USER,
        params = Some(UserParamsDTO(
          username = username,
          password = username,
          update = None
        )),
        result = None
      ))
      resultUserDTO.result.get.userId.get
    }else {
      addUser(username, webClient)
    }
  }

  def createConfig(userId: String, configUrl: String) : String = {
    val graph: OrientGraph = Database.getFactory()._1.get.getTx
    val sql: String = s"select count(configUrl) from Config where configUrl like '$configUrl'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    val count = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString.toInt).head
    if(count == 1 ) {
      val sql: String = s"select * from Config where configUrl like '$configUrl'"
      val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
      val rid = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getIdentity.toString())
      val idHash = RidToHash.setIdAndHash(rid.head)._2
      idHash
    }else {
      addConfig(userId, configUrl)
    }
  }

  def addConfig(userId: String, configUrl: String): String = {

    val configDTOParams = ConfigDTO(
      action = Actions.ADD_CONFIG,
      params = Some(ConfigParamsDTO(
        userId = Some(userId),
        configUrl = Some(configUrl),
        configurationCourse = Some("sequence"),
        update = None
      )),
      result = None
    )
    Logger.info("IN " + configDTOParams)

    val configDTOResult = Config.addConfig(configDTOParams)

    Logger.info("OUT " + configDTOResult)

    configDTOResult.result.get.configs.get.head.configId.get
  }

  def deleteVertex(rId : String, clazz : String): Option[common.Error] = {
    GraphCommon.deleteVertex(rId, clazz)
  }

  def deleteStepAppendedToConfig(configId: String): Int = {
    val graph: OrientGraph = Database.getFactory()._1.get.getTx
    val res: Int = graph.command(
      new OCommandSQL(
        s"DELETE VERTEX Step where @rid IN (SELECT out() from Config where @rid='$configId')"
      )
    ).execute()
    graph.commit()
    res
  }

  def addStep(
               nameToShow : Option[String],
               outId : Option[String],
               kind : Option[String] = Some("undefined"),
               min : Int,
               max : Int,
               wC : WebClient
             ) : Option[String] = {
    Logger.info("Erstelle neuen Step " + nameToShow)
    val graph: OrientGraph = Database.getFactory()._1.get.getTx
    val sql: String = s"select count(*) from Step where nameToShow like '${nameToShow.get}'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    val count : Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString()).head.toInt
    if(count < 1 ) {
      val stepDTO : JsResult[StepDTO] = Json.fromJson[StepDTO](wC.handleMessage(Json.toJson(StepDTO(
        action = Actions.ADD_STEP,
        params = Some(StepParamsDTO(
          outId = outId,
          kind = kind,
          properties = Some(StepPropertiesDTO(
            nameToShow = nameToShow,
            selectionCriterion = Some(SelectionCriterionDTO(
              min = Some(min),
              max = Some(max)
            ))
          ))
        )),
        result = None
      ))))
      Logger.info("ADD_STEP -> " + stepDTO)
      stepDTO.get.result.get.errors match {
        case Some(error) =>
          Logger.info("Beim erstellen von Step ein Fehler aufgetretten")
          Logger.info("Fehler -> " + error)
          None
        case None =>
          Logger.info("Der neue Step erstellt wurde")
          stepDTO.asOpt.get.result.get.stepId
      }
    }else {
      val graph: OrientGraph = Database.getFactory()._1.get.getTx
      val sql: String = s"select * from Step where nameToShow like '${nameToShow.get}'"
      val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
      val stepId = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getIdentity.toString()).head
      Logger.info("Der Step existiert bereits")
      Some(RidToHash.setIdAndHash(stepId)._2)
    }
  }

  def createComponent(wC : WebClient, stepId : Option[String], nameToShow : Option[String]) : Option[String] = {
    Logger.info("Erstelle neu Komponente " + nameToShow)
    val graph: OrientGraph = Database.getFactory()._1.get.getTx
    val sql: String = s"select count(*) from Component where nameToShow like '${nameToShow.get}'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    val count : Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString()).head.toInt
    if(count < 1) {
      val addComponentResult : JsResult[ComponentDTO] = Json.fromJson[ComponentDTO](
        wC.handleMessage(Json.toJson(ComponentDTO(
          action = Actions.ADD_COMPONENT,
          params = Some(ComponentParamsDTO(
            configProperties = Some(ComponentConfigPropertiesDTO(
              stepId = stepId
            )),
            userProperties = Some(ComponentUserPropertiesDTO(
              nameToShow = nameToShow
            ))
          ))
        ))
        ))
      Logger.info("ADD_COMPONENT <- " + addComponentResult)
      addComponentResult.get.result.get.errors match {
        case Some(errors) =>
          Logger.info("Beim erstellen der Komponente ein Fehler aufgetretten")
          Logger.info("Fehler -> " + errors)
          None
        case None =>
          Logger.info("Der neue Komponente erstellt wurde")
          addComponentResult.get.result.get.configProperties.get.componentId
      }
    }else {
      val graph: OrientGraph = Database.getFactory()._1.get.getTx
      val sql: String = s"select * from Component where nameToShow like '${nameToShow.get}'"
      val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
      Logger.info("Die Komponente existiert bereits")
      Some(RidToHash.setIdAndHash(res.asScala.toList.map(_.asInstanceOf[OrientVertex].getIdentity.toString()).head)._2)
    }
  }

  def connectComponentToStep(outId : String, inId : String, wC : WebClient) : Option[List[ErrorDTO]] = {
    Logger.info("Erstelle Edge HAS_STEP")
    val graph: OrientGraph = Database.getFactory()._1.get.getTx

    val vOut: OrientVertex = graph.getVertex(RidToHash.getRId(outId).get)

    val res = vOut.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP)

    if(res.asScala.toList.length < 1) {
      Logger.info("Edge wird erstellt")

      val connectComponentToStepParam = Json.toJson(ComponentDTO(
        action = Actions.CONNECT_COMPONENT_TO_STEP,
        params = Some(ComponentParamsDTO(
          configProperties = Some(ComponentConfigPropertiesDTO(
            stepId = Some(inId),
            componentId = Some(outId)
          )),
          userProperties = Some(ComponentUserPropertiesDTO(
            nameToShow = Some("ComponentUpdated")
          ))
        ))
      ))

      val jsValueConnectComponentToStepResult = wC.handleMessage(connectComponentToStepParam)

      val connectComponentToStepResult : JsResult[ComponentDTO] = Json.fromJson[ComponentDTO](jsValueConnectComponentToStepResult)

      connectComponentToStepResult.get.result.get.errors match {
        case Some(errors) => Some(errors)
        case None => None
      }
    }else {
      Logger.info("Edge existiert bereits")
      None
    }
  }

//==================================================================================================






  def deleteConfigVertex(username: String): Int = {
    val sql: String = s"DELETE VERTEX Config where @rid IN (SELECT OUT('hasConfig') FROM AdminUser WHERE username='$username')"
    val graph: OrientGraph = Database.getFactory()._1.orNull.getTx
    val res: Int = graph
      .command(new OCommandSQL(sql)).execute()
    graph.commit()
    Logger.info("Deleting count: " + res)
    res
  }

//  def deleteStepAppendToComponent(componentId: String): Int = {
//    GraphCommon.deleteStepAppendedToComponent(componentId)
//  }

//  def getConfigs(userId: String, wC: WebClient): Set[String] = {
//    val getConfigsIn = Json.obj(
//      "json" -> JsonNames.GET_CONFIGS
//      , "params" -> Json.obj(
//        "userId" -> userId
//      )
//    )
//    val getConfigsOut = wC.handleMessage(getConfigsIn)
//
//    Logger.info("getConfigsIn " + getConfigsIn)
//    Logger.info("getConfigsOut " + getConfigsOut)
//
//    val jsConfigsIds: Set[JsValue] = (getConfigsOut \ "result" \ "configs").asOpt[Set[JsValue]].get
//
//    jsConfigsIds map (jsCId => (jsCId \ "configId").asOpt[String].get)
//  }

//  def getConfigTree(configId: String): ConfigTreeBO = {
//    val getCondifTreeBOIn = ConfigTreeBO(
//      configId = Some(configId)
//    )
//    Config.getConfigTree(getCondifTreeBOIn)
//  }

  def deleteUser(username: String): Int = {
    val graph: OrientGraph = Database.getFactory()._1.orNull.getTx
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX AdminUser where username='$username'")).execute()
    graph.commit()
    res
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepId : String
    * @return Int
    */

  def deleteComponents(stepId: String): Int = {
    val graph: OrientGraph = Database.getFactory()._1.get.getTx
    val stepRId = RidToHash.getRId(stepId)
    val sql: String = s"delete VERTEX Component where @rid in (select out('hasComponent') from Step where @rid='${
      stepRId.get
    }')"
    val res: Int = graph.command(new OCommandSQL(sql)).execute()
    graph.commit()
    res
  }
}
