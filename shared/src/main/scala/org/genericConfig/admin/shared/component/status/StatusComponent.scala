package org.genericConfig.admin.shared.component.status

import org.genericConfig.admin.shared.common.status.Status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.06.2017
 */
case class StatusComponent (
    addComponent: Option[StatusAddComponent] = None,
    appendComponent: Option[StatusAppendComponent] = None,
    deleteComponent: Option[StatusDeleteComponent] = None,
    updateComponent: Option[Any] = None,
    common: Option[Status] = None
)