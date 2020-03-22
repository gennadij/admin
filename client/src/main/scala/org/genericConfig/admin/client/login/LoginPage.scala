package org.genericConfig.admin.client.login

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




class LoginPage extends  CommonFunction{

  def drawLoginPage(webSocket: WebSocket, errors: Option[List[ErrorDTO]]): JQuery = {

    errors match {
      case Some(errors) => drawNewStatus(errors.head.name)
      case None =>
        drawNewStatus("Kein Fehler")
    }

    cleanPage
    val html =
      "<dev id='main' class='main'>" +
      "<p>Login Page</p> </br>"  +
        drawInputField("username", "Benutzername") + "</br>" + "</br>" +
        drawInputField("password", "Passwort") + "</br>" + "</br>" +
        drawButton("login", "Anmelden") +
        drawButton("startPage", "startPage") +
    "</dev>"

    drawNewMain(html)

    jQuery("#login").on("click", () => login(webSocket))
    jQuery("#startPage").on("click", () => startPage(webSocket))
  }

  def startPage(webSocket: WebSocket) : Unit = {
    new StartPage(webSocket).drawStartPage()
  }

  def login(webSocket: WebSocket): Unit = {
    val getUser = Json.toJson(
      UserDTO(
        action = Actions.GET_USER,
        params = Some(UserParamsDTO(
          username = jQuery("#username").value().toString,
          password = jQuery("#password").value().toString,
          update = None,

        )),
        result = None
      )
    ).toString
    println("OUT -> " + getUser)
    webSocket.send(getUser)
  }
}