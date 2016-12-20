/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.persistence.db.orientdb

import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType
import com.orientechnologies.orient.core.metadata.schema.OType
import org.status.SuccessfulStatus
import org.status.WarningStatus
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import org.status.Status
import org.dto.connComponentToStep.ConnComponentToStepCS
import org.dto.connComponentToStep.ConnComponentToStepSC
import org.dto.step.StepSC
import org.dto.connComponentToStep.ConnComponentToStepResultSC


object NextStepEdge {
  
  val classname = "nextStep"
  val propKeyNextStepId = "nextStepId"
  val propKeyAdminId = "adminId"
  val propKeyComponentId = "componentId"
  val propKeyStepId = "stepId"
  
//  def createSchema(){
//    val graph: OrientGraph = OrientDB.getGraph
//    if(graph.getEdgeType("NextStep") == null){
//  	  val eNextStep: OrientEdgeType = graph.createEdgeType(classname)
//  	  eNextStep.createProperty(propKeyNextStepId, OType.STRING)
//  	  eNextStep.createProperty(propKeyAdminId, OType.STRING)
//  	  graph.commit
//  	  new SuccessfulStatus(s"Class $classname was created", "")
//    }else{
//      WarningStatus(s"Class $classname was already created", "")
//    }
//  }
  
//  def connect(nextSteps: Seq[NextStep]): List[Status] = {
//    val graph: OrientGraph = OrientDB.getGraph
//    val status: List[Status] = List.empty
//    val st = nextSteps.foreach(nS => {
//      if(nS.step != "S00000"){
//        if(graph.getEdges("nextStepId", nS.byComponent + nS.step).size == 0){
//      	  val eNextStep: OrientEdge = graph.addEdge(s"class:$classname", 
//      			  graph.getVertices(propKeyComponentId, nS.byComponent).head, 
//      			  graph.getVertices(propKeyStepId, nS.step).head, "nextStep")
//      		eNextStep.setProperty("nextStepId", nS.byComponent + nS.step)
//          graph.commit
//          status.::(new SuccessfulStatus("Edge with id = " + nS.byComponent + nS.step + " was created", ""))
//        }else{
//          status.::(new WarningStatus("Edge with id = " + nS.byComponent + nS.step + " already exist", ""))
//        }
//      }
//    })
//    status
//  }

//  def addNextStep(adminId: String, outComponent: String, inStep: String): StepSC = {
//    val graph: OrientGraph = OrientDB.getGraph
//    
//     val eNextStep: OrientEdge = graph.addEdge("class:nextStep", 
//       graph.getVertex(outComponent), 
//        graph.getVertex(inStep), 
//       "nextStep")
//     eNextStep.setProperty("adminId", adminId)
//     eNextStep.setProperty("nextStepId", "C" + outComponent + "S" + inStep )
//  	 graph.commit
//     
////  	 new NextStepSC(result: new NextStepResultSC(
////  	     inStep,
////  	     
////  	 ))
//    
//    null
////     new SuccessfulStatus("added nextStep", "C" + outComponent + "S" + inStep)
//  }
  
  
  
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