package org.genericConfig.admin.shared.common.json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * Object definiert die Namen des DTO Objekts
 */

object JsonNames {
  val REGISTRATION: String = "Registration"
  
  val LOGIN: String = "Login"
  
  val ADD_CONFIG: String = "AddConfig"
  val GET_CONFIGS: String = "GetConfigs"
  val DELET_CONFIG: String = "DeleteConfig"
  val UPDATE_CONFIG: String = "UpdateConfig"
  
  val ADD_STEP: String = "AddStep"
  val DELETE_STEP: String = "DeleteStep"
  val UPDATE_STEP: String = "UpdateStep"
  
  val ADD_FIRST_STEP: String = "AddFirstStep"
  val DELETE_FIRST_STEP: String = "DeleteFirstStep"
  val UPDATE_FIRST_STEP: String = "UpdateFirstStep"
  
  val CREATE_COMPONENT: String = "CreateComponent"
  val CONFIG_TREE: String = "ConfigTree"
  val CONNECTION_COMPONENT_TO_STEP: String = "ConnectionComponentToStep"
  val CREATE_DEPENDENCY: String = "CreateDependency"
  val VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL: String = 
    "VisualProposalForAdditionalStepsInOneLevel" 
  val ERROR: String = "Error"
  
  
}