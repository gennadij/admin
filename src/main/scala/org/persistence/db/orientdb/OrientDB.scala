package org.persistence.db.orientdb

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal

object OrientDB {
  
  
  def setGraph(){
    
  }
  
  def getGraph(): OrientGraph = {
    val xmlDBConfig = scala.xml.XML.loadFile("global_config/globalConfig.xml")
    
    val dbName = (xmlDBConfig \ "globalConfig" \ "db" \ "dbName").text
    val username = (xmlDBConfig \ "globalConfig" \ "db" \ "username").text
    val password = (xmlDBConfig \ "globalConfig" \ "db" \ "password").text
    
    val uri = if(System.getProperty("os.name") == "Linux") 
                "remote:localhost/" + dbName 
              else 
                "remote:localhost/" + dbName
    
    val factory:  OrientGraphFactory = new OrientGraphFactory(uri, username, password)
    factory.getTx()
  }
  
  def getGraph(db: String, username: String, password: String) = {
    val uri = if(System.getProperty("os.name") == "Linux") 
                "remote:localhost/" + db 
              else 
                "remote:localhost/" + db
    
    val factory:  OrientGraphFactory = new OrientGraphFactory(uri, username, password)
    factory.getTx()
  }
  
  
  private def getDBConfig = {
    val xmlDBConfig = scala.xml.XML.loadFile("global_config/globalConfig.xml")
    
    val dbName = (xmlDBConfig \ "globalConfig" \ "db" \ "dbName").text
    val username = (xmlDBConfig \ "globalConfig" \ "db" \ "username").text
    val password = (xmlDBConfig \ "globalConfig" \ "db" \ "password").text
  }
}