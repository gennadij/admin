package org.genericConfig.admin.models.persistence

import org.genericConfig.admin.shared.configTree.bo._


/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.11.2016
  */
object Persistence {

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
//  def createDependenciesForAdditionalStepInLevelCS(
//                                                    stepCS: Option[Any],
//                                                    visualProposalForAdditionalStepsInOneLevelIn: VisualProposalForAdditionalStepsInOneLevelIn
//                                                  ): Set[JsonDependencyForAdditionalStepsInOneLevel] = {

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
//  }
}