package org.genericConfig.admin.client.config

import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import org.genericConfig.admin.configTree.ConfigTree
import org.genericConfig.admin.shared.config.json.JsonGetConfigsOut
import org.genericConfig.admin.shared.common.json.JsonStatus
import org.genericConfig.admin.shared.config.json.JsonDeleteConfigIn
import org.genericConfig.admin.shared.config.json.JsonDeleteConfigParams
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.04.2018
 */
class GetConfig(websocket: WebSocket) {
  
  val numPattern = "[0-9]+".r
  
  def drowAllConfigs(getConfigsOut: JsonGetConfigsOut) = {
    
    jQuery("#main").remove
    jQuery("#status").remove()
    
    val addConfig: Option[JsonStatus] = getConfigsOut.result.status.addConfig
    val getConfigs: Option[JsonStatus] = getConfigsOut.result.status.getConfigs
//    val deleteConfig: Option[JsonStatus] = getConfigsOut.result.status.deleteConfig
    val updateConfig: Option[JsonStatus] = getConfigsOut.result.status.updateConfig
    val common: Option[JsonStatus] = getConfigsOut.result.status.common
    
    val htmlHeader = 
      s"<dev id='status' class='status'>" + 
        getConfigs.get.status + " , " + common.get.status + 
      "</dev>"
  
    jQuery(htmlHeader).appendTo(jQuery("header"))
    
    val htmlMain =  
    """<dev id='main' class='main'> 
      <p>Konfigurationen</p>
      <dev id='createConfig' class='button'> New Config </dev>
    </dev> """
        
   jQuery(htmlMain).appendTo(jQuery("section"))
   
   
   jQuery(s"#createConfig").on("click", () => createConfig(getConfigsOut.result.userId))
   
   getConfigsOut.result.configs foreach { config =>
     
     val configId: String = numPattern.findAllIn(config.configId).toArray.mkString
     
     val htmlConfig =  
       "<dev id='" + configId + "' class='config'> " +
         "<div id='text_for_config'>" + config.configId + " " + config.configUrl + 
           "<dev id='showConfig" + configId + "' class='button'>" + 
             "Show" + 
            "</dev>" + 
            "<dev id='editConfig" + configId + "' class='button'>" + 
              "Edit" + 
            "</dev>" +
            "<dev id='deleteConfig" + configId + "' class='button'>" + 
              "Delete" + 
            "</dev>" +
           "</div>" +
       "</dev> "
           
      jQuery(htmlConfig).appendTo(jQuery("#main"))
      jQuery(s"#showConfig$configId").on("click", () => showConfig(configId, config.configId))
      
      jQuery(s"#editConfig$configId").on("click", () => 
        editConfig(config.configId, config.configUrl, getConfigsOut.result.userId))
      
      jQuery(s"#deleteConfig$configId").on("click", () => 
        deleteConfig(config.configId, config.configUrl, getConfigsOut.result.userId))
      
    }
    
    
  }
  
  def showConfig(configId: String, configIdRaw: String) = {
    println("selected ConfigId" + configIdRaw)
    val jsonGetConfigtree = "{\"json\":\"ConfigTree\",\"params\":{\"configId\":\"" + configIdRaw + "\"}}"
    println("websocket  send " + jsonGetConfigtree)
    websocket.send(jsonGetConfigtree)
    jQuery("#main").remove()
  }
  
  def createConfig(userId: String) = {
    println("Create Config")
    new CreateConfig(websocket, userId).createConfig
  }
  
  def editConfig(configId: String, configUrl: String, userId: String) = {
    println("edit Config")
    new EditConfig(websocket).editConfig(configId, configUrl, userId)
  }
  
  def deleteConfig(configId: String, configUrl: String, userId: String) = {
    println("delete config")
    new DeleteConfig(websocket).deleteConfig(configId, configUrl, userId)
  }
}