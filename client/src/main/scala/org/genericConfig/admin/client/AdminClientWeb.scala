package org.genericConfig.admin.client

import org.genericConfig.admin.client.component.Component
import org.genericConfig.admin.client.config.{DeleteConfig, EditConfig, GetConfig}
import org.genericConfig.admin.client.configTree.ConfigTree
import org.genericConfig.admin.client.registration.RegistrationPage
import org.genericConfig.admin.client.step.AddStep
import org.genericConfig.admin.client.user.UserPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.component.json.JsonComponentOut
import org.genericConfig.admin.shared.config.ConfigDTO
import org.genericConfig.admin.shared.config.json.{JsonDeleteConfigOut, JsonUpdateConfigOut}
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeOut
import org.genericConfig.admin.shared.step.json.JsonStepOut
import org.genericConfig.admin.shared.user.UserDTO
import org.scalajs.dom.raw.WebSocket
import play.api.libs.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.04.2018
 */
class AdminClientWeb(webSocket: WebSocket) {


  def handleMessage(receivedMessage: JsValue): Any = {
    (receivedMessage \ "action").asOpt[String] match {
      case Some(Actions.ADD_USER) => addUser(receivedMessage)
      case Some(Actions.GET_USER) => getUser(receivedMessage)
      case Some(Actions.ADD_CONFIG) => addConfig(receivedMessage)
      case Some(Actions.GET_CONFIGS) => getConfigs(receivedMessage)
//      case Some(JsonNames.DELET_CONFIG) => deleteConfig(receivedMessage)
//      case Some(JsonNames.UPDATE_CONFIG) => updateConfig(receivedMessage)
//      case Some(JsonNames.ADD_STEP) => addStep(receivedMessage)
//      case Some(JsonNames.CONFIG_TREE) => configTree(receivedMessage)
//      case Some(JsonNames.ADD_COMPONENT) => addComponent(receivedMessage)
//      case Some(JsonNames.CONNECT_COMPONENT_TO_STEP) => ??? //connectComponentToStep(receivedMessage, admin)
//      case Some(JsonNames.CREATE_DEPENDENCY) => ??? //createDependency(receivedMessage, admin)
//      case Some(JsonNames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL) => ???
//        visualProposalForAdditionalStepsInOneLevel(receivedMessage, admin)
      case _ => Json.obj("error" -> "keinen Treffer")
    }
  }

  private def addUser(receivedMessage: JsValue): Unit = {
    Json.fromJson[UserDTO](receivedMessage) match {
      case addUserResult: JsSuccess[UserDTO] =>
        new RegistrationPage(webSocket).drawRegistrationPage(addUserResult.get.result.get.errors)
      case e: JsError => println("Errors -> " + Actions.ADD_USER + ": " + JsError.toJson(e).toString())
    }
  }

  private def getUser(receivedMessage: JsValue): Unit = {
    Json.fromJson[UserDTO](receivedMessage) match {
      case getUserResult: JsSuccess[UserDTO] => new UserPage(webSocket).drawUserPage(getUserResult.get)
      case e: JsError => println("Errors -> " + Actions.GET_USER + ": " + JsError.toJson(e).toString())
    }
  }

  private def addConfig(receivedMessage: JsValue) = {
    Json.fromJson[ConfigDTO](receivedMessage) match {
      case addConfigResult : JsSuccess[ConfigDTO] =>
        new GetConfig(webSocket = webSocket).update(addConfigResult.value)
      case e: JsError => println("Errors -> : " + Actions.ADD_CONFIG + JsError.toJson(e).toString())
    }
  }

  private def getConfigs(receivedMessage: JsValue): Unit = {
    val getConfigsParams: JsResult[ConfigDTO] = Json.fromJson[ConfigDTO](receivedMessage)
    getConfigsParams match {
      case s: JsSuccess[ConfigDTO] => new GetConfig(webSocket).drawAllConfigs(getConfigsParams.get)
      case e: JsError => println("Error -> : " + Actions.GET_CONFIGS + " -> " + JsError.toJson(e).toString())
    }
  }










  private def addComponent(receivedMessage: JsValue): Unit = {
    val jsonComponentOut: JsResult[JsonComponentOut] = Json.fromJson[JsonComponentOut](receivedMessage)
    jsonComponentOut match {
      case jCOut: JsSuccess[JsonComponentOut] => new Component(webSocket).updateStatus(jCOut.value)
      case e: JsError => println("Errors -> " + JsonNames.ADD_COMPONENT + ": " + JsError.toJson(e).toString())
    }
  }



  private def configTree(receivedMessage: JsValue) = {
    val configTreeOut: JsResult[JsonConfigTreeOut] = Json.fromJson[JsonConfigTreeOut](receivedMessage)
    configTreeOut match {
      case s: JsSuccess[JsonConfigTreeOut] => s.get
      case e: JsError => println("Errors -> CREATE_DEPENDENCY: " + JsError.toJson(e).toString())
    }
    new ConfigTree(webSocket).drawConfigTree(configTreeOut.get)
  }



  private def deleteConfig(receivedMessage: JsValue) = {
    val deleteConfigOut: JsResult[JsonDeleteConfigOut] = Json.fromJson[JsonDeleteConfigOut](receivedMessage)
    deleteConfigOut match {
      case s: JsSuccess[JsonDeleteConfigOut] => s.get
      case e: JsError => println("Errors -> CREATE_CONFIG: " + JsError.toJson(e).toString())
    }
    new DeleteConfig(webSocket).updateStatus(deleteConfigOut.get)
  }

  private def updateConfig(receivedMessage: JsValue) = {
    val updateConfigOut: JsResult[JsonUpdateConfigOut] = Json.fromJson[JsonUpdateConfigOut](receivedMessage)
    updateConfigOut match {
      case s: JsSuccess[JsonUpdateConfigOut] => s.get
      case e: JsError => println("Errors -> CREATE_CONFIG: " + JsError.toJson(e).toString())
    }
    new EditConfig(webSocket).updateStatus(updateConfigOut.get)
  }

  private def addStep(receivedMessage: JsValue) = {
    val addFirstStepOut: JsResult[JsonStepOut] = Json.fromJson[JsonStepOut](receivedMessage)
    addFirstStepOut match {
      case s: JsSuccess[JsonStepOut] => s.get
      case e: JsError => println("Errors -> ADD_FIRST_STEP: " + JsError.toJson(e).toString())
    }
    new AddStep(webSocket).updateStatus(addFirstStepOut.get)
  }
}