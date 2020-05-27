package org.genericConfig.admin.models.component

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentParams}
import org.genericConfig.admin.shared.component.status.DeleteComponentSuccess
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsValue, Json}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 18.06.2018
  */
class DeleteComponentSpecs
  extends Specification
  with BeforeAfterAll
  with CommonFunction{

  val usernamePassword = "user_v016_11"
  var userId = ""
  var stepId = ""
  var componentId = ""
  val wC: WebClient = WebClient.init

  def beforeAll() : Unit = {

  }

  def afterAll(): Unit = {}

  "Der Benutzer entfernt der Komponente aus der Konfiguration" >> {
    "Die Komponente im Abgang keinen Schritt hat S -> C" >> {

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
