package org.genericConfig.admin.client.old.registration

import org.genericConfig.admin.client.controllers.websocket.WebSocket
import org.genericConfig.admin.client.views.StartPage
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

    jQuery("#startPage").on("click", () => startPage())

    jQuery("#registration").on("click", () => registration())
  }

  def startPage() : Unit = {
    new StartPage().drawStartPage()
  }

  def registration(): Unit = {
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
   WebSocket.webSocket.send(addUser)
  }
}