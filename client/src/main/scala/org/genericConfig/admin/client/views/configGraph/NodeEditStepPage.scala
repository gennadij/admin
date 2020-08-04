package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.controllers.websocket.ActionsForClient
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.ConfigGraphStepDTO
import org.scalajs.jquery.{JQuery, jQuery}
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class NodeEditStepPage extends CommonFunction{
  def drawStepPage(configGraphStepDTO: ConfigGraphStepDTO): Unit = {

    val configGraphNodePage = jQuery(HtmlElementIds.jQueryConfigGraphNodeEdit)

    jQuery(HtmlElementIds.jQueryEditGroupNodePage).remove

    val editGroup : JQuery = HtmlElementText.drawDiv(HtmlElementIds.htmlEditGroupNodePage, "Schritt")

    val jQueryInputFieldNameToShow = HtmlElementText.drawInputField(
      id = s"${configGraphStepDTO.stepId}_nameToShow",
      label = "Name",
      defaultText = configGraphStepDTO.properties.nameToShow.get
    )
    val jQueryInputFieldSelectionCriterionMin = HtmlElementText.drawInputField(
      id = s"${configGraphStepDTO.stepId}_MIN",
      label = "SelectionCriterionMIN",
      defaultText = configGraphStepDTO.properties.selectionCriterion.get.min.get.toString
    )
    val jQueryInputFieldSelectionCriterionMax = HtmlElementText.drawInputField(
      id = s"${configGraphStepDTO.stepId}_MAX",
      label = "SelectionCriterionMAX",
      defaultText = configGraphStepDTO.properties.selectionCriterion.get.max.get.toString
    )


    val jQueryButtonUpdateStep = HtmlElementText.drawButton(id = s"${configGraphStepDTO.stepId}_updateStep", "Schritt aendern")
    val jQueryButtonAddComponent = HtmlElementText.drawButton(id = s"${configGraphStepDTO.stepId}_addComponent", "Neu Komponente hinzufuegen")
    val jQueryButtonDeleteStep = HtmlElementText.drawButton(id = s"${configGraphStepDTO.stepId}_deleteStep", "Schritt entfernen")

    editGroup.appendTo(configGraphNodePage)
    jQueryInputFieldNameToShow.appendTo(editGroup)
    jQueryInputFieldSelectionCriterionMin.appendTo(editGroup)
    jQueryInputFieldSelectionCriterionMax.appendTo(editGroup)
    jQueryButtonUpdateStep.appendTo(editGroup)
    jQueryButtonAddComponent.appendTo(editGroup)
    jQueryButtonDeleteStep.appendTo((editGroup))
    configGraphNodePage.appendTo(jQuery(HtmlElementIds.section))
    jQuery(s"#${configGraphStepDTO.stepId}").css("fill", "#15e751")

    new Mouse().mouseClick(jQueryButtonUpdateStep, Actions.UPDATE_STEP, Some(configGraphStepDTO))
    new Mouse().mouseClick(jQueryButtonAddComponent, ActionsForClient.ADD_COMPONENT_PAGE, Some(configGraphStepDTO))
    new Mouse().mouseClick(jQueryButtonDeleteStep, Actions.DELETE_STEP, Some(configGraphStepDTO))
  }
}
