package org.genericConfig.admin.shared.config.status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 18.04.2018
 */
sealed abstract class StatusAddConfig //extends Error

case class AddConfigSuccess() extends StatusAddConfig {
  def status: String = "ADD_CONFIG_ADDED"
  def message: String = ""
  def code :String = ""
}

case class AddConfigAlreadyExist() extends StatusAddConfig {
  def status: String = "ADD_CONFIG_ALREADY_EXIST"
  def message: String = ""
  def code :String = ""
}

case class AddConfigError() extends StatusAddConfig {
  def status: String = "ADD_CONFIG_ERROR"
  def message: String = ""
  def code :String = ""
}

