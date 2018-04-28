package org.genericConfig.admin.configTree

import org.scalajs.jquery.jQuery
import org.scalajs.dom.raw.WebSocket
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeOut
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeComponent

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 26.04.2018
 */
class ConfigTree(websocket: WebSocket) {
  
  val numPattern = "[0-9]+".r
  
  def drawConfigTree(configTree: JsonConfigTreeOut) = {
    
    val stepIdRow = configTree.result.step.get.stepId
    
    val stepId = numPattern.findAllIn(stepIdRow).toArray.mkString
    
    val stepKind = configTree.result.step.get.kind
    
    val htmlMain =  
    """<dev id='main' class='main'> 
      <p>Konfigurationsbaum</p>
    </dev> """
    
    jQuery(htmlMain).appendTo(jQuery("section"))
    
    val components: Set[JsonConfigTreeComponent] = configTree.result.step.get.components
    
    var htmlComponents = ""
    
    components foreach { component => 
      val componentId = numPattern.findAllIn(component.componentId).toArray.mkString
      val nextStepId = numPattern.findAllIn(component.nextStepId.get).toArray.mkString
      
      
      
      htmlComponents = htmlComponents + 
        "<div id='" + componentId + "' class='component'>" + 
          "ID " + component.componentId + "</br> Kind " + component.kind + 
          "<div id='" + nextStepId + "' class='nextStep'>" + 
            "nextStep " + component.nextStepId.get  + 
          "</div>" + 
        "</div>"
    }
    
    
    val htmlStep =  
       "<dev> " +
         "<div id='" + stepId + "' class='step'> ID " + stepIdRow  + " </br> Kind " + stepKind  + htmlComponents + "</div>" +
       "</dev> "
         
    jQuery(htmlStep).appendTo(jQuery("#main"))
    
    jQuery(s"#325").on("click", () => selectComponent())
    jQuery(s"#25101").on("click", () => selectNextStep())
    
    jQuery(s"#$stepId").on("click", () => selectStep())
  }
  
  def selectComponent() = {
    println("selectComponent")
  }
  
  def selectNextStep() = {
    println("selectNExtStep")
  }
  
  def selectStep() = {
    println("selectStep")
  }
}