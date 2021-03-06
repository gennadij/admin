package org.genericConfig.admin.shared.configTree.bo


/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 27.04.2018
  */
case class ComponentForConfigTreeBO(
                                     componentId: String,
                                     nameToShow: String,
                                     kind: String,
                                     nextStepId: Option[String]
                                   )