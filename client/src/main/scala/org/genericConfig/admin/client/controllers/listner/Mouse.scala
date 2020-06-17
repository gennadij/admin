package org.genericConfig.admin.client.controllers.listner

import org.genericConfig.admin.client.controllers.websocket.ActionsForClient
import org.genericConfig.admin.client.models.{Config, ConfigGraph, Register, Start, User}
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
        //Actions internal
      case ActionsForClient.REGISTER_PAGE => jQueryElem.on("click", () => new Register().register())
      case ActionsForClient.START_PAGE => jQueryElem.on("click", () => new Start().start())
      case ActionsForClient.USER_PAGE => jQueryElem.on("click", () => new User().showUser(param))
      case ActionsForClient.UPDATE_USER_PAGE => jQueryElem.on("click", () => new User().showUpdateUserPage(param))
      case ActionsForClient.ADD_CONFIG_PAGE => jQueryElem.on("click", () => new Config().showAddConfigPage(param))
      case ActionsForClient.CONFIG_PAGE => jQueryElem.on("click", () => new Config().showConfigPage(param))
      case ActionsForClient.CONFIG_GRAPH => jQueryElem.on("click", () => new ConfigGraph().showConfigGraph(param))
      case _ => jQueryElem.on("click", () => new Start().actionGetUser()) //TODO eine Fehlerpage erstellen
    }
  }
}
