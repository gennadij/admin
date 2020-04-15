package org.genericConfig.admin.models.user

import org.genericConfig.admin.controllers.converter.MessageHandler
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.wrapper.Wrapper
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.{JsValue, Json}
import play.api.libs.json.Json.toJsFieldJsValueWrapper

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.01.2017
 * 
 * Username = user1
 */

//@RunWith(classOf[JUnitRunner])
class AddNewUserSpecs extends Specification
                                with MessageHandler
                                with BeforeAfterAll
                                with CommonFunction
                                with Wrapper{

  var userResult : JsValue = null
  val wC = WebClient.init

  def beforeAll() = {


    val userParams = Json.obj(
      "action" -> Actions.ADD_USER
      ,"params" -> Json.obj(
        "username" -> "user1",
        "password"-> "user1",
        "update" -> Json.obj(
          "newUsername" -> "",
          "oldUsername" -> "",
          "newPassword" -> "",
          "oldPassword" -> ""
        )
      ), "result" -> Json.obj(
        "userId" -> "",
        "username" -> "",
        "errors" -> Json.arr()
      )
    )
    Logger.info("TEST")

    userResult = wC.handleMessage(userParams)
    Logger.info("" + userResult)
  }
  
  def afterAll() = {
    val count = deleteUser("user1")
    require(count == 1, "Anzahl der geloescten AdminUserVertexes " + count)
  }
  
  "Ein neuer Benutzer wird erstellt. Es soll kein Fehler geben." >> {
    "result.username = user1" >> {
      (userResult \ "result" \ "username").asOpt[String].get === "user1"
    }
    "result.userId <= 32" >> {
      (userResult \ "result" \ "userId").asOpt[String].get.size must be_<=(32)
    }
    "result.errors = None" >> {
      (userResult \ "result" \ "errors").asOpt[List[ErrorDTO]] must_== None
    }
  }
}