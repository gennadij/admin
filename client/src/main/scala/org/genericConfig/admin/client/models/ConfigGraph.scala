package org.genericConfig.admin.client.models

import org.genericConfig.admin.shared.config.UserConfigDTO

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 17.06.2020
 */
class ConfigGraph {
  def showConfigGraph(param: Option[Any]) = {
    println("showConfigGraph")
    param.get.asInstanceOf[UserConfigDTO]
  }
}
