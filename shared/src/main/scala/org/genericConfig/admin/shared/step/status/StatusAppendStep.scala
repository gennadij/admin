package org.genericConfig.admin.shared.step.status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 15.05.2018
 */
sealed abstract class StatusAppendStep extends Error

case class AppendStepSuccess() extends StatusAppendStep {
  def status: String = "APPEND_STEP_SUCCESS"
  def message: String = ""
  def code :String = ""
}

