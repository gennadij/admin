package org.genericConfig.admin.client.controllers.websocket

import org.genericConfig.admin.client.old.config.GetConfig
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.ConfigDTO
import org.scalajs.dom.raw.WebSocket
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 08.04.2020
 */

object ConverterFromJsonForConfig{
  def addConfig(receivedMessage: JsValue, webSocket: WebSocket) = {
    new ConverterFromJsonForConfig(webSocket).addConfig(receivedMessage)
  }

  def getConfigs(receivedMessage: JsValue, webSocket: WebSocket) = {
    new ConverterFromJsonForConfig(webSocket).getConfigs(receivedMessage)
  }

  def deleteConfig(receivedMessage: JsValue, webSocket: WebSocket) = {
    new ConverterFromJsonForConfig(webSocket).deleteConfig(receivedMessage)
  }

  def updateConfig(receivedMessage: JsValue, webSocket: WebSocket) = {
    new ConverterFromJsonForConfig(webSocket).deleteConfig(receivedMessage)
  }
}

class ConverterFromJsonForConfig(webSocket: WebSocket) {
  private def addConfig(receivedMessage: JsValue) = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case addConfigResult : JsSuccess[ConfigDTO] =>
        new GetConfig(webSocket).update(addConfigResult.value)
      case e: JsError => println("Errors -> : " + Actions.ADD_CONFIG + JsError.toJson(e).toString())
    }
  }

  private def getConfigs(receivedMessage: JsValue): Unit = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case getConfigsResult: JsSuccess[ConfigDTO] => new GetConfig(webSocket).drawAllConfigs(getConfigsResult.get)
      case e: JsError => println("Error -> : " + Actions.GET_CONFIGS + " -> " + JsError.toJson(e).toString())
    }
  }

  private def deleteConfig(receivedMessage: JsValue): Unit = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case deleteConfigResult: JsSuccess[ConfigDTO] => //new DeleteConfig(webSocket).updateStatus(deleteConfigParams.get)
      case e: JsError => println("Errors -> CREATE_CONFIG: " + JsError.toJson(e).toString())
    }
  }

  private def updateConfig(receivedMessage: JsValue): Unit = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case updateConfigResult: JsSuccess[ConfigDTO] => ???
      case e: JsError => println("Errors -> CREATE_CONFIG: " + JsError.toJson(e).toString())
    }
  }
}
