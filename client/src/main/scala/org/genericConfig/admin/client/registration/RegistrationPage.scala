package org.genericConfig.admin.client.registration

import org.genericConfig.admin.client.start.StartPage
import org.scalajs.dom.WebSocket
import org.scalajs.jquery.{JQuery, jQuery}
import util.CommonFunction

import scala.scalajs.js.Any.fromFunction0

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 12.04.2018
 */

class RegistrationPage extends CommonFunction {

  def drawRegistrationPage(webSocket: WebSocket): JQuery = {
    cleanPage
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
    new StartPage().drawStartPage(webSocket)
  }

  def registration(webSocket: WebSocket): Unit = {
    println("Sende ADD_USER")
  }
}