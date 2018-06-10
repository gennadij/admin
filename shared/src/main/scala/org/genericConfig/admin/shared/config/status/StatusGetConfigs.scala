package org.genericConfig.admin.shared.config.status

import org.genericConfig.admin.shared.common.status.Status


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 24.04.2018
 */
sealed abstract class StatusGetConfigs extends Status

case class GetConfigsSuccess() extends StatusGetConfigs {
  def status: String = "GET_CONFIGS_SUCCESS"
  def message: String = ""
}

case class GetConfigsEmpty() extends StatusGetConfigs {
  def status: String = "GET_CONFIGS_EMPTY"
  def message: String = ""
}

case class GetConfigsError() extends StatusGetConfigs {
  def status: String = "GET_CONFIGS_ERROR"
  def message: String = ""
}

case class GetConfigsIdHashNotExist() extends StatusGetConfigs {
  def status: String = "GET_CONFIGS_ID_HASH_NOT_EXIST"
  def message: String = ""
}

