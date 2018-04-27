package org.genericConfig.admin.client

import org.scalajs.dom
import org.scalajs.jquery.jQuery
import scala.scalajs.js.JSON
import scala.scalajs.js.Dynamic
import scala.scalajs._
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.JsResult
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsError

//import scala.collection.JavaConverters._


object Websocket {
  val url = "ws://localhost:9000/admin"
	val socket = new dom.WebSocket(url)
  val numPattern = "[0-9]+".r
  
  def main(args: Array[String]): Unit = {
    socket.onmessage = {
      (e: dom.MessageEvent) => {
        println("-> " + e.data.toString())
        val jsValue: JsValue = Json.parse(e.data.toString())
        new AdminClienWeb(socket).handleMessage(jsValue)
      }
    }
    
    socket.onopen = { (e: dom.Event) => {
        println("Websocket open")
        //user mit 3 Configs (leer)
//        val getConfigs = """{"json":"GetConfigs", "params":{"userId":"#24:52"}}"""
        // user mit 1 Config 1 Step 3 Components
        val getConfigs = """{"json":"GetConfigs", "params":{"userId":"#23:3"}}"""
        socket.send(getConfigs)
      }
    }
    
    socket.onerror = {
      (e: dom.Event) => {
        println("Websocket error")
      }
    }
  }
}