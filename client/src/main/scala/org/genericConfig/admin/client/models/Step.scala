package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.views.step.StepPage
import org.genericConfig.admin.shared.configGraph.ConfigGraphStepDTO

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class Step {
  def showStep(param: Option[Any]): Unit = {
    val stepDTO: ConfigGraphStepDTO = param.get.asInstanceOf[ConfigGraphStepDTO]
    new StepPage().drawStepPage(stepDTO)
  }
}
