package org.genericConfig.admin.client.views.html

import org.genericConfig.admin.shared.common.ErrorDTO
import org.scalajs.jquery.{JQuery, jQuery}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann ${date}
 */
object HtmlElementText {
  def mainPage(
                title : String
              ) : JQuery = {
    jQuery(s"<div id='main' class='main'><center> <h3> $title</h3> </center> </div>")
  }

  def configGraphNodePage(title : String) : JQuery = {
    jQuery(s"<div id='${HtmlElementIds.htmlConfigGraphNodeEdit}'><center> <h3> $title</h3> </center> </div>")
  }

  def devInputFields(inputFields : List[String]) : String = {
    inputFields.mkString(" </br> ")
  }

  def devButtons() : String = {
    ""
  }

  def drawInputField(id: String, label: String, typeofInput: String = "text", defaultText : String = ""): JQuery = {
    jQuery(s"<div class='inputField'>$label <input id='$id' type='$typeofInput' value='$defaultText'></div>")
  }

  def drawButton(id: String, text: String): JQuery = {
    jQuery(s"<div id='$id' class='button'>$text</div>")
  }

  def drawDiv(id : String, title : String = "", hValue : String = "2"): JQuery = {
    jQuery(s"<div id='$id'><h$hValue align='center'>$title</h$hValue></div>")
  }

  def drawAlert(e : ErrorDTO) = {
    import org.scalajs.dom
    dom.window.alert(s"message : ${e.message} | name : ${e.name} | code : ${e.code}")
  }
}
