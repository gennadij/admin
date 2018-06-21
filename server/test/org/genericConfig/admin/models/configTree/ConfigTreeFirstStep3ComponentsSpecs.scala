package org.genericConfig.admin.models.configTree

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.config.status.StatusAddConfig
import org.genericConfig.admin.shared.configTree.json.{JsonConfigTreeIn, JsonConfigTreeParams}
import org.genericConfig.admin.shared.configTree.status.GetConfigTreeSuccess
import org.genericConfig.admin.shared.user.status.{AddUserAlreadyExist, AddUserError, AddUserSuccess}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.{JsValue, Json}
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 16.01.2017
 */

@RunWith(classOf[JUnitRunner])
class ConfigTreeFirstStep3ComponentsSpecs extends Specification
                                          with BeforeAfterAll
                                          with CommonFunction{

  val wC: WebClient = WebClient.init
  val usernamePassword = "user7"
  var stepId = ""
  var configId = ""
  var componentId_1 = ""
  var componentId_2 = ""
  var componentId_3 = ""

  def beforeAll(): Unit = {
    val (username: String, userId: String, status: String) = addUser(this.usernamePassword)

    status match {
      case s if AddUserSuccess().status == s =>

        val (configId: String, _: StatusAddConfig) = addConfig(userId, s"http://contig/$username")

        this.configId = configId
        this.stepId = addStep(Some(configId), None).get

        this.componentId_1 = addComponentToStep(this.stepId, "Component1", "immutable1")._1
        this.componentId_2 = addComponentToStep(this.stepId, "Component2", "immutable2")._1
        this.componentId_3 = addComponentToStep(this.stepId, "Component3", "immutable3")._1

      case s if AddUserAlreadyExist().status == s =>
        val configId = getConfigId(usernamePassword = this.usernamePassword, configUrl = s"http://contig/$username")

        this.configId = configId

      case s if AddUserError().status == s =>
        Logger.info("Fehler bei der Vorbereitung")
    }
  }

  def afterAll(): Unit = {}

  "Specification spezifiziert die Erzeugung von der ConfigTree ein Schritt mit 3 Komponenten " >> {

    val configTreeIn = Json.toJsObject(
      JsonConfigTreeIn(
        params = JsonConfigTreeParams(
          configId = this.configId
        )
      )
    )

    Logger.info("IN " + configTreeIn)

    val configTreeOut = wC.handleMessage(configTreeIn)

    Logger.info("OUT " + configTreeOut)

//    (configTreeOut \ "json").asOpt[String].get === JsonNames.CONFIG_TREE
//    ((configTreeOut \ "result" \ "step") \ "kind").asOpt[String].get === "first"
//    (((configTreeOut \ "result" \ "step") \ "components")).asOpt[Set[JsValue]].get.size === 3
//    ((((configTreeOut \ "result" \ "step") \ "components")(0)) \ "kind").asOpt[String].get === "immutable"
//    ((((configTreeOut \ "result" \ "step") \ "components")(0)) \ "nextStep" \ "kind").asOpt[String].get === "default"
//    ((((configTreeOut \ "result" \ "step") \ "components")(0)) \ "nextStep" \ "components").asOpt[Set[JsValue]].get.size === 0
//    ((((configTreeOut \ "result" \ "step") \ "components")(1)) \ "kind").asOpt[String].get === "immutable"
//    ((((configTreeOut \ "result" \ "step") \ "components")(1)) \ "nextStepId").asOpt[String].get === "last"
//    ((((configTreeOut \ "result" \ "step") \ "components")(1)) \ "nextStep").asOpt[String] === None
//    ((((configTreeOut \ "result" \ "step") \ "components")(2)) \ "kind").asOpt[String].get === "immutable"
//    ((((configTreeOut \ "result" \ "step") \ "components")(2)) \ "nextStepId").asOpt[String].get === "last"
//    ((((configTreeOut \ "result" \ "step") \ "components")(2)) \ "nextStep").asOpt[String] === None
//    (configTreeOut \ "result" \ "status" \ "getConfigTree" \ "status" ).asOpt[String].get === GetConfigTreeSuccess().status
//    (configTreeOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    "" === ""
  }
}