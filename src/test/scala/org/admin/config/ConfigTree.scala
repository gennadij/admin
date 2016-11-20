package org.admin.config

import org.admin.AdminWeb
import org.specs2.Specification
import play.api.libs.json.{JsValue, Json}

/**
  * Created by gennadi on 16.11.16.
  */
class ConfigTree extends Specification with AdminWeb{

  def is = s2"""
    Diese Specification prueft die Erzeugung eines neuen Steps
      autentification                                   $e1
      jsonId                                            $e2
      method                                            $e3
      adminId                                           $e4
      kind                                              $e5
    """


  val autentificationClientServer = Json.obj(
    "jsonId" -> 2,
    "method" -> "autheticate"
    ,"params" -> Json.obj(
      "username" -> "firstStepConfigTree",
      "password" -> "firstStepConfigTree"
    )
  )

  val autentificationServerClient = handelMessage(autentificationClientServer)
  def e1 = (autentificationServerClient \ "result" \ "authentication").asOpt[Boolean].get === true

  val firstStepConfigTreeClientServer = Json.obj(
    "jsonId" -> 4,
    "method" -> "addFirstStep"
    ,"params" -> Json.obj(
      "adminId" -> (autentificationServerClient \ "result" \ "adminId").asOpt[String].get,
      "kind" -> "immutable"
    )
  )

  val firstStepConfigTreeServerClient = handelMessage(firstStepConfigTreeClientServer)

  println(firstStepConfigTreeServerClient)

  def e2 = (firstStepConfigTreeServerClient \ "jsonId").asOpt[Int].get === 4
  def e3 = (firstStepConfigTreeServerClient \ "method").asOpt[String].get === "addFirstStep"
  def e4 = (firstStepConfigTreeServerClient \ "result" \ "adminId").asOpt[String].get ===
    (autentificationServerClient \ "result" \ "adminId").asOpt[String].get
  def e5 = (firstStepConfigTreeServerClient \ "result" \ "kind").asOpt[String].get === "immutable"
}
