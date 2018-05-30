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
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.config.status.UpdateConfigUpdated
import play.api.libs.json.JsValue
import org.genericConfig.admin.shared.config.status.GetConfigsGot
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.05.2018
 */
@RunWith(classOf[JUnitRunner])
class ScenarioSpecs_v016_6 extends Specification 
                           with BeforeAfterAll
                           with CommonFunction{
  
  val wC = WebClient.init
  var userId: String = ""
  val username = "user_v016_6"
  
  def beforeAll() = {
    val (username, userId): (String, String) = addAdminUser(this.username)
    this.userId = userId
    Logger.info("username : " + username)
    Logger.info("userId : " + userId)
  }
  
  def afterAll() = {
    Logger.info("Deleting Configs : " + deleteAllConfigs(this.username))
  }
  
  "Diese Spezifikation spezifiziert die Editierung einer Konfigurationen" >> {
    "AdminUser=user_v016_6" >> {
      
      val createConfigOut = createConfig(userId, "//http://contig1/user_v016_6", wC)
      
      val configId = (createConfigOut \ "result" \ "configId").asOpt[String].get
      
      Logger.info(configId)
      
      val jsonEditConfigIn = Json.obj(
          "json" -> JsonNames.UPDATE_CONFIG
          , "params" -> Json.obj(
              "configId" -> configId,
              "configUrl" -> "//http://contig1/user_v016_4_updated"
          )
      )
      
      val jsonEditConfigOut = wC.handleMessage(jsonEditConfigIn)
      
      Logger.info("<- " + jsonEditConfigIn)
      Logger.info("-> " + jsonEditConfigOut)
      
      (jsonEditConfigOut \ "json").asOpt[String].get === JsonNames.UPDATE_CONFIG
      (jsonEditConfigOut \ "result" \ "status" \ "addConfig").asOpt[String] === None
      (jsonEditConfigOut \ "result" \ "status" \ "getConfigs" \ "status").asOpt[String] === None
      (jsonEditConfigOut \ "result" \ "status" \ "updateConfig" \ "status").asOpt[String].get === UpdateConfigUpdated().status
      (jsonEditConfigOut \ "result" \ "status" \ "deleteConfig" \ "status").asOpt[String] === None
      (jsonEditConfigOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
      
      val getConfigsIn = Json.obj(
          "json" -> JsonNames.GET_CONFIGS
          , "params" -> Json.obj(
              "userId" -> this.userId
          )
      )
      val getConfigsOut = wC.handleMessage(getConfigsIn)
      
      Logger.info("getConfigsIn " + getConfigsIn)
      Logger.info("getConfigsOut " + getConfigsOut)
    
      (getConfigsOut \ "json").asOpt[String].get === JsonNames.GET_CONFIGS
      (getConfigsOut \ "result" \ "configs").asOpt[Set[JsValue]].get.size === 1
      ((getConfigsOut \ "result" \ "configs")(0) \ "configUrl").asOpt[String].get === "//http://contig1/user_v016_4_updated"
      (getConfigsOut \ "result" \ "status" \ "addConfig").asOpt[String] === None
      (getConfigsOut \ "result" \ "status" \ "getConfigs" \ "status").asOpt[String].get === GetConfigsGot().status
      (getConfigsOut \ "result" \ "status" \ "deleteConfig").asOpt[String] === None
      (getConfigsOut \ "result" \ "status" \ "updateConfig").asOpt[String] === None
      (getConfigsOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    }
  }
}