package org.genericConfig.admin.client.configTree

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
import util.HtmlElementIds

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 26.04.2018
 */
class ConfigTree(websocket: WebSocket) extends CommonFunction{

  cleanPage
  
  def drawConfigTree(configTree: JsonConfigTreeOut) = {
    
    configTree.result.status.getConfigTree.get.status match {
      case status if status == GetConfigTreeSuccess().status =>
        cleanPage
        drawNewStatus(status + " | " + configTree.result.status.common.get.status)
        drawConfigTreeNotEmpty(configTree)
      case status if status == GetConfigTreeEmpty().status =>
        cleanPage
        drawNewStatus(status + " | " + configTree.result.status.common.get.status)
        drawConfigTreeEmpty(configTree)
      case status if status == GetConfigTreeError().status => 
        drawNewStatus(status + " | " + configTree.result.status.common.get.status)
    }
  }
  
  private def drawConfigTreeNotEmpty(configTree: JsonConfigTreeOut) = {
    val stepId = configTree.result.step.get.stepId
    
    val stepKind = configTree.result.step.get.kind
    
    val htmlMain =  
    "<dev id='main' class='main'> " +
        "<p>Konfigurationsbaum</p>" +
        drawButton(HtmlElementIds.getConfigsHtml, "Konfigurationen") + 
        "</dev>" +
    "</dev>"
    
    jQuery(htmlMain).appendTo(jQuery("section"))
    
    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => getConfigs(configTree.result.userId.get))
    
    val components: Set[JsonConfigTreeComponent] = configTree.result.step.get.components
    
    var htmlComponents = ""
    
    components foreach { component => 
      val componentId = prepareIdForHtml(component.componentId)
//      val nextStepId = prepareIdForHtml(component.nextStepId.get)
      
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
          "ID " + stepId.subSequence(0, 6) + "   " + 
          drawButton(HtmlElementIds.updateStepHtml, "update step") + 
          drawButton(HtmlElementIds.deleteStepHtml, "delete step") + 
          drawButton(HtmlElementIds.addComponentHtml, "add component") +
          drawButton(HtmlElementIds.deleteComponentHtml, "delete component") +
          " </br>" + 
          "Kind " + stepKind  + 
          htmlComponents + 
        "</div>" +
      "</dev> "
         
    jQuery(htmlStep).appendTo(jQuery(HtmlElementIds.mainJQuery))
    
    jQuery(HtmlElementIds.updateStepJQuery + stepId).on("click", () => editStep())
    
    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => getConfigs(configTree.result.userId.get))
    
    jQuery(HtmlElementIds.addComponentJQuery).on("click", () => addComponent)
    
    jQuery(HtmlElementIds.deleteComponentJQuery).on("click", () => deleteComponent)
    
    val componentIds = components map {c => prepareIdForHtml(c.componentId)}
    
    componentIds foreach { componentId => 
      jQuery(s"#editComponent$componentId").on("click", () => editComponent())
    }
    
//    val nextSteps: Set[(String, JsonConfigTreeStep)] = components map {c =>
//      c.nextStep match {
//        case Some(nextStep) => (prepareIdForHtml(c.nextStepId.get), c.nextStep.get)
//        case None => (prepareIdForHtml(c.nextStepId.get), null)
//      }
//    }
    
//    nextSteps foreach { nextStepId =>
//      val nSId = nextStepId._1
//      jQuery(s"#selectNextStep$nSId").on("click", () => selectNextStep(nextStepId._2))
//    }
  }
  
  private def deleteComponent = {
    println("delete Component")
  }
  
  private def addComponent = {
    println("addComponent")
  }
  
  private def drawConfigTreeEmpty(configTree: JsonConfigTreeOut) = {
    
    val htmlMain =  
      "<dev id='main' class='main'>" +
      "<p>Konfiguration</p>" + 
      drawButton(HtmlElementIds.addStepHtml, "Schritt erstellen") +
      drawButton(HtmlElementIds.getConfigsHtml, "Konfigurationen") + 
    "</dev>"
         
    drawNewMain(htmlMain)
    
    jQuery(HtmlElementIds.addStepJQuery).on("click", () => addStep(configTree.result.configId.get, configTree.result.userId.get))
    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => getConfigs(configTree.result.userId.get))
  }
 
  private def addStep(idToAppend: String, userId: String) = {
    new AddStep(websocket).addStep(idToAppend, userId)
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
  
//  def updateStatus(getConfigTreeStatus: String, commonStatus: String) = {
//    val htmlHeader = 
//      s"<dev id='status' class='status'>" + 
//        getConfigTreeStatus + " , " + commonStatus + 
//      "</dev>"
//    jQuery("#status").remove()
//    jQuery(htmlHeader).appendTo(jQuery("header"))
//  }
}