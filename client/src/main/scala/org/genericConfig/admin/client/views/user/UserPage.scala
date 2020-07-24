package org.genericConfig.admin.client.views.user

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.controllers.websocket.ActionsForClient
import org.genericConfig.admin.client.models.{Config, Progress, State}
import org.genericConfig.admin.client.views.StartPage
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.config.ConfigDTO
import org.genericConfig.admin.shared.user.UserDTO
import org.scalajs.jquery.{JQuery, jQuery}
import util.CommonFunction

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 04.06.2018
  */
class UserPage() extends CommonFunction{

  private def drawUserPage(userDTO: UserDTO): Unit = {

    cleanPage

    Progress.addState(State(userDTO = Some(userDTO)))

    userDTO.result.get.errors match {
      case None =>
        drawNewStatus("Kein Fehler")

        val main : JQuery = HtmlElementText.mainPage(s"Benutzer : ${userDTO.result.get.username.get}" )
        val jQueryButtonUpdateUser : JQuery = HtmlElementText.drawButton("updateUser", "Benutzer bearbeiten")
        val jQueryButtonDeleteUser : JQuery = HtmlElementText.drawButton("deleteUser", "Benutzer löschen")
        val jQueryButtonLogout : JQuery = HtmlElementText.drawButton("start", "Auslogen")

        main.appendTo(jQuery(HtmlElementIds.section))

        jQueryButtonUpdateUser.appendTo(main)
        jQueryButtonDeleteUser.appendTo(main)
        jQueryButtonLogout.appendTo(main)

        new Mouse().mouseClick(jQueryButtonUpdateUser, ActionsForClient.UPDATE_USER_PAGE, Some(userDTO))
        new Mouse().mouseClick(jQueryButtonLogout, ActionsForClient.START_PAGE)
      case _ =>
        new StartPage().drawStartPage(userDTO.result.get.errors)
    }
  }

  def drawUserPageWithConfigPage(userDTO: Option[UserDTO] = None, configDTO: Option[ConfigDTO] = None) : Unit = {
    (userDTO, configDTO) match {
      case (Some(userDTO), None) =>
        drawUserPage(userDTO)
        new Config().getConfigs(Some(userDTO))
      case (None, Some(configDTO)) =>
        new Config().showAllConfigsInUserPage(Some(configDTO))
      case (_) => println("Undefinierter Zustand") //TODO Fehlerseite darstellen
    }
  }
}
