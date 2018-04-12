//package models.v012
//
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import org.specs2.mutable.Specification
//import models.json.DTOIds
//import models.json.DTONames
//import play.api.libs.json.Json
//import play.api.libs.json.JsValue
//import models.preparingConfigs.PrepareConfigsForSpecsv012
//import models.json.StatusErrorStepExist
//import models.json.StatusErrorFirstStepExist
//import models.json.StatusSuccessfulLogin
//import play.api.Logger
//import models.persistence.OrientDB
//import models.websocket.WebClient
//
///**
//	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
//	*
//	* Created by Gennadi Heimann 05.05.2017
//	*
//	* Username = user15
//	*/
//@RunWith(classOf[JUnitRunner])
//class TwoFirstStepsForOneConfigSpecs extends Specification
//	with AdminWeb
//	with BeforeAfterAll {
//  
//  var configId = ""
//  val webClient = WebClient.init
//  
//  def beforeAll = {
//    PrepareConfigsForSpecsv012.prepareTwoFirstStepsForOneConfig(webClient)
//    configId = login()
//  }
//	def afterAll = {}
//	
//	"Hier wird die Erzeugung von 2 FirstSteps innerhalb einer Config spezifiziert" >> {
//    "2 FirstStep sind nicht erlaubt" >> {
//      
//      val firstStepCS = Json.obj(
//        "dtoId" -> DTOIds.CREATE_FIRST_STEP,
//        "dto" -> DTONames.CREATE_FIRST_STEP
//        ,"params" -> Json.obj(
//          "configId" -> configId,
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
//      (firstStepSC \ "result" \ "status").asOpt[String].get === StatusErrorFirstStepExist.status
//      (firstStepSC \ "result" \ "message").asOpt[String].get === StatusErrorFirstStepExist.message
//    }
//  }
//	def login(): String = {
//    val user = "user15"
//      val jsonClientServer = Json.obj(
//          "dtoId" -> DTOIds.LOGIN,
//          "dto" -> DTONames.LOGIN
//          ,"params" -> Json.obj(
//              "username" -> user,
//              "password"-> user
//           )
//      )
//      val jsonServerClient: JsValue = webClient.handleMessage(jsonClientServer)
//      require((jsonServerClient \ "result" \ "status").asOpt[String].get == StatusSuccessfulLogin.status)
//      ((jsonServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get
//  }
//}