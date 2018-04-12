package models.v011

import play.api.libs.json._
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.genericConfig.admin.models.json.DTONames
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.genericConfig.admin.models.persistence.TestDB
import org.genericConfig.admin.models.persistence.GlobalConfigForDB
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import org.genericConfig.admin.models.persistence.db.orientdb.AdminUserVertex
import org.genericConfig.admin.models.json.StatusSuccessfulRegist
import org.genericConfig.admin.controllers.websocket.WebClient

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
    val count = AdminUserVertex.deleteAdmin("user1")
    require(count == 1, "Anzahl der geloescten AdminUserVertexes " + count)
  }
  
  def afterAll() = {
    
  }
  
  "Specification spezifiziert die Registrierung des neuen Users" >> {
    "Das Hinzufuegen des nicht exstierenden/neuen User" >> {
      val registerCS = Json.obj(
          "json" -> DTONames.REGISTRATION
          ,"params" -> Json.obj(
               "username" -> "user1",
               "password"-> "user1"
           )
      )
      val wC = WebClient.init
      val registerSC = wC.handleMessage(registerCS)
      "dto" >> {
        (registerSC \ "dto").asOpt[String].get === DTONames.REGISTRATION
      }
      "username" >> {
        (registerSC \ "result" \ "username").asOpt[String].get must_== "user1"
      }
      "status" >> {
        (registerSC \ "result" \ "status").asOpt[String].get must_== StatusSuccessfulRegist.status
      }
      "message" >> {
        (registerSC \ "result" \ "message").asOpt[String].get must_== StatusSuccessfulRegist.message
      }
    }
  }
}