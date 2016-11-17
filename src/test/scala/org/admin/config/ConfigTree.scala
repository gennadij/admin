package org.admin.config

import org.specs2.Specification
import play.api.libs.json.Json
import org.persistence.db.orientdb.OrientDB

/**
  * Created by gennadi on 16.11.16.
  */
class ConfigTree extends Specification{

  def is = s2"""
    Diese Specification erstellt die Konfiguration
      autentification                   
      1. step
      2. step
      3. step
      4. step
      5. step
      6. step
      7. step
      8. step
    """

//  val db = "testDB"
//  val username = "root"
//  val password = "root"
//  OrientDB.getGraph(db, username, password)
  
  
  val autentificationClientServer = Json.obj(
    "jsonId" -> 2,
    "method" -> "autheticate"
    ,"params" -> Json.obj(
      "username" -> "test",
      "password" -> "test"
    )
  )
//  {"jsonId": 2, "method": "autheticate", result: {"id": "AU#40:0", "username": "test", "authentication": true}}
//  val jsonServerClient3 = handelMessage(jsonClientServer3)
  def e1 = (autentificationClientServer \ "jsonId").asOpt[Int] must_== Some(2)
//  def e11 = (jsonServerClient3 \ "method").asOpt[String] must_== Some("autheticate")
//  def e12 = (jsonServerClient3 \ "result" \ "adminId").asOpt[String].get must_==
//    (jsonServerClient2 \ "result" \ "adminId").asOpt[String].get
//  def e13 = (jsonServerClient3 \ "result" \ "username").asOpt[String].get must_==
//    (jsonServerClient2 \ "result" \ "username").asOpt[String].get
//  def e14 = (jsonServerClient3 \ "result" \ "authentication").asOpt[Boolean].get must_==
//    (jsonServerClient2 \ "result" \ "authentication").asOpt[Boolean].get
//}
  
  
}
