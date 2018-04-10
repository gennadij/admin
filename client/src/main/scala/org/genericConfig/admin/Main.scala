package org.genericConfig.admin

import org.scalajs.dom
import dom.document
import org.scalajs.jquery.jQuery
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.raw.WebSocket
import scala.scalajs.js.JSON
import scala.scalajs.js.Dynamic
import scala.scalajs._

//import scala.collection.JavaConverters._


object Main {
  val url = "ws://localhost:9000/admin"
		  val socket = new dom.WebSocket(url)
  val numPattern = "[0-9]+".r
  
  def main(args: Array[String]): Unit = {
    
//    val startPage = new StartPage
    
//    startPage.setStartPage(socket)
    
    socket.onmessage = {
      (e: dom.MessageEvent) => {
        println(e.data.toString())
        val message: Dynamic = JSON.parse(e.data.toString())
        message.json.toString() match {
          
          case _ => println("matching")
        }
      }
    }
    
    socket.onopen = { (e: dom.Event) => {
        println("Websocket open")
        val login = """{"dtoId":2, "dto":"Login", "params":{"username":"test", "password":"test"}}"""
        
        println("sende " + login)
        socket.send(login)
      }
    }
    
    socket.onerror = {
      (e: dom.Event) => {
        println("Websocket error")
      }
    }
  }
}