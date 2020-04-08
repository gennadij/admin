package org.genericConfig.admin.client.controllers.action

import org.genericConfig.admin.client.views.StartPage
import org.scalajs.dom.raw.WebSocket

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 08.04.2020
 */

object Start {
  def start(webSocket: WebSocket): Unit = {
    new Start(webSocket).start()
  }
}

class Start(webSocket: WebSocket) {
  def start() : Unit = {
    new StartPage(webSocket).drawStartPage()
  }
}
