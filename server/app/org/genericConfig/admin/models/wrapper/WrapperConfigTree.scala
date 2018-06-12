package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.configTree.bo.ConfigTreeBO
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeOut
import org.genericConfig.admin.shared.configTree.status.GetConfigTreeSuccess
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeResult
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeStep
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeStatus
import org.genericConfig.admin.shared.common.json.JsonStatus
import org.genericConfig.admin.shared.configTree.status.GetConfigTreeEmpty
import org.genericConfig.admin.shared.configTree.status.GetConfigTreeError
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeComponent
import org.genericConfig.admin.shared.configTree.bo.ComponentForConfigTreeBO
import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeIn

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 12.06.2018
  */
class WrapperConfigTree {
  
      /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param ConfigTreeOut
   * 
   * @return JsonConfigTreeOut
   */
  def toConfigTreeBO(jsonConfigTreeIn: JsonConfigTreeIn): ConfigTreeBO = {
    ConfigTreeBO(
      configId = Some(jsonConfigTreeIn.params.configId)
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configTreeBO: ConfigTreeBO
   * 
   * @return JsonConfigTreeOut
   */
  private[wrapper] def toJsonConfigTreeOut(configTreeBO: ConfigTreeBO): JsonConfigTreeOut = {
    
    configTreeBO.status.get.getConfigTree match {
      case GetConfigTreeSuccess() => {
        JsonConfigTreeOut(
            result = JsonConfigTreeResult(
                Some(configTreeBO.userId.get),
                Some(configTreeBO.configId.get),
                Some(JsonConfigTreeStep(
                    configTreeBO.configTree.get.stepId,
                    configTreeBO.configTree.get.kind,
                    getJsonConfigTreeComponents(configTreeBO.configTree.get.components)
                )),
                JsonConfigTreeStatus(
                    Some(JsonStatus(
                        configTreeBO.status.get.getConfigTree.status,
                        configTreeBO.status.get.getConfigTree.message
                    )),
                    Some(JsonStatus(
                        configTreeBO.status.get.common.status,
                        configTreeBO.status.get.common.message
                    ))
                )
            )
        )
      }
      case GetConfigTreeEmpty() => {
        JsonConfigTreeOut(
            result = JsonConfigTreeResult(
                Some(configTreeBO.userId.get),
                Some(configTreeBO.configId.get),
                None, 
                JsonConfigTreeStatus(
                    Some(JsonStatus(
                        configTreeBO.status.get.getConfigTree.status,
                        configTreeBO.status.get.getConfigTree.message
                    )),
                    Some(JsonStatus(
                        configTreeBO.status.get.common.status,
                        configTreeBO.status.get.common.message
                    ))
                )
            )
        )
      }
      case GetConfigTreeError() => {
        JsonConfigTreeOut(
            result = JsonConfigTreeResult(
                None,
                None,
                None, 
                JsonConfigTreeStatus(
                    Some(JsonStatus(
                        configTreeBO.status.get.getConfigTree.status,
                        configTreeBO.status.get.getConfigTree.message
                    )),
                    Some(JsonStatus(
                        configTreeBO.status.get.common.status,
                        configTreeBO.status.get.common.message
                    ))
                )
            )
        )
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param components: Set[Option[ComponentForConfigTreeBO]]
   * 
   * @return Set[JsonConfigTreeComponent]
   */
  private def getJsonConfigTreeComponents(components: Set[Option[ComponentForConfigTreeBO]]): Set[JsonConfigTreeComponent] = {
    
    components.map{
      component => {
        component.get.nextStepId match {
          case Some(step) => {
            component.get.nextStep match {
              case Some(step) => {
                JsonConfigTreeComponent(
                    component.get.componentId,
                    component.get.kind,
                    Some(component.get.nextStepId.get),
                    Some(JsonConfigTreeStep(
                        component.get.nextStep.get.stepId,
                        component.get.nextStep.get.kind,
                        getJsonConfigTreeComponents(component.get.nextStep.get.components)
                    ))
                )
              }
              case None => {
                JsonConfigTreeComponent(
                    component.get.componentId,
                    component.get.kind,
                    Some(component.get.nextStepId.get),
                    None
                )
              }
            }
            
          }
          case None => {
            JsonConfigTreeComponent(
                component.get.componentId,
                component.get.kind,
                Some("last"),
                None
            )
          }
        }
      }
    }
  }
}