package org.genericConfig.admin.controllers.websocket

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann on 24.10.2017
 */
import org.genericConfig.admin.controllers.admin.MessageHandler
import play.api.libs.json.JsValue

object WebClient {
  def init: WebClient = {
    new WebClient()
  }
}


class WebClient extends MessageHandler{
  
  def handleMessage(receivedMessage: JsValue): JsValue = {
    hMessage(receivedMessage)
  }
}