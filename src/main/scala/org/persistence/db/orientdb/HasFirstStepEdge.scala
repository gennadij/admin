package org.persistence.db.orientdb

import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.dto.step.FirstStepSC
import org.dto.step.FirstStepCS

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.01.2017
 */

object HasFirstStepEdge {
  
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param firstStepCS: FirstStepCS, firstStepSC: FirstStepSC
   * 
   * @return OrientEdge
   */
  def hasFirstStep(firstStepCS: FirstStepCS, firstStepSC: FirstStepSC): OrientEdge = {
    //TODO Try Catch Block
   
    val graph: OrientGraph = OrientDB.getGraph()
    val vConfig = graph.getVertex(firstStepCS.params.configId)
    val vFirstStep = graph.getVertex(firstStepSC.result.stepId)
    val eHasFirstStep: OrientEdge = graph.addEdge(
        "class:" + PropertyKey.EDGE_HAS_FIRST_STEP, 
        vConfig, 
        vFirstStep, 
        PropertyKey.EDGE_HAS_FIRST_STEP
    )
    graph.commit
    
    eHasFirstStep
  }
}