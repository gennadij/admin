package org.genericConfig.admin.models.step

import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.websocket.WebClient
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
class AddFirstStepSpecs extends Specification
                          with AdminWeb
                          with BeforeAfterAll
                          with CommonFunction{
                          
  
  var configId = ""

  val wC: WebClient = WebClient.init

  val usernamePassword = "user4"

  def beforeAll(): Unit = {
    val (username: String, userId: String, status: String) = addUser(usernamePassword)
    status match {
      case s if AddUserSuccess().status == s =>
        val (configId: String, _) = addConfig(userId, s"http://contig/$username")

        val (_, configIdHash) = RidToHash.setIdAndHash(configId)

        this.configId = configIdHash

      case s if AddUserAlreadyExist().status == s =>
        this.configId = getConfigId(usernamePassword = "user4", configUrl = s"http://contig/$username")

        val (_, configIdHash) = RidToHash.setIdAndHash(configId)

        this.configId = configIdHash

      case s if AddUserError().status == s =>
        Logger.info("Fehler bei der Vorbereitung")

    }
  }
  
  def afterAll(): Unit = {
    val count = deleteStepAppendedToConfig(RidToHash.getId(configId).get)
    require(count == 1, "Anzahl der geloeschten Steps " + count)
  }

  
  "Diese Specification spezifiziert das Hinzufügen von dem Step zu der Konfiguration" >> {
    "FirstStep hinzufuegen" >> {
      
      val jsonAddFirstStep = Json.toJsObject(JsonStepIn(
        json = JsonNames.ADD_FIRST_STEP,
        params = JsonStepParams(
          configId = configId,
          nameToShow = "FirstStep",
          kind = "first",
          selectionCriterium = Some(JsonSelectionCriterium(
            min = 1,
            max = 1
          ))
        )
      ))

      Logger.info("<-" + jsonAddFirstStep)
      
      val firstStepSC: JsValue = wC.handleMessage(jsonAddFirstStep)
      
      Logger.info("->" + firstStepSC )
      
      (firstStepSC \ "json").asOpt[String].get === JsonNames.ADD_FIRST_STEP
      (firstStepSC \ "result" \ "status" \ "addStep" \ "status").asOpt[String].get === AddStepSuccess().status
      (firstStepSC \ "result" \ "status" \ "deleteStep" \ "status").asOpt[String] === None
      (firstStepSC \ "result" \ "status" \ "updateStep" \ "status").asOpt[String] === None
      (firstStepSC \ "result" \ "status" \ "appendStep" \ "status").asOpt[String].get === AppendStepSuccess().status
      (firstStepSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status

      val jsonAddFirstStep_2 = Json.toJsObject(JsonStepIn(
        json = JsonNames.ADD_FIRST_STEP,
        params = JsonStepParams(
          configId = configId,
          nameToShow = "FirstStep",
          kind = "first",
          selectionCriterium = Some(JsonSelectionCriterium(
            min = 1,
            max = 1
          ))
        )
      ))

      Logger.info("<-" + jsonAddFirstStep_2)
      val twiceStepConfigTreeSC: JsValue = wC.handleMessage(jsonAddFirstStep_2)
      Logger.info("->" + twiceStepConfigTreeSC)
      
      (twiceStepConfigTreeSC \ "json").asOpt[String].get === JsonNames.ADD_FIRST_STEP
      (twiceStepConfigTreeSC \ "result" \ "status" \ "addStep" \ "status").asOpt[String].get === AddStepAlreadyExist().status
      (twiceStepConfigTreeSC \ "result" \ "status" \ "deleteStep" \ "status").asOpt[String] === None
      (twiceStepConfigTreeSC \ "result" \ "status" \ "updateStep" \ "status").asOpt[String] === None
      (twiceStepConfigTreeSC \ "result" \ "status" \ "appendStep" \ "status").asOpt[String] === None
      (twiceStepConfigTreeSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Error().status


      val jsonAddFirstStep_3 = Json.toJsObject(JsonStepIn(
        json = JsonNames.ADD_FIRST_STEP,
        params = JsonStepParams(
          configId = "#1:1",
          nameToShow = "FirstStep",
          kind = "first",
          selectionCriterium = Some(JsonSelectionCriterium(
            min = 1,
            max = 1
          ))
        )
      ))

      Logger.info("<-" + jsonAddFirstStep_3)

      RidToHash.setIdAndHash("#1:1")
      val stepOut_3: JsValue = wC.handleMessage(jsonAddFirstStep_3)
      
      Logger.info("->" + stepOut_3)
      
      (stepOut_3 \ "json").asOpt[String].get === JsonNames.ADD_FIRST_STEP
      (stepOut_3 \ "result" \ "status" \ "addStep" \ "status").asOpt[String].get === AddStepDefectComponentOrConfigId().status
      (stepOut_3 \ "result" \ "status" \ "deleteStep" \ "status").asOpt[String] === None
      (stepOut_3 \ "result" \ "status" \ "updateStep" \ "status").asOpt[String] === None
      (stepOut_3 \ "result" \ "status" \ "appendStep" \ "status").asOpt[String] === None
      (stepOut_3 \ "result" \ "status" \ "common" \ "status").asOpt[String].get === ODBRecordIdDefect().status
    }
  }
}