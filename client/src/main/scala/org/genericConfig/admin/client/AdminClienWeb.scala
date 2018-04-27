package org.genericConfig.admin.client

import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.JsResult
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsError
import org.scalajs.dom.raw.WebSocket
import org.genericConfig.admin.client.config.GetConfig
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.config.json.JsonGetConfigsOut
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeOut
import org.genericConfig.admin.configTree.ConfigTree

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
      case Some(JsonNames.CREATE_CONFIG) => ???//createConfig(receivedMessage, admin)
      case Some(JsonNames.GET_CONFIGS) => getConfigs(receivedMessage)
      case Some(JsonNames.DELET_CONFIG) => ???//deleteConfig(receivedMessage, admin)
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
  
  
}