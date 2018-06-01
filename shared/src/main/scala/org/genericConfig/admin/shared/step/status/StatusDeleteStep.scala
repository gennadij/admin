package org.genericConfig.admin.shared.step.status

import org.genericConfig.admin.shared.common.status.Status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2018
 */
sealed abstract class StatusDeleteStep extends Status

case class DeleteStepSuccess() extends StatusDeleteStep {
  def status: String = "DELETE_STEP_SUCCESS"
  def message: String = ""
}

case class DeleteStepError() extends StatusDeleteStep {
  def status: String = "Delete_STEP_ERROR"
  def message: String = ""
}

case class DeleteStepDefectID() extends StatusDeleteStep {
  def status: String = "DELETE_CONFIG_DEFECT_ID"
  def message: String = ""
}