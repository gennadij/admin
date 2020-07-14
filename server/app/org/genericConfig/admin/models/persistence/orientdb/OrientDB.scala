package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientGraphFactory}
import org.genericConfig.admin.models.persistence.TestDB

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
 */

object OrientDB {

  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.3
   * 
   * @param
   * 
   * @return
   */
  
  def getFactory(): OrientGraphFactory = {
    
    val db = TestDB()
    
    val databaseFactory: OrientGraphFactory = db match {
      case testDB: TestDB => 
        val uri = "remote:localhost/" + testDB.dbName
        new OrientGraphFactory(uri, testDB.username, testDB.password)
    }
    
    databaseFactory
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param SQL-Query -> SQL-Command that start with select
   * 
   * @return OrientDynaElementIterable
   */
  
  def executeSQLQuery(sqlQuery: String): Any = {
    new OrientDB().executeSQLQuery(sqlQuery)
  }
  
  def getGraph: OrientGraph = {
    new OrientDB().getGraph
  }
}

class OrientDB {
  
  private def getGraph = {
    val activeDb = TestDB()
    val uri = "remote:localhost/" + activeDb.dbName
    val graphFactory = new OrientGraphFactory(uri, activeDb.username, activeDb.password)
    graphFactory.getTx
  }
  
  def executeSQLQuery(sql: String): Any = {
    
    val graph: OrientGraph = getGraph
    
    val result: Any = try {
		  val result: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
		  graph.commit()
		  result
		}catch{
		  case e: Exception => 
		    graph.rollback()
		    e
		}finally {
		  graph.shutdown()
		}
		
		result
  }
  
}
