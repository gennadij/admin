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

//  private def addComponent(receivedMessage: JsValue): Unit = {
//    val jsonComponentOut: JsResult[JsonComponentOut] = Json.fromJson[JsonComponentOut](receivedMessage)
//    jsonComponentOut match {
//      case jCOut: JsSuccess[JsonComponentOut] => new Component(webSocket).updateStatus(jCOut.value)
//      case e: JsError => println("Errors -> " + JsonNames.ADD_COMPONENT + ": " + JsError.toJson(e).toString())
//    }
//  }
//  private def configTree(receivedMessage: JsValue) = {
//    val configTreeOut: JsResult[JsonConfigTreeOut] = Json.fromJson[JsonConfigTreeOut](receivedMessage)
//    configTreeOut match {
//      case s: JsSuccess[JsonConfigTreeOut] => s.get
//      case e: JsError => println("Errors -> CREATE_DEPENDENCY: " + JsError.toJson(e).toString())
//    }
//    new ConfigTree(webSocket).drawConfigTree(configTreeOut.get)
//  }


}