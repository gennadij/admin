package org.genericConfig.admin.client.config

import org.genericConfig.admin.client.start.StartPage
import org.genericConfig.admin.shared.config.ConfigDTO
import org.genericConfig.admin.shared.config.json.JsonGetConfigsOut
import org.genericConfig.admin.shared.configTree.json.{JsonConfigTreeIn, JsonConfigTreeParams}
import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json
import util.{CommonFunction, HtmlElementIds}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.04.2018
 */
class GetConfig(webSocket: WebSocket) extends CommonFunction{



  def drawAllConfigs(getConfigsResult: ConfigDTO): Unit = {

    cleanPage

//    val status: String = getConfigsParams.result.status.getConfigs.get.status + " , " +
//        getConfigsParams.result.status.common.get.status
//
//    drawNewStatus(status)
//
    val configurationen : String = getConfigsResult.result.get.configs.get.map(config => {
      " " + drawButton(config.configId.get, config.configUrl.get) + " "
    }).toString()
    println(configurationen)
    val htmlMain =
    "<dev id='main' class='main'>" +
      "<p>Konfigurationen</p>" +
      drawButton(HtmlElementIds.addConfigHtml, "New Config") +
      drawButton(HtmlElementIds.startPageHtml, "startPage") +
    "</dev>"

    drawNewMain(htmlMain)

    jQuery(HtmlElementIds.addConfigJQuery).on("click", () => createConfig(getConfigsResult.result.get.userId.get))
    jQuery(HtmlElementIds.startPageJQuery).on("click", () => startPage)

//    drawConfigs(getConfigsParams)
  }

  private def drawConfigs(getConfigsOut: JsonGetConfigsOut) = {
    getConfigsOut.result.configs foreach { config =>

      val htmlConfig =
        "<dev id='" + config.configId + "' class='config'> " +
          "<div id='text_for_config'>" + config.configId + " || " + config.configUrl + "</dev>" +
          drawButton(HtmlElementIds.getConfigsHtml + config.configId, "Show") +
          drawButton(HtmlElementIds.updateConfigHtml + config.configId, "Edit") +
          drawButton(HtmlElementIds.deleteConfigHtml + config.configId, "Delete") +
        "</dev> "

      jQuery(htmlConfig).appendTo(jQuery(HtmlElementIds.mainJQuery))

      jQuery(HtmlElementIds.getConfigsJQuery + config.configId).on("click", () => showConfig(config.configId))

      jQuery(HtmlElementIds.updateConfigJQuery + config.configId).on("click", () =>
        editConfig(config.configId, config.configUrl, getConfigsOut.result.userId))

      jQuery(HtmlElementIds.deleteConfigJQuery + config.configId).on("click", () =>
        deleteConfig(config.configId, config.configUrl, getConfigsOut.result.userId))
    }
  }

  private def showConfig(configId: String) = {

    val jsonConfigTree: String = Json.toJson(
        JsonConfigTreeIn(
            params = JsonConfigTreeParams(
                configId
            )
        )
    ).toString

    println("IN " + jsonConfigTree)
    webSocket.send(jsonConfigTree)
    jQuery("#main").remove()
  }

  private def startPage = {
    new StartPage(webSocket).drawStartPage()
  }

  private def createConfig(userId: String) = {
    new CreateConfig(webSocket, userId).createConfig
  }

  private def editConfig(configId: String, configUrl: String, userId: String) = {
    println("edit Config")
    new EditConfig(webSocket).editConfig(configId, configUrl, userId)
  }

  def deleteConfig(configId: String, configUrl: String, userId: String) = {
    println("delete config")
    new DeleteConfig(webSocket).deleteConfig(configId, configUrl, userId)
  }
}