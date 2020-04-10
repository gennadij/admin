package org.genericConfig.admin.client.views

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.controllers.websocket
import org.genericConfig.admin.client.controllers.websocket.WebSocket
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import org.scalajs.jquery.{JQuery, jQuery}
import play.api.libs.json.Json
import util.CommonFunction

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 04.06.2018
  */
class UserPage() extends CommonFunction{

  def drawUserPage(userDTO: UserDTO): Unit = {

    cleanPage

    userDTO.result.get.errors match {
      case None =>
        drawNewStatus("Kein Fehler")

        val main : JQuery = HtmlElementText.mainPage(s"Benutzer : ${userDTO.result.get.username.get}" )
        val jQueryButtonGetConfigs : JQuery = HtmlElementText.drawButton("getConfigs", "Konfiguratoren")
        val jQueryButtonUpdateUser : JQuery = HtmlElementText.drawButton("updateUser", "Benutzer bearbeiten")
        val jQueryButtonDeleteUser : JQuery = HtmlElementText.drawButton("deleteUser", "Benutzer löschen")
        val jQueryButtonLogout : JQuery = HtmlElementText.drawButton("start", "Auslogen")

        main.appendTo(jQuery(HtmlElementIds.section))

        jQueryButtonGetConfigs.appendTo(main)
        jQueryButtonUpdateUser.appendTo(main)
        jQueryButtonDeleteUser.appendTo(main)
        jQueryButtonLogout.appendTo(main)

        new Mouse().mouseClick(jQueryButtonGetConfigs, Actions.GET_CONFIGS, Some(userDTO))
        new Mouse().mouseClick(jQueryButtonUpdateUser, Actions.UPDATE_USER_PAGE, Some(userDTO))
        new Mouse().mouseClick(jQueryButtonDeleteUser, Actions.DELETE_USER_PAGE)
        new Mouse().mouseClick(jQueryButtonLogout, Actions.START_PAGE)
      case _ =>
        new StartPage().drawStartPage(userDTO.result.get.errors)
    }


  }


  

  
//  private def updateUser(userDTO: UserDTO): Unit = {
//    new UpdateUserPage(WebSocket.webSocket).drawUpdateUserPage(userDTO)
//  }

}
