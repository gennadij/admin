package org.genericConfig.admin.models

import com.orientechnologies.orient.core.record.impl.ODocument
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.logic._
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.models.persistence.orientdb.Graph
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.component.bo.ComponentBO
import org.genericConfig.admin.shared.component.status.StatusAddComponent
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.genericConfig.admin.shared.configTree.bo.ConfigTreeBO
import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import play.api.Logger
import play.api.libs.json.{JsValue, Json}

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
//      val resultUserDTO = User.getUser(UserDTO(
//        action = Actions.GET_USER,
//        params = Some(UserParamsDTO(
//          username = username,
//          password = username,
//          update = None
//        )),
//        result = None
//      ))
//      resultUserDTO.result.get.userId.get
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

//  def addUser(username: String): (String, String, String) = {
//
//    val jsonAddUserIn = Json.toJsObject(JsonUserIn(
//      json = JsonNames.ADD_USER,
//      params = JsonUserParams(
//        username = username,
//        password = username
//      )
//
//    ))
//
//    val wC = WebClient.init
//
//    //        Logger.info("IN " + jsonAddUserIn)
//
//    val jsonAddUserOut = wC.handleMessage(jsonAddUserIn)
//
//    val addUserOut = Json.fromJson[JsonUserOut](jsonAddUserOut)
//
//    //        Logger.info("OUT " + jsonAddUserOut)
//
//    (addUserOut.get.result.username.get, addUserOut.get.result.userId.get, addUserOut.get.result.status.addUser.get.status)
//  }

  def deleteAllConfigs(username: String): Int = {
    Graph.deleteAllConfigs(username)
  }




  def deleteStepAppendedToConfig(configId: String): Int = {
    Graph.deleteStepAppendedToConfig(configId)
  }

  def deleteStepAppendToComponent(componentId: String): Int = {
    Graph.deleteStepAppendedToComponent(componentId)
  }

  def getConfigs(userId: String, wC: WebClient): Set[String] = {
    val getConfigsIn = Json.obj(
      "json" -> JsonNames.GET_CONFIGS
      , "params" -> Json.obj(
        "userId" -> userId
      )
    )
    val getConfigsOut = wC.handleMessage(getConfigsIn)

    Logger.info("getConfigsIn " + getConfigsIn)
    Logger.info("getConfigsOut " + getConfigsOut)

    val jsConfigsIds: Set[JsValue] = (getConfigsOut \ "result" \ "configs").asOpt[Set[JsValue]].get

    jsConfigsIds map (jsCId => (jsCId \ "configId").asOpt[String].get)
  }

  def addStep(appendToId: Option[String] = None,
              nameToShow: Option[String] = Some("Step"),
              kind: Option[String] = Some("default")): Option[String] = {

    val addstepBOIn = StepBO(
      json = Some(JsonNames.ADD_STEP),
      appendToId = appendToId,
      nameToShow = nameToShow,
      kind = kind,
      selectionCriteriumMax = Some(1),
      selectionCriteriumMin = Some(1)
    )
    val addStepBOOut = Step.addStep(addstepBOIn)
    addStepBOOut.stepId
  }

  def getConfigTree(configId: String): ConfigTreeBO = {
    val getCondifTreeBOIn = ConfigTreeBO(
      configId = Some(configId)
    )
    Config.getConfigTree(getCondifTreeBOIn)
  }

  def deleteConfigVertex(username: String): Int = {
    val sql: String = s"DELETE VERTEX Config where @rid IN (SELECT OUT('hasConfig') FROM AdminUser WHERE username='$username')"
    val graph: OrientGraph = Database.getFactory()._1.orNull.getTx
    val res: Int = graph
      .command(new OCommandSQL(sql)).execute()
    graph.commit()
    Logger.info("Deleting count: " + res)
    res
  }

  def deleteUser(username: String): Int = {
    val graph: OrientGraph = Database.getFactory()._1.orNull.getTx
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX AdminUser where username='$username'")).execute()
    graph.commit()
    res
  }



  def getUserId(userPassword: String, webClient: WebClient): String = {
""
//    val userBO = UserBO(
//      username = Some(userPassword),
//      password = Some(userPassword)
//    )
//
//    val userBOOut = User.getUser(userBO)
//
//    userBOOut.userId.get
  }


  def getConfigId(usernamePassword: String, configUrl: String = ""): String = {
""
//    val userBOIn = UserBO(
//      username = Some(usernamePassword),
//      password = Some(usernamePassword)
//    )
//
//    val userBOOut = User.getUser(userBOIn)
//
//    val configBOIn = ConfigBO(
//      userId = Some(userBOOut.userId.get)
//    )
//
//    val configBOOut = Config.getConfigs(configBOIn)
//    configBOOut.status.get.getConfigs.get match {
//      case GetConfigsSuccess() =>
//        configBOOut.configs.get.head.configId.get
//      case GetConfigsEmpty() =>
//        val addConfigBOIn = ConfigBO(
//          userId = Some(userBOOut.userId.get),
//          configs = Some(List(Configuration(configUrl = Some(configUrl))))
//        )
//
//        val addConfigBOOut = Config.addConfig(addConfigBOIn)
//
//        addConfigBOOut.configs.get.head.configId.get
//      case GetConfigsError() => {
//        configBOOut.status.get.getConfigs.get.status
//      }
//    }
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
    graph.commit
    res
  }

  def addComponentToStep(stepId: String, nameToShow: String, kind: String): (String, StatusAddComponent) = {

    val componentBOIn = ComponentBO(
      json = Some(JsonNames.ADD_COMPONENT),
      stepId = Some(stepId),
      nameToShow = Some(nameToShow),
      kind = Some(kind)
    )

    val componentBOOut = Component.addComponent(componentBOIn)

    (componentBOOut.componentId.get, componentBOOut.status.get.addComponent.get)
  }

  def connectComponentToStep(stepId: String, componentId: String) = {
//    val connectCToSIn = StepBO(
//      appendToId = Some(componentId),
//      stepId = Some(stepId)
//    )
//
//    val connectCToSOut = Step.connectComponentToStep(connectCToSIn)
//
//    connectCToSOut.status.get.common.get
  }
}
