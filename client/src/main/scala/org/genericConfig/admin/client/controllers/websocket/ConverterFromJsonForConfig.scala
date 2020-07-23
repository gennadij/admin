package org.genericConfig.admin.client.controllers.websocket

import org.genericConfig.admin.client.models.Config
import org.genericConfig.admin.client.views.user.UserPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.ConfigDTO
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 08.04.2020
 */

object ConverterFromJsonForConfig{
  def addConfig(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForConfig().addConfig(receivedMessage)
  }

  def getConfigs(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForConfig().getConfigs(receivedMessage)
  }

  def deleteConfig(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForConfig().deleteConfig(receivedMessage)
  }

  def updateConfig(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForConfig().deleteConfig(receivedMessage)
  }
}

class ConverterFromJsonForConfig() {
  private def addConfig(receivedMessage: JsValue): Unit = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case addConfigResult : JsSuccess[ConfigDTO] => new Config().getConfigs(Some(addConfigResult.value))
      case e: JsError => println("Errors -> : " + Actions.ADD_CONFIG + JsError.toJson(e).toString())
    }
  }

  private def getConfigs(receivedMessage: JsValue): Unit = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case getConfigsResult: JsSuccess[ConfigDTO] => new UserPage().drawUserPageWithConfigPage(configDTO = Some(getConfigsResult.value))//new ConfigPage().drawAllConfigs(getConfigsResult.get)
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
