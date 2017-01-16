package org.persistence.db.orientdb

import scala.collection.JavaConversions._
import org.dto.config.CreateConfigCS
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.orientechnologies.orient.core.sql.OCommandSQL
import org.dto.configTree.ConfigTreeSC
import org.dto.configTree.ConfigTreeCS
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */
object ConfigVertex {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param CreateConfig
   * 
   * @return RegistrationSC
   */
  def createConfig(createConfigCS: CreateConfigCS): OrientVertex = {
    
    //TODO Try Catch Block einbauen -> Nullpointer Exception fangen
    val graph: OrientGraph = OrientDB.getGraph

    val vConfig: OrientVertex = graph.addVertex(
        "class:" + PropertyKey.VERTEX_CONFIG,
        PropertyKey.CONFIG_URL, createConfigCS.params.configUrl
    )
    graph.commit
    
    vConfig
  }
  
  /**
   * Loescht alle Steps und Components die zu der Config gehoeren
   * 
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param configId
   * 
   * @return Count from deleted Vertexes
   */
  
    def deleteAllStepsAndComponent(configId: String) = {
      val sql: String = s"DELETE VERTEX V where @rid IN (traverse out() from (select out('hasFirstStep') " + 
        s"from Config where @rid='$configId'))"
      val graph: OrientGraph = OrientDB.getGraph
      val res: Int = graph
        .command(new OCommandSQL(sql)).execute()
      graph.commit
      res
  }
    
  def deleteConfigVertex(username: String): Int = {
    val sql: String = s"DELETE VERTEX Config where @rid IN (SELECT OUT('hasConfig') FROM AdminUser WHERE username='$username')"
      val graph: OrientGraph = OrientDB.getGraph
      val res: Int = graph
        .command(new OCommandSQL(sql)).execute()
      graph.commit
      res
  }
    
  /**
   * 
   * 
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param configId
   * 
   * @return Count from deleted Vertexes
   */
  def getConfigTree(configTreeCS: ConfigTreeCS): ConfigTreeSC = {
    val graph: OrientGraph = OrientDB.getGraph
    val configId: String = configTreeCS.params.adminId
    
    //traverse OUT()from #41:6 STRATEGY BREADTH_FIRST
    
    val res: OrientDynaElementIterable = graph
      .command(new OCommandSQL(s"traverse out() from $configId STRATEGY BREADTH_FIRST")).execute()
    
    val vertexes: List[OrientVertex] = res.toList.map(_.asInstanceOf[OrientVertex])
    
    vertexes.foreach(vertex => println(vertex.getType.toString()))
    
    
    val filterd = vertexes.filter(vertex => vertex.getType.toString() == "Step" && vertex.getProperty("kind") == "first")
    
    println(filterd)
    
    
    
    
//    new ConfigTreeSC(result = new ConfigTreeResultSC(vSteps.map(getStep(_, graph, adminId)), ""))
  null
  
  }
}