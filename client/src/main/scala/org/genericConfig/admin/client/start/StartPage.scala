package org.genericConfig.admin.client.start

import org.genericConfig.admin.client.login.LoginPage
import org.genericConfig.admin.client.registration.RegistrationPage
import org.scalajs.dom.WebSocket
import org.scalajs.jquery.{JQuery, jQuery}
import util.{CommonFunction, HtmlElementIds}

class StartPage(webSocket: WebSocket) extends CommonFunction{


  def drawStartPage(): JQuery = {
    cleanPage

    val html =
      "<dev id='main' class='main'>" +
        "<p>Start Page</p> </br>"  +
        drawButton("login", "Anmelden") +
        drawButton("registration", "Registrieren") +
      "</dev>"
    drawNewMain(html)

    jQuery("#login").on("click", () => login)
    jQuery("#registration").on("click", () => registration)
  }

  def login() : Unit = {
    new LoginPage().drawLoginPage(webSocket, None)
  }

  def registration() : Unit = {
    new RegistrationPage().drawRegistrationPage(webSocket, None)
  }

}
