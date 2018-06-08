package org.genericConfig.admin.models.config

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.persistence.Database
import play.api.Logger
import util.CommonFunction

import scala.collection.JavaConverters._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.01.2017
  */
class PrepareConfig extends CommonFunction {

  val userAddingNewConfig =                "user3"
  val userTwoSameConfigUrls =              "user13"

  def prepareAddingNewConfig(wC: WebClient): Any = {
    val graph: OrientGraph = Database.getFactory._1.get.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userAddingNewConfig'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
    val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userAddingNewConfig ist schon erstellt worden")
    }else{
      registerNewUser(userAddingNewConfig, wC)

      getUserId(userAddingNewConfig, wC)
    }
  }

  def prepareTwoSameConfigUrls(wC: WebClient): Unit = {
    val graph: OrientGraph = Database.getFactory()._1.get.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userTwoSameConfigUrls'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    val count = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString.toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userTwoSameConfigUrls ist schon erstellt worden")
    }else {
      registerNewUser(userTwoSameConfigUrls, wC)

      val adminId_1 = getUserId(userTwoSameConfigUrls, wC)

      println("adminId " + adminId_1)

      val configId_1 = addConfig(adminId_1, s"http://config/$userTwoSameConfigUrls")

      println("ConfigId" + configId_1)
    }
  }

}
