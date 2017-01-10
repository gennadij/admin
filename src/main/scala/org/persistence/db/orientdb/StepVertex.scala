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
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import org.dto.step.StepCS
import org.dto.step.StepSC
import org.dto.step.StepResultSC
import org.dto.firstStep.FirstStepCS
import org.dto.firstStep.FirstStepResult
import org.dto.firstStep.FirstStepSC

/**
 * Created by Gennadi Heimann 1.1.2017
 */

object StepVertex {
  
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
    
    val vStep: OrientVertex = graph.addVertex("class:" + PropertyKey.VERTEX_STEP, 
            PropertyKey.ADMIN_ID, stepCS.params.adminId,
            PropertyKey.KIND, stepCS.params.kind,
            PropertyKey.SELECTION_CRITERIUM_MIN, stepCS.params.selectionCriterium.min.toString,
            PropertyKey.SELECTION_CRITERIUM_MAX, stepCS.params.selectionCriterium.max.toString
    )
    
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

  def firstStep(firstStepCS: FirstStepCS): FirstStepSC = {
    //TODO Try Catch Block einbauen -> Nullpointer Exception fangen
    
    val graph: OrientGraph = OrientDB.getGraph
    val configId = firstStepCS.params.configId
    val counts: OrientDynaElementIterable = graph
      .command(new OCommandSQL(s"select count(out('HasFirstStep')) from Config where @rid='$configId'")).execute()
      
      println(counts)
    val countsList = counts.toList
    println(countsList)
    println(countsList(0).asInstanceOf[OrientVertex].getProperty("count").toString.toInt)
    val count: Int = if(countsList.size == 1) countsList(0).asInstanceOf[OrientVertex].getProperty("count").toString.toInt else 2
    val vFirstStep: OrientVertex = graph.addVertex(
        "class:" + PropertyKey.VERTEX_STEP,
        PropertyKey.KIND, firstStepCS.params.kind
    )
    graph.commit
    
    if(count > 0) {
      new FirstStepSC(
          result = FirstStepResult(
              "",
              false,
              "Der FirstStep exstiert bereits"
          )
      )
    }else{
      new FirstStepSC(
          result = FirstStepResult(
              vFirstStep.getIdentity.toString,
              true,
              "Der erste Step wurde zu der Konfiguration hinzugefuegt"
          )
      )
    }
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