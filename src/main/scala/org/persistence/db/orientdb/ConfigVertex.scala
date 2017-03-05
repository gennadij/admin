package org.persistence.db.orientdb

import scala.collection.JavaConversions._
import org.dto.config.CreateConfigCS
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.orientechnologies.orient.core.sql.OCommandSQL
import org.dto.configTree.ConfigTreeSC
import org.dto.configTree.ConfigTreeCS
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import org.dto.configTree.ConfigTreeStepSC
import org.dto.configTree.ConfigTreeComponentSC
import org.dto.configTree.ConfigTreeResultSC
import com.tinkerpop.blueprints.Direction

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */
object ConfigVertex {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
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
   * @version 0.1.0
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
   * @version 0.1.0
   * 
   * @param ConfigTreeCS
   * 
   * @return ConfigTreeSC
   */
  def getConfigTree(configTreeCS: ConfigTreeCS): ConfigTreeSC = {
    val graph: OrientGraph = OrientDB.getGraph
    val configId: String = configTreeCS.params.configId
    
    val sql: String = s"select from (traverse out() from $configId STRATEGY BREADTH_FIRST) where @class='Step'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()

    val vStepsList: List[OrientVertex] = res.toList.map(_.asInstanceOf[OrientVertex])
    
    val vSteps: Set[OrientVertex] = vStepsList.toSet
    println(vSteps)
    
    new ConfigTreeSC(result = new ConfigTreeResultSC(vSteps.map(getStep(_, graph)), ""))
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param vStep: OrientVertex, graph: OrientGraph
   * 
   * @return ConfigTreeStepSC
   */
  private def getStep(vStep: OrientVertex, graph: OrientGraph): ConfigTreeStepSC = {
      val eHasComponent: Set[Edge] = vStep.getEdges(Direction.OUT).toSet
      val vComponents: Set[Vertex] = eHasComponent.map { hC => hC.getVertex(Direction.IN) }
      
      new ConfigTreeStepSC(
          vStep.getIdentity.toString,
          vStep.getProperty("kind").toString(),
          getComponents(vComponents)
      )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param vComponents: Set[Vertex]
   * 
   * @return Set[ConfigTreeComponentSC]
   */
  private def getComponents(vComponents: Set[Vertex]): Set[ConfigTreeComponentSC] = {
  vComponents.map({ vC => 
        new ConfigTreeComponentSC(
            vC.getId.toString(),
            vC.getProperty("kind").toString()
            ,getNextStep(vC)
        )
      })
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
  private def getNextStep(component: Vertex): String = {
    val eNextStep: List[Edge] = component.getEdges(Direction.OUT).toList
    val vNextStep: List[Vertex] = eNextStep.map ( { eNS => 
      eNS.getVertex(Direction.IN)
    })
    if(vNextStep.size == 1) vNextStep.head.getId.toString() else "no nextStep"
  }
}