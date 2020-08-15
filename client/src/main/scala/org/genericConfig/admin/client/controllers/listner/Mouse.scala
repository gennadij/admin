package org.genericConfig.admin.client.controllers.listner

import org.genericConfig.admin.client.controllers.websocket.ActionsForClient
import org.genericConfig.admin.client.models.{Component, Config, ConfigGraph, Register, Start, Step, User}
import org.genericConfig.admin.shared.Actions
import org.scalajs.jquery.JQuery

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.04.2020
 */
class Mouse {

  def mouseClick(jQueryElem: JQuery, action : String, param : Option[Any] = None): Unit = {
    //Param auf none Prüfen mit Exception
    action match {
        //Actions to Server
      case Actions.GET_USER => jQueryElem.on("click", () => new Start().actionGetUser())
      case Actions.ADD_USER => jQueryElem.on("click", () => new Register().actionAddUser())
      case Actions.GET_CONFIGS => jQueryElem.on("click", () => new Config().getConfigs(param))
      case Actions.UPDATE_USER => jQueryElem.on("click", () => new User().updateUsername(param))
      case Actions.DELETE_USER => jQueryElem.on("click", () => new User().deleteUser(param))
      case Actions.ADD_CONFIG => jQueryElem.on("click", () => new Config().addConfig(param))
      case Actions.CONFIG_GRAPH => jQueryElem.on("click", () => new ConfigGraph().configGraph(param))
      case Actions.UPDATE_STEP => jQueryElem.on("click" , () => new Step().updateStepRequest(param))
      case Actions.ADD_COMPONENT => jQueryElem.on("click", () => new Component().addComponentRequest(param))
      case Actions.ADD_STEP => jQueryElem.on("click", () => new Step().addStepRequest(param))
      case Actions.DELETE_STEP => jQueryElem.on("click", () => new Step().deleteStepRequest(param))
      case Actions.UPDATE_COMPONENT => jQueryElem.on("click", () => new Component().updateComponentRequest(param))
      case Actions.DELETE_COMPONENT => jQueryElem.on("click", () => new Component().deleteComponentRequest(param))
      case Actions.CONNECT_COMPONENT_TO_STEP => jQueryElem.on("click", () => new Component().connectComponentToStepRequest(param))
        //Actions internal
      case ActionsForClient.REGISTER_PAGE => jQueryElem.on("click", () => new Register().register())
      case ActionsForClient.START_PAGE => jQueryElem.on("click", () => new Start().start())
      case ActionsForClient.USER_PAGE => jQueryElem.on("click", () => new User().showUser(param))
      case ActionsForClient.UPDATE_USER_PAGE => jQueryElem.on("click", () => new User().showUpdateUserPage(param))
      case ActionsForClient.ADD_CONFIG_PAGE => jQueryElem.on("click", () => new Config().showAddConfigPage(param))
      case ActionsForClient.CONFIG_PAGE => jQueryElem.on("click", () => new Config().showConfigPage(param))
      case ActionsForClient.STEP_PAGE => jQueryElem.on("click", () => new Step().showStep(param))
      case ActionsForClient.COMPONENT_PAGE => jQueryElem.on("click", () => new Component().showComponentPage(param))
      case ActionsForClient.ADD_COMPONENT_PAGE => jQueryElem.on("click", () => new Component().showAddComponentPage(param))
      case ActionsForClient.ADD_STEP_PAGE => jQueryElem.on("click", () => new Step().showAddStepPage(param))
      case ActionsForClient.CONNECT_COMPONENT_TO_STEP_PAGE => jQueryElem.on("click", () => new Component().showConnectComponentToStepPage(param))
      case _ => jQueryElem.on("click", () => new Start().actionGetUser()) //TODO eine Fehlerpage erstellen
    }
  }
}
