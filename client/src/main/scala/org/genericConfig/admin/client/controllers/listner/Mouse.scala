package org.genericConfig.admin.client.controllers.listner

import org.genericConfig.admin.client.models.{Config, Register, Start, User}
import org.genericConfig.admin.client.views.config.AddConfigPage
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
      case Actions.REGISTER_PAGE => jQueryElem.on("click", () => new Register().register())
      case Actions.START_PAGE => jQueryElem.on("click", () => new Start().start())
      case Actions.USER_PAGE => jQueryElem.on("click", () => new User().showUser(param))
      case Actions.UPDATE_USER_PAGE => jQueryElem.on("click", () => new User().showUpdateUserPage(param))
      case Actions.ADD_CONFIG_PAGE => jQueryElem.on("click", () => new Config().showAddConfigPage(param))
      case Actions.CONFIG_PAGE => jQueryElem.on("click", () => new Config().showConfigPage(param))
      case _ => jQueryElem.on("click", () => new Start().actionGetUser()) //TODO eine Fehlerpage erstellen
    }
  }
}
