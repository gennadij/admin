package org.genericConfig.admin.client.old.config

import org.scalajs.jquery.jQuery
import play.api.libs.json.Json
import org.genericConfig.admin.shared.config.json._
import org.scalajs.dom.raw.WebSocket
import org.genericConfig.admin.shared.common.json.JsonStatus

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 15.05.2018
 */
class EditConfig(websocket: WebSocket) {
   def editConfig(configId: String, configUrl: String, userId: String) = {
    jQuery("#main").remove()
    jQuery("#status").remove()
    
    val htmlMain =  
    """<dev id='main' class='main'> 
      <p>Edit Konfiguration</p>
      configUrl <input id='inputConfigUrl' type='text'>
      <dev id='saveConfig' class='button'> Speichern </dev>
      <dev id='showConfigs' class='button'> Konfigurationen </dev>
    </dev> """
    
    jQuery(htmlMain).appendTo(jQuery("section"))
   
    jQuery(s"#showConfigs").on("click", () => showConfigs(userId))
    jQuery(s"#saveConfig").on("click", () => saveConfig(configId))
    jQuery("#inputConfigUrl").value(configUrl)
  }
  
  def saveConfig(configId: String) = {
    val configUrl = jQuery("#inputConfigUrl").value
    val jsonUpdateConfig: String = Json.toJson(JsonUpdateConfigIn(
        params = JsonUpdateConfigParams(
            configId,
            configUrl.toString()
        )
    )).toString()
    
    websocket.send(jsonUpdateConfig)
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
  
  def updateStatus(updateConfigOut: JsonUpdateConfigOut) = {
    
//    val addConfig: Option[JsonStatus] = deleteConfigOut.result.status.addConfig
//    val getConfigs: Option[JsonStatus] = deleteConfigOut.result.status.getConfigs
//    val deleteConfig: Option[JsonStatus] = deleteConfigOut.result.status.deleteConfig
    val updateConfig: Option[JsonStatus] = updateConfigOut.result.status.updateConfig
    val common: Option[JsonStatus] = updateConfigOut.result.status.common
    
    val htmlHeader = 
      s"<dev id='status' class='status'>" + 
        updateConfig.get.status + " , " + common.get.status + 
      "</dev>"
    jQuery("#status").remove()
    jQuery(htmlHeader).appendTo(jQuery("header"))
  }
}