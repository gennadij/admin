package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.models.wrapper.RidToHash
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.component.bo.ComponentBO

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.06.2018
  */
object Component {
  
  def addComponent(componentBO: ComponentBO): ComponentBO = {
    val stepRid = RidToHash.getRId(componentBO.stepId.get)
    val compponentBOOut: ComponentBO = Persistence.addComponent(componentBO.copy(stepId = stepRid))

    compponentBOOut.copy(
      json = Some(JsonNames.ADD_COMPONENT),
      componentId = Some(RidToHash.setIdAndHash(compponentBOOut.componentId.get)._2)
    )
  }
}