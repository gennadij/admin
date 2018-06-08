//package models.v011
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
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// *
// * Created by Gennadi Heimann 25.01.2016
// *
// * Username = user8
// */
//
//@RunWith(classOf[JUnitRunner])
//class AddingStepSpecs extends Specification
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
//    require(count == List(1, 0, 1), "Anzahl der geloeschten Steps" + count)
//  }
//
//  def beforeAll(): Unit = {
//    PrepareConfigsForSpecsv011.prepareAddStep(wC)
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
//              "componentId" -> componentIds(0),
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
//      (stepOut_1 \ "result" \ "status").asOpt[String].get === StatusSuccessfulStepCreated.status
//      (stepOut_1 \ "result" \ "message").asOpt[String].get === StatusSuccessfulStepCreated.message
//
//
//      val connectionComponentToStepIn = Json.obj(
//          "dtoId" -> DTOIds.CONNECTION_COMPONENT_TO_STEP,
//          "dto" -> DTONames.CONNECTION_COMPONENT_TO_STEP,
//          "params" -> Json.obj(
//              "componentId" -> componentIds(1),
//              "stepId" -> (stepOut_1 \ "result" \ "stepId").asOpt[String].get
//          )
//      )
////      Logger.info(connectionComponentToStepIn.toString())
//      val connectionComponentToStepOut = wC.handleMessage(connectionComponentToStepIn)
////      Logger.info(connectionComponentToStepOut.toString())
//      (connectionComponentToStepOut \ "dtoId").asOpt[Int].get === DTOIds.CONNECTION_COMPONENT_TO_STEP
//      (connectionComponentToStepOut \ "dto").asOpt[String].get === DTONames.CONNECTION_COMPONENT_TO_STEP
//      (connectionComponentToStepOut \ "result" \ "status").asOpt[String].get === StatusSuccessfulConnectionComponentToStep.status
//      (connectionComponentToStepOut \ "result" \ "message").asOpt[String].get === StatusSuccessfulConnectionComponentToStep.message
//
//      val stepIn_2 = Json.obj(
//          "dtoId" -> DTOIds.CREATE_STEP,
//          "dto" -> DTONames.CREATE_STEP,
//          "params" -> Json.obj(
//              "componentId" -> componentIds(2),
//              "nameToShow" -> "Component",
//              "kind" -> "default",
//              "selectionCriterium" -> Json.obj(
//                  "min" -> 1,
//                  "max" -> 1
//              )
//
//          )
//      )
////      Logger.info(stepIn_2.toString())
//
//      val stepOut_2 = wC.handleMessage(stepIn_2)
////      Logger.info(stepOut_2.toString())
//      (stepOut_2 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
//      (stepOut_2 \ "dto").asOpt[String].get === DTONames.CREATE_STEP
//      (stepOut_2 \ "result" \ "status").asOpt[String].get === StatusSuccessfulStepCreated.status
//      (stepOut_2 \ "result" \ "message").asOpt[String].get === StatusSuccessfulStepCreated.message
//    }
//  }
//}