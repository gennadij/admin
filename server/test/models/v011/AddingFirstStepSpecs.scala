package models.v011

import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import models.preparingConfigs.PrepareConfigsForSpecsv011
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 16.01.2017
 * 
 * Changes in
 * @version 0.1.0
 * @version 0.1.3
 * 
 * Username = user4
 */

@RunWith(classOf[JUnitRunner])
class AddingFirstStepSpecs extends Specification 
                          with AdminWeb
                          with BeforeAfterAll{
                          
  
  var userId = ""
  val wC = WebClient.init
  def beforeAll() = {
    userId = login
    PrepareConfigsForSpecsv011.prepareAddingFirstStep(wC)
  }
  
  def afterAll() = {
//    val count = StepVertex.removeStep(userId)
//    require(count == 1, "Anzahl der geloeschten Steps " + count)
    
  }

  
  "Diese Specification spezifiziert das Hinzufügen von dem Step zu der Konfiguration" >> {
    "FirstStep hinzufuegen" >> {
      val firstStepCS = Json.obj(
        "dtoId" -> JsonNames.CREATE_FIRST_STEP,
        "params" -> Json.obj(
          "configId" -> userId,
          "nameToShow" -> "FirstStep",
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      val firstStepSC: JsValue = wC.handleMessage(firstStepCS)
      
//      (firstStepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_FIRST_STEP
//      (firstStepSC \ "dto").asOpt[String].get === DTONames.CREATE_FIRST_STEP
//      (firstStepSC \ "result" \ "status").asOpt[String].get === StatusSuccessfulFirstStepCreated.status
//      (firstStepSC \ "result" \ "message").asOpt[String].get === StatusSuccessfulFirstStepCreated.message
      
      val twiceStepCS = Json.obj(
        "dtoId" -> JsonNames.CREATE_FIRST_STEP,
        "params" -> Json.obj(
          "configId" -> userId,
          "nameToShow" -> "FirstStep",
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      val twiceStepConfigTreeSC: JsValue = wC.handleMessage(twiceStepCS)
//      (twiceStepConfigTreeSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_FIRST_STEP
//      (twiceStepConfigTreeSC \ "dto").asOpt[String].get === DTONames.CREATE_FIRST_STEP
//      (twiceStepConfigTreeSC \ "result" \ "status").asOpt[String].get === StatusErrorFirstStepExist.status
//      (twiceStepConfigTreeSC \ "result" \ "message").asOpt[String].get === StatusErrorFirstStepExist.message
      "" === ""
    }
  }
  
  def login(): String = {
    val user = "user4"
      val jsonClientServer = Json.obj(
          "dtoId" -> JsonNames.LOGIN,
          "params" -> Json.obj(
              "username" -> user,
              "password"-> user
           )
      )
      val jsonServerClient: JsValue = wC.handleMessage(jsonClientServer)
      ((jsonServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get
      
  }
}