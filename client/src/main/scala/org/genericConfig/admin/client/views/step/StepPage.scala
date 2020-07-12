package org.genericConfig.admin.client.views.step

import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
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

    val configGraphElemPage = HtmlElementText.configGraphElemPage(configGraphStepDTO.properties.nameToShow.get)
    val jQueryInputFieldSelectionCriterionMin = HtmlElementText.drawInputField(s"${configGraphStepDTO.stepId}_MIN", "SelectionCreterionMIN")
    val jQueryInputFieldSelectionCriterionMax = HtmlElementText.drawInputField(s"${configGraphStepDTO.stepId}_MAX", "SelectionCreterionMAX")
    jQueryInputFieldSelectionCriterionMin.appendTo(configGraphElemPage)
    jQueryInputFieldSelectionCriterionMax.appendTo(configGraphElemPage)
    configGraphElemPage.appendTo(jQuery(HtmlElementIds.section))
    jQuery(s"#${configGraphStepDTO.stepId}").css("fill", "#15e751")

  }
}
