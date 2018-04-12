//package models.v015
//
//import org.specs2.mutable.Specification
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import play.api.libs.json.Json
//import models.json.DTOIds
//import models.json.DTONames
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import models.persistence.db.orientdb.StepVertex
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import models.persistence.GlobalConfigForDB
//import models.persistence.TestDB
//import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
//import play.api.libs.json.JsValue.jsValueToJsLookup
//import play.api.libs.json.Json.toJsFieldJsValueWrapper
//import models.json.StatusSuccessfulStepCreated
//import models.json.StatusWarningAdditionalStepInLevelCS
//import models.json.StatusSuccessfulConnectionComponentToStep
//import models.preparingConfigs.PrepareConfigsForSpecsv011
//import play.api.Logger
//import models.websocket.WebClient
//import models.json.StatusErrorFaultyComponentId
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 13.10.2017
// * 
// * Username = user12_v0.1.5
// */
//
//@RunWith(classOf[JUnitRunner])
//class AddingDefectStepSpecs extends Specification 
//    with BeforeAfterAll
//    with GeneralFunctionToPrepareConfigs{
//  
//  val wC = WebClient.init
//  
//  def afterAll(): Unit = {
//    val stepId: String = getFirstStep("user8")
//    val componentIds: List[String] = getComponentsFromFirstStep(stepId)
//    val count = componentIds map {StepVertex.deleteStepFromComponent(_)}
//    
//    require(count == List(0, 0, 0), "Anzahl der geloeschten Steps" + count)
//  }
//  
//  def beforeAll(): Unit = {
//    Logger.info("AddingDefectStepSpecs")
//    PrepareConfigsForSpecsv011.prepareDefectAddStep(wC)
//    
//  }
//  
//  "Specification spezifiziert die Erzeugung von dem Step" >> {
//    
//  
//    "component(0,1) -> step_1 und component(2) -> step 2" >> {
//      
//      val stepId: String = getFirstStep("user8")
//      val componentIds: List[String] = getComponentsFromFirstStep(stepId)
//      
//      val stepIn_1 = Json.obj(
//          "dtoId" -> DTOIds.CREATE_STEP,
//          "dto" -> DTONames.CREATE_STEP,
//          "params" -> Json.obj(
//              "componentId" -> "#0:0",
//              "nameToShow" -> "Component",
//              "kind" -> "default",
//              "selectionCriterium" -> Json.obj(
//                  "min" -> 1,
//                  "max" -> 1
//              )
//              
//          )
//      )
////      Logger.info(stepIn_1.toString())
//      val stepOut_1 = wC.handleMessage(stepIn_1)
////      Logger.info(stepOut_1.toString())
//      (stepOut_1 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
//      (stepOut_1 \ "dto").asOpt[String].get === DTONames.CREATE_STEP 
//      (stepOut_1 \ "result" \ "status").asOpt[String].get === StatusErrorFaultyComponentId.status
//      (stepOut_1 \ "result" \ "message").asOpt[String].get === StatusErrorFaultyComponentId.message
//    }
//  }
//}