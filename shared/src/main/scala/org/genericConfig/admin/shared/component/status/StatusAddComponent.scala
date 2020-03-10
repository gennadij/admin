package org.genericConfig.admin.shared.component.status

import org.genericConfig.admin.shared.common.status.Error

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.06.2018
 */
sealed abstract class StatusAddComponent extends Error

case class AddComponentSuccess() extends StatusAddComponent {
  def status: String = "ADD_COMPONENT_SUCCESS"
  def message: String = ""
  def code :String = ""
}

case class AddComponentError() extends StatusAddComponent {
  def status: String = "ADD_COMPONENT_ERROR"
  def message: String = ""
  def code :String = ""
}

//case class AddStepAlreadyExist() extends StatusAddStep {
//  def status: String = "ADD_STEP_ALREADY_EXIST"
//  def message: String = ""
//}
//
//case class AddStepDefectComponentOrConfigId() extends StatusAddStep {
//  def status: String = "ADD_STEP_DEFECT_COMPONENT_OR_CONFIG_ID"
//  def message: String = ""
//}
//
//case class AddStepIdHashNotExist() extends StatusAddStep {
//  def status: String = "ADD_STEP_ID_HASH_NOT_EXIST"
//  def message: String = ""
//}