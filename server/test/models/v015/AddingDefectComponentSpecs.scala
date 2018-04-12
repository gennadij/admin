//package models.v015
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
//import models.json.StatusErrorFaultyStepId
//import models.json.StatusErrorComponentGeneral
//import play.api.Logger
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 13.10.2017
// * 
// * Username = user6_v0.1.5
// */
//
//@RunWith(classOf[JUnitRunner])
//class AddingDefectComponentSpecs extends Specification 
//    with BeforeAfterAll
//    with GeneralFunctionToPrepareConfigs{
//  
//  val wC = WebClient.init
//  
//  def beforeAll() = {
//    Logger.info("AddingDefectComponentSpecs")
//    PrepareConfigsForSpecsv011.prepareAddingDefectComponent(wC)
//    val firstStepId = getFirstStep("user6_v0.1.5")
//    val count = ComponentVertex.deleteComponents(firstStepId)
//    require(count == 0, "Anzahl der geloeschten Components " + count)
//  }
//  def afterAll() = {}
//  
//  
//  "Diese Specification spezifiziert das Hinzufügen von der Component zu dem Step (user6)" >> {
//    "Fuege 1 Components zu dem Firststep hinzu" >> {
//      val firstStepId = getFirstStep("user6_v0.1.5")
//        
//      val componentCS_1 = Json.obj(
//        "dtoId" -> DTOIds.CREATE_COMPONENT,
//        "dto" -> DTONames.CREATE_COMPONENT
//        ,"params" -> Json.obj(
//          "stepId" -> "#0:0",
//          "nameToShow" -> "Component",
//          "kind" -> "immutable"
//        )
//      )
//      
//      val componentSC_1: JsValue = wC.handleMessage(componentCS_1)
//      (componentSC_1 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
//      (componentSC_1 \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
//      (componentSC_1 \ "result" \ "status").asOpt[String].get === StatusErrorComponentGeneral.status
//      (componentSC_1 \ "result" \ "message").asOpt[String].get === StatusErrorComponentGeneral.message
//    }
//  }
//}