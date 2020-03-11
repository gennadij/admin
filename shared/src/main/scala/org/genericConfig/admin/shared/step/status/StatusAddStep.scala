package org.genericConfig.admin.shared.step.status

import org.genericConfig.admin.shared.common.error.Error

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2018
 */
sealed abstract class StatusAddStep extends Error

case class AddStepSuccess() extends StatusAddStep {
  def status: String = "ADD_STEP_SUCCESS"
  def message: String = ""
  def code :String = ""
}

case class AddStepError() extends StatusAddStep {
  def status: String = "ADD_STEP_ERROR"
  def message: String = ""
  def code :String = ""
}

case class AddStepAlreadyExist() extends StatusAddStep {
  def status: String = "ADD_STEP_ALREADY_EXIST"
  def message: String = ""
  def code :String = ""
}

case class AddStepDefectComponentOrConfigId() extends StatusAddStep {
  def status: String = "ADD_STEP_DEFECT_COMPONENT_OR_CONFIG_ID"
  def message: String = ""
  def code :String = ""
}

case class AddStepIdHashNotExist() extends StatusAddStep {
  def status: String = "ADD_STEP_ID_HASH_NOT_EXIST"
  def message: String = ""
  def code :String = ""
}

