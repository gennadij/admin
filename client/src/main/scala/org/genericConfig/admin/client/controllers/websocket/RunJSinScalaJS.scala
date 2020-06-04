package org.genericConfig.admin.client.controllers.websocket

import scala.scalajs.js
import js.Dynamic.{ global => g }

object RunJSinScalaJS {
  def runJSFunction(): Unit = {
    g.alert("test")
  }
}
