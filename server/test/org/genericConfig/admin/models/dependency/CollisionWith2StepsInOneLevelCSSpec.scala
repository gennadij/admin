//package models.v015
//
//import org.specs2.mutable.Specification
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import models.preparingConfigs.PrepareConfigsForSpecsv015
//import play.api.libs.json.Json
//import models.json.DTOIds
//import models.json.DTONames
//import models.json.StatusWarningAdditionalStepInLevelCS
//import org.specs2.runner.JUnitRunner
//import org.junit.runner.RunWith
//import models.websocket.WebClient
//import play.api.libs.json.JsValue
//import models.json.StatusSuccessfulAdditionalStepInLevelCSCreated
//import models.tempConfig.TempConfigurations
//import play.api.Logger
//import models.persistence.OrientDB
//
///**
//	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
//	*
//	* Created by Gennadi Heimann 2.08.2017
//	*
//	* Username = user21_v015
//	*/
//@RunWith(classOf[JUnitRunner])
//class CollisionWith2StepsInOneLevelCSSpec 
//  extends Specification
//	with BeforeAfterAll
//	with GeneralFunctionToPrepareConfigs {
//  
//  val webClient = WebClient.init
//  
//  def beforeAll = {
//    Logger.info("CollisionWith2StepsInOneLevelCSSpec")
//    PrepareConfigsForSpecsv015.configurationForCollisionWith2Steps(webClient)
//    
////    val graph = OrientDB.getFactory().getTx
//    
////    delete edge hasDependency where in.nameToShow='component_1_3_user21_v015'
////    delete edge hasDependency where out.nameToShow='component_1_3_user21_v015'
////    delete vertex Step where nameToShow='step_3_user21_v015'
//  }
//	
//  def afterAll = {
////    Logger.info("AfterAll")
////    val tempStep = TempConfigurations.getAndRemoveAdditionalStepInLevelCS
////    Logger.info("TempStep " + tempStep)
//    try{
//      TempConfigurations.getAndRemoveAdditionalStepInLevelCS
//    }catch{
//      case e: Exception => {
//        Logger.info("temConfig")
//      }
//    }
//  }
//  
//  "Specification erstellt ConfigTree mit Kollision (2 Steps in einer Ebene)" >> {
//    "Erstelle 2. Step in einer Ebene" >> {
//      println("""
//                  S1 (min > 1 and max > 1)
//               /   \   \
//             /      \      \
//          /          \          \
//        C1            C2          C3
//         \           /            |
//           \       /              |
//             \   /                |
//              S2                 S3
//        
//        """)
//      
//      val configId = loginForConfigId("user21_v015", webClient)
//			val componentId = getComponentId("component_1_3_user21_v015")
//			val nameToShow = "step_3_user21_v015"
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
//			val stepSC = webClient.handleMessage(stepCS)
//			
//			
//			(stepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
//			(stepSC \ "dto").asOpt[String].get === DTONames.CREATE_STEP
//			(stepSC \ "result" \ "status").asOpt[String].get === StatusWarningAdditionalStepInLevelCS.status
//			(stepSC \ "result" \ "message").asOpt[String].get === StatusWarningAdditionalStepInLevelCS.message
//			(stepSC \ "result" \ "visualProposalForAdditionalStepInOneLevel")(0).asOpt[String].get === "graying"
//			(stepSC \ "result" \ "visualProposalForAdditionalStepInOneLevel")(1).asOpt[String].get === "remove"
//			(stepSC \ "result" \ "visualProposalForAdditionalStepInOneLevel")(2).asOpt[String].get === "unselectable"
//			(stepSC \ "result" \ "dependenciesForAdditionalStepsInOneLevel").asOpt[Set[JsValue]].get.size === 0
//			
//			val visualProposal = Json.obj(
//			    "dtoId" -> DTOIds.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL,
//				  "dto" -> DTONames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL,
//				  "params" -> Json.obj(
//				          "selectedVisualProposal" -> "remove"
//				  )
//			)
//			
//			val stepSCWithDependencies = webClient.handleMessage(visualProposal)
//			
//			
//			(stepSCWithDependencies \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
//			(stepSCWithDependencies \ "dto").asOpt[String].get === DTONames.CREATE_STEP
//			(stepSCWithDependencies \ "result" \ "status").asOpt[String].get === StatusSuccessfulAdditionalStepInLevelCSCreated.status
//			(stepSCWithDependencies \ "result" \ "message").asOpt[String].get === StatusSuccessfulAdditionalStepInLevelCSCreated.message
//			(stepSCWithDependencies \ "result" \ "visualProposalForAdditionalStepInOneLevel").asOpt[Set[JsValue]].get.size === 0
//			(stepSCWithDependencies \ "result" \ "dependenciesForAdditionalStepsInOneLevel").asOpt[Set[JsValue]].get.size === 4
//			(((stepSCWithDependencies \ "result" \ "dependenciesForAdditionalStepsInOneLevel")(0)) \ "dependencyType").asOpt[String].get === "default"
//			(((stepSCWithDependencies \ "result" \ "dependenciesForAdditionalStepsInOneLevel")(0)) \ "visualization").asOpt[String].get === "remove"
//			(((stepSCWithDependencies \ "result" \ "dependenciesForAdditionalStepsInOneLevel")(1)) \ "dependencyType").asOpt[String].get === "default"
//			(((stepSCWithDependencies \ "result" \ "dependenciesForAdditionalStepsInOneLevel")(1)) \ "visualization").asOpt[String].get === "remove"
//			(((stepSCWithDependencies \ "result" \ "dependenciesForAdditionalStepsInOneLevel")(2)) \ "dependencyType").asOpt[String].get === "default"
//			(((stepSCWithDependencies \ "result" \ "dependenciesForAdditionalStepsInOneLevel")(2)) \ "visualization").asOpt[String].get === "remove"
//			(((stepSCWithDependencies \ "result" \ "dependenciesForAdditionalStepsInOneLevel")(3)) \ "dependencyType").asOpt[String].get === "default"
//			(((stepSCWithDependencies \ "result" \ "dependenciesForAdditionalStepsInOneLevel")(3)) \ "visualization").asOpt[String].get === "remove"
//			
//    }
//  }
//}