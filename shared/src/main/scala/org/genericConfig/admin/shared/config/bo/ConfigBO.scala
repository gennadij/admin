package org.genericConfig.admin.shared.config.bo

import org.genericConfig.admin.shared.config.status.StatusConfig

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 12.04.2018
 */
case class ConfigBO (
    userId: Option[String] = None,
    configs: Option[List[Configuration]] = None,
    status: Option[StatusConfig] = None
)