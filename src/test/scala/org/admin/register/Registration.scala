package org.admin.register

import org.specs2.Specification
import play.api.libs.json._
import org.admin.AdminWeb

class Registration extends Specification with AdminWeb{
  def is = 
  
  s2"""
       Specification fuer die Pruefung der Registrierung schon exestierenden Admin
          jsonId                                                               $e1
          method                                                               $e2
          adminId                                                              $e3
          username                                                             $e4
          authentication                                                       
       Specification fuer die Registrierung nicht exestierenden / neuen Admins
       Regestrierung
          jsonId                                                               $e6
          dto                                                               $e7
          username                                                             $e8
          authentication                                                       
       Authentifizierung diesen neue angelegten adminUser
          jsonId                                                               $e10
          dto                                                               $e11
          adminId                                                              $e12
          username                                                             $e13
          authentication                                                       
          
    """
//TODO wenn der User nicht existiert wird OrientDB immer einen neuen angelegen -> das verhindern
  val jsonClientServer1 = Json.obj(
      "jsonId" -> 1,
      "dto" -> "Registration"
      ,"params" -> Json.obj(
          "username" -> "test8",
          "password"-> "test8"))


  val jsonServerClient1: JsValue = handelMessage(jsonClientServer1)
  println(jsonServerClient1)
  def e1 = (jsonServerClient1 \ "jsonId").asOpt[Int].get must_== 1
  def e2 = (jsonServerClient1 \ "dto").asOpt[String].get must_== "Registration"
  def e3 = (jsonServerClient1 \ "result" \ "adminId").asOpt[String].get must_== ""
  def e4 = (jsonServerClient1 \ "result" \ "username").asOpt[String].get must_== ""
//  def e5 = (jsonServerClient1 \ "result" \ "authentication").asOpt[Boolean].get must_== false

//  ================================================================
  /*
  Regestriere neuen User
   */
  val random = scala.util.Random

  val randomUserPasword = random.nextInt(1000)
  val usernamePassword = "test" + randomUserPasword

  val jsonClientServer2 = Json.obj(
    "jsonId" -> 1,
    "dto" -> "Registration"
    ,"params" -> Json.obj(
      "username" -> usernamePassword,
      "password"-> usernamePassword))


  val jsonServerClient2 = handelMessage(jsonClientServer2)
  println(jsonServerClient2)
  def e6 = (jsonServerClient2 \ "jsonId").asOpt[Int].get must_== 1
  def e7 = (jsonServerClient2 \ "dto").asOpt[String].get must_== "Registration"
  def e8 = (jsonServerClient2 \ "result" \ "username").asOpt[String] must_== Some(usernamePassword)
//  def e9 = (jsonServerClient2 \ "result" \ "authentication").asOpt[Boolean].get must_== true

  /*
  Authentifizierung des neu angelegten Adminuser über Loginseite
   */
//  {"jsonId": 2, "method": "autheticate", params: {"username": "test", "password": "test"}}
  val jsonClientServer3 = Json.obj(
    "jsonId" -> 2,
    "dto" -> "Login"
    ,"params" -> Json.obj(
      "username" -> usernamePassword,
      "password" -> usernamePassword
    )
  )
//  {"jsonId": 2, "method": "autheticate", result: {"id": "AU#40:0", "username": "test", "authentication": true}}
  val jsonServerClient3 = handelMessage(jsonClientServer3)
  println(jsonServerClient3)
  def e10 = (jsonServerClient3 \ "jsonId").asOpt[Int] must_== Some(2)
  def e11 = (jsonServerClient3 \ "dto").asOpt[String] must_== Some("Login")
  def e12 = (jsonServerClient3 \ "result" \ "adminId").asOpt[String].get must_==
    (jsonServerClient2 \ "result" \ "adminId").asOpt[String].get
  def e13 = (jsonServerClient3 \ "result" \ "username").asOpt[String].get must_==
    (jsonServerClient2 \ "result" \ "username").asOpt[String].get
//  def e14 = (jsonServerClient3 \ "result" \ "authentication").asOpt[Boolean].get must_==
//    (jsonServerClient2 \ "result" \ "authentication").asOpt[Boolean].get
}