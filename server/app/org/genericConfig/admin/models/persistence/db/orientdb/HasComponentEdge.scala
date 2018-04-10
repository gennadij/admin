package org.genericConfig.admin.models.persistence.db.orientdb

import scala.collection.JavaConverters._

import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import org.genericConfig.admin.models.persistence.OrientDB
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.wrapper.component.ComponentIn

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
 */

object HasComponentEdge {
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param componentCS: ComponentCS, componentSC: ComponentSC
   * 
   * @return OrientEdge
   */
  
  def hasComponent(componentCS: ComponentIn, vComponent: OrientVertex): Option[OrientEdge] = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
    try{
      val stepId: String = componentCS.stepId
      val componentId: String = vComponent.getIdentity.toString
    
      val eHasComponent = graph.addEdge(
          "class:" + PropertyKey.EDGE_HAS_COMPONENT, 
          graph.getVertex(stepId), 
          graph.getVertex(componentId), 
          PropertyKey.EDGE_HAS_COMPONENT
      )
      graph.commit
    
      Some(eHasComponent)
    }catch{
      case e: Exception => graph.rollback(); None
    }
  }
}