/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.persistence.db.orientdb

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal

/**
 * Created by Gennadi Heimann 1.1.2017
 */

object OrientDB {

  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param
   * 
   * @return
   */
  def getGraph(): OrientGraph = {
    
    val xmlDBConfig = scala.xml.XML.loadFile("global_config/globalConfig.xml")
    
    val activeDB: String = (xmlDBConfig \"activeDB" \ "dbName").text
    val dbs : scala.xml.NodeSeq = (xmlDBConfig \ "db")
    
    val db: List[DB] = dbs.filter (k => (k \ "dbName").text == activeDB ).map(db =>{
      new DB((db \ "kind").text, (db \ "dbName").text, (db \ "username").text, (db \ "password").text)
    }).toList
    
    val uri = if(System.getProperty("os.name") == "Linux") 
                "remote:localhost/" + db(0).dbName
              else 
                "remote:localhost/" + db(0).dbName
    
    val factory:  OrientGraphFactory = new OrientGraphFactory(uri, db(0).username, db(0).password)
    factory.getTx()
  }
}

case class DB (
    kind: String,
    dbName: String,
    username: String,
    password: String
    )
