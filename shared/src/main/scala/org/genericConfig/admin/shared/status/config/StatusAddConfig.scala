package org.genericConfig.admin.shared.status.config

import org.genericConfig.admin.shared.status.Status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 18.04.2018
 */
sealed abstract class StatusAddConfig extends Status

case class AddConfigAdded() extends StatusAddConfig {
  def status: String = "ADD_CONFIG_ADDED"
  def message: String = ""
}

case class AddConfigAlreadyExist() extends StatusAddConfig {
  def status: String = "ADD_CONFIG_ALREADY_EXIST"
  def message: String = ""
}

case class AddConfigError() extends StatusAddConfig {
  def status: String = "ADD_CONFIG_ERROR"
  def message: String = ""
}
