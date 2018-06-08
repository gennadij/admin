package org.genericConfig.admin.shared.user.status

import org.genericConfig.admin.shared.common.status.Status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.04.2018
 */
sealed abstract class StatusAddUser extends Status

case class AddUserSuccess() extends StatusAddUser {
  def status: String = "ADD_USER_SUCCESS"
  def message: String = ""
}

case class AddUserAlreadyExist() extends StatusAddUser {
  def status: String = "ADD_USER_ALREADY_EXIST"
  def message: String = ""
}

case class AddUserError() extends StatusAddUser {
  def status: String = "ADD_USER_ERROR"
  def message: String = ""
  
}