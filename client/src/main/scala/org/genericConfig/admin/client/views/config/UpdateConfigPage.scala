package org.genericConfig.admin.client.old.config

import org.scalajs.jquery.jQuery

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 15.05.2018
 */
class UpdateConfigPage() {
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
//    val configUrl = jQuery("#inputConfigUrl").value
//    val jsonUpdateConfig: String = Json.toJson(JsonUpdateConfigIn(
//        params = JsonUpdateConfigParams(
//            configId,
//            configUrl.toString()
//        )
//    )).toString()
//
//    websocket.send(jsonUpdateConfig)
  }
  
  def showConfigs(userId: String) = {
//    val jsonGetConfigs: String  = Json.toJson(JsonGetConfigsIn(
//        params = JsonGetConfigsParams(
//            userId
//        )
//    )).toString
//
//    println("OUT -> " + jsonGetConfigs)
//    websocket.send(jsonGetConfigs)
  }
}