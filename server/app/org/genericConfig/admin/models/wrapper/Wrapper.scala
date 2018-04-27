package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.models.json.step.JsonStepIn
import org.genericConfig.admin.models.json.step.JsonStepOut
import org.genericConfig.admin.models.wrapper.step.StepIn
import org.genericConfig.admin.models.wrapper.step.StepOut
import org.genericConfig.admin.models.wrapper.dependency.DependencyOut
import org.genericConfig.admin.models.json.step.JsonStepResult
import org.genericConfig.admin.models.json.step.JsonDependencyForAdditionalStepsInOneLevel
import org.genericConfig.admin.models.json.step.JsonFirstStepIn
import org.genericConfig.admin.models.wrapper.step.FirstStepIn
import org.genericConfig.admin.models.json.step.JsonFirstStepOut
import org.genericConfig.admin.models.wrapper.step.FirstStepOut
import org.genericConfig.admin.models.json.step.JsonFirstStepResult
import org.genericConfig.admin.models.json.component.JsonComponentIn
import org.genericConfig.admin.models.wrapper.component.ComponentIn
import org.genericConfig.admin.models.json.component.JsonComponentOut
import org.genericConfig.admin.models.json.component.ComponentResult
import org.genericConfig.admin.models.wrapper.component.ComponentOut
import org.genericConfig.admin.models.json.connectionComponentToStep.JsonConnectionComponentToStepIn
import org.genericConfig.admin.models.wrapper.connectionComponentToStep.ConnectionComponentToStepIn
import org.genericConfig.admin.models.wrapper.connectionComponentToStep.ConnectionComponentToStepOut
import org.genericConfig.admin.models.json.connectionComponentToStep.JsonConnectionComponentToStepOut
import org.genericConfig.admin.models.json.connectionComponentToStep.ConnectionComponentToStepResult

import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeComponent
import org.genericConfig.admin.models.json.dependency.JsonDependencyOut
import org.genericConfig.admin.models.json.dependency.JsonDependencyResult
import org.genericConfig.admin.models.json.dependency.JsonDependencyIn
import org.genericConfig.admin.models.wrapper.dependency.DependencyIn
import org.genericConfig.admin.models.json.step.JsonVisualProposalForAdditionalStepsInOneLevelIn
import org.genericConfig.admin.models.wrapper.step.VisualProposalForAdditionalStepsInOneLevelIn
import org.genericConfig.admin.shared.configTree.json._
import org.genericConfig.admin.shared.registration.bo._
import org.genericConfig.admin.shared.registration.json._
import org.genericConfig.admin.shared.common.json._
import org.genericConfig.admin.shared.login.json._
import org.genericConfig.admin.shared.config.json._
import org.genericConfig.admin.shared.config.bo._
import org.genericConfig.admin.shared.login.bo._
import org.genericConfig.admin.shared.configTree.bo._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.09.2017
 */



trait Wrapper {
  
  def calculateHash() = {
    ???
  }
  
