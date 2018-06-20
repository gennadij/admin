package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.common.json.{JsonNames, JsonStatus}
import org.genericConfig.admin.shared.component.bo.ComponentBO
import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentOut, JsonComponentResult, JsonComponentStatus}


/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.06.2017
  */
class WrapperComponent {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param jsonComponentIn : JsonComponentIn
    * @return ComponentBO
    */
  private[wrapper] def toComponentBO(jsonComponentIn: JsonComponentIn): ComponentBO = {
    jsonComponentIn.json match {
      case json if json == JsonNames.ADD_COMPONENT =>
        ComponentBO(
          json = Some(jsonComponentIn.json),
          stepId = Some(jsonComponentIn.params.stepId.get),
          nameToShow = Some(jsonComponentIn.params.nameToShow.get),
          kind = Some(jsonComponentIn.params.kind.get)
        )
      case json if json == JsonNames.DELETE_COMPONENT =>
        ComponentBO(
          json = Some(jsonComponentIn.json),
          componentId = jsonComponentIn.params.componentId
        )
      case json if json == JsonNames.UPDATE_COMPONENT =>
        ComponentBO(
          json = Some(jsonComponentIn.json),
          componentId = jsonComponentIn.params.componentId,
          nameToShow = Some(jsonComponentIn.params.nameToShow.get),
          kind = Some(jsonComponentIn.params.kind.get)
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param componentBO : ComponentBO
    * @return JsonComponentOut
    */
  private[wrapper] def toJsonComponentOut(componentBO: ComponentBO): JsonComponentOut = {
    componentBO.json.get match {
      case json if json == JsonNames.ADD_COMPONENT =>
        JsonComponentOut(
          json = componentBO.json.get,
          result = JsonComponentResult(
            componentId = componentBO.componentId,
            nameToShow = componentBO.nameToShow,
            kind = componentBO.kind,
            status = JsonComponentStatus(
              addComponent = componentBO.status.get.addComponent match {
                case Some(addComponent) => Some(JsonStatus(
                  status = addComponent.status,
                  message = addComponent.message
                ))
                case None => None
              },
              appendComponent = componentBO.status.get.appendComponent match {
                case Some(appendComponent) => Some(JsonStatus(
                  status = appendComponent.status,
                  message = appendComponent.message
                ))
                case None => None
              },
              deleteComponent = componentBO.status.get.deleteComponent match {
                case Some(deleteComponent) => Some(JsonStatus(
                  status = deleteComponent.status,
                  message = deleteComponent.message
                ))
                case None => None
              },
              updateComponent = componentBO.status.get.updateComponent match {
                case Some(updateComponent) => Some(JsonStatus(
                  status = updateComponent.status,
                  message = updateComponent.message
                ))
                case None => None
              },
              common = componentBO.status.get.common match {
                case Some(common) => Some(JsonStatus(
                  status = common.status,
                  message = common.message
                ))
                case None => None
              }
            )
          )
        )
      case json if json == JsonNames.DELETE_COMPONENT =>
        JsonComponentOut(
          json = componentBO.json.get,
          result = JsonComponentResult(
            status = JsonComponentStatus(
              addComponent = componentBO.status.get.addComponent match {
                case Some(addComponent) => Some(JsonStatus(
                  status = addComponent.status,
                  message = addComponent.message
                ))
                case None => None
              },
              appendComponent = componentBO.status.get.appendComponent match {
                case Some(appendComponent) => Some(JsonStatus(
                  status = appendComponent.status,
                  message = appendComponent.message
                ))
                case None => None
              },
              deleteComponent = componentBO.status.get.deleteComponent match {
                case Some(deleteComponent) => Some(JsonStatus(
                  status = deleteComponent.status,
                  message = deleteComponent.message
                ))
                case None => None
              },
              updateComponent = componentBO.status.get.updateComponent match {
                case Some(updateComponent) => Some(JsonStatus(
                  status = updateComponent.status,
                  message = updateComponent.message
                ))
                case None => None
              },
              common = componentBO.status.get.common match {
                case Some(common) => Some(JsonStatus(
                  status = common.status,
                  message = common.message
                ))
                case None => None
              }
            )
          )
        )
      case json if json == JsonNames.UPDATE_COMPONENT =>
        JsonComponentOut(
          json = componentBO.json.get,
          result = JsonComponentResult(
            componentId = componentBO.componentId,
            nameToShow = componentBO.nameToShow,
            kind = componentBO.kind,
            status = JsonComponentStatus(
              addComponent = componentBO.status.get.addComponent match {
                case Some(addComponent) => Some(JsonStatus(
                  status = addComponent.status,
                  message = addComponent.message
                ))
                case None => None
              },
              appendComponent = componentBO.status.get.appendComponent match {
                case Some(appendComponent) => Some(JsonStatus(
                  status = appendComponent.status,
                  message = appendComponent.message
                ))
                case None => None
              },
              deleteComponent = componentBO.status.get.deleteComponent match {
                case Some(deleteComponent) => Some(JsonStatus(
                  status = deleteComponent.status,
                  message = deleteComponent.message
                ))
                case None => None
              },
              updateComponent = componentBO.status.get.updateComponent match {
                case Some(updateComponent) => Some(JsonStatus(
                  status = updateComponent.status,
                  message = updateComponent.message
                ))
                case None => None
              },
              common = componentBO.status.get.common match {
                case Some(common) => Some(JsonStatus(
                  status = common.status,
                  message = common.message
                ))
                case None => None
              }
            )
          )
        )
    }
  }
}