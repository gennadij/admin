package org.genericConfig.admin.client.registration

import org.genericConfig.admin.client.start.StartPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import org.scalajs.dom.WebSocket
import org.scalajs.jquery.{JQuery, jQuery}
import play.api.libs.json.Json
import util.CommonFunction

import scala.scalajs.js.Any.fromFunction0

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 12.04.2018
 */

class RegistrationPage(webSocket: WebSocket) extends CommonFunction {

  def drawRegistrationPage(errors: Option[List[ErrorDTO]]): JQuery = {

    cleanPage

    errors match {
      case Some(errors) => drawNewStatus(errors.head.name)
      case None =>
        drawNewStatus("Kein Fehler")
    }

    val html =
      "<dev id='main' class='main'>" +
        "<p>Registration Page</p> </br>"  +
        drawInputField("username", "Benutzername") + "</br>" + "</br>" +
        drawInputField("password", "Passwort") + "</br>" + "</br>" +
        drawButton("registration", "Registration") +
        drawButton("startPage", "startPage") +
        "</dev>"

    drawNewMain(html)

    jQuery("#startPage").on("click", () => startPage(webSocket))

    jQuery("#registration").on("click", () => registration(webSocket))
  }

  def startPage(webSocket: WebSocket) : Unit = {
    new StartPage(webSocket).drawStartPage()
  }

  def registration(webSocket: WebSocket): Unit = {
    val addUser = Json.toJson(
      UserDTO(
        action = Actions.ADD_USER,
        params = Some(UserParamsDTO(
          username = jQuery("#username").value().toString,
          password = jQuery("#password").value().toString,
          update = None,
        )),
        result = None
      )
    ).toString
    println("OUT -> " + addUser)
    webSocket.send(addUser)
  }
}