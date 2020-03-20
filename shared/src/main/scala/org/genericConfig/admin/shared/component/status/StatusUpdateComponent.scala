package org.genericConfig.admin.shared.component.status

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 19.06.2018
  */
sealed abstract class StatusUpdateComponent //extends Error

case class UpdateComponentSuccess() extends StatusUpdateComponent {
  def status: String = "UPDATE_COMPONENT_SUCCESS"
  def message: String = ""
  def code :String = ""
}

case class UpdateComponentError() extends StatusUpdateComponent {
  def status: String = "UPDATE_COMPONENT_ERROR"
  def message: String = ""
  def code :String = ""
}