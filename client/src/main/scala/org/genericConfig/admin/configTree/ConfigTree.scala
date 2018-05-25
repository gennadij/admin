package org.genericConfig.admin.configTree

import org.scalajs.jquery.jQuery
import org.scalajs.dom.raw.WebSocket
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeOut
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeComponent
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeStep
import org.genericConfig.admin.shared.configTree.status._
import org.genericConfig.admin.shared.config.json.JsonGetConfigsIn
import org.genericConfig.admin.shared.config.json.JsonGetConfigsParams
import play.api.libs.json.Json
import org.genericConfig.admin.client.step.AddStep
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 26.04.2018
 */
class ConfigTree(websocket: WebSocket) extends CommonFunction{

  cleanPage
  
  def drawConfigTree(configTree: JsonConfigTreeOut) = {
    
    configTree.result.status.getConfigTree.get.status match {
      case status if status == GetConfigTreeSuccess().status => {
        updateStatus(status, configTree.result.status.common.get.status)
        drawConfigTreeNotEmpty(configTree)
      }
      case status if status == GetConfigTreeEmpty().status => 
        updateStatus(status, configTree.result.status.common.get.status)
        //TODO GetConfigTree need configId
        drawConfigTreeEmpty(configTree)
      case status if status == GetConfigTreeError().status => 
        updateStatus(status, configTree.result.status.common.get.status)
      
    }
    
    //TODO wrapper in share move
    

  }
  
  private def drawConfigTreeNotEmpty(configTree: JsonConfigTreeOut) = {
    println("drawConfigTree")
    val stepIdRow = configTree.result.step.get.stepId

    val stepId = prepareIdForHtml(stepIdRow)
    
    val stepKind = configTree.result.step.get.kind
    
    val htmlMain =  
    "<dev id='main' class='main'> " +
        "<p>Konfigurationsbaum</p>" +
        "<dev id='showConfigs' class='button'>" + 
            "Konfigurationen" + 
        "</dev>" +
    "</dev>"
    
    jQuery(htmlMain).appendTo(jQuery("section"))
    
    val components: Set[JsonConfigTreeComponent] = configTree.result.step.get.components
    
    var htmlComponents = ""
    
    components foreach { component => 
      val componentId = prepareIdForHtml(component.componentId)
      val nextStepId = prepareIdForHtml(component.nextStepId.get)
      
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
    
    jQuery(s"#showConfigs").on("click", () => showConfigs(configTree.result.userId.get))
    
    val componentIds = components map {c => prepareIdForHtml(c.componentId)}
    
    componentIds foreach { componentId => 
      jQuery(s"#editComponent$componentId").on("click", () => editComponent())
    }
    
    val nextSteps: Set[(String, JsonConfigTreeStep)] = components map {c => 
      c.nextStep match {
        case Some(nextStep) => (prepareIdForHtml(c.nextStepId.get), c.nextStep.get)
        case None => (prepareIdForHtml(c.nextStepId.get), null)
      }
    }
    
    nextSteps foreach { nextStepId =>
      val nSId = nextStepId._1
      jQuery(s"#selectNextStep$nSId").on("click", () => selectNextStep(nextStepId._2))
    }
  }
  
  
  def drawConfigTreeEmpty(configTree: JsonConfigTreeOut) = {
    val htmlMain =  
      """<dev id='main' class='main'> 
      <p>Konfiguration</p>
      <dev id='addStep' class='button'> Schritt erstellen </dev>
      <dev id='showConfigs' class='button'> Konfigurationen </dev>
    </dev> """
         
    jQuery(htmlMain).appendTo(jQuery("section"))
    
    jQuery(s"#addStep").on("click", () => addStep(configTree))
    jQuery(s"#showConfigs").on("click", () => showConfigs(configTree.result.userId.get))
    //TODO GetConfigs defecte userId abfangen
  }
 
  private def addStep(configTree: JsonConfigTreeOut) = {
    new AddStep(websocket).addStep(configTree)
  }
  
  def showConfigs(userId: String) = {
    val jsonGetConfigs: String  = Json.toJson(JsonGetConfigsIn(
        params = JsonGetConfigsParams(
            userId
        )
    )).toString
    
    println("OUT -> " + jsonGetConfigs)
    websocket.send(jsonGetConfigs)
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