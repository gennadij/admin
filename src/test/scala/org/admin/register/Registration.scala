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
          authentication                                                       $e5
       Specification fuer die Pruefung der Registrierung nicht exestierenden Admin
          jsonId                                                               $e6
          method                                                               $e7
          adminId                                                              $e8
          username                                                             $e9
          authentication                                                       $e10
    """

  val jsonClientServer1 = Json.obj(
      "jsonId" -> 1,
      "method" -> "register"
      ,"params" -> Json.obj(
          "username" -> "test8",
          "password"-> "test8"))


  val jsonServerClient1 = handelMessage(jsonClientServer1)
  //{"jsonId": 1, "method": "register", "result":
  //    {"adminId": "AU#40:0", "username": "test", "authentication": true}}

  def e1 = (jsonServerClient1 \ "jsonId").asOpt[Int].get must_== 1
  def e2 = (jsonServerClient1 \ "method").asOpt[String].get must_== "register"
  def e3 = (jsonServerClient1 \ "result" \ "adminId").asOpt[String] must_== None
  def e4 = (jsonServerClient1 \ "result" \ "username").asOpt[String] must_== None
  def e5 = (jsonServerClient1 \ "result" \ "authentication").asOpt[Boolean].get must_== false

//  ================================================================

  val random = scala.util.Random

  val randomUserPasword = random.nextInt(1000)
  val userPassword = "test" + randomUserPasword

  val jsonClientServer2 = Json.obj(
    "jsonId" -> 1,
    "method" -> "register"
    ,"params" -> Json.obj(
      "username" -> userPassword,
      "password"-> userPassword))


  val jsonServerClient2 = handelMessage(jsonClientServer2)
  //{"jsonId": 1, "method": "register", "result":
  //    {"adminId": "AU#40:0", "username": "test", "authentication": true}}

  def e6 = (jsonServerClient2 \ "jsonId").asOpt[Int].get must_== 1
  def e7 = (jsonServerClient2 \ "method").asOpt[String].get must_== "register"
  def e8 = (jsonServerClient2 \ "result" \ "username").asOpt[String] must_== Some(userPassword)
  def e9 = (jsonServerClient2 \ "result" \ "authentication").asOpt[Boolean].get must_== true

//  {"jsonId": 2, "method": "autheticate", params: {"username": "test", "password": "test"}}
  val jsonClientServer3 = Json.obj(
    "jsonId" -> 2,
    "method" -> "autheticate"
    ,"params" -> Json.obj(
      "username" -> "",
      "password" -> ""
    )
  )
//  {"jsonId": 2, "method": "autheticate", result: {"id": "AU#40:0", "username": "test", "authentication": true}}
  val jsonServerClient3 = handelMessage(jsonClientServer3)

  def e10 = (jsonServerClient3 \ "jsonId").asOpt[Int] must_== Some(2)
  def e11 = (jsonServerClient3 \ "method").asOpt[String] must_== Some("autheticate")
  def e12 = (jsonServerClient3 \ "result" \ "adminId").asOpt[String] must_==
    Some((jsonServerClient2 \ "result" \ "adminId").asOpt[String].get.toString)
  def e13 = (jsonServerClient3 \ "result" \ "username").asOpt[String] must_==
    Some((jsonServerClient2 \ "result" \ "username").asOpt[String].get.toString)
  def e14 = (jsonServerClient3 \ "result" \ "authentication").asOpt[String] must_==
    Some((jsonServerClient2 \ "result" \ "authentication").asOpt[String].get.toString)
}