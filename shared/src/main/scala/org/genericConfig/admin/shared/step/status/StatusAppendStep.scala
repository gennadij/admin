package org.genericConfig.admin.shared.step.status

import org.genericConfig.admin.shared.common.status.Error

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 15.05.2018
 */
sealed abstract class StatusAppendStep extends Error

case class AppendStepSuccess() extends StatusAppendStep {
  def status: String = "APPEND_STEP_SUCCESS"
  def message: String = ""
}

case class AppendStepError() extends StatusAppendStep {
  def status: String = "APPEND_STEP_ERROR"
  def message: String = ""
}