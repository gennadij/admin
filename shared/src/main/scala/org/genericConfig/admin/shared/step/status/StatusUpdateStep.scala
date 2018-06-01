package org.genericConfig.admin.shared.step.status

import org.genericConfig.admin.shared.common.status.Status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2018
 */
sealed abstract class StatusUpdateStep extends Status

case class UpdateStepSuccess() extends StatusUpdateStep {
  def status: String = "UPDATE_STEP_SUCCESS"
  def message: String = ""
}

case class UpdateStepError() extends StatusUpdateStep {
  def status: String = "UPDATE_STEP_ERROR"
  def message: String = ""
}