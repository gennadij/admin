package org.genericConfig.admin.client.views.step

import org.genericConfig.admin.shared.configGraph.ConfigGraphStepDTO
import org.scalajs.jquery.jQuery

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class StepPage {
  def drawStepPage(configGraphStepDTO: ConfigGraphStepDTO): Unit = {
    jQuery(s"#${configGraphStepDTO.id}").css("fill", "#15e751")
  }
}
