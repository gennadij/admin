package org.genericConfig.admin.shared.step.status

import org.genericConfig.admin.shared.common.status.Error

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2018
 */
case class StatusStep (
    addStep: Option[StatusAddStep] = None,
    deleteStep: Option[StatusDeleteStep] = None,
    updateStep: Option[StatusUpdateStep] = None,
    appendStep: Option[StatusAppendStep] = None,
    common: Option[Error] = None
)