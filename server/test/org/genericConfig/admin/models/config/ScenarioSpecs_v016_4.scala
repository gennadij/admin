package org.genericConfig.admin.models.config

import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import util.CommonFunction
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.genericConfig.admin.controllers.websocket.WebClient
import play.api.Logger
import org.genericConfig.admin.shared.common.json.JsonNames
import play.api.libs.json.Json
import org.genericConfig.admin.shared.config.status.DeleteConfigDeleted
import org.genericConfig.admin.shared.common.status.Success
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 02.05.2018
 */
@RunWith(classOf[JUnitRunner])
class ScenarioSpecs_v016_4 extends Specification 
                           with BeforeAfterAll
                           with CommonFunction{
  
  val wC = WebClient.init
  var userId: String = ""
  val username = "user_v016_4"
  
  def beforeAll() = {
    val (username, userId): (String, String) = addAdminUser(this.username)
    this.userId = userId
    Logger.info("username : " + username)
    Logger.info("userId : " + userId)
  }
  
  def afterAll() = {
//    Logger.info("Deleting Configs : " + deleteAllConfigs(this.username))
  }
  
  "Diese Spezifikation spezifiziert die Entfernng einer Konfigurationen" >> {
    "AdminUser=user_v016_4" >> {
      
      val createConfigOut = createConfig(userId, "//http://contig1/user_v016_4", wC)
      
      val configId = (createConfigOut \ "result" \ "configId").asOpt[String].get
      
      Logger.info(configId)
      
      val jsonDeleteConfigIn = Json.obj(
          "json" -> JsonNames.DELET_CONFIG
          , "params" -> Json.obj(
              "configId" -> configId,
              "configUrl" -> "//http://contig1/user_v016_4"
          )
      )
      
      val jsonDeleteConfigOut = wC.handleMessage(jsonDeleteConfigIn)
      
      Logger.info("<- " + jsonDeleteConfigIn)
      Logger.info("-> " + jsonDeleteConfigOut)
      
      (jsonDeleteConfigOut \ "json").asOpt[String].get === JsonNames.DELET_CONFIG
      (jsonDeleteConfigOut \ "result" \ "status" \ "addConfig").asOpt[String] === None
      (jsonDeleteConfigOut \ "result" \ "status" \ "getConfigs" \ "status").asOpt[String] === None
      (jsonDeleteConfigOut \ "result" \ "status" \ "updateConfig").asOpt[String] === None
      (jsonDeleteConfigOut \ "result" \ "status" \ "deleteConfig" \ "status").asOpt[String].get === DeleteConfigDeleted().status
      (jsonDeleteConfigOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    }
  }
}