package org.genericConfig.admin.controllers.admin

import play.api.libs.json._
import scala.collection.immutable.Seq
import play.api.Logger
import org.genericConfig.admin.models.json.component.JsonComponentIn
import org.genericConfig.admin.models.json.component.JsonComponentOut
import org.genericConfig.admin.models.json.connectionComponentToStep.JsonConnectionComponentToStepIn
import org.genericConfig.admin.models.json.connectionComponentToStep.JsonConnectionComponentToStepOut
import org.genericConfig.admin.models.json.dependency.JsonDependencyIn
import org.genericConfig.admin.shared.configTree.json._
import org.genericConfig.admin.models.json.dependency.JsonDependencyOut
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.registration.json._
import org.genericConfig.admin.shared.login.json._
import org.genericConfig.admin.shared.config.json._
import org.genericConfig.admin.shared.step.json._
import org.genericConfig.admin.shared.error.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 28.11.2016
 */

trait AdminWeb {
  
  def handleMessage(receivedMessage: JsValue, admin: Admin): JsValue = {
    (receivedMessage \ "json").asOpt[String] match {
      case Some(JsonNames.REGISTRATION) => register(receivedMessage, admin)
      
      case Some(JsonNames.LOGIN) => login(receivedMessage, admin)
      
      case Some(JsonNames.CREATE_CONFIG) => createConfig(receivedMessage, admin)
      case Some(JsonNames.GET_CONFIGS) => getConfigs(receivedMessage, admin)
      case Some(JsonNames.DELET_CONFIG) => deleteConfig(receivedMessage, admin)
      case Some(JsonNames.UPDATE_CONFIG) => updateConfig(receivedMessage, admin)
      
      case Some(JsonNames.CONFIG_TREE) => configTree(receivedMessage, admin)
      
      case Some(JsonNames.ADD_FIRST_STEP) => addFirstStep(receivedMessage, admin)
      case Some(JsonNames.CREATE_STEP) => createStep(receivedMessage, admin)
      
      case Some(JsonNames.CREATE_COMPONENT) => createComponent(receivedMessage, admin)
      case Some(JsonNames.CONNECTION_COMPONENT_TO_STEP) => connectComponentToStep(receivedMessage, admin)
      
      case Some(JsonNames.CREATE_DEPENDENCY) => createDependency(receivedMessage, admin)
      
      case Some(JsonNames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL) => 
        visualProposalForAdditionalStepsInOneLevel(receivedMessage, admin)
      case _ => Json.obj("error" -> "keinen Treffer")
    }
  }

  private def register(receivedMessage: JsValue, admin: Admin): JsValue = {
    val registrationIn: JsResult[JsonRegistrationIn] = Json.fromJson[JsonRegistrationIn](receivedMessage)
    registrationIn match {
      case s : JsSuccess[JsonRegistrationIn] => s.get
      case e : JsError => Logger.error("Errors -> REGISTRATION: " + JsError.toJson(e).toString())
    }
    val registrationOut: JsonRegistrationOut = 
      admin.register(registrationIn.get.params.username, registrationIn.get.params.password)
    Json.toJson(registrationOut)
  }

  private def login(receivedMessage: JsValue, admin: Admin): JsValue = {
    val loginIn: JsResult[JsonLoginIn] = Json.fromJson[JsonLoginIn](receivedMessage)
    loginIn match {
      case s : JsSuccess[JsonLoginIn] => s.get
      case e : JsError => Logger.error("Errors -> LOGIN: " + JsError.toJson(e).toString())
    }
    val loginOut: JsonLoginOut = admin.login(loginIn.get)
    Json.toJson(loginOut)
  }
  
  private def createConfig(receivedMessage: JsValue, admin: Admin): JsValue = {
    val createConfigIn: JsResult[JsonCreateConfigIn] = Json.fromJson[JsonCreateConfigIn](receivedMessage)
    createConfigIn match {
      case s : JsSuccess[JsonCreateConfigIn] => s.get
      case e : JsError => Logger.error("Errors -> CREATE_CONFIG: " + JsError.toJson(e).toString())
    }
    val createConfigOut: JsonCreateConfigOut = admin.createConfig(createConfigIn.get)
    Json.toJson(createConfigOut)
  }
  
  private def addFirstStep(receivedMessage: JsValue, admin: Admin): JsValue = {
    val firstStepIn: JsResult[JsonStepIn] = Json.fromJson[JsonStepIn](receivedMessage)
    firstStepIn match {
      case s : JsSuccess[JsonStepIn] => Json.toJson(admin.createFirstStep(firstStepIn.get))
      case e : JsError => jsonError("ADD_FIRST_STEP", e)
    }
  }
  
  private def createComponent(receivedMessage: JsValue, admin: Admin): JsValue = {
    val componentIn: JsResult[JsonComponentIn] = Json.fromJson[JsonComponentIn](receivedMessage)
    componentIn match {
      case s : JsSuccess[JsonComponentIn] => s.get
      case e : JsError => Logger.error("Errors -> CREATE_COMPONENT: " + JsError.toJson(e).toString())
    }
    val componentOut: JsonComponentOut = admin.createComponent(componentIn.get)
    Json.toJson(componentOut)
  }
  
