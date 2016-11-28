package org.admin.login

import org.specs2.Specification
import org.admin.AdminWeb
import play.api.libs.json.Json
import play.api.libs.json.JsValue

class LoginWithExistingUsernameAndPassword extends Specification with AdminWeb{
  def is = 
  
  s2"""
       Specification fuer die Pruefung der Registrierung eines neuen Admins
          jsonId=1                                                       $e1
          dto=Registration                                               $e2
          username=userexist                                                     $e4
          status=true                                                    $e5
          message="Registrierung war erfolgreich"                            $e6
    """

  val jsonClientServer = Json.obj(
      "jsonId" -> 2,
      "dto" -> "Login"
      ,"params" -> Json.obj(
          "username" -> "userexist",
          "password"-> "userexist"))


  val jsonServerClient: JsValue = handelMessage(jsonClientServer)
  println(jsonServerClient)
  def e1 = (jsonServerClient \ "jsonId").asOpt[Int].get must_== 2
  def e2 = (jsonServerClient \ "dto").asOpt[String].get must_== "Login"
//  def e3 = (jsonServerClient \ "result" \ "adminId").asOpt[String].get must_== ""
  def e4 = (jsonServerClient \ "result" \ "username").asOpt[String].get must_== "userexist"
  def e5 = (jsonServerClient \ "result" \ "status").asOpt[Boolean].get must_== true
  def e6 = (jsonServerClient \ "result" \ "message").asOpt[String].get must_== "Anmeldung mit User war erfolgreich"
}