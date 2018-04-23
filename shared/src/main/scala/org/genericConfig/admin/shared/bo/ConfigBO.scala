package org.genericConfig.admin.shared.bo

import org.genericConfig.admin.shared.status.config.StatusConfig

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 12.04.2018
 */
case class ConfigBO (
    admiId: String = "",
    configId: String = "",
    configUrl: String = "",
    status: StatusConfig
)