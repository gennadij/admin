package org.persistence.db.orientdb

import org.dto.ConfigTree.ConfigTreeCS
import org.admin.configTree.AdminConfigTree
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.orientechnologies.orient.core.sql.OCommandSQL
import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import org.admin.configTree.AdminConfigTreeStep
import com.tinkerpop.blueprints.Direction
import org.admin.configTree.AdminConfigTreeComponent
import org.dto.ConfigTree.ConfigTreeSC
import org.dto.ConfigTree.ConfigTreeResultSC
import org.dto.ConfigTree.ConfigTreeStepSC
import org.dto.ConfigTree.ConfigTreeComponentSC

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
    
    new ConfigTreeSC(params = new ConfigTreeResultSC(vSteps.map(getAdminStep(_, graph, adminId)), ""))
//    new AdminConfigTree(vSteps.map(getAdminStep(_, graph, adminId)))
  }
  
  
  private def getAdminStep(vStep: OrientVertex, graph: OrientGraph, adminId: String): ConfigTreeStepSC = {
      val eHasComponent: List[Edge] = vStep.getEdges(Direction.OUT).toList
      val vComponents: List[Vertex] = eHasComponent.map { hC => hC.getVertex(Direction.IN) }
      
      val stepId = if(vStep.getProperty("stepId").toString().substring(1) == vStep.getId.toString()){
          vStep.getProperty("stepId").toString().substring(1)}
        else {
          vStep.setProperty("stepId", vStep.getId.toString())
          graph.commit()
          "S" + vStep.getProperty("stepId").toString
        }
      
      new ConfigTreeStepSC(
          vStep.getIdentity.toString,
          stepId,
          adminId,
          vStep.getProperty("kind").toString(),
          getAdminComponents(vComponents)
      
      )
      
//      new AdminConfigTreeStep(
//          vStep.getIdentity.toString,
//          stepId,
//          adminId,
//          vStep.getProperty("kind").toString(),
//          getAdminComponents(vComponents)
//      )
  }
  
  private def getAdminComponents(vComponents: List[Vertex]): List[ConfigTreeComponentSC] = {
  vComponents.map({ vC => 
        new ConfigTreeComponentSC(
            vC.getId.toString(),
            vC.getProperty("componentId"),
            vC.getProperty("adminId").toString,
            vC.getProperty("kind").toString(),
            getNextStep(vC)
        )
      })
    
    
    
    
//    vComponents.map({ vC => 
//        new AdminConfigTreeComponent(
//            vC.getId.toString(),
//            vC.getProperty("componentId"),
//            vC.getProperty("adminId").toString,
//            vC.getProperty("kind").toString(),
//            getNextStep(vC)
//        )
//      })
  }
    
  private def getNextStep(component: Vertex): String = {
    val eNextStep: List[Edge] = component.getEdges(Direction.OUT).toList
    val vNextStep: List[Vertex] = eNextStep.map ( { eNS => 
      eNS.getVertex(Direction.IN)
    })
    if(vNextStep.size == 1) "NS" + vNextStep.head.getId.toString() else "no nextStep"
  }
}