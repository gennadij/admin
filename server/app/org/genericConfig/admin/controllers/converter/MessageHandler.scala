package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.shared.Actions
import play.api.libs.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 28.11.2016
 */

trait MessageHandler
    extends WrapperForUser
    with WrapperForConfig
    with ConverterJsonDTOForStep {

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

//      case Some(JsonNames.CONFIG_TREE) => configTree(receivedMessage, admin)

      case Some(Actions.ADD_STEP) => addStep(receivedMessage)
      case Some(Actions.DELETE_STEP) => deleteStep(receivedMessage)
      case Some(Actions.UPDATE_STEP) => updateStep(receivedMessage)
//      case Some(JsonNames.CONNECT_COMPONENT_TO_STEP) => connectComponentToStep(receivedMessage, admin)
//
//      case Some(JsonNames.ADD_COMPONENT) => addComponent(receivedMessage, admin)
//      case Some(JsonNames.DELETE_COMPONENT) => deleteComponent(receivedMessage, admin)
//      case Some(JsonNames.UPDATE_COMPONENT) => updateComponent(receivedMessage, admin)



      
//      case Some(JsonNames.CREATE_DEPENDENCY) => createDependency(receivedMessage, admin)
//
//      case Some(JsonNames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL) =>
//        visualProposalForAdditionalStepsInOneLevel(receivedMessage, admin)
      case _ => Json.obj("error" -> "keinen Treffer")
    }
  }




//  private def deleteStep(receivedMessage: JsValue, admin: Admin): JsValue = {
//    val deleteFirstStepIn: JsResult[JsonStepIn] = Json.fromJson[JsonStepIn](receivedMessage)
//    deleteFirstStepIn match {
//      case _ : JsSuccess[JsonStepIn] => Json.toJson(admin.deleteFirstStep(deleteFirstStepIn.get))
//      case e : JsError => jsonError(JsonNames.DELETE_STEP, e)
//    }
//  }
//
//  private def updateStep(receivedMessage: JsValue, admin: Admin): JsValue = {
//    val updateStepIn: JsResult[JsonStepIn] = Json.fromJson[JsonStepIn](receivedMessage)
//    updateStepIn match {
//      case _ : JsSuccess[JsonStepIn] => Json.toJson(admin.updateStep(updateStepIn.get))
//      case e : JsError => jsonError(JsonNames.UPDATE_STEP, e)
//    }
//  }
//
//  private def addComponent(receivedMessage: JsValue, admin: Admin): JsValue = {
//    Json.fromJson[JsonComponentIn](receivedMessage) match {
//      case componentIn : JsSuccess[JsonComponentIn] => Json.toJson(admin.addComponent(componentIn.get))
//      case e : JsError => jsonError(JsonNames.ADD_COMPONENT, e)
//    }
//  }
//  private def connectComponentToStep(receivedMessage: JsValue, admin: Admin): JsValue = {
//    Json.fromJson[JsonStepIn](receivedMessage) match {
//      case stepIn : JsSuccess[JsonStepIn] => Json.toJson(admin.connectComponentToStep(stepIn.get))
//      case e : JsError => jsonError(JsonNames.CONNECT_COMPONENT_TO_STEP, e)
//    }
//  }
//
////  private def connectComponentToStep(receivedMessage: JsValue, admin: Admin): JsValue = {
////    val connectionComponentToStepIn: JsResult[JsonConnectionComponentToStepIn] =
////      Json.fromJson[JsonConnectionComponentToStepIn](receivedMessage)
////    connectionComponentToStepIn match {
////      case s : JsSuccess[JsonConnectionComponentToStepIn] => s.get
////      case e : JsError => Logger.error("Errors -> CONNECTION_COMPONENT_TO_STEP: " + JsError.toJson(e).toString())
////    }
////    val connectionComponentToStepOut: JsonConnectionComponentToStepOut =
////      admin.connectComponentToStep(connectionComponentToStepIn.get)
////    Json.toJson(connectionComponentToStepOut)
////  }
//
//  private def configTree(receivedMessage: JsValue, admin: Admin): JsValue = {
//    Json.fromJson[JsonConfigTreeIn](receivedMessage) match {
//      case configTreeIn: JsSuccess[JsonConfigTreeIn] => Json.toJson(admin.getConfigTree(configTreeIn.get))
//      case e : JsError => jsonError(JsonNames.CONFIG_TREE, e)
//    }
//  }
//
//  private def createDependency(receivedMessage: JsValue, admin: Admin): JsValue = {
//    val dependencyIn: JsResult[JsonDependencyIn] = Json.fromJson[JsonDependencyIn](receivedMessage)
//    dependencyIn match {
//      case s: JsSuccess[JsonDependencyIn] => s.get
//      case e: JsError => Logger.error("Errors -> CREATE_DEPENDENCY: " + JsError.toJson(e).toString())
//    }
//
//    val dependencyOut: JsonDependencyOut  = admin.createDependency(dependencyIn.get)
//    Json.toJson(dependencyOut)
//   }
//
//  private def visualProposalForAdditionalStepsInOneLevel(
//      receivedMessage: JsValue, admin: Admin): JsValue = {
//    val visualProposalForAdditionalStepsInOneLevelIn: JsResult[JsonVisualProposalForAdditionalStepsInOneLevelIn] =
//      Json.fromJson[JsonVisualProposalForAdditionalStepsInOneLevelIn](receivedMessage)
//    visualProposalForAdditionalStepsInOneLevelIn match {
//      case s: JsSuccess[JsonVisualProposalForAdditionalStepsInOneLevelIn] => s.get
//      case e: JsError =>
//        Logger.error("Errors -> VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL: " +
//            JsError.toJson(e).toString())
//    }
//
//    val stepOut = admin.visualProposalForAdditionalStepsInOneLevel(
//        visualProposalForAdditionalStepsInOneLevelIn.get)
//    Json.toJson(stepOut)
//  }
//
//  private def deleteComponent(receivedMessage: JsValue, admin: Admin): JsValue = {
//    val deleteComponentIn: JsResult[JsonComponentIn] = Json.fromJson[JsonComponentIn](receivedMessage)
//    deleteComponentIn match {
//      case s: JsSuccess[JsonComponentIn] => Json.toJson(admin.deleteComponent(s.get))
//      case e: JsError => jsonError(JsonNames.DELETE_COMPONENT, e)
//    }
//  }
//
//  private def updateComponent(receivedMessage: JsValue, admin: Admin): JsValue = {
//    val updateComponentIn: JsResult[JsonComponentIn] = Json.fromJson[JsonComponentIn](receivedMessage)
//    updateComponentIn match {
//      case s: JsSuccess[JsonComponentIn] => Json.toJson(admin.updateComponent(s.get))
//      case e: JsError => jsonError(JsonNames.UPDATE_COMPONENT, e)
//    }
//  }

//  private def jsonError(errorText: String, e: JsError): JsValue = {
//    val error = JsonErrorIn(
//        JsonNames.ERROR,
//        JsonErrorParams(
//            "Errors -> " + errorText + " : " + JsError.toJson(e).toString()
//        )
//    )
//    Logger.error(error.toString)
//    Json.toJson(error)
//  }
}
