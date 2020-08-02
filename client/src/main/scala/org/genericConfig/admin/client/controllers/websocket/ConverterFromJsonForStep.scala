package org.genericConfig.admin.client.controllers.websocket

import org.genericConfig.admin.client.models.Step
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.step.StepDTO
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 02.08.2020
 */
object ConverterFromJsonForStep {
  def deleteStep(receivedMessage: JsValue): Unit = {
//    new ConverterFromJsonForComponent().deleteComponent(receivedMessage)
  }

  def addStep(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForStep().addStep(receivedMessage)
  }
}


class ConverterFromJsonForStep {
  def deleteStep(receivedMessage: JsValue): Any = {
    Json.fromJson[StepDTO](receivedMessage) match {
      case addComponentResult : JsSuccess[StepDTO] => //new Component().deleteComponentResponse(Some(addComponentResult.value))
      case e: JsError => println("Errors -> : " + Actions.DELETE_STEP + JsError.toJson(e).toString())
    }
  }

  def addStep(receivedMessage: JsValue): Unit = {
    Json.fromJson[StepDTO](receivedMessage) match {
      case addStepResult : JsSuccess[StepDTO] => new Step().addStepResponse(Some(addStepResult.value))
      case e: JsError => println("Errors -> : " + Actions.ADD_STEP + JsError.toJson(e).toString())
    }
  }
}
