package util

import play.api.libs.json.Json
import org.genericConfig.admin.controllers.websocket.WebClient
import play.api.Logger
import scala.collection.JavaConverters._
import org.genericConfig.admin.models.persistence.orientdb.Graph
import org.genericConfig.admin.shared.common.json.JsonNames
import play.api.libs.json.JsValue

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 22.04.2018
 */
trait CommonFunction {
  
  def newAdminUser(username: String) : (String, String) = {
    val registerCS = Json.obj(
          "json" -> JsonNames.REGISTRATION
          ,"params" -> Json.obj(
               "username" -> username,
               "password"-> username
           )
      )
      val wC = WebClient.init
      val registerSC = wC.handleMessage(registerCS)
//      Logger.info("<- " + registerCS)
//      Logger.info("-> " + registerSC)
      
      (registerSC \ "result" \ "status" \ "addUser" \ "status").asOpt[String].get.toString() match {
      case "ADDED_USER" => {
        (
          (registerSC \ "result" \ "username").asOpt[String].get,
          (registerSC \ "result" \ "adminId").asOpt[String].get
        )
      }
      case "ALREDY_EXIST_USER" => {
        (
          (registerSC \ "result" \ "username").asOpt[String].get,
          (registerSC \ "result" \ "adminId").asOpt[String].get
        )
      }
    }
  }
  
  def deleteAllConfigs(username: String): Int = {
    Graph.deleteAllConfigs(username)
  }
  
  def createConfig(userId: String, configUrl: String, wC: WebClient): JsValue = {
    val newConfigIn = Json.obj(
        "json" -> JsonNames.CREATE_CONFIG
        , "params" -> Json.obj(
            "adminId" -> userId,
            "configUrl" -> configUrl
        )
    )
    
    val newConfigOut = wC.handleMessage(newConfigIn)
	  
//    Logger.info("newConfigIn " + newConfigIn)
//    Logger.info("newConfigOut " + newConfigOut)
    
    newConfigOut
  }
  
  def deleteStepAppendedToConfig(configId: String) = {
    Graph.deleteStepAppendedToConfig(configId)
  }
  
  def getConfigs(userId: String, wC: WebClient) = {
    val getConfigsIn = Json.obj(
          "json" -> JsonNames.GET_CONFIGS
          , "params" -> Json.obj(
              "userId" -> userId
          )
      )
      val getConfigsOut = wC.handleMessage(getConfigsIn)
      
      Logger.info("getConfigsIn " + getConfigsIn)
      Logger.info("getConfigsOut " + getConfigsOut)
      
      val jsConfigsIds: Set[JsValue] = (getConfigsOut \ "result" \ "configs").asOpt[Set[JsValue]].get
      
      jsConfigsIds map( jsCId => (jsCId \ "configId").asOpt[String].get )
  }
}