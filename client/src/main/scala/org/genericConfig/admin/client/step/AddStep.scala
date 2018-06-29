package org.genericConfig.admin.client.step

import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import org.genericConfig.admin.shared.step.json.JsonStepIn
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.step.json.JsonStepParams
import org.genericConfig.admin.shared.step.json.JsonSelectionCriterium
import play.api.libs.json.Json
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeOut
import org.genericConfig.admin.shared.step.json.JsonStepOut
import util.CommonFunction
import util.HtmlElementIds
import org.genericConfig.admin.shared.config.json.JsonGetConfigsIn
import org.genericConfig.admin.shared.config.json.JsonGetConfigsParams


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 18.05.2018
 */
class AddStep(websocket: WebSocket) extends CommonFunction {
  
  def addStep(idToAppend: String, userId: String) = {
    
    cleanPage
    
    val htmlMain =  
    "<dev id='main' class='main'>" +
      "<p>Neuen Schritt erstellen</p>" +
      drawInputField(HtmlElementIds.inputStepNameToShowHtml, "nameToShow") +
      drawInputField(HtmlElementIds.inputSelectionCriteriumMinHtml, "Selection Criterium MIN", "number") + 
      drawInputField(HtmlElementIds.inputSelectionCriteriumMaxHtml, "Selection Criterium MAX", "number") +
      drawButton(HtmlElementIds.addStepHtml, "Speichern") +
      drawButton(HtmlElementIds.getConfigsHtml, "Konfiguration") +
    "</dev>"
    
    drawNewMain(htmlMain)
   
    jQuery(HtmlElementIds.addStepJQuery).on("click", () => saveStep(idToAppend))
    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => getConfigs(userId))
  }
  
  private def saveStep(configId: String) = {
    val nameToShow: Dynamic = jQuery(HtmlElementIds.inputStepNameToShowJQuery).value()
    val scMin: Dynamic = jQuery(HtmlElementIds.inputSelectionCriteriumMinJQuery).value()
    val scMax: Dynamic = jQuery(HtmlElementIds.inputSelectionCriteriumMaxJQuery).value()
    
    val jsonStepIn = Json.toJson(JsonStepIn(
        JsonNames.ADD_STEP,
        JsonStepParams(
            configId, //configId: String,
            "", //stepId: String,
            nameToShow.toString, //nameToShow: String,
            "first", //kind: String,
            Some(JsonSelectionCriterium(
                min = scMin.toString.toInt,
                max = scMax.toString.toInt
            )) //selectionCriterium: JsonSelectionCriterium
        )
    )).toString
    
    println("OUT -> " + jsonStepIn)
    websocket.send(jsonStepIn)
  }
  
  private def getConfigs(userId: String) = {
    val jsonGetConfigs: String  = Json.toJson(JsonGetConfigsIn(
        params = JsonGetConfigsParams(
            userId
        )
    )).toString
    
    println("OUT -> " + jsonGetConfigs)
    websocket.send(jsonGetConfigs)
  }
  
  def updateStatus(addStep: JsonStepOut) = {
    println(addStep)
    val htmlHeader = 
      s"<dev id='status' class='status'>" + 
        addStep.result.status.addStep.get.status + 
        " , " + 
        addStep.result.status.appendStep.get.status + 
        " ," + 
        addStep.result.status.common.get.status +
      "</dev>"
    jQuery("#status").remove()
    jQuery(htmlHeader).appendTo(jQuery("header"))
  }
}