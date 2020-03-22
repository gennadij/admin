package org.genericConfig.admin.client

import org.genericConfig.admin.client.component.Component
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.JsResult
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsError
import org.scalajs.dom.raw.WebSocket
import org.genericConfig.admin.client.config.GetConfig
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.config.json._
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeOut
import org.genericConfig.admin.client.config.CreateConfig
import org.genericConfig.admin.client.config.DeleteConfig
import org.genericConfig.admin.client.config.EditConfig
import org.genericConfig.admin.client.configTree.ConfigTree
import org.genericConfig.admin.client.registration.RegistrationPage
import org.genericConfig.admin.shared.step.json.JsonStepOut
import org.genericConfig.admin.client.step.AddStep
import org.genericConfig.admin.client.user.GetUser
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentOut}
import org.genericConfig.admin.shared.error.json.{JsonErrorIn, JsonErrorParams}
import org.genericConfig.admin.shared.user.UserDTO
import org.genericConfig.admin.shared.user.json.JsonUserOut

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.04.2018
 */
class AdminClientWeb(websocket: WebSocket) {
  
  
  def handleMessage(receivedMessage: JsValue): Any = {
    (receivedMessage \ "action").asOpt[String] match {
      case Some(Actions.ADD_USER) => addUser(receivedMessage)
      case Some(Actions.GET_USER) => getUser(receivedMessage)
//      case Some(JsonNames.ADD_CONFIG) => addConfig(receivedMessage)
//      case Some(JsonNames.GET_CONFIGS) => getConfigs(receivedMessage)
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

  private def getUser(receivedMessage: JsValue): Unit = {
    val userDTO: JsResult[UserDTO] = Json.fromJson[UserDTO](receivedMessage)
    userDTO match {
      case s: JsSuccess[UserDTO] => new GetUser(websocket).drawUser(userDTO.get)
      case e: JsError => println("Errors -> " + JsonNames.GET_USER + ": " + JsError.toJson(e).toString())
    }
  }

  private def addUser(receivedMessage: JsValue): Unit = {
    val userDTO: JsResult[UserDTO] = Json.fromJson[UserDTO](receivedMessage)
    userDTO match {
      case s: JsSuccess[UserDTO] => new RegistrationPage().drawRegistrationPage(websocket, userDTO.get.result.get.errors)
      case e: JsError => println("Errors -> " + JsonNames.GET_USER + ": " + JsError.toJson(e).toString())
    }
  }

  private def addComponent(receivedMessage: JsValue): Unit = {
    val jsonComponentOut: JsResult[JsonComponentOut] = Json.fromJson[JsonComponentOut](receivedMessage)
    jsonComponentOut match {
      case jCOut: JsSuccess[JsonComponentOut] => new Component(websocket).updateStatus(jCOut.value)
      case e: JsError => println("Errors -> " + JsonNames.ADD_COMPONENT + ": " + JsError.toJson(e).toString())
    }
  }

  private def getConfigs(receivedMessage: JsValue): Unit = {
    val getConfigsIn: JsResult[JsonGetConfigsOut] = Json.fromJson[JsonGetConfigsOut](receivedMessage)
    getConfigsIn match {
      case s: JsSuccess[JsonGetConfigsOut] => new GetConfig(websocket).drawAllConfigs(getConfigsIn.get)
      case e: JsError => println("Error -> : " + JsonNames.GET_CONFIGS + " -> " + JsError.toJson(e).toString())
    }
  }
  
  private def configTree(receivedMessage: JsValue) = {
    val configTreeOut: JsResult[JsonConfigTreeOut] = Json.fromJson[JsonConfigTreeOut](receivedMessage)
    configTreeOut match {
      case s: JsSuccess[JsonConfigTreeOut] => s.get
      case e: JsError => println("Errors -> CREATE_DEPENDENCY: " + JsError.toJson(e).toString())
    }
    new ConfigTree(websocket).drawConfigTree(configTreeOut.get)
  }
  
  private def addConfig(receivedMessage: JsValue) = {
    val createConfigOut: JsResult[JsonAddConfigOut] = Json.fromJson[JsonAddConfigOut](receivedMessage)
    createConfigOut match {
      case s: JsSuccess[JsonAddConfigOut] => s.get
      case e: JsError => println("Errors -> ADD_CONFIG: " + JsError.toJson(e).toString())
    }
    new CreateConfig(websocket, "").updateStatus(createConfigOut.get)
   }
  
  private def deleteConfig(receivedMessage: JsValue) = {
    val deleteConfigOut: JsResult[JsonDeleteConfigOut] = Json.fromJson[JsonDeleteConfigOut](receivedMessage)
    deleteConfigOut match {
      case s: JsSuccess[JsonDeleteConfigOut] => s.get
      case e: JsError => println("Errors -> CREATE_CONFIG: " + JsError.toJson(e).toString())
    }
    new DeleteConfig(websocket).updateStatus(deleteConfigOut.get)
  }
  
  private def updateConfig(receivedMessage: JsValue) = {
    val updateConfigOut: JsResult[JsonUpdateConfigOut] = Json.fromJson[JsonUpdateConfigOut](receivedMessage)
    updateConfigOut match {
      case s: JsSuccess[JsonUpdateConfigOut] => s.get
      case e: JsError => println("Errors -> CREATE_CONFIG: " + JsError.toJson(e).toString())
    }
    new EditConfig(websocket).updateStatus(updateConfigOut.get)
  }
  
  private def addStep(receivedMessage: JsValue) = {
    val addFirstStepOut: JsResult[JsonStepOut] = Json.fromJson[JsonStepOut](receivedMessage)
    addFirstStepOut match {
      case s: JsSuccess[JsonStepOut] => s.get
      case e: JsError => println("Errors -> ADD_FIRST_STEP: " + JsError.toJson(e).toString())
    }
    new AddStep(websocket).updateStatus(addFirstStepOut.get)
  }
}