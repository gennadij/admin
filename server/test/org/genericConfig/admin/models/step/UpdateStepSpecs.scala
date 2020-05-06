package org.genericConfig.admin.models.step

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.persistence.orientdb.PropertyKeys
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.step.{SelectionCriterionDTO, StepDTO, StepParamsDTO}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 17.05.2018
 */
@RunWith(classOf[JUnitRunner])
class UpdateStepSpecs extends Specification
                             with BeforeAfterAll
                             with CommonFunction{

  val wC: WebClient = WebClient.init
  var configId: String = _
  var stepId: Option[String] = _
  var updateFirstStepResult : JsResult[StepDTO] = _

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {
    val error = deleteVertex(stepId.get, PropertyKeys.VERTEX_STEP)
    Logger("Deleted Step error : " + error)
  }

  "Der User aendert den Name und Auswahlkriterium bei dem Schritt" >> {
    "AdminUser = user_updateStep_v016_1" >> {

      "" === ""
    }
  }

  def before() : Unit = {
    val wC: WebClient = WebClient.init
    val username = "user_updateStep_v016_1"
    val userId = createUser(username, wC)
    configId = createConfig(userId, s"http://contig/$username")

    val nameToShow: Option[String] = Some("FirstStepToUpdate")
    val kind : Option[String] = Some("first")

    stepId = addStep(nameToShow, Some(configId), kind, 1, 1, wC)

    val updateFirstStepParams: JsValue = Json.toJson(StepDTO(
      action = Actions.UPDATE_STEP,
      params = Some(StepParamsDTO(
        stepId = stepId,
        nameToShow = Some("FirstStepUpdated"),
        selectionCriterion = Some(SelectionCriterionDTO(
          min = 2,
          max = 2
        ))

      ))
    ))
    Logger.info("DELETE_STEP -> " + updateFirstStepParams)
    updateFirstStepResult = Json.fromJson[StepDTO](wC.handleMessage(updateFirstStepParams))
    Logger.info("DELETE_STEP <- " + updateFirstStepResult)

  }
}