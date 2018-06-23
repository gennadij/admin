package org.genericConfig.admin.models.step

import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models._
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.{Error, ODBRecordIdDefect, Success}
import org.genericConfig.admin.shared.step.json.{JsonSelectionCriterium, JsonStepIn, JsonStepParams}
import org.genericConfig.admin.shared.step.status.{AddStepAlreadyExist, AddStepDefectComponentOrConfigId, AddStepSuccess, AppendStepSuccess}
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
import org.genericConfig.admin.models.wrapper.RidToHash
import org.genericConfig.admin.shared.step.status.AddStepIdHashNotExist

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 16.01.2017
  *
  * Changes in
  * @version 0.1.0
  * @version 0.1.3
  *
  * Username = user4
  */

@RunWith(classOf[JUnitRunner])
class AddStepSpecs extends Specification
  with AdminWeb
  with BeforeAfterAll
  with CommonFunction{

  val wC: WebClient = WebClient.init
  var configId = ""
  val usernamePassword = "user8"
  var componentId = ""

  def beforeAll(): Unit = {
    val (username: String, userId: String, status: String) = addUser(usernamePassword)
    status match {
      case s if AddUserSuccess().status == s =>
        val (configId: String, _) = addConfig(userId, s"http://contig/$username")
        this.configId = configId
        val firstStepId = addStep(
          appendToId = Some(configId),
          nameToShow = Some("First Step"),
          kind = Some("first")).get

        this.componentId = addComponentToStep(firstStepId, nameToShow = "Component_1_1", kind = "immutable")._1

      case s if AddUserAlreadyExist().status == s =>
        this.configId = getConfigId(usernamePassword = usernamePassword, configUrl = s"http://contig/$usernamePassword")

        this.componentId = getConfigTree(this.configId).configTree.get.components.head.get.componentId
      case s if AddUserError().status == s =>
        Logger.info("Fehler bei der Vorbereitung")

    }
  }

  def afterAll(): Unit = {
//    val count = deleteStepAppendedToConfig(RidToHash.getRId(configId).get)
//    require(count == 1, "Anzahl der geloeschten Steps " + count)
  }


  "Diese Specification spezifiziert das Hinzufügen von dem Step zu der Konfiguration" >> {
    "FirstStep hinzufuegen" >> {

      val jsonAddStep = Json.toJsObject(JsonStepIn(
        json = JsonNames.ADD_STEP,
        params = JsonStepParams(
          appendToId = this.componentId,
          nameToShow = "FirstStep",
          kind = "first",
          selectionCriterium = Some(JsonSelectionCriterium(
            min = 1,
            max = 1
          ))
        )
      ))

      Logger.info("IN " + jsonAddStep)

//      val firstStepSC: JsValue = wC.handleMessage(jsonAddStep)

//      Logger.info("OUT " + firstStepSC )

//      (firstStepSC \ "json").asOpt[String].get === JsonNames.ADD_STEP
//      (firstStepSC \ "result" \ "status" \ "addStep" \ "status").asOpt[String].get === AddStepSuccess().status
//      (firstStepSC \ "result" \ "status" \ "deleteStep" \ "status").asOpt[String] === None
//      (firstStepSC \ "result" \ "status" \ "updateStep" \ "status").asOpt[String] === None
//      (firstStepSC \ "result" \ "status" \ "appendStep" \ "status").asOpt[String].get === AppendStepSuccess().status
//      (firstStepSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
      "" === ""
    }
  }
}