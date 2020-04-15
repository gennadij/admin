package org.genericConfig.admin.client.controllers.websocket

import org.genericConfig.admin.client.views.StartPage
import org.scalajs.dom
import org.scalajs.dom.WebSocket
import play.api.libs.json.{JsValue, Json}

import scala.util.matching.Regex

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 
 */
object WebSocketListner {
  val url = "ws://localhost:9000/admin"
	val webSocket : WebSocket = new dom.WebSocket(url)
  val numPattern: Regex = "[0-9]+".r

  def main(args: Array[String]): Unit = {

    webSocket.onmessage = { e: dom.MessageEvent => {
        println("IN -> " + e.data.toString)
        val jsValue: JsValue = Json.parse(e.data.toString)
        new MessageHandler().handleMessage(jsValue)
      }
    }

    webSocket.onopen = { e: dom.Event => {
      println("WebSocket open")
      new StartPage().drawStartPage()
//        Start.start(webSocket)
      }
    }

    webSocket.onerror = { e: dom.Event => {
        println("Websocket error")
      }
    }
  }
}
