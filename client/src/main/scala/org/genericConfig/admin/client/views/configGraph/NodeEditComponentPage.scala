package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.ConfigGraphComponentDTO
import org.scalajs.jquery.{JQuery, jQuery}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class NodeEditComponentPage() {
  def drawComponentPage(configGraphComponentDTO: ConfigGraphComponentDTO): Unit = {
    val configGraphNodePage = jQuery(HtmlElementIds.jQueryConfigGraphNodeEdit)

    jQuery(HtmlElementIds.jQueryEditGroupNodePage).remove

    val editGroup : JQuery = HtmlElementText.drawDiv(HtmlElementIds.htmlEditGroupNodePage)

    val jQueryInputFieldNameToShow = HtmlElementText.drawInputField(
      id = s"${configGraphComponentDTO.componentId}_nameToShow",
      label = "Name",
      defaultText = configGraphComponentDTO.properties.nameToShow.get
    )

    val jQueryButtonSave = HtmlElementText.drawButton(id = s"${configGraphComponentDTO.componentId}_save", "Speichern")
    val jQueryButtonAddComponent = HtmlElementText.drawButton(id = s"${configGraphComponentDTO.componentId}_update_component", "Schritt hinzufuegen")
    val jQueryButtonDeleteComponent = HtmlElementText.drawButton(id = s"${configGraphComponentDTO.componentId}_deleteComponent", "Component entfernen")

    editGroup.appendTo(configGraphNodePage)
    jQueryInputFieldNameToShow.appendTo(editGroup)
    jQueryButtonSave.appendTo(editGroup)
    jQueryButtonAddComponent.appendTo(editGroup)
    jQueryButtonDeleteComponent.appendTo((editGroup))
    configGraphNodePage.appendTo(jQuery(HtmlElementIds.section))

    new Mouse().mouseClick(jQueryButtonSave, Actions.UPDATE_COMPONENT, Some(configGraphComponentDTO))
    new Mouse().mouseClick(jQueryButtonAddComponent, Actions.ADD_STEP, Some(configGraphComponentDTO))
    new Mouse().mouseClick(jQueryButtonDeleteComponent, Actions.DELETE_COMPONENT, Some(configGraphComponentDTO))


    jQuery(s"#${configGraphComponentDTO.componentId}").css("fill", "#163183")
  }

}
