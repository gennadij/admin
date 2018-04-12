//package models.v013
//
//import org.specs2.mutable.Specification
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import models.json.DTOIds
//import models.json.DTONames
//import play.api.libs.json.Json
//import play.api.libs.json.JsValue
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import models.persistence.GlobalConfigForDB
//import models.persistence.db.orientdb.StepVertex
//import models.preparingConfigs.PrepareConfigsForSpecsv013
//import models.json.StatusErrorFaultyConfigId
//import models.json.StatusSuccessfulLogin
//import models.websocket.WebClient
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 12.06.2017
// * 
// * Username = user18
// */
//
//@RunWith(classOf[JUnitRunner])
//class FirstStepConfigIdFaultySpecs  extends Specification
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
//    PrepareConfigsForSpecsv013.prepareFirstStepConfigIdFaulty(webClient)
//  }
//  
//  "Diese Specification spezifiziert das Hinzufügen von dem Step zu der Konfiguration" >> {
//    
//    "FirstStep hinzufuegen" >> {
//      val firstStepCS = Json.obj(
//        "dtoId" -> DTOIds.CREATE_FIRST_STEP,
//        "dto" -> DTONames.CREATE_FIRST_STEP
//        ,"params" -> Json.obj(
//          "configId" -> "#1:1",
//          "nameToShow" -> "FirstStep",
//          "kind" -> "first",
//          "selectionCriterium" -> Json.obj(
//              "min" -> 1,
//              "max" -> 1
//          )
//        )
//      )
//      val firstStepSC: JsValue = webClient.handleMessage(firstStepCS)
//      (firstStepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_FIRST_STEP
//      (firstStepSC \ "dto").asOpt[String].get === DTONames.CREATE_FIRST_STEP
//      (firstStepSC \ "result" \ "status").asOpt[String].get === StatusErrorFaultyConfigId.status
//      (firstStepSC \ "result" \ "message").asOpt[String].get === StatusErrorFaultyConfigId.message
//    }
//  }
//}