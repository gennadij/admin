package org.genericConfig.admin.client.views.component

import org.genericConfig.admin.shared.configGraph.ConfigGraphComponentDTO
import org.scalajs.jquery.jQuery

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class ComponentPage() {
  def drawComponentPage(configGraphComponentDTO: ConfigGraphComponentDTO): Unit = {
    jQuery(s"#${configGraphComponentDTO.componentId}").css("fill", "#163183")
  }

}
