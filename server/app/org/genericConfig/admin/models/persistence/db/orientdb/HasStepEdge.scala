package org.genericConfig.admin.models.persistence.db.orientdb

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.models.wrapper.step.StepIn
import org.genericConfig.admin.models.wrapper.step.StepOut
import org.genericConfig.admin.models.wrapper.connectionComponentToStep.ConnectionComponentToStepIn
import org.genericConfig.admin.models.wrapper.connectionComponentToStep.ConnectionComponentToStepOut
import org.genericConfig.admin.models.json.StatusSuccessfulConnectionComponentToStep
import org.genericConfig.admin.models.json.StatusErrorConnectionComponentToStep

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
  def hasStep(stepCS: StepIn, stepSC: StepOut): OrientEdge = {
    
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val vComponent = graph.getVertex(stepCS.componentId)
    val vStep = graph.getVertex(stepSC.stepId)
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
      connectionComponentToStepIn: ConnectionComponentToStepIn
      ): ConnectionComponentToStepOut = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
    val eHasStep: Option[OrientEdge] = try{
      val vComponent = graph.getVertex(connectionComponentToStepIn.componentId)
      val vStep = graph.getVertex(connectionComponentToStepIn.stepId)
      
      val eHasStep: OrientEdge = graph.addEdge(
          "class:" + PropertyKey.EDGE_HAS_STEP, 
          vComponent, 
          vStep, 
          PropertyKey.EDGE_HAS_STEP
      )
    graph.commit
    Some(eHasStep)
    }catch{
      case e: Exception => {
        graph.rollback()
        None
      }
    }
    
    
    eHasStep match {
      case Some(eHasStep) => {
        ConnectionComponentToStepOut(
            StatusSuccessfulConnectionComponentToStep.status,
            StatusSuccessfulConnectionComponentToStep.message
//            TODO v016
//            ,
//            Set.empty,
//            Set.empty
        )
      }
      case None => {
        ConnectionComponentToStepOut(
            StatusErrorConnectionComponentToStep.status,
            StatusErrorConnectionComponentToStep.message
//            TODO v016
//            ,
//            Set.empty,
//            Set.empty
        )
      }
    }
  }
}