package org.genericConfig.admin.client.config

import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import com.fasterxml.jackson.annotation.JsonCreator
import org.genericConfig.admin.shared.config.json.JsonCreateConfigIn
import org.genericConfig.admin.shared.config.json.JsonCreateConfigParams
import play.api.libs.json.Json
import org.genericConfig.admin.shared.config.json.JsonGetConfigsIn
import org.genericConfig.admin.shared.config.json.JsonGetConfigsParams

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.04.2018
 */
class CreateConfig(websocket: WebSocket, userId: String) {
  
  def createConfig = {
    
    jQuery("#main").remove()
    
    val htmlMain =  
    """<dev id='main' class='main'> 
      <p>Create Konfiguration</p>
      configUrl <input id='inputConfigUrl' type='text'>
      <dev id='saveConfig' class='button'> Speichern </dev>
      <dev id='showConfigs' class='button'> Konfigurationen </dev>
    </dev> """
    
    jQuery(htmlMain).appendTo(jQuery("section"))
   
    jQuery(s"#saveConfig").on("click", () => saveConfig)
    jQuery(s"#showConfigs").on("click", () => showConfigs)
  }

  def saveConfig = {
    val configUrl_1 = jQuery("#inputConfigUrl").value()
    println("Save config " + configUrl_1)
    
    val jsonCreateConfig_1 = JsonCreateConfigIn(
        params = JsonCreateConfigParams(
            userId,
            configUrl_1.toString
        )
    )
    
    println("CreateConfigIn " + Json.toJson(jsonCreateConfig_1))
    
    websocket.send(Json.toJson(jsonCreateConfig_1).toString())
  }
  
  def showConfigs = {
    val jsonGetConfigs = JsonGetConfigsIn(
        params = JsonGetConfigsParams(
            userId
        )
    )
    
    websocket.send(Json.toJson(jsonGetConfigs).toString)
  }
  
}

