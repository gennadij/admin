package org.genericConfig.admin.client

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
import org.genericConfig.admin.configTree.ConfigTree
import org.genericConfig.admin.client.config.CreateConfig
import org.genericConfig.admin.client.config.DeleteConfig
import org.genericConfig.admin.client.config.EditConfig

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.04.2018
 */
class AdminClienWeb(websocket: WebSocket) {
  
  
  def handleMessage(receivedMessage: JsValue) = {
    (receivedMessage \ "json").asOpt[String] match {
      case Some(JsonNames.REGISTRATION) => ???//register(receivedMessage, admin)
      case Some(JsonNames.LOGIN) => ???//login(receivedMessage, admin)
      case Some(JsonNames.CREATE_CONFIG) => createConfig(receivedMessage)
      case Some(JsonNames.GET_CONFIGS) => getConfigs(receivedMessage)
      case Some(JsonNames.DELET_CONFIG) => deleteConfig(receivedMessage)
      case Some(JsonNames.UPDATE_CONFIG) => updateConfig(receivedMessage)
      case Some(JsonNames.CREATE_FIRST_STEP) => ???//createFirstStep(receivedMessage, admin)
      case Some(JsonNames.CONFIG_TREE) => configTree(receivedMessage)
      case Some(JsonNames.CREATE_COMPONENT) => ???//createComponent(receivedMessage, admin)
      case Some(JsonNames.CREATE_STEP) => ???//createStep(receivedMessage, admin)
      case Some(JsonNames.CONNECTION_COMPONENT_TO_STEP) => ???//connectComponentToStep(receivedMessage, admin)
      case Some(JsonNames.CREATE_DEPENDENCY) => ???//createDependency(receivedMessage, admin)
      case Some(JsonNames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL) => ???
//        visualProposalForAdditionalStepsInOneLevel(receivedMessage, admin)
      case _ => Json.obj("error" -> "keinen Treffer")
    }
  }
  
  private def getConfigs(receivedMessage: JsValue) = {
    val getConfigsIn: JsResult[JsonGetConfigsOut] = Json.fromJson[JsonGetConfigsOut](receivedMessage)
    getConfigsIn match {
      case s: JsSuccess[JsonGetConfigsOut] => s.get
      case e: JsError => println("Errors -> CREATE_DEPENDENCY: " + JsError.toJson(e).toString())
    }
    new GetConfig(websocket).drowAllConfigs(getConfigsIn.get)
    JsValue
  }
  
  private def configTree(receivedMessage: JsValue) = {
    val configTreeOut: JsResult[JsonConfigTreeOut] = Json.fromJson[JsonConfigTreeOut](receivedMessage)
    configTreeOut match {
      case s: JsSuccess[JsonConfigTreeOut] => s.get
      case e: JsError => println("Errors -> CREATE_DEPENDENCY: " + JsError.toJson(e).toString())
    }
    new ConfigTree(websocket).drawConfigTree(configTreeOut.get)
  }
  
  private def createConfig(receivedMessage: JsValue) = {
    val createConfigOut: JsResult[JsonCreateConfigOut] = Json.fromJson[JsonCreateConfigOut](receivedMessage)
    createConfigOut match {
      case s: JsSuccess[JsonCreateConfigOut] => s.get
      case e: JsError => println("Errors -> CREATE_CONFIG: " + JsError.toJson(e).toString())
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
}