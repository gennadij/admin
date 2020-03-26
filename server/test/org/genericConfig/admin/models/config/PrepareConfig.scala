package org.genericConfig.admin.models.config

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.01.2017
  */
class PrepareConfig extends CommonFunction {

  val userAddingNewConfig =                "user3"
  val userTwoSameConfigUrls =              "user13"

  def prepareAddingNewConfig(wC: WebClient): Any = {

  }

  def prepareTwoSameConfigUrls(wC: WebClient): Unit = {

  }

}
