package models.v011

import org.specs2.Specification
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import org.genericConfig.admin.controllers.websocket.WebClient
import models.preparingConfigs.PrepareConfigsForSpecsv011
import org.genericConfig.admin.shared.status.login.StatusLogin
import org.genericConfig.admin.shared.status.login.StatusUserLogin
import org.genericConfig.admin.shared.status.login.UserExist
import org.genericConfig.admin.shared.status.Success
import play.api.Logger
import org.genericConfig.admin.shared.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.01.2017
 * 
 * Username = user2
 */

@RunWith(classOf[JUnitRunner])
class LoginSpecs extends Specification with BeforeAfterAll{
  
  val wC = WebClient.init
  
  def afterAll(): Unit = {}
  def beforeAll(): Unit = {
    PrepareConfigsForSpecsv011.prepareLogin(wC)
  }
  
  def is = 
  

  s2"""
       Specification fuer die Pruefung der Registrierung eines neuen Admins
          json                                                            $e2
          username                                                       $e4
          config.size                                                    $e7
          config -> configUrl                                            $e9
          userLogin                                                    $e5
          common                      $e6
    """
  val user = "user2"
  
  val jsonClientServer = Json.obj(
      "json" -> JsonNames.LOGIN
      ,"params" -> Json.obj(
          "username" -> user,
          "password"-> user))


  val jsonServerClient: JsValue = wC.handleMessage(jsonClientServer)
  
//  Logger.info("jsonClientServer: " + jsonClientServer)
//  Logger.info("jsonServerClient: " + jsonServerClient)
  
  def e2 = (jsonServerClient \ "json").asOpt[String].get must_== "Login"
  def e4 = (jsonServerClient \ "result" \ "username").asOpt[String].get must_== user
  def e7 = (jsonServerClient \ "result" \ "configs").asOpt[List[JsValue]].get.size === 1
  def e9 = ((jsonServerClient \ "result" \ "configs")(0) \ "configUrl").asOpt[String].get === "http://contig1/user2"
  def e5 = (jsonServerClient \ "result" \ "status" \ "userLogin" \ "status").asOpt[String].get must_== UserExist().status
  def e6 = (jsonServerClient \ "result" \ "status" \ "common" \ "status").asOpt[String].get must_== Success().status
}