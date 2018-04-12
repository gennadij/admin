//package models.v011
//
//import org.specs2.mutable.Specification
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import play.api.libs.json.Json
//import models.json.DTOIds
//import models.json.DTONames
//import play.api.libs.json.JsValue
//import models.persistence.db.orientdb.ComponentVertex
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import models.persistence.GlobalConfigForDB
//import models.persistence.TestDB
//import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
//import play.api.libs.json.JsValue.jsValueToJsLookup
//import play.api.libs.json.Json.toJsFieldJsValueWrapper
//import models.json.StatusSuccessfulComponentCreated
//import models.preparingConfigs.PrepareConfigsForSpecsv011
//import models.websocket.WebClient
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 16.01.2017
// * 
// * Username = user6
// */
//
//@RunWith(classOf[JUnitRunner])
//class AddingNewComponentSpecs extends Specification 
//    with BeforeAfterAll
//    with GeneralFunctionToPrepareConfigs{
//  
//  val wC = WebClient.init
//  
//  def beforeAll() = {
//    PrepareConfigsForSpecsv011.prepareAddingNewComponent(wC)
//    val firstStepId = getFirstStep("user6")
//    val count = ComponentVertex.deleteComponents(firstStepId)
//    require(count == 4, "Anzahl der geloeschten Components " + count)
//  }
//  def afterAll() = {}
//  
//  
//  "Diese Specification spezifiziert das Hinzufügen von der Component zu dem Step (user6)" >> {
//    "Fuege 3 Components zu dem Firststep hinzu" >> {
//      val firstStepId = getFirstStep("user6")
//        
//      val componentCS_1 = Json.obj(
//        "dtoId" -> DTOIds.CREATE_COMPONENT,
//        "dto" -> DTONames.CREATE_COMPONENT
//        ,"params" -> Json.obj(
//          "stepId" -> firstStepId,
//          "nameToShow" -> "Component",
//          "kind" -> "immutable"
//        )
//      )
//      
//      val componentSC_1: JsValue = wC.handleMessage(componentCS_1)
//      (componentSC_1 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
//      (componentSC_1 \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
//      (componentSC_1 \ "result" \ "status").asOpt[String].get === StatusSuccessfulComponentCreated.status
//      (componentSC_1 \ "result" \ "message").asOpt[String].get === StatusSuccessfulComponentCreated.message
//      
//      val componentCS_2 = Json.obj(
//        "dtoId" -> DTOIds.CREATE_COMPONENT,
//        "dto" -> DTONames.CREATE_COMPONENT
//        ,"params" -> Json.obj(
//          "stepId" -> firstStepId,
//          "nameToShow" -> "Component",
//          "kind" -> "immutable"
//        )
//      )
//      val componentSC_2: JsValue = wC.handleMessage(componentCS_2)
//      (componentSC_2 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
//      (componentSC_2 \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
//      (componentSC_2 \ "result" \ "status").asOpt[String].get === StatusSuccessfulComponentCreated.status
//      (componentSC_2 \ "result" \ "message").asOpt[String].get === StatusSuccessfulComponentCreated.message
//      
//      val componentCS_3 = Json.obj(
//        "dtoId" -> DTOIds.CREATE_COMPONENT,
//        "dto" -> DTONames.CREATE_COMPONENT
//        ,"params" -> Json.obj(
//          "stepId" -> firstStepId,
//          "nameToShow" -> "Component",
//          "kind" -> "immutable"
//        )
//      )
//      val componentSC_3: JsValue = wC.handleMessage(componentCS_3)
//      (componentSC_3 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
//      (componentSC_3 \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
//      (componentSC_3 \ "result" \ "status").asOpt[String].get === StatusSuccessfulComponentCreated.status
//      (componentSC_3 \ "result" \ "message").asOpt[String].get === StatusSuccessfulComponentCreated.message
//      
//      val componentCS_4 = Json.obj(
//        "dtoId" -> DTOIds.CREATE_COMPONENT,
//        "dto" -> DTONames.CREATE_COMPONENT
//        ,"params" -> Json.obj(
//          "stepId" -> firstStepId,
//          "nameToShow" -> "Component",
//          "kind" -> "immutable"
//        )
//      )
//      val componentSC_4: JsValue = wC.handleMessage(componentCS_4)
//      (componentSC_4 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
//      (componentSC_4 \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
//      (componentSC_4 \ "result" \ "status").asOpt[String].get === StatusSuccessfulComponentCreated.status
//      (componentSC_4 \ "result" \ "message").asOpt[String].get === StatusSuccessfulComponentCreated.message
//    
//    }
//  }
//}