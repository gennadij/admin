package org.genericConfig.admin.client.old.configTree

import org.genericConfig.admin.client.old.component.Component
import org.scalajs.jquery.{JQuery, jQuery}
import org.scalajs.dom.raw.WebSocket
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeOut
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeComponent
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeStep
import org.genericConfig.admin.shared.configTree.status._
import org.genericConfig.admin.shared.config.json.JsonGetConfigsIn
import org.genericConfig.admin.shared.config.json.JsonGetConfigsParams
import play.api.libs.json.Json
import org.genericConfig.admin.client.old.step.AddStep
import org.genericConfig.admin.client.views.html.HtmlElementIds
import util.CommonFunction

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

  def drawConfigTree(configTree: JsonConfigTreeOut): Unit = {

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

  private def drawConfigTreeNotEmpty(configTree: JsonConfigTreeOut): Unit = {

    val htmlMain =
      "<div id='main' class='main'> " +
        "<p>Konfigurationsbaum</p>" +
        drawButton(HtmlElementIds.getConfigsHtml, "Konfigurationen") +
      "</div>"

    drawNewMain(htmlMain)

    val stepsHtml = drawFirstStep(configTree.result.step.get)

    jQuery(stepsHtml).appendTo(jQuery(HtmlElementIds.mainJQuery))

    val userId = configTree.result.userId.get

    jQuery(HtmlElementIds.updateStepJQuery).on("click", () => updateStep())

    jQuery(HtmlElementIds.deleteStepJQuery).on("click", () => deleteStep())

    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => getConfigs(userId))

    this.stepIds foreach(id => {
      jQuery(HtmlElementIds.addComponentJQuery + id).on("click", () => addComponent(id, userId))

    })

    this.componentIds foreach(cId => {
      jQuery(HtmlElementIds.deleteComponentJQuery + cId).on("click", () => deleteComponent())
      jQuery(HtmlElementIds.addStepJQuery + cId).on("click", () => addStep(cId, userId))
    })
  }

  def drawComponents(components: Set[JsonConfigTreeComponent]): String = {

    var htmlComponents = ""

    components foreach { component =>
      val componentId = component.componentId
      val nextStepId = component.nextStepId match {
        case Some(id) => id.subSequence(0,6)
        case None => "last component"
      }

      this.componentIds += componentId

      htmlComponents = htmlComponents +
        "<div id='" + componentId + "' class='component'>" +
          "ID " + component.componentId.subSequence(0, 6) +
          "&emsp; || &emsp;" +
          "Kind: " + component.kind +
          "&emsp; || &emsp;" +
          "nameToShow: " + component.nameToShow +
          "&emsp; || &emsp;" +
          "nextStep: " + nextStepId +
          "</br>" +
          drawButton(HtmlElementIds.updateComponentHtml + componentId, "update component") +
          drawButton(HtmlElementIds.deleteComponentHtml + componentId, "delete component") +
          drawButton(HtmlElementIds.addStepHtml + componentId, "add step") +
          drawButton(HtmlElementIds.connectToStepHtml + componentId, "connect to step") +
        "</div>"
    }
    if(components.isEmpty)
      "Scritt hat keine Komponente"
    else
      htmlComponents
  }

  def drawFirstStep(step: JsonConfigTreeStep): String = {
    val stepId = step.stepId

    this.stepIds += stepId

    val htmlStep = drawHtmlStep(step)

    val htmlNextStep_ = drawNextSteps_(step.nextSteps)

    htmlStep + htmlNextStep_.mkString
  }

  private def drawNextSteps_(nextSteps: Set[JsonConfigTreeStep]): Set[String] = {

    nextSteps map (nSS => {

      this.stepIds += nSS.stepId

      val html = drawHtmlStep(nSS)

      val nextStepsHtml = drawNextSteps_(nSS.nextSteps)

      html + nextStepsHtml.mkString
    })
  }

  private def drawHtmlStep(step: JsonConfigTreeStep): String = {

    val htmlComponents = drawComponents(step.components)

    "<div id='" + step.stepId + "' class='step'>" +
      "ID: " + step.stepId.subSequence(0, 6) +
      "&emsp; || &emsp;" +
      "Kind " + step.kind +
      "&emsp; || &emsp;" +
      "nameToShow: " + step.nameToShow +
      "</br>" +
      drawButton(HtmlElementIds.updateStepHtml + step.stepId, "update step") +
      drawButton(HtmlElementIds.deleteStepHtml + step.stepId, "delete step") +
      drawButton(HtmlElementIds.addComponentHtml + step.stepId, "add component") +
      "</br>" +
      htmlComponents +
      "</div>"
  }

  private def deleteComponent() : Unit = {
    println("delete Component")
  }

  private def addComponent(stepId: String, userId: String) = {
    new Component(websocket).addComponent(stepId, userId)
  }

  private def updateStep(): Unit = {
    println("update step")
  }

  private def deleteStep(): Unit = {
    println("delete step")
  }

  private def drawConfigTreeEmpty(configTree: JsonConfigTreeOut):JQuery = {

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

  private def addStep(idToAppend: String, userId: String): Unit = {
    new AddStep(websocket).addStep(idToAppend, userId)
  }

  private def getConfigs(userId: String): Unit = {
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