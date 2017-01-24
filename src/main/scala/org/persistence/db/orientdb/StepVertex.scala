/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.persistence.db.orientdb

import scala.collection.JavaConversions._

import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import org.dto.step.StepCS
import org.dto.step.StepSC
import com.tinkerpop.blueprints.Direction
import org.dto.step.StepResult
import org.dto.step.FirstStepSC
import org.dto.step.FirstStepCS
import org.dto.step.FirstStepResult

/**
 * Created by Gennadi Heimann 1.1.2017
 */

object StepVertex {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param
   * 
   * @return
   */
  def addStep(stepCS: StepCS): StepSC = {
    val graph: OrientGraph = OrientDB.getGraph
    
    val vStep: OrientVertex = graph.addVertex("class:" + PropertyKey.VERTEX_STEP, 
//            PropertyKey.ADMIN_ID, stepCS.params.adminId,
            PropertyKey.KIND, stepCS.params.kind,
            PropertyKey.SELECTION_CRITERIUM_MIN, stepCS.params.selectionCriterium.min.toString,
            PropertyKey.SELECTION_CRITERIUM_MAX, stepCS.params.selectionCriterium.max.toString
    )
    
    graph.commit
        
    new StepSC(
        result = new StepResult(
        	vStep.getIdentity.toString(),
        	true//TODO impl status
        	,"Der Step wurde hinzugefuegt"
        )
    )
  }
  
  def step(stepCS: StepCS): StepSC = {
    val graph: OrientGraph = OrientDB.getGraph
    val componentId: String = stepCS.params.componentId
    
    val countsOfSteps: Int = graph.getVertex(componentId).getEdges(Direction.OUT, PropertyKey.EDGE_HAS_STEP).toList.size
    
    if(countsOfSteps > 0) {
      new StepSC(
          result = StepResult(
              "",
              false,
              "Der Step exstiert bereits"
          )
      )
    }else{
      val vStep: OrientVertex = graph.addVertex(
        "class:" + PropertyKey.VERTEX_STEP,
        PropertyKey.KIND, stepCS.params.kind,
        PropertyKey.SELECTION_CRITERIUM_MIN, stepCS.params.selectionCriterium.min.toString,
        PropertyKey.SELECTION_CRITERIUM_MAX, stepCS.params.selectionCriterium.max.toString
      )
      graph.commit
      
      new StepSC(
          result = StepResult(
              vStep.getIdentity.toString,
              true,
              "Der Step wurde zu der Komponente hinzugefuegt"
          )
      )
    }
  }
  
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param
   * 
   * @return
   */

  def firstStep(firstStepCS: FirstStepCS): FirstStepSC = {
    //TODO Try Catch Block einbauen -> Nullpointer Exception fangen
    
    val graph: OrientGraph = OrientDB.getGraph
    val configId = firstStepCS.params.configId
    
    //check ob zweite FirstStep bei der Config ist
    val countsOfFirstSteps: Int = graph.getVertex(configId).getEdges(Direction.OUT, PropertyKey.EDGE_HAS_FIRST_STEP).toList.size
    if(countsOfFirstSteps > 0) {
      new FirstStepSC(
          result = FirstStepResult(
              "",
              false,
              "Der FirstStep exstiert bereits"
          )
      )
    }else{
      val vFirstStep: OrientVertex = graph.addVertex(
        "class:" + PropertyKey.VERTEX_STEP,
        PropertyKey.KIND, firstStepCS.params.kind,
        PropertyKey.SELECTION_CRITERIUM_MIN, firstStepCS.params.selectionCriterium.min.toString,
        PropertyKey.SELECTION_CRITERIUM_MAX, firstStepCS.params.selectionCriterium.max.toString
      )
      graph.commit
      
      new FirstStepSC(
          result = FirstStepResult(
              vFirstStep.getIdentity.toString,
              true,
              "Der erste Step wurde zu der Konfiguration hinzugefuegt"
          )
      )
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param
   * 
   * @return
   */
  def removerSteps(adminId: String) = {
    val graph: OrientGraph = OrientDB.getGraph
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX Step where adminId='$adminId'")).execute()
    graph.commit
    res
  }
  
  def removeStep(configId: String): Int = {
    val graph: OrientGraph = OrientDB.getGraph
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX Step where @rid IN (SELECT out() from Config where @rid='$configId')")).execute()
    graph.commit
    res
  }
}