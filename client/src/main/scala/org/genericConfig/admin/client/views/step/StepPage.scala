package org.genericConfig.admin.client.views.step

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.ConfigGraphStepDTO
import org.scalajs.jquery.jQuery
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class StepPage extends CommonFunction{
  def drawStepPage(configGraphStepDTO: ConfigGraphStepDTO): Unit = {

    //TODO delete bevor andere erstellt wird
    jQuery("#configGraphElem").remove()
    val configGraphNodePage = HtmlElementText.configGraphNodePage(configGraphStepDTO.properties.nameToShow.get)
    val jQueryInputFieldNameToShow = HtmlElementText.drawInputField(id = s"${configGraphStepDTO.stepId}_nameToShow", label = "Name")
    val jQueryInputFieldSelectionCriterionMin = HtmlElementText.drawInputField(id = s"${configGraphStepDTO.stepId}_MIN", label = "SelectionCriterionMIN")
    val jQueryInputFieldSelectionCriterionMax = HtmlElementText.drawInputField(s"${configGraphStepDTO.stepId}_MAX", "SelectionCriterionMAX")
    val jQueryButtonSave = HtmlElementText.drawButton(id = s"${configGraphStepDTO.stepId}_save", "Speichern")
    jQueryInputFieldNameToShow.appendTo(configGraphNodePage)
    jQueryInputFieldSelectionCriterionMin.appendTo(configGraphNodePage)
    jQueryInputFieldSelectionCriterionMax.appendTo(configGraphNodePage)
    jQueryButtonSave.appendTo(configGraphNodePage)
    configGraphNodePage.appendTo(jQuery(HtmlElementIds.section))
    jQuery(s"#${configGraphStepDTO.stepId}").css("fill", "#15e751")

    new Mouse().mouseClick(jQueryButtonSave, Actions.UPDATE_STEP, Some(configGraphStepDTO))
  }
}
