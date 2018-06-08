package models.v016.step.deleteStep

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.step.status.DeleteStepSuccess
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.Json
import util.CommonFunction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 17.05.2018
 */

@RunWith(classOf[JUnitRunner])
class DeleteFirstStepSpecs_v016_1 extends Specification 
                           with BeforeAfterAll
                           with CommonFunction{
  
  val wC: WebClient = WebClient.init
  var userId: String = ""
  var configId: String = ""
  var stepId: String = ""
  val username = "user_deleteStep_v016_1"
  
  def beforeAll(): Unit = {
    val (username, userId, _): (String, String, String) = addUser(this.username)
    this.userId = userId
    val (configId: String, _) = addConfig(userId, "//http://contig/" + username)
      
    this.configId = configId
    
    this.stepId = addStep(wC, configId = Some(this.configId)).get
    
    Logger.info("username : " + username)
    Logger.info("userId : " + userId)
    Logger.info("configId : " + configId)
    Logger.info("stepId : " + stepId)
  }
  
  def afterAll(): Unit = {
    Logger.info("Deleting Step :" + + deleteStepAppendedToConfig(this.configId))
    Logger.info("Deleting Configs : " + deleteAllConfigs(this.username))
  }
  
  "Diese Spezifikation spezifiziert die Entfernung eines Schrittes" >> {
    "AdminUser = user_deleteStep_v016_1" >> {
      
      val jsonUpdateStepIn = Json.obj(
          "json" -> JsonNames.UPDATE_STEP,
          "params" -> Json.obj(
              "configId" -> "",
              "componentId" -> "",
              "stepId" -> this.stepId,
              "nameToShow" -> "",
              "kind" -> "",
              "selectionCriterium" -> Json.obj(
                  "min" -> 0,
                  "max" -> 0
              )
          )
      )
      
      Logger.info("->" + jsonUpdateStepIn)
      
      val jsonDeleteStepOut = wC.handleMessage(jsonUpdateStepIn)
      
      Logger.info("<-" + jsonDeleteStepOut)
      
      (jsonDeleteStepOut \ "json").asOpt[String].get === JsonNames.DELETE_FIRST_STEP
      (jsonDeleteStepOut \ "result" \ "status" \ "deleteStep" \ "status").asOpt[String].get === DeleteStepSuccess().status
      (jsonDeleteStepOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    
    
    }
  }
}