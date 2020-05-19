package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.common.Error
import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, GraphComponent, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.component.{ComponentConfigPropertiesDTO, ComponentDTO, ComponentResultDTO, ComponentUserPropertiesDTO}
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
    * @param componentDTO: ComponentDTO
    *
    * @return ComponentDTO
    */
  def addComponent(componentDTO: ComponentDTO): ComponentDTO = {
    val stepRid : String = RidToHash.getRId(componentDTO.params.get.configProperties.get.stepId.get).get

    val (vComponent, errorAddComponent) : (Option[OrientVertex], Option[Error]) =
      GraphComponent.addComponent(componentDTO.params.get.userProperties.get)

    errorAddComponent match {
      case None =>
        val inRid: String = vComponent.get.getIdentity.toString
        val label: String = PropertyKeys.EDGE_HAS_COMPONENT
        GraphCommon.appendTo(stepRid, inRid, label) match {
          case None => createComponentDTO(
            Some(vComponent.get.getIdentity.toString),
            Some(stepRid),
            Some(vComponent.get.getProperty(PropertyKeys.NAME_TO_SHOW)),
            None)
          case Some(errorAppendComponent) => ???
        }
      case Some(errorAddComponent) => ???
    }
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

  def createComponentDTO(
                          componentRid : Option[String],
                          stepRid : Option[String],
                          nameToShow : Option[String],
                          errors : Option[List[Error]]) = {
    val e =  errors match {
      case None => None
      case Some(e) => Some(
        e.map(error => {
          ErrorDTO(
            name = error.name,
            message = error.message,
            code = error.code
          )
        })
      )
    }

    val stepId : Option[String] = Some(RidToHash.setIdAndHash(stepRid.get)._2)
    val componentId : Option[String] = Some(RidToHash.setIdAndHash(componentRid.get)._2)

    ComponentDTO(
      action = Actions.ADD_COMPONENT,
      result = Some(ComponentResultDTO(
        configProperties = Some(ComponentConfigPropertiesDTO(
          componentId = componentId,
          stepId = stepId
        )),
        userProperties = Some(ComponentUserPropertiesDTO(
          nameToShow = nameToShow
        ))
      ))
    )
  }
}