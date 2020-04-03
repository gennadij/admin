package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.{OrientEdge, OrientGraph, OrientVertex}
import org.genericConfig.admin.models.common.{DeleteConfigDefectID, Error, ODBClassCastError, ODBConnectionFail, ODBReadError, ODBRecordDuplicated, ODBWriteError, UnknownError}
import org.genericConfig.admin.models.persistence.Database
import play.api.Logger

import scala.collection.JavaConverters._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann ${date}
 */
object GraphConfig {
  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param configUrl : String
   * @return (Option[OrientVertex], Option[Error])
   */
  def addConfig(configUrl: String, configurationsCourse: String): (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphConfig(graph).addConfig(configUrl, configurationsCourse)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param fromUserId : String, configId: String
   * @return Option[Error]
   */
  def appendConfigTo(fromUserId: String, toConfigId: String): Option[Error] = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphConfig(graph).appendConfigTo(fromUserId, toConfigId)
      case (None, Some(ODBConnectionFail())) =>
        Some(ODBConnectionFail())
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param configId : String, configUrl: String
   * @return (StatusDeleteConfig, Error)
   */
  def deleteConfig(configId: String): Option[Error] = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphConfig(graph).deleteConfig(configId)
      case (None, Some(ODBConnectionFail())) =>
        Some(ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId : String
    * @return (Option[List[OrientVertex\]\], StatusGetConfigs, Status)
    */
  def getConfigs(userId: String): (Option[List[OrientVertex]], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphConfig(graph).getConfigs(userId)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param configId : String, configUrl: String
   * @return (Option[OrientVertex], StatusUpdateConfig, Status)
   */
  def updateConfig(
                    configId: String, configUrl: Option[String], configurationCourse : Option[String]
                  ): (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphConfig(graph).updateConfig(configId, configUrl, configurationCourse)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }
}

class GraphConfig(graph: OrientGraph) {
  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param configUrl : String
   * @return (Option[OrientVertex], StatusAddConfig, Status)
   */
  private def addConfig(configUrl: String, configurationCourse: String): (Option[OrientVertex], Option[Error]) = {
    try {
      val vConfig: OrientVertex = graph.addVertex(
        "class:" + PropertyKeys.VERTEX_CONFIG,
        PropertyKeys.CONFIG_URL, configUrl,
        PropertyKeys.CONFIGURATION_COURSE, configurationCourse)
      graph.commit()
      (Some(vConfig), None)
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
        (None, Some(ODBWriteError()))
    }
  }
  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param fromUserId : String, configId: String
   * @return  Option[Error]
   */
  private def appendConfigTo(fromUserId: String, toConfigId: String): Option[Error] = {
    try {
      val vUser: OrientVertex = graph.getVertex(fromUserId)
      val vConfig: OrientVertex = graph.getVertex(toConfigId)
      graph.addEdge(
        "class:" + PropertyKeys.EDGE_HAS_CONFIG,
        vUser,
        vConfig,
        PropertyKeys.EDGE_HAS_CONFIG
      )
      graph.commit()
      None
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        Some(ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        Some(ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        Some(ODBWriteError())
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param configId : String, configUrl: String
   * @return Option[Error]
   */
  private def deleteConfig(configId: String): Option[Error] = {
    try {
      val sql: String = s"DELETE VERTEX Config where @rid=$configId"
      val res: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit()
      res match {
        case 1 => None
        case _ => Some(DeleteConfigDefectID())
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        Some(ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        Some(ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        Some(ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId : String
    * @return (Option[List[OrientVertex\]\], Option[Error])
    */
    private def getConfigs(userId: String): (Option[List[OrientVertex]], Option[Error]) = {
      try {
        val vConfigs: List[OrientVertex] =
          graph.getVertex(userId).getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_CONFIG).asScala.toList map (
            _.asInstanceOf[OrientEdge].getVertex(Direction.IN))
        (Some(vConfigs), None)
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
   * @param configId : String, configUrl: String
   * @return (Option[OrientVertex], StatusUpdateConfig, Status)
   */
  private def updateConfig(
                            configId: String, configUrl: Option[String], configurationCourse : Option[String]
                          ): (Option[OrientVertex], Option[Error]) = {
    try {
      //TODO die ConfigurationsCourse beim Update soll immmer gesetzt werden
      (configUrl, configurationCourse) match {
        case (Some(configUrl), None) =>
          graph.getVertex(configId).setProperties(
            PropertyKeys.CONFIG_URL, configUrl,
            PropertyKeys.CONFIGURATION_COURSE, PropertyKeys.CONFIGURATION_COURSE_SEQUENCE
          )
//          graph.getVertex(configId).setProperty(PropertyKeys.CONFIG_URL, configUrl)
        case (None, Some(configurationCourse)) =>
          Logger.info(configurationCourse)
          graph.getVertex(configId).setProperty(
              PropertyKeys.CONFIGURATION_COURSE, configurationCourse
          )
        case (Some(configUrl), Some(configurationCourse)) =>
          graph.getVertex(configId).setProperties(
            PropertyKeys.CONFIG_URL, configUrl,
            PropertyKeys.CONFIGURATION_COURSE, configurationCourse
          )
          //TODO richtigen Fehler spezifizieren
        case (None, None) => (None, UnknownError())
      }

      graph.commit()

      val vUpdatedConfig = graph.getVertex(configId)

      (Some(vUpdatedConfig), None)
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None,  Some(ODBRecordDuplicated()))
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBClassCastError()))
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBWriteError()))
    }
  }
}