package org.genericConfig.admin.models.component

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentParams}
import org.genericConfig.admin.shared.config.status.StatusAddConfig
import org.genericConfig.admin.shared.user.status.{AddUserAlreadyExist, AddUserError, AddUserSuccess}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.{JsValue, Json}
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 16.01.2017
 *
 * Username = user5
 */

@RunWith(classOf[JUnitRunner])
class AddingComponentToFirstStepSpecs extends Specification
                                        with BeforeAfterAll
                                        with CommonFunction{

  val usernamePasword = "user5"
  var userId = ""
  var stepId = ""
  val wC: WebClient = WebClient.init

  def beforeAll() : Unit = {
    val (username: String, userId: String, status: String) = addUser(this.usernamePasword)

    status match {
      case s if AddUserSuccess().status == s =>

        val (configId: String, status: StatusAddConfig) = addConfig(userId, s"http://contig/$username")
        this.stepId = addStep(Some(configId), None).get
      case s if AddUserAlreadyExist().status == s =>
        val configId = getConfigId(usernamePassword = usernamePassword, configUrl = s"http://contig/$username")
        this.stepId = addStep(Some(configId), None).get
      case s if AddUserError().status == s =>
        Logger.info("Fehler bei der Vorbereitung")
    }
  }

  def afterAll(): Unit = {
  }

  "Diese Specification spezifiziert das Hinzufügen von der Component zu dem FirstStep user5" >> {
    "FirstStep -> Component hinzufuegen" >> {

      val jsonAddComponentIn = Json.toJson(JsonComponentIn(
        json = JsonNames.ADD_COMPONENT,
        params = JsonComponentParams(
          stepId = stepId,
          nameToShow = "Component 1",
          kind = "immutable"
        )
      ))

      val jsonAddComponentOut: JsValue = wC.handleMessage(jsonAddComponentIn)

      (jsonAddComponentOut \ "dto").asOpt[String] === Some(JsonNames.ADD_COMPONENT)
      (jsonAddComponentOut \ "result" \ "status").asOpt[String] === None
    }
  }
}