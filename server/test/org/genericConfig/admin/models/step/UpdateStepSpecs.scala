//package org.genericConfig.admin.models.step
//
//import org.genericConfig.admin.controllers.websocket.WebClient
//import org.genericConfig.admin.models.logic.RidToHash
//import org.genericConfig.admin.shared.common.json.JsonNames
//import org.genericConfig.admin.shared.common.status.Success
//import org.genericConfig.admin.shared.config.status.StatusAddConfig
//import org.genericConfig.admin.shared.step.json.{JsonSelectionCriterium, JsonStepIn, JsonStepParams}
//import org.genericConfig.admin.shared.step.status.UpdateStepSuccess
//import org.genericConfig.admin.shared.user.status.{AddUserAlreadyExist, AddUserError, AddUserSuccess}
//import org.junit.runner.RunWith
//import org.specs2.mutable.Specification
//import org.specs2.runner.JUnitRunner
//import org.specs2.specification.BeforeAfterAll
//import play.api.Logger
//import play.api.libs.json.Json
//import util.CommonFunction
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// *
// * Created by Gennadi Heimann 17.05.2018
// */
//@RunWith(classOf[JUnitRunner])
//class UpdateStepSpecs extends Specification
//                             with BeforeAfterAll
//                             with CommonFunction{
//
//  val wC: WebClient = WebClient.init
//  var userId: String = ""
//  var configId: String = ""
//  var stepId: String = ""
//  val usernamePassword = "user_updateStep_v016_1"
//
//
//  def beforeAll(): Unit = {
//    val (username: String, userId: String, status: String) = addUser(usernamePassword)
//
//    status match {
//      case s if AddUserSuccess().status == s =>
//
//        val (configId: String, status: StatusAddConfig) = addConfig(userId, s"http://contig/$username")
//        this.configId = configId
//        this.stepId = addStep(Some(configId), None).get
//      case s if AddUserAlreadyExist().status == s =>
//        val configId = getConfigId(usernamePassword = usernamePassword, configUrl = s"http://contig/$username")
//        this.configId = configId
//        this.stepId = addStep(Some(configId), nameToShow = Some("Step 1"), kind = Some("first")).get
//      case s if AddUserError().status == s =>
//        Logger.info("Fehler bei der Vorbereitung")
//    }
//  }
//
//  def afterAll(): Unit = {
//    val configRid = RidToHash.getRId(this.configId)
//    val count: Int = deleteStepAppendedToConfig(configRid.get)
//    require(count == 1 , "Deleting Step :" + count)
//  }
//
//  "Diese Spezifikation spezifiziert die Editierung eines Schrittes" >> {
//    "AdminUser = user_updateStep_v016_1" >> {
//
//      val jsonUpdateStepIn = Json.toJsObject(JsonStepIn(
//        json = JsonNames.UPDATE_STEP,
//        params = JsonStepParams(
//          stepId = this.stepId,
//          nameToShow = "FirstStep_updated",
//          kind = "first_updated",
//          selectionCriterium = Some(JsonSelectionCriterium(
//            min = 3,
//            max = 3
//          ))
//        )
//      ))
//
//      Logger.info("IN  " + jsonUpdateStepIn)
//      val jsonUpdateStepOut = wC.handleMessage(jsonUpdateStepIn)
//      Logger.info("OUT " + jsonUpdateStepOut)
//
//      (jsonUpdateStepOut \ "json").asOpt[String].get === JsonNames.UPDATE_STEP
//      (jsonUpdateStepOut \ "result" \ "status" \ "updateStep" \ "status").asOpt[String].get === UpdateStepSuccess().status
//      (jsonUpdateStepOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === Success().status
//    }
//  }
//}