package org.genericConfig.admin.shared.login.status

import org.genericConfig.admin.shared.common.status.Status


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.04.2018
 */
case class StatusLogin (
    userLogin: StatusUserLogin, 
    common: Status
)


