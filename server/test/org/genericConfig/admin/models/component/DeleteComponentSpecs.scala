package org.genericConfig.admin.models.component

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentParams}
import org.genericConfig.admin.shared.component.status.DeleteComponentSuccess
import org.genericConfig.admin.shared.config.status.StatusAddConfig
import org.genericConfig.admin.shared.user.status.{AddUserAlreadyExist, AddUserError, AddUserSuccess}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsValue, Json}
import util.CommonFunction

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 18.06.2018
  */
class DeleteComponentSpecs extends Specification
  with BeforeAfterAll
  with CommonFunction{

  val usernamePassword = "user_v016_11"
  var userId = ""
  var stepId = ""
  var componentId = ""
  val wC: WebClient = WebClient.init

  def beforeAll() : Unit = {
    val (username: String, userId: String, status: String) = addUser(this.usernamePassword)

    status match {
      case s if AddUserSuccess().status == s =>

        val (configId: String, _: StatusAddConfig) = addConfig(userId, s"http://contig/$username")
        this.stepId = addStep(Some(configId), nameToShow = Some("Step1"), kind = Some("first")).get
        this.componentId = addComponentToStep(this.stepId, "Component to Delete", "immutable")._1

      case s if AddUserAlreadyExist().status == s =>
        val configId = getConfigId(usernamePassword = this.usernamePassword, configUrl = s"http://contig/$username")
        val configTreeBO = getConfigTree(configId)

        this.stepId = configTreeBO.configTree.get.stepId

        this.componentId = addComponentToStep(this.stepId, "Component to Delete", "immutable")._1

      case s if AddUserError().status == s =>
        Logger.info("Fehler bei der Vorbereitung")
    }
  }

  def afterAll(): Unit = {}

  "Diese Specification spezifiziert das Entfernen der Komponenten" >> {
    "Delete Component" >> {

      val jsonAddComponentIn = Json.toJson(JsonComponentIn(
        json = JsonNames.DELETE_COMPONENT,
        params = JsonComponentParams(
          componentId = Some(this.componentId)
        )
      ))

      Logger.info("IN " + jsonAddComponentIn)

      val jsonAddComponentOut: JsValue = wC.handleMessage(jsonAddComponentIn)

      Logger.info("OUT " + jsonAddComponentOut)

      (jsonAddComponentOut \ "json").asOpt[String] === Some(JsonNames.DELETE_COMPONENT)
      (jsonAddComponentOut \ "result" \ "status" \ "deleteComponent" \ "status").asOpt[String] ===
        Some(DeleteComponentSuccess().status)
      (jsonAddComponentOut \ "result" \ "status" \ "common" \ "status").asOpt[String] === Some(Success().status)
    }
  }

}
