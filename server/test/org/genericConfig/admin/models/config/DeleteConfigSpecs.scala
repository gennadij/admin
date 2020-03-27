package org.genericConfig.admin.models.config

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.logic.User
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}
import scala.collection.JavaConverters._


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 02.05.2018
 */
@RunWith(classOf[JUnitRunner])
class DeleteConfigSpecs extends Specification
                           with BeforeAfterAll
                           with CommonFunction{

  val wC: WebClient = WebClient.init
  var deleteConfigResult : JsResult[ConfigDTO] = _
  val username = "user_v016_4"

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {
  }

  "Der Benutzer loescht ein Configuration" >> {
    "action = DELETE_CONFIG" >> {
      deleteConfigResult.asOpt.get.action === Actions.DELETE_CONFIG
    }
    "result.error = None" >> {
      deleteConfigResult.asOpt.get.result.get.errors === None
    }
  }

  private def before(): Unit = {
    val userId : String = createUser()
    Logger.info("username : " + username)
    Logger.info("userId : " + userId)


    val configId : String = addConfig(userId, "//http://contig1/user_v016_4")

    val configDTO : ConfigDTO = ConfigDTO(
      action = Actions.DELETE_CONFIG,
      params = Some(ConfigParamsDTO(
        userId = None,
        configId = Some(configId),
        configUrl = None,
        configurationCourse = None
      )),
      result = None
    )

    val deleteConfigParams : JsValue = Json.toJson(configDTO)

    Logger.info("IN " + deleteConfigParams)

    this.deleteConfigResult = Json.fromJson[ConfigDTO](wC.handleMessage(deleteConfigParams))

    Logger.info("OUT " + this.deleteConfigResult)

  }

  private def createUser() = {
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
      addUser(username, wC)
    }
  }
}