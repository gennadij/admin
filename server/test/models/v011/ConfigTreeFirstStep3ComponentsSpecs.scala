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
//import models.persistence.GlobalConfigForDB
//import models.persistence.TestDB
//import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
//import play.api.libs.json.JsValue.jsValueToJsLookup
//import play.api.libs.json.Json.toJsFieldJsValueWrapper
//import models.json.StatusSuccessfulLogin
//import models.preparingConfigs.PrepareConfigsForSpecsv011
//import models.websocket.WebClient
//import play.api.Logger
//import models.json.StatusSuccessfulConfigTree
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 16.01.2017
// * 
// * Username = user7
// */
//
//@RunWith(classOf[JUnitRunner])
//class ConfigTreeFirstStep3ComponentsSpecs extends Specification with BeforeAfterAll{
//  
//  val wC = WebClient.init
//  
//  def beforeAll() = {
//    PrepareConfigsForSpecsv011.prepareConfigTreeFirstStep3Components(wC)
//  }
//  
//  def afterAll() = {}
//  "Specification spezifiziert die Erzeugung von der ConfigTree" >> {
//    val loginCS = Json.obj(
//        "dtoId" -> DTOIds.LOGIN,
//        "dto" -> DTONames.LOGIN
//        ,"params" -> Json.obj(
//            "username" -> "user7",
//            "password"-> "user7"
//        )
//    )
//    
//    val loginSC: JsValue = wC.handleMessage(loginCS)
//    val configId = ((loginSC \ "result" \ "configs")(0) \ "configId").asOpt[String].get
//    (loginSC \ "result" \ "status").asOpt[String].get === StatusSuccessfulLogin.status
//    (loginSC \ "result" \ "configs").asOpt[List[JsValue]].get.size === 1
//      
//    val configTreeIn = Json.obj(
//        "dtoId" -> DTOIds.CONFIG_TREE,
//        "dto" -> DTONames.CONFIG_TREE
//        ,"params" -> Json.obj(
//            "configId" -> configId
//        )
//    )
//    val configTreeOut = wC.handleMessage(configTreeIn)
//    
//    Logger.info(configTreeIn.toString())
//    Logger.info(configTreeOut.toString())
//    
//    (configTreeOut \ "dtoId").asOpt[Int].get === DTOIds.CONFIG_TREE
//    (configTreeOut \ "dto").asOpt[String].get === DTONames.CONFIG_TREE
//    ((configTreeOut \ "result" \ "step") \ "kind").asOpt[String].get === "first"
//    (((configTreeOut \ "result" \ "step") \ "components")).asOpt[Set[JsValue]].get.size === 3
//    ((((configTreeOut \ "result" \ "step") \ "components")(0)) \ "kind").asOpt[String].get === "immutable"
//    ((((configTreeOut \ "result" \ "step") \ "components")(0)) \ "nextStep" \ "kind").asOpt[String].get === "default"
//    ((((configTreeOut \ "result" \ "step") \ "components")(0)) \ "nextStep" \ "components").asOpt[Set[JsValue]].get.size === 0
//    ((((configTreeOut \ "result" \ "step") \ "components")(1)) \ "kind").asOpt[String].get === "immutable"
//    ((((configTreeOut \ "result" \ "step") \ "components")(1)) \ "nextStepId").asOpt[String].get === "last"
//    ((((configTreeOut \ "result" \ "step") \ "components")(1)) \ "nextStep").asOpt[String] === None
//    ((((configTreeOut \ "result" \ "step") \ "components")(2)) \ "kind").asOpt[String].get === "immutable"
//    ((((configTreeOut \ "result" \ "step") \ "components")(2)) \ "nextStepId").asOpt[String].get === "last"
//    ((((configTreeOut \ "result" \ "step") \ "components")(2)) \ "nextStep").asOpt[String] === None
//    (configTreeOut \ "result" \ "status").asOpt[String].get === StatusSuccessfulConfigTree.status
//    (configTreeOut \ "result" \ "message").asOpt[String].get === StatusSuccessfulConfigTree.message
//  }
//}