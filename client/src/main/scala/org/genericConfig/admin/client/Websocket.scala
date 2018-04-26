package org.genericConfig.admin.client

import org.scalajs.dom
import org.scalajs.jquery.jQuery
import scala.scalajs.js.JSON
import scala.scalajs.js.Dynamic
import scala.scalajs._
import org.genericConfig.admin.shared.json.registration.JsonRegistrationIn
import org.genericConfig.admin.shared.json.registration.RegistrationParams
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.JsResult
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsError
import org.genericConfig.admin.shared.json.registration.JsonRegistrationOut

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
        println("jsValue " + jsValue)
        new AdminClienWeb(socket).handleMessage(jsValue)
      }
    }
    
    socket.onopen = { (e: dom.Event) => {
        println("Websocket open")
        val getConfigs = """{"json":"GetConfigs", "params":{"userId":"#24:52"}}"""
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