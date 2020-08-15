package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.models.Progress
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
  def drawAddStepFromComponentPage(configGraphComponentDTO: ConfigGraphComponentDTO): Unit = {

    val jQueryButtonAddComponent = drawAddStepPage(configGraphComponentDTO.componentId)

    new Mouse().mouseClick(jQueryButtonAddComponent, Actions.ADD_STEP, Some(configGraphComponentDTO))
  }

  def drawAddFirstStepPage() : Unit = {
    val id : String = Progress.getLastState.get.configDTO.get.params.get.configId.get

    val jQueryButtonAddComponent = drawAddStepPage(id)

    new Mouse().mouseClick(jQueryButtonAddComponent, Actions.ADD_STEP, Some(id))
  }

  def drawAddStepPage(id : String): JQuery = {
    val configGraphNodePage = jQuery(HtmlElementIds.jQueryConfigGraphNodeEdit)

    jQuery(HtmlElementIds.jQueryEditGroupNodePage).remove

    val editGroup : JQuery = HtmlElementText.drawDiv(HtmlElementIds.htmlEditGroupNodePage, "Schritt hinzufuegen")

    val jQueryInputFieldNameToShow = HtmlElementText.drawInputField(
      id = s"${id}_addStepNameToShow",
      label = "Name"
    )
    val jQueryInputFieldSelectionCriterionMin = HtmlElementText.drawInputField(
      id = s"${id}_MIN",
      label = "SelectionCriterionMIN",
      defaultText = "1"
    )
    val jQueryInputFieldSelectionCriterionMax = HtmlElementText.drawInputField(
      id = s"${id}_MAX",
      label = "SelectionCriterionMAX",
      defaultText = "1"
    )
    val jQueryButtonAddComponent = HtmlElementText.drawButton(s"${id}_addStep", "Schritt hinzufuegen")

    editGroup.appendTo(configGraphNodePage)
    jQueryInputFieldNameToShow.appendTo(editGroup)
    jQueryInputFieldSelectionCriterionMin.appendTo(editGroup)
    jQueryInputFieldSelectionCriterionMax.appendTo(editGroup)
    jQueryButtonAddComponent.appendTo(editGroup)

    jQueryButtonAddComponent
  }
}
