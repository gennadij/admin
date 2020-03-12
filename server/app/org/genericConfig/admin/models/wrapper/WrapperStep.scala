package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.step.json.{JsonStepIn, JsonStepOut}

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
      case json if json == JsonNames.ADD_STEP =>
        StepBO(
          Some(jsonStepIn.json),
          Some(jsonStepIn.params.appendToId),
          Some(jsonStepIn.params.nameToShow), // nameToShow
          Some(jsonStepIn.params.kind), // kind
          Some(jsonStepIn.params.selectionCriterium.get.min), // selectionCriteriumMin
          Some(jsonStepIn.params.selectionCriterium.get.max), // selectionCriteriumMax
          None, // stepId
          None // status
        )
      case json if json == JsonNames.DELETE_STEP =>

        StepBO(
          Some(jsonStepIn.json),
          None,
          None, // nameToShow
          None, // kind
          None, // selectionCriteriumMin
          None, // selectionCriteriumMax
          Some(jsonStepIn.params.stepId), // stepId
          None // status
        )
      case json if json == JsonNames.UPDATE_STEP  =>

        StepBO(
          Some(jsonStepIn.json),
          None,
          Some(jsonStepIn.params.nameToShow), // nameToShow
          Some(jsonStepIn.params.kind), // kind
          Some(jsonStepIn.params.selectionCriterium.get.min), // selectionCriteriumMin
          Some(jsonStepIn.params.selectionCriterium.get.max), // selectionCriteriumMax
          Some(jsonStepIn.params.stepId), // stepId
          None // status
        )
      case json if json == JsonNames.CONNECT_COMPONENT_TO_STEP  =>
        StepBO(
          json = Some(jsonStepIn.json),
          appendToId = Some(jsonStepIn.params.appendToId),
          stepId = Some(jsonStepIn.params.stepId)
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
  private[wrapper] def toJsonStepOut(stepBO: StepBO): JsonStepOut = {
    stepBO.json.get match {
      case json if json == JsonNames.ADD_STEP =>
        createJsonStepOut(stepBO, json)
      case json if json == JsonNames.DELETE_STEP =>
        createJsonStepOut(stepBO, json)
      case json if json == JsonNames.UPDATE_STEP =>
        createJsonStepOut(stepBO, json)
      case json if json == JsonNames.CONNECT_COMPONENT_TO_STEP=>
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
    ???

//    JsonStepOut(
//      json = json,
//      result = JsonStepResult(
//        stepBO.stepId,
//        Set(),
//        Set(),
//        JsonStepStatus(
//          stepBO.status.get.addStep match {
//            case Some(addStep) =>
//              Some(JsonStatus(
//                addStep.status,
//                addStep.message
//              ))
//            case None => None
//          },
//          stepBO.status.get.deleteStep match {
//            case Some(deleteStep) =>
//              Some(JsonStatus(
//                deleteStep.status,
//                deleteStep.message
//              ))
//            case None => None
//          },
//          stepBO.status.get.updateStep match {
//            case Some(updateStep) =>
//              Some(JsonStatus(
//                updateStep.status,
//                updateStep.message
//              ))
//            case None => None
//          },
//          stepBO.status.get.appendStep match {
//            case Some(appendStep) =>
//              Some(JsonStatus(
//                appendStep.status,
//                appendStep.message
//              ))
//            case None => None
//          },
//          stepBO.status.get.common match {
//            case Some(common) =>
//              Some(JsonStatus(
//                common.status,
//                common.message
//              ))
//            case None => None
//          }
//        )
//      )
//    )
  }
}
