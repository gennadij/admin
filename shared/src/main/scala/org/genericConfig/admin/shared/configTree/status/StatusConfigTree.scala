package org.genericConfig.admin.shared.configTree.status

import org.genericConfig.admin.shared.common.status.Error


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.04.2018
 */
case class StatusConfigTree (
    getConfigTree: StatusGetConfigTree,
    common: Error
)