package org.genericConfig.admin.models.config

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.config.ConfigDTO
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.{JsResult, JsValue, Json}

import scala.collection.JavaConverters._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 16.01.2017
  *
  * Username = user3
  */

class AddConfigSpecs extends Specification with BeforeAfterAll {

  var configDTO :JsResult[ConfigDTO] = null

  def beforeAll(): Unit = {

    val configResult = new PrepareAddConfig().befor()

    Logger.info("<- " + configResult)

    configDTO = Json.fromJson[ConfigDTO](configResult)


  }

  def afterAll(): Unit = {}
  //TODO userid wird als RID an Client gesendet
  "Der Benutzer fuegt eine neue Konfiguration hinzu" >> {
    "result.userId" >> {
      configDTO.asOpt.get.result.get.userId.get.size must be_<=(32)
    }
    "result.configs(0).configId" >> {
      configDTO.asOpt.get.result.get.configs.get.head.configId.get.size must be_<=(32)
    }
    "result.configs(0).configUrl" >> {
      configDTO.asOpt.get.result.get.configs.get.head.configUrl.get === "http://contig1/user3"
    }
    "result.errors" >> {
      configDTO.asOpt.get.result.get.errors === None
    }
  }
}

class PrepareAddConfig extends CommonFunction {
  def befor(): JsValue ={
    val userPassword = "user3"

    val wC: WebClient = WebClient.init

    val graph: OrientGraph = Database.getFactory._1.get.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userPassword'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
    val countUsers: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(countUsers == 1 ) {
      Logger.info(s"Der User $userPassword ist schon erstellt worden")
    }else{
      addUser(userPassword, wC)

      getUserId(userPassword, wC)
    }

    val countDeletingConfigs: Int = deleteConfigVertex(userPassword)
//    require(countDeletingConfigs == 1, "Anzahl der geloeschten ConfigVertexes " + countDeletingConfigs)

    val userParams = Json.obj(
      "action" -> Actions.GET_USER
      ,"params" -> Json.obj(
        "username" -> userPassword,
        "password"-> userPassword,
        "update" -> Json.obj(
          "newUsername" -> "",
          "oldUsername" -> "",
          "newPassword" -> "",
          "oldPassword" -> ""
        ),
      ),
      "result" -> Json.obj(
        "userId" -> "",
        "username" -> "",
        "errors" -> Json.arr()
      ))

    val userResult = wC.handleMessage(userParams)

    require((userResult \ "result" \  "errors" ).asOpt[List[ErrorDTO]] == None, "Erwarte None")

    Logger.info(userResult.toString())

    val configParams = Json.obj(
      "action" -> Actions.ADD_CONFIG,
      "params" -> Json.obj(
        "userId" -> (userResult \ "result" \ "userId").asOpt[String].get,
        "configUrl" -> "http://contig1/user3",
        "configurationCourse" -> "sequence",
        "update" -> Json.obj(
          "dummy" -> "",
          "dummy2" -> ""
        )
      ),
      "result" -> Json.obj(
        "userId" -> "",
        "configs" -> Json.arr(),
        "errors" -> Json.arr()
      )
    )

    Logger.info("-> " + configParams)

    wC.handleMessage(configParams)
  }
}