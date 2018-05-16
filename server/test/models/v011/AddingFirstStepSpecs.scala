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
import org.genericConfig.admin.shared.step.status.AddStepSuccess
import org.genericConfig.admin.shared.step.status.AppendStepSuccess
import org.genericConfig.admin.shared.common.status.Success
import util.CommonFunction
import play.api.Logger
import org.genericConfig.admin.shared.step.status.AddStepAlreadyExist
import org.genericConfig.admin.shared.step.status.AppendStepError
import org.genericConfig.admin.shared.common.status.Error
import org.genericConfig.admin.shared.common.status.ODBRecordIdDefect
import org.genericConfig.admin.shared.step.status.AddStepDefectComponentOrConfigId

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
                          with BeforeAfterAll
                          with CommonFunction{
                          
  
  var configId = ""
//  var configIds = Set[String]()
  val wC = WebClient.init
  def beforeAll() = {
    configId = login
    Logger.info(configId)
//    configIds = getConfigs(userId, wC)
    PrepareConfigsForSpecsv011.prepareAddingFirstStep(wC)
  }
  
  def afterAll() = {
    val count = deleteStepAppendedToConfig(configId)
    require(count == 1, "Anzahl der geloeschten Steps " + count)
    
  }

  
  "Diese Specification spezifiziert das Hinzufügen von dem Step zu der Konfiguration" >> {
    "FirstStep hinzufuegen" >> {
      
      val firstStepCS = Json.obj(
        "json" -> JsonNames.ADD_FIRST_STEP,
        "params" -> Json.obj(
          "configId" -> configId,
          "componentId" -> "",
          "nameToShow" -> "FirstStep",
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      
      Logger.info("<-" + firstStepCS)
      
      val firstStepSC: JsValue = wC.handleMessage(firstStepCS)
      
      Logger.info("->" + firstStepSC )
      
      (firstStepSC \ "json").asOpt[String].get === JsonNames.ADD_FIRST_STEP
      (firstStepSC \ "result" \ "status" \ "addStep" \ "status").asOpt[String].get === AddStepSuccess().status
      (firstStepSC \ "result" \ "status" \ "deleteStep" \ "status").asOpt[String] === None
      (firstStepSC \ "result" \ "status" \ "updateStep" \ "status").asOpt[String] === None
      (firstStepSC \ "result" \ "status" \ "appendStep" \ "status").asOpt[String].get === AppendStepSuccess().status
      (firstStepSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
      
      val twiceStepCS = Json.obj(
        "json" -> JsonNames.ADD_FIRST_STEP,
        "params" -> Json.obj(
          "configId" -> configId,
          "componentId" -> "",
          "nameToShow" -> "FirstStep",
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      Logger.info("<-" + twiceStepCS)
      val twiceStepConfigTreeSC: JsValue = wC.handleMessage(twiceStepCS)
      Logger.info("->" + twiceStepConfigTreeSC)
      
      (twiceStepConfigTreeSC \ "json").asOpt[String].get === JsonNames.ADD_FIRST_STEP
      (twiceStepConfigTreeSC \ "result" \ "status" \ "addStep" \ "status").asOpt[String].get === AddStepAlreadyExist().status
      (twiceStepConfigTreeSC \ "result" \ "status" \ "deleteStep" \ "status").asOpt[String] === None
      (twiceStepConfigTreeSC \ "result" \ "status" \ "updateStep" \ "status").asOpt[String] === None
      (twiceStepConfigTreeSC \ "result" \ "status" \ "appendStep" \ "status").asOpt[String] === None
      (twiceStepConfigTreeSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Error().status
      
       val stepIn_3 = Json.obj(
        "json" -> JsonNames.ADD_FIRST_STEP,
        "params" -> Json.obj(
          "configId" -> "#1:1",
          "componentId" -> "",
          "nameToShow" -> "FirstStep",
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      Logger.info("<-" + stepIn_3)
      
      val stepOut_3: JsValue = wC.handleMessage(stepIn_3)
      
      Logger.info("->" + twiceStepConfigTreeSC)
      
      (stepOut_3 \ "json").asOpt[String].get === JsonNames.ADD_FIRST_STEP
      (stepOut_3 \ "result" \ "status" \ "addStep" \ "status").asOpt[String].get === AddStepDefectComponentOrConfigId().status
      (stepOut_3 \ "result" \ "status" \ "deleteStep" \ "status").asOpt[String] === None
      (stepOut_3 \ "result" \ "status" \ "updateStep" \ "status").asOpt[String] === None
      (stepOut_3 \ "result" \ "status" \ "appendStep" \ "status").asOpt[String] === None
      (stepOut_3 \ "result" \ "status" \ "common" \ "status").asOpt[String].get === ODBRecordIdDefect().status
      
      //TODO all add delete and update auf null pruefen
    }
  }
  
  def login(): String = {
    val user = "user4"
      val jsonClientServer = Json.obj(
          "json" -> JsonNames.LOGIN,
          "params" -> Json.obj(
              "username" -> user,
              "password"-> user
           )
      )
      val jsonServerClient: JsValue = wC.handleMessage(jsonClientServer)
      
      Logger.info("<- " + jsonClientServer)
      Logger.info("-> " + jsonServerClient)
      ((jsonServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get
  }
}