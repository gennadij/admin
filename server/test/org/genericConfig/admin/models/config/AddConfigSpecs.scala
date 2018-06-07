package org.genericConfig.admin.models.config

import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import play.api.libs.json.{JsValue, Json}
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import models.preparingConfigs.PrepareConfigsForSpecsv011
import org.genericConfig.admin.controllers.websocket.WebClient
import play.api.Logger
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.config.status.AddConfigAdded
import org.genericConfig.admin.shared.user.json.{JsonUserIn, JsonUserParams}
import util.CommonFunction

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 16.01.2017
  *
  * Username = user3
  */

@RunWith(classOf[JUnitRunner])
class AddConfigSpecs extends Specification
  with BeforeAfterAll
  with CommonFunction {

  val userPassword = "user3"

  val wC = WebClient.init

  def beforeAll() = {
    new PrepareConfig().prepareAddingNewConfig(wC)
    val count: Int = deleteConfigVertex(userPassword)
    require(count == 1, "Anzahl der geloeschten ConfigVertexes " + count)
  }

  def afterAll() = {}

  "Diese Spezifikation erzeugt neue Konfiguration fuer die Admin" >> {
    "Login mit AdminUser und fuege Konfig zu dem AdminUser hinzu" >> {

      //      val loginCS: JsValue = Json.obj(
      //        "json" -> JsonNames.GET_USER
      //        ,"params" -> Json.obj(
      //            "username" -> userPassword,
      //            "password" -> userPassword
      //        )
      //    )
      val getUserIn = JsonUserIn(
        json = JsonNames.GET_USER,
        params = JsonUserParams(
          username = userPassword,
          password = userPassword
        )
      )
      val jsonGetUserIn: JsValue = Json.toJsObject(getUserIn)

      val loginSC = wC.handleMessage(jsonGetUserIn)
      //    (loginSC \ "result" \ "status" \ "userLogin" \ "status").asOpt[String].get must_== UserExist().status
      (loginSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get must_== Success().status

      Logger.info(loginSC.toString())
      val createConfigCS = Json.obj(
        "json" -> JsonNames.ADD_CONFIG
        , "params" -> Json.obj(
          "userId" -> (loginSC \ "result" \ "userId").asOpt[String].get,
          "configUrl" -> "//http://contig1/user3"
        )
      )
      Logger.info("IN " + createConfigCS)

      val createConfigSC = wC.handleMessage(createConfigCS)

      //TODO com.orientechnologies.orient.core.exception.OValidationException: analysieren


      Logger.info("OUT " + createConfigSC)

      (createConfigSC \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigAdded().status
      (createConfigSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    }
  }
}