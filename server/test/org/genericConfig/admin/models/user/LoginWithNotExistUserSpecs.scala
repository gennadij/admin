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
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Error
import play.api.Logger
import org.genericConfig.admin.shared.user.status.GetUserNotExist

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.01.2017
 */

@RunWith(classOf[JUnitRunner])
class LoginWithNotExistUserSpecs extends Specification with AdminWeb  with BeforeAfterAll {
  
  def afterAll(): Unit = {}
  def beforeAll(): Unit = {}
  
  def is = 
  
  s2"""
       Specification fuer die Pruefung der Registrierung eines neuen nicht exestierenden Users
          json                                                            $e2
          username                                                       $e4
          userId                                                    $e5
          status                      $e6
          status                                             $e7
    """
  val user = "user"
  val webClient = WebClient.init
  val jsonClientServer = Json.obj(
      "json" -> JsonNames.GET_USER,
      "params" -> Json.obj(
          "username" -> user,
          "password"-> user))


  val jsonServerClient: JsValue = webClient.handleMessage(jsonClientServer)
  
  Logger.info("jsonClientServer: " + jsonClientServer)
  Logger.info("jsonServerClient: " + jsonServerClient)
  
  def e2 = (jsonServerClient \ "json").asOpt[String].get must_== JsonNames.GET_USER
  def e4 = (jsonServerClient \ "result" \ "username").asOpt[String].get must_== user
  def e5 = (jsonServerClient \ "result" \ "result" \ "userId").asOpt[String] must_== None
  def e6 = (jsonServerClient \ "result" \ "status" \ "getUser" \ "status").asOpt[String].get must_== GetUserNotExist().status
  def e7 = (jsonServerClient \ "result" \ "status"\ "common" \ "status").asOpt[String].get must_== Error().status
}