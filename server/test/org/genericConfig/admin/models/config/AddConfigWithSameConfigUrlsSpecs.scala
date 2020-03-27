package org.genericConfig.admin.models.config

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import models.preparingConfigs.GeneralFunctionToPrepareConfigs
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.common.ODBRecordDuplicated
import org.genericConfig.admin.models.logic.{Config, User}
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.genericConfig.admin.shared.config.json.{JsonAddConfigIn, JsonAddConfigParams}
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, Json}

import scala.collection.JavaConverters._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 05.05.2017
  *
  * Username = user13
  * Username = user14
  */

class AddConfigWithSameConfigUrlsSpecs extends Specification
  with BeforeAfterAll
  with GeneralFunctionToPrepareConfigs
  with CommonFunction {


  val webClient: WebClient = WebClient.init
  var resultUserDTO: JsResult[UserDTO] = null
  val configUrl = "http://config/user13"
  val user13 = "user13"
  val user14 = "user14"
  var configIdUser13 : String = ""

  var userId = "test"

  def beforeAll: Unit = {
    before()
    Logger.info("Before")
  }

  def afterAll: Unit = {

    val configDTO : ConfigDTO = ConfigDTO(
      action = Actions.DELETE_CONFIG,
      params = Some(ConfigParamsDTO(
        userId = None,
        configId = Some(configIdUser13),
        configUrl = None,
        configurationCourse = None
      )),
      result = None
    )

    Config.deleteConfig(configDTO)
  }

  "Hier wird die Erzeugung von zwei verschiedenen AdminUser mit gleicher ConfigUrl spezifiziert" >> {
    "result.error == ORecordDuplicatedException" >> {
      Logger.info("TEST")
      Logger.info("OUT " + resultUserDTO)
      resultUserDTO.asOpt.get.result.get.errors.get.head.name === ODBRecordDuplicated().name
    }
  }
  private def before(): Unit = {
    val userIdForUser13 = createUser(user13)
    val userIdForUser14 = createUser(user14)

//    val count = deleteUser("user14")

//    require(count == 1, count.toString)

//    val userIdUser13 = addUser(user13, webClient)
    //TODO wenn schon exstiert nicht anlegen
    configIdUser13 =  addConfig(userIdForUser13, this.configUrl)

//    val userIdUser14 = addUser("user14", webClient)

    val paramsUserDTO = Json.toJsObject(JsonAddConfigIn(
      json = JsonNames.ADD_CONFIG,
      params = JsonAddConfigParams(
        userId = userIdForUser14,
        configUrl = configUrl
      )
    ))

    Logger.info("IN " + paramsUserDTO)
    resultUserDTO = Json.fromJson[UserDTO](webClient.handleMessage(paramsUserDTO))
  }

  private def createUser(username : String) : String = {
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

//      val configId_1 = addConfig(userId13, s"http://config/$username")

//      println("ConfigId" + configId_1)
    }
  }
  private def deleteConfig(username : String): Unit ={
    // Specs DeleteConfig vorbereiten
  }
}