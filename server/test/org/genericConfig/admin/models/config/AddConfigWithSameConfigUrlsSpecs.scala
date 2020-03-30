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
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}

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
  var resultConfigDTO: JsResult[ConfigDTO] = null
  val configUrl = "http://config/user13"
  val user13 = "user13"
  val user14 = "user14"
  var configIdUser13 : String = ""

  var userId = "test"

  def beforeAll: Unit = {
    before()
  }

  def afterAll: Unit = {
    deleteConfig()
  }

  "Hier wird die Erzeugung von zwei verschiedenen AdminUser mit gleicher ConfigUrl spezifiziert" >> {
    "result.error == ORecordDuplicatedException" >> {
      resultConfigDTO.asOpt.get.result.get.errors.get.head.name === ODBRecordDuplicated().name
    }
  }

  private def before(): Unit = {
    val userIdForUser13 = createUser(user13)
    val userIdForUser14 = createUser(user14)

    configIdUser13 =  createConfig(userIdForUser13, this.configUrl)

    val paramsConfigDTO : JsValue = Json.toJson(ConfigDTO(
      action = Actions.ADD_CONFIG,
      params = Some(ConfigParamsDTO(
        userId = Some(userIdForUser14),
        configUrl = Some(configUrl),
        configurationCourse = Some("sequence")
      )),
      result = None
    ))

    Logger.info("IN " + paramsConfigDTO)
    resultConfigDTO = Json.fromJson[ConfigDTO](webClient.handleMessage(paramsConfigDTO))
    Logger.info("OUT " + resultConfigDTO)
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
    }
  }


  private def deleteConfig(): Unit ={
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
}