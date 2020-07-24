package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.ConfigGraphStepDTO
import org.scalajs.jquery.{JQuery, jQuery}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 24.07.2020
 */
class NodeAddComponentPage {
  def drawAddComponentPage(configGraphStepDTO: ConfigGraphStepDTO): Unit = {

    val configGraphNodePage = jQuery(HtmlElementIds.jQueryConfigGraphNodeEdit)

    jQuery(HtmlElementIds.jQueryEditGroupNodePage).remove

    val editGroup : JQuery = HtmlElementText.drawDiv(HtmlElementIds.htmlEditGroupNodePage, "Komponente hinzufuegen")

    val jQueryInputFieldNameToShow = HtmlElementText.drawInputField(
      id = s"${configGraphStepDTO.stepId}_addComponentNameToShow",
      label = "Name"
    )

    val jQueryButtonAddComponent = HtmlElementText.drawButton(s"${configGraphStepDTO.stepId}_addComponent", "Komponente hinzufuegen")

    editGroup.appendTo(configGraphNodePage)
    jQueryInputFieldNameToShow.appendTo(editGroup)
    jQueryButtonAddComponent.appendTo(editGroup)

    new Mouse().mouseClick(jQueryButtonAddComponent, Actions.ADD_COMPONENT, Some(configGraphStepDTO))
  }

}
