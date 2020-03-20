package org.genericConfig.admin.models.login

import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.common.GetUserNotExist
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
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
 */

class LoginWithNotExistUserSpecs extends Specification with AdminWeb  with BeforeAfterAll {

  def afterAll(): Unit = {}
  def beforeAll(): Unit = {}

  def is =

  s2"""
       Specification fuer die Pruefung der Registrierung eines neuen nicht exestierenden Users
          json                                                            $e2
          username                                                        $e4
          userId                                                          $e5
          errors count                                                    $e7
          error name                                                      $e8
    """
  val user = "user"
  val webClient = WebClient.init

  val userParams = Json.obj(
    "action" -> Actions.GET_USER
    ,"params" -> Json.obj(
      "username" -> user,
      "password"-> user,
      "update" -> Json.obj(
        "newUsername" -> "",
        "oldUsername" -> "",
        "newPassword" -> "",
        "oldPassword" -> ""
      ),
    ),
    "result" -> Json.obj(
      "userId" -> "",
      "username" -> "",
      "errors" -> Json.arr()
    ))


  val userResult: JsValue = webClient.handleMessage(userParams)

  Logger.info("jsonClientServer: " + userParams)
  Logger.info("jsonServerClient: " + userResult)

  def e2 = (userResult \ "action").asOpt[String].get must_== Actions.GET_USER
  def e4 = (userResult \ "result" \ "username").asOpt[String] must_== None
  def e5 = (userResult \ "result" \ "userId").asOpt[String] must_== None
  def e7 = (userResult \ "result" \ "errors").asOpt[List[ErrorDTO]].get.size must_== 1
  def e8 = ((userResult \ "result" \ "errors")(0) \ "name" ).asOpt[String].get must_== GetUserNotExist().name
}