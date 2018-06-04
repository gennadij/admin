package org.genericConfig.admin.models.user

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
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.user.status.GetUserSuccess
import play.api.Logger

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
    new PrepareUser().prepareLogin(wC)
  }
  
  def is = 
  

  s2"""
       Specification fuer die Pruefung der Registrierung eines neuen Admins
          json                                                            $e2
          username                                                       $e4
          userId                                                    $e5
          getUserStatus                      $e8
          commonStatus            $e7
    """
  val user = "user2"
  
  val jsonClientServer = Json.obj(
      "json" -> JsonNames.GET_USER
      ,"params" -> Json.obj(
          "username" -> user,
          "password"-> user))


  val jsonServerClient: JsValue = wC.handleMessage(jsonClientServer)
  
  Logger.info("jsonClientServer: " + jsonClientServer)
  Logger.info("jsonServerClient: " + jsonServerClient)
  
  def e2 = (jsonServerClient \ "json").asOpt[String].get must_== JsonNames.GET_USER
  def e4 = (jsonServerClient \ "result" \ "username").asOpt[String].get must_== user
  def e5 = (jsonServerClient \ "result" \ "userId").asOpt[String].get.size must_== 32
  def e8 = (jsonServerClient \ "result" \ "status" \ "getUser" \ "status").asOpt[String].get must_== GetUserSuccess().status
  def e7 = (jsonServerClient \ "result" \ "status" \ "common" \ "status").asOpt[String].get must_== Success().status
}