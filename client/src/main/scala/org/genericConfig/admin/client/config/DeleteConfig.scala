package org.genericConfig.admin.client.config

import org.scalajs.jquery.jQuery
import org.genericConfig.admin.shared.config.json.JsonDeleteConfigOut
import org.genericConfig.admin.shared.common.json.JsonStatus
import org.scalajs.dom.raw.WebSocket
import play.api.libs.json.Json
import org.genericConfig.admin.shared.config.json.JsonGetConfigsIn
import org.genericConfig.admin.shared.config.json.JsonGetConfigsParams
import scala.scalajs.js.timers._
import org.genericConfig.admin.shared.config.json.ConfigDTO
import org.genericConfig.admin.shared.config.json.JsonDeleteConfigParams
import util.CommonFunction
import util.HtmlElementIds

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 03.05.2018
 */
class DeleteConfig(websocket: WebSocket) extends CommonFunction{
  
  def deleteConfig(configId: String, configUrl: String, userId: String) = {
    cleanPage
    
    val htmlMain =  
    "<dev id='main' class='main'>" +  
      "<p>Delete Konfiguration</p>" +
      drawButton(HtmlElementIds.deleteConfigHtml, "Delete")+
      drawButton(HtmlElementIds.getConfigsHtml, "Konfigurationen") +
    "</dev>" 
    
      drawNewMain(htmlMain)
   
    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => showConfigs(userId))
    jQuery(HtmlElementIds.deleteConfigJQuery).on("click", () => delete(configId, configUrl))
  }
  
  def delete(configId: String, configUrl: String) = {
    println("Delete")
    
    val jsonDeleteConfig: String = Json.toJson(ConfigDTO(
        params = JsonDeleteConfigParams(
            configId,
            configUrl
        )
    )).toString()
    
    websocket.send(jsonDeleteConfig)
  }
  
  def showConfigs(userId: String) = {
    val jsonGetConfigs: String  = Json.toJson(JsonGetConfigsIn(
        params = JsonGetConfigsParams(
            userId
        )
    )).toString
    
    println("OUT -> " + jsonGetConfigs)
    websocket.send(jsonGetConfigs)
  }
  
  def updateStatus(deleteConfigOut: JsonDeleteConfigOut) = {
    
    println("Status Delete Config")
//    val addConfig: Option[JsonStatus] = deleteConfigOut.result.status.addConfig
//    val getConfigs: Option[JsonStatus] = deleteConfigOut.result.status.getConfigs
    val deleteConfig: Option[JsonStatus] = deleteConfigOut.result.status.deleteConfig
//    val updateConfig: Option[JsonStatus] = deleteConfigOut.result.status.updateConfig
    val common: Option[JsonStatus] = deleteConfigOut.result.status.common
    
    val htmlHeader = 
      s"<dev id='status' class='status'>" + 
        deleteConfig.get.status + " , " + common.get.status + 
      "</dev>"
    jQuery("#status").remove()
    jQuery(htmlHeader).appendTo(jQuery("header"))
  }
}