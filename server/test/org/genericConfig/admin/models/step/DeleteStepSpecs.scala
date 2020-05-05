package org.genericConfig.admin.models.step

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.step.{StepDTO, StepParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 08.06.2018
  */
class DeleteStepSpecs extends Specification
                              with BeforeAfterAll
                              with CommonFunction{

  var deleteFirstStep1Result : JsResult[StepDTO] = _

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {}

  "Der Benutzer löscht den ersten Schritt" >> {
    "action = DELETE_STEP" >> {deleteFirstStep1Result.asOpt.get.action === Actions.DELETE_STEP}
    "result.stepId < 32 " >> {deleteFirstStep1Result.asOpt.get.result.get.stepId must beNone}
    "result.errors = " >> {deleteFirstStep1Result.asOpt.get.result.get.errors must beNone}
  }

  private def before(): Unit = {
    val wC: WebClient = WebClient.init
    val username = "user_v016_10"
    val userId = createUser(username, wC)
    val configId = createConfig(userId, s"http://contig/$username")

    val stepId = addStep(Some("FirstStep"), Some(configId), Some("first"), 1, 1, wC)

    val deleteFirstStep1Params: JsValue = Json.toJson(StepDTO(
      action = Actions.DELETE_STEP,
      params = Some(StepParamsDTO(
        stepId = stepId
      )),
      result = None
    ))
    Logger.info("DELETE_STEP -> " + deleteFirstStep1Params)
    deleteFirstStep1Result = Json.fromJson[StepDTO](wC.handleMessage(deleteFirstStep1Params))
    Logger.info("DELETE_STEP <- " + deleteFirstStep1Result)
  }

}
