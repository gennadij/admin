//package models.v011
//
//import org.specs2.Specification
//import play.api.libs.json.Json
//import play.api.libs.json.JsValue
//import models.admin.AdminWeb
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import models.json.DTONames
//import models.json.DTOIds
//import org.specs2.specification.BeforeAfterAll
//import models.persistence.GlobalConfigForDB
//import models.persistence.TestDB
//import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
//import play.api.libs.json.JsValue.jsValueToJsLookup
//import play.api.libs.json.Json.toJsFieldJsValueWrapper
//import models.json.StatusSuccessfulLogin
//import models.preparingConfigs.PrepareConfigsForSpecsv011
//import models.websocket.WebClient
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 13.01.2017
// * 
// * Username = user2
// */
//
//@RunWith(classOf[JUnitRunner])
//class SpecsLogin extends Specification with AdminWeb with BeforeAfterAll{
//  
//  val wC = WebClient.init
//  
//  def afterAll(): Unit = {}
//  def beforeAll(): Unit = {
//    PrepareConfigsForSpecsv011.prepareLogin(wC)
//  }
//  
//  def is = 
//  
//
//  s2"""
//       Specification fuer die Pruefung der Registrierung eines neuen Admins
//          dtoId                                                         $e1
//          dto                                                            $e2
//          username                                                       $e4
//          config.size                                                    $e7
//          config -> configUrl                                            $e9
//          status=true                                                    $e5
//          message="Anmeldung war nicht erfolgreich"                      $e6
//    """
//  val user = "user2"
//  
//  val jsonClientServer = Json.obj(
//      "dtoId" -> DTOIds.LOGIN,
//      "dto" -> DTONames.LOGIN
//      ,"params" -> Json.obj(
//          "username" -> user,
//          "password"-> user))
//
//
//  val jsonServerClient: JsValue = wC.handleMessage(jsonClientServer)
//  
//  def e1 = (jsonServerClient \ "dtoId").asOpt[Int].get must_== 2
//  def e2 = (jsonServerClient \ "dto").asOpt[String].get must_== "Login"
//  def e4 = (jsonServerClient \ "result" \ "username").asOpt[String].get must_== user
//  def e7 = (jsonServerClient \ "result" \ "configs").asOpt[List[JsValue]].get.size === 1
//  def e9 = ((jsonServerClient \ "result" \ "configs")(0) \ "configUrl").asOpt[String].get === "http://contig1/user2"
//  def e5 = (jsonServerClient \ "result" \ "status").asOpt[String].get must_== StatusSuccessfulLogin.status
//  def e6 = (jsonServerClient \ "result" \ "message").asOpt[String].get must_== StatusSuccessfulLogin.message
//}