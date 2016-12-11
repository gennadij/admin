package org.admin.login

import org.specs2.Specification
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import org.admin.AdminWeb

class LoginWithNotExistingUsername extends Specification with AdminWeb {
  def is = 
  
  s2"""
       Specification fuer die Pruefung der Registrierung eines neuen Admins
          jsonId=2                                                       $e1
          dto=Login                                                      $e2
          username=userexist                                             $e4
          status=true                                                    $e5
          message="Anmeldung war nicht erfolgreich"                      $e6
    """
  val user = "usernotexist"
  
  val jsonClientServer = Json.obj(
      "jsonId" -> 2,
      "dto" -> "Login"
      ,"params" -> Json.obj(
          "username" -> user,
          "password"-> user))


  val jsonServerClient: JsValue = handelMessage(jsonClientServer)
  def e1 = (jsonServerClient \ "jsonId").asOpt[Int].get must_== 2
  def e2 = (jsonServerClient \ "dto").asOpt[String].get must_== "Login"
  def e4 = (jsonServerClient \ "result" \ "username").asOpt[String].get must_== user
  def e5 = (jsonServerClient \ "result" \ "status").asOpt[Boolean].get must_== false
  def e6 = (jsonServerClient \ "result" \ "message").asOpt[String].get must_== 
    s"Anmeldung mit Username $user war nicht erfolgreich"
}