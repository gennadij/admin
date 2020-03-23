package org.genericConfig.admin.models.config

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.config.status.AddConfigSuccess
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.{JsValue, Json}

import scala.collection.JavaConverters._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 16.01.2017
  *
  * Username = user3
  */

class AddConfigSpecs extends Specification
  with BeforeAfterAll
  with CommonFunction {

  val userPassword = "user3"

  val wC: WebClient = WebClient.init

  def beforeAll(): Unit = {


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
    require(countDeletingConfigs == 1, "Anzahl der geloeschten ConfigVertexes " + countDeletingConfigs)


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

    val userResult = wC.handleMessage(userResult)
    (userResult \ "result" \  "errors" ).asOpt[List[ErrorDTO]] must_== None
    Logger.info(userResult.toString())

    val configParams = Json.obj(
      "json" -> JsonNames.ADD_CONFIG
      , "params" -> Json.obj(
        "userId" -> (loginSC \ "result" \ "userId").asOpt[String].get,
        "configUrl" -> "//http://contig1/user3"
      )
    )
    Logger.info("IN " + configParams)

    val createConfigSC = wC.handleMessage(configParams)

    Logger.info("OUT " + createConfigSC)
  }

  def afterAll(): Unit = {}

  "Der Benutzer fuegt eine neue Konfiguration hinzu" >> {
    "Login mit AdminUser und fuege Konfig zu dem AdminUser hinzu" >> {



      (createConfigSC \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigSuccess().status
      (createConfigSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    }
  }
}