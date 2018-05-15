package org.genericConfig.admin.models.persistence.db.orientdb

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.Vertex
import org.genericConfig.admin.models.persistence.OrientDB
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import org.genericConfig.admin.models.wrapper.dependency.DependencyOut
import org.genericConfig.admin.models.wrapper.dependency.DependencyIn
import org.genericConfig.admin.models.json.StatusWarningAdditionalStepInLevelCS
import org.genericConfig.admin.models.json.StatusSuccessfulGeneral
import org.genericConfig.admin.models.json.StatusErrorGeneral
import org.genericConfig.admin.models.wrapper.step.VisualProposalForAdditionalStepsInOneLevelIn
import com.orientechnologies.orient.core.sql.OCommandSQL

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 04.06.2017
 */

object HasDependencyEdge {

  
    def createDependency(dependencyIn: DependencyIn): Option[OrientEdge] = {
      val graph: OrientGraph = OrientDB.getFactory().getTx
      
      try{
        val componentFromId = dependencyIn.componentFromId
        val componentToId = dependencyIn.componentToId
        val eHasDependency: OrientEdge = graph.addEdge(
            "class:" + PropertyKey.EDGE_HAS_DEPENDENCY, 
            graph.getVertex(componentFromId), 
            graph.getVertex(componentToId), 
            PropertyKey.EDGE_HAS_DEPENDENCY
        )
        eHasDependency.setProperty(PropertyKey.DEPENDENCY_TYPE, dependencyIn.dependencyType)
        eHasDependency.setProperty(PropertyKey.VISUALIZATION, dependencyIn.visualization)
        eHasDependency.setProperty(PropertyKey.NAME_TO_SHOW, dependencyIn.nameToShow)
        graph.commit()
        Some(eHasDependency)
      }catch{
        case e: Exception => {
          graph.rollback()
          None
        }
      }
      
      
  }
    
  def checkForAdditionalStepInLevelComponentToStep(componentId: String): String = {
    
    val siblings: Option[List[OrientVertex]] = ComponentVertex.getAllSiblings(componentId)
    
    
     siblings match {
      case Some(siblings) => 
        val vSteps: List[Vertex] = siblings.flatMap(_.getEdges(Direction.OUT, "hasStep").asScala.toList.map(_.getVertex(Direction.IN)))
        vSteps.size match {
          case count if count > 0 => {
            // Der Step wurde schon in Level ComponentToStep angelegt, fuer weitere Steps braucht man hier Dependencies
            // Der Benutzer bekommt ein Warning und Vorschlag die Dependencies einzulegen oder Strep gar nicht einzulegen
            
            ComponentVertex.getFatherStep(componentId) match {
              case Some(fatherStep) => {
                fatherStep match {
                  case sC if fatherStep.getProperty(PropertyKey.SELECTION_CRITERIUM_MIN).toString.toInt > 1 =>
                    StatusWarningAdditionalStepInLevelCS.status
                  case sC if fatherStep.getProperty(PropertyKey.SELECTION_CRITERIUM_MAX).toString.toInt > 1 => 
                    StatusWarningAdditionalStepInLevelCS.status
                  case sC if fatherStep.getProperty(PropertyKey.SELECTION_CRITERIUM_MIN).toString.toInt == 1 => 
                    StatusSuccessfulGeneral.status
                  case sC if fatherStep.getProperty(PropertyKey.SELECTION_CRITERIUM_MAX).toString.toInt == 1 => 
                    StatusSuccessfulGeneral.status
                  case _ => StatusErrorGeneral.status
                }
              }
              case None => StatusErrorGeneral.status
            }
          }
          case count if count == 0 => StatusSuccessfulGeneral.status
          case _ => StatusErrorGeneral.status
        }
      case None => StatusErrorGeneral.status
    }
  }
  
  def createDependenciesForAdditionalStepInLevelCS(
      targetComponentId: String,
      allSiblingcomponents: List[OrientVertex],
      selectedVisualProposal: VisualProposalForAdditionalStepsInOneLevelIn): List[Option[OrientEdge]] = {
    
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
    val siblings: List[OrientVertex] = allSiblingcomponents.filterNot(sibling => sibling.getIdentity.toString() == targetComponentId)
    
    // siblingComponent ---> targetComponent
    val fromSiblingToTarget: List[Option[OrientEdge]] = siblings.map(sibling => {
      HasDependencyEdge.createDependency(
          DependencyIn(
              targetComponentId, //to
              sibling.getIdentity.toString, //from
              "default", 
              selectedVisualProposal.selectedVisualProposal,
              assembleNameToSchowForDependency(
                  sibling.getProperty(PropertyKey.NAME_TO_SHOW).toString, 
                  graph.getVertex(targetComponentId).getProperty(PropertyKey.NAME_TO_SHOW).toString
              )
          )
      )
    })
    
    //targetComponent ----> siblingComponent
    val fromTargetToSiblings: List[Option[OrientEdge]] = siblings.map(sibling => {
      HasDependencyEdge.createDependency(
          DependencyIn(
              sibling.getIdentity.toString, //to
              targetComponentId, // from
              "default", 
              selectedVisualProposal.selectedVisualProposal,
              assembleNameToSchowForDependency(
                  sibling.getProperty(PropertyKey.NAME_TO_SHOW).toString, 
                  graph.getVertex(targetComponentId).getProperty(PropertyKey.NAME_TO_SHOW).toString
              )
          )
      )
    })
    
    fromTargetToSiblings ::: fromSiblingToTarget
  }
  
  private def assembleNameToSchowForDependency(componentOut: String, componentIn: String): String = {
    s"($componentOut) ----> ($componentIn)"
  }
  
  def deleteDependency(nameToShow: String): Int = {
    // DELETE EDGE hasDependency WHERE nameToShow="dependency_C2_C3_user28_v015" 
    val sqlQuery = s"DELETE EDGE hasDependency WHERE nameToShow='$nameToShow'"
    
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val res: Int = graph.command(new OCommandSQL(sqlQuery)).execute()
    graph.commit
    res
  }
}