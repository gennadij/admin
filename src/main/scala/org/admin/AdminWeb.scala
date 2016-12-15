/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.admin

import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.admin.configTree.AdminStep
import org.admin.configTree.AdminComponent
import org.persistence.db.orientdb.AdminUserVertex
import org.admin.configTree.AdminConfigTree
import org.admin.configTree.AdminConfigTreeStep
import scala.collection.immutable.Seq
import org.dto.login.LoginCS
import org.dto.login.LoginSC
import org.dto.firstStep.FirstStepCS
import org.dto.registration.RegistrationCS
import org.dto.registration.RegistrationSC
import org.dto.configTree.ConfigTreeCS
import org.dto.component.ComponentCS
import org.dto.component.ComponentSC

trait AdminWeb {
  
  //TODO Objekte für Transport erzeugen
  /**
   * 1. register => register Admin
   *   Server <- Client
   *   {"jsonId": 1, "method": "register", "params": {"username": "test", "password": "test"}}
   *   Server -> Client
   *   {"jsonId": 1, "method": "register", result": {"adminId": "AU#40:0", "username": "test"}}
   * 2. => login Admin
   *   Server <- Client
   *   {"jsonId": 2, "method": "autheticate", params: {"username": "test", "password": "test"}}
   *   Server -> Client
   *   {"jsonId": 2, "method": "autheticate", 
   *       result: {"id": "AU#40:0", "username": "test", "password": "test", "authentication": true}}
   * 3. => updateUser
   * 4. => updatePassword
   * 5. => removeAdmin
   * 6. => configTree
   *   Server <- Client
   *   {"jsonId": 6, "method": "configTree", params: {"adminId": "AU#40:0", "authentication": true}}
   *   Server -> Client
   *   {"jsonId": 7, "method": "configTree", result: {"steps":
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
   *      "result": {"id": "#13:1", "componentId": "C#13:1", "adminId": "AU#40:0", "kind": "immutable", "stepId": "#12:1"}}
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
      case Some("FirstStep") => firstStep(receivedMessage)
      case Some("ConfigTree") => configTree(receivedMessage)
      case Some("Component") => component(receivedMessage)
      case Some("NextStep") => addNextStep(receivedMessage)
      case _ => Json.obj("error" -> "keinen Treffer")
    }
  }

  private def register(receivedMessage: JsValue): JsValue = {
    val registrationCS: RegistrationCS = Json.fromJson[RegistrationCS](receivedMessage).get
    val registrationSC: RegistrationSC = Admin.register(registrationCS)
    Json.toJson(registrationSC)
  }

  private def login(receivedMessage: JsValue): JsValue = {
    val loginCS: LoginCS = Json.fromJson[LoginCS](receivedMessage).get
    val loginSC: LoginSC = Admin.login(loginCS)
    Json.toJson(loginSC)
  }
  
  
  private def firstStep(receivedMessage: JsValue): JsValue = {
    val firstStepCS: FirstStepCS = Json.fromJson[FirstStepCS](receivedMessage).get
    //TODO impl Reads with validation show 
    // https://www.playframework.com/documentation/2.4.x/ScalaJsonCombinators
    val step = Admin.addFirstStep(firstStepCS)
    Json.toJson(step)
  }
  
  private def component(receivedMessage: JsValue): JsValue = {
    val componentCS: ComponentCS = Json.fromJson[ComponentCS](receivedMessage).get
    
//    val adminId = (receivedMessage \ "adminId").toString()
//    val kind = (receivedMessage \ "kind").toString()
//    val stepId = (receivedMessage \ "stepId").toString()
    val componentSC: ComponentSC = Admin.addComponent(componentCS)
//    val component = Admin.addComponent(new AdminComponent("", "", adminId, kind))
//    val hasComponent = Admin.addHasComponent(adminId, stepId, component.id)
    val hasComponent = Admin.addHasComponent(componentSC)
//    Json.toJson(component)
    Json.toJson(componentSC)
  }
  
  private def addNextStep(receivedMessage: JsValue): JsValue = {
    val adminId = (receivedMessage \ "adminId").toString()
    val kind = (receivedMessage \ "kind").toString()
    val componentId = (receivedMessage \ "componentId").toString()
//    val step = Admin.addStep(new AdminStep("", "", adminId, kind))
//    val nextStep = Admin.addNextStep(adminId, componentId, step.id)
    Json.toJson("")
  }
  
  private def configTree(receivedMessage: JsValue): JsValue = {
    val configTreeCS: ConfigTreeCS = Json.fromJson[ConfigTreeCS](receivedMessage).get
    val steps = Admin.configTree(configTreeCS)
    Json.toJson(steps)
  }
}