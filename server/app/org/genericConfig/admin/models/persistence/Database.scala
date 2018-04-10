package org.genericConfig.admin.models.persistence

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */
object Database {
  def getFactory(): OrientGraphFactory = {
    
    new Database("OrientDB").getFactory()
  }
}

class Database(name: String) {
  
  private def getFactory(): OrientGraphFactory = {
    
    val db = TestDB()
    
    db match {
      case testDB: TestDB => 
        val uri = "remote:localhost/" + testDB.dbName
        new OrientGraphFactory(uri, testDB.username, testDB.password)
    }
  }
}

case class TestDB() {
    def dbName: String = "testDB"
    def username: String = "root"
    def password: String = "root"
}