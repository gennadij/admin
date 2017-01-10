package org.persistence.db.orientdb

import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertex

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

object HasConfigEdge {
  
  def hasConfig(adminId: String, vConfig: OrientVertex): OrientEdge = {
    //TODO Try Catch Block
    
    val graph: OrientGraph = OrientDB.getGraph()
    val outVertex = graph.getVertex(adminId)
      val eHasConfig: OrientEdge = graph.addEdge(
        "class:" + PropertyKey.EDGE_HAS_CONFIG, 
         outVertex, 
         vConfig, 
         PropertyKey.EDGE_HAS_CONFIG
      )
    graph.commit
    
    eHasConfig
  }
}