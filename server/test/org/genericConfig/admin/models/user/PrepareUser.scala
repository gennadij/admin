package org.genericConfig.admin.models.user


import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.persistence.OrientDB
import play.api.Logger

import scala.collection.JavaConverters._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 13.01.2017
  */
class PrepareUser extends CommonFunction{


  val userLogin =                          "user2"



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

      val adminId: String = getUserId(userLogin, wC)

      println("adminId " + adminId)

      val (configId, _) = addConfig(adminId, "http://contig1/user2")

      println("configId " + configId)
    }
  }

}
