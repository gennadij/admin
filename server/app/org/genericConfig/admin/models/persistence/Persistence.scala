package org.genericConfig.admin.models.persistence

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import scala.collection.JavaConverters._
import com.orientechnologies.orient.core.sql.OCommandSQL
import org.genericConfig.admin.models.persistence.db.orientdb.StepVertex
import org.genericConfig.admin.models.persistence.db.orientdb.ComponentVertex
import org.genericConfig.admin.models.persistence.db.orientdb.HasComponentEdge
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import org.genericConfig.admin.models.persistence.db.orientdb.HasDependencyEdge
import org.genericConfig.admin.models.persistence.db.orientdb.AdminUserVertex
import play.api.Logger
import org.genericConfig.admin.models.visualization.VisualizationProposal
import org.genericConfig.admin.models.tempConfig.TempConfigurations
import org.genericConfig.admin.models.wrapper.step.StepIn
import org.genericConfig.admin.models.wrapper.step.StepOut
import org.genericConfig.admin.models.json.StatusSuccessfulGeneral
import org.genericConfig.admin.models.json.StatusErrorFaultyComponentId
import org.genericConfig.admin.models.json.StatusErrorStepExist
import org.genericConfig.admin.models.json.StatusSuccessfulStepCreated
import org.genericConfig.admin.models.json.StatusErrorFaultyStepId
import org.genericConfig.admin.models.json.StatusWarningAdditionalStepInLevelCS
import org.genericConfig.admin.models.json.StatusErrorGeneral
import org.genericConfig.admin.models.persistence.db.orientdb.HasStepEdge
import org.genericConfig.admin.models.json.step.JsonVisualProposalForAdditionalStepsInOneLevelIn
import org.genericConfig.admin.models.json.step.JsonDependencyForAdditionalStepsInOneLevel
import org.genericConfig.admin.models.wrapper.login.LoginIn
import org.genericConfig.admin.models.wrapper.login.LoginOut
import org.genericConfig.admin.models.wrapper.config.CreateConfigIn
import org.genericConfig.admin.models.wrapper.config.CreateConfigOut
import org.genericConfig.admin.models.json.StatusSuccessfulConfig
import org.genericConfig.admin.models.persistence.db.orientdb.ConfigVertex
import org.genericConfig.admin.models.persistence.db.orientdb.HasConfigEdge
import org.genericConfig.admin.models.json.StatusErrorConfig
import org.genericConfig.admin.models.wrapper.step.FirstStepIn
import org.genericConfig.admin.models.wrapper.step.FirstStepOut
import org.genericConfig.admin.models.json.StatusErrorFirstStepExist
import org.genericConfig.admin.models.persistence.db.orientdb.HasFirstStepEdge
import org.genericConfig.admin.models.json.StatusSuccessfulFirstStepCreated
import org.genericConfig.admin.models.json.StatusErrorFaultyFirstStepId
import org.genericConfig.admin.models.json.StatusErrorFaultyConfigId
import org.genericConfig.admin.models.wrapper.component.ComponentIn
import org.genericConfig.admin.models.wrapper.component.ComponentOut
import org.genericConfig.admin.models.json.StatusSuccessfulComponentCreated
import org.genericConfig.admin.models.json.StatusErrorComponentGeneral
import org.genericConfig.admin.models.wrapper.connectionComponentToStep.ConnectionComponentToStepIn
import org.genericConfig.admin.models.wrapper.connectionComponentToStep.ConnectionComponentToStepOut
import org.genericConfig.admin.models.json.StatusErrorWriteToDB
import org.genericConfig.admin.models.wrapper.configTree.ConfigTreeIn
import org.genericConfig.admin.models.wrapper.configTree.ConfigTreeOut
import org.genericConfig.admin.models.wrapper.dependency.DependencyIn
import org.genericConfig.admin.models.wrapper.dependency.DependencyOut
import org.genericConfig.admin.models.json.StatusSuccessfulDependencyCreated
import org.genericConfig.admin.models.json.StatusErrorDependencyCreated
import org.genericConfig.admin.models.json.StatusErrorDuplicateConfigUrl
import org.genericConfig.admin.models.wrapper.step.VisualProposalForAdditionalStepsInOneLevelIn
import org.genericConfig.admin.shared.bo.RegistrationBO
import org.genericConfig.admin.models.persistence.db.orientdb.PropertyKey
import org.genericConfig.admin.models.persistence.orientdb.Graph
import org.genericConfig.admin.shared.status.Status
import org.genericConfig.admin.shared.status.Success
import org.genericConfig.admin.shared.status.Error
import org.genericConfig.admin.shared.status.ODBClassCastError
import org.genericConfig.admin.shared.status.ODBReadError
import org.genericConfig.admin.shared.bo.LoginBO
import org.genericConfig.admin.shared.bo.ConfigBO
import org.genericConfig.admin.shared.status.login.StatusLogin
import org.genericConfig.admin.shared.status.login.UserExist
import org.genericConfig.admin.shared.status.login.UserConfigsError
import org.genericConfig.admin.shared.status.login.UserNotExist

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
 */


