package util

import play.api.libs.json.Json
import org.genericConfig.admin.shared.json.JsonNames
import org.genericConfig.admin.controllers.websocket.WebClient
import play.api.Logger
import org.genericConfig.admin.shared.status.registration.AddedUser
import org.genericConfig.admin.shared.status.registration.AlredyExistUser
import scala.collection.JavaConverters._
import org.genericConfig.admin.models.persistence.orientdb.Graph

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
      Logger.info("<- " + registerCS)
      Logger.info("-> " + registerSC)
      
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
}