//package org.genericConfig.admin.models.dependency
//
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import org.genericConfig.admin.controllers.websocket.WebClient
//import org.genericConfig.admin.models.persistence.db.orientdb.HasDependencyEdge
//import org.genericConfig.admin.shared.common.json.JsonNames
//import org.genericConfig.admin.shared.config.status.StatusAddConfig
//import org.genericConfig.admin.shared.user.status.{AddUserAlreadyExist, AddUserError, AddUserSuccess}
//import org.junit.runner.RunWith
//import org.specs2.mutable.Specification
//import org.specs2.runner.JUnitRunner
//import org.specs2.specification.BeforeAfterAll
//import play.api.Logger
//import play.api.libs.json.Json
//import util.CommonFunction
//
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// *
// * Created by Gennadi Heimann 18.10.2017
// */
//@RunWith(classOf[JUnitRunner])
//class AddingNewDependencyBetweenTwoComponent
//  extends Specification
//	with BeforeAfterAll
//	with CommonFunction {
//
//  val wC: WebClient = WebClient.init
//  val usernamePassword = "user28_v015"
//
//  def beforeAll: Unit = {
//    val (username: String, userId: String, status: String) = addUser(this.usernamePassword)
//
//    status match {
//      case s if AddUserSuccess().status == s =>
//
//        val (configId: String, _: StatusAddConfig) = addConfig(userId, s"http://contig/$username")
//
//        val stepId_1 = addStep(Some(configId), nameToShow = Some("First Step"), kind = Some("first")).get
//
//        val componentId_1_1 = addComponentToStep(stepId_1, "Component11", "immutable1")._1
//        val componentId_1_2 = addComponentToStep(stepId_1, "Component12", "immutable2")._1
//
//        val stepId_2 = addStep(Some(componentId_1_1), nameToShow = Some("Step 2"), kind = Some("default")).get
//
//        connectComponentToStep(stepId = stepId_2, componentId_1_2)
//
//        val componentId_2_1 = addComponentToStep(stepId_2, nameToShow = "Component21", kind = "immutable")._1
//        val componentId_2_2 = addComponentToStep(stepId_2, nameToShow = "Component22", kind = "immutable")._1
//
//
//      case s if AddUserAlreadyExist().status == s =>
//        val configId =
//          getConfigId(usernamePassword = this.usernamePassword, configUrl = s"http://contig/${this.usernamePassword}")
//
//        val configTreeBO = getConfigTree(configId)
//
//        val componentId_11 = configTreeBO.configTree.get.components.head.componentId
//        val componentId_21 = configTreeBO.configTree.get.nextSteps.head.components.head.componentId
//
//        Logger.info("componentId_11 " + componentId_11)
//        Logger.info("componentId_21 " + componentId_21)
//
//      case s if AddUserError().status == s =>
//        Logger.info("Fehler bei der Vorbereitung")
//    }
//
//  }
//
//  def afterAll: Unit = {
//    //TODO Delete Dependency
//
//  }
//
//  /*
//                 S1
//                /  \
//               /    \
//              /      \
//             C1      C2
//              \      /
//               \    /
//                \  /
//                 S2
//               / |  \
//              /  |   \
//             /   |    \
//            C3   C4   C5
//            Dependency C2 -> C3
//   */
//  "Specs fuer die Erzeugung einer Abhaengigkeit zwischen zwei Komponenten" >> {
//    "Erstelle Abhaengigkeit zwischen C2 -> C3" >> {
//
//    val dependencyIn = Json.obj(
//				"json" -> JsonNames.CREATE_DEPENDENCY,
//				"params" -> Json.obj(
//					  "componentFromId" -> "",
//            "componentToId" -> "",
//            "dependencyType" -> "test",
//            "visualization" -> "remove",
//            "nameToShow" -> ""
//				)
//			)
//
////			val dependencyOut = wC.handleMessage(dependencyIn)
//
////			Logger.info(dependencyIn.toString())
////			Logger.info(dependencyOut.toString())
//
////      (dependencyOut \ "dtoId").asOpt[Int].get === DTOIds.CREATE_DEPENDENCY
////			(dependencyOut \ "dto").asOpt[String].get === DTONames.CREATE_DEPENDENCY
////			(dependencyOut \ "result" \ "status").asOpt[String].get === StatusSuccessfulDependencyCreated.status
////			(dependencyOut \ "result" \ "message").asOpt[String].get === StatusSuccessfulDependencyCreated.message
////			(dependencyOut \ "result" \ "dependencyType").asOpt[String].get === "test"
////			(dependencyOut \ "result" \ "visualization").asOpt[String].get === "remove"
////			(dependencyOut \ "result" \ "nameToShow").asOpt[String].get === s"dependency_C2_C3_$username"
//      "" === ""
//    }
//  }
//}