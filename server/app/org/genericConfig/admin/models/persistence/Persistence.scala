package org.genericConfig.admin.models.persistence

import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.common.Error
import org.genericConfig.admin.models.persistence.orientdb.{Graph, PropertyKeys}
import org.genericConfig.admin.models.wrapper.step.VisualProposalForAdditionalStepsInOneLevelIn
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.component.bo.ComponentBO
import org.genericConfig.admin.shared.config.bo.ConfigBO
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigResultDTO}
import org.genericConfig.admin.shared.configTree.bo._
import org.genericConfig.admin.shared.step.bo._
import org.genericConfig.admin.shared.step.json.JsonDependencyForAdditionalStepsInOneLevel
import org.genericConfig.admin.shared.step.status._
import org.genericConfig.admin.shared.user.{UserDTO, UserResultDTO}


/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.11.2016
  */
object Persistence {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param username : String, password: String
    * @return UserBO
    */
  def addUser(username: String, password: String): UserDTO = {
    val (vUser: Option[OrientVertex], error : Option[Error]) =
      Graph.addUser(username, password)

    error match {
      case None =>
        UserDTO(
          action = Actions.ADD_USER,
          params = None,
          result = Some(UserResultDTO(
            userId = Some(vUser.get.getIdentity.toString),
            username = Some(vUser.get.getProperty(PropertyKeys.USERNAME).toString),
            errors = None
          ))
        )
      case _ =>
        UserDTO(
          action = Actions.ADD_USER,
          params = None,
          result = Some(UserResultDTO(
            userId = None,
            username = None,
            errors = Some(List(ErrorDTO(
              name = error.get.name,
              message = error.get.message,
              code = error.get.code
            )))
          ))
        )
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param username : String, password: String
   * @return UserDTO
   */
  def deleteUser(username: String, password: String): UserDTO = {
    val (vUser: Option[OrientVertex], error : Option[Error]) =
      Graph.deleteUser(username, password)

    error match {
      case None =>
        UserDTO(
          action = Actions.DELETE_USER,
          params = None,
          result = Some(UserResultDTO(
            userId = None,
            username = Some(username),
            errors = None
          ))
        )
      case _ =>
        UserDTO(
          action = Actions.DELETE_USER,
          params = None,
          result = Some(UserResultDTO(
            userId = None,
            username = None,
            errors = Some(List(ErrorDTO(
              name = error.get.name,
              message = error.get.message,
              code = error.get.code
            )))
          ))
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param username : String, password: String
    * @return UserBO
    */
  def getUser(username: String, password: String) : UserDTO = {
    val (vUser: Option[OrientVertex], error: Option[Error]) =
      Graph.getUser(username, password)

    error match {
      case None =>
        UserDTO(
          action = Actions.GET_USER,
          params = None,
          result = Some(UserResultDTO(
            userId = Some(vUser.get.getIdentity.toString),
            username = Some(vUser.get.getProperty(PropertyKeys.USERNAME).toString),
            errors = None
          ))
        )
      case _ =>
        UserDTO(
          action = Actions.GET_USER,
          params = None,
          result = Some(UserResultDTO(
            userId = None,
            username = None,
            errors = Some(List(ErrorDTO(
              name = error.get.name,
              message = error.get.message,
              code = error.get.code
            )
            ))
          ))
        )
    }
  }

  def updateUsername(oldUsername: String, newUsername: String) : UserDTO = {
    val (vUser: Option[OrientVertex], error: Option[Error]) =
      Graph.updateUserName(oldUsername, newUsername)

    error match {
      case None =>
        UserDTO(
          action = Actions.UPDATE_USER,
          params = None,
          result = Some(UserResultDTO(
            userId = None,
            username = Some(newUsername),
            errors = None
          ))
        )
      case _ =>
        UserDTO(
          action = Actions.UPDATE_USER,
          params = None,
          result = Some(UserResultDTO(
            userId = None,
            username = None,
            errors = Some(List(ErrorDTO(
              name = error.get.name,
              message = error.get.message,
              code = error.get.code
            )
            ))
          ))
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String
    * @return String, Status
    */
  def getUserId(configId: String): (String, ErrorDTO) = {
    ???
//    Graph.getUserId(configId)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId : String, configUrl: String
    * @return ConfigDTO
    */
  def addConfig(userId: String, configUrl: String): ConfigDTO = {

    val (vConfig, error): (Option[OrientVertex], Option[Error]) =
      Graph.addConfig(configUrl)
    error match {
      case None =>
        ConfigDTO(
          action = "",
          params = None,
          result = Some(ConfigResultDTO(
            userId = Some(userId),
            configId = Some(vConfig.get.getIdentity.toString),
            configs = None,
            errors = None
          ))
        )
      case Some(error) =>
        ConfigDTO(
          action = "",
          params = None,
          result = Some(ConfigResultDTO(
            userId = Some(userId),
            configId = None,
            configs = None,
            errors = Some(List(ErrorDTO(
              name = error.name,
              message = error.message,
              code = error.code
            )))
          )
        ))
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId : String, configId: String
    * @return Status
    */
  def appendConfigTo(userId: String, configId: String): Error = {
    Graph.appendConfigTo(userId, configId)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId : String
    * @return ConfigBO
    */
  def getConfigs(userId: String): ConfigBO = {
    ???
//    val (vConfigs, statusGetConfig, statusCommon): (Option[List[OrientVertex]], StatusGetConfigs, Error) =
//      Graph.getConfigs(userId)
//    statusGetConfig match {
//      case GetConfigsSuccess() =>
//        ConfigBO(
//          Some(userId),
//          Some(vConfigs.get map (vConfig => {
//            Configuration(
//              Some(vConfig.getIdentity.toString),
//              Some(vConfig.getProperty(PropertyKey.CONFIG_URL)))
//          })),
//          Some(StatusConfig(
//            None, //addConfig
//            Some(GetConfigsSuccess()), //getConfigs
//            None, //deleteConfig
//            None, //updateConfig
//            Some(Success())
//          )
//          ))
//      case GetConfigsEmpty() => ConfigBO(
//        Some(userId), None,
//        Some(StatusConfig(None, Some(GetConfigsEmpty()), None, None, Some(Success()))))
//      case GetConfigsError() =>
//        ConfigBO(Some(userId), None, Some(StatusConfig(None, Some(GetConfigsError()), None, None, Some(statusCommon))))
//    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String, configUrl: String
    * @return ConfigBO
    */
  def deleteConfig(configId: String, configUrl: String): ConfigBO = {
???
//    val (userId, status): (String, Error) = Graph.getUserId(configId)
//    val (statusDeleteConfig, statusCommon): (StatusDeleteConfig, Error) = Graph.deleteConfig(configId, configUrl: String)
//
//    status match {
//      case Success() =>
//        ConfigBO(
//          Some(userId), None,
//          Some(StatusConfig(
//            None, //addConfig: Option[StatusAddConfig],
//            None, //getConfigs: Option[StatusGetConfigs],
//            Some(statusDeleteConfig), //deleteConfig: Option[StatusDeleteConfig],
//            None, //updateConfig: Option[StatusUpdateConfig],
//            Some(statusCommon) //common: Option[Status
//          )
//          ))
//      case _ =>
//        ConfigBO(
//          Some(userId), None,
//          Some(StatusConfig(
//            None, //addConfig: Option[StatusAddConfig],
//            None, //getConfigs: Option[StatusGetConfigs],
//            Some(DeleteConfigError()), //deleteConfig: Option[StatusDeleteConfig],
//            None, //updateConfig: Option[StatusUpdateConfig],
//            Some(status) //common: Option[Status
//          )
//          )
//        )
//    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String, configUrl: String
    * @return ConfigBO
    */
  def updateConfig(configId: String, configUrl: String): ConfigBO = {
    ???
//    val (userId, status): (String, Error) = Graph.getUserId(configId)
//    val (vUpdatedConfig, statusUpdateConfig, statusCommon): (Option[OrientVertex], StatusUpdateConfig, Error) =
//      Graph.updateConfig(configId: String, configUrl: String)
//    status match {
//      case Success() =>
//        ConfigBO(
//          Some(userId),
//          Some(List(Configuration(
//            configId = Some(vUpdatedConfig.get.getIdentity.toString),
//            configUrl = Some(vUpdatedConfig.get.getProperty(PropertyKey.CONFIG_URL))
//          ))),
//          Some(StatusConfig(
//            None, //addConfig: Option[StatusAddConfig],
//            None, //getConfigs: Option[StatusGetConfigs],
//            None, //deleteConfig: Option[StatusDeleteConfig],
//            Some(statusUpdateConfig), //updateConfig: Option[StatusUpdateConfig],
//            Some(statusCommon) //common: Option[Status
//          )
//          ))
//      case _ =>
//        ConfigBO(
//          Some(userId), None, Some(StatusConfig(
//            None, //addConfig: Option[StatusAddConfig],
//            None, //getConfigs: Option[StatusGetConfigs],
//            None, //deleteConfig: Option[StatusDeleteConfig],
//            Some(UpdateConfigError()), //updateConfig: Option[StatusUpdateConfig],
//            Some(status) //common: Option[Status
//          )
//          )
//        )
//    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configTreeBO : ConfigTreeBO
    * @return ConfigTreeBO
    */
  def getConfigTree(configTreeBO: ConfigTreeBO): ConfigTreeBO = {
    ???
//    val (configTree, statusGetConfigTree, commonStatus): (Option[StepForConfigTreeBO], StatusGetConfigTree, Error) =
//      Graph.getConfigTree(configTreeBO.configId.get)
//
//    val (userId: String, status: Error) = Persistence.getUserId(configTreeBO.configId.get)
//
//    statusGetConfigTree match {
//      case GetConfigTreeSuccess() =>
//        status match {
//          case Success() =>
//            ConfigTreeBO(
//              Some(userId),
//              configTreeBO.configId,
//              configTree,
//              Some(StatusConfigTree(
//                GetConfigTreeSuccess(),
//                Success()
//              )
//              ))
//          case _ =>
//            ConfigTreeBO(
//              None,
//              None,
//              None,
//              Some(StatusConfigTree(
//                GetConfigTreeError(),
//                status
//              )
//              ))
//        }
//      case GetConfigTreeEmpty() =>
//        status match {
//          case Success() =>
//            ConfigTreeBO(
//              Some(userId),
//              configTreeBO.configId,
//              None,
//              Some(StatusConfigTree(
//                GetConfigTreeEmpty(),
//                Success()
//              )
//              ))
//          case _ =>
//            ConfigTreeBO(
//              None,
//              None,
//              None,
//              Some(StatusConfigTree(
//                GetConfigTreeError(),
//                status
//              )
//              ))
//        }
//      case GetConfigTreeError() => ConfigTreeBO(
//        None,
//        None,
//        None,
//        Some(StatusConfigTree(
//          GetConfigTreeError(),
//          commonStatus
//        )
//        ))
//    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  def addStep(stepBO: StepBO): StepBO = {
    ???
//    val (vStep: Option[OrientVertex], addStepStatus: StatusAddStep, commonStatus: Error) =
//      Graph.addStep(stepBO)
//
//    addStepStatus match {
//      case AddStepSuccess() =>
//        StepBO(
//          appendToId = stepBO.appendToId,
//          stepId = Some(vStep.get.getIdentity.toString),
//          status = Some(StatusStep(
//            addStep = Some(AddStepSuccess()),
//            common = Some(Success())
//          ))
//        )
//      case AddStepError() =>
//        StepBO(
//          stepId = None,
//          status = Some(StatusStep(
//            addStep = Some(AddStepError()),
//            common = Some(commonStatus)
//          ))
//        )
//      case AddStepAlreadyExist() =>
//        StepBO(
//          stepId = None,
//          status = Some(StatusStep(
//            addStep = Some(AddStepAlreadyExist()),
//            common = Some(commonStatus)
//          ))
//        )
//      case AddStepDefectComponentOrConfigId() =>
//        StepBO(
//          stepId = None,
//          status = Some(StatusStep(
//            addStep = Some(AddStepDefectComponentOrConfigId()),
//            common = Some(commonStatus)
//          ))
//        )
//    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param id : String, stepId: String
    * @return (StatusAppendStep, Status)
    */
  def appendStepTo(id: String, stepId: String): (StatusAppendStep, ErrorDTO) = {
  ???
    //    Graph.appendStepTo(id, stepId)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepId : String
    * @return (StatusDeleteStep, Status)
    */
  def deleteStep(stepId: String): (StatusDeleteStep, ErrorDTO) = {
    ???
//    Graph.deleteStep(stepId)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  def updateStep(stepBO: StepBO): StepBO = {
    ???
//    val (updateStepStatus: StatusUpdateStep, commonStatus: Error) = Graph.updateStep(stepBO)
//
//    updateStepStatus match {
//      case UpdateStepSuccess() =>
//        StepBO(
//          json = Some(JsonNames.UPDATE_STEP),
//          status = Some(StatusStep(
//            updateStep = Some(UpdateStepSuccess()),
//            common = Some(Success())
//          )
//          ))
//      case UpdateStepError() =>
//
//        StepBO(
//          json = Some(JsonNames.UPDATE_STEP),
//          status = Some(StatusStep(
//            updateStep = Some(UpdateStepError()),
//            common = Some(commonStatus)
//          )
//          )
//        )
//    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentBO : ComponentBO
    * @return ComponentBO
    */
  def addComponent(componentBO: ComponentBO): ComponentBO = {
    ???
//    val (vComponent, statusAddComponnet, statusCommon): (Option[OrientVertex], StatusAddComponent, Error) =
//      Graph.addComponent(componentBO)
//
//    statusAddComponnet match {
//      case AddComponentSuccess() =>
//        val componentBOForAppend =
//          ComponentBO(stepId = componentBO.stepId, componentId = Some(vComponent.get.getIdentity.toString))
//        val (statusAppendComponent, statusCommon): (StatusAppendComponent, Error) =
//          Graph.appendComponentToStep(componentBOForAppend)
//        statusAppendComponent match {
//          case AppendComponentSuccess() =>
//            ComponentBO(
//              stepId = componentBO.stepId,
//              componentId = Some(vComponent.get.getIdentity.toString),
//              nameToShow = Some(vComponent.get.getProperty(PropertyKeys.NAME_TO_SHOW)),
//              kind = Some(vComponent.get.getProperty(PropertyKeys.KIND)),
//              status = Some(StatusComponent(
//                addComponent = Some(AddComponentSuccess()),
//                appendComponent = Some(AppendComponentSuccess()),
//                common = Some(statusCommon)
//              ))
//            )
//          case AppendComponentError() =>
//            ComponentBO(
//              status = Some(StatusComponent(
//                addComponent = Some(AddComponentError()),
//                appendComponent = Some(AppendComponentError()),
//                common = Some(statusCommon)
//              ))
//            )
//        }
//      case AddComponentError() =>
//        ComponentBO(
//          status = Some(StatusComponent(
//            addComponent = Some(AddComponentError()),
//            appendComponent = Some(AppendComponentError()),
//            common = Some(statusCommon)
//          ))
//        )
//    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentBO : ComponentBO
    * @return ComponentBO
    */
  def deleteComponent(componentBO: ComponentBO): ComponentBO = {
    ???
//    val (statusDeleteComponent, statusCommon): (StatusDeleteComponent, Error) = Graph.deleteComponent(componentBO)
//    ComponentBO(status = Some(StatusComponent(
//      deleteComponent = Some(statusDeleteComponent), common = Some(statusCommon)
//    )))
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentBO : ComponentBO
    * @return ComponentBO
    */
  def updateComponent(componentBO: ComponentBO): ComponentBO = {
    ???
//    val (vComponent, statusUpdateComponnet, statusCommon): (Option[OrientVertex], StatusUpdateComponent, Error) =
//      Graph.updateComponent(componentBO)
//
//    statusUpdateComponnet match {
//      case UpdateComponentSuccess() =>
//        ComponentBO(
//          json = Some(JsonNames.UPDATE_COMPONENT),
//          componentId = componentBO.componentId,
//          nameToShow = componentBO.nameToShow,
//          kind = componentBO.kind,
//          status = Some(StatusComponent(
//            updateComponent = Some(UpdateComponentSuccess()),
//            common = Some(Success())
//          )
//          ))
//      case UpdateComponentError() =>
//        ComponentBO(
//          json = Some(JsonNames.UPDATE_COMPONENT),
//          status = Some(StatusComponent(
//            updateComponent = Some(UpdateComponentError()),
//            common = Some(statusCommon)
//          ))
//        )
//    }
  }


  //  /**
  //    * @author Gennadi Heimann
  //    * @version 0.1.5
  //    *
  //    *          Nach der pruefung in der Methode createStep
  //    *          wird hier der zusaetzlicher Schritt in der Level ComponentStep angelegt
  //    * @param StepSC
  //    * @return StepCS
  //    */
  //  def createAditionalStepInLevelCS(stepCS: StepIn) = {
  //
  //    val stepSC: StepOut = StepVertex.step(stepCS)
  //
  //    stepSC.status match {
  //      case StatusSuccessfulStepCreated.status => {
  //        val eHasStep: OrientEdge = HasStepEdge.hasStep(stepCS, stepSC)
  //        eHasStep match {
  //          case null => {
  //            StepOut(
  //              "",
  //              StatusErrorFaultyStepId.status,
  //              StatusErrorFaultyStepId.message,
  //              Set.empty,
  //              Set.empty
  //            )
  //          }
  //          case _ => stepSC
  //        }
  //      }
  //      case _ => stepSC
  //    }
  //  }

  //  /**
  //    * @author Gennadi Heimann
  //    * @version 0.1.0
  //    * @param ConnectionComponentToStepCS
  //    * @param componentId : String,
  //    * @param stepId      : String
  //    * @return ConnectionComponentToStepSC
  //    */

//  def connectComponentToStep(
//                              connectionComponentToStepIn: ConnectionComponentToStepIn
//                            ): ConnectionComponentToStepOut = {

    /*
     *
     * 
     * 
     * * suche alle Siblings des Components 
     * 
     * select expand(in('hasComponent').out('hasComponent')) from Component where @rid='#46:1'
     * 
     * #46:1 - Component bei dem neu Step angehaengt wird
     * 
     * suche schon angelegte Steps fuer die Siblings 
     * 
     * select expand(in('hasComponent').out('hasComponent').out('hasStep')) from Component where @rid='#46:1'
     * 
     * wenn eine oder mehr Steps gefunden wird -> 
     * 				dann wurde schon für eine oder andere Sibling einen Step hinzugefuegt.
     * 				Der Benutzer bekommt eine Warnmeldung dass er zu dem Step die Abhaengigkeiten hinzufuegen soll.
     * 				Die Abhaengigkeiten werden automatisch angelegt. Der Benutzer muss nur die Visualisierung zu diesen 
     * 					Abhaengigkeiten hinzufuegen. Diese Visualiesierung wird im Konfigurator bei der Kollesion ausgeführt.
     * 
     */

    //    HasStepEdge.hasStep(connectionComponentToStepIn)

    //    val status: String = HasDependencyEdge.checkForAdditionalStepInLevelComponentToStep(connectionComponentToStepIn.componentId)
    //
    //    status match {
    //      case StatusSuccessfulGeneral.status => {
    //        HasStepEdge.hasStep(connectionComponentToStepIn)
    //      }
    //      case StatusWarningAdditionalStepInLevelCS.status => {
    //        // Speichere StepCS temporaer
    //
    //        val step = StepVertex.getStep(connectionComponentToStepIn.stepId)
    //        val tempStepIn =  StepIn(
    //            connectionComponentToStepIn.componentId,
    //            step.get.nameToShow,
    //            step.get.kind,
    //            step.get.selectionCriteriumMin,
    //            step.get.selectionCriteriumMax
    //        )
    //
    //        try {
    //        	TempConfigurations.setAdditionalStepInLevelCS(Some(tempStepIn))
    //        } catch {
    //          case e: Exception => e.printStackTrace()
    //        }
    //        ConnectionComponentToStepOut(
    //            StatusWarningAdditionalStepInLevelCS.status,
    //            StatusWarningAdditionalStepInLevelCS.message,
    //            VisualizationProposal.proposal,
    //            Set.empty
    //        )
    //      }
    //      case StatusErrorGeneral.status => {
    //        ConnectionComponentToStepOut(
    //            StatusErrorGeneral.status,
    //            StatusErrorGeneral.message,
    //            Set.empty,
    //            Set.empty
    //        )
    //      }
    //    }
//    ???
//  }

  //  /**
  //    * @author Gennadi Heimann
  //    * @version 0.1.5
  //    * @param
  //    * @return
  //    */
  //  def connectToAditionalStepInLevelCS(stepCS: StepIn) = {
  //
  //    val stepSC: StepOut = StepVertex.step(stepCS)
  //
  //    stepSC.status match {
  //      case StatusSuccessfulStepCreated.status => {
  //        val eHasStep: OrientEdge = HasStepEdge.hasStep(stepCS, stepSC)
  //        eHasStep match {
  //          case null => {
  //            StepOut(
  //              "",
  //              StatusErrorFaultyStepId.status,
  //              StatusErrorFaultyStepId.message,
  //              Set.empty,
  //              Set.empty
  //            )
  //          }
  //          case _ => stepSC
  //        }
  //      }
  //      case _ => stepSC
  //    }
  //  }

  //  /**
  //    * @author Gennadi Heimann
  //    * @version 0.1.2
  //    *
  //    *          ChangeLog 0.1.2_3
  //    * @param DependencyCS
  //    * @return DependencySC
  //    */
//  def createDependency(dependencyIn: DependencyIn): DependencyOut = {

    //    val eHasDependency: Option[OrientEdge] = HasDependencyEdge.createDependency(dependencyIn)
    //
    //    eHasDependency match {
    //      case Some(eHasDependency) => {
    //        DependencyOut(
    //          dependencyIn.componentFromId,
    //          dependencyIn.componentToId,
    //          eHasDependency.getIdentity.toString,
    //          eHasDependency.getProperty(PropertyKey.DEPENDENCY_TYPE),
    //          eHasDependency.getProperty(PropertyKey.VISUALIZATION),
    //          eHasDependency.getProperty(PropertyKey.NAME_TO_SHOW),
    //          StatusSuccessfulDependencyCreated.status,
    //          StatusSuccessfulDependencyCreated.message
    //        )
    //      }
    //      case None => {
    //        DependencyOut(
    //          "",
    //          "",
    //          "",
    //          "",
    //          "",
    //          "",
    //          StatusErrorDependencyCreated.status,
    //          StatusErrorDependencyCreated.message
    //        )
    //      }
    //    }
//    ???
//  }

  //  /**
  //    * @author Gennadi Heimann
  //    * @version 0.1.5
  //    *
  //    *          Aus dem TempStep die Target-ComponentId heraushollen
  //    *
  //    *          Suche alle Sibling-Componente
  //    *
  //    *          Erstelle Dependencies aus der Target-Component zu alle Sibling-Components
  //    * @param
  //    * @return
  //    */
  def createDependenciesForAdditionalStepInLevelCS(
                                                    stepCS: Option[Any],
                                                    visualProposalForAdditionalStepsInOneLevelIn: VisualProposalForAdditionalStepsInOneLevelIn
                                                  ): Set[JsonDependencyForAdditionalStepsInOneLevel] = {

    //    val targetComponentId = stepCS.get.componentId
    //    val siblingComponents: Option[List[OrientVertex]] = ComponentVertex.getAllSiblings(targetComponentId)
    //    // Bei der Impl von neu Redising von Status diese Status abfangen
    //    siblingComponents match {
    //      case Some(siblingComponents) => {
    //        val eDependencies: List[Option[OrientEdge]] = HasDependencyEdge.createDependenciesForAdditionalStepInLevelCS(
    //          targetComponentId,
    //          siblingComponents,
    //          visualProposalForAdditionalStepsInOneLevelIn)
    //
    //        eDependencies.map(eDep => {
    //          eDep match {
    //            case Some(eDep) => {
    //              JsonDependencyForAdditionalStepsInOneLevel(
    //                eDep.getVertex(Direction.OUT).getIdentity.toString,
    //                eDep.getVertex(Direction.IN).getIdentity.toString,
    //                eDep.getIdentity.toString,
    //                eDep.getProperty(PropertyKey.DEPENDENCY_TYPE),
    //                eDep.getProperty(PropertyKey.VISUALIZATION),
    //                eDep.getProperty(PropertyKey.NAME_TO_SHOW)
    //              )
    //            }
    //            case None => {
    //              JsonDependencyForAdditionalStepsInOneLevel(
    //                "",
    //                "",
    //                "",
    //                "",
    //                "",
    //                ""
    //              )
    //            }
    //          }
    //        }).toSet
    //      }
    //      case None => {
    //        Set.empty
    //      }
    //    }
    //
    //
    //  }
    ???
  }
}