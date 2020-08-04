package org.genericConfig.admin.client.controllers.websocket

import org.genericConfig.admin.shared.Actions
import play.api.libs.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.04.2018
 */
class MessageHandler() {


  def handleMessage(receivedMessage: JsValue): Any = {
    (receivedMessage \ "action").asOpt[String] match {
      case Some(Actions.ADD_USER) => ConverterFromJsonForUser.addUser(receivedMessage)
      case Some(Actions.GET_USER) => ConverterFromJsonForUser.getUser(receivedMessage)
      case Some(Actions.DELETE_USER) => ConverterFromJsonForUser.deleteUser(receivedMessage)
      case Some(Actions.UPDATE_USER) => ConverterFromJsonForUser.updateUser(receivedMessage)
      case Some(Actions.ADD_CONFIG) => ConverterFromJsonForConfig.addConfig(receivedMessage)
      case Some(Actions.GET_CONFIGS) => ConverterFromJsonForConfig.getConfigs(receivedMessage)
      case Some(Actions.DELETE_CONFIG) => ConverterFromJsonForConfig.deleteConfig(receivedMessage)
      case Some(Actions.UPDATE_CONFIG) => ???
      case Some(Actions.CONFIG_GRAPH) => ConverterJsonDTOForConfigGraph.configGraph(receivedMessage)
      case Some(Actions.ADD_COMPONENT) => ConverterFromJsonForComponent.addComponent(receivedMessage)
      case Some(Actions.DELETE_COMPONENT) => ConverterFromJsonForComponent.deleteComponent(receivedMessage)
      case Some(Actions.ADD_STEP) => ConverterFromJsonForStep.addStep(receivedMessage)
      case Some(Actions.DELETE_STEP) => ConverterFromJsonForStep.deleteStep(receivedMessage)
      case Some(Actions.UPDATE_STEP) => ConverterFromJsonForStep.updateStep(receivedMessage)
//      case Some(JsonNames.CONNECT_COMPONENT_TO_STEP) => ??? //connectComponentToStep(receivedMessage, admin)
//      case Some(JsonNames.CREATE_DEPENDENCY) => ??? //createDependency(receivedMessage, admin)
//      case Some(JsonNames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL) => ???
//        visualProposalForAdditionalStepsInOneLevel(receivedMessage, admin)
      case _ => Json.obj("error" -> "keinen Treffer")
    }
  }
}