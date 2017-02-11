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
    
    val uri = if(System.getProperty("os.name") == "Linux") 
    "remote:localhost/" + GlobalConfigForDB.db.dbName
      else 
    "remote:localhost/" + GlobalConfigForDB.db.dbName
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
