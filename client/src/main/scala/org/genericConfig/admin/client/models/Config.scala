package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.controllers.websocket.WebSocketListner
import org.genericConfig.admin.client.views.config.{AddConfigPage, ConfigPage}
import org.genericConfig.admin.client.views.html.HtmlElementIds
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO, UserConfigDTO}
import org.genericConfig.admin.shared.user.UserDTO
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 11.04.2020
  */

class Config {
  def showConfigs(param : Option[Any]): Unit = {
    param match {
      case Some(configDTO) => configDTO match {
        case configDTO : ConfigDTO => new ConfigPage().drawAllConfigs(configDTO)
        case _ => println("Config.showConfigs(param not ConfigDTO")
      }
      case None => println("Config.showConfigs(param == None")
    }
  }

  def showAddConfigPage(param: Option[Any]): Any = {
    param match {
      case Some(configDTO) => configDTO match {
        case configDTO : ConfigDTO => new AddConfigPage().drawAddConfigPage(configDTO)
        case _ => println("Config.showConfigs(param not ConfigDTO")
      }
      case None => println("Config.showConfigs(param == None")
    }
  }

  def addConfig(param : Option[Any]) = {
        val configUrl : String = jQuery(HtmlElementIds.inputConfigUrlJQuery).value().toString
        val configurationCourse : String = jQuery(HtmlElementIds.inputConfigUrlJQuery).value().toString
        val userId = param.get.asInstanceOf[ConfigDTO].result.get.userId
        val addConfig: String = Json.toJson(ConfigDTO(
            action = Actions.ADD_CONFIG,
            params = Some(ConfigParamsDTO(
              userId = userId,
              configUrl = Some(configUrl),
              configurationCourse = Some("sequence")
            )),
          result = None
        )).toString
        println("OUT -> " + addConfig)
        WebSocketListner.webSocket.send(addConfig)
  }

  def getConfigs(param: Option[Any]): Unit = {
    val userId: Option[String] = param.get match {
      case userDTO : UserDTO => userDTO.result.get.userId
      case configDTO : ConfigDTO => configDTO.result.get.userId
    }
    val getConfigParams = Json.toJson(ConfigDTO(
      action = Actions.GET_CONFIGS,
      params = Some(ConfigParamsDTO(
        userId = userId
      )),
      result = None
    )).toString
    println("OUT -> " + getConfigParams)
    WebSocketListner.webSocket.send(getConfigParams)
  }

  def showAllConfigsInUserPage(param: Option[Any]) : Unit = {
    new ConfigPage().drawAllConfigsInUserPage(Some(param.get.asInstanceOf[ConfigDTO]))
  }

  def showConfigPage(param: Option[Any]) : Unit = {
    new ConfigPage().drawConfigPage(param.get.asInstanceOf[UserConfigDTO])
  }
}
