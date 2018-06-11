package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.common.json._
import org.genericConfig.admin.shared.config.bo._
import org.genericConfig.admin.shared.config.json._
import org.genericConfig.admin.shared.configTree.bo._
import org.genericConfig.admin.shared.configTree.json._
import org.genericConfig.admin.shared.configTree.status._
import org.genericConfig.admin.shared.step.bo._
import org.genericConfig.admin.shared.step.json._
import org.genericConfig.admin.shared.user.bo.UserBO
import org.genericConfig.admin.shared.user.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.09.2017
 */



trait Wrapper{
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonUserIn: JsonUserIn
   * 
   * @return UserBO
   */
  def toAddUserBO(jsonUserIn: JsonUserIn): UserBO = {
    new WrapperUser().toAddUserBO(jsonUserIn)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param userBO: UserBO
   * 
   * @return JsonUserOut
   */
  def toJsonAddUserOut(userBO: UserBO): JsonUserOut = {
    new WrapperUser().toJsonAddUserOut(userBO)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonUserIn: JsonUserIn
   * 
   * @return UserBO
   */
  def toGetUserBO(jsonUserIn: JsonUserIn): UserBO = {
    new WrapperUser().toGetUserBO(jsonUserIn)
  }
  
   /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param userBO: UserBO
   * 
   * @return JsonUserOut
   */
  def toJsonGetUserOut(userBO: UserBO): JsonUserOut = {
    new WrapperUser().toJsonGetUserOut(userBO)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonAddconfigIn: JsonAddConfigIn
   * 
   * @return ConfigBO
   */
  def toAddConfigBO(jsonAddconfigIn: JsonAddConfigIn): ConfigBO = {
    new WrapperConfig().toAddConfigBO(jsonAddconfigIn)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configBO: ConfigBO
   * 
   * @return JsonAddConfigOut
   */
  def toJsonAddConfigOut(configBO: ConfigBO): JsonAddConfigOut = {
    new WrapperConfig().toJsonAddConfigOut(configBO)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonAddconfigIn: JsonAddConfigIn
   * 
   * @return ConfigBO
   */
  def toGetConfigsBO(jsonAddconfigIn: JsonGetConfigsIn): ConfigBO = {
    new WrapperConfig().toGetConfigsBO(jsonAddconfigIn)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param configBO: ConfigBO
   * 
   * @return JsonGetConfigsOut
   */
  
  def toJsonGetConfigsOut(configBO:ConfigBO): JsonGetConfigsOut = {
    new WrapperConfig().toJsonGetConfigsOut(configBO)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonDeleteConfigIn: JsonDeleteConfigIn
   * 
   * @return ConfigBO
   */
  def toDeleteConfigBO(jsonDeleteConfigIn: JsonDeleteConfigIn): ConfigBO = {
    new WrapperConfig().toDeleteConfigBO(jsonDeleteConfigIn)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param configBO: ConfigBO
   * 
   * @return JsonDeleteConfigOut
   */
  def toJsonDeleteConfigOut(configBO: ConfigBO): JsonDeleteConfigOut = {
    new WrapperConfig().toJsonDeleteConfigOut(configBO)
  }

  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonUpdateConfigIn: JsonDeleteConfigIn
   * 
   * @return ConfigBO
   */
  def toUpdateConfigBO(jsonUpdateConfigIn: JsonUpdateConfigIn): ConfigBO = {
    new WrapperConfig().toUpdateConfigBO(jsonUpdateConfigIn)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configBO: ConfigBO
   * 
   * @return JsonUpdateConfigOut
   */
  def toJsonUpdateConfigOut(configBO: ConfigBO): JsonUpdateConfigOut = {
    new WrapperConfig().toJsonUpdateConfigOut(configBO)
  }
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param jsonStepIn: JsonStepCS
   * 
   * @return StepCS
   */
  def toStepBO(jsonStepIn: JsonStepIn): StepBO = {
    new WrapperStep().toStepBO(jsonStepIn)
  }
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param stepBO: StepBO
   * 
   * @return JsonStepSC
   */
  def toJsonStepOut(stepBO: StepBO): JsonStepOut = {
    new WrapperStep().toJsonStepOut(stepBO)
  }
  

  

  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param JsonComponentCS
   * 
   * @return JsonFirstStepSC
   */
//  def toComponentIn(jsonComponentIn : JsonComponentIn): ComponentIn = {
//    ComponentIn(
//        jsonComponentIn.params.stepId
//        , jsonComponentIn.params.nameToShow
//        , jsonComponentIn.params.kind
//    )
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param ComponentSC
   * 
   * @return JsonComponentSC
   */
//  def toJsonComponentOut(componentOut: ComponentOut): JsonComponentOut = {
//    JsonComponentOut(
//        result = ComponentResult(
//            componentOut.componentId
//            , componentOut.status
//            , componentOut.message
//        )
//    )
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param JsonConnectionComponentToStepCS
   * 
   * @return ConnectionComponentToStepCS
   */
//  def toConnectionComponentToStepIn(
//      jsonConnectionComponentToStepIn: JsonConnectionComponentToStepIn): ConnectionComponentToStepIn = {
//    ConnectionComponentToStepIn(
//        jsonConnectionComponentToStepIn.params.componentId
//        , jsonConnectionComponentToStepIn.params.stepId
//    )
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param ConnectionComponentToStepSC
   * 
   * @return JsonConnectionComponentToStepSC
   */
//  def toJsonConnectionComponentToStepOut(
//          connectionComponentToStepOut: ConnectionComponentToStepOut
//      ): JsonConnectionComponentToStepOut = {
//    JsonConnectionComponentToStepOut(
//        result = ConnectionComponentToStepResult(
//            connectionComponentToStepOut.status,
//            connectionComponentToStepOut.message
//        )
//    )
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param ConfigTreeOut
   * 
   * @return JsonConfigTreeOut
   */
  def toJsonConfigTreeOut(configTreeBO: ConfigTreeBO): JsonConfigTreeOut = {
    
    configTreeBO.status.getConfigTree match {
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
                        configTreeBO.status.getConfigTree.status,
                        configTreeBO.status.getConfigTree.message
                    )),
                    Some(JsonStatus(
                        configTreeBO.status.common.status,
                        configTreeBO.status.common.message
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
                        configTreeBO.status.getConfigTree.status,
                        configTreeBO.status.getConfigTree.message
                    )),
                    Some(JsonStatus(
                        configTreeBO.status.common.status,
                        configTreeBO.status.common.message
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
                        configTreeBO.status.getConfigTree.status,
                        configTreeBO.status.getConfigTree.message
                    )),
                    Some(JsonStatus(
                        configTreeBO.status.common.status,
                        configTreeBO.status.common.message
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
   * @version 0.1.5
   * 
   * @param Set[Option[Component]]
   * 
   * @return Set[JsonConfigTreeComponent]
   */
  def getJsonConfigTreeComponents(components: Set[Option[ComponentForConfigTreeBO]]): Set[JsonConfigTreeComponent] = {
    
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
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param DependencyOut
   * 
   * @return JsonDependencyOut
   */
//  def toJsonDependencyOut(dependencyOut: DependencyOut): JsonDependencyOut = {
//    JsonDependencyOut(
//        result = JsonDependencyResult(
//            dependencyOut.componentFromId,
//            dependencyOut.componentToId,
//            dependencyOut.dependencyId,
//            dependencyOut.dependencyType,
//            dependencyOut.visualization,
//            dependencyOut.nameToShow,
//            dependencyOut.status,
//            dependencyOut.message
//        )
//    )
//  }
  
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param JsonDependencyIn
   * 
   * @return DependencyIn
   */
//  def toDependencyIn(jsonDependencyIn: JsonDependencyIn): DependencyIn = {
//    DependencyIn(
//        jsonDependencyIn.params.componentFromId,
//        jsonDependencyIn.params.componentToId,
//        jsonDependencyIn.params.dependencyType,
//        jsonDependencyIn.params.visualization,
//        jsonDependencyIn.params.nameToShow
//    )
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param JsonDependencyIn
   * 
   * @return DependencyIn
   */
//  def toVisualProposalForAdditionalStepsInOneLevelIn(
//      jsonVisualProposalForAdditionalStepsInOneLevelIn: JsonVisualProposalForAdditionalStepsInOneLevelIn): 
//      VisualProposalForAdditionalStepsInOneLevelIn = {
//    
//     VisualProposalForAdditionalStepsInOneLevelIn(
//         jsonVisualProposalForAdditionalStepsInOneLevelIn.params.selectedVisualProposal
//     )
//  }
}
