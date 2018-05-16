package org.genericConfig.admin.models.logic

import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.step.json.JsonStepOut
import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.step.status._
import org.genericConfig.admin.shared.common.status.Status
import org.genericConfig.admin.shared.common.json.JsonNames
import play.api.Logger

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
   * @param 
   * 
   * @return 
   */
  def addFirstStep(stepBO: StepBO): StepBO = {
    new Step().addFirstStep(stepBO)
  }
}

class Step {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
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
}

