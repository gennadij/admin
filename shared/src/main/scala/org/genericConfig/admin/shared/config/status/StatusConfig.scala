package org.genericConfig.admin.shared.config.status

import org.genericConfig.admin.shared.common.status.{ODBConnectionFail, Error}


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
    common: Option[Error] = None
)