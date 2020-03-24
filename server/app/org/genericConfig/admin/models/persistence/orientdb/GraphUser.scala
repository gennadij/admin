package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.models.common.{AddUserAlreadyExist, Error, GetUserNotExist, ODBClassCastError, ODBConnectionFail, ODBReadError, ODBRecordDuplicated, ODBWriteError, UnknownError}
import org.genericConfig.admin.models.persistence.Database
import play.api.Logger

import scala.collection.JavaConverters._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann ${date}
 */
object GraphUser {

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param username : String, password: String
   * @return (Option[OrientVertex], Option[Error])
   */
  def addUser(username: String, password: String): (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphUser(graph).addUser(username, password)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param username : String, password: String
   * @return (Option[OrientVertex], Option[Error])
   */
  def deleteUser(username: String, password: String): (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphUser(graph).deleteUser(username, password)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param username : String, password: String
   * @return (Option[OrientVertex], Option[Error])
   */
  def getUser(username: String, password: String): (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphUser(graph).getUser(username, password)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param oldUsername : String, newUsername: String
   * @return (Option[OrientVertex], Option[Error])
   */
  def updateUserName(oldUsername: String, newUsername : String): (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphUser(graph).updateUserName(oldUsername, newUsername)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }
}

class GraphUser(graph: OrientGraph) {
  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param username : String, password: String
   * @return (Option[OrientVertex], Option[Error])
   */
  private def addUser(username: String, password: String): (Option[OrientVertex], Option[Error]) = {
    try {
      val vUser: List[Vertex] = graph.getVertices(PropertyKeys.USERNAME, username).asScala.toList
      if (vUser.isEmpty) {
        val vAdminUser: OrientVertex = graph.addVertex("class:" + PropertyKeys.VERTEX_ADMIN_USER,
          PropertyKeys.USERNAME, username,
          PropertyKeys.PASSWORD, password)
        graph.commit()
        (Some(vAdminUser), None)
      } else if (vUser.size == 1) {
        (Some(vUser.head.asInstanceOf[OrientVertex]), Some(AddUserAlreadyExist()))
      } else {
        (None, Some(UnknownError()))
      }
    } catch {
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(message = e.printStackTrace().toString)
        (None, Some(ODBClassCastError()))
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBWriteError()))
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param username : String, password: String
   * @return (Option[OrientVertex], Option[Error])
   */
  private def deleteUser(username: String, password: String): (Option[OrientVertex], Option[Error]) = {
    try {
      val sql: String = s"DELETE VERTEX AdminUser where username='$username'"
      val countOfDeletedVertex: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit()
      if(countOfDeletedVertex == 1){
        (None, None)
      }else{
        (None, Some(UnknownError()))
      }
    } catch {
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(message = e.printStackTrace().toString)
        (None, Some(ODBClassCastError()))
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBReadError()))
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param username : String, password: String
   * @return (Option[OrientVertex], Option[Error])
   */
  private def getUser(username: String, password: String): (Option[OrientVertex], Option[Error]) = {

    try {
      val dynElemUsers: OrientDynaElementIterable =
        graph.command(new OCommandSQL(s"SELECT FROM AdminUser WHERE username='$username' and password='$password'")).execute()
      graph.commit()
      val vUsers: List[OrientVertex] = dynElemUsers.asScala.toList map (_.asInstanceOf[OrientVertex])

      vUsers match {
        case userCount if userCount.size == 1 => (Some(vUsers.head), None)
        case _ => (None, Some(GetUserNotExist()))
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, Some(ODBRecordDuplicated()))
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBClassCastError()))
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBReadError()))
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param newUsername : String, oldUsername: String
   * @return (Option[OrientVertex], Option[Error])
   */
  private def updateUserName(oldUsername: String, newUsername: String): (Option[OrientVertex], Option[Error]) = {

    try {
      val sql: String = s"update AdminUser set username='$newUsername' where username like '$oldUsername'"
      val res: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit()

      if(res == 1){
        (None, None)
      }else{
        (None, Some(UnknownError()))
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, Some(ODBRecordDuplicated()))
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(message = e.printStackTrace().toString)
        (None, Some(ODBClassCastError()))
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBWriteError()))
    }
  }
}