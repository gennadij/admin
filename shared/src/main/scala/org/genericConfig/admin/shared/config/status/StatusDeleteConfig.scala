package org.genericConfig.admin.shared.config.status

import org.genericConfig.admin.shared.common.status.Error

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 24.04.2018
 */
sealed abstract class StatusDeleteConfig extends Error

case class DeleteConfigSuccess() extends StatusDeleteConfig {
  def status: String = "DELETE_CONFIG_SUCCESS"
  def message: String = ""
}

case class DeleteConfigError() extends StatusDeleteConfig {
  def status: String = "DELETE_CONFIG_ERROR"
  def message: String = ""
}

case class DeleteConfigDefectID() extends StatusDeleteConfig {
  def status: String = "DELETE_CONFIG_DEFECT_ID"
  def message: String = ""
}

case class DeleteConfigIdHashNotExist() extends StatusDeleteConfig {
  def status: String = "DELETE_CONFIG_ID_HASH_NOT_EXIST"
  def message: String = ""
}