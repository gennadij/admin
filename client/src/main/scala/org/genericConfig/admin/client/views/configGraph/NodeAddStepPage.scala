package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.ConfigGraphComponentDTO
import org.scalajs.jquery.{JQuery, jQuery}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 02.08.2020
 */
class NodeAddStepPage {
  def drawAddStepPage(configGraphComponentDTO: ConfigGraphComponentDTO): Unit = {

    val configGraphNodePage = jQuery(HtmlElementIds.jQueryConfigGraphNodeEdit)

    jQuery(HtmlElementIds.jQueryEditGroupNodePage).remove

    val editGroup : JQuery = HtmlElementText.drawDiv(HtmlElementIds.htmlEditGroupNodePage, "Schritt hinzufuegen")

    val jQueryInputFieldNameToShow = HtmlElementText.drawInputField(
      id = s"${configGraphComponentDTO.componentId}_addStepNameToShow",
      label = "Name"
    )
    val jQueryInputFieldSelectionCriterionMin = HtmlElementText.drawInputField(
      id = s"${configGraphComponentDTO.componentId}_MIN",
      label = "SelectionCriterionMIN",
      defaultText = "1"
    )
    val jQueryInputFieldSelectionCriterionMax = HtmlElementText.drawInputField(
      id = s"${configGraphComponentDTO.componentId}_MAX",
      label = "SelectionCriterionMAX",
      defaultText = "1"
    )
    val jQueryButtonAddComponent = HtmlElementText.drawButton(s"${configGraphComponentDTO.componentId}_addStep", "Schritt hinzufuegen")

    editGroup.appendTo(configGraphNodePage)
    jQueryInputFieldNameToShow.appendTo(editGroup)
    jQueryInputFieldSelectionCriterionMin.appendTo(editGroup)
    jQueryInputFieldSelectionCriterionMax.appendTo(editGroup)
    jQueryButtonAddComponent.appendTo(editGroup)

    new Mouse().mouseClick(jQueryButtonAddComponent, Actions.ADD_STEP, Some(configGraphComponentDTO))
  }
}
