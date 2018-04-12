//package models.v015
//
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import org.specs2.mutable.Specification
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import play.api.libs.json.Json
//import models.json.DTONames
//import models.json.DTOIds
//import play.api.libs.json.JsValue
//import models.preparingConfigs.PrepareConfigsForSpecsv015
//import models.websocket.WebClient
//import play.api.Logger
//import scala.util.matching.Regex
//import org.specs2.matcher.Matcher
//import org.specs2.matcher.ResultMatchers
//
///**
//	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
//	*
//	* Created by Gennadi Heimann 29.07.2017
//	*
//	* Username = user20_v015
//	*/
//
//@RunWith(classOf[JUnitRunner])
//class ConfigurationForWebClientSpec 
//  extends Specification
//	with BeforeAfterAll
//	with GeneralFunctionToPrepareConfigs
//	with ResultMatchers {
//  
//  val webClient = WebClient.init
//  
//  def beforeAll = {
//    Logger.info("ConfigurationForWebClientSpec")
//    PrepareConfigsForSpecsv015.configurationForWebClient(webClient)
//  }
//	
//  def afterAll = {}
//	
//	"Specification erstellt Konfiguration fuer WebClient" >> {
//	  "Pruefe die Erstellung der Konfiguration" >> {
//	    
//	    val configId = loginForConfigId("user20_v015", webClient)
//	    
//	    val configTreeIn = Json.obj(
//        "dtoId" -> DTOIds.CONFIG_TREE,
//        "dto" -> DTONames.CONFIG_TREE
//        ,"params" -> Json.obj(
//            "configId" -> configId
//        )
//    )
//	    
//	    
//	  val configTreeOut = webClient.handleMessage(configTreeIn)
//    (configTreeOut \ "dtoId").asOpt[Int].get === DTOIds.CONFIG_TREE
//    (configTreeOut \ "dto").asOpt[String].get === DTONames.CONFIG_TREE
//    (configTreeOut \ "result" \ "step" \ "kind").asOpt[String].get === "first"
//    (configTreeOut \ "result" \ "step" \ "components").asOpt[Set[JsValue]].get.size === 3
//    ((configTreeOut \ "result" \ "step" \ "components")(0) \ "kind").asOpt[String].get === "immutable"
//    ((configTreeOut \ "result" \ "step" \ "components")(0) \ "nextStepId").asOpt[String].get must beMatching("""#\d{1,3}:\d{1,3}""")
//    ((configTreeOut \ "result" \ "step" \ "components")(0) \ "nextStep").asOpt[String] === None
//    ((configTreeOut \ "result" \ "step" \ "components")(1) \ "kind").asOpt[String].get === "immutable"
//    ((configTreeOut \ "result" \ "step" \ "components")(1) \ "nextStepId").asOpt[String].get must beMatching("""#\d{1,3}:\d{1,3}""")
//    ((configTreeOut \ "result" \ "step" \ "components")(1) \ "nextStep").asOpt[String] === None
//    ((configTreeOut \ "result" \ "step" \ "components")(2) \ "kind").asOpt[String].get === "immutable"
//    ((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStepId").asOpt[String].get must beMatching("""#\d{1,3}:\d{1,3}""")
//    ((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStep" \ "kind").asOpt[String].get === "immutable"
//    (((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStep" \ "components")(0) \ "kind").asOpt[String].get === "immutable"
//    (((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStep" \ "components")(0) \ "nextStepId").asOpt[String].get === "last"
//    (((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStep" \ "components")(0) \ "nextStep").asOpt[String] === None
//    (((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStep" \ "components")(1) \ "kind").asOpt[String].get === "immutable"
//    (((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStep" \ "components")(1) \ "nextStepId").asOpt[String].get === "last"
//    (((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStep" \ "components")(1) \ "nextStep").asOpt[String] === None
//    (((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStep" \ "components")(2) \ "kind").asOpt[String].get === "immutable"
//    (((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStep" \ "components")(2) \ "nextStepId").asOpt[String].get === "last"
//    (((configTreeOut \ "result" \ "step" \ "components")(2) \ "nextStep" \ "components")(2) \ "nextStep").asOpt[String] === None
//	  }
//	}
//}