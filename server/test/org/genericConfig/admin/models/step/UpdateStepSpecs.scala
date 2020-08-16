package org.genericConfig.admin.models.step

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.models.persistence.orientdb.PropertyKeys
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.step.{SelectionCriterionDTO, StepDTO, StepParamsDTO, StepPropertiesDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 17.05.2018
 */

class UpdateStepSpecs extends Specification
                             with BeforeAfterAll
                             with CommonFunction{

  val wC: WebClient = WebClient.init
  val username : String = "updateStep"
  val configUrlUpdateAll = "updateStep_All"
  var stepIdAll: Option[String] = _
  var uSAllResult : JsResult[StepDTO] = _

  val configUrlOnlyNameToShow = "updateStep_OnlyNameToShow"
  var stepIdOnlyNameToShow: Option[String] = _
  var uSOnlyNameToShowResult : JsResult[StepDTO] = _

  val configUrlOnlySC = "updateStep_OnlySC"
  var stepIdOnlySC: Option[String] = _
  var uSOnlySCResult : JsResult[StepDTO] = _

  val configUrlOnlySCMin = "updateStep_OnlySCMin"
  var stepIdOnlySCMin: Option[String] = _
  var uSOnlySCMinResult : JsResult[StepDTO] = _

  val configUrlOnlySCMax = "updateStep_OnlySCMax"
  var stepIdOnlySCMax: Option[String] = _
  var uSOnlySCMaxResult : JsResult[StepDTO] = _

  val configUrlOnlySCMax_2 = "updateStep_OnlySCMax_2"
  var stepIdOnlySCMax_2 : Option[String] = _
  var uSOnlySCMaxResult_2 : JsResult[StepDTO] = _

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {
    val errorAll = deleteVertex(RidToHash.getRId(stepIdAll.get).get, PropertyKeys.VERTEX_STEP)
    val errorNTS = deleteVertex(RidToHash.getRId(stepIdOnlyNameToShow.get).get, PropertyKeys.VERTEX_STEP)
    val errorSC = deleteVertex(RidToHash.getRId(stepIdOnlySC.get).get, PropertyKeys.VERTEX_STEP)
    val errorSCMIN = deleteVertex(RidToHash.getRId(stepIdOnlySCMin.get).get, PropertyKeys.VERTEX_STEP)
    val errorSCMax = deleteVertex(RidToHash.getRId(stepIdOnlySCMax.get).get, PropertyKeys.VERTEX_STEP)
    Logger.info("Deleted Step error : " + errorAll)
    Logger.info("Deleted Step error : " + errorNTS)
    Logger.info("Deleted Step error : " + errorSC)
    Logger.info("Deleted Step error : " + errorSCMIN)
    Logger.info("Deleted Step error : " + errorSCMax)
  }

  "Der User aendert den Name und Auswahlkriterium bei dem Schritt" >> {
    "User aendert Name und Auswahlkriterium" >> {
      "action = UPDATE_STEP" >> {uSAllResult.get.action === Actions.UPDATE_STEP}
      "stepId < 32 && > 10" >> {uSAllResult.get.result.get.stepId.get.length must be_<=(32) and be_>(10)}
      "Name = FirstStepUpdated" >> {uSAllResult.get.result.get.properties.get.nameToShow.get === "FirstStepUpdated"}
      "MIN = 2" >> {uSAllResult.get.result.get.properties.get.selectionCriterion.get.min.get === 2}
      "MAX = 2" >> {uSAllResult.get.result.get.properties.get.selectionCriterion.get.max.get === 2}
    }
    "User aendert Name" >> {
      "action = UPDATE_STEP" >> {uSOnlyNameToShowResult.get.action === Actions.UPDATE_STEP}
      "stepId < 32 && > 10" >> {uSOnlyNameToShowResult.get.result.get.stepId.get.length must be_<=(32) and be_>(10)}
      "Name = FirstStepUpdated" >> {uSOnlyNameToShowResult.get.result.get.properties.get.nameToShow.get === "FirstStepUpdated"}
      "MIN = 1" >> {uSOnlyNameToShowResult.get.result.get.properties.get.selectionCriterion.get.min.get === 1}
      "MAX = 1" >> {uSOnlyNameToShowResult.get.result.get.properties.get.selectionCriterion.get.max.get === 1}
    }
    "User aendert Name Auswahlkriterium" >> {
      "action = UPDATE_STEP" >> {uSOnlySCResult.get.action === Actions.UPDATE_STEP}
      "stepId < 32 && > 10" >> {uSOnlySCResult.get.result.get.stepId.get.length must be_<=(32) and be_>(10)}
      "Name = FirstStepToUpdateOnlySC" >> {uSOnlySCResult.get.result.get.properties.get.nameToShow.get === "FirstStepToUpdateOnlySC"}
      "MIN = 2" >> {uSOnlySCResult.get.result.get.properties.get.selectionCriterion.get.min.get === 2}
      "MAX = 2" >> {uSOnlySCResult.get.result.get.properties.get.selectionCriterion.get.max.get === 2}
    }
    "User aendert Name Auswahlkriterium nur Min" >> {
      "action = UPDATE_STEP" >> {uSOnlySCMinResult.get.action === Actions.UPDATE_STEP}
      "stepId < 32 && > 10" >> {uSOnlySCMinResult.get.result.get.stepId.get.length must be_<=(32) and be_>(10)}
      "Name = FirstStepToUpdateOnlySCMin" >> {uSOnlySCMinResult.get.result.get.properties.get.nameToShow.get === "FirstStepToUpdateOnlySCMin"}
      "MIN = 2" >> {uSOnlySCMinResult.get.result.get.properties.get.selectionCriterion.get.min.get === 2}
      "MAX = 1" >> {uSOnlySCMinResult.get.result.get.properties.get.selectionCriterion.get.max.get === 1}
    }
    "User aendert Name Auswahlkriterium nur Max" >> {
      "action = UPDATE_STEP" >> {uSOnlySCMaxResult.get.action === Actions.UPDATE_STEP}
      "stepId < 32 && > 10" >> {uSOnlySCMaxResult.get.result.get.stepId.get.length must be_<=(32) and be_>(10)}
      "Name = FirstStepToUpdateOnlySCMax" >> {uSOnlySCMaxResult.get.result.get.properties.get.nameToShow.get === "FirstStepToUpdateOnlySCMax"}
      "MIN = 1" >> {uSOnlySCMaxResult.get.result.get.properties.get.selectionCriterion.get.min.get === 1}
      "MAX = 2" >> {uSOnlySCMaxResult.get.result.get.properties.get.selectionCriterion.get.max.get === 2}
    }
  }

  def before() : Unit = {
    val wC: WebClient = WebClient.init
    val userId = createUser(username, wC)
    createStepForUpdateAll(userId)
    createStepForUpdateOnlyNameToShow(userId)
    createStepForUpdateOnlySC(userId)
    createStepForUpdateOnlySCMin(userId)
    createStepForUpdateOnlySCMax(userId)
    createStepForUpdateOnlySCMax_2(userId)
  }

  def createStepForUpdateAll(userId : String): Unit = {
    val configId = createConfig(userId, s"http://contig/$configUrlUpdateAll")
    val nameToShow: Option[String] = Some("FirstStepToUpdate")
    val kind : Option[String] = Some("first")

    stepIdAll = addStep(nameToShow, Some(configId), kind, 1, 1, wC)

    val updateFirstStepParams: JsValue = Json.toJson(StepDTO(
      action = Actions.UPDATE_STEP,
      params = Some(StepParamsDTO(
        stepId = stepIdAll,
        properties = Some(StepPropertiesDTO(
          nameToShow = Some("FirstStepUpdated"),
          selectionCriterion = Some(SelectionCriterionDTO(
            min = Some(2),
            max = Some(2)
          ))
        ))
      ))
    ))
    Logger.info("UPDATE_STEP_ALL -> " + updateFirstStepParams)
    uSAllResult = Json.fromJson[StepDTO](wC.handleMessage(updateFirstStepParams))
    Logger.info("UPDATE_STEP_ALL <- " + uSAllResult)
  }

  def createStepForUpdateOnlyNameToShow(userId : String): Unit = {
    val configId = createConfig(userId, s"http://contig/$configUrlOnlyNameToShow")
    val nameToShow: Option[String] = Some("FirstStepToUpdate")
    val kind : Option[String] = Some("first")

    stepIdOnlyNameToShow = addStep(nameToShow, Some(configId), kind, 1, 1, wC)

    val updateFirstStepParams: JsValue = Json.toJson(StepDTO(
      action = Actions.UPDATE_STEP,
      params = Some(StepParamsDTO(
        stepId = stepIdOnlyNameToShow,
        properties = Some(StepPropertiesDTO(
          nameToShow = Some("FirstStepUpdated")
        ))
      ))
    ))
    Logger.info("UPDATE_STEP ONLY NAME_TO_SCHOW -> " + updateFirstStepParams)
    uSOnlyNameToShowResult = Json.fromJson[StepDTO](wC.handleMessage(updateFirstStepParams))
    Logger.info("UPDATE_STEP ONLY NAME_TO_SCHOW <- " + Json.toJson[StepDTO](uSOnlyNameToShowResult.get))
  }

  def createStepForUpdateOnlySC(userId : String): Unit = {
    val configId = createConfig(userId, s"http://contig/$configUrlOnlySC")
    val nameToShow: Option[String] = Some("FirstStepToUpdateOnlySC")
    val kind : Option[String] = Some("first")

    stepIdOnlySC = addStep(nameToShow, Some(configId), kind, 1, 1, wC)

    val updateFirstStepParams: JsValue = Json.toJson(StepDTO(
      action = Actions.UPDATE_STEP,
      params = Some(StepParamsDTO(
        stepId = stepIdOnlySC,
        properties = Some(StepPropertiesDTO(
          selectionCriterion = Some(SelectionCriterionDTO(
            min = Some(2),
            max = Some(2)
          ))
        ))
      ))
    ))
    Logger.info("UPDATE_STEP_SC -> " + updateFirstStepParams)
    uSOnlySCResult = Json.fromJson[StepDTO](wC.handleMessage(updateFirstStepParams))
    Logger.info("UPDATE_STEP_SC <- " + Json.toJson[StepDTO](uSOnlySCResult.get))
  }

  def createStepForUpdateOnlySCMin(userId : String): Unit = {
    val configId = createConfig(userId, s"http://contig/$configUrlOnlySCMin")
    val nameToShow: Option[String] = Some("FirstStepToUpdateOnlySCMin")
    val kind : Option[String] = Some("first")

    stepIdOnlySCMin = addStep(nameToShow, Some(configId), kind, 1, 1, wC)

    val updateFirstStepParams: JsValue = Json.toJson(StepDTO(
      action = Actions.UPDATE_STEP,
      params = Some(StepParamsDTO(
        stepId = stepIdOnlySCMin,
        properties = Some(StepPropertiesDTO(
          selectionCriterion = Some(SelectionCriterionDTO(
            min = Some(2)
          ))
        ))
      ))
    ))
    Logger.info("UPDATE_STEP_MIN -> " + updateFirstStepParams)
    uSOnlySCMinResult = Json.fromJson[StepDTO](wC.handleMessage(updateFirstStepParams))
    Logger.info("UPDATE_STEP_MIN <- " + Json.toJson[StepDTO](uSOnlySCMinResult.get))
  }

  def createStepForUpdateOnlySCMax(userId : String): Unit = {
    val configId = createConfig(userId, s"http://contig/$configUrlOnlySCMax")
    val nameToShow: Option[String] = Some("FirstStepToUpdateOnlySCMax")
    val kind : Option[String] = Some("first")

    stepIdOnlySCMax = addStep(nameToShow, Some(configId), kind, 1, 1, wC)

    val updateFirstStepParams: JsValue = Json.toJson(StepDTO(
      action = Actions.UPDATE_STEP,
      params = Some(StepParamsDTO(
        stepId = stepIdOnlySCMax,
        properties = Some(StepPropertiesDTO(
          selectionCriterion = Some(SelectionCriterionDTO(
            max = Some(2)
          ))
        ))
      ))
    ))
    Logger.info("UPDATE_STEP_MAX -> " + updateFirstStepParams)
    uSOnlySCMaxResult = Json.fromJson[StepDTO](wC.handleMessage(updateFirstStepParams))
    Logger.info("UPDATE_STEP_MAX <- " + Json.toJson[StepDTO](uSOnlySCMaxResult.get))
  }

  def createStepForUpdateOnlySCMax_2(userId : String): Unit = {
//    val configId = createConfig(userId, s"http://contig/$configUrlOnlySCMax_2")
//    val nameToShow: Option[String] = Some("FirstStepToUpdateOnlySCMax")
//    val kind : Option[String] = Some("first")

//    stepIdOnlySCMax = addStep(nameToShow, Some(configId), kind, 1, 1, wC)

    val updateFirstStepParams: JsValue = Json.toJson(StepDTO(
      action = Actions.UPDATE_STEP,
      params = Some(StepParamsDTO(
        stepId = stepIdOnlySCMax,
        properties = Some(StepPropertiesDTO(
          selectionCriterion = Some(SelectionCriterionDTO(
            max = Some(3),
            min = Some(4)
          ))
        ))
      ))
    ))
    Logger.info("UPDATE_STEP_MAX -> " + updateFirstStepParams)
    uSOnlySCMaxResult_2 = Json.fromJson[StepDTO](wC.handleMessage(updateFirstStepParams))
    Logger.info("UPDATE_STEP_MAX <- " + Json.toJson[StepDTO](uSOnlySCMaxResult_2.get))
  }
}