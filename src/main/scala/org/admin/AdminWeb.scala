package org.admin

import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.persistence.db.orientdb.AdminUserVertex
import scala.collection.immutable.Seq
import org.dto.login.LoginCS
import org.dto.login.LoginSC
import org.dto.registration.RegistrationCS
import org.dto.registration.RegistrationSC
import org.dto.configTree.ConfigTreeCS
import org.dto.component.ComponentCS
import org.dto.component.ComponentSC
import org.dto.step.StepCS
import org.dto.step.StepSC
import org.dto.config.CreateConfigCS
import org.dto.config.CreateConfigSC
import org.dto.step.FirstStepCS
import org.dto.step.FirstStepSC
import org.dto.DTONames
import org.dto.connectionComponentToStep.ConnectionComponentToStepCS
import org.dto.connectionComponentToStep.ConnectionComponentToStepSC

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 28.11.2016
 */

trait AdminWeb {
  
  /**
   * ChangeLog 06.01.2017
   *   Vertex Step
   *     - selectionCriterium => definiert maximale und minimale Anzahl der ausgewaelten Komponenten in dem Step
   *     
   *   new Vertex Config
   *      + configUrl
   *   
   *   AdminUser
   *      - configUri
   *   
   *   Step
   *     - adminId
   *   
   *   Component
   *      - adminId
   *     
   *   AdminUser -> Config -> FitstStep
   *   
   *   Alle TD die An der Client desendet werden mit einem HASH Wert verschluesseln oder 
   *   verschluesselte Kommunikation
   *   
   *   Version 0.0.1 created 31.01.2017
   *   
   */
  
  /**
   * ChangeLog 09.01.2017
   * 
   * FirstStep wird immer an der Config Vertes angehaengt
   * 
   * NextStep wird immer an der Component Vertex angehaengt
   * 
   * deswegen wird es automatisch bei der erzeugen der jeweiligen Steps die Verbindungsedge erzeugt
   * 
   *  - ConnComponentToStep
   *  - ConnStepToComponent
   * 
   * 
   * Log to v0.1.0
   * 
   * Integration in einen Play Project
   * 
   */
  
  /**
   * 1. => Registration
   *   Server <- Client
   *   {jsonId : 1, dto : Registeration, params : {username : test, password : test}}
   *   Server -> Client
   *   {jsonId : 1, dto : Registeration, result : {adminId : #40:0, username : test, status : true, message : Nachricht}}
   * 2. => Login
   *   Server <- Client
   *   {jsond : 2, dto : Login, params : {username : test, password : test}}
   *   Server -> Client
   *   {jsonId : 2, dto: Login, result: {
   *      adminId : #40:0, username : test, configs: 
   *      [{configId: #12:23, configUrl: http:// .. } ..], status : true, message : Nachricht}}
   * 3. CreateConfig
   *   Server <- Client
   *   {jsond : 3, dto : CreateConfig, params : {adminId : #40:0, configUrl : test.test.org}
   *   Server -> Client
   *   {jsond : 3, dto : CreateConfig, result : {configId: #23:12, status : true, message : Nachricht}
   * 4. Config
   *   Server <- Client
   *   {dtoId : 4, dto : Config, params : {adminId : #40:0}
   *   Server -> Client
   *   {dtoId: 4, dto: Config, result: {configs: [{configId: #23:13, configUrl: http...}, ...]}}
   * 5. => removeAdmin
   * 6. => ConfigTree
   *   Server <- Client
   *   {jsonId : 6, dto : ConfigTree, params: {configId : #40:0}}
   *   Server -> Client
   *   {jsonId : 6, dto : ConfigTree, result: {steps :
          	[
          		{stepId :#19:1, kind: first, components:
          				[{componentId : #21:0, kind : immutable}]
          		}
          	]
          }}
   * 7. => FirstStep
   *   Server <- Client
   *   {jsonId: 7, dto : FirstStep, params : {configId : #40:0, kind  : first}}
   *   Server -> Client
   *   {jsonId : 7, dto : FirstStep, result : {stepId : #12:1, status : true, message : Nachricht}} 
   * 8. => Component
   *   Server <- Client
   *   {dtoIdId : 8, dto : Component, params : {stepId : #40:0, kind : immutable}
   *    Server -> Client
   *    {dtoId : 8, dto : Component, result : {componentId : #13:1, status : true, message : Nachricht}}
   * 10. => Step
   *   Server <- Client
   *   {jsonId : 10, dto : Step, params : {adminId : #40:0, kind : default}
   *   Server -> Client
   *    {jsonId : 10, dto : Step, result : {stepId : #14:1", status : true, message : Nachricht}}
   * 7. => ConnectionComponentToStep
   *    Server <- Client
   *    {jsonId : 11, dto : ConnComponentToStep, params : {adminId : #40:0, inStepId : #40:1, outComponentId : #40:2}}
   *    Server -> Client
   *    {jsonId : 11, dto : ConnComponentToStep, result : {status: true, message: Nachricht}}
   * 7. => updateStep update Step
   * 8. => deleteStep delete Step and its hasComponent  (brauche zugehörige Step, was soll mit der weiterer ConfigTree passieren)
   * 9. => updateComponent 
   * 10. => deleteComponen
   */
  
