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

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 29.01.2017
 * 
 * Username = user9
 */

@RunWith(classOf[JUnitRunner])
class ConfigTreeEmptySpecs extends Specification with AdminWeb with BeforeAfterAll {

  val wC = WebClient.init
  
  def afterAll(): Unit = {
    PrepareConfigsForSpecsv011.prepareConfigTreeEmpty(wC)
  }
  
  def beforeAll(): Unit = {
  }
      
  "Diese Specification prueft die leere Konfiguration" >> {
    val loginClientServer = Json.obj(
        "json" -> JsonNames.GET_USER
        ,"params" -> Json.obj(
            "username" -> "user9",
            "password" -> "user9"
        )
    )
    
    val loginOut = wC.handleMessage(loginClientServer)
    
    Logger.info("->" + loginOut)
    
    val configId = ((loginOut \ "result" \ "configs")(0) \ "configId").asOpt[String].get
    
    
    
    (loginOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    
    val configTreeIn = Json.obj(
        "json" -> JsonNames.CONFIG_TREE
        ,"params" -> Json.obj(
            "configId" -> configId
        )
    )
    val configTreeOut = wC.handleMessage(configTreeIn)
    
    Logger.info("<- " + configTreeIn)
    Logger.info("-> " + configTreeOut)
    
    (configTreeOut \ "json").asOpt[String].get === JsonNames.CONFIG_TREE
    (configTreeOut \ "result" \ "step").asOpt[String] === None
    (configTreeOut \ "result" \ "status" \ "getConfigTree" \ "status").asOpt[String].get === GetConfigTreeEmpty().status
    (configTreeOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
  }
}
