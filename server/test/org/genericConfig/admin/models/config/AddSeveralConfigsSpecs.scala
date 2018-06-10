package org.genericConfig.admin.models.config

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.config.json.{JsonAddConfigIn, JsonAddConfigParams}
import org.genericConfig.admin.shared.config.status.AddConfigSuccess
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json
import util.CommonFunction

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 22.04.2018
  */

@RunWith(classOf[JUnitRunner])
class AddSeveralConfigsSpecs extends Specification
  with BeforeAfterAll
  with CommonFunction {

  val wC: WebClient = WebClient.init
  var userId: String = ""
  val username = "user_1_v016"
  val configUrl_1 = "//http://contig1/user_1_v016_1"
  val configUrl_2 = "//http://contig1/user_1_v016_2"
  val configUrl_3 = "//http://contig1/user_1_v016_3"

  def beforeAll(): Unit = {
    val (_, userId, _): (String, String, _) = addUser(this.username)
    this.userId = userId
  }

  def afterAll(): Unit = {
    Logger.info("Deleting Configs : " + deleteAllConfigs(this.username))
  }


  "Diese Spezifikation spezifiziert die Erstellung von mehreren Konfigurationen" >> {
    "fuer einen AdminUser" >> {

      val jsonAddConfigIn_1 = Json.toJsObject(JsonAddConfigIn(
        json = JsonNames.ADD_CONFIG,
        params = JsonAddConfigParams(
          userId = this.userId,
          configUrl = this.configUrl_1
        )
      ))

      Logger.info("IN " + jsonAddConfigIn_1)

      val jsonAddConfigOut_1 = wC.handleMessage(jsonAddConfigIn_1)

      Logger.info("OUT " + jsonAddConfigOut_1)

      (jsonAddConfigOut_1 \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigSuccess().status
      (jsonAddConfigOut_1 \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status

      val jsonAddConfigIn_2 = Json.toJsObject(JsonAddConfigIn(
        json = JsonNames.ADD_CONFIG,
        params = JsonAddConfigParams(
          userId = this.userId,
          configUrl = this.configUrl_2
        )
      ))

      Logger.info("IN " + jsonAddConfigIn_2)
      val jsonAddConfigOut_2 = wC.handleMessage(jsonAddConfigIn_2)

      Logger.info("OUT " + jsonAddConfigOut_2)

      (jsonAddConfigOut_2 \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigSuccess().status
      (jsonAddConfigOut_2 \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status

      val jsonAddConfigIn_3 = Json.toJsObject(JsonAddConfigIn(
        json = JsonNames.ADD_CONFIG,
        params = JsonAddConfigParams(
          userId = this.userId,
          configUrl = this.configUrl_3
        )
      ))

      Logger.info("IN " + jsonAddConfigIn_3)

      val jsonAddConfigOut_3 = wC.handleMessage(jsonAddConfigIn_3)

      Logger.info("OUT " + jsonAddConfigOut_3)

      (jsonAddConfigOut_3 \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigSuccess().status
      (jsonAddConfigOut_3 \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    }
  }
}