package org.genericConfig.admin.configTree

import org.scalajs.jquery.jQuery
import org.scalajs.dom.raw.WebSocket
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeOut
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeComponent
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeStep
import org.genericConfig.admin.shared.configTree.status._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 26.04.2018
 */
class ConfigTree(websocket: WebSocket) {
  
  val numPattern = "[0-9]+".r
  
  def drawConfigTree(configTree: JsonConfigTreeOut) = {
    
    configTree.result.status.getConfigTree.get.status match {
      case status if status == GetConfigTreeSuccess().status => 
        updateStatus(status, configTree.result.status.common.get.status)
      case status if status == GetConfigTreeEmpty().status => 
        updateStatus(status, configTree.result.status.common.get.status)
      case status if status == GetConfigTreeError().status => 
        updateStatus(status, configTree.result.status.common.get.status)
      
    }
    
    //TODO wrapper in share move
    
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
          "ID " + component.componentId + 
          "</br>" + 
          "Kind " + component.kind +
          "<dev id='editComponent" + componentId + "' class='button'>" + 
            "Edit" + 
          "</dev>" + 
          "<dev id='removeComponent" + componentId + "' class='button'>" + 
            "Remove" + 
          "</dev>" + 
          "<div id='" + nextStepId + "' class='nextStep'>" + 
            "nextStep " + component.nextStepId.get  + 
            "<dev id='selectNextStep" + nextStepId + "' class='button'>" + 
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
    
    val componentIds = components map {c => numPattern.findAllIn(c.componentId).toArray.mkString}
    
    componentIds foreach { componentId => 
      jQuery(s"#editComponent$componentId").on("click", () => editComponent())
    }
    
    val nextSteps: Set[(String, JsonConfigTreeStep)] = components map {c => 
      c.nextStep match {
        case Some(nextStep) => (numPattern.findAllIn(c.nextStepId.get).toArray.mkString, c.nextStep.get)
        case None => (numPattern.findAllIn(c.nextStepId.get).toArray.mkString, null)
      }
    }
    
    nextSteps foreach { nextStepId =>
      val nSId = nextStepId._1
      jQuery(s"#selectNextStep$nSId").on("click", () => selectNextStep(nextStepId._2))
    }
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
  
  def updateStatus(getConfigTreeStatus: String, commonStatus: String) = {
    
    val htmlHeader = 
      s"<dev id='status' class='status'>" + 
        getConfigTreeStatus + " , " + commonStatus + 
      "</dev>"
    jQuery("#status").remove()
    jQuery(htmlHeader).appendTo(jQuery("header"))
  }
}