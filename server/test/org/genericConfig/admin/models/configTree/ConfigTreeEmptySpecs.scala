package org.genericConfig.admin.models.configTree

import models.preparingConfigs.PrepareConfigsForSpecsv011
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.configTree.status.GetConfigTreeEmpty
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.Json
import util.CommonFunction
import org.genericConfig.admin.shared.user.status.AddUserSuccess
import org.genericConfig.admin.shared.user.status.AddUserAlreadyExist
import org.genericConfig.admin.shared.user.status.AddUserError

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 29.01.2017
 * 
 * Username = user9
 */

@RunWith(classOf[JUnitRunner])
class ConfigTreeEmptySpecs extends Specification 
                           with AdminWeb 
                           with BeforeAfterAll
                           with CommonFunction {

  val wC: WebClient = WebClient.init
  
  val usernamePassword = "user9"
  
  var configId = ""
  
  def afterAll(): Unit = {
    
  }
  
  def beforeAll(): Unit = {
    val (username: String, userId: String, status: String) = addUser(usernamePassword)
    Logger.info(username + userId + status)
      status match {
        case s if AddUserSuccess().status == s =>
          val (configId: String, _) = addConfig(userId, s"http://contig/$username")
          this.configId = configId
        case s if AddUserAlreadyExist().status == s =>
          this.configId = getConfigId(usernamePassword = usernamePassword, configUrl = s"http://contig/$username")
        case s if AddUserError().status == s =>  
          Logger.info("Fehler bei der Vorbereitung")
      }
  }
      
  "Diese Specification prueft die leere Konfiguration" >> {
    val configTreeIn = Json.obj(
        "json" -> JsonNames.CONFIG_TREE
        ,"params" -> Json.obj(
            "configId" -> configId
        )
    )
    
    Logger.info("<- " + configTreeIn)
    val configTreeOut = wC.handleMessage(configTreeIn)
    Logger.info("-> " + configTreeOut)
    
    (configTreeOut \ "json").asOpt[String].get === JsonNames.CONFIG_TREE
    (configTreeOut \ "result" \ "step").asOpt[String] === None
    (configTreeOut \ "result" \ "status" \ "getConfigTree" \ "status").asOpt[String].get === GetConfigTreeEmpty().status
    (configTreeOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
  }
}
