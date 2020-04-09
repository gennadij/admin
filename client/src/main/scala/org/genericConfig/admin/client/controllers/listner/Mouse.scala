package org.genericConfig.admin.client.controllers.listner

import org.genericConfig.admin.client.models.Start
import org.genericConfig.admin.shared.Actions
import org.scalajs.jquery.JQuery

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.04.2020
 */
class Mouse {

  def mouseClick(jQueryElem: JQuery, action : String): Unit = {
    action match {
      case Actions.GET_USER =>
        jQueryElem.on("click", () => {new Start().actionGetUser()})
      case Actions.ADD_USER =>
        jQueryElem.on("click", () => {println("new RegistrationPage().drawRegistrationPage(None)")})
//        new RegistrationPage().drawRegistrationPage(None)
      case _ => ???
    }

  }

  def getUser() = {}
}
