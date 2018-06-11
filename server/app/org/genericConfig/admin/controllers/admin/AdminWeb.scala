package org.genericConfig.admin.controllers.admin

import org.genericConfig.admin.models.json.component.{JsonComponentIn, JsonComponentOut}
import org.genericConfig.admin.models.json.connectionComponentToStep.{JsonConnectionComponentToStepIn, JsonConnectionComponentToStepOut}
import org.genericConfig.admin.models.json.dependency.{JsonDependencyIn, JsonDependencyOut}
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.config.json._
import org.genericConfig.admin.shared.configTree.json._
import org.genericConfig.admin.shared.error.json._
import org.genericConfig.admin.shared.step.json._
import org.genericConfig.admin.shared.user.json.JsonUserIn
import play.api.Logger
import play.api.libs.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 28.11.2016
 */

trait AdminWeb {
  
  def handleMessage(receivedMessage: JsValue, admin: Admin): JsValue = {
    (receivedMessage \ "json").asOpt[String] match {
      case Some(JsonNames.ADD_USER) => addUser(receivedMessage, admin)
      case Some(JsonNames.GET_USER) => getUser(receivedMessage, admin)
      
      case Some(JsonNames.ADD_CONFIG) => addConfig(receivedMessage, admin)
      case Some(JsonNames.GET_CONFIGS) => getConfigs(receivedMessage, admin)
      case Some(JsonNames.DELET_CONFIG) => deleteConfig(receivedMessage, admin)
      case Some(JsonNames.UPDATE_CONFIG) => updateConfig(receivedMessage, admin)
      
      case Some(JsonNames.CONFIG_TREE) => configTree(receivedMessage, admin)
      
      case Some(JsonNames.ADD_FIRST_STEP) => addFirstStep(receivedMessage, admin)
      case Some(JsonNames.DELETE_FIRST_STEP) => deleteFirstStep(receivedMessage, admin)
      case Some(JsonNames.UPDATE_STEP) => updateStep(receivedMessage, admin)
      case Some(JsonNames.ADD_STEP) => addStep(receivedMessage, admin)
      
      case Some(JsonNames.CREATE_COMPONENT) => createComponent(receivedMessage, admin)
      case Some(JsonNames.CONNECTION_COMPONENT_TO_STEP) => connectComponentToStep(receivedMessage, admin)
      
      case Some(JsonNames.CREATE_DEPENDENCY) => createDependency(receivedMessage, admin)
      
      case Some(JsonNames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL) => 
        visualProposalForAdditionalStepsInOneLevel(receivedMessage, admin)
      case _ => Json.obj("error" -> "keinen Treffer")
    }
  }

  private def addUser(receivedMessage: JsValue, admin: Admin): JsValue = {
    val registrationIn: JsResult[JsonUserIn] = Json.fromJson[JsonUserIn](receivedMessage)
    registrationIn match {
      case s : JsSuccess[JsonUserIn] => Json.toJson(admin.addUser(s.value))
      case e : JsError => jsonError(JsonNames.ADD_USER, e)
    }
  }

  private def getUser(receivedMessage: JsValue, admin: Admin): JsValue = {
    val loginIn: JsResult[JsonUserIn] = Json.fromJson[JsonUserIn](receivedMessage)
    loginIn match {
      case _: JsSuccess[JsonUserIn] => Json.toJson(admin.getUser(loginIn.get))
      case e : JsError => jsonError(JsonNames.GET_USER, e)
    }
  }
  
  private def addConfig(receivedMessage: JsValue, admin: Admin): JsValue = {
    val addConfigIn: JsResult[JsonAddConfigIn] = Json.fromJson[JsonAddConfigIn](receivedMessage)
    addConfigIn match {
      case _ : JsSuccess[JsonAddConfigIn] => Json.toJson(admin.addConfig(addConfigIn.get))
      case e : JsError => jsonError(JsonNames.ADD_CONFIG, e)
    }
  }
  
