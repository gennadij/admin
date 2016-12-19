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
      loginStatus=true                                   $e1
      jsonId = 6                                            $e2
      dto=ConfigTree                                            $e3
      result = None                                            $e4
    """
  
  //TODO message testen
  val loginClientServer = Json.obj(
    "jsonId" -> 2,
    "dto" -> "Login"
    ,"params" -> Json.obj(
      "username" -> "testEmptyConfigTree",
      "password" -> "testEmptyConfigTree"
    )
  )
  
  val loginServerClient = handelMessage(loginClientServer)
  
  def e1 = (loginServerClient \ "result" \ "status").asOpt[Boolean].get === true
  
  val configTreeClientServer = Json.obj(
    "jsonId" -> 6,
    "dto" -> "ConfigTree"
    ,"params" -> Json.obj(
      "adminId" -> (loginServerClient \ "result" \ "adminId").asOpt[String].get,
      "loginStatus" -> (loginServerClient \ "result" \ "status").asOpt[Boolean].get
    )
  )

  val configTreeServerClient = handelMessage(configTreeClientServer)
  def e2 = (configTreeServerClient \ "jsonId").asOpt[Int].get === 6
  def e3 = (configTreeServerClient \ "dto").asOpt[String].get === "ConfigTree"
  def e4 = (configTreeServerClient \ "result" \ "steps").asOpt[List[JsValue]].get === List.empty
}
