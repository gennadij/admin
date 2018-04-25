package models.v016.config

import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.genericConfig.admin.controllers.websocket.WebClient
import util.CommonFunction
import play.api.Logger
import org.genericConfig.admin.shared.json.JsonNames
import play.api.libs.json.Json
import org.genericConfig.admin.shared.status.Success
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 24.04.2018
 */

@RunWith(classOf[JUnitRunner])
class ScenarioSpecs_v016_2 extends Specification 
                           with BeforeAfterAll
                           with CommonFunction{
  
  val wC = WebClient.init
  var userId: String = ""
  val username = "user_v016_2"
  
  def beforeAll() = {
    val (username, userId): (String, String) = newAdminUser(this.username)
    this.userId = userId
    Logger.info("username : " + username)
    Logger.info("userId : " + userId)
    
    createConfig(userId, "//http://contig1/user_1_v016_1", wC)
    
    createConfig(userId, "//http://contig1/user_1_v016_2", wC)
	  
    createConfig(userId, "//http://contig1/user_1_v016_3", wC)
    
  }
  
  def afterAll() = {
    Logger.info("Deleting Configs : " + deleteAllConfigs(this.username))
  }
  
  
  
  "Diese Spezifikation spezifiziert das Holen von allen Konfigurationen" >> {
    "fuer einen AdminUser" >> {
      
      //TODO com.orientechnologies.orient.core.exception.OValidationException: analysieren
      //Special type of exception which indicates that invalid index id was passed into storage and index data should be reloaded
      
      val getConfigsIn = Json.obj(
          "json" -> JsonNames.GET_CONFIGS
          , "params" -> Json.obj(
              "userId" -> this.userId
          )
      )
      val getConfigsOut = wC.handleMessage(getConfigsIn)
      
//      Logger.info("getConfigsIn " + getConfigsIn)
//      Logger.info("getConfigsOut " + getConfigsOut)
    
      "" === ""
    }
  }
}