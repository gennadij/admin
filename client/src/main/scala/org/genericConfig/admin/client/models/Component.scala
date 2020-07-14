package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.views.configGraph.NodeEditComponentPage
import org.genericConfig.admin.shared.configGraph.{ConfigGraphComponentDTO, ConfigGraphStepDTO}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class Component() {
  def updateComponent(param: Option[Any]) = {
    val componentDTO: ConfigGraphComponentDTO = param.get.asInstanceOf[ConfigGraphComponentDTO]
    println("UPDATE_COMPONENT " + componentDTO.properties.nameToShow)
  }

  def addStep(param: Option[Any]) = {
    val componentDTO: ConfigGraphComponentDTO = param.get.asInstanceOf[ConfigGraphComponentDTO]
    println("ADD_STEP " + componentDTO.properties.nameToShow)
  }

  def showComponentPage(param: Option[Any]): Unit = {
    new NodeEditComponentPage().drawComponentPage(param.get.asInstanceOf[ConfigGraphComponentDTO])
  }
}
