package org.admin

import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.admin.configTree.AdminStep
import org.admin.configTree.AdminComponent
import org.persistence.db.orientdb.AdminUserVertex
import org.admin.configTree.AdminConfigTree
import org.admin.configTree.AdminConfigTreeStep
import scala.collection.immutable.Seq
import org.dto.RegistrationCS
import org.dto.RegistrationSC
import org.dto.LoginCS

trait AdminWeb {
  
  //TODO Objekte für Transport erzeugen
  /**
   * 1. register => register Admin
   *   Server <- Client
   *   {"jsonId": 1, "method": "register", "params": {"username": "test", "password": "test"}}
   *   Server -> Client
   *   {"jsonId": 1, "method": "register", result": {"adminId": "AU#40:0", "username": "test"}}
   * 2. => authenticate Admin
   *   Server <- Client
   *   {"jsonId": 2, "method": "autheticate", params: {"username": "test", "password": "test"}}
   *   Server -> Client
   *   {"jsonId": 2, "method": "autheticate", 
   *       result: {"id": "AU#40:0", "username": "test", "password": "test", "authentication": true}}
   * 3. => configTree
   *   Server <- Client
   *   {"jsonId": 3, "method": "configTree", params: {"adminId": "AU#40:0", "authentication": true}}
   *   Server -> Client
   *   {"jsonId": 3, "method": "configTree", result: {"steps":
                  	[
                  		{
                  			"id":"#19:1","stepId":"#19:1","adminId":"AU#37:0","kind":"first",
                  			"components":
                  				[
                  					{
                  						"id":"#21:0","componentId":"C#21:0","adminId":"AU#37:0","kind":"immutable","nextSteps":"NS#17:2"
                  					},
                  					{
                  						"id":"#22:0","componentId":"C#22:0","adminId":"AU#37:0","kind":"immutable","nextSteps":"NS#17:2"
                  					},
                  					{
                  						"id":"#23:0","componentId":"C#23:0","adminId":"AU#37:0","kind":"immutable","nextSteps":"NS#17:2"
                  					}
                  				]
                  		},
                  		{
                  			"id":"#17:2","stepId":"#17:2","adminId":"AU#37:0","kind":"default",
                  			"components":
                  				[
                  					{
                  						"id":"#23:1","componentId":"C#23:1","adminId":"AU#37:0","kind":"immutable","nextSteps":"NS#18:2"
                  					},
                  					{
                  						"id":"#22:1","componentId":"C#22:1","adminId":"AU#37:0","kind":"immutable","nextSteps":"NS#18:2"
                  					},
                  					{
                  						"id":"#21:1","componentId":"C#21:1","adminId":"AU#37:0","kind":"immutable","nextSteps":"NS#18:2"
                  					}
                  				]
                  		}
                  	]
                  }}
   * 4. => addFirstStep
   *   Server <- Client
   *   {"jsonId": 4, "method": "addFirstStep", "params": {"adminId": "AU#40:0", "kind": "immutable"}}
   *   Server -> Client
   *   {"jsonId": 4, "method": "addFirstStep", 
   *     "result": {"id": "#12.1", "stepId": "S#12.1", "adminId": "AU#40:0", "kind": "first" }} 
   * 5. => addComponent and connect Step with Component
   *   Server <- Client
   *   {"jsonId": 5, "method": "addComponent", "params": {"adminId": "AU#40:0", "kind": "immutable", "stepId": "#12:1"}
   *    Server -> Client
   *    {"jsonId": 5, "method": "addComponent", 
   *      "result": {"id": "#13:1", "componentId": "C#13:1", "adminId": "AU#40:0", "kind": "immutable"}}
   * 6. => addNextStep add connect Component with NextStep
   *   Server <- Client
   *   {"jsonId": 6, "method": "addNextStep", "params": {"adminId": "AU#40:0", "kind": "default", "componentId": "#13:1"}
   *   Server -> Client
   *    {"jsonId": 6, "method": "addNextStep", 
   *      "result": {"id": "#14:1", "stepId": "S#14:1", "adminId": "AU#40:0", "kind": "default"}}
   * 7. => updateStep update Step
   * 8. => deleteStep delete Step and its hasComponent  (brauche zugehörige Step, was soll mit der weiterer ConfigTree passieren)
   * 9. => updateComponent 
   * 10. => deleteComponen
   */
  
