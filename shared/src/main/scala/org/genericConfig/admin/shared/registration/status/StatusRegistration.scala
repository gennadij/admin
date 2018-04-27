package org.genericConfig.admin.shared.registration.status

import org.genericConfig.admin.shared.login.status.StatusLogin
import org.genericConfig.admin.shared.common.status.Status


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.04.2018
 */

case class StatusRegistration(
    addUser: Option[StatusAddUser], 
    common: Option[Status]
)
