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
    val registrationCS: JsResult[RegistrationCS] = Json.fromJson[RegistrationCS](receivedMessage)
    registrationCS match {
      case s : JsSuccess[RegistrationCS] => s.get
      case e : JsError => println("Errors -> REGISTRATION: " + JsError.toJson(e).toString())
    }
    val registrationSC: RegistrationSC = Admin.register(registrationCS.get)
    Json.toJson(registrationSC)
  }

  private def login(receivedMessage: JsValue): JsValue = {
    val loginCS: JsResult[LoginCS] = Json.fromJson[LoginCS](receivedMessage)
    loginCS match {
      case s : JsSuccess[LoginCS] => s.get
      case e : JsError => println("Errors -> LOGIN: " + JsError.toJson(e).toString())
    }
    val loginSC: LoginSC = Admin.login(loginCS.get)
    Json.toJson(loginSC)
  }
  
  private def createConfig(receivedMessage: JsValue): JsValue = {
    val createConfigCS: JsResult[CreateConfigCS] = Json.fromJson[CreateConfigCS](receivedMessage)
    createConfigCS match {
      case s : JsSuccess[CreateConfigCS] => s.get
      case e : JsError => println("Errors -> CREATE_CONFIG: " + JsError.toJson(e).toString())
    }
    val createConfigSC: CreateConfigSC = Admin.createConfig(createConfigCS.get)
    Json.toJson(createConfigSC)
  }
  
  private def createFirstStep(receivedMessage: JsValue): JsValue = {
    val firstStepCS: JsResult[FirstStepCS] = Json.fromJson[FirstStepCS](receivedMessage)
    firstStepCS match {
      case s : JsSuccess[FirstStepCS] => s.get
      case e : JsError => println("Errors -> CREATE_FIRST_STEP: " + JsError.toJson(e).toString())
    }
    val firstStepSC: FirstStepSC = Admin.createFirstStep(firstStepCS.get)
    Json.toJson(firstStepSC)
  }
  
  private def createComponent(receivedMessage: JsValue): JsValue = {
    val componentCS: JsResult[ComponentCS] = Json.fromJson[ComponentCS](receivedMessage)
    componentCS match {
      case s : JsSuccess[ComponentCS] => s.get
      case e : JsError => println("Errors -> CREATE_COMPONENT: " + JsError.toJson(e).toString())
    }
    val componentSC: ComponentSC = Admin.createComponent(componentCS.get)
    Json.toJson(componentSC)
  }
  
  private def createStep(receivedMessage: JsValue): JsValue = {
    val stepCS: JsResult[StepCS] = Json.fromJson[StepCS](receivedMessage)
    stepCS match {
      case s : JsSuccess[StepCS] => s.get
      case e : JsError => println("Errors -> CREATE_STEPT: " + JsError.toJson(e).toString())
    }
    val stepSC: StepSC = Admin.createStep(stepCS.get)
    Json.toJson(stepSC)
  }
  
  private def connectComponentToStep(receivedMessage: JsValue): JsValue = {
    val connectionComponentToStepCS: JsResult[ConnectionComponentToStepCS] = 
      Json.fromJson[ConnectionComponentToStepCS](receivedMessage)
    connectionComponentToStepCS match {
      case s : JsSuccess[ConnectionComponentToStepCS] => s.get
      case e : JsError => println("Errors -> CONNECTION_COMPONENT_TO_STEP: " + JsError.toJson(e).toString())
    }
    val connectionComponentToStepSC: ConnectionComponentToStepSC = 
      Admin.connectComponentToStep(connectionComponentToStepCS.get)
    Json.toJson(connectionComponentToStepSC)
  }
  
  private def configTree(receivedMessage: JsValue): JsValue = {
    val configTreeCS: JsResult[ConfigTreeCS] = Json.fromJson[ConfigTreeCS](receivedMessage)
    configTreeCS match {
      case s : JsSuccess[ConfigTreeCS] => s.get
      case e : JsError => println("Errors -> CONFIG_TREE: " + JsError.toJson(e).toString())
    }
    val configTreeSC = Admin.configTree(configTreeCS.get)
    Json.toJson(configTreeSC)
  }
}