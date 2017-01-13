package org.admin.login

import org.specs2.Specification
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import org.admin.AdminWeb

class SpecsLogin extends Specification with AdminWeb {
  def is = 
  
  s2"""
       Specification fuer die Pruefung der Registrierung eines neuen Admins
          dtoId                                                         $e1
          dto                                                            $e2
          username                                                       $e4
          config.size                                                    $e7
          config -> configId                                             $e8
          config -> configUrl                                            $e9
          status=true                                                    $e5
          message="Anmeldung war nicht erfolgreich"                      $e6
    """
  val user = "user2"
  
  val jsonClientServer = Json.obj(
      "dtoId" -> 2,
      "dto" -> "Login"
      ,"params" -> Json.obj(
          "username" -> user,
          "password"-> user))


  val jsonServerClient: JsValue = handelMessage(jsonClientServer)
  println(jsonServerClient)
  def e1 = (jsonServerClient \ "dtoId").asOpt[Int].get must_== 2
  def e2 = (jsonServerClient \ "dto").asOpt[String].get must_== "Login"
  def e4 = (jsonServerClient \ "result" \ "username").asOpt[String].get must_== user
  def e7 = (jsonServerClient \ "result" \ "configs").asOpt[List[JsValue]].get.size === 1
  def e8 = ((jsonServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get === "#41:4"
  def e9 = ((jsonServerClient \ "result" \ "configs")(0) \ "configUrl").asOpt[String].get === "http//:config1/user2"
  def e5 = (jsonServerClient \ "result" \ "status").asOpt[Boolean].get must_== true
  def e6 = (jsonServerClient \ "result" \ "message").asOpt[String].get must_== 
    s"Anmeldung mit Username $user war erfolgreich"
}