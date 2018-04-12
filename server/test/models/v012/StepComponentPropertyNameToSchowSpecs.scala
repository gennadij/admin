//package models.v012
//
//import models.json.DTOIds
//import models.json.DTONames
//import play.api.libs.json.JsValue
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import org.specs2.mutable.Specification
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import play.api.libs.json.Json
//import models.persistence.db.orientdb.ComponentVertex
//import models.persistence.db.orientdb.StepVertex
//import models.preparingConfigs.PrepareConfigsForSpecsv011
//import models.persistence.GlobalConfigForDB
//import play.api.libs.json.JsValue.jsValueToJsLookup
//import play.api.libs.json.Json.toJsFieldJsValueWrapper
//import models.json.StatusSuccessfulFirstStepCreated
//import models.json.StatusSuccessfulStepCreated
//import models.json.StatusSuccessfulComponentCreated
//import models.json.StatusSuccessfulLogin
//import models.json.StatusSuccessfulConnectionComponentToStep
//import play.api.Logger
//import models.websocket.WebClient
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 05.03.2017
// * 
// * Username = user11
// * 
// * Linux
// * adminId #21:29
// * configId #41:14
// */
//
//@RunWith(classOf[JUnitRunner])
//class StepComponentPropertyNameToSchowSpecs extends Specification 
//                          with BeforeAfterAll {
//  
//  var componentId = ""
//  val webClient = WebClient.init
//   
//  def afterAll(): Unit = {
//    val firstStepId = models.preparingConfigs.PrepareConfigsForSpecsv011.getFirstStep("user11")
//    val countStep = StepVertex.deleteStepFromComponent(componentId)
//    require(countStep == 1, "Anzahl der geloeschten Steps " + countStep)
//    val countComponent = ComponentVertex.deleteComponents(firstStepId)
//    require(countComponent == 3, "Anzahl der geloeschten Components " + countComponent)
//    val countFirstStep = StepVertex.removeStep(login)
//    require(countFirstStep == 1, "Anzahl der geloeschten Steps " + countFirstStep)
//
//  }
//  
//  def beforeAll() = {
//    PrepareConfigsForSpecsv011.prepareStepComponentPropertyNameToSchow(webClient)
//    
//  }
//  
//  "Specifikation spezifiziert neu Property <NameToShow>" >> {
//    "FirstStep -> 3 Components -> Step 2" >> {
//      val firstStepCS = Json.obj(
//        "dtoId" -> DTOIds.CREATE_FIRST_STEP,
//        "dto" -> DTONames.CREATE_FIRST_STEP
//        ,"params" -> Json.obj(
//          "configId" -> login,
//          "nameToShow" -> "S_1",
//          "kind" -> "first",
//          "selectionCriterium" -> Json.obj(
//              "min" -> 1,
//              "max" -> 1
//          )
//        )
//      )
//      
//      val firstStepSC = webClient.handleMessage(firstStepCS)
//      
//      val firstStepId = (firstStepSC \ "result" \ "stepId").asOpt[String].get
//      
//      val componentCS = Json.obj(
//          "dtoId" -> DTOIds.CREATE_COMPONENT,
//          "dto" -> DTONames.CREATE_COMPONENT
//          ,"params" -> Json.obj(
//              "stepId" -> firstStepId,
//              "nameToShow" -> "C_1_1",
//              "kind" -> "immutable"
//          )
//      )
//      val componentSC = webClient.handleMessage(componentCS)
//      
//      componentId = (componentSC \ "result" \ "componentId").asOpt[String].get
//      
//      val stepCS = Json.obj(
//          "dtoId" -> DTOIds.CREATE_STEP,
//          "dto" -> DTONames.CREATE_STEP,
//          "params" -> Json.obj(
//              "componentId" -> componentId,
//              "nameToShow" -> "S_2",
//              "kind" -> "default",
//              "selectionCriterium" -> Json.obj(
//                  "min" -> 1,
//                  "max" -> 1
//              )
//          )
//      )
//      val stepSC = webClient.handleMessage(stepCS)
//      
//      val connectionComponentToStepCS = Json.obj(
//          "dtoId" -> DTOIds.CONNECTION_COMPONENT_TO_STEP,
//          "dto" -> DTONames.CONNECTION_COMPONENT_TO_STEP,
//          "params" -> Json.obj(
//              "componentId" -> componentId,
//              "stepId" -> (stepSC \ "result" \ "stepId").asOpt[String].get
//          )
//      )
//      val connectionComponentToStepSC = webClient.handleMessage(connectionComponentToStepCS)
//      
//      val componentCS_1 = Json.obj(
//          "dtoId" -> DTOIds.CREATE_COMPONENT,
//          "dto" -> DTONames.CREATE_COMPONENT
//          ,"params" -> Json.obj(
//              "stepId" -> firstStepId,
//              "nameToShow" -> "C_1_2",
//              "kind" -> "immutable"
//          )
//      )
//      val componentSC_1: JsValue = webClient.handleMessage(componentCS_1)
//    
//      val componentCS_2 = Json.obj(
//          "dtoId" -> DTOIds.CREATE_COMPONENT,
//          "dto" -> DTONames.CREATE_COMPONENT
//          ,"params" -> Json.obj(
//              "stepId" -> firstStepId,
//              "nameToShow" -> "C_1_3",
//              "kind" -> "immutable"
//          )
//      )
//    
//      val componentSC_2: JsValue = webClient.handleMessage(componentCS_2)
//      
//      (firstStepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_FIRST_STEP
//      (firstStepSC \ "dto").asOpt[String].get === DTONames.CREATE_FIRST_STEP
//      (firstStepSC \ "result" \ "status").asOpt[String].get === StatusSuccessfulFirstStepCreated.status
//      (firstStepSC \ "result" \ "message").asOpt[String].get === StatusSuccessfulFirstStepCreated.message
//      (componentSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
//      (componentSC \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
//      (componentSC \ "result" \ "status").asOpt[String].get === StatusSuccessfulComponentCreated.status
//      (componentSC \ "result" \ "message").asOpt[String].get === StatusSuccessfulComponentCreated.message
//      componentId = (componentSC \ "result" \ "componentId").asOpt[String].get
//    
//      (stepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
//      (stepSC \ "dto").asOpt[String].get === DTONames.CREATE_STEP 
//      (stepSC \ "result" \ "status").asOpt[String].get === StatusSuccessfulStepCreated.status
//      (stepSC \ "result" \ "message").asOpt[String].get === StatusSuccessfulStepCreated.message
//    
//      (connectionComponentToStepSC \ "dtoId").asOpt[Int].get === DTOIds.CONNECTION_COMPONENT_TO_STEP
//      (connectionComponentToStepSC \ "dto").asOpt[String].get === DTONames.CONNECTION_COMPONENT_TO_STEP
//      (connectionComponentToStepSC \ "result" \ "status").asOpt[String].get === StatusSuccessfulConnectionComponentToStep.status
//      (connectionComponentToStepSC \ "result" \ "message").asOpt[String].get === StatusSuccessfulConnectionComponentToStep.message
//  
//      (componentSC_1 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
//      (componentSC_1 \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
//      (componentSC_1 \ "result" \ "status").asOpt[String].get === StatusSuccessfulComponentCreated.status
//      (componentSC_1 \ "result" \ "message").asOpt[String].get === StatusSuccessfulComponentCreated.message
//  
//      (componentSC_2 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
//      (componentSC_2 \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
//      (componentSC_2 \ "result" \ "status").asOpt[String].get === StatusSuccessfulComponentCreated.status
//      (componentSC_2 \ "result" \ "message").asOpt[String].get === StatusSuccessfulComponentCreated.message
//    }
//  }
//  def login: String = {
//    val userPassword = "user11"
//    val jsonClientServer = Json.obj(
//        "dtoId" -> DTOIds.LOGIN,
//        "dto" -> DTONames.LOGIN
//        ,"params" -> Json.obj(
//            "username" -> userPassword,
//            "password"-> userPassword
//         )
//    )
//    val jsonServerClient: JsValue = webClient.handleMessage(jsonClientServer)
//    require((jsonServerClient \ "result" \ "status").asOpt[String].get == StatusSuccessfulLogin.status)
//    ((jsonServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get
//  }
//}