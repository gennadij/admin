package org.genericConfig.admin.models.persistence.db.orientdb

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.persistence.OrientDB
import com.tinkerpop.blueprints.Direction
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
}