package models.v011

import play.api.libs.json._
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.genericConfig.admin.models.persistence.TestDB
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import org.genericConfig.admin.models.json.StatusErrorRegistUserAlreadyExist
import models.preparingConfigs.PrepareConfigsForSpecsv011
import org.genericConfig.admin.controllers.websocket.WebClient
import play.api.Logger
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.user.status.AddUserAlreadyExist

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.01.2017
 * 
 * Username = user2
 */

@RunWith(classOf[JUnitRunner])
class AddingAlredyExistingAdminUserSpecs extends Specification with AdminWeb with BeforeAfterAll{

  val wC = WebClient.init
  
  def beforeAll() = {
    PrepareConfigsForSpecsv011.prepareWithAlredyExistingUser(wC)
  }
  
  def afterAll() = {
  }
  
  "Specification spezifiziert die Registrierung des exestierenden Users" >> {
    "schon exstierenden User hinzufuegen" >> {
      val registerCS = Json.obj(
          "json" -> JsonNames.ADD_USER
          ,"params" -> Json.obj(
               "username" -> "userExist",
               "password"-> "userExist"
           )
      )
      
      val registerSC = wC.handleMessage(registerCS)
      
//      Logger.info("<- " + registerCS)
//      Logger.info("-> " + registerSC)
      "json" >> {
        (registerSC \ "json").asOpt[String].get === JsonNames.ADD_USER
      }
      "status" >> {
        (registerSC \ "result" \ "status" \ "addUser" \ "status").asOpt[String].get must_== AddUserAlreadyExist().status
      }
      "message" >> {
        (registerSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get must_== Success().status
      }
    }
  }
}