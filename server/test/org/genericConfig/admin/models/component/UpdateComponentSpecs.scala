package org.genericConfig.admin.models.component

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.wrapper.RidToHash
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentParams}
import org.genericConfig.admin.shared.component.status.{AddComponentSuccess, AppendComponentSuccess, UpdateComponentSuccess}
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
class UpdateComponentSpecs extends Specification
  with BeforeAfterAll
  with CommonFunction{

  val usernamePassword = "user_v016_12"
  var userId = ""
  var stepId = ""
  var componentId = ""
  val wC: WebClient = WebClient.init

  def beforeAll() : Unit = {
    val (username: String, userId: String, status: String) = addUser(this.usernamePassword)

    status match {
      case s if AddUserSuccess().status == s =>

        val (configId: String, _: StatusAddConfig) = addConfig(userId, s"http://contig/$username")
        this.stepId = addStep(Some(configId), nameToShow = Some("Step 1"), kind = Some("first")).get
        this.componentId = addComponentToStep(this.stepId, "Component", "immutable")._1
      case s if AddUserAlreadyExist().status == s =>
        val configId = getConfigId(usernamePassword = this.usernamePassword, configUrl = s"http://contig/$username")
        val configTreeBO = getConfigTree(configId)

        this.stepId = configTreeBO.configTree.get.stepId
        //TODO
        this.stepId = RidToHash.setIdAndHash(this.stepId)._2
        this.componentId = addComponentToStep(this.stepId, "Component", "immutable")._1
      case s if AddUserError().status == s =>
        Logger.info("Fehler bei der Vorbereitung")
    }
  }

  def afterAll(): Unit = {
    val count = deleteComponents(this.stepId)
    require(count == 1, "deleted components " + count)

  }

  "Diese Specification spezifiziert das Hinzufügen von der Component zu dem FirstStep user5" >> {
    "FirstStep -> Component hinzufuegen" >> {

      val jsonAddComponentIn = Json.toJson(JsonComponentIn(
        json = JsonNames.UPDATE_COMPONENT,
        params = JsonComponentParams(
          componentId = Some(this.componentId),
          nameToShow = Some("Component 1 updated"),
          kind = Some("immutable updated")
        )
      ))

      Logger.info("IN " + jsonAddComponentIn)

      val jsonAddComponentOut: JsValue = wC.handleMessage(jsonAddComponentIn)

      Logger.info("OUT " + jsonAddComponentOut)

      (jsonAddComponentOut \ "json").asOpt[String] === Some(JsonNames.UPDATE_COMPONENT)
      (jsonAddComponentOut \ "result" \ "componentId").asOpt[String].get.length must be_<=(32)
      (jsonAddComponentOut \ "result" \ "nameToShow").asOpt[String] === Some("Component 1 updated")
      (jsonAddComponentOut \ "result" \ "kind").asOpt[String] === Some("immutable updated")
      (jsonAddComponentOut \ "result" \ "status" \ "updateComponent" \ "status").asOpt[String] ===
        Some(UpdateComponentSuccess().status)
      (jsonAddComponentOut \ "result" \ "status" \ "common" \ "status").asOpt[String] === Some(Success().status)


    }
  }

}
