package org.genericConfig.admin.models.wrapper.login

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann on 14.09.16.
 */
case class LoginOut (
    adminId: String,
    username: String,
    configs: List[Config],
    status: String,
    message: String
)