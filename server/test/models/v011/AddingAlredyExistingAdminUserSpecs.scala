package models.v011

import play.api.libs.json._
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.genericConfig.admin.models.persistence.TestDB
import org.genericConfig.admin.models.persistence.GlobalConfigForDB
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import org.genericConfig.admin.models.json.StatusErrorRegistUserAlreadyExist
import models.preparingConfigs.PrepareConfigsForSpecsv011
import org.genericConfig.admin.controllers.websocket.WebClient

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
          "dtoId" -> DTOIds.REGISTRATION,
          "dto" -> DTONames.REGISTRATION
          ,"params" -> Json.obj(
               "username" -> "userExist",
               "password"-> "userExist"
           )
      )
      
      val registerSC = wC.handleMessage(registerCS)
      "dtoId" >> {
        (registerSC \ "dtoId").asOpt[Int].get === DTOIds.REGISTRATION
      }
      "dto" >> {
        (registerSC \ "dto").asOpt[String].get === DTONames.REGISTRATION
      }
      "username" >> {
        (registerSC \ "result" \ "username").asOpt[String].get must_== "userExist"
      }
      "status" >> {
        (registerSC \ "result" \ "status").asOpt[String].get must_== StatusErrorRegistUserAlreadyExist.status
      }
      "message" >> {
        (registerSC \ "result" \ "message").asOpt[String].get must_== StatusErrorRegistUserAlreadyExist.message
      }
    }
  }
}