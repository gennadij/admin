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

  cleanPage
  
  def drawAllConfigs(getConfigsOut: JsonGetConfigsOut): Unit = {
    
    val status: String = getConfigsOut.result.status.getConfigs.get.status + " , " + 
        getConfigsOut.result.status.common.get.status
    
    drawNewStatus(status)
    
    val htmlMain =  
    """<dev id='main' class='main'> 
      <p>Konfigurationen</p>
      <dev id='createConfig' class='button'> New Config </dev>
    </dev> """
    
    drawNewMain(htmlMain)
    
    jQuery(htmlMain).appendTo(jQuery("section"))
   
    jQuery(s"#createConfig").on("click", () => createConfig(getConfigsOut.result.userId))
  
    drawConfigs(getConfigsOut)
  }
  
  private def drawConfigs(getConfigsOut: JsonGetConfigsOut) = {
    getConfigsOut.result.configs foreach { config =>
     
     val configId: String = prepareIdForHtml(config.configId)
     
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
           
      jQuery(htmlConfig).appendTo(jQuery(HtmlElementIds.main))
      
      jQuery(s"#showConfig$configId").on("click", () => showConfig(configId, config.configId))
      
      jQuery(s"#editConfig$configId").on("click", () => 
        editConfig(config.configId, config.configUrl, getConfigsOut.result.userId))
      
      jQuery(s"#deleteConfig$configId").on("click", () => 
        deleteConfig(config.configId, config.configUrl, getConfigsOut.result.userId))
    }
  }
  
  private def showConfig(configId: String, configIdRaw: String) = {
    
    val jsonConfigTree: String = Json.toJson(
        JsonConfigTreeIn(
            params = JsonConfigTreeParams(
                configIdRaw
            )
        )
    ).toString
    
    println("-> " + jsonConfigTree)
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