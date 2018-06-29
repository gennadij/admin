package org.genericConfig.admin.models.step

import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.step.json.{JsonStepIn, JsonStepParams}
import org.genericConfig.admin.shared.step.status._
import org.genericConfig.admin.shared.user.status.{AddUserAlreadyExist, AddUserError, AddUserSuccess}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsValue, Json}
import util.CommonFunction

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 08.06.2018
  */
@RunWith(classOf[JUnitRunner])
class DeleteFirstStepSpecs extends Specification
                              with AdminWeb
                              with BeforeAfterAll
                              with CommonFunction{


    var configId = ""

    var stepId = ""

    val wC: WebClient = WebClient.init

    val usernamePassword = "user_v016_10"

    def beforeAll(): Unit = {
      val (username: String, userId: String, status: String) = addUser(usernamePassword)
      status match {
        case s if AddUserSuccess().status == s =>
          val (configId: String, _) = addConfig(userId, s"http://contig/$username")
          this.configId = configId
          this.stepId = addStep(Some(configId), None).get
        case s if AddUserAlreadyExist().status == s =>
          this.configId = getConfigId(usernamePassword = usernamePassword, configUrl = s"http://contig/$username")
          this.stepId = addStep(Some(this.configId), nameToShow = Some("Step 1"), kind = Some("first")).get
        case s if AddUserError().status == s =>
          Logger.info("Fehler bei der Vorbereitung")
      }
    }

    def afterAll(): Unit = {
      val count = deleteStepAppendedToConfig(RidToHash.getRId(this.configId).get)
      require(count == 0, "Anzahl der geloeschten Steps " + count)
    }


    "Diese Specification spezifiziert die Loeschung des Schrittes aus der Konfiguration" >> {
      "FirstStep loeschen" >> {

        val jsonDeleteFirstStep = Json.toJsObject(JsonStepIn(
          json = JsonNames.DELETE_STEP,
          params = JsonStepParams(
            stepId = this.stepId,
        )))

        Logger.info("IN " + jsonDeleteFirstStep)

        val jsonDeleteFirstStepOut: JsValue = wC.handleMessage(jsonDeleteFirstStep)

        Logger.info("OUT " + jsonDeleteFirstStepOut )

        (jsonDeleteFirstStepOut \ "json").asOpt[String].get === JsonNames.DELETE_STEP
        (jsonDeleteFirstStepOut \ "result" \ "status" \ "addStep" \ "status").asOpt[String] === None
        (jsonDeleteFirstStepOut \ "result" \ "status" \ "deleteStep" \ "status").asOpt[String] === Some(DeleteStepSuccess().status)
        (jsonDeleteFirstStepOut \ "result" \ "status" \ "updateStep" \ "status").asOpt[String] === None
        (jsonDeleteFirstStepOut \ "result" \ "status" \ "appendStep" \ "status").asOpt[String] === None
        (jsonDeleteFirstStepOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
      }
    }

}
