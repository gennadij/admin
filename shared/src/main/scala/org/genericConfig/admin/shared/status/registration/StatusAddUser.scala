package org.genericConfig.admin.shared.status.registration

import org.genericConfig.admin.shared.status.Status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.04.2018
 */
sealed abstract class StatusAddUser extends Status

case class AddedUser() extends StatusAddUser {
  def status: String = "ADDED_USER"
  def message: String = ""
}

case class AlredyExistUser() extends StatusAddUser {
  def status: String = "ALREDY_EXIST_USER"
  def message: String = ""
}