//package org.genericConfig.admin.models.step
//
//import org.genericConfig.admin.controllers.websocket.WebClient
//import org.genericConfig.admin.models.logic.RidToHash
//import org.genericConfig.admin.shared.common.json.JsonNames
//import org.genericConfig.admin.shared.common.status.Success
//import org.genericConfig.admin.shared.config.status.StatusAddConfig
//import org.genericConfig.admin.shared.step.json.{JsonStepIn, JsonStepParams}
//import org.genericConfig.admin.shared.step.status.AppendStepSuccess
//import org.genericConfig.admin.shared.user.status.{AddUserAlreadyExist, AddUserError, AddUserSuccess}
//import org.specs2.mutable.Specification
//import org.specs2.specification.BeforeAfterAll
//import play.api.Logger
//import play.api.libs.json.{JsValue, Json}
//import util.CommonFunction
//
///**
//  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
//  *
//  * Created by Gennadi Heimann 26.06.2018
//  */
//class ConnectAddedStepToComponentSpecs extends Specification
//  with BeforeAfterAll
//  with CommonFunction{
//
//  val usernamePassword = "user_v016_13"
//  var userId = ""
//  var stepId_1 = ""
//  var stepId_2 = ""
//  var stepId_3 = ""
//  var componentId_1 = ""
//  var componentId_2 = ""
//  var componentId_3 = ""
//  val wC: WebClient = WebClient.init
//
//  def beforeAll() : Unit = {
//    val (username: String, userId: String, status: String) = addUser(this.usernamePassword)
//
//    status match {
//      case s if AddUserSuccess().status == s =>
//
//        val (configId: String, _: StatusAddConfig) = addConfig(userId, s"http://contig/$username")
//        this.stepId_1 = addStep(Some(configId), nameToShow = Some("Step 1"), kind = Some("first")).get
//        this.componentId_1 = addComponentToStep(this.stepId_1, "Component 1", "immutable")._1
//        this.componentId_2 = addComponentToStep(this.stepId_1, "Component 2", "immutable")._1
//        this.componentId_3 = addComponentToStep(this.stepId_1, "Component 3", "immutable")._1
//        this.stepId_2 = addStep(Some(componentId_1), nameToShow = Some("Step 2"), kind = Some("default")).get
//        this.stepId_3 = addStep(Some(componentId_2), nameToShow = Some("Step 3"), kind = Some("default")).get
//      case s if AddUserAlreadyExist().status == s =>
//        val configId = getConfigId(usernamePassword = this.usernamePassword, configUrl = s"http://contig/$username")
//        val configTreeBO = getConfigTree(configId)
//
//        this.stepId_1 = configTreeBO.configTree.get.stepId
//
//        this.componentId_1 = addComponentToStep(this.stepId_1, "Component 1", "immutable")._1
//        this.componentId_2 = addComponentToStep(this.stepId_1, "Component 2", "immutable")._1
//        this.componentId_3 = addComponentToStep(this.stepId_1, "Component 3", "immutable")._1
//        this.stepId_2 = addStep(Some(componentId_1), nameToShow = Some("Step 2"), kind = Some("default")).get
//        this.stepId_3 = addStep(Some(componentId_2), nameToShow = Some("Step 3"), kind = Some("default")).get
//
//      case s if AddUserError().status == s =>
//        Logger.info("Fehler bei der Vorbereitung")
//    }
//  }
//
//  def afterAll(): Unit = {
//    val count = deleteComponents(this.stepId_1)
//    require(count == 1, "deleted components " + count)
//
//  }
//
//  "" >> {
//    "" >> {
//
//      val jsonConnectComponentToStepIn = Json.toJson(JsonStepIn(
//        json = JsonNames.CONNECT_COMPONENT_TO_STEP,
//        params = JsonStepParams(
//          appendToId = this.componentId_3,
//          stepId = this.stepId_3
//        )
//      ))
//
//      Logger.info("IN " + jsonConnectComponentToStepIn)
//
//      val jsonConnectComponentToStepOut: JsValue = wC.handleMessage(jsonConnectComponentToStepIn)
//
//      Logger.info("OUT " + jsonConnectComponentToStepOut)
//
//      (jsonConnectComponentToStepOut \ "json").asOpt[String] === Some(JsonNames.CONNECT_COMPONENT_TO_STEP)
////      (jsonAddComponentOut \ "result" \ "componentId").asOpt[String].get.length must be_<=(32)
////      (jsonAddComponentOut \ "result" \ "nameToShow").asOpt[String] === Some("Component 1 updated")
////      (jsonAddComponentOut \ "result" \ "kind").asOpt[String] === Some("immutable updated")
//      (jsonConnectComponentToStepOut \ "result" \ "status" \ "appendStep" \ "status").asOpt[String] ===
//        Some(AppendStepSuccess().status)
//      (jsonConnectComponentToStepOut \ "result" \ "status" \ "common" \ "status").asOpt[String] === Some(Success().status)
//
//
//    }
//  }
//
//}
