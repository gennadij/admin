package org.genericConfig.admin.shared.login.bo

import org.genericConfig.admin.shared.config.bo.ConfigBO
import org.genericConfig.admin.shared.login.status.StatusLogin


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