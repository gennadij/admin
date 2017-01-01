/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

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
 * Created by Gennadi Heimann 1.1.2017
 */

object HasComponentEdge {
  
  val classname = "hasComponent"
  val propKeyHasComponentId = "hasComponentId"
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
}