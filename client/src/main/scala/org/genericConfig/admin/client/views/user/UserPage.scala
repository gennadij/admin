package org.genericConfig.admin.client.views.user

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.models.{Progress, State}
import org.genericConfig.admin.client.views.StartPage
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.UserDTO
import org.scalajs.jquery.{JQuery, jQuery}
import util.CommonFunction

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 04.06.2018
  */
class UserPage() extends CommonFunction{

  def drawUserPage(userDTO: UserDTO): Unit = {

    cleanPage

    Progress.addState(State(userDTO = Some(userDTO)))

    userDTO.result.get.errors match {
      case None =>
        drawNewStatus("Kein Fehler")

        val main : JQuery = HtmlElementText.mainPage(s"Benutzer : ${userDTO.result.get.username.get}" )
        val jQueryButtonGetConfigs : JQuery = HtmlElementText.drawButton("getConfigs", "Konfiguratoren")
        val jQueryButtonUpdateUser : JQuery = HtmlElementText.drawButton("updateUser", "Benutzer bearbeiten")
        val jQueryButtonDeleteUser : JQuery = HtmlElementText.drawButton("deleteUser", "Benutzer löschen")
        val jQueryButtonLogout : JQuery = HtmlElementText.drawButton("start", "Auslogen")
        val jQueryBr : JQuery = jQuery("</br> </br> </br>")
        val jQueryMainConfigs : JQuery = jQuery("<center> <h3>Konfiguratoren</h3> </center>")

        main.appendTo(jQuery(HtmlElementIds.section))

        jQueryButtonGetConfigs.appendTo(main)
        jQueryButtonUpdateUser.appendTo(main)
        jQueryButtonDeleteUser.appendTo(main)
        jQueryButtonLogout.appendTo(main)
        jQueryBr.appendTo(main)
        jQueryMainConfigs.appendTo(main)


        new Mouse().mouseClick(jQueryButtonGetConfigs, Actions.GET_CONFIGS, Some(userDTO))
        new Mouse().mouseClick(jQueryButtonUpdateUser, Actions.UPDATE_USER_PAGE, Some(userDTO))
        new Mouse().mouseClick(jQueryButtonDeleteUser, Actions.DELETE_USER, Some(userDTO))
        new Mouse().mouseClick(jQueryButtonLogout, Actions.START_PAGE)
      case _ =>
        new StartPage().drawStartPage(userDTO.result.get.errors)
    }


  }

}
