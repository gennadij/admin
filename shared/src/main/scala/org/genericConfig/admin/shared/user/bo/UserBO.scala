package org.genericConfig.admin.shared.user.bo

import org.genericConfig.admin.shared.user.status.StatusUser


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */
case class UserBO (
    username: Option[String] = None, 
    password: Option[String] = None, 
    
    userId: Option[String] = None,  
    status: Option[StatusUser] = None
)
