package org.genericConfig.admin.models.logic

import org.genericConfig.admin.shared.component.bo.ComponentBO
import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.models.wrapper.RidToHash

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.06.2018
  */
object Component {
  
  def addComponent(componentBO: ComponentBO): ComponentBO = {
    val stepRid = RidToHash.getRId(componentBO.stepId.get)
    Persistence.addComponent(componentBO.copy(stepId = stepRid))
  }
}