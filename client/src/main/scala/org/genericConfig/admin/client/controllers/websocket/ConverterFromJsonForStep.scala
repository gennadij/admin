package org.genericConfig.admin.client.controllers.websocket

import org.genericConfig.admin.client.controllers.websocket
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
    new websocket.ConverterFromJsonForStep().deleteStep(receivedMessage)
  }

  def addStep(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForStep().addStep(receivedMessage)
  }

  def updateStep(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForStep().updateStep(receivedMessage)
  }
}


class ConverterFromJsonForStep {
  private def deleteStep(receivedMessage: JsValue): Any = {
    Json.fromJson[StepDTO](receivedMessage) match {
      case deleteStepResult : JsSuccess[StepDTO] => new Step().updateGraphResponse(Some(deleteStepResult.value))
      case e: JsError => println("Errors -> : " + Actions.DELETE_STEP + JsError.toJson(e).toString())
    }
  }

  private def addStep(receivedMessage: JsValue): Unit = {
    Json.fromJson[StepDTO](receivedMessage) match {
      case addStepResult : JsSuccess[StepDTO] => new Step().updateGraphResponse(Some(addStepResult.value))
      case e: JsError => println("Errors -> : " + Actions.DELETE_STEP + JsError.toJson(e).toString())
    }
  }

  private def updateStep(receivedMessage: JsValue): Unit = {
    Json.fromJson[StepDTO](receivedMessage) match {
      case updateStepResult : JsSuccess[StepDTO] => new Step().updateGraphResponse(Some(updateStepResult.value))
      case e: JsError => println("Errors -> : " + Actions.UPDATE_STEP + JsError.toJson(e).toString())
    }
  }
}
