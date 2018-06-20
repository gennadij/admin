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

  /**
    * @author Gennadi Heimann
    *
    * @version 0.1.0
    *
    * @param componentBO: ComponentBO
    *
    * @return ComponentBO
    */
  def addComponent(componentBO: ComponentBO): ComponentBO = {
    val stepRid = RidToHash.getRId(componentBO.stepId.get)
    val componentBOOut: ComponentBO = Persistence.addComponent(componentBO.copy(stepId = stepRid))

    componentBOOut.copy(
      json = Some(JsonNames.ADD_COMPONENT),
      componentId = Some(RidToHash.setIdAndHash(componentBOOut.componentId.get)._2)
    )
  }

  /**
    * @author Gennadi Heimann
    *
    * @version 0.1.0
    *
    * @param componentBO: ComponentBO
    *
    * @return ComponentBO
    */
  def deleteComponent(componentBO: ComponentBO): ComponentBO = {
    val componentBOIn = componentBO.copy(componentId = RidToHash.getRId(componentBO.componentId.get))
    val componentBOOut: ComponentBO = Persistence.deleteComponent(componentBOIn)

    componentBOOut.copy(
      json = Some(JsonNames.DELETE_COMPONENT)
    )
  }

  /**
    * @author Gennadi Heimann
    *
    * @version 0.1.0
    *
    * @param componentBO: ComponentBO
    *
    * @return ComponentBO
    */
  def updateComponent(componentBO: ComponentBO): ComponentBO = {
    val componentRid = RidToHash.getRId(componentBO.componentId.get)
    val componentBOOut: ComponentBO = Persistence.updateComponent(
      componentBO.copy(componentId = componentRid)
    )

    componentBOOut.copy(
      json = Some(JsonNames.UPDATE_COMPONENT),
      componentId = RidToHash.getHash(componentBOOut.componentId.get)
    )
  }
}