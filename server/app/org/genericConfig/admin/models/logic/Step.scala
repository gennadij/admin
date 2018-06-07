package org.genericConfig.admin.models.logic

import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.step.status._
import org.genericConfig.admin.shared.common.status.Status
import org.genericConfig.admin.shared.common.json.JsonNames
import play.api.Logger
import org.genericConfig.admin.shared.common.status.Success

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
    val firstStepBO: StepBO = Persistence.addStep(stepBO)
    Logger.info("firstStepBO " + firstStepBO)
    firstStepBO.status.get.addStep match {
      case Some(AddStepSuccess()) => 
        val (appendStepStatus: StatusAppendStep, commonStatus: Status) = 
          Persistence.appendStepTo(firstStepBO.configId.get, firstStepBO.stepId.get)
          appendStepStatus match {
            case AppendStepSuccess() => 
              firstStepBO.copy(json = Some(JsonNames.ADD_FIRST_STEP), 
                  status = Some(StatusStep(
                      addStep = firstStepBO.status.get.addStep,
                      appendStep = Some(AppendStepSuccess()),
                      common = firstStepBO.status.get.common)))
            case AppendStepError() => 
              val (deleteStepStatus: StatusDeleteStep, commonStatus: Status) = 
                Persistence.deleteStep(firstStepBO.stepId.get)
              
              firstStepBO.copy(json = Some(JsonNames.ADD_FIRST_STEP), 
                  status = Some(StatusStep(
                      addStep = firstStepBO.status.get.addStep,
                      appendStep = Some(AppendStepError()),
                      common = firstStepBO.status.get.common)))
          }
      case _ => firstStepBO.copy(json = Some(JsonNames.ADD_FIRST_STEP))
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

