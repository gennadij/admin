package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.common.Error
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, GraphComponent, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.component.{ComponentConfigPropertiesDTO, ComponentDTO, ComponentResultDTO, ComponentUserPropertiesDTO}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.06.2018
  */
object Component {

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentDTO: ComponentDTO
    * @return ComponentDTO
    */
  def addComponent(componentDTO : ComponentDTO) : ComponentDTO = {
    new Component().addComponent(componentDTO = componentDTO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentDTO: ComponentDTO
    * @return ComponentDTO
    */
  def deleteComponent(componentDTO: ComponentDTO) : ComponentDTO = {
    new Component().deleteComponent(componentDTO = componentDTO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentDTO: ComponentDTO
    * @return ComponentDTO
    */
  def updateComponent(componentDTO: ComponentDTO): ComponentDTO = {
    new Component().updateComponent(componentDTO = componentDTO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentDTO: ComponentDTO
    * @return ComponentDTO
    */
  def connectComponentToStep(componentDTO: ComponentDTO) : ComponentDTO = {
    new Component().connectComponentToStep(componentDTO)
  }
}

class Component {
  private def addComponent(componentDTO: ComponentDTO): ComponentDTO = {
    val stepRid : String = RidToHash.getRId(componentDTO.params.get.configProperties.get.stepId.get).get

    val (vComponent, errorAddComponent) : (Option[OrientVertex], Option[Error]) =
      GraphComponent.addComponent(componentDTO.params.get.userProperties.get)

    errorAddComponent match {
      case None =>
        val inRid: String = vComponent.get.getIdentity.toString
        val label: String = PropertyKeys.EDGE_HAS_COMPONENT
        GraphCommon.appendTo(stepRid, inRid, label) match {
          case None => createComponentDTO(
            Actions.ADD_COMPONENT,
            Some(vComponent.get.getIdentity.toString),
            Some(stepRid),
            Some(vComponent.get.getProperty(PropertyKeys.NAME_TO_SHOW)),
            None)
          case Some(errorAppendComponent) =>
            GraphCommon.deleteVertex(vComponent.get.getIdentity.toString, PropertyKeys.VERTEX_COMPONENT)
            createComponentDTO(Actions.ADD_COMPONENT, None, None, None, Some(List(errorAppendComponent)))
        }
      case Some(errorAddComponent) => createComponentDTO(Actions.ADD_COMPONENT, None, None, None, Some(List(errorAddComponent)))
    }
  }

  private def deleteComponent(componentDTO: ComponentDTO): ComponentDTO = {
    val componentRId = RidToHash.getRId(componentDTO.params.get.configProperties.get.componentId.get)
    val error : Option[Error] = GraphCommon.deleteVertex(componentRId.get, PropertyKeys.VERTEX_COMPONENT)

    error match {
      case Some(e) => createComponentDTO(Actions.DELETE_COMPONENT, None, None, None, Some(List(e)))
      case None => createComponentDTO(Actions.DELETE_COMPONENT, None, None, None, None)
    }
  }

  private def updateComponent(componentDTO: ComponentDTO) : ComponentDTO = {
    val (vComponent, error) : (Option[OrientVertex], Option[Error]) = GraphComponent.updateComponent(
      userProp = componentDTO.params.get.userProperties.get,
      configProp = componentDTO.params.get.configProperties.get
    )

    error match {
      case Some(error) => createComponentDTO(Actions.UPDATE_COMPONENT, None, None, None, Some(List(error)))
      case None => createComponentDTO(
        action = Actions.UPDATE_COMPONENT,
        componentRid = Some(vComponent.get.getIdentity.toString),
        stepRid = None,
        nameToShow = Some(vComponent.get.getProperty(PropertyKeys.NAME_TO_SHOW)),
        errors = None
      )
    }
  }

  def connectComponentToStep(componentDTO: ComponentDTO) : ComponentDTO = {
    val stepRid : String = RidToHash.getRId(componentDTO.params.get.configProperties.get.stepId.get).get
    val componentRid : String = RidToHash.getRId(componentDTO.params.get.configProperties.get.componentId.get).get

    val error : Option[Error] = GraphCommon.appendTo(componentRid, stepRid, PropertyKeys.EDGE_HAS_STEP)

    error match {
      case None => createComponentDTO(
        action = Actions.CONNECT_COMPONENT_TO_STEP,
        componentRid = Some(componentRid),
        stepRid = Some(stepRid),
        nameToShow = None,
        errors = None
      )
      case Some(e) => createComponentDTO(
        action = Actions.CONNECT_COMPONENT_TO_STEP,
        errors = Some(List(e))
      )
    }
  }

  private def createComponentDTO(
                          action : String,
                          componentRid : Option[String] = None,
                          stepRid : Option[String] = None,
                          nameToShow : Option[String] = None,
                          errors : Option[List[Error]]): ComponentDTO = {
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

    val stepId : Option[String] = stepRid match {
      case Some(stepRId) => Some(RidToHash.setIdAndHash(stepRId)._2)
      case None => None
    }
    val componentId : Option[String] = componentRid match {
      case Some(componentRid) => Some(RidToHash.setIdAndHash(componentRid)._2)
      case None => None
    }

    ComponentDTO(
      action = action,
      result = Some(ComponentResultDTO(
        configProperties = Some(ComponentConfigPropertiesDTO(
          componentId = componentId,
          stepId = stepId
        )),
        userProperties = Some(ComponentUserPropertiesDTO(
          nameToShow = nameToShow
        )),
        errors = e
      ))
    )
  }
}