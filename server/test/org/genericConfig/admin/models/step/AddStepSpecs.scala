package org.genericConfig.admin.models.step

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.common.{IdHashNotExistError, StepAlreadyExistError}
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.step.{SelectionCriterionDTO, StepDTO, StepParamsDTO, StepPropertiesDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 16.01.2017
 *
 * Changes in
 * @version 0.1.0
 * @version 0.1.3
 *
 * Username = user4
 */

class AddStepSpecs extends Specification
                          with BeforeAfterAll
                          with CommonFunction{


  var addFirstStep1Result : JsResult[StepDTO] = _
  var addFirstStep2Result : JsResult[StepDTO] = _
  var addFirstStep3Result : JsResult[StepDTO] = _
  var configId : String = _

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {
    val count = deleteStepAppendedToConfig(RidToHash.getRId(configId).get)
    require(count == 1, "Anzahl der geloeschten Steps " + count)
  }


  "Der Benutzer erstellt einen ersten neuen Schritt" >> {
    "Erster Schritt " >> {
      "action = ADD_STEP" >> {addFirstStep1Result.asOpt.get.action === Actions.ADD_STEP}
      "result.stepId < 32 " >> {addFirstStep1Result.asOpt.get.result.get.stepId.get.length must (be_<=(32) and be_>(10))}
      "result.errors = " >> {addFirstStep1Result.asOpt.get.result.get.errors must beNone}
    }
    "Zeiter Schritt in der selben Konfiguration" >> {
      "action = ADD_STEP" >> {addFirstStep2Result.asOpt.get.action === Actions.ADD_STEP}
      "result.stepId = " >> {addFirstStep2Result.asOpt.get.result.get.stepId must beNone}
      "result.errors = " >> {addFirstStep2Result.asOpt.get.result.get.errors.get.head.name === StepAlreadyExistError().name}
    }
    "Dritter Schritt mit defekten ID für Konfiguration" >> {
      "action = ADD_STEP" >> {addFirstStep3Result.asOpt.get.action === Actions.ADD_STEP}
      "result.stepId = " >> {addFirstStep3Result.asOpt.get.result.get.stepId must beNone}
      "result.errors = " >> {addFirstStep3Result.asOpt.get.result.get.errors.get.head.name === IdHashNotExistError().name}
    }
  }

  def before(): Unit = {
    val wC: WebClient = WebClient.init
    val username = "user4"
    val userId = createUser(username, wC)
    configId = createConfig(userId, s"http://contig/$username")
    val nameToShow: Option[String] = Some("FirstStep")
    val kind : Option[String] = Some("first")

    val addFirstStep1Params: JsValue = Json.toJson(StepDTO(
      action = Actions.ADD_STEP,
      params = Some(StepParamsDTO(
        outId = Some(configId),
        kind = kind,
          properties = Some(StepPropertiesDTO(
          nameToShow = nameToShow,
          selectionCriterion = Some(SelectionCriterionDTO(
            min = Some(1),
            max = Some(1)
          ))
        ))
      )),
      result = None
    ))
    Logger.info("ADD_STEP -> " + addFirstStep1Params)
    addFirstStep1Result = Json.fromJson[StepDTO](wC.handleMessage(addFirstStep1Params))
    Logger.info("ADD_STEP <- " + addFirstStep1Result)

    val addFirstStep2Params: JsValue = Json.toJson(StepDTO(
      action = Actions.ADD_STEP,
      params = Some(StepParamsDTO(
        outId = Some(configId),
        kind = kind,
          properties = Some(StepPropertiesDTO(
          nameToShow = nameToShow,
          selectionCriterion = Some(SelectionCriterionDTO(
            min = Some(1),
            max = Some(1)
          ))
        ))
      )),
      result = None
    ))
    Logger.info("ADD_STEP -> " + addFirstStep2Params)
    addFirstStep2Result = Json.fromJson[StepDTO](wC.handleMessage(addFirstStep2Params))
    Logger.info("ADD_STEP <- " + addFirstStep2Result)

    val addFirstStep3Params: JsValue = Json.toJson(StepDTO(
      action = Actions.ADD_STEP,
      params = Some(StepParamsDTO(
        outId = Some("#1:1"),
        kind = kind,
          properties = Some(StepPropertiesDTO(
          nameToShow = nameToShow,
          selectionCriterion = Some(SelectionCriterionDTO(
            min = Some(1),
            max = Some(1)
          ))
        ))
      )),
      result = None
    ))
    Logger.info("ADD_STEP -> " + addFirstStep3Params)
    addFirstStep3Result = Json.fromJson[StepDTO](wC.handleMessage(addFirstStep3Params))
    Logger.info("ADD_STEP <- " + addFirstStep3Result)
  }
}