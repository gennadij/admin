package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.client.models.{Progress, State}
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.component.{ComponentConfigPropertiesDTO, ComponentDTO, ComponentParamsDTO}
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.genericConfig.admin.shared.configGraph.ConfigGraphComponentDTO
import org.scalajs.jquery.{JQuery, jQuery}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 14.08.2020
 */
class NodeConnectComponentToStepPage {
  def drawConnectComponentToStepPage(configGraphComponentDTO: ConfigGraphComponentDTO) : Unit =  {
    val configGraphNodePage = jQuery(HtmlElementIds.jQueryConfigGraphNodeEdit)

    jQuery(HtmlElementIds.jQueryEditGroupNodePage).remove

    val editGroup : JQuery = HtmlElementText.drawDiv(HtmlElementIds.htmlEditGroupNodePage, "Komponente mit Schritt verbinden")

    val text : JQuery = HtmlElementText.drawDiv(HtmlElementIds.htmlEditGroupNodePage, "Schritt auswaehlen mit welchem die Komponente verbunden werden soll", "5")

    Progress.addState(State(
      componentDTO = Some(ComponentDTO(
        action = Actions.CONNECT_COMPONENT_TO_STEP,
        params = Some(ComponentParamsDTO(
          configProperties = Some(ComponentConfigPropertiesDTO(
            componentId = Some(configGraphComponentDTO.componentId)
          ))
        ))
      )),
      configDTO = Some(ConfigDTO(
        action = Actions.CONFIG_GRAPH,
        params = Some(ConfigParamsDTO(
          configId = Progress.getLastState.get.configDTO.get.params.get.configId
        ))
      ))
    ))

    editGroup.appendTo(configGraphNodePage)
    text.appendTo(editGroup)
    configGraphNodePage.appendTo(jQuery(HtmlElementIds.section))

  }
}
