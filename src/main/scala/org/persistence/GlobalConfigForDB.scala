package org.persistence

object GlobalConfigForDB {
  val activeDB: String = "test"
  //Test
  def db: DB = {
    activeDB match {
      case "test" => new TestDBv011
    }
  }
}

abstract class DB {
  val dbName: String
  val username: String
  val password: String
}

class TestDB extends DB {
  override val dbName = "testDB"
  override val username = "root"
  override val password = "root"
}

class TestDBv011 extends DB {
  override val dbName = "testDBv011"
  override val username = "root"
  override val password = "root"
}