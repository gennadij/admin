package org.genericConfig.admin.client.old.config

import org.genericConfig.admin.client.views.html.HtmlElementIds
import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import util.CommonFunction

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
    
    val jsonDeleteConfig: String = ""
//      Json.toJson(ConfigDTO(
//        params = JsonDeleteConfigParams(
//            configId,
//            configUrl
//        )
//    )).toString()
    
    websocket.send(jsonDeleteConfig)
  }
  
  def showConfigs(userId: String) = {
    val jsonGetConfigs: String  = ""
//      Json.toJson(JsonGetConfigsIn(
//        params = JsonGetConfigsParams(
//            userId
//        )
//    )).toString
    
    println("OUT -> " + jsonGetConfigs)
    websocket.send(jsonGetConfigs)
  }
  
  def updateStatus(deleteConfigOut: Any) = {
    
    println("Status Delete Config")
//    val addConfig: Option[JsonStatus] = deleteConfigOut.result.status.addConfig
//    val getConfigs: Option[JsonStatus] = deleteConfigOut.result.status.getConfigs
//    val deleteConfig: Option[JsonStatus] = deleteConfigOut.result.status.deleteConfig
//    val updateConfig: Option[JsonStatus] = deleteConfigOut.result.status.updateConfig
//    val common: Option[JsonStatus] = deleteConfigOut.result.status.common
    
    val htmlHeader = ""
//      s"<dev id='status' class='status'>" +
//        deleteConfig.get.status + " , " + common.get.status +
//      "</dev>"
    jQuery("#status").remove()
    jQuery(htmlHeader).appendTo(jQuery("header"))
  }
}