package org.genericConfig.admin.configTree

import org.scalajs.jquery.jQuery
import org.scalajs.dom.raw.WebSocket
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeOut

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 26.04.2018
 */
class ConfigTree(websocket: WebSocket) {
  
  def drawConfigTree(configTree: JsonConfigTreeOut) = {
    println("ConfigTree " + configTree)
    val htmlMain =  
    """<dev id='main' class='main'> 
      <p>Konfigurationsbaum</p>
    </dev> """
        
   jQuery(htmlMain).appendTo(jQuery("section"))
  }
  
}