  def getRid(hash: String) = {
    ???
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
  def toStepIn(jsonStepIn: JsonStepIn): StepIn = {
    StepIn(
        jsonStepIn.params.componentId,
        jsonStepIn.params.nameToShow,
        jsonStepIn.params.kind,
        jsonStepIn.params.selectionCriterium.min,
        jsonStepIn.params.selectionCriterium.max
    )
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
  def toJsonStepOut(stepOut: StepOut): JsonStepOut = {
    JsonStepOut(result = JsonStepResult(
        stepOut.stepId,
        stepOut.status,
        stepOut.message,
        stepOut.visualProposalForAdditionalStepInOneLevel,
        stepOut.dependenciesForAdditionalStepsInOneLevel.map(d => {
          JsonDependencyForAdditionalStepsInOneLevel(
              d.componentFromId,
              d.componentToId,
              d.dependencyId,
              d.dependencyType,
              d.visualization,
              d.nameToShow
          )
        })
    ))
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param RegistrationSC
   * 
   * @return JsonRegistrationSC
   */
  def toJsonRegistrationOut(registrationBO: RegistrationBO): JsonRegistrationOut = {
    JsonRegistrationOut(
        result = RegistrationResult(
          registrationBO.adminId,
          registrationBO.username,
          JsonRegistrationStatus(
              registrationBO.status.addUser match {
                case Some(adduser) => Some(JsonStatus(adduser.status, adduser.message))
                case None => None
              },
              registrationBO.status.common match {
                case Some(common) => Some(JsonStatus(common.status, common.message))
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
   * @param JsonLoginCS
   * 
   * @return LoginCS
   */
//  def toLoginIn(jsonLoginIn: JsonLoginIn): LoginIn = {
//    LoginIn(
//        jsonLoginIn.params.username,
//        jsonLoginIn.params.password
//    )
//  }
  
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
   * @param JsonCreateConfigCS
   * 
   * @return CreateConfigCS
   */
//  def toCreateConfigIn(jsonCreateConfigIn: JsonCreateConfigIn): CreateConfigIn = {
//    CreateConfigIn(
//        jsonCreateConfigIn.params.adminId,
//        jsonCreateConfigIn.params.configUrl
//    )
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param CreateConfigSC
   * 
   * @return JsonCreateConfigSC
   */
  def toJsonCreateConfigOut(configBO: ConfigBO): JsonCreateConfigOut = {
    JsonCreateConfigOut(
        result = JsonCreateConfigResult(
            configBO.configs match {
              case List() => ""
              case _ => {
                configBO.configs.head.configId
              }
            },
            JsonConfigStatus(
                configBO.status.addConfig match {
                  case Some(addConfig) => 
                    Some(JsonStatus(
                      addConfig.status,
                      addConfig.message
                    ))
                  case None => None
                },
                configBO.status.getConfigs match {
                  case Some(getConfigs) => 
                    Some(JsonStatus(
                      getConfigs.status,
                      getConfigs.message
                    ))
                  case None => None
                },
                configBO.status.deleteConfig match {
                  case Some(deleteConfig) => 
                    Some(JsonStatus(
                      deleteConfig.status,
                      deleteConfig.message
                    ))
                  case None => None
                },
                configBO.status.updateConfig match {
                  case Some(updateConfig) => 
                    Some(JsonStatus(
                      updateConfig.status,
                      updateConfig.message
                    ))
                  case None => None
                },
                configBO.status.common match {
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
   * @param JsonFirstStepCS
   * 
   * @return FirstStepCS
   */
  def toFirstStepIn(jsonFirstStepIn: JsonFirstStepIn): FirstStepIn = {
    FirstStepIn(
        jsonFirstStepIn.params.configId,
        jsonFirstStepIn.params.nameToShow,
        jsonFirstStepIn.params.kind,
        jsonFirstStepIn.params.selectionCriterium.min,
        jsonFirstStepIn.params.selectionCriterium.max
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param LoginSC
   * 
   * @return JsonFirstStepSC
   */
  def toJsonFirstStepOut(firstStepOut: FirstStepOut): JsonFirstStepOut = {
    JsonFirstStepOut(
        result = JsonFirstStepResult(
            firstStepOut.stepId,
            firstStepOut.status,
            firstStepOut.message
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
  def toComponentIn(jsonComponentIn : JsonComponentIn): ComponentIn = {
    ComponentIn(
        jsonComponentIn.params.stepId
        , jsonComponentIn.params.nameToShow
        , jsonComponentIn.params.kind
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param ComponentSC
   * 
   * @return JsonComponentSC
   */
  def toJsonComponentOut(componentOut: ComponentOut): JsonComponentOut = {
    JsonComponentOut(
        result = ComponentResult(
            componentOut.componentId
            , componentOut.status
            , componentOut.message
        )
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param JsonConnectionComponentToStepCS
   * 
   * @return ConnectionComponentToStepCS
   */
  def toConnectionComponentToStepIn(
      jsonConnectionComponentToStepIn: JsonConnectionComponentToStepIn): ConnectionComponentToStepIn = {
    ConnectionComponentToStepIn(
        jsonConnectionComponentToStepIn.params.componentId
        , jsonConnectionComponentToStepIn.params.stepId
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param ConnectionComponentToStepSC
   * 
   * @return JsonConnectionComponentToStepSC
   */
  def toJsonConnectionComponentToStepOut(
          connectionComponentToStepOut: ConnectionComponentToStepOut
      ): JsonConnectionComponentToStepOut = {
    JsonConnectionComponentToStepOut(
        result = ConnectionComponentToStepResult(
            connectionComponentToStepOut.status,
            connectionComponentToStepOut.message
        )
    )
  }
  
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
    
    configTreeBO.configTree match {
      case Some(configTree) => {
        JsonConfigTreeOut(
            result = JsonConfigTreeResult(
                Some(JsonConfigTreeStep(
                    configTree.stepId,
                    configTree.kind,
                    getJsonConfigTreeComponents(configTree.components)
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
      case None => {
        JsonConfigTreeOut(
            result = JsonConfigTreeResult(
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
   * @param JsonConfigTreeIn
   * 
   * @return ConfigTreeIn
   */
  def toConfigTreeIn(jsonConfigTreeCS: JsonConfigTreeIn) = {
//    ConfigTreeIn(
//        jsonConfigTreeCS.params.configId
//    )
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
  def toJsonDependencyOut(dependencyOut: DependencyOut): JsonDependencyOut = {
    JsonDependencyOut(
        result = JsonDependencyResult(
            dependencyOut.componentFromId,
            dependencyOut.componentToId,
            dependencyOut.dependencyId,
            dependencyOut.dependencyType,
            dependencyOut.visualization,
            dependencyOut.nameToShow,
            dependencyOut.status,
            dependencyOut.message
        )
    )
  }
  
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param JsonDependencyIn
   * 
   * @return DependencyIn
   */
  def toDependencyIn(jsonDependencyIn: JsonDependencyIn): DependencyIn = {
    DependencyIn(
        jsonDependencyIn.params.componentFromId,
        jsonDependencyIn.params.componentToId,
        jsonDependencyIn.params.dependencyType,
        jsonDependencyIn.params.visualization,
        jsonDependencyIn.params.nameToShow
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param JsonDependencyIn
   * 
   * @return DependencyIn
   */
  def toVisualProposalForAdditionalStepsInOneLevelIn(
      jsonVisualProposalForAdditionalStepsInOneLevelIn: JsonVisualProposalForAdditionalStepsInOneLevelIn): 
      VisualProposalForAdditionalStepsInOneLevelIn = {
    
     VisualProposalForAdditionalStepsInOneLevelIn(
         jsonVisualProposalForAdditionalStepsInOneLevelIn.params.selectedVisualProposal
     )
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
    val cBOs: List[JsonConfig] = configBO.configs map { configBO => {
      JsonConfig(
          configBO.configId,
          configBO.configUrl
      )
    }}
    JsonGetConfigsOut(
        result = JsonGetConfigsResult(
            configBO.admiId,
            cBOs,
            JsonConfigStatus(
                configBO.status.addConfig match {
                  case Some(status) => 
                    Some(JsonStatus(
                        status.status,
                        status.message
                    ))
                  case None => None
                },
                configBO.status.getConfigs match {
                  case Some(getConfigs) => 
                    Some(JsonStatus(
                      getConfigs.status,
                      getConfigs.message
                    ))
                  case None => None
                },
                configBO.status.deleteConfig match {
                  case Some(deleteConfig) => 
                    Some(JsonStatus(
                      deleteConfig.status,
                      deleteConfig.message
                    ))
                  case None => None
                },
                configBO.status.updateConfig match {
                  case Some(updateConfig) => 
                    Some(JsonStatus(
                      updateConfig.status,
                      updateConfig.message
                    ))
                  case None => None
                },
                configBO.status.common match {
                  case Some(status) => 
                    Some(JsonStatus(
                        status.status,
                        status.message
                    ))
                  case None => None
                }
            )
        )
    )
  }
}
