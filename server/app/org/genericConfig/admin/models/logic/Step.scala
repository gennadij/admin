package org.genericConfig.admin.models.logic

import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.step.json.JsonStepOut
import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.step.status._
import org.genericConfig.admin.shared.common.status.Status

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
    
    firstStepBO.status.get.addStep match {
      case Some(CreateStepSuccess()) => 
        val (appendStepStatus: StatusAppendStep, commonStatus: Status) = 
          Persistence.appendStepTo(firstStepBO.configId.get, firstStepBO.stepId.get)
          appendStepStatus match {
            case AppendStepSuccess() => 
              firstStepBO.copy(status = Some(StatusStep(appendStep = Some(AppendStepSuccess()))))
            case AppendStepError() => 
              val (deleteStepStatus: StatusDeleteStep, commonStatus: Status) = 
                Persistence.deleteStep(firstStepBO.stepId.get)
              
              firstStepBO.copy(status = Some(StatusStep(appendStep = Some(AppendStepError()))))
          }
      case _ => firstStepBO
    }
  }
}

