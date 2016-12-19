/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.persistence.db.orientdb

import scala.collection.JavaConversions._

import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.status.SuccessfulStatus
import org.status.WarningStatus
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.status.Status
import org.dto.component._

object ComponentVertex {
  
  val propClassName = "Component"
  val propKeyId = "componentId"
  val propKeyAdminId = "adminId"
  
  
//  def addComponent(adminComponent: AdminComponent): Status = {
//    val graph: OrientGraph = OrientDB.getGraph()
//    if(graph.getVertices(propKeyId, component.id).size == 0){
//        val vertex: OrientVertex = graph.addVertex("class:Component", 
//            propKeyAdminId, component.adminId)
//        graph.commit
//        vertex.setProperty(propKeyId, "C" + vertex.getIdentity.toString())
//        graph.commit
//        vertex.getIdentity.toString
//    }else{
//      ""
//    }
//  }
  
  
  def addComponent(componentCS: ComponentCS): ComponentSC = {
    val graph: OrientGraph = OrientDB.getGraph
    
    val vComponent: OrientVertex = graph.addVertex(
        "class:Component", 
        "kind", componentCS.params.kind,
        "adminId", componentCS.params.adminId
    )
    
    graph.commit
    vComponent.setProperty("componentId", "C" + vComponent.getIdentity.toString())
    graph.commit
    
    new ComponentSC(
        result = new ComponentResultSC(
            vComponent.getIdentity.toString,
            true, // TODO Status implementieren
            message = ""
        )
    )
  }
  
  def deleteComponent(adminId: String) = {
    val graph: OrientGraph = OrientDB.getGraph
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX Component where adminId='$adminId'")).execute()
    graph.commit
    
  }
  
  
  
//  def get(id: String): AdminComponent = {
//    val graph: OrientGraph = OrientDB.getGraph
//    val vComponent = graph.getVertex(id)
//    new AdminComponent(
//        vComponent.getIdentity. toString,
//        vComponent.getProperty(PropertyKey.COMPONENT_ID),
//        vComponent.getProperty(PropertyKey.ADMIN_ID),
//        vComponent.getProperty(PropertyKey.KIND)
//    )
//  }
  
  
  
  
  
//  def addComponent(adminComponent: AdminComponent): AdminComponent = {
//    val graph: OrientGraph = OrientDB.getGraph
//    
//    val vComponent: OrientVertex = graph.addVertex(
//        "class:Component", 
//        "kind", adminComponent.kind,
//        "adminId", adminComponent.adminId
//    )
//    graph.commit
//    vComponent.setProperty("componentId", "C" + vComponent.getIdentity.toString())
//    graph.commit
//    new AdminComponent(
//        vComponent.getIdentity.toString,
//        "C" + vComponent.getIdentity,
//        adminComponent.adminId,
//        adminComponent.kind
//    )
//  }
  
  
  def createSchema(graph: OrientGraph) = {
    val graph: OrientGraph = OrientDB.getGraph
    if(graph.getVertexType(propClassName) == null){
      val vStep: OrientVertexType = graph.createVertexType(propClassName)
      vStep.createProperty(propKeyId, OType.STRING)
      vStep.createProperty(propKeyAdminId, OType.STRING)
      graph.commit
      new SuccessfulStatus("class Component was created", "")
    }else {
      new WarningStatus("class Component already exist", "")
    }
  }
  
  def create(props: Map[String, String]) = {
    val graph: OrientGraph = OrientDB.getGraph
    if(graph.getVertices(propKeyId, props(propKeyId)).size == 0){
        graph.addVertex(s"class:$propClassName", propKeyId, props(propKeyId), propKeyAdminId, props(propKeyAdminId))
        graph.commit
        new SuccessfulStatus("object Component with " + props(propKeyId) + " was created", "")
    }else{
      new WarningStatus("object Component with " + props(propKeyId) + "already exist", "")
    }
  }
  
   def update(graph: OrientGraph, props: Map[String, String]){
    //TODO bessere such Methode
    if(graph.getVertices("stepId", props("id")).size == 0){
//        graph.addVertex("class:Step", "stepId", props("id"))
//        graph.commit
      new WarningStatus("object Step with " + props("id") + "cannot update because not exist", "")
    }else{
      new SuccessfulStatus("object Step with " + props("id") + " was updated", "")
     
    }
  }
  
  def get() = ???
  
//  def components(stepId: String): List[Component] = {
//    val graph: OrientGraph = OrientDB.getGraph
//    val res: OrientDynaElementIterable = graph
//      .command(new OCommandSQL(s"select expand(out()) from Step where stepId='%stepId'")).execute()
//    res.toList.map(c => { new ImmutableComponent(c.asInstanceOf[OrientVertex].getProperty("componentId").toString(), "") })
//  }
  
}