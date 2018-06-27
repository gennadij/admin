package org.genericConfig.admin.shared.common.json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * Object definiert die Namen des DTO Objekts
 */

object JsonNames {
  val ADD_USER: String = "AddUser"
  val GET_USER: String = "GetUser"
  
  val ADD_CONFIG: String = "AddConfig"
  val GET_CONFIGS: String = "GetConfigs"
  val DELET_CONFIG: String = "DeleteConfig"
  val UPDATE_CONFIG: String = "UpdateConfig"
  
  val ADD_STEP: String = "AddStep"
  val DELETE_STEP: String = "DeleteStep"
  val UPDATE_STEP: String = "UpdateStep"
  val CONNECT_COMPONENT_TO_STEP: String = "ConnectComponentToStep"

  val ADD_COMPONENT: String = "AddComponent"
  val DELETE_COMPONENT: String = "DeleteComponent"
  val UPDATE_COMPONENT: String = "UpdateComponent"
  val CONFIG_TREE: String = "ConfigTree"

  val CREATE_DEPENDENCY: String = "CreateDependency"
  val VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL: String = 
    "VisualProposalForAdditionalStepsInOneLevel" 
  val ERROR: String = "Error"
  
  
}