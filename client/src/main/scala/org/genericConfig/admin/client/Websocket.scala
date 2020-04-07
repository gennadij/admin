package org.genericConfig.admin.client

import org.genericConfig.admin.client.start.StartPage
import org.scalajs.dom
import play.api.libs.json.{JsValue, Json}

import scala.util.matching.Regex

//import scala.collection.JavaConverters._


object Websocket {
  val url = "ws://localhost:9000/admin"
	val socket = new dom.WebSocket(url)
  val numPattern: Regex = "[0-9]+".r
  
  def main(args: Array[String]): Unit = {
    
    println("main")
    socket.onmessage = { e: dom.MessageEvent => {
        println("IN -> " + e.data.toString)
        val jsValue: JsValue = Json.parse(e.data.toString)
        new AdminClientWeb(socket).handleMessage(jsValue)
      }
    }
    
    socket.onopen = { e: dom.Event => {
      println("WebSocket open")
      new StartPage(webSocket = socket).drawStartPage()
      }
    }
    
    socket.onerror = { e: dom.Event => {
        println("Websocket error")
      }
    }
  }
}