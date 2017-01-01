/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.persistence.db.orientdb

import scala.collection.JavaConversions._

import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.dto.firstStep.FirstStepCS
import org.dto.firstStep.FirstStepSC
import org.dto.firstStep.FirstStepResultSC
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import org.dto.step.StepCS
import org.dto.step.StepSC
import org.dto.step.StepResultSC

/**
 * Created by Gennadi Heimann 1.1.2017
 */

object StepVertex {
  val propClassName = "Step"
  val propKeyId = "stepId"
  val propKeyAdminId = "adminId"
  val propKeyKind = "kind"
  
    /**
   * 
   * fuegt Vertex Step zu ConfigTree hinzu
   * 
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param AdminStep
   * 
   * @return Status
   */
  
  def addFirstStep(firstStepCS: FirstStepCS): FirstStepSC = {
    val graph: OrientGraph = OrientDB.getGraph
    val vStep: OrientVertex = graph.addVertex("class:" + PropertyKey.STEP, 
            PropertyKey.ADMIN_ID, firstStepCS.params.adminId,
            PropertyKey.KIND, firstStepCS.params.kind)
        graph.commit
        
        new FirstStepSC(
            result = new FirstStepResultSC(
                vStep.getIdentity.toString(),
                true, //TODO Status implementieren
                message = "Erste Schritt wurde zu Ihre Konfiguration hinzugefügt"
            )
        )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param
   * 
   * @return
   */
  def addStep(stepCS: StepCS): StepSC = {
    val graph: OrientGraph = OrientDB.getGraph
    
    val vStep: OrientVertex = graph.addVertex("class:" + PropertyKey.STEP, 
            PropertyKey.ADMIN_ID, stepCS.params.adminId,
            PropertyKey.KIND, stepCS.params.kind)
        graph.commit
        
        new StepSC(
            result = new StepResultSC(
            	vStep.getIdentity.toString(),
            	true//TODO impl status
            	,"Der Step wurde hinzugefuegt"
            )
        )
  }

  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param
   * 
   * @return
   */
  def removerSteps(adminId: String) = {
    val graph: OrientGraph = OrientDB.getGraph
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX Step where adminId='$adminId'")).execute()
    graph.commit
    res
  }
}