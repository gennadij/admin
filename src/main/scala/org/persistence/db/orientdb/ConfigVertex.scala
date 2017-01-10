package org.persistence.db.orientdb

import org.dto.config.CreateConfigCS
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertex

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */
object ConfigVertex {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param CreateConfig
   * 
   * @return RegistrationSC
   */
  def createConfig(createConfigCS: CreateConfigCS): OrientVertex = {
    
    //TODO Try Catch Block einbauen -> Nullpointer Exception fangen
    
    val graph: OrientGraph = OrientDB.getGraph

    val vConfig: OrientVertex = graph.addVertex(
        "class:" + PropertyKey.VERTEX_CONFIG,
        PropertyKey.CONFIG_URL, createConfigCS.params.configUrl
    )
    graph.commit
    
    vConfig
  }
}