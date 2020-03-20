package org.genericConfig.admin.client

import org.genericConfig.admin.client.start.StartPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
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
import org.genericConfig.admin.shared.user.json.JsonUserIn
import org.genericConfig.admin.shared.user.json.JsonUserParams
import play.api.libs._

import scala.util.matching.Regex

//import scala.collection.JavaConverters._


object Websocket {
  val url = "ws://localhost:9000/admin"
	val socket = new dom.WebSocket(url)
  val numPattern: Regex = "[0-9]+".r
  
  def main(args: Array[String]): Unit = {
    
    println("main")
    socket.onmessage = {
      (e: dom.MessageEvent) => {
        println("IN -> " + e.data.toString)
        val jsValue: JsValue = Json.parse(e.data.toString)
        new AdminClientWeb(socket).handleMessage(jsValue)
      }
    }
    
    socket.onopen = { (e: dom.Event) => {
      println("Websocket open")
      new StartPage().drawStartPage(socket)
//      val getUser = Json.toJson(
//        UserDTO(
//          action = Actions.GET_USER,
//          params = Some(UserParamsDTO(
//            username = "user2",
//            password = "user2",
//            update = None,
//
//          )),
//          result = None
//        )
//      ).toString
//      println("OUT -> " + getUser)
//      socket.send(getUser)
      }
    }
    
    socket.onerror = {
      (e: dom.Event) => {
        println("Websocket error")
      }
    }
  }
}