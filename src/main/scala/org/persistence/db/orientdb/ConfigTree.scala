/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.persistence.db.orientdb

import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.orientechnologies.orient.core.sql.OCommandSQL
import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.Direction
import org.dto.configTree.ConfigTreeCS
import org.dto.configTree.ConfigTreeSC
import org.dto.configTree.ConfigTreeResultSC
import org.dto.configTree.ConfigTreeStepSC
import org.dto.configTree.ConfigTreeComponentSC

object ConfigTree {
  def getConfigTree(configTreeCS: ConfigTreeCS): ConfigTreeSC = {
    val graph: OrientGraph = OrientDB.getGraph
    val adminId: String = configTreeCS.params.adminId
    
    val res: OrientDynaElementIterable = graph
      .command(new OCommandSQL("select from " + 
          "(SELECT FROM " + 
                "(traverse out(hasComponent) from " + 
                      "(select from Step where kind='first') STRATEGY BREADTH_FIRST)" + 
                 s" where @class='Step') where adminId='$adminId'")).execute()
      
    val vSteps: List[OrientVertex] = res.toList.map(_.asInstanceOf[OrientVertex])
    
    new ConfigTreeSC(result = new ConfigTreeResultSC(vSteps.map(getStep(_, graph, adminId)), ""))
  }
  
  
  private def getStep(vStep: OrientVertex, graph: OrientGraph, adminId: String): ConfigTreeStepSC = {
      val eHasComponent: List[Edge] = vStep.getEdges(Direction.OUT).toList
      val vComponents: List[Vertex] = eHasComponent.map { hC => hC.getVertex(Direction.IN) }
      
      new ConfigTreeStepSC(
          vStep.getIdentity.toString,
          vStep.getProperty("kind").toString(),
          getComponents(vComponents)
      )
  }
  
  private def getComponents(vComponents: List[Vertex]): List[ConfigTreeComponentSC] = {
  vComponents.map({ vC => 
        new ConfigTreeComponentSC(
            vC.getId.toString(),
            vC.getProperty("kind").toString()
            ,getNextStep(vC)
        )
      })
  }
    
  private def getNextStep(component: Vertex): String = {
    val eNextStep: List[Edge] = component.getEdges(Direction.OUT).toList
    val vNextStep: List[Vertex] = eNextStep.map ( { eNS => 
      eNS.getVertex(Direction.IN)
    })
    if(vNextStep.size == 1) vNextStep.head.getId.toString() else "no nextStep"
  }
}