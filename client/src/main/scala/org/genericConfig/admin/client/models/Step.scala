package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.views.configGraph.NodeEditStepPage
import org.genericConfig.admin.shared.configGraph.ConfigGraphStepDTO

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class Step {
  def updateStep(param: Option[Any]): Unit = {
    val stepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]
    println("UPDATE_STEP " + stepDTO.properties.nameToShow)
  }

  def addComponent(param : Option[Any]) : Unit = {
    val stepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]
    println("ADD_Component " + stepDTO.properties.nameToShow)
  }

  def showStep(param: Option[Any]): Unit = {
    val stepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]
    new NodeEditStepPage().drawStepPage(stepDTO)
  }
}