  private def createStep(receivedMessage: JsValue, admin: Admin): JsValue = {
    val stepIn: JsResult[JsonStepIn] = Json.fromJson[JsonStepIn](receivedMessage)
    stepIn match {
      case s : JsSuccess[JsonStepIn] => s.get
      case e : JsError => Logger.error("Errors -> CREATE_STEPT: " + JsError.toJson(e).toString())
    }
    val stepOut: JsonStepOut = admin.createStep(stepIn.get)
    Json.toJson(stepOut)
  }
  
  private def connectComponentToStep(receivedMessage: JsValue, admin: Admin): JsValue = {
    val connectionComponentToStepIn: JsResult[JsonConnectionComponentToStepIn] = 
      Json.fromJson[JsonConnectionComponentToStepIn](receivedMessage)
    connectionComponentToStepIn match {
      case s : JsSuccess[JsonConnectionComponentToStepIn] => s.get
      case e : JsError => Logger.error("Errors -> CONNECTION_COMPONENT_TO_STEP: " + JsError.toJson(e).toString())
    }
    val connectionComponentToStepOut: JsonConnectionComponentToStepOut = 
      admin.connectComponentToStep(connectionComponentToStepIn.get)
    Json.toJson(connectionComponentToStepOut)
  }
  
  private def configTree(receivedMessage: JsValue, admin: Admin): JsValue = {
    val configTreeIn: JsResult[JsonConfigTreeIn] = Json.fromJson[JsonConfigTreeIn](receivedMessage)
    configTreeIn match {
      case s : JsSuccess[JsonConfigTreeIn] => s.get
      case e : JsError => Logger.error("Errors -> CONFIG_TREE: " + JsError.toJson(e).toString())
    }
    val configTreeOut: JsonConfigTreeOut = admin.getConfigTree(configTreeIn.get.params.configId)
    Json.toJson(configTreeOut)
  }
  
  private def createDependency(receivedMessage: JsValue, admin: Admin): JsValue = {
    val dependencyIn: JsResult[JsonDependencyIn] = Json.fromJson[JsonDependencyIn](receivedMessage)
    dependencyIn match {
      case s: JsSuccess[JsonDependencyIn] => s.get
      case e: JsError => Logger.error("Errors -> CREATE_DEPENDENCY: " + JsError.toJson(e).toString())
    }
    
    val dependencyOut: JsonDependencyOut  = admin.createDependency(dependencyIn.get)
    Json.toJson(dependencyOut)
   }
  
  private def visualProposalForAdditionalStepsInOneLevel(
      receivedMessage: JsValue, admin: Admin): JsValue = {
    val visualProposalForAdditionalStepsInOneLevelIn: JsResult[JsonVisualProposalForAdditionalStepsInOneLevelIn] = 
      Json.fromJson[JsonVisualProposalForAdditionalStepsInOneLevelIn](receivedMessage)
    visualProposalForAdditionalStepsInOneLevelIn match {
      case s: JsSuccess[JsonVisualProposalForAdditionalStepsInOneLevelIn] => s.get
      case e: JsError => {
        Logger.error("Errors -> VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL: " + 
            JsError.toJson(e).toString())
      }
    }
    
    val stepOut = admin.visualProposalForAdditionalStepsInOneLevel(
        visualProposalForAdditionalStepsInOneLevelIn.get)
    Json.toJson(stepOut)
  }
  
  private def getConfigs(receivedMessage: JsValue, admin: Admin): JsValue = {
    val getConfigsIn: JsResult[JsonGetConfigsIn] = Json.fromJson[JsonGetConfigsIn](receivedMessage)
    getConfigsIn match {
      case s: JsSuccess[JsonGetConfigsIn] => s.get
      case e: JsError => Logger.error("Errors -> CREATE_DEPENDENCY: " + JsError.toJson(e).toString())
    }
    
    val getConfigsOut: JsonGetConfigsOut  = admin.getConfigs(getConfigsIn.get)
    Json.toJson(getConfigsOut)
   }
  
  private def deleteConfig(receivedMessage: JsValue, admin: Admin): JsValue = {
    val deleteConfigIn: JsResult[JsonDeleteConfigIn] = Json.fromJson[JsonDeleteConfigIn](receivedMessage)
    deleteConfigIn match {
      case s: JsSuccess[JsonDeleteConfigIn] => s.get
      case e: JsError => Logger.error("Errors -> CREATE_DEPENDENCY: " + JsError.toJson(e).toString())
    }
    
    val deleteConfigOut: JsonDeleteConfigOut  = admin.deleteConfig(deleteConfigIn.get)
    Json.toJson(deleteConfigOut)
   }
  
  private def updateConfig(receivedMessage: JsValue, admin: Admin): JsValue = {
    val editConfigIn: JsResult[JsonUpdateConfigIn] = Json.fromJson[JsonUpdateConfigIn](receivedMessage)
    editConfigIn match {
      case s: JsSuccess[JsonUpdateConfigIn] => s.get
      case e: JsError => Logger.error("Errors -> EDIT_CONFIG: " + JsError.toJson(e).toString())
    }
    
    val editConfigOut: JsonUpdateConfigOut  = admin.editConfig(editConfigIn.get)
    Json.toJson(editConfigOut)
   }
  
  private def jsonError(errorText: String, e: JsError): JsValue = {
    val error = JsonErrorOut(
        JsonNames.ERROR,
        JsonErrorParams(
            "Errors -> " + errorText + " : " + JsError.toJson(e).toString()
        )
    )
    Logger.error(error.toString)
    Json.toJson(error)
  }
}