  def handelMessage(receivedMessage: JsValue): JsValue = {
    (receivedMessage \ "dto").asOpt[String] match {
      case Some(DTONames.REGISTRATION) => register(receivedMessage)
      case Some(DTONames.LOGIN) => login(receivedMessage)
      case Some(DTONames.CREATE_CONFIG) => createConfig(receivedMessage)
      case Some(DTONames.CREATE_FIRST_STEP) => createFirstStep(receivedMessage)
      case Some(DTONames.CONFIG_TREE) => configTree(receivedMessage)
      case Some(DTONames.CREATE_COMPONENT) => createComponent(receivedMessage)
      case Some(DTONames.CREATE_STEP) => createStep(receivedMessage)
      case Some(DTONames.CONNECTION_COMPONENT_TO_STEP) => connectComponentToStep(receivedMessage)
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
  
  private def createConfig(receivedMessage: JsValue): JsValue = {
    val createConfigCS: CreateConfigCS = Json.fromJson[CreateConfigCS](receivedMessage).get
    val createConfigSC: CreateConfigSC = Admin.createConfig(createConfigCS)
    Json.toJson(createConfigSC)
  }
  //TODO Parameter kind ist hier umbrauchbar
  private def createFirstStep(receivedMessage: JsValue): JsValue = {
    val firstStepCS: FirstStepCS = Json.fromJson[FirstStepCS](receivedMessage).get
    val firstStepSC: FirstStepSC = Admin.createFirstStep(firstStepCS)
    Json.toJson(firstStepSC)
  }
  
  private def createComponent(receivedMessage: JsValue): JsValue = {
    val componentCS: ComponentCS = Json.fromJson[ComponentCS](receivedMessage).get
    val componentSC: ComponentSC = Admin.createComponent(componentCS)
    Json.toJson(componentSC)
  }
  
  private def createStep(receivedMessage: JsValue): JsValue = {
    val stepCS: StepCS = Json.fromJson[StepCS](receivedMessage).get
    val stepSC: StepSC = Admin.createStep(stepCS)
    Json.toJson(stepSC)
  }
  
  private def connectComponentToStep(receivedMessage: JsValue): JsValue = {
    val connectionComponentToStepCS: ConnectionComponentToStepCS = 
      Json.fromJson[ConnectionComponentToStepCS](receivedMessage).get
    val connectionComponentToStepSC: ConnectionComponentToStepSC = 
      Admin.connectComponentToStep(connectionComponentToStepCS)
    Json.toJson(connectionComponentToStepSC)
  }
  
  private def configTree(receivedMessage: JsValue): JsValue = {
    val configTreeCS: ConfigTreeCS = Json.fromJson[ConfigTreeCS](receivedMessage).get
    val steps = Admin.configTree(configTreeCS)
    Json.toJson(steps)
  }
}