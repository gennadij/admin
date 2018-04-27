package models.v011

import play.api.libs.json._
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.genericConfig.admin.models.persistence.TestDB
import org.genericConfig.admin.models.persistence.GlobalConfigForDB
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import org.genericConfig.admin.models.json.StatusSuccessfulRegist
import org.genericConfig.admin.controllers.websocket.WebClient
import play.api.Logger
import models.preparingConfigs.PrepareConfigsForSpecsv011
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.registration.status.AddedUser
import org.genericConfig.admin.shared.common.status.Success

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.01.2017
 * 
 * Username = user1
 */

@RunWith(classOf[JUnitRunner])
class AddingNewAdminUserSpecs extends Specification 
                                with AdminWeb 
                                with BeforeAfterAll{

  sequential
  
  def beforeAll() = {
    val count = PrepareConfigsForSpecsv011.deleteAdmin("user1")
    require(count == 1, "Anzahl der geloescten AdminUserVertexes " + count)
  }
  
  def afterAll() = {
    
  }
  
  "Specification spezifiziert die Registrierung des neuen Users" >> {
    "Das Hinzufuegen des nicht exstierenden/neuen User" >> {
      val registerCS = Json.obj(
          "json" -> JsonNames.REGISTRATION
          ,"params" -> Json.obj(
               "username" -> "user1",
               "password"-> "user1"
           )
      )
      val wC = WebClient.init
      val registerSC = wC.handleMessage(registerCS)
//      Logger.info("<- " + registerCS)
//      Logger.info("-> " + registerSC)
      
      "dto" >> {
        (registerSC \ "json").asOpt[String].get === JsonNames.REGISTRATION
      }
      "username" >> {
        (registerSC \ "result" \ "username").asOpt[String].get must_== "user1"
      }
      "status" >> {
        (registerSC \ "result" \ "status" \ "addUser" \ "status").asOpt[String].get must_== AddedUser().status
      }
      "message" >> {
        (registerSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get must_== Success().status
      }
    }
  }
}