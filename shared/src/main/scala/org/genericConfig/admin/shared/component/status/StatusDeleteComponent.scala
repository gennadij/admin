package org.genericConfig.admin.shared.component.status

import org.genericConfig.admin.shared.common.status.Error

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 18.06.2018
  */
sealed abstract class StatusDeleteComponent extends Error

case class DeleteComponentSuccess() extends StatusDeleteComponent {
  def status: String = "DELETE_COMPONENT_SUCCESS"
  def message: String = ""
}

case class DeleteComponentError() extends StatusDeleteComponent {
  def status: String = "DELETE_COMPONENT_ERROR"
  def message: String = ""
}

case class DeleteComponentDefectID() extends StatusDeleteComponent {
  def status: String = "DELETE_COMPONENT_DEFECT_ID"
  def message: String = ""
}
