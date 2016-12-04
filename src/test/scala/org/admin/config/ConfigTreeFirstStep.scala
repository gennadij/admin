package org.admin.config

import org.admin.AdminWeb
import org.specs2.Specification
import play.api.libs.json.{JsValue, Json}

/**
  * Created by gennadi on 16.11.16.
  */
class ConfigTreeFirstStep extends Specification with AdminWeb{

  def is = s2"""
    Diese Specification prueft die Erzeugung eines neuen Steps
      Login -> loginStatus=true                                              $e1
      FirstStep -> jsonId = 4                                                $e2
      FirstStep -> dto = ConfigTree                                          $e3
      FirstStep -> adminId                                                   $e4
      FirstStep -> kind = Immutable                                          $e5
      ConfigTree -> jsonId = 6                                               $e6
      ConfigTree -> dto = ConfigTree                                         $e7
      ConfigTree -> configTree = FirstStep                                   $e8
      ConfigTree -> step -> adminId                                          $e9
      ConfigTree -> step -> kind=first                                      $e10
      ConfigTree -> step -> components.size=0                               $e11
      
    """
// TODO Löschen der vorherigen Step bevor der Schritt hinzugefügt wird
  
  val loginClientServer = Json.obj(
    "jsonId" -> 2,
    "dto" -> "Login"
    ,"params" -> Json.obj(
      "username" -> "firstStepConfigTree",
      "password" -> "firstStepConfigTree"
    )
  )

  val loginServerClient = handelMessage(loginClientServer)
  def e1 = (loginServerClient \ "result" \ "status").asOpt[Boolean].get === true
  
//  ============================================================================

  val firstStepConfigTreeClientServer = Json.obj(
    "jsonId" -> 4,
    "dto" -> "FirstStep" 
    ,"params" -> Json.obj(
      "adminId" -> (loginServerClient \ "result" \ "adminId").asOpt[String].get,
      "kind" -> "first",
      "loginStatus" -> (loginServerClient \ "result" \ "status").asOpt[Boolean].get
    )
  )

  val firstStepConfigTreeServerClient = handelMessage(firstStepConfigTreeClientServer)

  def e2 = (firstStepConfigTreeServerClient \ "jsonId").asOpt[Int].get === 4
  def e3 = (firstStepConfigTreeServerClient \ "dto").asOpt[String].get === "FirstStep"
  def e4 = (firstStepConfigTreeServerClient \ "result" \ "adminId").asOpt[String].get ===
    (loginServerClient \ "result" \ "adminId").asOpt[String].get
  def e5 = (firstStepConfigTreeServerClient \ "result" \ "kind").asOpt[String].get === "first"

//  ============================================================================
  
  val configTreeClientServer = Json.obj(
    "jsonId" -> 6,
    "dto" -> "ConfigTree"
    ,"params" -> Json.obj(
      "adminId" -> (loginServerClient \ "result" \ "adminId").asOpt[String].get,
      "loginStatus" -> (loginServerClient \ "result" \ "status").asOpt[Boolean].get
    )
  )
  
  val configTreeServerClient = handelMessage(configTreeClientServer)
  
  def e6 = (configTreeServerClient \ "jsonId").asOpt[Int].get === 6
  def e7 = (configTreeServerClient \ "dto").asOpt[String].get === "ConfigTree"
//  List({"id":"#26:3","stepId":"#26:3","adminId":"AU#21:3","kind":"first","components":[]})
  def e8 = (configTreeServerClient \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
  def e9 = (((configTreeServerClient \ "result" \ "steps")(0)) \ "adminId").asOpt[String].get === 
    (loginServerClient \ "result" \ "adminId").asOpt[String].get
  def e10 = (((configTreeServerClient \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
  def e11 = (((configTreeServerClient \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 0
}
