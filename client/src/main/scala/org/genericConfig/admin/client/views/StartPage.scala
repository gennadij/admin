package org.genericConfig.admin.client.views

import org.genericConfig.admin.client.registration.RegistrationPage
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
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

    val main : JQuery = HtmlElementText.mainPage("Administrator für generischer Konfigurator")
    val jQueryInputFieldUsername : JQuery = HtmlElementText.drawInputField("username", "Benutzername")
    val jQueryInputFieldPassword : JQuery= HtmlElementText.drawInputField("password", "Password", typeofInput = "password")
    val jQueryButtonLogin : JQuery = HtmlElementText.drawButton("login", "Anmelden")
    val jQueryButtonRegister : JQuery = HtmlElementText.drawButton("registration", "Registrieren")

    main.appendTo(jQuery(HtmlElementIds.section))

    jQueryInputFieldUsername.appendTo(main)
    jQueryInputFieldPassword.appendTo(main)
    jQueryButtonLogin.appendTo(main)
    jQueryButtonRegister.appendTo(main)

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
