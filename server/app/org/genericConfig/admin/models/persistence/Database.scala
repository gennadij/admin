package org.genericConfig.admin.models.persistence

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import org.genericConfig.admin.shared.common.status.{Error, ODBConnectionFail}
import play.api.Logger

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */
object Database {
  def getFactory(): (Option[OrientGraphFactory], Option[Error]) = {
    try {
      val db = new Database().getFactory()
      db.exists()
      (Some(db), None)
    }catch{
          case e: Exception => {
            Logger.error(e.printStackTrace().toString)
            (None, Some(ODBConnectionFail()))
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