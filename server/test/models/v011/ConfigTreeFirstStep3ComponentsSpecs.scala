package models.v011

import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import models.preparingConfigs.PrepareConfigsForSpecsv011
import play.api.Logger
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.configTree.status.GetConfigTreeSuccess

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 16.01.2017
 * 
 * Username = user7
 */

@RunWith(classOf[JUnitRunner])
class ConfigTreeFirstStep3ComponentsSpecs extends Specification with BeforeAfterAll{
  
  val wC = WebClient.init
  
  def beforeAll() = {
    PrepareConfigsForSpecsv011.prepareConfigTreeFirstStep3Components(wC)
  }
  
  def afterAll() = {}
  "Specification spezifiziert die Erzeugung von der ConfigTree" >> {
    val loginCS = Json.obj(
        "json" -> JsonNames.GET_USER
        ,"params" -> Json.obj(
            "username" -> "user7",
            "password"-> "user7"
        )
    )
    
    val loginSC: JsValue = wC.handleMessage(loginCS)
    val configId = ((loginSC \ "result" \ "configs")(0) \ "configId").asOpt[String].get
    
    (loginSC \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
    (loginSC \ "result" \ "configs").asOpt[List[JsValue]].get.size === 1
      
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
    ((configTreeOut \ "result" \ "step") \ "kind").asOpt[String].get === "first"
    (((configTreeOut \ "result" \ "step") \ "components")).asOpt[Set[JsValue]].get.size === 3
    ((((configTreeOut \ "result" \ "step") \ "components")(0)) \ "kind").asOpt[String].get === "immutable"
    ((((configTreeOut \ "result" \ "step") \ "components")(0)) \ "nextStep" \ "kind").asOpt[String].get === "default"
    ((((configTreeOut \ "result" \ "step") \ "components")(0)) \ "nextStep" \ "components").asOpt[Set[JsValue]].get.size === 0
    ((((configTreeOut \ "result" \ "step") \ "components")(1)) \ "kind").asOpt[String].get === "immutable"
    ((((configTreeOut \ "result" \ "step") \ "components")(1)) \ "nextStepId").asOpt[String].get === "last"
    ((((configTreeOut \ "result" \ "step") \ "components")(1)) \ "nextStep").asOpt[String] === None
    ((((configTreeOut \ "result" \ "step") \ "components")(2)) \ "kind").asOpt[String].get === "immutable"
    ((((configTreeOut \ "result" \ "step") \ "components")(2)) \ "nextStepId").asOpt[String].get === "last"
    ((((configTreeOut \ "result" \ "step") \ "components")(2)) \ "nextStep").asOpt[String] === None
    (configTreeOut \ "result" \ "status" \ "getConfigTree" \ "status" ).asOpt[String].get === GetConfigTreeSuccess().status
    (configTreeOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
  }
}