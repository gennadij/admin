package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.shared.Actions
import play.api.libs.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 28.11.2016
 */

trait MessageHandler
    extends ConverterJsonDTOForUser
    with ConverterJsonDTOForConfig
    with ConverterJsonDTOForStep
    with ConverterJsonDTOForComponent {

  def hMessage(receivedMessage: JsValue): JsValue = {
    (receivedMessage \ "action").asOpt[String] match {
      case Some(Actions.ADD_USER) => addUser(receivedMessage)
      case Some(Actions.DELETE_USER) => deleteUser(receivedMessage)
      case Some(Actions.GET_USER) => getUser(receivedMessage)
      case Some(Actions.UPDATE_USER) => updateUser(receivedMessage)

      case Some(Actions.ADD_CONFIG) => addConfig(receivedMessage)
      case Some(Actions.DELETE_CONFIG) => deleteConfig(receivedMessage)
      case Some(Actions.GET_CONFIGS) => getConfigs(receivedMessage)
      case Some(Actions.UPDATE_CONFIG) => updateConfig(receivedMessage)

      case Some(Actions.ADD_STEP) => addStep(receivedMessage)
      case Some(Actions.DELETE_STEP) => deleteStep(receivedMessage)
      case Some(Actions.UPDATE_STEP) => updateStep(receivedMessage)

      case Some(Actions.ADD_COMPONENT) => addComponent(receivedMessage)
      case Some(Actions.DELETE_COMPONENT) => deleteComponent(receivedMessage)
      case Some(Actions.UPDATE_COMPONENT) => updateComponent(receivedMessage)

      //      case Some(JsonNames.CONNECT_COMPONENT_TO_STEP) => connectComponentToStep(receivedMessage, admin)
      //      case Some(JsonNames.CONFIG_TREE) => configTree(receivedMessage, admin)
      //      case Some(JsonNames.CREATE_DEPENDENCY) => createDependency(receivedMessage, admin)
      //      case Some(JsonNames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL) =>
      //        visualProposalForAdditionalStepsInOneLevel(receivedMessage, admin)
      case _ => Json.obj("error" -> "keinen Treffer")
    }
  }
}
