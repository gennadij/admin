package org.genericConfig.admin.controllers.admin

import org.genericConfig.admin.models.logic.Config
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.ConfigDTO
import play.api.libs.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann ${date}
 */
trait WrapperForConfig extends WrapperCommon {
  def addConfig(receivedMessage: JsValue): JsValue = {
    val addConfigParams: JsResult[ConfigDTO] = Json.fromJson[ConfigDTO](receivedMessage)
    addConfigParams match {
      case _: JsSuccess[ConfigDTO] => Json.toJson(Config.addConfig(addConfigParams.get))
      case e: JsError => jsonError(Actions.ADD_CONFIG, e)
    }
  }

  def deleteConfig(receivedMessage: JsValue): JsValue = {
    val deleteConfigParams: JsResult[ConfigDTO] = Json.fromJson[ConfigDTO](receivedMessage)
    deleteConfigParams match {
      case _: JsSuccess[ConfigDTO] => Json.toJson(Config.deleteConfig(deleteConfigParams.get))
      case e: JsError => jsonError(Actions.DELETE_CONFIG, e)
    }
  }

  def getConfigs(receivedMessage: JsValue): JsValue = {
    val getConfigsParams: JsResult[ConfigDTO] = Json.fromJson[ConfigDTO](receivedMessage)
    getConfigsParams match {
      case _: JsSuccess[ConfigDTO] => Json.toJson(Config.getConfigs(getConfigsParams.get))
      case e: JsError => jsonError(Actions.GET_CONFIGS, e)
    }
  }

  def updateConfig(receivedMessage: JsValue): JsValue = {
    val updateConfigParams: JsResult[ConfigDTO] = Json.fromJson[ConfigDTO](receivedMessage)
    updateConfigParams match {
      case s: JsSuccess[ConfigDTO] => Json.toJson(Config.updateConfig(updateConfigParams.get))
      case e: JsError => jsonError(Actions.UPDATE_CONFIG, e)
    }
  }
}
