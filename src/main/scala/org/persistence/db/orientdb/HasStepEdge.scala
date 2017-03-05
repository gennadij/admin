/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.persistence.db.orientdb

import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import org.dto.step.StepSC
import org.dto.step.StepCS
import org.dto.connectionComponentToStep.ConnectionComponentToStepCS
import org.dto.connectionComponentToStep.ConnectionComponentToStepSC
import org.dto.connectionComponentToStep.ConnectionComponentToStepResult

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 24.01.2017
 */

object HasStepEdge {
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param
   * 
   * @return
   */
  def hasStep(stepCS: StepCS, stepSC: StepSC): OrientEdge = {
    
    val graph: OrientGraph = OrientDB.getGraph()
    val vComponent = graph.getVertex(stepCS.params.componentId)
    val vStep = graph.getVertex(stepSC.result.stepId)
    val eHasStep: OrientEdge = graph.addEdge(
        "class:" + PropertyKey.EDGE_HAS_STEP, 
        vComponent, 
        vStep, 
        PropertyKey.EDGE_HAS_STEP
    )
    graph.commit
    
    eHasStep
  }
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param
   * 
   * @return
   */
  def hasStep(
      connectionComponentToStepCS: ConnectionComponentToStepCS
      ): ConnectionComponentToStepSC = {
    val graph: OrientGraph = OrientDB.getGraph()
    val vComponent = graph.getVertex(connectionComponentToStepCS.params.componentId)
    val vStep = graph.getVertex(connectionComponentToStepCS.params.stepId)
    
    val eHasStep: OrientEdge = graph.addEdge(
        "class:" + PropertyKey.EDGE_HAS_STEP, 
        vComponent, 
        vStep, 
        PropertyKey.EDGE_HAS_STEP
    )
    graph.commit
    if(eHasStep != null) {
      ConnectionComponentToStepSC(
          result = ConnectionComponentToStepResult(
              true,
              "Component mit dem Step wurde Verbunden"
          )
      )
    }else{
      ConnectionComponentToStepSC(
          result = ConnectionComponentToStepResult(
              false,
              "Component mit dem Step wurde nicht Verbunden"
          )
      )
    }
  }
}