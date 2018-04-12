//package models.v013
//
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import org.specs2.mutable.Specification
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import models.preparingConfigs.PrepareConfigsForSpecsv013
//import models.persistence.GlobalConfigForDB
//import play.api.libs.json.Json
//import models.json.DTOIds
//import models.json.DTONames
//import play.api.libs.json.JsValue
//import models.json.StatusErrorFaultyComponentId
//import models.websocket.WebClient
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann Jun 16, 2017
// */
// @RunWith(classOf[JUnitRunner])
//class StepComponentIdFaultySpecs extends Specification
//                          with BeforeAfterAll
//                          with GeneralFunctionToPrepareConfigs{
//  
//  val webClient = WebClient.init
//  
//  def afterAll(): Unit = {
//    
//  }
//  def beforeAll(): Unit = {
//    
//    PrepareConfigsForSpecsv013.prepareStepComponentIdFaulty(webClient)
//    
//  }
//  
//  "Diese Specification spezifiziert das Hinzufügen von dem Step zu der Konfiguration mit difekten ComponentId" >> {
//    "Step hinzufuegen" >> {
//      
//      val stepCS_1 = Json.obj(
//          "dtoId" -> DTOIds.CREATE_STEP,
//          "dto" -> DTONames.CREATE_STEP,
//          "params" -> Json.obj(
//              "componentId" -> "#1:1",
//              "nameToShow" -> "Step_1",
//              "kind" -> "default",
//              "selectionCriterium" -> Json.obj(
//                  "min" -> 1,
//                  "max" -> 1
//              )
//              
//          )
//      )
//      val stepSC_1 = webClient.handleMessage(stepCS_1)
//      (stepSC_1 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
//      (stepSC_1 \ "dto").asOpt[String].get === DTONames.CREATE_STEP 
//      (stepSC_1 \ "result" \ "status").asOpt[String].get === StatusErrorFaultyComponentId.status
//      (stepSC_1 \ "result" \ "message").asOpt[String].get === StatusErrorFaultyComponentId.message
//    }
//  }
//}