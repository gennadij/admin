//package models.v011
//
//import org.specs2.runner.JUnitRunner
//import org.specs2.mutable.Specification
//import models.admin.AdminWeb
//import org.specs2.specification.BeforeAfterAll
//import org.junit.runner.RunWith
//import models.json.DTOIds
//import models.json.DTONames
//import play.api.libs.json.Json
//import models.persistence.db.orientdb.ConfigVertex
//import models.persistence.GlobalConfigForDB
//import models.persistence.TestDB
//import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
//import play.api.libs.json.JsValue.jsValueToJsLookup
//import play.api.libs.json.Json.toJsFieldJsValueWrapper
//import models.json.StatusSuccessfulLogin
//import models.json.StatusSuccessfulConfig
//import models.preparingConfigs.PrepareConfigsForSpecsv011
//import models.websocket.WebClient
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 16.01.2017
// * 
// * Username = user3
// */
//
//@RunWith(classOf[JUnitRunner])
//class AddingNewConfigSpecs extends Specification 
//                           with BeforeAfterAll{
//  
//  val userPassword = "user3"
//  val wC = WebClient.init
//  def beforeAll() = {
//    PrepareConfigsForSpecsv011.prepareAddingNewConfig(wC)
//    val count: Int = ConfigVertex.deleteConfigVertex(userPassword)
//    require(count == 1, "Anzahl der geloeschten ConfigVertexes " + count)
//  }
//  
//  def afterAll() = {}
//  
//  "Diese Spezifikation erzeugt neue Konfiguration für die Admin" >> {
//    "Login mit AdminUser und fuege Konfig zu dem AdminUser hinzu" >> {
//      
//      val loginCS = Json.obj(
//        "dtoId" -> DTOIds.LOGIN,
//        "dto" -> DTONames.LOGIN
//        ,"params" -> Json.obj(
//            "username" -> userPassword,
//            "password" -> userPassword
//        )
//    )
//   
//    val loginSC = wC.handleMessage(loginCS)
//    (loginSC \ "result" \ "status").asOpt[String].get === StatusSuccessfulLogin.status
//    
//    val createConfigCS = Json.obj(
//        "jsonId" -> DTOIds.CREATE_CONFIG,
//        "dto" -> DTONames.CREATE_CONFIG
//        , "params" -> Json.obj(
//            "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
//            "configUrl" -> "//http://contig1/user3"
//        )
//    )
//    val createConfigSC = wC.handleMessage(createConfigCS)
//	  (createConfigSC \ "result" \ "status").asOpt[String].get === StatusSuccessfulConfig.status
//	  (createConfigSC \ "result" \ "message").asOpt[String].get === StatusSuccessfulConfig.message
//    }
//  }
//}