package org.genericConfig.admin.shared.bo

import org.genericConfig.admin.shared.status.login.StatusLogin
import org.genericConfig.admin.shared.bo.config.Configuration
import org.genericConfig.admin.shared.bo.config.ConfigBO

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 12.04.2018
 */
case class LoginBO (
    username: String = "",
    adminId: String = "",
    config: ConfigBO,
    status: StatusLogin
)