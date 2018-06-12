package org.genericConfig.admin.shared.configTree.bo

import org.genericConfig.admin.shared.configTree.status.StatusConfigTree


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.04.2018
 */
case class ConfigTreeBO (
    userId: Option[String] = None,
    configId: Option[String] = None,
    configTree: Option[StepForConfigTreeBO] = None,
    status: Option[StatusConfigTree] = None
)