package org.genericConfig.admin.controllers.websocket

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann on 24.10.2017
 */
import play.api.libs.json.JsValue
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.admin.Admin

object WebClient {
  def init: WebClient = {
    new WebClient
  }
}


class WebClient extends AdminWeb{
  val admin = new Admin()
  
  def handleMessage(receivedMessage: JsValue): JsValue = {
    handleMessage(receivedMessage, admin)
  }
}