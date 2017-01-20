package org.persistence.db.orientdb

import scala.collection.JavaConversions._

import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import org.dto.component._
import org.dto.connStepToComponent.ConnStepToComponentCS
import org.dto.connStepToComponent.ConnStepToComponentResultSC
import org.dto.connStepToComponent.ConnStepToComponentSC

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */

object HasComponentEdge {

  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param
   * 
   * @return
   */
  def add(connStepToComponent: ConnStepToComponentCS): ConnStepToComponentSC = {
    val graph: OrientGraph = OrientDB.getGraph
    val outStep: String = connStepToComponent.params.outStepId
    val inComponent: String = connStepToComponent.params.inComponentId
    val adminId: String = connStepToComponent.params.adminId
    val eHasComponent: OrientEdge = graph.addEdge("class:hasComponent", 
      graph.getVertex(outStep), 
      graph.getVertex(inComponent), 
       "hasComponent")
   
    eHasComponent.setProperty("adminId", adminId)
    graph.commit
    
    new ConnStepToComponentSC(
        result = new ConnStepToComponentResultSC(
            true, //TODO Status implementieren
            s"Der Step mit id=$outStep wurde mit der Komponente mit id=$inComponent verbunden"
        )
    )
  }
  
  def hasComponent(componentCS: ComponentCS, componentSC: ComponentSC): OrientEdge = {
    val graph: OrientGraph = OrientDB.getGraph
    
    val stepId = componentCS.params.stepId
    val componentId = componentSC.result.componentId
    
    val eHasComponent = graph.addEdge(
        "class:" + PropertyKey.EDGE_HAS_COMPONENT, 
        graph.getVertex(stepId), 
        graph.getVertex(componentId), 
        PropertyKey.EDGE_HAS_COMPONENT
    )
    graph.commit
    
    eHasComponent
  }
}