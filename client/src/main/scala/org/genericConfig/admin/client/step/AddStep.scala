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


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 18.05.2018
 */
class AddStep(websocket: WebSocket) {
  
  def addStep(configTree: JsonConfigTreeOut) = {
    
    jQuery("#main").remove()
    
    val htmlMain =  
    """<dev id='main' class='main'> 
      <p>Neuen Schritt erstellen</p>
      nameToShow <input id='inputStepNameToShow' type='text'> </br> </br>
      Selection Criterium MIN <input id='inputSelectionCriteriumMin' type='text'> </br> </br>
      Selection Criterium MAX <input id='inputSelectionCriteriumMax' type='text'> </br> </br>
      <dev id='saveStep' class='button'> Speichern </dev>
      <dev id='showConfig' class='button'> Konfiguration </dev>
    </dev> """
    
    jQuery(htmlMain).appendTo(jQuery("section"))
   
    jQuery(s"#saveStep").on("click", () => saveStep(configTree.result.configId.get))
    jQuery(s"#showConfig").on("click", () => showConfigTree(configTree.result.configId.get))
  }
  
  private def saveStep(configId: String) = {
    val nameToShow = jQuery("#inputStepNameToShow").value()
    val scMin = jQuery("#inputSelectionCriteriumMin").value()
    val scMax = jQuery("#inputSelectionCriteriumMax").value()
    
    println("saveStep " + nameToShow)
    println("saveStep " + scMin)
    println("saveStep " + scMax)
    
    val jsonStepIn = Json.toJson(JsonStepIn(
        JsonNames.ADD_FIRST_STEP,
        JsonStepParams(
            configId, //configId: String,
            "", //componentId: String,
            "", //stepId: String,
            nameToShow.toString, //nameToShow: String,
            "first", //kind: String,
            Some(JsonSelectionCriterium(
                scMin.toString.toInt,
                scMax.toString.toInt
            )) //selectionCriterium: JsonSelectionCriterium
        )
    )).toString
    
    println("OUT -> " + jsonStepIn)
    websocket.send(jsonStepIn)
  }
  
  private def showConfigTree(configId: String) = {
    println("showConfig")
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