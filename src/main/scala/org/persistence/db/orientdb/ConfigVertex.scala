package org.persistence.db.orientdb

import org.dto.Config.CreateConfigCS
import org.dto.Config.CreateConfigSC
import org.dto.Config.CreateConfigResult
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertex

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 
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
  def createConfig(createConfigCS: CreateConfigCS): CreateConfigSC = {
     val graph: OrientGraph = OrientDB.getGraph
     
     val vConfig: OrientVertex = graph.addVertex(
         "class:" + PropertyKey.CONFIG,
         PropertyKey.CONFIG_URL, createConfigCS.params.configUrl)
     graph.commit
    CreateConfigSC(
        result = CreateConfigResult(
            vConfig.getIdentity.toString,
            true,
            "Die Konfiguration wurde erfolgreich erzeugt"
        )
    )
  }
  
}