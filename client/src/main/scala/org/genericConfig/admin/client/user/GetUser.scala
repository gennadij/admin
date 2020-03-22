package org.genericConfig.admin.client.user

import org.genericConfig.admin.client.config.CreateConfig
import org.genericConfig.admin.client.login.LoginPage
import org.genericConfig.admin.client.start.StartPage
import org.genericConfig.admin.shared.config.json.{JsonGetConfigsIn, JsonGetConfigsParams}
import org.genericConfig.admin.shared.user.UserDTO
import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json
import util.{CommonFunction, HtmlElementIds}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 04.06.2018
  */
class GetUser(webSocket: WebSocket) extends CommonFunction{

  def drawUser(userDTO: UserDTO): Unit = {

    cleanPage

    userDTO.result.get.errors match {
      case None =>
        drawNewStatus("Kein Fehler")
        val htmlMain : String =
          "<dev id='main' class='main'>" +
            "<p>User Page</p> </br>" +
            "<p> userId: " + userDTO.result.get.userId.get.subSequence(0, 6) + "</p>" +
            "<p> Username: " + userDTO.result.get.username.get + "</p>" +
            drawButton(HtmlElementIds.getConfigsHtml, "GetConfigs") +
            drawButton(HtmlElementIds.deleteConfigHtml, "DeleteUser") +
            drawButton(HtmlElementIds.updateConfigHtml, "UpdateUser") +
            drawButton(HtmlElementIds.startPageHtml, "startPage") +
            "</dev>"

        drawNewMain(htmlMain)

        jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => getConfigs(userDTO.result.get.userId.get))
        jQuery(HtmlElementIds.deleteConfigJQuery).on("click", () => deleteUser(userDTO.result.get.userId.get))
        jQuery(HtmlElementIds.updateConfigJQuery).on("click", () => updateUser(userDTO.result.get.userId.get))
        jQuery(HtmlElementIds.startPageJQuery).on("click", () => startPage)
      case _ =>
        drawNewStatus(userDTO.result.get.errors.get.head.name)
        new LoginPage().drawLoginPage(webSocket, userDTO.result.get.errors)
    }


  }

  private def startPage = {
    new StartPage(webSocket).drawStartPage()
  }
  private def getConfigs(userId: String) = {
    println("Get Configs")
    val getConfigsIn = Json.toJson(JsonGetConfigsIn(
      params = JsonGetConfigsParams(
        userId
      )
    )).toString
//    websocket.send(getConfigsIn)
  }
  
  private def deleteUser(userId: String) = {
    println("DeleteUser")
  }
  
  private def updateUser(userId: String) = {
    println("UpdateUser")
  }

}
