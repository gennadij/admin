package org.genericConfig.admin.models.persistence.db.orientdb

import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.models.json.StatusErrorWriteToDB
import org.genericConfig.admin.models.json.StatusSuccessfulConfig
import play.api.Logger

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

object HasConfigEdge {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param adminId: String, vConfig: OrientVertex
   * 
   * @return OrientEdge
   */
  def hasConfig(adminId: String, vConfig: OrientVertex): (Option[OrientEdge], String) = {
    
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
    val eHasConfig: Any = try{
      val outVertex = graph.getVertex(adminId)
      val eHasConfig: OrientEdge = graph.addEdge(
        "class:" + PropertyKey.EDGE_HAS_CONFIG, 
         outVertex, 
         vConfig, 
         PropertyKey.EDGE_HAS_CONFIG
      )
      graph.commit
      eHasConfig
    }catch{
      case e : Exception => graph.rollback()
    }
    
    Logger.info(eHasConfig.toString())
    
    eHasConfig match {
      case eHasConfig: OrientEdge => (Some(eHasConfig), StatusSuccessfulConfig.status)
      case e : Exception => {
        Logger.info(e.printStackTrace().toString())
        (None, StatusErrorWriteToDB.status)
      }
      case _ => {
        Logger.info("Unbekannt")
        (None, StatusErrorWriteToDB.status)
      }
    }
  }
}