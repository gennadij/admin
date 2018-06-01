package org.genericConfig.admin.shared.config.bo

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 24.04.2018
 */
case class Configuration (
    configId: Option[String] = None, 
    configUrl: Option[String] = None
)