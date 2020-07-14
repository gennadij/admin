package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.client.controllers.listner.Mouse
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

    val editGroup : JQuery = HtmlElementText.drawDiv(HtmlElementIds.htmlEditGroupNodePage)

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

    val jQueryButtonSave = HtmlElementText.drawButton(id = s"${configGraphStepDTO.stepId}_save", "Speichern")
    val jQueryButtonAddComponent = HtmlElementText.drawButton(id = s"${configGraphStepDTO.stepId}_update_step", "Komponente hinzufuegen")

    editGroup.appendTo(configGraphNodePage)
    jQueryInputFieldNameToShow.appendTo(editGroup)
    jQueryInputFieldSelectionCriterionMin.appendTo(editGroup)
    jQueryInputFieldSelectionCriterionMax.appendTo(editGroup)
    jQueryButtonSave.appendTo(editGroup)
    jQueryButtonAddComponent.appendTo(editGroup)
    configGraphNodePage.appendTo(jQuery(HtmlElementIds.section))
    jQuery(s"#${configGraphStepDTO.stepId}").css("fill", "#15e751")

    new Mouse().mouseClick(jQueryButtonSave, Actions.UPDATE_STEP, Some(configGraphStepDTO))
    new Mouse().mouseClick(jQueryButtonAddComponent, Actions.ADD_COMPONENT, Some(configGraphStepDTO))
  }
}
