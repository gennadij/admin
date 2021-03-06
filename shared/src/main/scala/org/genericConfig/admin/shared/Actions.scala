package org.genericConfig.admin.shared

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 29.05.2020
 */
object Actions {

  val ADD_USER : String = "ADD_USER"
  val DELETE_USER : String = "DELETE_USER"
  val GET_USER : String = "GET_USER"
  val UPDATE_USER : String = "UPDATE_USER"

  val ADD_CONFIG : String = "ADD_CONFIG"
  val DELETE_CONFIG : String = "DELETE_CONFIG"
  val GET_CONFIGS : String = "GET_CONFIGS"
  val UPDATE_CONFIG : String = "UPDATE_CONFIG"
  val CONFIG_GRAPH : String = "CONFIG_GRAPH"

  val ADD_STEP : String = "ADD_STEP"
  val DELETE_STEP : String = "DELETE_STEP"
  val UPDATE_STEP : String = "UPDATE_STEP"

  val ADD_COMPONENT : String = "ADD_COMPONENT"
  val DELETE_COMPONENT : String = "DELETE_COMPONENT"
  val UPDATE_COMPONENT : String = "UPDATE_COMPONENT"
  val CONNECT_COMPONENT_TO_STEP : String = "CONNECT_COMPONENT_TO_STEP"

  val ADD_DEPENDENCY : String = "ADD_DEPENDENCE"
  val DELETE_DEPENDENCY : String = "DELETE_DEPENDENCY"
  val UPDATE_DEPENDENCY : String = "UPDATE_DEPENDENCY"

  val ERROR : String = "ERROR"
}
