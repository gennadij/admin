package org.genericConfig.admin.models.config

import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction

import play.api.Logger
import play.api.libs.json.Json
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.JsValue
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.config.status.GetConfigsEmpty

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 24.04.2018
 */

class GetEmptyConfigsSpecs extends Specification
                           with BeforeAfterAll
                           with CommonFunction{

  val wC: WebClient = WebClient.init
  var userId: String = ""
  val username = "user_v016_7"

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {
//    Logger.info("Deleting Configs : " + deleteAllConfigs(this.username))
  }



  "Diese Spezifikation spezifiziert das Holen von allen Konfigurationen" >> {
    "fuer einen AdminUser. Der User hat keine Konfigurationen" >> {

    "" === ""

//      (getConfigsOut \ "json").asOpt[String].get === JsonNames.GET_CONFIGS
//      (getConfigsOut \ "result" \ "configs").asOpt[Set[JsValue]].get.size === 0
//      (getConfigsOut \ "result" \ "status" \ "addConfig").asOpt[String] === None
//      (getConfigsOut \ "result" \ "status" \ "getConfigs" \ "status").asOpt[String].get === GetConfigsEmpty().status
//      (getConfigsOut \ "result" \ "status" \ "deleteConfig").asOpt[String] === None
//      (getConfigsOut \ "result" \ "status" \ "updateConfig").asOpt[String] === None
//      (getConfigsOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    }
  }
  private def before() = {
    val (username, userId, _): (String, String, _) = addUser(this.username)
    this.userId = userId

    val getConfigsIn = Json.obj(
      "json" -> JsonNames.GET_CONFIGS
      , "params" -> Json.obj(
        "userId" -> this.userId
      )
    )

    Logger.info("getConfigsIn " + getConfigsIn)

    val getConfigsOut = wC.handleMessage(getConfigsIn)

    Logger.info("getConfigsOut " + getConfigsOut)
  }
}