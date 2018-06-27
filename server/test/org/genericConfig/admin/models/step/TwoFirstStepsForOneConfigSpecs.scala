package org.genericConfig.admin.models.step

import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import play.api.Logger
import org.genericConfig.admin.controllers.admin.AdminWeb
import util.CommonFunction
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.step.json.JsonStepIn
import org.genericConfig.admin.shared.user.status.AddUserSuccess
import org.genericConfig.admin.shared.user.status.AddUserAlreadyExist
import org.genericConfig.admin.shared.user.status.AddUserError
import org.genericConfig.admin.shared.step.json.JsonStepParams
import org.genericConfig.admin.shared.step.json.JsonSelectionCriterium
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.step.status.AddStepSuccess
import org.genericConfig.admin.shared.step.status.AppendStepSuccess
import org.genericConfig.admin.shared.step.status.AddStepAlreadyExist
import org.genericConfig.admin.shared.common.status.Error

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 05.05.2017
	*
	* Username = user15
	*/
@RunWith(classOf[JUnitRunner])
class TwoFirstStepsForOneConfigSpecs   extends Specification
                                       with AdminWeb
                                       with BeforeAfterAll
                                       with CommonFunction {
  
  var configId = ""
  var usernamePassword = "user15"
  
  val webClient: WebClient = WebClient.init
  
  def beforeAll = {
    val (username: String, userId: String, status: String) = addUser(usernamePassword)
    status match {
      case s if AddUserSuccess().status == s =>
        val (configId: String, _) = addConfig(userId, s"http://contig/$username")
        this.configId = configId
        addStep(Some(configId), None)
      case s if AddUserAlreadyExist().status == s =>
        this.configId = getConfigId(usernamePassword = usernamePassword, configUrl = s"http://contig/$username")
        addStep(Some(configId), nameToShow = Some("Stepo 1"), kind = Some("first"))
      case s if AddUserError().status == s =>
        Logger.info("Fehler bei der Vorbereitung")
    }
  }
	def afterAll = {}
	
	"Hier wird die Erzeugung von 2 FirstSteps innerhalb einer Config spezifiziert" >> {
    "2 FirstStep sind nicht erlaubt" >> {
      
      val jsonAddFirstStep = Json.toJsObject(JsonStepIn(
        json = JsonNames.ADD_STEP,
        params = JsonStepParams(
          appendToId = configId,
          nameToShow = "FirstStep",
          kind = "first",
          selectionCriterium = Some(JsonSelectionCriterium(
            min = 1,
            max = 1
          ))
        )
      ))
      
      val jsonFirstStepOut_1: JsValue = webClient.handleMessage(jsonAddFirstStep)
      
      Logger.info("OUT " + jsonFirstStepOut_1 )
      
      (jsonFirstStepOut_1 \ "json").asOpt[String].get === JsonNames.ADD_STEP
      (jsonFirstStepOut_1 \ "result" \ "status" \ "addStep" \ "status").asOpt[String] === Some(AddStepAlreadyExist().status)
      (jsonFirstStepOut_1 \ "result" \ "status" \ "deleteStep" \ "status").asOpt[String] === None
      (jsonFirstStepOut_1 \ "result" \ "status" \ "updateStep" \ "status").asOpt[String] === None
      (jsonFirstStepOut_1 \ "result" \ "status" \ "appendStep" \ "status").asOpt[String] === None
      (jsonFirstStepOut_1 \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Error().status
    }
  }
}