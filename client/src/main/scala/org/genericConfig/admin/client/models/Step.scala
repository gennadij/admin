package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.controllers.websocket.WebSocketListner
import org.genericConfig.admin.client.views.configGraph.{NodeAddStepPage, NodeEditStepPage}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.UserConfigDTO
import org.genericConfig.admin.shared.configGraph.{ConfigGraphComponentDTO, ConfigGraphStepDTO}
import org.genericConfig.admin.shared.step.{SelectionCriterionDTO, StepDTO, StepParamsDTO, StepPropertiesDTO}
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class Step {

  def addStepRequest(param: Option[Any]): Unit = {
    val id : String = param.get match {
      case param : ConfigGraphComponentDTO => param.componentId
      case param : String => param
      case _ => "undefinedId"
    }

    val inputFieldNameToShow : String = jQuery(s"#${id}_addStepNameToShow").value().toString
    val inputFieldSelectionCriterionMin : String = jQuery(s"#${id}_MIN").value().toString
    val inputFieldSelectionCriterionMax : String = jQuery(s"#${id}_MAX").value().toString

    val addStep: String = Json.toJson(StepDTO(
      action = Actions.ADD_STEP,
      params = Some(StepParamsDTO(
        outId = Some(id),
        kind = Some(""),
        properties = Some(StepPropertiesDTO(
          nameToShow = Some(inputFieldNameToShow),
          selectionCriterion = Some(SelectionCriterionDTO(
            min = Some(inputFieldSelectionCriterionMin.toInt),
            max = Some(inputFieldSelectionCriterionMax.toInt)
          ))
        ))
      )),
      result = None
    )).toString()

    println("OUT -> " + addStep)
    WebSocketListner.webSocket.send(addStep)
  }

  def updateGraphResponse(stepDTO : Option[StepDTO]): Unit = {
    val state : State = Progress.getLastState.get

    val userConfigDTO = UserConfigDTO(
      configId = state.configDTO.get.params.get.configId
    )

    new ConfigGraph().configGraph(Some(userConfigDTO))
  }

  def deleteStepRequest(param: Option[Any]): Unit = {
    val stepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]

    val deleteStep: String = Json.toJson(StepDTO(
      action = Actions.DELETE_STEP,
      params = Some(StepParamsDTO(
        stepId = Some(stepDTO.stepId)
      )),
      result = None
    )).toString()

    println("OUT -> " + deleteStep)
    WebSocketListner.webSocket.send(deleteStep)
  }


  def updateStepRequest(param: Option[Any]): Unit = {
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

  def showStep(param: Option[Any]): Unit = {
    val stepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]
    new NodeEditStepPage().drawStepPage(stepDTO)
  }

  def showAddStepPage(param: Option[Any]): Unit = {
    val componentDTO: ConfigGraphComponentDTO = param.get.asInstanceOf[ConfigGraphComponentDTO]
    new NodeAddStepPage().drawAddStepFromComponentPage(componentDTO)
  }


}
