/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.persistence.db.orientdb

import scala.collection.JavaConversions._

import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.dto.component._

/**
 * Created by Gennadi Heimann 1.1.2017
 */
object ComponentVertex {
  
  val propClassName = "Component"
  val propKeyId = "componentId"
  val propKeyAdminId = "adminId"
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param
   * 
   * @return
   */
  def addComponent(componentCS: ComponentCS): ComponentSC = {
    val graph: OrientGraph = OrientDB.getGraph
    
    val vComponent: OrientVertex = graph.addVertex(
        "class:Component", 
        "kind", componentCS.params.kind
//        "adminId", componentCS.params.adminId
    )
    graph.commit
    
    new ComponentSC(
        result = new ComponentResult(
            vComponent.getIdentity.toString,
            true, // TODO Status implementieren
            message = "Die Komponente wurde hinzugefuegt"
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
  def component(componentCS: ComponentCS): ComponentSC = {
    val graph: OrientGraph = OrientDB.getGraph
    
    val vComponent: OrientVertex = graph.addVertex(
        "class:Component", 
        "kind", componentCS.params.kind
    )
    graph.commit
    
    if(vComponent != null) {
      new ComponentSC(
          result = new ComponentResult(
              vComponent.getIdentity.toString,
              true, // TODO Status implementieren
              message = "Die Komponente wurde hinzugefuegt"
          )
      )
    }else{
      ComponentSC(
          result = ComponentResult(
              "",
              false,
              "Es ist einen Fehler aufgetreten"
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
  def deleteComponent(adminId: String) = {
    val graph: OrientGraph = OrientDB.getGraph
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX Component where adminId='$adminId'")).execute()
    graph.commit
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
  def update() = ???
}