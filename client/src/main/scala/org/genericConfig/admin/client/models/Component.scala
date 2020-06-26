package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.views.component.ComponentPage
import org.genericConfig.admin.shared.configGraph.ConfigGraphComponentDTO

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.06.2020
 */
class Component() {
  def showComponentPage(param: Option[Any]): Unit = {
    new ComponentPage().drawComponentPage(param.get.asInstanceOf[ConfigGraphComponentDTO])
  }
}
