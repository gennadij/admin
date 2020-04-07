package org.genericConfig.admin.client.start

import org.genericConfig.admin.client.registration.RegistrationPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import org.scalajs.dom.WebSocket
import org.scalajs.jquery.{JQuery, jQuery}
import play.api.libs.json.Json
import util.CommonFunction

class StartPage(webSocket: WebSocket) extends CommonFunction{


  def drawStartPage(errors : Option[List[ErrorDTO]] = None) : JQuery = {

    cleanPage

    errors match {
      case Some(errors) =>
        drawNewStatus(errors.head.name)
      case None =>
        drawNewStatus("Kein Fehler")
    }



    val html =
      "<dev id='main' class='main'>" +
        "<p>Start Seite</p> </br>"  +
        drawInputField("username", "Benutzername") + "</br>" + "</br>" +
        drawInputField("password", "Passwort") + "</br>" + "</br>" +
        drawButton("login", "Anmelden") +
        drawButton("registration", "Registrieren") +
      "</dev>"
    drawNewMain(html)

    jQuery("#login").on("click", () => login)
    jQuery("#registration").on("click", () => registration)
  }

  def login() : Unit = {
    val getUser = Json.toJson(UserDTO(
      action = Actions.GET_USER,
      params = Some(UserParamsDTO(
        username = jQuery("#username").value().toString,
        password = jQuery("#password").value().toString,
        update = None,

      )),
      result = None
    )).toString
    println("OUT -> " + getUser)
    webSocket.send(getUser)
  }

  def registration() : Unit = {
    new RegistrationPage(webSocket).drawRegistrationPage(None)
  }

}
