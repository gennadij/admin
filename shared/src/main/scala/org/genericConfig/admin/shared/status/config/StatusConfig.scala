package org.genericConfig.admin.shared.status.config

import org.genericConfig.admin.shared.status.Status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 18.04.2018
 */
case class StatusConfig (
    addConfig: Option[StatusAddConfig],
    getConfigs: Option[StatusGetConfigs],
    deleteConfig: Option[StatusDeleteConfig],
    updateConfig: Option[StatusUpdateConfig],
    common: Option[Status]
)