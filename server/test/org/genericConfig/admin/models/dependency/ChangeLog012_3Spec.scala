//package models.v012
//
//import org.specs2.mutable.Specification
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import models.json.DTOIds
//import models.json.DTONames
//import play.api.libs.json.Json
//import play.api.libs.json.JsValue
//import models.persistence.GlobalConfigForDB
//import models.preparingConfigs.PrepareConfigsForSpecsv012
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import models.websocket.WebClient
//import models.json.StatusSuccessfulDependencyCreated
//
///**
//	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
//	*
//	* Created by Gennadi Heimann 05.05.2017
//	*
//	* Username = user16
//	*/
//
//@RunWith(classOf[JUnitRunner])
//class ChangeLog012_3Spec extends Specification
//	with BeforeAfterAll 
//	with GeneralFunctionToPrepareConfigs{
//  
//  val webClient = WebClient.init
//  
//  def beforeAll = {
//    PrepareConfigsForSpecsv012.prepareChangeLog012_3(webClient)
//  }
//	def afterAll = {
//		PrepareConfigsForSpecsv012.deleteHasDependency("Component_1", "Component_2")
//	}
//  
//	"Hier wird die Erzeugung von Dependency zwischen zwei Components innerhalb eines Stepes spezifiziert" >> {
//	  "Dependency Component 1 -> Component 3" >> {
//	    
//	    
//	    val firstStep = getFirstStep("user16")
//	    val components: List[String] = getComponentsFromFirstStep(firstStep)
//	    val dependencyCS = Json.obj(
//        "dtoId" -> DTOIds.CREATE_DEPENDENCY,
//        "dto" -> DTONames.CREATE_DEPENDENCY
//        ,"params" -> Json.obj(
//          "componentFromId" -> components(0),
//          "componentToId" -> components(1),
//          "dependencyType" -> "exclude",
//          "visualization" -> "",
//          "nameToShow" -> ""
//        )
//      )
//	    
//      val depedndencySC = webClient.handleMessage(dependencyCS)
//      
//	    (depedndencySC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_DEPENDENCY
//      (depedndencySC \ "dto").asOpt[String].get === DTONames.CREATE_DEPENDENCY
//      (depedndencySC \ "result" \ "status").asOpt[String].get === StatusSuccessfulDependencyCreated.status
//      (depedndencySC \ "result" \ "message").asOpt[String].get === StatusSuccessfulDependencyCreated.message
//	  }
//	}
//}