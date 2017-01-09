package org.persistence.db.orientdb

import org.dto.Config.CreateConfigCS
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.impls.orient.OrientGraph

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

object HasConfigEdge {
  
  def hasConfig(adminId: String, configId: String): Boolean = {
    val graph: OrientGraph = OrientDB.getGraph()
    val outVertex = graph.getVertex(adminId)
    val inVertex = graph.getVertex(configId)
    println(outVertex + "  " +  inVertex)
    if(outVertex != null && inVertex != null) {
      val eHasConfig: OrientEdge = graph.addEdge(
        "class:" + PropertyKey.EDGE_HAS_CONFIG, 
         outVertex, 
         inVertex, 
         PropertyKey.EDGE_HAS_CONFIG
      )
      graph.commit
      true
    }else{
      false
    }
  }
}