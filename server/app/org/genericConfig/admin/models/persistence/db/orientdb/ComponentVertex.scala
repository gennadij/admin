package org.genericConfig.admin.models.persistence.db.orientdb

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.persistence.OrientDB
import com.tinkerpop.blueprints.Direction
import org.genericConfig.admin.models.wrapper.component.ComponentIn
import org.genericConfig.admin.models.wrapper.component.ComponentOut
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.Edge

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
 */
object ComponentVertex {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param
   * 
   * @return
   */
  def component(componentCS: ComponentIn): Option[OrientVertex] = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
    val vComponent: Any = try{
      val vComponent: OrientVertex = graph.addVertex(
          "class:" + PropertyKey.VERTEX_COMPONENT, 
          PropertyKey.NAME_TO_SHOW, componentCS.nameToShow,
          PropertyKey.KIND, componentCS.kind
      )
    graph.commit
    vComponent
    }catch{
      case e: Exception => graph.rollback()
    }
    
    vComponent match {
      case vComponent: OrientVertex => Some(vComponent)
      case e: Exception => None
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.3
   * 
   * @param componentId
   * 
   * @return all siblings 
   */
  
  def getAllSiblings(componentId: String): Option[List[OrientVertex]] = {
//    select expand(in('hasComponent').out('hasComponent')) from Component where @rid='#46:1'
    
    val sql = s"select expand(in('hasComponent').out('hasComponent')) from Component where @rid='$componentId'"
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
    try{
      val queryResult: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
      graph.commit
      Some(queryResult.asScala.toList.map(_.asInstanceOf[OrientVertex]))
    }catch{
      case e: Exception => 
        graph.rollback()
        None
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param componentId
   * 
   * @return FatherStep
   */
  def getFatherStep(componentId: String): Option[OrientVertex] = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
    try{
    	val eHasSteps: List[Edge] = graph.getVertex(componentId).getEdges(Direction.IN, PropertyKey.EDGE_HAS_COMPONENT).asScala.toList
    	eHasSteps.size match {
          case count if count == 1 => Some(eHasSteps.head.getVertex(Direction.OUT).asInstanceOf[OrientVertex])
          case _ => None
      }
    }catch{
      case e: Exception => {
        graph.rollback() 
        None
      }
    }
  }
  
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param
   * 
   * @return
   */
//  def deleteComponent(adminId: String) = {
//    val graph: OrientGraph = OrientDB.getGraph
//    val res: Int = graph
//      .command(new OCommandSQL(s"DELETE VERTEX Component where adminId='$adminId'")).execute()
//    graph.commit
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param
   * 
   * @return
   */
  def update() = ???
  
  def deleteComponents(stepId: String): Int = {
//    delete VERTEX Component where @rid in (select out('hasConfig').out('hasFirstStep').out('hasComponent') from AdminUser where username='user6')
//    delete VERTEX Component where @rid in (select @rid from Step where @rid='')
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val sql: String = s"delete VERTEX Component where @rid in " + 
      s"(select out('hasComponent') from Step where @rid='$stepId')"
    val res: Int = graph
      .command(new OCommandSQL(sql)).execute()
    graph.commit
    res
  }
}