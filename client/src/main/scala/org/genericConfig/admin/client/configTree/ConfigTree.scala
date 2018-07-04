package org.genericConfig.admin.client.configTree

import org.genericConfig.admin.client.component.Component
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

import scala.collection.mutable

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 26.04.2018
  */
class ConfigTree(websocket: WebSocket) extends CommonFunction {


  var componentIds: mutable.ListBuffer[String] = scala.collection.mutable.ListBuffer()

  var stepIds: mutable.ListBuffer[String] = scala.collection.mutable.ListBuffer()

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

    val htmlMain =
      "<div id='main' class='main'> " +
        "<p>Konfigurationsbaum</p>" +
        drawButton(HtmlElementIds.getConfigsHtml, "Konfigurationen") +
      "</div>"

    drawNewMain(htmlMain)

    jQuery(drawFirstStep(configTree.result.step.get)).appendTo(jQuery(HtmlElementIds.mainJQuery))

    println("Step ID " + this.stepIds)
    println("Component ID" + this.componentIds)

    val userId = configTree.result.userId.get

    jQuery(HtmlElementIds.updateStepJQuery).on("click", () => updateStep)

    jQuery(HtmlElementIds.deleteStepJQuery).on("click", () => deleteStep)

    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => getConfigs(userId))

    this.stepIds foreach(id => {
      jQuery(HtmlElementIds.addComponentJQuery + id).on("click", () => addComponent(id, userId))

    })

    this.componentIds foreach(cId => {
      jQuery(HtmlElementIds.deleteComponentJQuery + cId).on("click", () => deleteComponent)
      jQuery(HtmlElementIds.addStepJQuery + cId).on("click", () => addStep(cId, userId))
    })
  }

  def drawComponents(components: Set[JsonConfigTreeComponent]): String = {

    var htmlComponents = ""

    components foreach { component =>
      val componentId = component.componentId
      val nextStepId = component.nextStepId

      this.componentIds += componentId

      htmlComponents = htmlComponents +
        "<div id='" + componentId + "' class='component'>" +
          "ID " + component.componentId.subSequence(0, 6) +
          "</br>" +
          "Kind: " + component.kind +
          "</br>" +
          "nameToShow: " + component.nameToShow +
          "</br>" +
          drawButton(HtmlElementIds.updateComponentHtml + componentId, "update component") +
          drawButton(HtmlElementIds.deleteComponentHtml + componentId, "delete component") +
          drawButton(HtmlElementIds.addStepHtml + componentId, "add step") +
          drawButton(HtmlElementIds.connectToStepHtml + componentId, "connect to step") +
        "</div>"
    }
    if(components.size == 0)
      "Scritt hat keine Komponente"
    else
      htmlComponents
  }

  def drawFirstStep(step: JsonConfigTreeStep): String = {
    val stepId = step.stepId
    val kind = step.kind

    var htmlNextStep = ""

    this.stepIds += stepId

    val htmlComponents = drawComponents(step.components)
//    match {
//      case components if components.isEmpty =>
//        "Scritt hat keine Komponente"
//      case components => components
//    }


    val htmlStep =
      "<div id='" + stepId + "' class='step'>" +
        "ID " + stepId.subSequence(0, 6) + "   " +
        "</br>" +
        "Kind " + kind +
        "</br>" +
        "nameToShow: " + step.nameToShow +
        "</br>" +
        drawButton(HtmlElementIds.updateStepHtml + stepId, "update step") +
        drawButton(HtmlElementIds.deleteStepHtml + stepId, "delete step") +
        drawButton(HtmlElementIds.addComponentHtml + stepId, "add component") +
        " </br>" +
        htmlComponents +
      "</div>"

    htmlNextStep = drawNextSteps(htmlNextStep, step.nextSteps)

    htmlStep + htmlNextStep
  }

  private def drawNextSteps(htmlNextStep: String, nextSteps: Set[JsonConfigTreeStep]): String = {

    var htmlNextSteps = htmlNextStep

    nextSteps foreach (nextStep => {
      this.stepIds += nextStep.stepId

      val htmlComponents = drawComponents(nextStep.components)

        htmlNextSteps = htmlNextSteps + "<div id='" + nextStep.stepId + "' class='step'>" +
        "ID " + nextStep.stepId.subSequence(0, 6) +
        "</br>" +
        "Kind " + nextStep.kind +
        "</br>" +
        "nameToShow: " + nextStep.nameToShow +
        "</br>" +
        drawButton(HtmlElementIds.updateStepHtml + nextStep.stepId, "update step") +
        drawButton(HtmlElementIds.deleteStepHtml + nextStep.stepId, "delete step") +
        drawButton(HtmlElementIds.addComponentHtml + nextStep.stepId, "add component") +
        "</br>" +
        htmlComponents +
        "</div>"

      if (nextStep.nextSteps.size >= 1)
        htmlNextSteps + drawNextSteps(htmlNextSteps, nextStep.nextSteps)
    })

    htmlNextSteps
  }

  private def deleteComponent = {
    println("delete Component")
  }

  private def addComponent(stepId: String, userId: String) = {
//    println("add component")
//    println(stepId)
//    println(userId)
    new Component(websocket).addComponent(stepId, userId)
  }

  private def updateStep = {
    println("update step")
  }

  private def deleteStep = {
    println("delete step")
  }

  private def drawConfigTreeEmpty(configTree: JsonConfigTreeOut) = {

    val htmlMain =
      "<div id='main' class='main'>" +
        "<p>Konfiguration</p>" +
        drawButton(HtmlElementIds.addStepHtml, "Schritt erstellen") +
        drawButton(HtmlElementIds.getConfigsHtml, "Konfigurationen") +
        "</div>"

    drawNewMain(htmlMain)

    jQuery(HtmlElementIds.addStepJQuery).on("click", () => addStep(configTree.result.configId.get, configTree.result.userId.get))
    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => getConfigs(configTree.result.userId.get))
  }

  private def addStep(idToAppend: String, userId: String) = {
    new AddStep(websocket).addStep(idToAppend, userId)
  }

  private def getConfigs(userId: String) = {
    val jsonGetConfigs: String = Json.toJson(JsonGetConfigsIn(
      params = JsonGetConfigsParams(
        userId
      )
    )).toString

    println("OUT -> " + jsonGetConfigs)
    websocket.send(jsonGetConfigs)
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