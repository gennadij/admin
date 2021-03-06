package org.genericConfig.admin.client.views.config

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.controllers.websocket.ActionsForClient
import org.genericConfig.admin.client.models.{Progress, State}
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO, UserConfigDTO}
import org.scalajs.jquery.{JQuery, jQuery}
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.04.2018
 */
class ConfigPage() extends CommonFunction{

  def update(addConfigResult: ConfigDTO): Unit = {
//    getConfigs(addConfigResult.result.get.userId.get)
  }

  def drawConfigPage(userConfigDTO: UserConfigDTO): Unit = {

    cleanPage

    val main : JQuery = HtmlElementText.mainPage(s"Konfigurator" )

    val jQueryButtonShowConfigGraph : JQuery = HtmlElementText.drawButton("configGraph", "Graph")
    val jQueryButtonUpdateConfig : JQuery = HtmlElementText.drawButton(HtmlElementIds.updateConfigHtml, "Konfigurator Bearbeiten")
    val jQueryButtonDeleteConfig : JQuery = HtmlElementText.drawButton(HtmlElementIds.deleteConfigHtml, "Konfigurator loeschen")
    val jQueryButtonStartPage : JQuery =  HtmlElementText.drawButton(HtmlElementIds.deleteConfigHtml, "Hauptseite")


    main.appendTo(jQuery(HtmlElementIds.section))

    jQueryButtonShowConfigGraph.appendTo(main)
    jQueryButtonUpdateConfig.appendTo(main)
    jQueryButtonDeleteConfig.appendTo(main)
    jQueryButtonStartPage.appendTo(main)

    Progress.addState(State(
      configDTO = Some(ConfigDTO(
        params = Some(ConfigParamsDTO(
          configId = Some(userConfigDTO.configId.get)
        ))
      ))
    ))

    new Mouse().mouseClick(jQueryButtonShowConfigGraph, Actions.CONFIG_GRAPH, Some(userConfigDTO))
  }

  def drawAllConfigs(getConfigsResult: ConfigDTO): Unit = {

    cleanPage

    val configurationsJQuery : List[(JQuery, UserConfigDTO)] = getConfigsResult.result.get.configs.get.map(config => {
      (HtmlElementText.drawButton(config.configId.get, config.configUrl.get), config)
    })

    val main : JQuery = HtmlElementText.mainPage(s"Konfiguratoren" )

    val jQueryButtonAddConfig : JQuery = HtmlElementText.drawButton(HtmlElementIds.addConfigHtml, "Konfigurator hinzufügen")
    val jQueryButtonLogout : JQuery = HtmlElementText.drawButton("start", "Auslogen")

    main.appendTo(jQuery(HtmlElementIds.section))

    val configDiv = jQuery("<div id='configs'></div>")

    configurationsJQuery.foreach(config => {
      config._1.appendTo(configDiv)
    })
    configDiv.appendTo(main)
    jQuery("</br> </br> </br>").appendTo(main)
    jQueryButtonAddConfig.appendTo(main)
    jQueryButtonLogout.appendTo(main)

    configurationsJQuery.foreach(configJQuery => new Mouse().mouseClick(configJQuery._1, ActionsForClient.CONFIG_PAGE, Some(configJQuery._2)))
    new Mouse().mouseClick(jQueryButtonAddConfig, ActionsForClient.ADD_CONFIG_PAGE, Some(getConfigsResult))
    new Mouse().mouseClick(jQueryButtonLogout, ActionsForClient.START_PAGE)
  }
// TODO Nuetzlicjkeit prüfen
//  private def drawConfig(config : UserConfigDTO): JQuery = {
//    cleanPage
//
//    val htmlMain =
//      "<dev id='main' class='main'>" +
//        "<p>Konfiguration</p>" +
//        drawInputField("configurationUrl", "configurationUrl", defaultText = config.configUrl.get)  + "</br> </br>" +
// //        drawInputField("configurationCourse", "configurationCourse", defaultText = config.configurationCourse.get) +
//        drawButton("updateConfig", "updateConfig") +
//        drawButton("deleteConfig", "deleteConfig") +
//        drawButton("addStep", "addStep") +
//        drawButton(HtmlElementIds.startPageHtml, "startPage") +
//      "</dev>"
//
//    drawNewMain(htmlMain)
//
////    jQuery(HtmlElementIds.startPageJQuery).on("click", () => startPage)
//  }

  def drawAllConfigsInUserPage(configDTO: Option[ConfigDTO]):Unit = {
    val main : JQuery = jQuery(HtmlElementIds.mainJQuery)
    val jQueryBr : JQuery = jQuery("</br> </br> </br>")
    val jQueryMainConfigs : JQuery = jQuery("<center> <h3>Konfiguratoren</h3> </center>")

    val configurationsJQuery : List[(JQuery, UserConfigDTO)] = configDTO.get.result.get.configs.get.map(config => {
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
    new Mouse().mouseClick(jQueryButtonAddConfig, ActionsForClient.ADD_CONFIG_PAGE, configDTO)
    new Mouse().mouseClick(jQueryButtonLogout, ActionsForClient.START_PAGE)
  }
}