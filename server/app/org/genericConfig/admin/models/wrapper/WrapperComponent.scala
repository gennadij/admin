package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.component.json.JsonComponentIn
import org.genericConfig.admin.shared.component.bo.ComponentBO
import org.genericConfig.admin.shared.component.json.JsonComponentOut
import org.genericConfig.admin.shared.component.json.JsonComponentResult

/**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonComponentIn : JsonComponentIn
   * 
   * @return ComponentBO
   */
class WrapperComponent {
  
  private[wrapper] def toComponentBO(jsonComponentIn : JsonComponentIn): ComponentBO = {
    ComponentBO(
        json = Some(jsonComponentIn.json),
        stepId = Some(jsonComponentIn.params.stepId),
        nameToShow = Some(jsonComponentIn.params.nameToShow),
        kind = Some(jsonComponentIn.params.kind)
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param componentBO: ComponentBO
   * 
   * @return JsonComponentOut
   */
  private[wrapper] def toJsonComponentOut(componentBO: ComponentBO): JsonComponentOut = {
    JsonComponentOut(
        json = componentBO.json.get,
        result = JsonComponentResult(
            componentId = componentBO.componentId.get,
            status = ???
        )
    )
  }
}