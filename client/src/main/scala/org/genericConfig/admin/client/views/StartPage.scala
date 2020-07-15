package org.genericConfig.admin.client.views

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.controllers.websocket.ActionsForClient
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.scalajs.jquery.{JQuery, jQuery}
import util.CommonFunction

class StartPage() extends CommonFunction{


  def drawStartPage(errors : Option[List[ErrorDTO]] = None) : Unit = {

    cleanPage

    val main : JQuery = HtmlElementText.mainPage("Administrator für generischer Konfigurator")
    val jQueryInputFieldUsername : JQuery = HtmlElementText.drawInputField("username", "Benutzername")
    val jQueryInputFieldPassword : JQuery= HtmlElementText.drawInputField("password", "Password", typeofInput = "password")
    val jQueryButtonLogin : JQuery = HtmlElementText.drawButton("login", "Anmelden")
    val jQueryButtonRegister : JQuery = HtmlElementText.drawButton("register", "Neuer Benutzer hinzufügen")

    main.appendTo(jQuery(HtmlElementIds.section))

    jQueryInputFieldUsername.appendTo(main)
    jQueryInputFieldPassword.appendTo(main)
    jQueryButtonLogin.appendTo(main)
    jQueryButtonRegister.appendTo(main)

    new Mouse().mouseClick(jQueryButtonLogin, Actions.GET_USER)
    new Mouse().mouseClick(jQueryButtonRegister, ActionsForClient.REGISTER_PAGE)
  }
}
