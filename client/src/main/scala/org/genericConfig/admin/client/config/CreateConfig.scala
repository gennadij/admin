package org.genericConfig.admin.client.config

import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import com.fasterxml.jackson.annotation.JsonCreator
import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonStatus
import org.genericConfig.admin.shared.config.json._
import util.CommonFunction
import util.HtmlElementIds

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.04.2018
 */
class CreateConfig(websocket: WebSocket, userId: String) extends CommonFunction {
  
  def createConfig = {
    
    cleanPage
    
    val htmlMain =  
    "<dev id='main' class='main'>" +
      "<p>Create Konfiguration</p>" +
      drawInputField(HtmlElementIds.inputGonfigUrlHtml, "configUrl") +
      drawButton(HtmlElementIds.addConfigHtml, "save") + 
      drawButton(HtmlElementIds.getConfigsHtml, "Konfigurationen") +
    "</dev>" 
    
      drawNewMain(htmlMain)
   
    jQuery(HtmlElementIds.addConfigJQuery).on("click", () => saveConfig)
    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => showConfigs)
  }

  def saveConfig = {
    val configUrl = jQuery("#inputConfigUrl").value()
    
    val jsonCreateConfig: String = Json.toJson(JsonAddConfigIn(
        params = JsonAddConfigParams(
            userId,
            configUrl.toString
        )
    )).toString
    
    websocket.send(jsonCreateConfig)
  }
  
  def showConfigs = {
    val jsonGetConfigs: String  = Json.toJson(JsonGetConfigsIn(
        params = JsonGetConfigsParams(
            userId
        )
    )).toString
    
    println("OUT -> " + jsonGetConfigs)
    websocket.send(jsonGetConfigs)
  }
  
  def updateStatus(createConfigOut: JsonAddConfigOut) = {
    val addConfig: Option[JsonStatus] = createConfigOut.result.status.addConfig
//    val getConfigs: Option[JsonStatus] = createConfigOut.result.status.getConfigs
//    val deleteConfig: Option[JsonStatus] = createConfigOut.result.status.deleteConfig
//    val updateConfig: Option[JsonStatus] = createConfigOut.result.status.updateConfig
    val common: Option[JsonStatus] = createConfigOut.result.status.common
    
    val htmlHeader = 
      s"<dev id='status' class='status'>" + 
        addConfig.get.status + " , " + common.get.status + 
      "</dev>"
    jQuery("#status").remove()
    jQuery(htmlHeader).appendTo(jQuery("header"))
  }
}

