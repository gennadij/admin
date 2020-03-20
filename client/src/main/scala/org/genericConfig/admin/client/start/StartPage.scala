package org.genericConfig.admin.client.start

import org.scalajs.dom.WebSocket
import org.scalajs.jquery.JQuery
import util.{CommonFunction, HtmlElementIds}

class StartPage extends CommonFunction{
  def drawStartPage(webSocket: WebSocket): JQuery = {
    cleanPage
    val html = "<dev id='main' class='main'>" +
      "<p>Start Page</p> </br>" + "</dev>"
    drawNewMain(html)
  }
}