//TODO DOcu aus der OrientDB
      //Without an index against the property name, this query can take up a lot of time. You can improve performance by creating a new index against the name property:
      //http://orientdb.com/docs/last/Graph-VE.html
object Persistence {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  
  def addUser(username: String, password: String): RegistrationBO = {
    Graph.addUser(username, password)
  }

  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param LoginCS
   * 
   * @return LoginSC
   */
  def login(username: String, password: String): LoginBO = {
    
    val vUser: (Option[OrientVertex], Status) = Graph.readUser(username, password)
    
    vUser._2 match {
      case Success() => {
        getLoginBO(vUser._1.get)
      }
      case Error() => {
        LoginBO(
            username, 
            "",
            None,
            StatusLogin(
                UserNotExist(),
                Error()
            )
        )
      }
      case ODBClassCastError() => {
        LoginBO(
            "", "",
            None,
            StatusLogin(
                UserNotExist(),
                ODBClassCastError()
            )
        )
      }
      case ODBReadError() => {
        LoginBO(
            "", "",
            None,
            StatusLogin(
                UserNotExist(),
                ODBReadError()
            )
        )
      }
    }
  }
  
  private def getLoginBO(vUser: OrientVertex): LoginBO = {
    val vConfigs: (Option[List[OrientVertex]], Status) = Graph.readConfigs(vUser.getIdentity.toString)
    vConfigs._2 match {
      case Success() => {
        LoginBO(
            vUser.getProperty(PropertyKey.USERNAME),
            vUser.getIdentity.toString,
            Some(
                vConfigs._1.get map (vConfig => {
                  ConfigBO(
                      vConfig.getId.toString,
                      vConfig.getProperty(PropertyKey.CONFIG_URL)
                  )
                })
            ),
            StatusLogin(
                UserExist(),
                Success()
            )
        )
      }
      case Error() => {
        LoginBO(
            "", "",
            None,
            StatusLogin(
                UserConfigsError(),
                Error()
            )
        )
      }
      case ODBClassCastError() => {
        LoginBO(
            "", "",
            None,
            StatusLogin(
                UserConfigsError(),
                Error()
            )
        )
      }
      case ODBReadError() => {
        LoginBO(
            "", "",
            None,
            StatusLogin(
                UserConfigsError(),
                Error()
            )
        )
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param CreateConfigSC
   * 
   * @return CreateConfigCS
   */
  def createConfig(createConfigIn: CreateConfigIn): CreateConfigOut = {
    val vConfig: (Option[OrientVertex], String) = ConfigVertex.createConfig(createConfigIn)
    vConfig._2 match {
      case StatusSuccessfulConfig.status => {
        val eHasConfig: (Option[OrientEdge], String) = HasConfigEdge.hasConfig(createConfigIn.adminId, vConfig._1.get)
        eHasConfig._2 match {
          case StatusSuccessfulConfig.status => {
            CreateConfigOut(
                vConfig._1.get.getIdentity.toString,
                StatusSuccessfulConfig.status,
                StatusSuccessfulConfig.message
            )
          }
          case StatusErrorWriteToDB.status => {
            CreateConfigOut(
                "",
                StatusErrorConfig.status,
                StatusErrorConfig.message
            )
          }
        }
        
      }
      case StatusErrorDuplicateConfigUrl.status => {
        CreateConfigOut(
            "",
            StatusErrorDuplicateConfigUrl.status,
            StatusErrorDuplicateConfigUrl.message
        )
      }
      case StatusErrorWriteToDB.status => {
        CreateConfigOut(
            "",
            StatusErrorWriteToDB.status,
            StatusErrorWriteToDB.message
        )
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param FirstStepCS
   * 
   * @return FirstStepSC
   */
  
  def createFirstStep(firstStepIn: FirstStepIn): FirstStepOut = {
    
    val firstStepSC: FirstStepOut = StepVertex.firstStep(firstStepIn)
    firstStepSC.status match {
      case StatusErrorFirstStepExist.status => {
        firstStepSC
      }
      case StatusSuccessfulFirstStepCreated.status => {
        val eHasFirstStep: Option[OrientEdge] = HasFirstStepEdge.hasFirstStep(firstStepIn, firstStepSC)
        eHasFirstStep match {
          case None => {
            FirstStepOut(
                "",
                StatusErrorFaultyFirstStepId.status,
                StatusErrorFaultyFirstStepId.message
            )
          }
          case Some(eHasFirstStep) => {
            firstStepSC
          }
        }
      }
      case StatusErrorFaultyConfigId.status => firstStepSC
    }
  }
  
  /**
   * creting von new Component and connect thies new Component with Step from param
   * 
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param ComponentCS
   * 
   * @return ComponentSC
   */
  def createComponent(componentIn: ComponentIn): ComponentOut = {
    val vComponent: Option[OrientVertex] = ComponentVertex.component(componentIn)
    
    vComponent match {
      case Some(vComponent) => {
        val eHasComponent: Option[OrientEdge] = HasComponentEdge.hasComponent(componentIn, vComponent)
        eHasComponent match {
          case Some(eHasComponent) => {
            ComponentOut(
                vComponent.getIdentity.toString,
                StatusSuccessfulComponentCreated.status,
                StatusSuccessfulComponentCreated.message
            )
          }
          case None => {
            ComponentOut(
                "",
                StatusErrorComponentGeneral.status,
                StatusErrorComponentGeneral.message
            )
          }
        }
      }
      case None => {
        ComponentOut(
            "",
            StatusErrorComponentGeneral.status,
            StatusErrorComponentGeneral.message
        )
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param StepSC
   * 
   * @return StepCS
   */
  def createStep(stepIn: StepIn): StepOut = {
    
    val status: String = HasDependencyEdge.checkForAdditionalStepInLevelComponentToStep(stepIn.componentId)
    
    status match {
      case StatusSuccessfulGeneral.status => {
        val stepSC: StepOut = StepVertex.step(stepIn)
        
        stepSC.status match {
          case StatusErrorFaultyComponentId.status => {
            stepSC
          }
          case StatusErrorStepExist.status => {
            stepSC
          }
          case StatusSuccessfulStepCreated.status => {
            val eHasStep: OrientEdge = HasStepEdge.hasStep(stepIn, stepSC)
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
        }
      }
      case StatusWarningAdditionalStepInLevelCS.status => {
        // Speichere StepCS temporaer
        try {
        	TempConfigurations.setAdditionalStepInLevelCS(Some(stepIn))
        } catch {
          case e: Exception => e.printStackTrace()
        }
        
        StepOut(
            "",
            StatusWarningAdditionalStepInLevelCS.status,
            StatusWarningAdditionalStepInLevelCS.message,
            VisualizationProposal.proposal,
            Set.empty
        )
      }
      case StatusErrorGeneral.status => {
        StepOut(
            "",
            StatusErrorGeneral.status,
            StatusErrorGeneral.message,
            Set.empty,
            Set.empty
        )
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * Nach der pruefung in der Methode createStep 
   * wird hier der zusaetzlicher Schritt in der Level ComponentStep angelegt
   * 
   * @param StepSC
   * 
   * @return StepCS
   */
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
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param ConnectionComponentToStepCS
   * @param componentId: String,
   * @param stepId: String
   * 
   * @return ConnectionComponentToStepSC
   */
  
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
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param 
   * 
   * @return
   */
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
  
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param ConfigTreeCS
   * 
   * @return ConfigTreeSC
   */
  def getConfigTree(configTreeIn: ConfigTreeIn): ConfigTreeOut = {
    ConfigVertex.getConfigTree(configTreeIn)
  }
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.2
   * 
   * ChangeLog 0.1.2_3
   * 
   * @param DependencyCS
   * 
   * @return DependencySC
   */
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
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * Aus dem TempStep die Target-ComponentId heraushollen
   * 
   * Suche alle Sibling-Componente
   * 
   * Erstelle Dependencies aus der Target-Component zu alle Sibling-Components
   * @param 
   * 
   * @return 
   */
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