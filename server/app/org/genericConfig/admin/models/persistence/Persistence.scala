package org.genericConfig.admin.models.persistence

import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.{OrientEdge, OrientVertex}
import org.genericConfig.admin.models.json._
import org.genericConfig.admin.models.persistence.db.orientdb._
import org.genericConfig.admin.models.persistence.orientdb.{Graph, PropertyKeys}
import org.genericConfig.admin.models.tempConfig.TempConfigurations
import org.genericConfig.admin.models.visualization.VisualizationProposal
import org.genericConfig.admin.models.wrapper.connectionComponentToStep.{ConnectionComponentToStepIn, ConnectionComponentToStepOut}
import org.genericConfig.admin.models.wrapper.dependency.{DependencyIn, DependencyOut}
import org.genericConfig.admin.models.wrapper.step.{StepIn, StepOut, VisualProposalForAdditionalStepsInOneLevelIn}
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.common.status._
import org.genericConfig.admin.shared.config.bo.{ConfigBO, Configuration}
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.configTree.bo.{StepForConfigTreeBO, _}
import org.genericConfig.admin.shared.configTree.status._
import org.genericConfig.admin.shared.step.bo._
import org.genericConfig.admin.shared.step.json.JsonDependencyForAdditionalStepsInOneLevel
import org.genericConfig.admin.shared.step.status._
import org.genericConfig.admin.shared.user.bo.UserBO
import org.genericConfig.admin.shared.user.status._


/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.11.2016
  */
object Persistence {

