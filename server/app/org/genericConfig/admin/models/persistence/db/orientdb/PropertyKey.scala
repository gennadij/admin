package org.genericConfig.admin.models.persistence.db.orientdb

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
 */

object PropertyKey {
  
  val VERTEX_ADMIN_USER = "AdminUser"
  val VERTEX_CONFIG = "Config"
  val VERTEX_STEP = "Step"
  val VERTEX_COMPONENT = "Component"
  val EDGE_HAS_CONFIG = "hasConfig"
  val EDGE_HAS_FIRST_STEP = "hasFirstStep"
  val EDGE_HAS_COMPONENT = "hasComponent"
  val EDGE_HAS_STEP = "hasStep"
  val EDGE_HAS_DEPENDENCY = "hasDependency"
  val USERNAME = "username"
  val PASSWORD = "password"
  val ADMIN_ID = "adminId"
  val COMPONENT_ID = "componentId"
  val KIND = "kind"
  val STEP_ID = "stepId"
  val SELECTION_CRITERIUM_MIN = "selectionCriteriumMin"
  val SELECTION_CRITERIUM_MAX = "selectionCriteriumMax"
  val CONFIG_URL = "configUrl"
  val NAME_TO_SHOW: String = "nameToShow"
  val DEPENDENCY_TYPE: String = "dependencyType"
  val VISUALIZATION: String = "visualization"
  
  
}