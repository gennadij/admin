package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentOut, JsonComponentResult, JsonComponentStatus}
import org.genericConfig.admin.shared.component.bo.ComponentBO
import org.genericConfig.admin.shared.common.json.JsonStatus
import play.api.Logger


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
    ComponentBO(
      json = Some(jsonComponentIn.json),
      stepId = Some(jsonComponentIn.params.stepId),
      nameToShow = Some(jsonComponentIn.params.nameToShow),
      kind = Some(jsonComponentIn.params.kind)
    )
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param componentBO : ComponentBO
    * @return JsonComponentOut
    */
  private[wrapper] def toJsonComponentOut(componentBO: ComponentBO): JsonComponentOut = {
    Logger.info(componentBO.toString)
    JsonComponentOut(
      json = componentBO.json.get,
      result = JsonComponentResult(
        componentId = componentBO.componentId.get,
        nameToShow = componentBO.nameToShow.get,
        kind = componentBO.kind.get,
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
              status = "",
              message = ""
            ))
            case None => None
          },
          updateComponent = componentBO.status.get.updateComponent match {
            case Some(updateComponent) => Some(JsonStatus(
              status = "",
              message = ""
            ))
            case None => None
          },
          common = componentBO.status.get.common match {
            case Some(common) => Some(JsonStatus(
              status = common.status,
              message = common.message
            ))
            case None => None
          },
        )
      )
    )
  }
}