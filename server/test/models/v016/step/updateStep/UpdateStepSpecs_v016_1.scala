package models.v016.step.updateStep

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import util.CommonFunction
import org.genericConfig.admin.controllers.websocket.WebClient
import play.api.Logger
import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.step.status.UpdateStepSuccess
import org.genericConfig.admin.shared.common.status.Success

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 17.05.2018
 */
@RunWith(classOf[JUnitRunner])
class UpdateStepSpecs_v016_1 extends Specification 
                             with BeforeAfterAll
                             with CommonFunction{
  
  val wC = WebClient.init
  var userId: String = ""
  var configId: String = ""
  var stepId: String = ""
  val username = "user_updateStep_v016_1"
  
  
  
  def beforeAll() = {
    val (username, userId): (String, String) = addAdminUser(this.username)
    this.userId = userId
    val createConfigOut = createConfig(userId, "//http://contig/" + username, wC)
      
    this.configId = (createConfigOut \ "result" \ "configId").asOpt[String].get
    
    this.stepId = addStep(wC, configId = Some(this.configId)).get
    
    Logger.info("username : " + username)
    Logger.info("userId : " + userId)
    Logger.info("configId : " + configId)
    Logger.info("stepId : " + stepId)
  }
  
  def afterAll() = {
    Logger.info("Deleting Step :" + + deleteStepAppendedToConfig(this.configId))
    Logger.info("Deleting Configs : " + deleteAllConfigs(this.username))
  }
  
  "Diese Spezifikation spezifiziert die Editierung eines Schrittes" >> {
    "AdminUser = user_updateStep_v016_1" >> {
      
      val jsonUpdateStepIn = Json.obj(
            "json" -> JsonNames.UPDATE_STEP,
            "params" -> Json.obj(
                "configId" -> "",
                "componentId" -> "",
                "stepId" -> this.stepId,
                "nameToShow" -> "FirstStep_updated",
                "kind" -> "first",
                "selectionCriterium" -> Json.obj(
                    "min" -> 3,
                    "max" -> 3
                )
            )
        )
      Logger.info("-> " + jsonUpdateStepIn)
      val jsonUpdateStepOut = wC.handleMessage(jsonUpdateStepIn)
      Logger.info("<- " + jsonUpdateStepOut)
      
      (jsonUpdateStepOut \ "json").asOpt[String].get === JsonNames.UPDATE_STEP
      (jsonUpdateStepOut \ "result" \ "status" \ "updateStep" \ "status").asOpt[String].get === UpdateStepSuccess().status
      (jsonUpdateStepOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    }
  }
}