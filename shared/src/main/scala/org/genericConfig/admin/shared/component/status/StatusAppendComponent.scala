package org.genericConfig.admin.shared.component.status

import org.genericConfig.admin.shared.common.status.Status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann Jun 13, 2018
 */

sealed abstract class StatusAppendComponent extends Status


case class AppendComponentSuccess() extends StatusAppendComponent {
  def status: String = "APPEND_COMPONENT_SUCCESS"
  def message: String = ""
}

case class AppendComponentError() extends StatusAppendComponent {
  def status: String = "APPEND_COMPONENT_ERROR"
  def message: String = ""
}