  //TODO DOcu aus der OrientDB
  //Without an index against the property name, this query can take up a lot of time. You can improve performance by creating a new index against the name property:
  //http://orientdb.com/docs/last/Graph-VE.html

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param username : String, password: String
    * @return UserBO
    */
  def addUser(username: String, password: String): UserBO = {
    val (vUser: Option[OrientVertex], statusAddUser: StatusAddUser, statusCommon: Status) =
      Graph.addUser(username, password)

    statusAddUser match {
      case AddUserSuccess() =>
        UserBO(
          username = Some(vUser.get.getProperty(PropertyKeys.USERNAME).toString),
          userId = Some(vUser.get.getIdentity.toString),
          status =Some(StatusUser(
            addUser = Some(AddUserSuccess()),
            common = Some(Success())
          ))
        )
      case AddUserAlreadyExist() =>
        UserBO(
          username = Some(vUser.get.getProperty(PropertyKeys.USERNAME).toString),
          userId = Some(vUser.get.getIdentity.toString),
          status = Some(StatusUser(
            addUser = Some(AddUserAlreadyExist()),
            common = Some(Error())
          ))
        )
      case AddUserError() =>
        UserBO(
          status = Some(StatusUser(
            addUser = Some(AddUserError()),
            common = Some(Error())
          ))
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param username: String, password: String
    * @return UserBO
    */
  def getUser(username: String, password: String): UserBO = {

    val (vUser: Option[OrientVertex], getUserStatus: StatusGetUser, commonStatus: Status) =
      Graph.getUser(username, password)

    getUserStatus match {
      case GetUserSuccess() =>
        UserBO(
          username = Some(vUser.get.getProperty(PropertyKeys.USERNAME)),
          userId = Some(vUser.get.getIdentity.toString),
          status = Some(StatusUser(
            getUser = Some(GetUserSuccess()),
            common = Some(Success())
          ))
        )
      case GetUserError() =>
        UserBO(
          username = Some(username),
          status = Some(StatusUser(
            getUser = Some(GetUserError()),
            common = Some(commonStatus)
          ))
        )
      case GetUserAlreadyExist() =>
        UserBO(
          username = Some(username),
          status = Some(StatusUser(
            getUser = Some(GetUserAlreadyExist()),
            common = Some(commonStatus)
          ))
        )
      case GetUserNotExist() =>
        UserBO(
          username = Some(username),
          status = Some(StatusUser(
            getUser = Some(GetUserNotExist()),
            common = Some(commonStatus)
          ))
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId: String
    * @return String, Status
    */
  def getUserId(configId: String): (String, Status) = {
    Graph.getUserId(configId)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId: String, configUrl: String
    * @return ConfigBO
    */
  def addConfig(userId: String, configUrl: String): ConfigBO = {
    val (vConfig, statusAddConfig, statusCommon): (Option[OrientVertex], StatusAddConfig, Status) =
      Graph.addConfig(configUrl)
    statusAddConfig match {
      case AddConfigSuccess() =>
        ConfigBO(
          Some(userId),
          Some(List(Configuration(
            Some(vConfig.get.getIdentity.toString),
            Some(vConfig.get.getProperty(PropertyKeys.CONFIG_URL))
          ))),
          Some(StatusConfig(
            Some(AddConfigSuccess()),
            None, None, None, Some(Success())
          )
          ))
      case AddConfigAlreadyExist() =>
        ConfigBO(
          Some(userId),
          None,
          Some(StatusConfig(
            Some(AddConfigAlreadyExist()),
            None, None, None, Some(statusCommon)
          )
          ))
      case AddConfigError() =>
        ConfigBO(
          Some(userId),
          None,
          Some(StatusConfig(
            Some(AddConfigError()),
            None, None, None, Some(statusCommon)
          )
          )
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId: String, configId: String
    * @return Status
    */
  def appendConfigTo(userId: String, configId: String): Status = {
    Graph.appendConfigTo(userId, configId)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId: String
    * @return ConfigBO
    */
  def getConfigs(userId: String): ConfigBO = {
    val (vConfigs, statusGetConfig, statusCommon): (Option[List[OrientVertex]], StatusGetConfigs, Status) =
      Graph.getConfigs(userId)
    statusGetConfig match {
      case GetConfigsSuccess() =>
        ConfigBO(
          Some(userId),
          Some(vConfigs.get map (vConfig => {
            Configuration(
              Some(vConfig.getIdentity.toString),
              Some(vConfig.getProperty(PropertyKey.CONFIG_URL)))
          })),
          Some(StatusConfig(
            None, //addConfig
            Some(GetConfigsSuccess()), //getConfigs
            None, //deleteConfig
            None, //updateConfig
            Some(Success())
          )
          ))
      case GetConfigsEmpty() => ConfigBO(
        Some(userId), None,
        Some(StatusConfig(None, Some(GetConfigsEmpty()), None, None, Some(Success()))))
      case GetConfigsError() =>
        ConfigBO(Some(userId), None, Some(StatusConfig(None, Some(GetConfigsError()), None, None, Some(statusCommon))))
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId: String, configUrl: String
    * @return ConfigBO
    */
  def deleteConfig(configId: String, configUrl: String): ConfigBO = {

    val (userId, status): (String, Status) = Graph.getUserId(configId)
    val (statusDeleteConfig, statusCommon): (StatusDeleteConfig, Status) = Graph.deleteConfig(configId, configUrl: String)

    status match {
      case Success() =>
        ConfigBO(
          Some(userId), None,
          Some(StatusConfig(
            None, //addConfig: Option[StatusAddConfig],
            None, //getConfigs: Option[StatusGetConfigs],
            Some(statusDeleteConfig), //deleteConfig: Option[StatusDeleteConfig],
            None, //updateConfig: Option[StatusUpdateConfig],
            Some(statusCommon) //common: Option[Status
          )
          ))
      case _ =>
        ConfigBO(
          Some(userId), None,
          Some(StatusConfig(
            None, //addConfig: Option[StatusAddConfig],
            None, //getConfigs: Option[StatusGetConfigs],
            Some(DeleteConfigError()), //deleteConfig: Option[StatusDeleteConfig],
            None, //updateConfig: Option[StatusUpdateConfig],
            Some(status) //common: Option[Status
          )
          )
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId: String, configUrl: String
    * @return ConfigBO
    */
  def updateConfig(configId: String, configUrl: String): ConfigBO = {
    val (userId, status): (String, Status) = Graph.getUserId(configId)
    val (vUpdatedConfig, statusUpdateConfig, statusCommon): (Option[OrientVertex], StatusUpdateConfig, Status) =
      Graph.updateConfig(configId: String, configUrl: String)
    status match {
      case Success() =>
        ConfigBO(
          Some(userId),
          Some(List(Configuration(
            configId = Some(vUpdatedConfig.get.getIdentity.toString),
            configUrl = Some(vUpdatedConfig.get.getProperty(PropertyKey.CONFIG_URL))
          ))),
          Some(StatusConfig(
            None, //addConfig: Option[StatusAddConfig],
            None, //getConfigs: Option[StatusGetConfigs],
            None, //deleteConfig: Option[StatusDeleteConfig],
            Some(statusUpdateConfig), //updateConfig: Option[StatusUpdateConfig],
            Some(statusCommon) //common: Option[Status
          )
          ))
      case _ =>
        ConfigBO(
          Some(userId), None, Some(StatusConfig(
            None, //addConfig: Option[StatusAddConfig],
            None, //getConfigs: Option[StatusGetConfigs],
            None, //deleteConfig: Option[StatusDeleteConfig],
            Some(UpdateConfigError()), //updateConfig: Option[StatusUpdateConfig],
            Some(status) //common: Option[Status
          )
          )
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId: String
    * @return ConfigTreeBO
    */
  def getConfigTree(configTreeBO: ConfigTreeBO): ConfigTreeBO = {
    val (configTree, statusGetConfigTree, commonStatus): (Option[StepForConfigTreeBO], StatusGetConfigTree, Status) =
      Graph.getConfigTree(configTreeBO.configId.get)

    val (userId: String, status: Status) = Persistence.getUserId(configTreeBO.configId.get)

    statusGetConfigTree match {
      case GetConfigTreeSuccess() =>
        status match {
          case Success() =>
            ConfigTreeBO(
              Some(userId),
              configTreeBO.configId,
              configTree,
              Some(StatusConfigTree(
                GetConfigTreeSuccess(),
                Success()
              )
            ))
          case _ =>
            ConfigTreeBO(
              None,
              None,
              None,
              Some(StatusConfigTree(
                GetConfigTreeError(),
                status
              )
            ))
        }
      case GetConfigTreeEmpty() =>
        status match {
          case Success() =>
            ConfigTreeBO(
              Some(userId),
              configTreeBO.configId,
              None,
              Some(StatusConfigTree(
                GetConfigTreeEmpty(),
                Success()
              )
            ))
          case _ =>
            ConfigTreeBO(
              None,
              None,
              None,
              Some(StatusConfigTree(
                GetConfigTreeError(),
                status
              )
            ))
        }
      case GetConfigTreeError() => ConfigTreeBO(
        None,
        None,
        None,
        Some(StatusConfigTree(
          GetConfigTreeError(),
          commonStatus
        )
      ))
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO: StepBO
    * @return StepBO
    */
  def addStep(stepBO: StepBO): StepBO = {

    val (vStep: Option[OrientVertex], addStepStatus: StatusAddStep, commonStatus: Status) =
      Graph.addStep(stepBO)
    addStepStatus match {
      case AddStepSuccess() =>
        StepBO(
          configId = stepBO.configId,
          componentId = stepBO.componentId,
          stepId = Some(vStep.get.getIdentity.toString),
          status = Some(StatusStep(
            addStep = Some(AddStepSuccess()),
            common = Some(Success())
          ))
        )
      case AddStepError() =>
        StepBO(
          stepId = None,
          status = Some(StatusStep(
            addStep = Some(AddStepError()),
            common = Some(commonStatus)
          ))
        )
      case AddStepAlreadyExist() =>
        StepBO(
          stepId = None,
          status = Some(StatusStep(
            addStep = Some(AddStepAlreadyExist()),
            common = Some(commonStatus)
          ))
        )
      case AddStepDefectComponentOrConfigId() =>
        StepBO(
          stepId = None,
          status = Some(StatusStep(
            addStep = Some(AddStepDefectComponentOrConfigId()),
            common = Some(commonStatus)
          ))
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param id : String, stepId: String
    * @return (StatusAppendStep, Status)
    */
  def appendStepTo(id: String, stepId: String): (StatusAppendStep, Status) = {
    Graph.appendStepTo(id, stepId)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO: StepBO
    * @return StepBO
    */
  def updateStep(stepBO: StepBO): StepBO = {
    val (updateStepStatus: StatusUpdateStep, commonStatus: Status) = Graph.updateStep(stepBO)

    updateStepStatus match {
      case UpdateStepSuccess() =>
        StepBO(
          json = Some(JsonNames.UPDATE_STEP),
          status = Some(StatusStep(
            updateStep = Some(UpdateStepSuccess()),
            common = Some(Success())
          )
          ))
      case UpdateStepError() =>

        StepBO(
          json = Some(JsonNames.UPDATE_STEP),
          status = Some(StatusStep(
            updateStep = Some(UpdateStepError()),
            common = Some(commonStatus)
          )
          )
        )
    }
  }





//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param
//    * @return
//    */
  def deleteStep(stepId: String): (StatusDeleteStep, Status) = {
    Graph.deleteStep(stepId)
  }

  //  /**
  //   * creting von new Component and connect thies new Component with Step from param
  //   *
  //   * @author Gennadi Heimann
  //   *
  //   * @version 0.1.0
  //   *
  //   * @param ComponentCS
  //   *
  //   * @return ComponentSC
  //   */
  //  def createComponent(componentIn: ComponentIn): ComponentOut = {
  //    val vComponent: Option[OrientVertex] = ComponentVertex.component(componentIn)
  //
  //    vComponent match {
  //      case Some(vComponent) => {
  //        val eHasComponent: Option[OrientEdge] = HasComponentEdge.hasComponent(componentIn, vComponent)
  //        eHasComponent match {
  //          case Some(eHasComponent) => {
  //            ComponentOut(
  //                vComponent.getIdentity.toString,
  //                StatusSuccessfulComponentCreated.status,
  //                StatusSuccessfulComponentCreated.message
  //            )
  //          }
  //          case None => {
  //            ComponentOut(
  //                "",
  //                StatusErrorComponentGeneral.status,
  //                StatusErrorComponentGeneral.message
  //            )
  //          }
  //        }
  //      }
  //      case None => {
  //        ComponentOut(
  //            "",
  //            StatusErrorComponentGeneral.status,
  //            StatusErrorComponentGeneral.message
  //        )
  //      }
  //    }
  //  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.0
//    * @param StepSC
//    * @return StepCS
//    */
//  def createStep(stepIn: StepIn): StepOut = {
//
//    val status: String = HasDependencyEdge.checkForAdditionalStepInLevelComponentToStep(stepIn.componentId)
//
//    status match {
//      case StatusSuccessfulGeneral.status => {
//        val stepSC: StepOut = StepVertex.step(stepIn)
//
//        stepSC.status match {
//          case StatusErrorFaultyComponentId.status => {
//            stepSC
//          }
//          case StatusErrorStepExist.status => {
//            stepSC
//          }
//          case StatusSuccessfulStepCreated.status => {
//            val eHasStep: OrientEdge = HasStepEdge.hasStep(stepIn, stepSC)
//            eHasStep match {
//              case null => {
//                StepOut(
//                  "",
//                  StatusErrorFaultyStepId.status,
//                  StatusErrorFaultyStepId.message,
//                  Set.empty,
//                  Set.empty
//                )
//              }
//              case _ => stepSC
//            }
//          }
//        }
//      }
//      case StatusWarningAdditionalStepInLevelCS.status => {
//        // Speichere StepCS temporaer
//        try {
//          TempConfigurations.setAdditionalStepInLevelCS(Some(stepIn))
//        } catch {
//          case e: Exception => e.printStackTrace()
//        }
//
//        StepOut(
//          "",
//          StatusWarningAdditionalStepInLevelCS.status,
//          StatusWarningAdditionalStepInLevelCS.message,
//          VisualizationProposal.proposal,
//          Set.empty
//        )
//      }
//      case StatusErrorGeneral.status => {
//        StepOut(
//          "",
//          StatusErrorGeneral.status,
//          StatusErrorGeneral.message,
//          Set.empty,
//          Set.empty
//        )
//      }
//    }
//  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.5
//    *
//    *          Nach der pruefung in der Methode createStep
//    *          wird hier der zusaetzlicher Schritt in der Level ComponentStep angelegt
//    * @param StepSC
//    * @return StepCS
//    */
  def createAditionalStepInLevelCS(stepCS: StepIn) = {

    val stepSC: StepOut = StepVertex.step(stepCS)

    stepSC.status match {
      case StatusSuccessfulStepCreated.status => {
        val eHasStep: OrientEdge = HasStepEdge.hasStep(stepCS, stepSC)
        eHasStep match {
          case null => {
            StepOut(
              "",
              StatusErrorFaultyStepId.status,
              StatusErrorFaultyStepId.message,
              Set.empty,
              Set.empty
            )
          }
          case _ => stepSC
        }
      }
      case _ => stepSC
    }
  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.0
//    * @param ConnectionComponentToStepCS
//    * @param componentId : String,
//    * @param stepId      : String
//    * @return ConnectionComponentToStepSC
//    */

  def connectComponentToStep(
                              connectionComponentToStepIn: ConnectionComponentToStepIn
                            ): ConnectionComponentToStepOut = {

    /*
     * TODO v016 dasselbe wie bei createStep implementieren aber einen Partnercomponent beruecksichtigen
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

    HasStepEdge.hasStep(connectionComponentToStepIn)

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
  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.5
//    * @param
//    * @return
//    */
  def connectToAditionalStepInLevelCS(stepCS: StepIn) = {

    val stepSC: StepOut = StepVertex.step(stepCS)

    stepSC.status match {
      case StatusSuccessfulStepCreated.status => {
        val eHasStep: OrientEdge = HasStepEdge.hasStep(stepCS, stepSC)
        eHasStep match {
          case null => {
            StepOut(
              "",
              StatusErrorFaultyStepId.status,
              StatusErrorFaultyStepId.message,
              Set.empty,
              Set.empty
            )
          }
          case _ => stepSC
        }
      }
      case _ => stepSC
    }
  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.2
//    *
//    *          ChangeLog 0.1.2_3
//    * @param DependencyCS
//    * @return DependencySC
//    */
  def createDependency(dependencyIn: DependencyIn): DependencyOut = {

    val eHasDependency: Option[OrientEdge] = HasDependencyEdge.createDependency(dependencyIn)

    eHasDependency match {
      case Some(eHasDependency) => {
        DependencyOut(
          dependencyIn.componentFromId,
          dependencyIn.componentToId,
          eHasDependency.getIdentity.toString,
          eHasDependency.getProperty(PropertyKey.DEPENDENCY_TYPE),
          eHasDependency.getProperty(PropertyKey.VISUALIZATION),
          eHasDependency.getProperty(PropertyKey.NAME_TO_SHOW),
          StatusSuccessfulDependencyCreated.status,
          StatusSuccessfulDependencyCreated.message
        )
      }
      case None => {
        DependencyOut(
          "",
          "",
          "",
          "",
          "",
          "",
          StatusErrorDependencyCreated.status,
          StatusErrorDependencyCreated.message
        )
      }
    }
  }

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
                                                    stepCS: Option[StepIn],
                                                    visualProposalForAdditionalStepsInOneLevelIn: VisualProposalForAdditionalStepsInOneLevelIn
                                                  ): Set[JsonDependencyForAdditionalStepsInOneLevel] = {

    val targetComponentId = stepCS.get.componentId
    val siblingComponents: Option[List[OrientVertex]] = ComponentVertex.getAllSiblings(targetComponentId)
    // TODO v016 JsonDependencyForAdditionalStepsInOneLevel == None
    // Bei der Impl von neu Redising von Status diese Status abfangen
    siblingComponents match {
      case Some(siblingComponents) => {
        val eDependencies: List[Option[OrientEdge]] = HasDependencyEdge.createDependenciesForAdditionalStepInLevelCS(
          targetComponentId,
          siblingComponents,
          visualProposalForAdditionalStepsInOneLevelIn)

        eDependencies.map(eDep => {
          eDep match {
            case Some(eDep) => {
              JsonDependencyForAdditionalStepsInOneLevel(
                eDep.getVertex(Direction.OUT).getIdentity.toString,
                eDep.getVertex(Direction.IN).getIdentity.toString,
                eDep.getIdentity.toString,
                eDep.getProperty(PropertyKey.DEPENDENCY_TYPE),
                eDep.getProperty(PropertyKey.VISUALIZATION),
                eDep.getProperty(PropertyKey.NAME_TO_SHOW)
              )
            }
            case None => {
              JsonDependencyForAdditionalStepsInOneLevel(
                "",
                "",
                "",
                "",
                "",
                ""
              )
            }
          }
        }).toSet
      }
      case None => {
        Set.empty
      }
    }


  }

}