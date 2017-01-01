/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.persistence.db.orientdb

import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import org.dto.connComponentToStep.ConnComponentToStepCS
import org.dto.connComponentToStep.ConnComponentToStepSC
import org.dto.step.StepSC
import org.dto.connComponentToStep.ConnComponentToStepResultSC

/**
 * Created by Gennadi Heimann 1.1.2017
 */

object NextStepEdge {
  
  val classname = "nextStep"
  val propKeyNextStepId = "nextStepId"
  val propKeyAdminId = "adminId"
  val propKeyComponentId = "componentId"
  val propKeyStepId = "stepId"
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param
   * 
   * @return
   */
  def add(connComponentToStepCS: ConnComponentToStepCS): ConnComponentToStepSC = {
    val graph: OrientGraph = OrientDB.getGraph
    val adminId: String = connComponentToStepCS.params.adminId
    val outComponentId: String = connComponentToStepCS.params.outComponentId
    val inStepId: String = connComponentToStepCS.params.inStepId
     val eNextStep: OrientEdge = graph.addEdge("class:nextStep", 
       graph.getVertex(outComponentId), 
        graph.getVertex(inStepId), 
       "nextStep")
     eNextStep.setProperty("adminId", adminId)
  	 graph.commit
  	 
  	 new ConnComponentToStepSC(
  	     result = new ConnComponentToStepResultSC(
  	         true,
  	         s"Die Componnet mit id=$outComponentId wurde mit Step mit id=$inStepId verbunden"
  	     )
  	 )
  }
  
}