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
      Login=========
      loginStatus=true                                                       $e1
      FirstStep
      jsonId = 4                                                             $e2
      dto = ConfigTree                                                       $e3
      adminId                                                                $e4
      kind = Immutable                                                       $e5
      ConfigTree
      jsonId = 6                                                             $e6
      dto = ConfigTree                                                       $e7
      configTree = FirstStep                                                 $e8
      
    """

  
  val loginClientServer = Json.obj(
    "jsonId" -> 2,
    "dto" -> "Login"
    ,"params" -> Json.obj(
      "username" -> "firstStepConfigTree",
      "password" -> "firstStepConfigTree"
    )
  )

  val loginServerClient = handelMessage(loginClientServer)
  def e1 = (loginServerClient \ "result" \ "authentication").asOpt[Boolean].get === true

//  ============================================================================
  
  val firstStepConfigTreeClientServer = Json.obj(
    "jsonId" -> 4,
    "method" -> "addFirstStep"
    ,"params" -> Json.obj(
      "adminId" -> (loginServerClient \ "result" \ "adminId").asOpt[String].get,
      "kind" -> "immutable",
      "loginStatus" -> (loginServerClient \ "result" \ "loginStatus").asOpt[Boolean].get
    )
  )

  val firstStepConfigTreeServerClient = handelMessage(firstStepConfigTreeClientServer)

  println(firstStepConfigTreeServerClient)

  def e2 = (firstStepConfigTreeServerClient \ "jsonId").asOpt[Int].get === 4
  def e3 = (firstStepConfigTreeServerClient \ "method").asOpt[String].get === "addFirstStep"
  def e4 = (firstStepConfigTreeServerClient \ "result" \ "adminId").asOpt[String].get ===
    (loginServerClient \ "result" \ "adminId").asOpt[String].get
  def e5 = (firstStepConfigTreeServerClient \ "result" \ "kind").asOpt[String].get === "immutable"

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
  def e8 = (configTreeServerClient \ "result" \ "steps").asOpt[List[JsValue]] === None
}
