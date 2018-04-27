package org.genericConfig.admin.shared.login.status

import org.genericConfig.admin.shared.common.status.Status


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.04.2018
 */
sealed abstract class StatusUserLogin extends Status


case class UserExist() extends StatusUserLogin {
  def status: String = "USER_EXIST"
  def message: String = ""
}

case class UserPasswordWrong() extends StatusUserLogin {
  def status: String = "USER_PASSWORD_WRONG"
  def message: String = ""
}

case class UserNotExist() extends StatusUserLogin {
  def status: String = "USER_NOT_EXIST"
  def message: String = ""
}

case class UserConfigsError() extends StatusUserLogin {
  def status: String = "USER_CONFIG_ERROR"
  def message: String = ""
}
