package org.genericConfig.admin

import org.scalajs.dom
import dom.document
import org.scalajs.jquery.jQuery
import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom.raw.WebSocket
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


object Main {
  val url = "ws://localhost:9000/admin"
	val socket = new dom.WebSocket(url)
  val numPattern = "[0-9]+".r
  
  def main(args: Array[String]): Unit = {
    
//    val startPage = new StartPage
    
//    startPage.setStartPage(socket)
    
    socket.onmessage = {
      (e: dom.MessageEvent) => {
//        println(e.data.toString())
        val jsValue: JsValue = Json.parse(e.data.toString())
        println("jsValue " + jsValue)
        val registrationOut: JsResult[JsonRegistrationOut] = Json.fromJson[JsonRegistrationOut](jsValue)
        registrationOut match {
          case s : JsSuccess[JsonRegistrationOut] => s.get
          case e : JsError => println("Errors -> REGISTRATION: " + JsError.toJson(e).toString())
        }
//        
        println("registrationOut " + registrationOut)
        
        val message: Dynamic = JSON.parse(e.data.toString())
        message.json.toString() match {
          case "CreateConfig" => println("CreateConfig")
          case _ => println("matching")
        }
      }
    }
    
    socket.onopen = { (e: dom.Event) => {
        println("Websocket open")  
//        val createConfig = """{"json":"CreateConfig", "params":{"adminId":"#21:3", "configUrl":"//http://contig1/user3"}}"""
        
        val regist = """{"json":"Registration","params":{"username":"user1","password":"user1"}}"""
        val registOb = JsonRegistrationIn(
            params = RegistrationParams(
                "",
                ""
            )
        )
        println(registOb)
        val html =  
        """<dev id='test' class='config'> 
          <p>Konfiguration Erstellen</p>
          <div id='configs' class='list'>Hier werden die Konfigurationen augelistet</div>
          <div id='createConfig' class='button'>Neu Konfiguration erstellen. Hier kommt eine Kreis mit Plus Zeichen in der Mitte</div>
        </dev> """
        
        jQuery(html).appendTo(jQuery("section"))
        
        
//        val login = """{"dtoId":2, "dto":"Login", "params":{"username":"test", "password":"test"}}"""
//        
//        println("sende " + login)
        socket.send(regist)
        
        
        
//        new Login().getUsername
//        new Registration().getRegistrationPage
//        socket.send(createConfig)
      }
    }
    
    socket.onerror = {
      (e: dom.Event) => {
        println("Websocket error")
      }
    }
  }
}