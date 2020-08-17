package org.genericConfig.admin.client.views.user

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.controllers.websocket.ActionsForClient
import org.genericConfig.admin.client.views.StartPage
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.scalajs.jquery.{JQuery, jQuery}
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 12.04.2018
 */

class AddUserPage() extends CommonFunction {

  def drawAddUserPage(errors: Option[List[ErrorDTO]]): Unit = {

    cleanPage

    errors match {
      case Some(errors) =>
        HtmlElementText.drawErrorAlert(errors.head)
        new StartPage().drawStartPage(Some(errors))
      case None =>
        val main : JQuery = HtmlElementText.mainPage("Registrierung")
        val jQueryInputFieldUsername : JQuery = HtmlElementText.drawInputField("username", "Benutzername")
        val jQueryInputFieldPassword : JQuery= HtmlElementText.drawInputField("password", "Password", typeofInput = "password")
        val jQueryButtonRegister : JQuery = HtmlElementText.drawButton("register", "Benutzer hinzufügen")
        val jQueryButtonStart : JQuery = HtmlElementText.drawButton("start", "Startseite")

        main.appendTo(jQuery(HtmlElementIds.section))

        jQueryInputFieldUsername.appendTo(main)
        jQueryInputFieldPassword.appendTo(main)
        jQueryButtonRegister.appendTo(main)
        jQueryButtonStart.appendTo(main)

        new Mouse().mouseClick(jQueryButtonRegister, Actions.ADD_USER)
        new Mouse().mouseClick(jQueryButtonStart, ActionsForClient.START_PAGE)
    }
  }
}