package org.genericConfig.admin.client.config

import org.genericConfig.admin.client.views.StartPage
import org.genericConfig.admin.client.views.html.HtmlElementIds
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO, UserConfigDTO}
import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.{JQuery, jQuery}
import play.api.libs.json.Json
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.04.2018
 */
class GetConfig(webSocket: WebSocket) extends CommonFunction{

  def update(addConfigResult: ConfigDTO): Unit = {
    getConfigs(addConfigResult.result.get.userId.get)
  }

  def drawAllConfigs(getConfigsResult: ConfigDTO): Unit = {

    cleanPage

    val configurations : String = getConfigsResult.result.get.configs.get.map(config => {
      " " + drawButton(config.configId.get, config.configUrl.get) + " "
    }).mkString(" ")

    val htmlMain =
    "<dev id='main' class='main'>" +
      "<p>Konfigurationen</p>" +
      drawButton(HtmlElementIds.addConfigHtml, "New Config") +
      drawButton(HtmlElementIds.startPageHtml, "startPage") + "</br>" + "</br>" +
      configurations +
    "</dev>"

    drawNewMain(htmlMain)
    getConfigsResult.result.get.configs.get.map(config => {
      jQuery(s"#${config.configId.get}").on("click", () => drawConfig(config))
    })
    jQuery(HtmlElementIds.addConfigJQuery).on("click", () => createConfig(getConfigsResult.result.get.userId.get))
    jQuery(HtmlElementIds.startPageJQuery).on("click", () => startPage)
  }

  private def drawConfig(config : UserConfigDTO): JQuery = {
    cleanPage

    val htmlMain =
      "<dev id='main' class='main'>" +
        "<p>Konfiguration</p>" +
        drawInputField("configurationUrl", "configurationUrl", defaultText = config.configUrl.get)  + "</br> </br>" +
 //        drawInputField("configurationCourse", "configurationCourse", defaultText = config.configurationCourse.get) +
        drawButton("updateConfig", "updateConfig") +
        drawButton("deleteConfig", "deleteConfig") +
        drawButton("addStep", "addStep") +
        drawButton(HtmlElementIds.startPageHtml, "startPage") +
      "</dev>"

    drawNewMain(htmlMain)

    jQuery(HtmlElementIds.startPageJQuery).on("click", () => startPage)
  }

  private def startPage: JQuery = {
    new StartPage(webSocket).drawStartPage()
  }

  private def createConfig(userId: String): JQuery = {
    new CreateConfig(webSocket, userId).createConfig
  }

  def deleteConfig(configId: String, configUrl: String, userId: String): Unit = {
    println("delete config")
//    new DeleteConfig(webSocket).deleteConfig(configId, configUrl, userId)
  }

  private def getConfigs(userId: String): Unit = {
    println("Get Configs")
    val getConfigParams = Json.toJson(ConfigDTO(
      action = Actions.GET_CONFIGS,
      params = Some(ConfigParamsDTO(
        userId = Some(userId)
      )),
      result = None
    )).toString
    println("OUT -> " + getConfigParams)
    webSocket.send(getConfigParams)
  }
}