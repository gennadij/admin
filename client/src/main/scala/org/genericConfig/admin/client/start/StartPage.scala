package org.genericConfig.admin.client.start

import org.genericConfig.admin.client.login.LoginPage
import org.genericConfig.admin.client.registration.RegistrationPage
import org.scalajs.dom.WebSocket
import org.scalajs.jquery.{JQuery, jQuery}
import util.{CommonFunction, HtmlElementIds}

class StartPage extends CommonFunction{
  def drawStartPage(webSocket: WebSocket): JQuery = {
    cleanPage

    val html =
      "<dev id='main' class='main'>" +
        "<p>Start Page</p> </br>"  +
        drawButton("login", "Anmelden") +
        drawButton("registration", "Registrieren") +
      "</dev>"
    drawNewMain(html)

    jQuery("#login").on("click", () => login(webSocket))
    jQuery("#registration").on("click", () => registration(webSocket))
  }

  def login(webSocket: WebSocket): Unit = {
    new LoginPage().drawLoginPage(webSocket, None)
  }

  def registration(webSocket: WebSocket) : Unit = {
    new RegistrationPage().drawRegistrationPage(webSocket)
  }

}
