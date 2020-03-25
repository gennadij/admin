package org.genericConfig.admin.models.persistence.orientdb

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
 */

object PropertyKeys {
  //AdminUser
  val VERTEX_ADMIN_USER : String = "AdminUser"
  val USERNAME : String = "username"
  val PASSWORD : String = "password"
  val ADMIN_ID : String = "adminId"
  //Config
  val EDGE_HAS_CONFIG : String = "hasConfig"
  val CONFIG_URL : String = "configUrl"
  val VERTEX_CONFIG : String = "Config"
  val CONFIGURATION_COURSE : String = "configurationCourse"

  val EDGE_HAS_COMPONENT = "hasComponent"
  val EDGE_HAS_STEP = "hasStep"
  val EDGE_HAS_DEPENDENCY = "hasDependency"
  val VERTEX_STEP = "Step"
  val VERTEX_COMPONENT = "Component"
  val COMPONENT_ID = "componentId"
  val KIND = "kind"
  val STEP_ID = "stepId"
  val SELECTION_CRITERIUM_MIN = "selectionCriteriumMin"
  val SELECTION_CRITERIUM_MAX = "selectionCriteriumMax"

  val NAME_TO_SHOW: String = "nameToShow"
  val DEPENDENCY_TYPE: String = "dependencyType"
  val VISUALIZATION: String = "visualization"
  def CLASS: String = "class:" 
}