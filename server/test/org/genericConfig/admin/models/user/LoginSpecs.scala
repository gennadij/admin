package org.genericConfig.admin.models.user

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.ErrorDTO
import org.specs2.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.{JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 13.01.2017
 *
 * Username = user2
 */

//@RunWith(classOf[JUnitRunner])
class LoginSpecs extends Specification with BeforeAfterAll{

  val wC: WebClient = WebClient.init

  def afterAll(): Unit = {}
  def beforeAll(): Unit = {
    new PrepareUser().prepareLogin(wC)
  }

  def is =


  s2"""
       Specification fuer die Pruefung der Registrierung eines neuen Admins
          json                                                            $e2
          username                                                        $e4
          userId                                                          $e5
          commonStatus                                                    $e7
    """
  val user = "user2"

  val userParams = Json.obj(
      "action" -> Actions.GET_USER
      ,"params" -> Json.obj(
          "username" -> user,
          "password"-> user),
      "result" -> Json.obj(
        "userId" -> "",
        "username" -> "",
        "errors" -> Json.arr()
      ))


  val userResult: JsValue = wC.handleMessage(userParams)

  Logger.info("jsonClientServer: " + userParams)
  Logger.info("jsonServerClient: " + userResult)

  def e2 = (userResult \ "action").asOpt[String].get must_== Actions.GET_USER
  def e4 = (userResult \ "result" \ "username").asOpt[String].get must_== user
  def e5 = (userResult \ "result" \ "userId").asOpt[String].get.size must_== 32
  def e7 = (userResult \ "result" \ "errors").asOpt[List[ErrorDTO]] must_== None
}