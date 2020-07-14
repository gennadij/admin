package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.controllers.websocket.WebSocketListner
import org.genericConfig.admin.client.views.configGraph.NodeEditStepPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.ConfigGraphStepDTO
import org.genericConfig.admin.shared.step.{SelectionCriterionDTO, StepDTO, StepParamsDTO, StepPropertiesDTO}
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class Step {
  def updateStep(param: Option[Any]): Unit = {
    val stepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]
    val inputFieldNameToShow : String = jQuery(s"#${stepDTO.stepId}_nameToShow").value().toString
    val inputFieldSelectionCriterionMin : String = jQuery(s"#${stepDTO.stepId}_MIN").value().toString
    val inputFieldSelectionCriterionMax : String = jQuery(s"#${stepDTO.stepId}_MAX").value().toString
    val updateStep : String = Json.toJson(StepDTO(
      action = Actions.UPDATE_STEP,
      params = Some(StepParamsDTO(
        stepId = Some(stepDTO.stepId),
        properties = Some(StepPropertiesDTO(
          nameToShow = Some(inputFieldNameToShow),
          selectionCriterion = Some(SelectionCriterionDTO(
            min = Some(inputFieldSelectionCriterionMin.toInt),
            max = Some(inputFieldSelectionCriterionMax.toInt)
          ))
        ))
      ))
    )).toString
    println("OUT -> " + updateStep)
    WebSocketListner.webSocket.send(updateStep)
  }

  def addComponent(param : Option[Any]) : Unit = {
    val stepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]
    println("ADD_Component " + stepDTO.properties.nameToShow)
  }

  def showStep(param: Option[Any]): Unit = {
    val stepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]
    new NodeEditStepPage().drawStepPage(stepDTO)
  }
}
