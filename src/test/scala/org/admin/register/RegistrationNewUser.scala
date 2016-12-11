//package org.admin.register
//
//import org.specs2.Specification
//import org.admin.AdminWeb
//import play.api.libs.json.Json
//import play.api.libs.json.JsValue
//
//class RegistrationNewUser extends Specification with AdminWeb{
//  def is = 
//  
//  s2"""
//       Specification fuer die Pruefung der Registrierung eines neuen Admins
//          jsonId=1                                                       $e1
//          dto=Registration                                               $e2
//          username=?                                                     $e4
//          status=true                                                    $e5
//          message="Registrierung war erfolgreich"                            $e6
//    """
//val random = scala.util.Random
//
//  val randomUserPasword = random.nextInt(1000000)
//  val usernamePassword = "test" + randomUserPasword
//
//  val jsonClientServer = Json.obj(
//      "jsonId" -> 1,
//      "dto" -> "Registration"
//      ,"params" -> Json.obj(
//          "username" -> usernamePassword,
//          "password"-> usernamePassword))
//
//
//  val jsonServerClient: JsValue = handelMessage(jsonClientServer)
//  def e1 = (jsonServerClient \ "jsonId").asOpt[Int].get must_== 1
//  def e2 = (jsonServerClient \ "dto").asOpt[String].get must_== "Registration"
////  def e3 = (jsonServerClient \ "result" \ "adminId").asOpt[String].get must_== ""
//  def e4 = (jsonServerClient \ "result" \ "username").asOpt[String].get must_== usernamePassword
//  def e5 = (jsonServerClient \ "result" \ "status").asOpt[Boolean].get must_== true
//  def e6 = (jsonServerClient \ "result" \ "message").asOpt[String].get must_== "Registrierung war erfolgreich"
//}