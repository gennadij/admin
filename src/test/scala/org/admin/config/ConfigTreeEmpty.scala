package org.admin.config

import org.admin.AdminWeb
import org.specs2.Specification
import play.api.libs.json.{JsValue, Json}
import org.persistence.db.orientdb.OrientDB
import org.specs2.json.Json

//TODO loginStatus true und false
class ConfigTreeEmpty extends Specification with AdminWeb{

  def is = s2"""
    Diese Specification prueft die leere Konfiguration
    (erste Aufruf nach der Regestrierung und Anmeldung)
      autentification                                   $e1
      jsonId                                            $e2
      method                                            $e3
      result                                            $e4
    """
  
  
  val loginClientServer = Json.obj(
    "jsonId" -> 2,
    "dto" -> "Login"
    ,"params" -> Json.obj(
      "username" -> "testEmptyConfigTree",
      "password" -> "testEmptyConfigTree"
    )
  )

  val loginServerClient = handelMessage(loginClientServer)
  def e1 = (loginServerClient \ "result" \ "authentication").asOpt[Boolean].get === true

  val configTreeClientServer = Json.obj(
    "jsonId" -> 6,
    "method" -> "configTree"
    ,"params" -> Json.obj(
      "adminId" -> (loginServerClient \ "result" \ "adminId").asOpt[String].get,
      "authentication" -> (loginServerClient \ "result" \ "authentication").asOpt[Boolean].get
    )
  )

  val configTreeServerClient = handelMessage(configTreeClientServer)
  println(configTreeServerClient)
  def e2 = (configTreeServerClient \ "jsonId").asOpt[Int].get === 3
  def e3 = (configTreeServerClient \ "method").asOpt[String].get === "configTree"
  def e4 = (configTreeServerClient \ "result" \ "steps").asOpt[List[JsValue]].get === List.empty
}
