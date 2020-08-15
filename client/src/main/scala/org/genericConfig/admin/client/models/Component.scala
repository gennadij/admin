package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.controllers.websocket.WebSocketListner
import org.genericConfig.admin.client.views.configGraph.{NodeAddComponentPage, NodeConnectComponentToStepPage, NodeEditComponentPage}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.component.{ComponentConfigPropertiesDTO, ComponentDTO, ComponentParamsDTO, ComponentUserPropertiesDTO}
import org.genericConfig.admin.shared.config.UserConfigDTO
import org.genericConfig.admin.shared.configGraph.{ConfigGraphComponentDTO, ConfigGraphStepDTO}
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class Component() {

  def addComponentRequest(param : Option[Any]) : Unit = {
    val stepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]
    println("ADD_Component " + stepDTO.properties.nameToShow)
    val inputFieldNameToShow : String = jQuery(s"#${stepDTO.stepId}_addComponentNameToShow").value().toString

    val addComponent: String = Json.toJson(ComponentDTO(
      action = Actions.ADD_COMPONENT,
      params = Some(ComponentParamsDTO(
        configProperties = Some(ComponentConfigPropertiesDTO(
          stepId = Some(stepDTO.stepId)
        )),
        userProperties = Some(ComponentUserPropertiesDTO(
          nameToShow = Some(inputFieldNameToShow)
        ))
      ))
    )).toString()
    println("OUT -> " + addComponent)
    WebSocketListner.webSocket.send(addComponent)
  }

  def deleteComponentRequest(param: Option[Any]) = {
    val configGraphComponentDTO = param.get.asInstanceOf[ConfigGraphComponentDTO]

    val deleteComponent: String = Json.toJson(ComponentDTO(
      action = Actions.DELETE_COMPONENT,
      params = Some(ComponentParamsDTO(
        configProperties = Some(ComponentConfigPropertiesDTO(
          componentId = Some(configGraphComponentDTO.componentId)
        ))
      ))
    )).toString()

    println("OUT -> " + deleteComponent)
    WebSocketListner.webSocket.send(deleteComponent)
  }

  def updateGraphResponse(componentDTO: Option[ComponentDTO]) : Unit = {
    val state : State = Progress.getLastState.get

    val userConfigDTO = UserConfigDTO(
      configId = state.configDTO.get.params.get.configId
    )

    new ConfigGraph().configGraph(Some(userConfigDTO))
  }

  def showAddComponentPage(param: Option[Any]) : Unit = {
    val configGraphStepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]
    new NodeAddComponentPage().drawAddComponentPage(configGraphStepDTO)
  }

  def updateComponentRequest(param: Option[Any]) = {
    val componentDTO: ConfigGraphComponentDTO = param.get.asInstanceOf[ConfigGraphComponentDTO]
    val inputFieldNameToShow : String = jQuery(s"#${componentDTO.componentId}_nameToShow").value().toString
    val updateComponent: String = Json.toJson(ComponentDTO(
      action = Actions.UPDATE_COMPONENT,
      params = Some(ComponentParamsDTO(
        configProperties = Some(ComponentConfigPropertiesDTO(
          componentId = Some(componentDTO.componentId)
        )),
        userProperties = Some(ComponentUserPropertiesDTO(
          nameToShow = Some(inputFieldNameToShow)
        ))
      ))
    )).toString
    println("OUT -> " + updateComponent)
    WebSocketListner.webSocket.send(updateComponent)
  }

  def updateComponentResponse() : Unit = {
    val state : State = Progress.getLastState.get

    val userConfigDTO = UserConfigDTO(
      configId = state.configDTO.get.params.get.configId
    )

    new ConfigGraph().configGraph(Some(userConfigDTO))
  }

  def showComponentPage(param: Option[Any]): Unit = {
    new NodeEditComponentPage().drawComponentPage(param.get.asInstanceOf[ConfigGraphComponentDTO])
  }

  def showConnectComponentToStepPage(param: Option[Any]) : Unit= {
    new NodeConnectComponentToStepPage().drawConnectComponentToStepPage(param.get.asInstanceOf[ConfigGraphComponentDTO])
  }

  def connectComponentToStepRequest(param: Option[Any]) : Unit = {
    val connectComponentToStep: String = Json.toJson(param.get.asInstanceOf[ComponentDTO]).toString()
    WebSocketListner.webSocket.send(connectComponentToStep)
  }

  def connectComponentToStepResponse(componentDTO: Option[ComponentDTO]): Unit = {
    val state : State = Progress.getLastState.get

    val userConfigDTO = UserConfigDTO(
      configId = state.configDTO.get.params.get.configId
    )
    //TODO Progress zuruecksetzen
    new ConfigGraph().configGraph(Some(userConfigDTO))
  }
}
