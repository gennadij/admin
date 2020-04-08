package org.genericConfig.admin.client.config

import org.genericConfig.admin.client.views.html.HtmlElementIds
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.{JQuery, jQuery}
import play.api.libs.json.Json
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.04.2018
 */
class CreateConfig(webSocket: WebSocket, userId: String) extends CommonFunction {

  def createConfig: JQuery = {

    cleanPage

    val htmlMain =
    "<dev id='main' class='main'>" +
      "<p>Create Konfiguration</p>" +
      drawInputField(HtmlElementIds.inputConfigUrlHtml, "configUrl") +
      drawInputField(HtmlElementIds.inputConfigurationCourseHtml, "configurationCourse") +
      drawButton(HtmlElementIds.addConfigHtml, "save") +
      drawButton(HtmlElementIds.getConfigsHtml, "Konfigurationen") +
    "</dev>"

      drawNewMain(htmlMain)

    jQuery(HtmlElementIds.addConfigJQuery).on("click", () => saveConfig)
    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => showConfigs)
  }

  def saveConfig = {
    val configUrl : String = jQuery(HtmlElementIds.inputConfigUrlJQuery).value().toString
    val configurationCourse : String = jQuery(HtmlElementIds.inputConfigUrlJQuery).value().toString
    val addConfig: String = Json.toJson(ConfigDTO(
        action = Actions.ADD_CONFIG,
        params = Some(ConfigParamsDTO(
          userId = Some(userId),
          configUrl = Some(configUrl),
          configurationCourse = Some("sequence")
        )),
      result = None
    )).toString
    println("OUT -> " + addConfig)
    webSocket.send(addConfig)
  }

  def showConfigs = {
//    val jsonGetConfigs: String  = Json.toJson(JsonGetConfigsIn(
//        params = JsonGetConfigsParams(
//            userId
//        )
//    )).toString

//    println("OUT -> " + jsonGetConfigs)
//    webSocket.send(jsonGetConfigs)
  }
  
  def updateStatus(createConfigOut: ConfigDTO) = {
//    val addConfig: Option[JsonStatus] = createConfigOut.result.status.addConfig
////    val getConfigs: Option[JsonStatus] = createConfigOut.result.status.getConfigs
////    val deleteConfig: Option[JsonStatus] = createConfigOut.result.status.deleteConfig
////    val updateConfig: Option[JsonStatus] = createConfigOut.result.status.updateConfig
//    val common: Option[JsonStatus] = createConfigOut.result.status.common
//
//    val htmlHeader =
//      s"<dev id='status' class='status'>" +
//        addConfig.get.status + " , " + common.get.status +
//      "</dev>"
//    jQuery("#status").remove()
//    jQuery(htmlHeader).appendTo(jQuery("header"))
  }
}

