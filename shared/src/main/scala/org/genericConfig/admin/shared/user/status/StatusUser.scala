package org.genericConfig.admin.shared.user.status

import org.genericConfig.admin.shared.login.status.StatusLogin
import org.genericConfig.admin.shared.common.status.Status


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.04.2018
 */

case class StatusUser(
    addUser: Option[StatusAddUser] = None,
    deleteUser: Option[StatusDeleteUser] = None,
    getUser: Option[StatusGetUser] = None,
    updateUser: Option[StatusUpdateUser] = None,
    common: Option[Status] = None
)
