//package models.v011
//
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import org.specs2.mutable.Specification
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import play.api.libs.json.Json
//import models.json.DTOIds
//import models.json.DTONames
//import play.api.libs.json.JsValue
//import models.persistence.GlobalConfigForDB
//import models.persistence.TestDB
//import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
//import play.api.libs.json.JsValue.jsValueToJsLookup
//import play.api.libs.json.Json.toJsFieldJsValueWrapper
//import models.json.StatusSuccessfulLogin
//import models.preparingConfigs.PrepareConfigsForSpecsv011
//import models.websocket.WebClient
//import play.api.Logger
//import models.json.StatusWarningConfigTreeEmpty
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 29.01.2017
// * 
// * Username = user9
// */
//
//@RunWith(classOf[JUnitRunner])
//class ConfigTreeEmptySpecs extends Specification with AdminWeb with BeforeAfterAll {
//
//  val wC = WebClient.init
//  
//  def afterAll(): Unit = {
//    PrepareConfigsForSpecsv011.prepareConfigTreeEmpty(wC)
//  }
//  
//  def beforeAll(): Unit = {
//  }
//      
//  "Diese Specification prueft die leere Konfiguration" >> {
//    val loginClientServer = Json.obj(
//        "dtoId" -> DTOIds.LOGIN,
//        "dto" -> DTONames.LOGIN
//        ,"params" -> Json.obj(
//            "username" -> "user9",
//            "password" -> "user9"
//        )
//    )
//    
//    val loginOut = wC.handleMessage(loginClientServer)
//    
//    Logger.info(loginOut.toString())
//    
//    val configId = ((loginOut \ "result" \ "configs")(0) \ "configId").asOpt[String].get
//    
//    
//    
//    (loginOut \ "result" \ "status").asOpt[String].get === StatusSuccessfulLogin.status
//    
//    val configTreeClientServer = Json.obj(
//        "dtoId" -> DTOIds.CONFIG_TREE,
//        "dto" -> DTONames.CONFIG_TREE
//        ,"params" -> Json.obj(
//            "configId" -> configId
//        )
//    )
//    val configTreeOut = wC.handleMessage(configTreeClientServer)
//    
//    Logger.info(configTreeOut.toString())
//    
//    (configTreeOut \ "dtoId").asOpt[Int].get === DTOIds.CONFIG_TREE
//    (configTreeOut \ "dto").asOpt[String].get === DTONames.CONFIG_TREE
//    (configTreeOut \ "result" \ "step").asOpt[String] === None
//    (configTreeOut \ "result" \ "status").asOpt[String].get === StatusWarningConfigTreeEmpty.status
//    (configTreeOut \ "result" \ "message").asOpt[String].get === StatusWarningConfigTreeEmpty.message
//  }
//}
