package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.common.json.{JsonNames, JsonStatus}
import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.step.json.{JsonStepIn, JsonStepOut, JsonStepResult, JsonStepStatus}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 08.06.2018
  */
class WrapperStep {

  /**
    * @author Gennadi Heimann
    *
    * @version 0.1.5
    *
    * @param jsonStepIn: JsonStepCS
    *
    * @return StepCS
    */
  private[wrapper] def toStepBO(jsonStepIn: JsonStepIn): StepBO = {

    jsonStepIn.json match {
      case json if json == JsonNames.ADD_FIRST_STEP =>
        val configRid = RidToHash.getId(jsonStepIn.params.configId) match {
          case Some(id) => id
          case None => "-1"
        }
        StepBO(
          Some(jsonStepIn.json),
          Some(configRid), //configId
          None,//componentId
          Some(jsonStepIn.params.nameToShow), // nameToShow
          Some(jsonStepIn.params.kind), // kind
          Some(jsonStepIn.params.selectionCriterium.get.min), // selectionCriteriumMin
          Some(jsonStepIn.params.selectionCriterium.get.max), // selectionCriteriumMax
          None, // stepId
          None // status
        )
      case json if json == JsonNames.DELETE_FIRST_STEP || json == JsonNames.DELETE_STEP =>

        val stepRId = RidToHash.getId(jsonStepIn.params.stepId)

        StepBO(
          Some(jsonStepIn.json),
          None, //configId
          None,//componentId
          None, // nameToShow
          None, // kind
          None, // selectionCriteriumMin
          None, // selectionCriteriumMax
          Some(stepRId.get), // stepId
          None // status
        )
      case json if json == JsonNames.UPDATE_FIRST_STEP || json == JsonNames.UPDATE_STEP  =>

        val stepRId = RidToHash.getId(jsonStepIn.params.stepId)

        StepBO(
          Some(jsonStepIn.json),
          None, //configId
          None,//componentId
          Some(jsonStepIn.params.nameToShow), // nameToShow
          Some(jsonStepIn.params.kind), // kind
          Some(jsonStepIn.params.selectionCriterium.get.min), // selectionCriteriumMin
          Some(jsonStepIn.params.selectionCriterium.get.max), // selectionCriteriumMax
          Some(stepRId.get), // stepId
          None // status
        )
      case json if json == JsonNames.ADD_STEP =>

        val componentRId = RidToHash.getId(jsonStepIn.params.componentId)
        StepBO(
          Some(jsonStepIn.json),
          None, //configId
          Some(componentRId.get),//componentId
          Some(jsonStepIn.params.nameToShow), // nameToShow
          Some(jsonStepIn.params.kind), // kind
          Some(jsonStepIn.params.selectionCriterium.get.min), // selectionCriteriumMin
          Some(jsonStepIn.params.selectionCriterium.get.max), // selectionCriteriumMax
          None, // stepId
          None // status
        )
    }
  }

  /**
    * @author Gennadi Heimann
    *
    * @version 0.1.5
    *
    * @param stepBO: StepBO
    *
    * @return JsonStepOut
    */
  def toJsonStepOut(stepBO: StepBO): JsonStepOut = {
    stepBO.json.get match {
      case json if json == JsonNames.ADD_FIRST_STEP =>
        stepBO.stepId match {
          case Some(id) => RidToHash.setIdAndHash(id)
          case None => "-1"
        }

        createJsonStepOut(stepBO, json)
      case json if json == JsonNames.DELETE_FIRST_STEP =>
        createJsonStepOut(stepBO, json)
//      case json if json == JsonNames.UPDATE_FIRST_STEP =>
//        createJsonStepOut(stepBO, json)
      case json if json == JsonNames.ADD_STEP =>
        ???
//        createJsonStepOut(stepBO, json)
      case json if json == JsonNames.DELETE_STEP =>
        ???
//        createJsonStepOut(stepBO, json)
      case json if json == JsonNames.UPDATE_STEP =>
        createJsonStepOut(stepBO, json)
    }
  }

  /**
    * @author Gennadi Heimann
    *
    * @version 0.1.5
    *
    * @param stepBO: StepBO, json: String
    *
    * @return JsonStepOut
    */
  private def createJsonStepOut(stepBO: StepBO, json: String): JsonStepOut = {

    JsonStepOut(
      json = json,
      result = JsonStepResult(
        stepBO.stepId match {
          case Some(stepId) => Some(RidToHash.getHash(stepId).get)
          case None => None
        },
        Set(),
        Set(),
        JsonStepStatus(
          stepBO.status.get.addStep match {
            case Some(addStep) =>
              Some(JsonStatus(
                addStep.status,
                addStep.message
              ))
            case None => None
          },
          stepBO.status.get.deleteStep match {
            case Some(deleteStep) =>
              Some(JsonStatus(
                deleteStep.status,
                deleteStep.message
              ))
            case None => None
          },
          stepBO.status.get.updateStep match {
            case Some(updateStep) =>
              Some(JsonStatus(
                updateStep.status,
                updateStep.message
              ))
            case None => None
          },
          stepBO.status.get.appendStep match {
            case Some(appendStep) =>
              Some(JsonStatus(
                appendStep.status,
                appendStep.message
              ))
            case None => None
          },
          stepBO.status.get.common match {
            case Some(common) =>
              Some(JsonStatus(
                common.status,
                common.message
              ))
            case None => None
          }
        )
      )
    )
  }
}
