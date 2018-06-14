//package models.v015
//
//import org.specs2.runner.JUnitRunner
//import org.junit.runner.RunWith
//import org.specs2.mutable.Specification
//import org.specs2.specification.BeforeAfterAll
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import models.preparingConfigs.PrepareConfigsForSpecsv015
//import models.websocket.WebClient
//import play.api.libs.json.Json
//import models.json.DTOIds
//import models.json.DTONames
//import models.json.StatusWarningAdditionalStepInLevelCS
//import play.api.libs.json.JsValue
//import models.preparingConfigs.UsernamesForSpecs
//import play.api.Logger
//import models.tempConfig.TempConfigurations
//
///**
//	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
//	*
//	* Created by Gennadi Heimann 31.08.2017
//	*
//	* Username = user22_v015
//	*/
//
//@RunWith(classOf[JUnitRunner])
//class SelectionCriterium13ForAdditionalStepInLevelCSSpec 
//  extends Specification
//  with BeforeAfterAll
//  with GeneralFunctionToPrepareConfigs{
//  
//  val wC= WebClient.init
//  
//  def afterAll(): Unit = {
//    TempConfigurations.getAndRemoveAdditionalStepInLevelCS
//  }
//  def beforeAll(): Unit = {
//    Logger.info("SelectionCriterium13ForAdditionalStepInLevelCSSpec")
//    PrepareConfigsForSpecsv015.selectionCriterium13ForAdditionalStepInLevelCSSpec(wC)
//    
//  }
//  
//  "Spezifikation fuer einen Warnmeldung bei den VaterStep mit SelectionCriterium 1,3 und erstellung zusaetzlichen Schritt in Level CS " >> {
//    "Erstelle zweite Schritt im Level CS" >> {
//      val username = UsernamesForSpecs.userSelectionCriterium13ForAdditionalStepInLevelCS
//      val configId = loginForConfigId(username, wC)
//			val componentId = getComponentId(s"component_1_3_$username")
//			val nameToShow = s"step_3_$username"
//			val kind = "default"
//			val min = 1
//			val max = 1
//			val stepCS = Json.obj(
//				"dtoId" -> DTOIds.CREATE_STEP,
//				"dto" -> DTONames.CREATE_STEP,
//				"params" -> Json.obj(
//					"componentId" -> componentId,
//					"nameToShow" -> nameToShow,
//					"kind" -> kind,
//					"selectionCriterium" -> Json.obj(
//						"min" -> min,
//						"max" -> max
//					)
//				)
//			)
//
//			val stepSC = wC.handleMessage(stepCS)
//			
//			println(stepSC)
//			
//			(stepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
//			(stepSC \ "dto").asOpt[String].get === DTONames.CREATE_STEP
//			(stepSC \ "result" \ "status").asOpt[String].get === StatusWarningAdditionalStepInLevelCS.status
//			(stepSC \ "result" \ "message").asOpt[String].get === StatusWarningAdditionalStepInLevelCS.message
//			(stepSC \ "result" \ "visualProposalForAdditionalStepInOneLevel")(0).asOpt[String].get === "graying"
//			(stepSC \ "result" \ "visualProposalForAdditionalStepInOneLevel")(1).asOpt[String].get === "remove"
//			(stepSC \ "result" \ "visualProposalForAdditionalStepInOneLevel")(2).asOpt[String].get === "unselectable"
//			(stepSC \ "result" \ "dependenciesForAdditionalStepsInOneLevel").asOpt[Set[JsValue]].get.size === 0
//    }
//  }
//
//}