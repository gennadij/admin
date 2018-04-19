package org.genericConfig.admin.shared.status.config

import org.genericConfig.admin.shared.status.Status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 18.04.2018
 */
sealed abstract class StatusAddConfig extends Status

case class ConfigAdded() extends StatusAddConfig {
  def status: String = "CONFIG_ADDED"
  def message: String = ""
}

case class ConfigAlredyExist() extends StatusAddConfig {
  def status: String = "CONFIG_ALREDY_EXIST"
  def message: String = ""
}

//case class ConfigExist() extends StatusAddConfig {
//  def status: String = "CONFIG_Exist"
//  def message: String = ""
//}
