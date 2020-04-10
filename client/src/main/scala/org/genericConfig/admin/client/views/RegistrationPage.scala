package org.genericConfig.admin.client.views

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.controllers.websocket.WebSocket
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import org.scalajs.jquery.{JQuery, jQuery}
import play.api.libs.json.Json
import util.CommonFunction

import scala.scalajs.js.Any.fromFunction0

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 12.04.2018
 */

class RegistrationPage() extends CommonFunction {

  def drawRegistrationPage(errors: Option[List[ErrorDTO]]): Unit = {

    cleanPage

    errors match {
      case Some(errors) => drawNewStatus(errors.head.name)
      case None =>
        drawNewStatus("Kein Fehler")
    }

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
    new Mouse().mouseClick(jQueryButtonStart, Actions.START_PAGE)
  }
}