  def handelMessage(receivedMessage: JsValue): JsValue = {
    (receivedMessage \ "dto").asOpt[String] match {
      case Some("Registration") => register(receivedMessage)
      case Some("Login") => login(receivedMessage)
      case Some("addFirstStep") => addFirstStep(receivedMessage)
      case Some("configTree") => configTree(receivedMessage)
      case Some("addComponent") => addComponent(receivedMessage)
      case Some("addNextStep") => addNextStep(receivedMessage)
      case _ => Json.obj("error" -> "keinen Treffer")
    }
  }

  private def register(receivedMessage: JsValue): JsValue = {
    val register: RegistrationCS = Json.fromJson[RegistrationCS](receivedMessage).get
    val admin: RegistrationSC = Admin.registAdminUser(register)
    Json.toJson(admin)
    
//    Json.obj(
//      "jsonId" -> 1,
//      "method" -> "register"
//      ,"result"-> Json.toJson(admin)
//    )

  }

  private def login(receivedMessage: JsValue): JsValue = {
    val loginDTO: LoginCS = Json.fromJson[LoginCS](receivedMessage).get
//    val username = (receivedMessage \ "params" \"username").asOpt[String].get
//    val password = (receivedMessage \ "params" \ "password").asOpt[String].get
    val adminId = Admin.authenticate(loginDTO)
    //TODO impl autentification
    val adminUser = new AdminUser(adminId, loginDTO.params.username, 
        loginDTO.params.password, true)
    println(adminUser)
    //TODO impl LoginDTO
    Json.obj(
        "jsonId"-> 2, 
        "dto" -> "Login"
        ,"result" -> Json.toJson(adminUser))
  }
  private def addFirstStep(receivedMessage: JsValue): JsValue = {
    //TODO impl Reads with validation show 
    // https://www.playframework.com/documentation/2.4.x/ScalaJsonCombinators
    val adminId = (receivedMessage \ "params" \ "adminId").asOpt[String].get
    val kind = (receivedMessage \ "params" \ "kind").asOpt[String].get
    val step = Admin.addStep(new AdminStep("", "", adminId, kind))
    Json.obj(
        "jsonId"-> 4,
        "method" -> "addFirstStep"
        ,"result" -> Json.toJson(step)
        )
  }
  
  private def addComponent(receivedMessage: JsValue): JsValue = {
    val adminId = (receivedMessage \ "adminId").toString()
    val kind = (receivedMessage \ "kind").toString()
    val stepId = (receivedMessage \ "stepId").toString()
    val component = Admin.addComponent(new AdminComponent("", "", adminId, kind))
    val hasComponent = Admin.addHasComponent(adminId, stepId, component.id)
    Json.toJson(component)
  }
  
  private def addNextStep(receivedMessage: JsValue): JsValue = {
    val adminId = (receivedMessage \ "adminId").toString()
    val kind = (receivedMessage \ "kind").toString()
    val componentId = (receivedMessage \ "componentId").toString()
    val step = Admin.addStep(new AdminStep("", "", adminId, kind))
    val nextStep = Admin.addNextStep(adminId, componentId, step.id)
    Json.toJson(step)
  }
  
  private def configTree(receivedMessage: JsValue): JsValue = {
    val adminId = (receivedMessage \ "params" \ "adminId").asOpt[String].get
    val authentication = (receivedMessage \ "params" \ "authentication").asOpt[Boolean].get
    val steps = Admin.configTree(adminId)
    Json.obj(
      "jsonId"-> 3,
      "method" -> "configTree"
      ,"result" -> Json.toJson(steps)
    )
  }
}