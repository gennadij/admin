package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status.{Status, Success}
import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.step.status._
import org.genericConfig.admin.models.wrapper.RidToHash

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2018
 */
object Step{
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param stepBO: StepBO
   *
   * @return StepBO
   */
  def addFirstStep(stepBO: StepBO): StepBO = {
    new Step().addFirstStep(stepBO)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param stepBO: StepBO
   * 
   * @return StepBO
   */
  def deleteFirstStep(stepBO: StepBO): StepBO = {
    new Step().deleteFirstStep(stepBO)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param stepBO: StepBO
   * 
   * @return StepBO
   */
  def updateStep(stepBO: StepBO): StepBO = {
    new Step().updateStep(stepBO)
  }
}

class Step {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param stepBO: StepBO
   * 
   * @return StepBO
   */
  private def addFirstStep(stepBO: StepBO): StepBO = {
  
    RidToHash.getRId(stepBO.configId.get) match {
      case Some(configRid) => 
        val firstStepBO: StepBO = Persistence.addStep(stepBO.copy(configId = Some(configRid)))
        firstStepBO.status.get.addStep match {
          case Some(AddStepSuccess()) => 
              val (appendStepStatus: StatusAppendStep, _) = Persistence.appendStepTo(configRid, firstStepBO.stepId.get)
              appendStepStatus match {
                case AppendStepSuccess() => 
                  firstStepBO.copy(
                      json = Some(JsonNames.ADD_FIRST_STEP),
                      configId = RidToHash.getHash(configRid),
                      stepId = Some(RidToHash.setIdAndHash(firstStepBO.stepId.get)._2),
                      status = Some(StatusStep(
                          addStep = Some(AddStepSuccess()),
                          appendStep = Some(AppendStepSuccess()),
                          common = firstStepBO.status.get.common)))
                case AppendStepError() => 
                  val (statusDeleteStep: StatusDeleteStep, _: Status) = Persistence.deleteStep(firstStepBO.stepId.get)
                  
                  firstStepBO.copy(json = Some(JsonNames.ADD_FIRST_STEP), 
                      status = Some(StatusStep(
                          addStep = Some(AddStepDefectComponentOrConfigId()),
                          deleteStep = Some(statusDeleteStep),
                          appendStep = Some(AppendStepError()),
                          common = firstStepBO.status.get.common)))
              }
          case _ => firstStepBO.copy(json = Some(JsonNames.ADD_FIRST_STEP))
        }
      case None => StepBO(
          json = Some(JsonNames.ADD_FIRST_STEP),
          status = Some(StatusStep(addStep = Some(AddStepIdHashNotExist()))))
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param stepBO: StepBO
   * 
   * @return StepBO
   */
  private def deleteFirstStep(stepBO: StepBO): StepBO = {
    
    val (deleteStepStatus: StatusDeleteStep, commonStatus: Status) = Persistence.deleteStep(stepBO.stepId.get)
    
    deleteStepStatus match {
      case DeleteStepSuccess() => 
        StepBO(
            json = Some(JsonNames.DELETE_FIRST_STEP),
            status = Some(StatusStep(deleteStep = Some(DeleteStepSuccess()), common = Some(Success())))
        )
      case DeleteStepError() => 
        StepBO(
            json = Some(JsonNames.DELETE_FIRST_STEP),
            status = Some(StatusStep(deleteStep = Some(DeleteStepError()), common = Some(commonStatus)))
        )
      case DeleteStepDefectID() => 
        StepBO(
            json = Some(JsonNames.DELETE_FIRST_STEP),
            status = Some(StatusStep(deleteStep = Some(DeleteStepDefectID()), common = Some(commonStatus)))
        )
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param stepBO: StepBO
   * 
   * @return StepBO
   */
  private def updateStep(stepBO: StepBO): StepBO = {
    Persistence.updateStep(stepBO)
  }
}

