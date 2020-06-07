package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.models.logic.Config
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.ConfigDTO
import play.api.libs.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann ${date}
 */
trait ConverterJsonDTOForConfig extends ConverterJsonDTOForCommon {
  private[converter] def addConfig(receivedMessage: JsValue): JsValue = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case addConfigParams : JsSuccess[ConfigDTO] => Json.toJson(Config.addConfig(addConfigParams.value))
      case e: JsError => jsonError(Actions.ADD_CONFIG, e)
    }
  }

  private[converter] def deleteConfig(receivedMessage: JsValue): JsValue = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case deleteConfigParams : JsSuccess[ConfigDTO] => Json.toJson(Config.deleteConfig(deleteConfigParams.value))
      case e: JsError => jsonError(Actions.DELETE_CONFIG, e)
    }
  }

  private[converter] def getConfigs(receivedMessage: JsValue): JsValue = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case getConfigsParams : JsSuccess[ConfigDTO] => Json.toJson(Config.getConfigs(getConfigsParams.value))
      case e: JsError => jsonError(Actions.GET_CONFIGS, e)
    }
  }

  private[converter] def updateConfig(receivedMessage: JsValue): JsValue = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case updateConfigParams : JsSuccess[ConfigDTO] => Json.toJson(Config.updateConfig(updateConfigParams.value))
      case e: JsError => jsonError(Actions.UPDATE_CONFIG, e)
    }
  }

    private[converter] def configGraph(receivedMessage: JsValue): JsValue = {
//      Json.fromJson[JsonConfigTreeIn](receivedMessage) match {
//        case configTreeIn: JsSuccess[JsonConfigTreeIn] => Json.toJson(admin.getConfigTree(configTreeIn.get))
//        case e : JsError => jsonError(JsonNames.CONFIG_TREE, e)
//      }
      ???
    }
}
