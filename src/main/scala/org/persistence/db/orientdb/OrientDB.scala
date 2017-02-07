package org.persistence.db.orientdb

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory

import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.orientechnologies.orient.core.db.ODatabaseRecordThreadLocal
import org.persistence.GlobalConfigForDB

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
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
    
//    val xmlDBConfig = scala.xml.XML.loadFile("global_config/globalConfig.xml")
//    
//    val activeDB: String = (xmlDBConfig \"activeDB" \ "dbName").text
//    val dbs : scala.xml.NodeSeq = (xmlDBConfig \ "db")
//    
//    val db: List[DB] = dbs.filter (k => (k \ "dbName").text == activeDB ).map(db =>{
//      new DB((db \ "kind").text, (db \ "dbName").text, (db \ "username").text, (db \ "password").text)
//    }).toList
    
    val uri = if(System.getProperty("os.name") == "Linux") 
      "remote:localhost/" + GlobalConfigForDB.db.dbName
//                "remote:localhost/" + db(0).dbName
              else 
//                "remote:localhost/" + db(0).dbName
      "remote:localhost/" + GlobalConfigForDB.db.dbName
    
                
                
                
//    val factory:  OrientGraphFactory = new OrientGraphFactory(uri, db(0).username, db(0).password)
      val factory:  OrientGraphFactory = new OrientGraphFactory(uri, GlobalConfigForDB.db.username, GlobalConfigForDB.db.password)
    factory.getTx()
  }
}

case class DB (
    kind: String,
    dbName: String,
    username: String,
    password: String
    )
