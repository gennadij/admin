package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.configTree.json._
import org.genericConfig.admin.shared.common.json._
import org.genericConfig.admin.shared.login.json._
import org.genericConfig.admin.shared.config.json._
import org.genericConfig.admin.shared.config.bo._
import org.genericConfig.admin.shared.login.bo._
import org.genericConfig.admin.shared.configTree.bo._
import org.genericConfig.admin.shared.step.json._
import org.genericConfig.admin.shared.step.bo._
import org.genericConfig.admin.shared.configTree.status._
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.shared.user.json._
import org.genericConfig.admin.shared.user.bo.UserBO

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
   * @param 
   * 
   * @return 
   */
  def toRegistrationBO(jsonUserIn: JsonUserIn): UserBO = {
    new WrapperUser().toUserBO(jsonUserIn)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return
   */
  def toJsonUserOut(registrationBO: UserBO): JsonUserOut = {
    new WrapperUser().toJsonUserOut(registrationBO)
  }
  
  
   /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param LoginSC
   * 
   * @return JsonLoginSC
   */
  def toJsonLoginOut(loginBO: LoginBO): JsonLoginOut = {
    JsonLoginOut(
        result = JsonLoginResult(
            loginBO.adminId,
            loginBO.username,
            loginBO.config.configs map (config => { JsonConfig(
                config.configId,
                config.configUrl
            )}),
            JsonLoginStatus(
                Some(JsonStatus(
                    loginBO.status.userLogin.status,
                    loginBO.status.userLogin.message
                )),
                Some(JsonStatus(
                    loginBO.status.common.status,
                    loginBO.status.common.message
                )),
            )
        )
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param CreateConfigSC
   * 
   * @return JsonCreateConfigSC
   */
  def toJsonAddConfigOut(configBO: ConfigBO): JsonAddConfigOut = {
    new WrapperConfig(configBO).toJsonAddConfigOut
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param 
   * 
   * @return 
   */
  
  def toJsonGetConfigsOut(configBO:ConfigBO): JsonGetConfigsOut = {
    new WrapperConfig(configBO).toJsonGetConfigsOut
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param JsonFirstStepCS
   * 
   * @return FirstStepCS
   */
  def toJsonDeleteConfigOut(configBO: ConfigBO): JsonDeleteConfigOut = {
    new WrapperConfig(configBO).toJsonDeleteConfigOut
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param JsonFirstStepCS
   * 
   * @return FirstStepCS
   */
  def toJsonUpdateConfigOut(configBO: ConfigBO): JsonUpdateConfigOut = {
    new WrapperConfig(configBO).toJsonUpdateConfigOut
  }
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param JsonStepCS
   * 
   * @return StepCS
   */
  def toStepBO(jsonStepIn: JsonStepIn): StepBO = {
    jsonStepIn.json match {
      case json if json == JsonNames.ADD_FIRST_STEP => 
        StepBO(
            Some(jsonStepIn.json),
            Some(jsonStepIn.params.configId), //configId
            None,//componentId
            Some(jsonStepIn.params.nameToShow), // nameToShow
            Some(jsonStepIn.params.kind), // kind
            Some(jsonStepIn.params.selectionCriterium.min), // selectionCriteriumMin
            Some(jsonStepIn.params.selectionCriterium.max), // selectionCriteriumMax
            None, // stepId
            None // status
        )
      case json if json == JsonNames.DELETE_FIRST_STEP || json == JsonNames.DELETE_STEP => 
        StepBO(
            Some(jsonStepIn.json), 
            None, //configId 
            None,//componentId
            None, // nameToShow
            None, // kind
            None, // selectionCriteriumMin
            None, // selectionCriteriumMax
            Some(jsonStepIn.params.stepId), // stepId
            None // status
        )
      case json if json == JsonNames.UPDATE_FIRST_STEP || json == JsonNames.UPDATE_STEP  => 
        StepBO(
            Some(jsonStepIn.json),
            None, //configId
            None,//componentId
            Some(jsonStepIn.params.nameToShow), // nameToShow
            Some(jsonStepIn.params.kind), // kind
            Some(jsonStepIn.params.selectionCriterium.min), // selectionCriteriumMin
            Some(jsonStepIn.params.selectionCriterium.max), // selectionCriteriumMax
            Some(jsonStepIn.params.stepId), // stepId
            None // status
        )
      case json if json == JsonNames.ADD_STEP =>
        StepBO(
            Some(jsonStepIn.json),
            None, //configId
            Some(jsonStepIn.params.componentId),//componentId
            Some(jsonStepIn.params.nameToShow), // nameToShow
            Some(jsonStepIn.params.kind), // kind
            Some(jsonStepIn.params.selectionCriterium.min), // selectionCriteriumMin
            Some(jsonStepIn.params.selectionCriterium.max), // selectionCriteriumMax
            None, // stepId
            None // status
        )
    }
  }
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param StepSC
   * 
   * @return JsonStepSC
   */
  def toJsonStepOut(stepBO: StepBO): JsonStepOut = {
    stepBO.json.get match {
      case json if json == JsonNames.ADD_FIRST_STEP => 
        createJsonStepOut(stepBO, json)
      case json if json == JsonNames.DELETE_FIRST_STEP => 
        createJsonStepOut(stepBO, json)
      case json if json == JsonNames.UPDATE_FIRST_STEP => 
       createJsonStepOut(stepBO, json)
      case json if json == JsonNames.ADD_STEP =>
       createJsonStepOut(stepBO, json)
      case json if json == JsonNames.DELETE_STEP => 
        createJsonStepOut(stepBO, json)
      case json if json == JsonNames.UPDATE_STEP => 
        createJsonStepOut(stepBO, json)
    }
  }
  
  private def createJsonStepOut(stepBO: StepBO, json: String): JsonStepOut = {
    JsonStepOut(
        json = json,
        result = JsonStepResult(
            stepBO.stepId match {
              case Some(stepId) => Some(stepId)
              case None => None
            },
            Set(),
            Set(),
            JsonStepStatus(
                stepBO.status.get.addStep match {
                  case Some(addStep) => 
                    Some(JsonStatus(
                      addStep.status,
                      addStep.message
                    ))
                  case None => None
                },
                stepBO.status.get.deleteStep match {
                  case Some(deleteStep) => 
                    Some(JsonStatus(
                      deleteStep.status,
                      deleteStep.message
                    ))
                  case None => None
                },
                stepBO.status.get.updateStep match {
                  case Some(updateStep) => 
                    Some(JsonStatus(
                      updateStep.status,
                      updateStep.message
                    ))
                  case None => None
                },
                stepBO.status.get.appendStep match {
                  case Some(appendStep) => 
                    Some(JsonStatus(
                      appendStep.status,
                      appendStep.message
                    ))
                  case None => None
                },
                stepBO.status.get.common match {
                  case Some(common) => 
                    Some(JsonStatus(
                      common.status,
                      common.message
                    ))
                  case None => None
                }
            )
        )
    )
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
