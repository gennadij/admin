package org.genericConfig.admin.models.user

import org.genericConfig.admin.controllers.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import org.genericConfig.admin.controllers.websocket.WebClient
import play.api.Logger
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import util.CommonFunction
import play.api.libs.json.Json
import org.genericConfig.admin.models.wrapper.Wrapper
import org.genericConfig.admin.shared.user.status.AddUserSuccess

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
                                with BeforeAfterAll
                                with CommonFunction
                                with Wrapper{

  sequential
  
  def beforeAll() = {
    val count = deleteAdmin("user1")
    require(count == 1, "Anzahl der geloescten AdminUserVertexes " + count)
  }
  
  def afterAll() = {
    
  }
  
  "Specification spezifiziert die Registrierung des neuen Users" >> {
    "Das Hinzufuegen des nicht exstierenden/neuen User" >> {
      val registerCS = Json.obj(
          "json" -> JsonNames.ADD_USER
          ,"params" -> Json.obj(
               "username" -> "user1",
               "password"-> "user1"
           )
      )
      val wC = WebClient.init
      val registerSC = wC.handleMessage(registerCS)
      Logger.info("<- " + registerCS)
      Logger.info("-> " + registerSC)
      
      "dto" >> {
        (registerSC \ "json").asOpt[String].get === JsonNames.ADD_USER
      }
      "userId" >> {
        (registerSC \ "result" \ "userId").asOpt[String].get.size must be_<=(32)
      }
      "username" >> {
        (registerSC \ "result" \ "username").asOpt[String].get must_== "user1"
      }
      "status" >> {
        (registerSC \ "result" \ "status" \ "addUser" \ "status").asOpt[String].get must_== AddUserSuccess().status
      }
      "message" >> {
        (registerSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get must_== Success().status
      }
    }
  }
}