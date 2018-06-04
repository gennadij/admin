package org.genericConfig.admin.client.user

import org.genericConfig.admin.client.config.CreateConfig
import org.genericConfig.admin.shared.config.json.{JsonGetConfigsIn, JsonGetConfigsParams}
import org.genericConfig.admin.shared.user.json.JsonUserOut
import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json
import util.{CommonFunction, HtmlElementIds}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 04.06.2018
  */
class GetUser(websocket: WebSocket) extends CommonFunction{

  def drawUser(getUserOut: JsonUserOut): Unit = {

    cleanPage

    drawNewStatus(getUserOut.result.status.getUser.get.status)

    val htmlMain =
      "<dev id='main' class='main'>" +
        "<p>GetUser Page</p>" +
          drawButton(HtmlElementIds.getConfigsHtml, "GetConfigs") +
        "</dev>"

    drawNewMain(htmlMain)

    jQuery(HtmlElementIds.getConfigsJQuery).on("click", () => getConfigs(getUserOut.result.userId.get))
  }

  private def getConfigs(userId: String) = {
    println("Get Configs")
    val getConfigsIn = Json.toJson(JsonGetConfigsIn(
      params = JsonGetConfigsParams(
        userId
      )
    )).toString
    websocket.send(getConfigsIn)
  }

}
