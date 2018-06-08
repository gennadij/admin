package org.genericConfig.admin.models.config

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.config.json.{JsonDeleteConfigIn, JsonDeleteConfigParams}
import org.genericConfig.admin.shared.config.status.DeleteConfigDeleted
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
 * Created by Gennadi Heimann 02.05.2018
 */
@RunWith(classOf[JUnitRunner])
class DeleteConfigSpecs extends Specification
                           with BeforeAfterAll
                           with CommonFunction{
  
  val wC: WebClient = WebClient.init
  var userId: String = ""
  val username = "user_v016_4"
  
  def beforeAll(): Unit = {
    val (username, userId, _): (String, String, String) = addUser(this.username)
    this.userId = userId
    Logger.info("username : " + username)
    Logger.info("userId : " + userId)
  }
  
  def afterAll(): Unit = {
//    Logger.info("Deleting Configs : " + deleteAllConfigs(this.username))
  }
  
  "Diese Spezifikation spezifiziert die Entfernng einer Konfigurationen" >> {
    "AdminUser=user_v016_4" >> {
      
      val (configId, _) = addConfig(userId, "//http://contig1/user_v016_4")

      val jsonDeleteConfigIn = Json.toJsObject(
        JsonDeleteConfigIn(
          json = JsonNames.DELET_CONFIG,
          params = JsonDeleteConfigParams(
            configId = configId,
            configUrl = "//http://contig1/user_v016_4"
          )

        )
      )

      Logger.info("IN " + jsonDeleteConfigIn)

      val jsonDeleteConfigOut = wC.handleMessage(jsonDeleteConfigIn)
      
      Logger.info("OUT " + jsonDeleteConfigOut)
      
      (jsonDeleteConfigOut \ "json").asOpt[String].get === JsonNames.DELET_CONFIG
      (jsonDeleteConfigOut \ "result" \ "status" \ "addConfig").asOpt[String] === None
      (jsonDeleteConfigOut \ "result" \ "status" \ "getConfigs" \ "status").asOpt[String] === None
      (jsonDeleteConfigOut \ "result" \ "status" \ "updateConfig").asOpt[String] === None
      (jsonDeleteConfigOut \ "result" \ "status" \ "deleteConfig" \ "status").asOpt[String].get === DeleteConfigDeleted().status
      (jsonDeleteConfigOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    }
  }
}