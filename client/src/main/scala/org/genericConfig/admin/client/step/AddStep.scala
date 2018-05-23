package org.genericConfig.admin.client.step

import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import org.genericConfig.admin.shared.step.json.JsonStepIn
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.step.json.JsonStepParams
import org.genericConfig.admin.shared.step.json.JsonSelectionCriterium


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 18.05.2018
 */
class AddStep(webSocket: WebSocket) {
  
  def addStep = {
    
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
   
    jQuery(s"#saveStep").on("click", () => saveStep)
    jQuery(s"#showConfig").on("click", () => showConfig)
  }
  
  private def saveStep = {
    println("saveStep")
    
    val jsonStepIn = JsonStepIn(
        JsonNames.ADD_FIRST_STEP,
        JsonStepParams(
            "", //configId: String,
            "", //componentId: String,
            "", //stepId: String,
            "", //nameToShow: String,
            "", //kind: String,
            JsonSelectionCriterium(
                1,
                1
            ), //selectionCriterium: JsonSelectionCriterium
        )
    )
  }
  
  private def showConfig = {
    println("showConfig")
  }
}