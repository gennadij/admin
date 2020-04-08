package org.genericConfig.admin.client.component

import org.genericConfig.admin.client.views.html.HtmlElementIds
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentOut, JsonComponentParams}
import org.genericConfig.admin.shared.config.json.{JsonGetConfigsIn, JsonGetConfigsParams}
import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json
import util.CommonFunction

class Component(websocket: WebSocket) extends CommonFunction {

  def addComponent(stepId: String, userId: String) = {
//    println("stepId" + stepId)
    cleanPage

    val htmlMain =
      "<dev id='main' class='main'>" +
        "<p>Neuen Komponent erstellen</p>" +
        drawInputField(HtmlElementIds.inputStepNameToShowHtml, "nameToShow") +
        drawButton(HtmlElementIds.addStepHtml, "Speichern") +
        drawButton(HtmlElementIds.getConfigsHtml, "Konfiguration") +
        "</dev>"

    drawNewMain(htmlMain)

    jQuery(HtmlElementIds.addStepJQuery).on("click", () => saveComponent(stepId))
    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => getConfigs(userId))
  }

  private def saveComponent(stepId: String) = {
//    println(stepId)
    val nameToShow: Dynamic = jQuery(HtmlElementIds.inputStepNameToShowJQuery).value()

    val jsonComponentOut = Json.toJson(JsonComponentIn(
      json = JsonNames.ADD_COMPONENT,
      params = JsonComponentParams(
        stepId = Some(stepId),
        nameToShow = Some(nameToShow.toString),
        kind = Some("immutable")
        )
      )
    ).toString

    println("OUT -> " + jsonComponentOut)
    websocket.send(jsonComponentOut)
  }

  private def getConfigs(userId: String) = {
    val jsonGetConfigs: String  = Json.toJson(JsonGetConfigsIn(
      params = JsonGetConfigsParams(
        userId
      )
    )).toString
    websocket.send(jsonGetConfigs)
  }

  def updateStatus(jsonComponentOut: JsonComponentOut) = {
    val htmlHeader =
      s"<dev id='status' class='status'>" +
        jsonComponentOut.result.status.addComponent.get.status +
        " , " +
        jsonComponentOut.result.status.appendComponent.get.status +
        " ," +
        jsonComponentOut.result.status.common.get.status +
        "</dev>"

    jQuery("#status").remove()
    jQuery(htmlHeader).appendTo(jQuery("header"))
  }

}
