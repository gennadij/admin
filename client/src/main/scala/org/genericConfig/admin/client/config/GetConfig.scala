package org.genericConfig.admin.client.config

import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import org.genericConfig.admin.configTree.ConfigTree
import org.genericConfig.admin.shared.config.json.JsonGetConfigsOut
import org.genericConfig.admin.shared.common.json.JsonStatus
import org.genericConfig.admin.shared.config.json.JsonDeleteConfigIn
import org.genericConfig.admin.shared.config.json.JsonDeleteConfigParams
import play.api.libs.json.Json
import util.CommonFunction
import scala.util.matching.Regex
import util.HtmlElementIds
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeIn
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeParams

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.04.2018
 */
class GetConfig(websocket: WebSocket) extends CommonFunction{


  
  def drawAllConfigs(getConfigsOut: JsonGetConfigsOut): Unit = {

    cleanPage

    val status: String = getConfigsOut.result.status.getConfigs.get.status + " , " + 
        getConfigsOut.result.status.common.get.status
    
    drawNewStatus(status)
    
    val htmlMain =  
    "<dev id='main' class='main'>" + 
      "<p>Konfigurationen</p>" + 
      drawButton(HtmlElementIds.addConfigHtml, "New Config") + 
    "</dev>"
    
    drawNewMain(htmlMain)
   
    jQuery(HtmlElementIds.addConfigJQuery).on("click", () => createConfig(getConfigsOut.result.userId))
  
    drawConfigs(getConfigsOut)
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
    websocket.send(jsonConfigTree)
    jQuery("#main").remove()
  }
  
  private def createConfig(userId: String) = {
    println("Create Config")
    new CreateConfig(websocket, userId).createConfig
  }
  
  private def editConfig(configId: String, configUrl: String, userId: String) = {
    println("edit Config")
    new EditConfig(websocket).editConfig(configId, configUrl, userId)
  }
  
  def deleteConfig(configId: String, configUrl: String, userId: String) = {
    println("delete config")
    new DeleteConfig(websocket).deleteConfig(configId, configUrl, userId)
  }
}