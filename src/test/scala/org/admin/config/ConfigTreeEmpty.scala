package org.admin.config

import org.admin.AdminWeb
import org.specs2.Specification
import play.api.libs.json.{JsValue, Json}
import org.persistence.db.orientdb.OrientDB
import org.specs2.json.Json

/**
  * Created by gennadi on 16.11.16.
  */
class ConfigTreeEmpty extends Specification with AdminWeb{

  def is = s2"""
    Diese Specification prueft die leere Konfiguration
    (erste Aufruf nach der Regestrierung und Anmeldung)
      autentification                                   $e1
      jsonId                                            $e2
      method                                            $e3
      result                                            $e4
    """
  
  
  val autentificationClientServer = Json.obj(
    "jsonId" -> 2,
    "method" -> "autheticate"
    ,"params" -> Json.obj(
      "username" -> "testEmptyConfigTree",
      "password" -> "testEmptyConfigTree"
    )
  )

  val autentificationServerServer = handelMessage(autentificationClientServer)
  def e1 = (autentificationServerServer \ "result" \ "authentication").asOpt[Boolean].get === true

  val configTreeClientServer = Json.obj(
    "jsonId" -> 3,
    "method" -> "configTree"
    ,"params" -> Json.obj(
      "adminId" -> (autentificationServerServer \ "result" \ "adminId").asOpt[String].get,
      "authentication" -> (autentificationServerServer \ "result" \ "authentication").asOpt[Boolean].get
    )
  )

  val configTreeServerClient = handelMessage(configTreeClientServer)
  println(configTreeServerClient)
  def e2 = (configTreeServerClient \ "jsonId").asOpt[Int].get === 3
  def e3 = (configTreeServerClient \ "method").asOpt[String].get === "configTree"
  def e4 = (configTreeServerClient \ "result" \ "steps").asOpt[List[JsValue]].get === List.empty
}
