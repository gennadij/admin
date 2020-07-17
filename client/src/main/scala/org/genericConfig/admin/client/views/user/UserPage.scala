package org.genericConfig.admin.client.views.user

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.controllers.websocket.{ActionsForClient, WebSocketListner}
import org.genericConfig.admin.client.models.{Progress, State}
import org.genericConfig.admin.client.views.StartPage
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO, UserConfigDTO}
import org.genericConfig.admin.shared.user.UserDTO
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

    Progress.addState(State(userDTO = Some(userDTO)))

    userDTO.result.get.errors match {
      case None =>
        drawNewStatus("Kein Fehler")

        val main : JQuery = HtmlElementText.mainPage(s"Benutzer : ${userDTO.result.get.username.get}" )
//        val jQueryButtonGetConfigs : JQuery = HtmlElementText.drawButton("getConfigs", "Konfiguratoren")
        val jQueryButtonUpdateUser : JQuery = HtmlElementText.drawButton("updateUser", "Benutzer bearbeiten")
        val jQueryButtonDeleteUser : JQuery = HtmlElementText.drawButton("deleteUser", "Benutzer löschen")
        val jQueryButtonLogout : JQuery = HtmlElementText.drawButton("start", "Auslogen")

        main.appendTo(jQuery(HtmlElementIds.section))

//        jQueryButtonGetConfigs.appendTo(main)
        jQueryButtonUpdateUser.appendTo(main)
        jQueryButtonDeleteUser.appendTo(main)
        jQueryButtonLogout.appendTo(main)

//        new Mouse().mouseClick(jQueryButtonGetConfigs, Actions.GET_CONFIGS, Some(userDTO))
        new Mouse().mouseClick(jQueryButtonUpdateUser, ActionsForClient.UPDATE_USER_PAGE, Some(userDTO))
//        new Mouse().mouseClick(jQueryButtonDeleteUser, Actions.DELETE_USER, Some(userDTO))
        new Mouse().mouseClick(jQueryButtonLogout, ActionsForClient.START_PAGE)
      case _ =>
        new StartPage().drawStartPage(userDTO.result.get.errors)
    }
  }

  def drawUserWithConfigPage(userDTO: Option[UserDTO] = None, configDTO: Option[ConfigDTO] = None) : Unit = {
    (userDTO, configDTO) match {
      case (Some(userDTO), Some(configDTO)) => println("Beide") //Wenn sowohl User als auch Config DTO gegeben wird
      case (Some(userDTO), None) =>
        drawUserPage(userDTO)
        //sende getConfigs
        val getConfigParams = Json.toJson(ConfigDTO(
          action = Actions.GET_CONFIGS,
          params = Some(ConfigParamsDTO(
            userId = userDTO.result.get.userId
          )),
          result = None
        )).toString
        println("OUT -> " + getConfigParams)
        WebSocketListner.webSocket.send(getConfigParams)
      case (None, Some(configDTO)) =>
        val main : JQuery = jQuery(HtmlElementIds.mainJQuery)
        val jQueryBr : JQuery = jQuery("</br> </br> </br>")
        val jQueryMainConfigs : JQuery = jQuery("<center> <h3>Konfiguratoren</h3> </center>")

        val configurationsJQuery : List[(JQuery, UserConfigDTO)] = configDTO.result.get.configs.get.map(config => {
          (HtmlElementText.drawButton(config.configId.get, config.configUrl.get), config)
        })

        val jQueryButtonAddConfig : JQuery = HtmlElementText.drawButton(HtmlElementIds.addConfigHtml, "Konfigurator hinzufügen")
        val jQueryButtonLogout : JQuery = HtmlElementText.drawButton("start", "Auslogen")

        jQueryBr.appendTo(main)
        jQueryMainConfigs.appendTo(main)
        val configDiv = jQuery("<div id='configs'></div>")

        configurationsJQuery.foreach(config => {
          config._1.appendTo(configDiv)
        })
        configDiv.appendTo(main)
        jQuery("</br> </br> </br>").appendTo(main)
        jQueryButtonAddConfig.appendTo(main)
        jQueryButtonLogout.appendTo(main)

        configurationsJQuery.foreach(configJQuery => {
          new Mouse().mouseClick(jQueryElem = configJQuery._1, action = ActionsForClient.CONFIG_PAGE, param = Some(configJQuery._2))
        })
        new Mouse().mouseClick(jQueryButtonAddConfig, ActionsForClient.ADD_CONFIG_PAGE, Some(configDTO))
        new Mouse().mouseClick(jQueryButtonLogout, ActionsForClient.START_PAGE)

      case (None, None) => println("keine")
    }
  }
}
