package models.v016.config

import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.genericConfig.admin.controllers.websocket.WebClient
import util.CommonFunction
import play.api.Logger
import play.api.libs.json.Json
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.config.status.AddConfigAdded
import org.genericConfig.admin.shared.common.status.Success

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 22.04.2018
 */

@RunWith(classOf[JUnitRunner])
class Scenario_v016_1 extends Specification 
                           with BeforeAfterAll
                           with CommonFunction{
  
  val wC = WebClient.init
  var userId: String = ""
  val username = "user_1_v016"
  
  def beforeAll() = {
    val (username, userId): (String, String) = newAdminUser(this.username)
    this.userId = userId
    Logger.info("username : " + username)
    Logger.info("userId : " + userId)
    
  }
  
  def afterAll() = {
    Logger.info("Deleting Configs : " + deleteAllConfigs(this.username))
  }
  
  
  
  "Diese Spezifikation spezifiziert die Erstellung von mehreren Konfigurationen" >> {
    "fuer einen AdminUser" >> {
      
      val newConfigIn_1 = Json.obj(
        "json" -> JsonNames.ADD_CONFIG
        , "params" -> Json.obj(
            "adminId" -> this.userId,
            "configUrl" -> "//http://contig1/user_1_v016_1"
        )
    )
    val newConfigOut_1 = wC.handleMessage(newConfigIn_1)
	  
    //TODO com.orientechnologies.orient.core.exception.OValidationException: analysieren
    //Special type of exception which indicates that invalid index id was passed into storage and index data should be reloaded
    
//    Logger.info("newConfigIn_1 " + newConfigIn_1)
//    Logger.info("newConfigOut_1 " + newConfigOut_1)
    
    (newConfigOut_1 \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigAdded().status
	  (newConfigOut_1 \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    
	  val newConfigIn_2 = Json.obj(
        "json" -> JsonNames.ADD_CONFIG 
        , "params" -> Json.obj(
            "adminId" -> this.userId,
            "configUrl" -> "//http://contig1/user_1_v016_2"
        )
    )
    val newConfigOut_2 = wC.handleMessage(newConfigIn_2)
	  
//    Logger.info("newConfigIn_2 " + newConfigIn_2)
//    Logger.info("newConfigOut_2 " + newConfigOut_2)
    
    (newConfigOut_2 \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigAdded().status
	  (newConfigOut_2 \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
	  
	  val newConfigIn_3 = Json.obj(
        "json" -> JsonNames.ADD_CONFIG
        , "params" -> Json.obj(
            "adminId" -> this.userId,
            "configUrl" -> "//http://contig1/user_1_v016_3"
        )
    )
    val newConfigOut_3 = wC.handleMessage(newConfigIn_3)
	  
//    Logger.info("newConfigIn_3 " + newConfigIn_3)
//    Logger.info("newConfigOut_3 " + newConfigOut_3)
    
    (newConfigOut_3 \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigAdded().status
	  (newConfigOut_3 \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    }
  }
}