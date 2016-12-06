package org.admin.config

import org.admin.AdminWeb
import org.specs2.Specification
import play.api.libs.json.{JsValue, Json}
import org.persistence.db.orientdb.StepVertex

class ConfigTreeFirstStepWithComponent extends Specification with AdminWeb{

  def is = s2"""
    Diese Specification prueft die Erzeugung eines neuen Steps
      Login -> loginStatus=true                                              $e1
      FirstStep -> Delete                                                   $e12
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
      Component -> component.jsonId=5                                       $e13
      Component -> component.dto=Component                                  $e14
      Component -> component.result.adminId                                 $e15
      Component -> component.result.kind                                    $e16
      Component -> component.result.stepId                                  $e17
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
  
  //delete Step von vorheriger Ausfuerung
  def e12 = StepVertex.removerSteps((loginServerClient \ "result" \ "adminId").asOpt[String].get) === 1

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
  
  //============================================================================
  
//  {"jsonId": 5, "method": "addComponent", "params": {"adminId": "AU#40:0", "kind": "immutable", "stepId": "#12:1"}
  
  val componentClientServer = Json.obj(
      "jsonId" -> 5,
      "dto" -> "Component"
      ,"params" -> Json.obj(
          "adminId" -> (loginServerClient \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "immutable"
          ,"stepId" -> (firstStepConfigTreeServerClient \ "result" \ "id").asOpt[String].get
      )
  )
  
  
//{"jsonId": 5, "method": "addComponent", 
//   *      "result": {"id": "#13:1", "componentId": "C#13:1", "adminId": "AU#40:0", "kind": "immutable", "stepId": "#12:1"}}
  
  val componentServerClient = handelMessage(componentClientServer)
  
  def e13 = (componentServerClient \ "jsonId").asOpt[Int].get === 5
  def e14 = (configTreeServerClient \ "dto").asOpt[String].get === "Component"
  def e15 = (configTreeServerClient \ "result" \ "adminId").asOpt[String].get === 
    (loginServerClient \ "result" \ "adminId").asOpt[String].get
  def e16 = (configTreeServerClient \ "result" \ "kind").asOpt[String].get === "immutable" 
  def e17 = (configTreeServerClient \ "result" \ "stepId").asOpt[String].get === 
    (firstStepConfigTreeServerClient \ "result" \ "id").asOpt[String].get
  
//  ============================================================================
    
  val configTreeClientServer1 = Json.obj(
    "jsonId" -> 6,
    "dto" -> "ConfigTree"
    ,"params" -> Json.obj(
      "adminId" -> (loginServerClient \ "result" \ "adminId").asOpt[String].get,
      "loginStatus" -> (loginServerClient \ "result" \ "status").asOpt[Boolean].get
    )
  )
  
  val configTreeServerClient1 = handelMessage(configTreeClientServer1)
  
  def e18 = (configTreeServerClient1 \ "jsonId").asOpt[Int].get === 6
  def e19 = (configTreeServerClient1 \ "dto").asOpt[String].get === "ConfigTree"
  def e20 = (configTreeServerClient1 \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
  def e21 = (((configTreeServerClient1 \ "result" \ "steps")(0)) \ "adminId").asOpt[String].get === 
    (loginServerClient \ "result" \ "adminId").asOpt[String].get
  def e22 = (((configTreeServerClient1 \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
  def e23 = (((configTreeServerClient1 \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 0
  
}