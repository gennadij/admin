package org.genericConfig.admin.client.user

import org.genericConfig.admin.client.config.CreateConfig
import org.genericConfig.admin.client.login.LoginPage
import org.genericConfig.admin.client.start.StartPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.genericConfig.admin.shared.config.json.{JsonGetConfigsIn, JsonGetConfigsParams}
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO, UserUpdateDTO}
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
        jQuery(HtmlElementIds.deleteConfigJQuery).on("click", () => deleteUser(userDTO))
        jQuery(HtmlElementIds.updateConfigJQuery).on("click", () => updateUser(userDTO))
        jQuery(HtmlElementIds.startPageJQuery).on("click", () => startPage)
      case _ =>
        drawNewStatus(userDTO.result.get.errors.get.head.name)
        new LoginPage().drawLoginPage(webSocket, userDTO.result.get.errors)
    }


  }

  private def startPage = {
    new StartPage(webSocket).drawStartPage()
  }
  private def getConfigs(userId: String): Unit = {
    println("Get Configs")
    val getConfigParams = Json.toJson(ConfigDTO(
      action = Actions.GET_CONFIGS,
      params = Some(ConfigParamsDTO(
        userId = Some(userId)
      )),
      result = None
    )).toString
    println("OUT -> " + getConfigParams)
    webSocket.send(getConfigParams)
  }
  
  private def deleteUser(userDTO: UserDTO): Unit = {
    val deleteUsername = Json.toJson(
      UserDTO(
        action = Actions.DELETE_USER,
        params = Some(UserParamsDTO(
          username = userDTO.result.get.username.get,
          password = "",
          update = None,
        )),
        result = None
      )
    ).toString
    println("OUT -> " + deleteUsername)
    webSocket.send(deleteUsername)
    new StartPage(webSocket).drawStartPage()
  }
  
  private def updateUser(userDTO: UserDTO): Unit = {
    new UpdateUser(webSocket).drawUpdateUser(userDTO)
  }

}
