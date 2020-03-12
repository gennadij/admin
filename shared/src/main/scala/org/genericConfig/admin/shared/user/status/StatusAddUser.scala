package org.genericConfig.admin.shared.user.status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.04.2018
 */
sealed abstract class StatusAddUser extends Error

case class AddUserSuccess() extends StatusAddUser {
  def status: String = "ADD_USER_SUCCESS"
  def message: String = ""
  def code :String = ""
}



case class AddUserError() extends StatusAddUser {
  def status: String = "ADD_USER_ERROR"
  def message: String = ""
  def code :String = ""
}