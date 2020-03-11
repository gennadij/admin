package org.genericConfig.admin.shared.user.status

import org.genericConfig.admin.shared.common.error.Error

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 28.05.2018
 */
sealed abstract class StatusGetUser extends Error

case class GetUserSuccess() extends StatusGetUser {
  def status: String = "GET_USER_SUCCESS"
  def message: String = ""
  def code :String = ""
}

case class GetUserError() extends StatusGetUser {
  def status: String = "GET_USER_ERROR"
  def message: String = ""
  def code :String = ""
}

case class GetUserAlreadyExist() extends StatusGetUser {
  def status: String = "GET_USER_ALREADY_EXIST"
  def message: String = ""
  def code :String = ""
}

//case class GetUserPasswordWrong() extends StatusGetUser {
//  def status: String = "GET_USER_PASSWORD_WRONG"
//  def message: String = ""
//}

case class GetUserNotExist() extends StatusGetUser {
  def status: String = "GET_USER_NOT_EXIST"
  def message: String = ""
  def code :String = ""
}


