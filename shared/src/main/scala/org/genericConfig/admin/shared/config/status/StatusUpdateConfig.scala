package org.genericConfig.admin.shared.config.status

import org.genericConfig.admin.shared.common.status.Status


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 24.04.2018
 */
sealed abstract  class StatusUpdateConfig extends Status

case class UpdateConfigUpdated() extends StatusUpdateConfig {
  def status: String = "UPDATE_CONFIG_UPDATED"
  def message: String = ""
}

case class UpdateConfigError() extends StatusUpdateConfig {
  def status: String = "UPDATE_CONFIG_ERROR"
  def message: String = ""
}

//case class UpdateConfigDefectID() extends StatusUpdateConfig {
//  def status: String = "DELETE_CONFIG_DEFECT_ID"
//  def message: String = ""
//}