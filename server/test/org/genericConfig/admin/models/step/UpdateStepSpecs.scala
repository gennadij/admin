package org.genericConfig.admin.models.step

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.models.persistence.orientdb.PropertyKeys
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.step.{SelectionCriterionDTO, StepDTO, StepParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 17.05.2018
 */

class UpdateStepSpecs extends Specification
                             with BeforeAfterAll
                             with CommonFunction{

  val wC: WebClient = WebClient.init
  val username = "updateStep_All"
  var stepId: Option[String] = _
  var updateStepAllResult : JsResult[StepDTO] = _

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {
    val error = deleteVertex(RidToHash.getRId(stepId.get).get, PropertyKeys.VERTEX_STEP)
    Logger("Deleted Step error : " + error)
  }

  "Der User aendert den Name und Auswahlkriterium bei dem Schritt" >> {
    "User aendert Name und Auswahlkriterium" >> {
      "action = UPDATE_STEP" >> {updateStepAllResult.get.action === Actions.UPDATE_STEP}
      "stepId < 32" >> {updateStepAllResult.get.result.get.stepId.get.length must be_<(32)}
      "Name = FirstStepUpdated" >> {updateStepAllResult.get.result.get.properties.get.nameToShow.get === "FirstStepUpdated"}
      "MIN = 2" >> {updateStepAllResult.get.result.get.properties.get.selectionCriterion.get.min.get === 2}
      "MAX = 2" >> {updateStepAllResult.get.result.get.properties.get.selectionCriterion.get.max.get === 2}
    }
    "User aendert Name" >> {
      "action = UPDATE_STEP" >> {"" === ""}
      "stepId < 32" >> {"" === ""}
      "Name = FirstStepUpdated" >> {"" === ""}
      "MIN = 1" >> {"" === ""}
      "MAX = 1" >> {"" === ""}
    }
    "User aendert Name Auswahlkriterium" >> {
      "action = UPDATE_STEP" >> {"" === ""}
      "stepId < 32" >> {"" === ""}
      "Name = FirstStepToUpdate" >> {"" === ""}
      "MIN = 2" >> {"" === ""}
      "MAX = 2" >> {"" === ""}
    }
    "User aendert Name Auswahlkriterium nur Min" >> {
      "action = UPDATE_STEP" >> {"" === ""}
      "stepId < 32" >> {"" === ""}
      "Name = FirstStepToUpdate" >> {"" === ""}
      "MIN = 2" >> {"" === ""}
      "MAX = 1" >> {"" === ""}
    }
    "User aendert Name Auswahlkriterium nur Max" >> {
      "action = UPDATE_STEP" >> {"" === ""}
      "stepId < 32" >> {"" === ""}
      "Name = FirstStepToUpdate" >> {"" === ""}
      "MIN = 1" >> {"" === ""}
      "MAX = 2" >> {"" === ""}
    }
  }

  def before() : Unit = {
    val wC: WebClient = WebClient.init
    val userId = createUser(username, wC)
    createStepForUpdateAll(userId)
    createStepForUpdateOnlyNameToShow(userId)
    createStepForUpdateOnlySC(userId)
    createStepForUpdateOnlySCMin(userId)
    createStepForUpdateOnlySCMax(userId)
  }

  def createStepForUpdateAll(userId : String): Unit = {
    val configId = createConfig(userId, s"http://contig/$username")
    val nameToShow: Option[String] = Some("FirstStepToUpdate")
    val kind : Option[String] = Some("first")

    stepId = addStep(nameToShow, Some(configId), kind, 1, 1, wC)

    val updateFirstStepParams: JsValue = Json.toJson(StepDTO(
      action = Actions.UPDATE_STEP,
      params = Some(StepParamsDTO(
        stepId = stepId,
        nameToShow = Some("FirstStepUpdated"),
        selectionCriterion = Some(SelectionCriterionDTO(
          min = Some(2),
          max = Some(2)
        ))
      ))
    ))
    Logger.info("UPDATE_STEP -> " + updateFirstStepParams)
    updateStepAllResult = Json.fromJson[StepDTO](wC.handleMessage(updateFirstStepParams))
    Logger.info("UPDATE_STEP <- " + updateStepAllResult)
  }

  def createStepForUpdateOnlyNameToShow(userId : String): Unit = {

  }

  def createStepForUpdateOnlySC(userId : String): Unit = {

  }

  def createStepForUpdateOnlySCMin(userId : String): Unit = {

  }

  def createStepForUpdateOnlySCMax(userId : String): Unit = {

  }
}