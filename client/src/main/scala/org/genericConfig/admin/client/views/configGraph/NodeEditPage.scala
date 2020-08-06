package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.scalajs.jquery.jQuery

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 14.07.2020
 */
class NodeEditPage {
  def showNodeEditPage(graphIsEmpty : Boolean) : Unit = {
    if(graphIsEmpty) {

    }else {
      jQuery(HtmlElementIds.jQueryConfigGraphNodeEdit).remove()
      val configGraphNodePage = HtmlElementText.configGraphNodePage("Konfigurationsgraph")
      configGraphNodePage.appendTo(jQuery(HtmlElementIds.section))
    }

  }
}
