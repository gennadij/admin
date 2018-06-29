package org.genericConfig.admin.models.component

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentParams}
import org.genericConfig.admin.shared.component.status.{AddComponentSuccess, AppendComponentSuccess}
import org.genericConfig.admin.shared.user.status.{AddUserAlreadyExist, AddUserError, AddUserSuccess}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.{JsValue, Json}
import util.CommonFunction

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 16.01.2017
  *
  * Username = user6
  */

@RunWith(classOf[JUnitRunner])
class AddingNewComponentSpecs extends Specification
  with BeforeAfterAll
  with CommonFunction {

  val wC: WebClient = WebClient.init

  val usernamePassword = "user6"
  var userId = ""
  var stepId = ""

  def beforeAll(): Unit = {
    val (username: String, userId: String, status: String) = addUser(this.usernamePassword)

    status match {
      case s if AddUserSuccess().status == s =>

        val configId: String = addConfig(userId, s"http://contig/$username")._1
        this.stepId = addStep(Some(configId), nameToShow = Some("Step 1"), kind = Some("first")).get
      case s if AddUserAlreadyExist().status == s =>
        val configId = getConfigId(usernamePassword = this.usernamePassword, configUrl = s"http://contig/$username")
        val configTreeBO = getConfigTree(configId)


        this.stepId = configTreeBO.configTree.get.stepId
      case s if AddUserError().status == s =>
        Logger.info("Fehler bei der Vorbereitung")
    }
  }

  def afterAll(): Unit = {
    val count = deleteComponents(this.stepId)
    require(count == 3, "deleted components " + count)

  }


  "Diese Specification spezifiziert das Hinzufügen von der Component zu dem Step (user6)" >> {
    "Fuege 3 Components zu dem Firststep hinzu" >> {

      val jsonAddComponentIn_1 = Json.toJson(JsonComponentIn(
        json = JsonNames.ADD_COMPONENT,
        params = JsonComponentParams(
          stepId = Some(stepId),
          nameToShow = Some("Component 1"),
          kind = Some("immutable")
        )
      ))
      Logger.info("IN " + jsonAddComponentIn_1)
      val jsonAddComponentOut_1: JsValue = wC.handleMessage(jsonAddComponentIn_1)
      Logger.info("OUT " + jsonAddComponentOut_1)

      (jsonAddComponentOut_1 \ "json").asOpt[String] === Some(JsonNames.ADD_COMPONENT)
      (jsonAddComponentOut_1 \ "result" \ "nameToShow").asOpt[String] === Some("Component 1")
      (jsonAddComponentOut_1 \ "result" \ "kind").asOpt[String] === Some("immutable")
      (jsonAddComponentOut_1 \ "result" \ "status" \ "addComponent" \ "status").asOpt[String] ===
        Some(AddComponentSuccess().status)
      (jsonAddComponentOut_1 \ "result" \ "status" \ "appendComponent" \ "status").asOpt[String] ===
        Some(AppendComponentSuccess().status)
      (jsonAddComponentOut_1 \ "result" \ "status" \ "common" \ "status").asOpt[String] === Some(Success().status)

      val jsonAddComponentIn_2 = Json.toJson(JsonComponentIn(
        json = JsonNames.ADD_COMPONENT,
        params = JsonComponentParams(
          stepId = Some(stepId),
          nameToShow = Some("Component 1"),
          kind = Some("immutable")
        )
      ))
      Logger.info("IN " + jsonAddComponentIn_2)
      val jsonAddComponentOut_2: JsValue = wC.handleMessage(jsonAddComponentIn_2)
      Logger.info("OUT " + jsonAddComponentOut_2)

      (jsonAddComponentOut_2 \ "json").asOpt[String] === Some(JsonNames.ADD_COMPONENT)
      (jsonAddComponentOut_2 \ "result" \ "nameToShow").asOpt[String] === Some("Component 1")
      (jsonAddComponentOut_2 \ "result" \ "kind").asOpt[String] === Some("immutable")
      (jsonAddComponentOut_2 \ "result" \ "status" \ "addComponent" \ "status").asOpt[String] ===
        Some(AddComponentSuccess().status)
      (jsonAddComponentOut_2 \ "result" \ "status" \ "appendComponent" \ "status").asOpt[String] ===
        Some(AppendComponentSuccess().status)
      (jsonAddComponentOut_2 \ "result" \ "status" \ "common" \ "status").asOpt[String] === Some(Success().status)

      val jsonAddComponentIn_3 = Json.toJson(JsonComponentIn(
        json = JsonNames.ADD_COMPONENT,
        params = JsonComponentParams(
          stepId = Some(stepId),
          nameToShow = Some("Component 1"),
          kind = Some("immutable")
        )
      ))
      Logger.info("IN " + jsonAddComponentIn_3)
      val jsonAddComponentOut_3: JsValue = wC.handleMessage(jsonAddComponentIn_1)
      Logger.info("OUT " + jsonAddComponentOut_3)

      (jsonAddComponentOut_3 \ "json").asOpt[String] === Some(JsonNames.ADD_COMPONENT)
      (jsonAddComponentOut_3 \ "result" \ "nameToShow").asOpt[String] === Some("Component 1")
      (jsonAddComponentOut_3 \ "result" \ "kind").asOpt[String] === Some("immutable")
      (jsonAddComponentOut_3 \ "result" \ "status" \ "addComponent" \ "status").asOpt[String] ===
        Some(AddComponentSuccess().status)
      (jsonAddComponentOut_3 \ "result" \ "status" \ "appendComponent" \ "status").asOpt[String] ===
        Some(AppendComponentSuccess().status)
      (jsonAddComponentOut_3 \ "result" \ "status" \ "common" \ "status").asOpt[String] === Some(Success().status)
    }
  }
}