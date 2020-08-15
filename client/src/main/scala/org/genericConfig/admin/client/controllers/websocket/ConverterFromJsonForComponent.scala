package org.genericConfig.admin.client.controllers.websocket

import org.genericConfig.admin.client.models.Component
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.component.ComponentDTO
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 24.07.2020
 */
object ConverterFromJsonForComponent {
  def deleteComponent(receivedMessage: JsValue): Any = {
    new ConverterFromJsonForComponent().deleteComponent(receivedMessage)
  }

  def addComponent(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForComponent().addComponent(receivedMessage)
  }

  def connectComponent(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForComponent().connectComponentToStep(receivedMessage)
  }

  def updateComponent(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForComponent().updateComponent(receivedMessage)
  }
}

class ConverterFromJsonForComponent() {
  def deleteComponent(receivedMessage: JsValue): Any = {
    Json.fromJson[ComponentDTO](receivedMessage) match {
      case addComponentResult : JsSuccess[ComponentDTO] => new Component().updateGraphResponse(Some(addComponentResult.value))
      case e: JsError => println("Errors -> : " + Actions.DELETE_COMPONENT + JsError.toJson(e).toString())
    }
  }

  def addComponent(receivedMessage: JsValue): Unit = {
    Json.fromJson[ComponentDTO](receivedMessage) match {
      case addComponentResult : JsSuccess[ComponentDTO] => new Component().updateGraphResponse(Some(addComponentResult.value))
      case e: JsError => println("Errors -> : " + Actions.ADD_COMPONENT + JsError.toJson(e).toString())
    }
  }

  def connectComponentToStep(receivedMessage: JsValue): Unit = {
    Json.fromJson[ComponentDTO](receivedMessage) match {
      case connectComponentToStepResult : JsSuccess[ComponentDTO] => new Component().connectComponentToStepResponse(Some(connectComponentToStepResult.value))
      case e: JsError => println("Errors -> : " + Actions.ADD_COMPONENT + JsError.toJson(e).toString())
    }
  }

  def updateComponent(receivedMessage: JsValue): Unit = {
    Json.fromJson[ComponentDTO](receivedMessage) match {
      case updateComponentResult : JsSuccess[ComponentDTO] => new Component().updateComponentResponse()
      case e: JsError => println("Errors -> : " + Actions.ADD_COMPONENT + JsError.toJson(e).toString())
    }
  }
}

