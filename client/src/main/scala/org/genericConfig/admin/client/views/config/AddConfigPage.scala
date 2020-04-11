package org.genericConfig.admin.client.views.config

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.ConfigDTO
import org.scalajs.jquery.{JQuery, jQuery}
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.04.2018
 */
class AddConfigPage() extends CommonFunction {

  def drawAddConfigPage(configDTO: ConfigDTO): Unit = {

    cleanPage

    val main : JQuery = HtmlElementText.mainPage("Neue Konfiguration hinzufügen")
    val jQueryInputFieldConfigUrl : JQuery = HtmlElementText.drawInputField(HtmlElementIds.inputConfigUrlHtml, "URI für Konfigurator")
    val jQueryInputFieldConfigurationCourse : JQuery = HtmlElementText.drawInputField(HtmlElementIds.inputConfigurationCourseHtml, "Darstellungsverlauf des Konfigurators")
    val jQueryButtonAddConfig : JQuery = HtmlElementText.drawButton("register", "Konfigurator hinzufügen")
    val jQueryButtonCancel : JQuery = HtmlElementText.drawButton("cancel", "Abbrechen")

    main.appendTo(jQuery(HtmlElementIds.section))

    jQueryInputFieldConfigUrl.appendTo(main)
    jQueryInputFieldConfigurationCourse.appendTo(main)
    jQueryButtonAddConfig.appendTo(main)
    jQueryButtonCancel.appendTo(main)

    new Mouse().mouseClick(jQueryButtonAddConfig, Actions.ADD_CONFIG, Some(configDTO))
    new Mouse().mouseClick(jQueryButtonCancel, Actions.GET_CONFIGS, Some(configDTO))
  }
}

