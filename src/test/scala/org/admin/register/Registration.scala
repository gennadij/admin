package org.admin.register

import org.specs2.Specification
import play.api.libs.json._
import org.admin.AdminWeb

class Registration extends Specification with AdminWeb{
  def is = 
  
  s2"""
      Specification fuer die Pruefung der Registrierung das Admins
        jsonId                                                               $e1
        method                                                               $e2
        adminId                                                              $e3
        username                                                             $e4
        authentication                                                       $e5
    """
val jsonClientServer = Json.obj(
    "jsonId" -> 1, 
    "method" -> "register"
    ,"params" -> Json.obj(
        "username" -> "test1", 
        "password"-> "test1"))

        
val jsonServerClient = handelMessage(jsonClientServer)
//{"jsonId": 1, "method": "register", "result": 
//    {"adminId": "AU#40:0", "username": "test", "authentication": true}}
        
def e1 = (jsonServerClient \ "jsonId").toString must_== 1
def e2 = (jsonServerClient \ "method").toString must_== "register"
def e3 = (jsonServerClient \ "result" \ "adminId").toString must_== "AU#40:0"
def e4 = (jsonServerClient \ "result" \ "username").toString must_== "test"
def e5 = (jsonServerClient \ "result" \ "authentication").toString().toBoolean must_== true

}