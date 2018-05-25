package models.v011

import org.genericConfig.admin.controllers.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.genericConfig.admin.models.persistence.TestDB
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
import util.CommonFunction
import play.api.libs.json.Json
import org.genericConfig.admin.shared.wrapper.Wrapper
import org.genericConfig.admin.models.logic.RidToHash

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
    
//    val id1 = "#123:45"
//    val id2 = "#123:46"
//    val id3 = "#123:47"
    val hash1 = RidToHash.calculateHash("#23:61")
//    val hash2 = calculateHash(id2)
//    val hash3 = calculateHash(id3)
//    
    println("" + "  " + hash1)
//    println(id2 + "  " + hash2)
//    println(id3 + "  " + hash3)
//    
//    RidToHash.setIdAndHash(id1, hash1)
//    RidToHash.setIdAndHash(id2, hash2)
//    RidToHash.setIdAndHash(id3, hash3)
//    
//    
//    println(RidToHash.getId(hash1))
//    
//    println(RidToHash.getHash(id3))
    
//    RidToHash.cleanMap
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