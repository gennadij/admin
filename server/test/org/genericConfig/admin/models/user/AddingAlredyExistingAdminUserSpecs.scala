package org.genericConfig.admin.models.user

import play.api.libs.json._
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import play.api.libs.json.JsValue.jsValueToJsLookup
import org.genericConfig.admin.controllers.websocket.WebClient
import play.api.Logger
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.user.status.AddUserAlreadyExist
import org.genericConfig.admin.shared.common.status.Error

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.01.2017
 * 
 * Username = user2
 */

@RunWith(classOf[JUnitRunner])
class AddingAlredyExistingAdminUserSpecs extends Specification with AdminWeb with BeforeAfterAll{

  val wC: WebClient = WebClient.init
  
  def beforeAll(): Unit = {
    new PrepareUser().prepareWithAlredyExistingUser(wC)
  }
  
  def afterAll(): Unit = {
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
      
      Logger.info("<- " + registerCS)
      Logger.info("-> " + registerSC)
      "json" >> {
        (registerSC \ "json").asOpt[String].get === JsonNames.ADD_USER
      }
      "userId" >> {
        (registerSC \ "result" \ "userId").asOpt[String].get.size must_== 32
      }
      "status" >> {
        (registerSC \ "result" \ "status" \ "addUser" \ "status").asOpt[String].get must_== AddUserAlreadyExist().status
      }
      "status" >> {
        (registerSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Error().status
      }
    }
  }
}