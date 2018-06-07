package org.genericConfig.admin.models.user


import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.persistence.OrientDB
import play.api.Logger
import util.CommonFunction

import scala.collection.JavaConverters._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.01.2017
  */
class PrepareUser extends CommonFunction{

  val userWithAlredyExistingUser =         "userExist"
  val userLogin =                          "user2"

  def prepareWithAlredyExistingUser(wC: WebClient): Unit = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userWithAlredyExistingUser'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit()
    val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString.toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userWithAlredyExistingUser ist schon erstellt worden")
    }else {
      registerNewUser(userWithAlredyExistingUser, wC)
    }
  }

  def prepareLogin(wC : WebClient): Unit = {

    val graph: OrientGraph = OrientDB.getFactory().getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userLogin'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit()
    val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString.toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userLogin ist schon erstellt worden")
    }else{
      registerNewUser(userLogin, wC)

      val adminId: String = login(userLogin, wC)

      println("adminId " + adminId)

      val configId: String = createNewConfig(adminId, "http://contig1/user2", wC)

      println("configId " + configId)
    }
  }

}
