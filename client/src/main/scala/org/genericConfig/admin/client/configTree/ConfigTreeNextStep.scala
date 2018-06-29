package org.genericConfig.admin.client.configTree

import org.scalajs.jquery.jQuery
import org.scalajs.dom.raw.WebSocket
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeComponent
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeStep

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 30.04.2018
 */
class ConfigTreeNextStep {
  
  val numPattern = "[0-9]+".r
  
  def drowNextStep(nextStep: JsonConfigTreeStep) = {
    
    val stepIdRow = nextStep.stepId
    
    val stepId = numPattern.findAllIn(stepIdRow).toArray.mkString
    
    val stepKind = nextStep.kind
    
    val htmlMain =  
    """<dev id='main' class='main'> 
      <p>Konfigurationsbaum</p>
    </dev> """
    
    jQuery(htmlMain).appendTo(jQuery("section"))
    
    val components: Set[JsonConfigTreeComponent] = nextStep.components
    
    var htmlComponents = ""
    
    components foreach { component => 
      val componentId = numPattern.findAllIn(component.componentId).toArray.mkString
//      val nextStepId = numPattern.findAllIn(component.nextStepId.get).toArray.mkString
      
      htmlComponents = htmlComponents + 
        "<div id='" + componentId + "' class='component'>" + 
          "ID " + component.componentId + 
          "</br>" + 
          "Kind " + component.kind +
          "<dev id='editComponent" + componentId + "' class='button'>" + 
            "Edit" + 
          "</dev>" + 
          "<dev id='removeComponent" + componentId + "' class='button'>" + 
            "Remove" + 
          "</dev>" + 
//          "<div id='" + nextStepId + "' class='nextStep'>" +
//            "nextStep " + component.nextStepId.get  +
//            "<dev id='selectNextStep" + nextStepId + "' class='button'>" +
              "nextStep" + 
            "</dev>" + 
          "</div>" + 
        "</div>"
    }
    
    val htmlStep =  
      "<dev> " +
        "<div id='" + stepId + "' class='step'>" +
          "ID " + stepIdRow  + 
          "<dev id='editStep" + stepId + "'  class='button'>" + 
            "Edit" + 
          "</dev>" + 
          "<dev id='removeStep" + stepId + "' class='button'>" + 
            "Remove" + 
          "</dev>" + 
          " </br>" + 
          "Kind " + stepKind  + 
          htmlComponents + 
        "</div>" +
      "</dev> "
      
      jQuery(htmlStep).appendTo(jQuery("#main"))
      
      jQuery(s"#editStep$stepId").on("click", () => editStep())
  }
  
  
  private def editComponent() = {
    println("editComponent")
  }
  
  private def selectNextStep(nextStep: JsonConfigTreeStep) = {
    println("selectNExtStep" + nextStep)
    
    jQuery("#main").remove
    
    new ConfigTreeNextStep().drowNextStep(nextStep)
  }
  
  private def editStep() = {
    println("editStep")
  }
}