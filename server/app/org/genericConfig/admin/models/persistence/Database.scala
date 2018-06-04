package org.genericConfig.admin.models.persistence

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.orientechnologies.orient.core.exception.OStorageException
import play.api.Logger
import org.genericConfig.admin.shared.common.status.Status
import org.genericConfig.admin.shared.common.status.Success
import org.genericConfig.admin.shared.common.status.ODBConnectionFail

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */
object Database {
  def getFactory(): (Option[OrientGraphFactory], Status) = {
    try {
      val db = new Database().getFactory()
      Logger.info(db.toString)
//      db.exists()
      (Some(db), Success())
    }catch{
          case e: Exception => {
            Logger.error(e.printStackTrace().toString)
            (None, ODBConnectionFail())
          }
    }
}

class Database {

  def getFactory(): OrientGraphFactory = {

    val db = TestDB()

    db match {
      case testDB: TestDB =>
        val uri = "remote:localhost/" + testDB.dbName

          new OrientGraphFactory(uri, testDB.username, testDB.password)

        }

    }
  }
}

case class TestDB() {
    def dbName: String = "testDB"
    def username: String = "root"
    def password: String = "root"
}