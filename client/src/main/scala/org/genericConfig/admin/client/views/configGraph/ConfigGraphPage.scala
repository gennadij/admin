package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.shared.configGraph.ConfigGraphResultDTO

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.06.2020
 */
class ConfigGraphPage {
  def drawConfigGraph(configGraphResultDTO: ConfigGraphResultDTO): Unit = {
    println(configGraphResultDTO)
    RunJSinScalaJS.runD3()
  }
}
