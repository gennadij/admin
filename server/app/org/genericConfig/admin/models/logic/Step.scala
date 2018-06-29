package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.{Status, Success}
import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.step.status._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 14.05.2018
  */
object Step {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  def addStep(stepBO: StepBO): StepBO = {
    new Step().addStep(stepBO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  def deleteFirstStep(stepBO: StepBO): StepBO = {
    new Step().deleteStep(stepBO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  def updateStep(stepBO: StepBO): StepBO = {
    new Step().updateStep(stepBO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  def connectComponentToStep(stepBO: StepBO): StepBO = {
    new Step().connectComponentToStep(stepBO)
  }

}

class Step {
  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  private def addStep(stepBO: StepBO): StepBO = {

    RidToHash.getRId(stepBO.appendToId.get) match {
      case Some(appendToRid) =>
        val stepBOOut: StepBO = Persistence.addStep(stepBO.copy(appendToId= Some(appendToRid)))
        stepBOOut.status.get.addStep match {
          case Some(AddStepSuccess()) =>
            val (appendStepStatus: StatusAppendStep, _) = Persistence.appendStepTo(appendToRid, stepBOOut.stepId.get)
            appendStepStatus match {
              case AppendStepSuccess() =>
                val s = stepBOOut.copy(
                  json = Some(JsonNames.ADD_STEP),
                  appendToId = RidToHash.getHash(appendToRid),
                  stepId = Some(RidToHash.setIdAndHash(stepBOOut.stepId.get)._2),
                  status = Some(StatusStep(
                    addStep = Some(AddStepSuccess()),
                    appendStep = Some(AppendStepSuccess()),
                    common = stepBOOut.status.get.common)))
                s
              case AppendStepError() =>
                val (statusDeleteStep: StatusDeleteStep, _) = Persistence.deleteStep(stepBOOut.stepId.get)

                stepBOOut.copy(json = Some(JsonNames.ADD_STEP),
                  status = Some(StatusStep(
                    addStep = Some(AddStepDefectComponentOrConfigId()),
                    deleteStep = Some(statusDeleteStep),
                    appendStep = Some(AppendStepError()),
                    common = stepBOOut.status.get.common)))
            }
          case _ => stepBOOut.copy(json = Some(JsonNames.ADD_STEP))
        }
      case None => StepBO(
        json = Some(JsonNames.ADD_STEP),
        status = Some(StatusStep(addStep = Some(AddStepIdHashNotExist()))))
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  private def deleteStep(stepBO: StepBO): StepBO = {

    val stepBOIn = stepBO.copy(stepId = RidToHash.getRId(stepBO.stepId.get))

    val (deleteStepStatus: StatusDeleteStep, commonStatus: Status) = Persistence.deleteStep(stepBOIn.stepId.get)

    deleteStepStatus match {
      case DeleteStepSuccess() =>
        StepBO(
          json = Some(JsonNames.DELETE_STEP),
          status = Some(StatusStep(deleteStep = Some(DeleteStepSuccess()), common = Some(Success())))
        )
      case DeleteStepError() =>
        StepBO(
          json = Some(JsonNames.DELETE_STEP),
          status = Some(StatusStep(deleteStep = Some(DeleteStepError()), common = Some(commonStatus)))
        )
      case DeleteStepDefectID() =>
        StepBO(
          json = Some(JsonNames.DELETE_STEP),
          status = Some(StatusStep(deleteStep = Some(DeleteStepDefectID()), common = Some(commonStatus)))
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  private def updateStep(stepBO: StepBO): StepBO = {
    val stepRId = RidToHash.getRId(stepBO.stepId.get)

    Persistence.updateStep(stepBO.copy(stepId = stepRId))
  }

  /**
    * @author Gennadi Heimann
    *
    * @version 0.1.0
    *
    * @param stepBO: StepBO
    *
    * @return ComponentBO
    */
  def connectComponentToStep(stepBO: StepBO): StepBO = {
    val componentRid = RidToHash.getRId(stepBO.appendToId.get)
    val stepRid = RidToHash.getRId(stepBO.stepId.get)

    val (statusAppendStep, statusCommon): (StatusAppendStep, Status) =
      Persistence.appendStepTo(id = componentRid.get, stepId = stepRid.get)

    statusAppendStep match {
      case AppendStepSuccess() => StepBO(
        json = Some(JsonNames.CONNECT_COMPONENT_TO_STEP),
        status = Some(StatusStep(
          appendStep = Some(AppendStepSuccess()),
          common = Some(statusCommon)
        ))
      )
      case AppendStepError() => StepBO(
        json = Some(JsonNames.CONNECT_COMPONENT_TO_STEP),
        status = Some(StatusStep(
          appendStep = Some(AppendStepError()),
          common = Some(statusCommon)
        ))
      )
    }
  }
}

