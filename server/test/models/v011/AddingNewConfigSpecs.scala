package models.v011

import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import play.api.libs.json.Json
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import models.preparingConfigs.PrepareConfigsForSpecsv011
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.status.Success
import org.genericConfig.admin.shared.status.login.UserExist
import play.api.Logger
import org.genericConfig.admin.shared.json.JsonNames
import org.genericConfig.admin.shared.status.config.AddConfigAdded

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 16.01.2017
 * 
 * Username = user3
 */

@RunWith(classOf[JUnitRunner])
class AddingNewConfigSpecs extends Specification 
                           with BeforeAfterAll{
  
  val userPassword = "user3"
  
  val wC = WebClient.init
  def beforeAll() = {
    PrepareConfigsForSpecsv011.prepareAddingNewConfig(wC)
    val count: Int = PrepareConfigsForSpecsv011.deleteConfigVertex(userPassword)
    require(count == 1, "Anzahl der geloeschten ConfigVertexes " + count)
  }
  
  def afterAll() = {}
  
  "Diese Spezifikation erzeugt neue Konfiguration fuer die Admin" >> {
    "Login mit AdminUser und fuege Konfig zu dem AdminUser hinzu" >> {
      
      val loginCS = Json.obj(
        "json" -> JsonNames.LOGIN
        ,"params" -> Json.obj(
            "username" -> userPassword,
            "password" -> userPassword
        )
    )
   
    val loginSC = wC.handleMessage(loginCS)
    (loginSC \ "result" \ "status" \ "userLogin" \ "status").asOpt[String].get must_== UserExist().status
    (loginSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get must_== Success().status
    
    Logger.info((loginSC \ "result" \ "adminId").asOpt[String].get)
    val createConfigCS = Json.obj(
        "json" -> JsonNames.CREATE_CONFIG
        , "params" -> Json.obj(
            "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
            "configUrl" -> "//http://contig1/user3"
        )
    )
    val createConfigSC = wC.handleMessage(createConfigCS)
	  
    //TODO com.orientechnologies.orient.core.exception.OValidationException: analysieren
    
//    Logger.info("createConfigCS " + createConfigCS)
//    Logger.info("createConfigSC " + createConfigSC)
    
    (createConfigSC \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigAdded().status
	  (createConfigSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    }
  }
}