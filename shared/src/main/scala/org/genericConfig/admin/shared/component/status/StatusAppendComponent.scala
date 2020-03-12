package org.genericConfig.admin.shared.component.status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann Jun 13, 2018
 */

sealed abstract class StatusAppendComponent //extends Error


case class AppendComponentSuccess() extends StatusAppendComponent {
  def status: String = "APPEND_COMPONENT_SUCCESS"
  def message: String = ""
  def code :String = ""
}

case class AppendComponentError() extends StatusAppendComponent {
  def status: String = "APPEND_COMPONENT_ERROR"
  def message: String = ""
  def code :String = ""
}