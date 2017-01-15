package org.persistence.db.orientdb

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
  def getConfigTree(configTreeCS: ConfigTreeCS): ConfigTreeSC = {
    val graph: OrientGraph = OrientDB.getGraph
    val adminId: String = configTreeCS.params.adminId
    
    val res: OrientDynaElementIterable = graph
      .command(new OCommandSQL("select from " + 
          "(SELECT FROM " + 
                "(traverse out(hasComponent) from " + 
                      "(select from Step where kind='first') STRATEGY BREADTH_FIRST)" + 
                 s" where @class='Step') where adminId='$adminId'")).execute()
      
//    val vSteps: List[OrientVertex] = res.toList.map(_.asInstanceOf[OrientVertex])
    
//    new ConfigTreeSC(result = new ConfigTreeResultSC(vSteps.map(getStep(_, graph, adminId)), ""))
  null
  
  }
}