  private def addFirstStep(receivedMessage: JsValue, admin: Admin): JsValue = {
    val firstStepIn: JsResult[JsonStepIn] = Json.fromJson[JsonStepIn](receivedMessage)
    firstStepIn match {
      case _ : JsSuccess[JsonStepIn] => Json.toJson(admin.addFirstStep(firstStepIn.get))
      case e : JsError => jsonError("ADD_FIRST_STEP", e)
    }
  }
  
  private def deleteFirstStep(receivedMessage: JsValue, admin: Admin): JsValue = {
    val deleteFirstStepIn: JsResult[JsonStepIn] = Json.fromJson[JsonStepIn](receivedMessage)
    deleteFirstStepIn match {
      case _ : JsSuccess[JsonStepIn] => Json.toJson(admin.deleteFirstStep(deleteFirstStepIn.get))
      case e : JsError => jsonError(JsonNames.DELETE_FIRST_STEP, e)
    }
  }
  
  private def updateStep(receivedMessage: JsValue, admin: Admin): JsValue = {
    val updateStepIn: JsResult[JsonStepIn] = Json.fromJson[JsonStepIn](receivedMessage)
    updateStepIn match {
      case _ : JsSuccess[JsonStepIn] => Json.toJson(admin.updateStep(updateStepIn.get))
      case e : JsError => jsonError(JsonNames.UPDATE_STEP, e)
    }
  }
  
  private def createComponent(receivedMessage: JsValue, admin: Admin): JsValue = {
    val componentIn: JsResult[JsonComponentIn] = Json.fromJson[JsonComponentIn](receivedMessage)
    componentIn match {
      case s : JsSuccess[JsonComponentIn] => s.get
      case e : JsError => Logger.error("Errors -> CREATE_COMPONENT: " + JsError.toJson(e).toString())
    }
    val componentOut: JsonComponentOut = admin.addComponent(componentIn.get)
    Json.toJson(componentOut)
  }
  
  private def addStep(receivedMessage: JsValue, admin: Admin): JsValue = {
    val stepIn: JsResult[JsonStepIn] = Json.fromJson[JsonStepIn](receivedMessage)
    stepIn match {
      case s : JsSuccess[JsonStepIn] => s.get
      case e : JsError => Logger.error("Errors -> CREATE_STEPT: " + JsError.toJson(e).toString())
    }
    val stepOut: JsonStepOut = admin.addStep(stepIn.get)
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
      case _: JsSuccess[JsonConfigTreeIn] => Json.toJson(admin.getConfigTree(configTreeIn.get.params.configId))
      case e : JsError => jsonError(JsonNames.CONFIG_TREE, e)
    }
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
      case e: JsError =>
        Logger.error("Errors -> VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL: " +
            JsError.toJson(e).toString())
    }
    
    val stepOut = admin.visualProposalForAdditionalStepsInOneLevel(
        visualProposalForAdditionalStepsInOneLevelIn.get)
    Json.toJson(stepOut)
  }
  
  private def getConfigs(receivedMessage: JsValue, admin: Admin): JsValue = {
    val getConfigsIn: JsResult[JsonGetConfigsIn] = Json.fromJson[JsonGetConfigsIn](receivedMessage)
    getConfigsIn match {
      case _: JsSuccess[JsonGetConfigsIn] => Json.toJson(admin.getConfigs(getConfigsIn.get))
      case e: JsError => jsonError(JsonNames.GET_CONFIGS, e)
    }
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
    
    val editConfigOut: JsonUpdateConfigOut  = admin.updateConfig(editConfigIn.get)
    Json.toJson(editConfigOut)
   }
  
  private def jsonError(errorText: String, e: JsError): JsValue = {
    val error = JsonErrorIn(
        JsonNames.ERROR,
        JsonErrorParams(
            "Errors -> " + errorText + " : " + JsError.toJson(e).toString()
        )
    )
    Logger.error(error.toString)
    Json.toJson(error)
  }
}
