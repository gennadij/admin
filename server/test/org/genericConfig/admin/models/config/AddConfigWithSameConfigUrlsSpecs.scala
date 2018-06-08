package org.genericConfig.admin.models.config

import models.preparingConfigs.GeneralFunctionToPrepareConfigs
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.ODBRecordDuplicated
import org.genericConfig.admin.shared.config.json.{JsonAddConfigIn, JsonAddConfigParams}
import org.genericConfig.admin.shared.config.status.AddConfigAlreadyExist
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.{JsValue, Json}
import util.CommonFunction

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 05.05.2017
  *
  * Username = user13
  * Username = user14
  */

@RunWith(classOf[JUnitRunner])
class AddConfigWithSameConfigUrlsSpecs extends Specification
  with BeforeAfterAll
  with GeneralFunctionToPrepareConfigs
  with CommonFunction {


  val webClient: WebClient = WebClient.init

  val configUrl = "http://config/user13"

  def beforeAll: Unit = {
    new PrepareConfig().prepareTwoSameConfigUrls(webClient)
    val count = deleteAdmin("user14")
    require(count == 1, count.toString)
  }

  def afterAll: Unit = {}

  "Hier wird die Erzeugung von zwei verschiedenen AdminUser mit gleicher ConfigUrl spezifiziert" >> {
    "ORecordDuplicatedException" >> {

      registerNewUser("user14", webClient)

      val user14 = getUserId("user14", webClient)


      val jsonAddConfigIn = Json.toJsObject(JsonAddConfigIn(
        json = JsonNames.ADD_CONFIG,
        params = JsonAddConfigParams(
          userId = user14,
          configUrl = configUrl
        )
      ))

      Logger.info("IN " + jsonAddConfigIn)

      val jsonAddConfigOut: JsValue = webClient.handleMessage(jsonAddConfigIn)

      Logger.info("OUT " + jsonAddConfigOut)

      (jsonAddConfigOut \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigAlreadyExist().status
      (jsonAddConfigOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === ODBRecordDuplicated().status

    }
  }

}