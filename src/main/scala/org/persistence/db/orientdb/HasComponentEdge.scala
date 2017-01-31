package org.persistence.db.orientdb

import scala.collection.JavaConversions._

import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import org.dto.component._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
 */

object HasComponentEdge {
  
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