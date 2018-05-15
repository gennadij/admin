package org.genericConfig.admin.models.persistence.db.orientdb

import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.genericConfig.admin.models.persistence.OrientDB

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
//  def hasFirstStep(firstStepCS: FirstStepIn, firstStepSC: FirstStepOut): Option[OrientEdge] = {
//    
//    val graph: OrientGraph = OrientDB.getFactory().getTx
//    
//    val eHasFirstStep: Any = try{
//      val vConfig = graph.getVertex(firstStepCS.configId)
//      val vFirstStep = graph.getVertex(firstStepSC.stepId)
//      val eHasFirstStep: OrientEdge = graph.addEdge(
//          "class:" + PropertyKey.EDGE_HAS_FIRST_STEP, 
//          vConfig, 
//          vFirstStep, 
//          PropertyKey.EDGE_HAS_FIRST_STEP
//      )
//      graph.commit
//      
//      eHasFirstStep
//    }catch{
//      case e: Exception => graph.rollback()
//    }
//    
//    eHasFirstStep match {
//      case eHasFirstStep: OrientEdge => Some(eHasFirstStep)
//      case e: Exception => None
//    }
//  }
}