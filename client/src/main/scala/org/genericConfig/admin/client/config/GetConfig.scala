package org.genericConfig.admin.client.config

import org.genericConfig.admin.shared.json.config.JsonGetConfigsOut
import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import org.genericConfig.admin.configTree.ConfigTree

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.04.2018
 */
class GetConfig(websocket: WebSocket) {
  
  val numPattern = "[0-9]+".r
  
  def drowAllConfigs(getConfigsOut: JsonGetConfigsOut) = {
    
  
    val htmlMain =  
    """<dev id='main' class='main'> 
      <p>Konfigurationen</p>
    </dev> """
        
   jQuery(htmlMain).appendTo(jQuery("section"))
   
   getConfigsOut.result.configs foreach { config =>
     
     val configId: String = numPattern.findAllIn(config.configId).toArray.mkString
     
     val htmlConfig =  
       "<dev id='" + configId + "' class='config'> " +
         "<div id='text_for_config'>" + config.configId + " " + config.configUrl + "</div>" +
       "</dev> "
     
     jQuery(htmlConfig).appendTo(jQuery("#main"))
     jQuery(s"#$configId").on("click", () => selectConfig(configId))
    }
  }
  
  def selectConfig(configId: String) = {
    println("selected ConfigId" + configId)
    jQuery("#main").remove()
    new ConfigTree(websocket).drawConfigTree()
  }
}