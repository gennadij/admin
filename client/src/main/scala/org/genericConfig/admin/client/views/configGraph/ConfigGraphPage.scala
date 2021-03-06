package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.controllers.websocket.ActionsForClient
import org.genericConfig.admin.shared.configGraph.{ConfigGraphComponentDTO, ConfigGraphResultDTO, ConfigGraphStepDTO}
import org.scalajs.jquery.{JQuery, jQuery}
import play.api.libs.json.Json
import util.CommonFunction

import scala.scalajs.js.Dynamic.{global => g}
/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.06.2020
 */
class ConfigGraphPage extends CommonFunction {
  def drawConfigGraph(configGraphResultDTO: ConfigGraphResultDTO): Unit = {
    cleanPage

    jQuery("svg").remove()

    val jsonData : String = Json.toJson(configGraphResultDTO.d3Data.get).toString()

    g.runGraphD3_2(jsonData)

    val steps : List[(JQuery, ConfigGraphStepDTO)]  =
      configGraphResultDTO.steps.get.map(configGraphStepDTO => {
      (jQuery(s"#${configGraphStepDTO.stepId}"), configGraphStepDTO)
    })

    val components : List[(JQuery, ConfigGraphComponentDTO)] =
      configGraphResultDTO.components.get.map(configGraphComponentDTO => {
        (jQuery(s"#${configGraphComponentDTO.componentId}"), configGraphComponentDTO)
      })

    if (configGraphResultDTO.steps.get.isEmpty) {
      new NodeEditPage().showNodeEditPage(true)
    }else {
      new NodeEditPage().showNodeEditPage(false)
    }


    steps.foreach(jQS => {
      new Mouse().mouseClick(jQS._1, ActionsForClient.STEP_PAGE, Some(jQS._2))
    })

    components.foreach(jQC => {
      new Mouse().mouseClick(jQC._1, ActionsForClient.COMPONENT_PAGE, Some(jQC._2))
    })
  }
}
