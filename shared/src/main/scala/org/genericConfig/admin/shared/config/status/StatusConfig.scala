package org.genericConfig.admin.shared.config.status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 18.04.2018
 */
case class StatusConfig (
    addConfig: Option[StatusAddConfig] = None,
    getConfigs: Option[StatusGetConfigs] = None,
    deleteConfig: Option[StatusDeleteConfig] = None,
    updateConfig: Option[StatusUpdateConfig] = None,
    common: Option[Any] = None
)