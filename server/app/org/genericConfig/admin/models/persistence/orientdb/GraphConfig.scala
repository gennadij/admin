package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import org.genericConfig.admin.models.common.{Error, ODBClassCastError, ODBConnectionFail, ODBRecordDuplicated, ODBWriteError}
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.config.status.{DeleteConfigDefectID, DeleteConfigError, DeleteConfigSuccess, StatusDeleteConfig}
import play.api.Logger

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
  def addConfig(configUrl: String): (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphConfig(graph).addConfig(configUrl)
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
      case (None, Some*ODBConnectionFail()) =>
        Some(ODBConnectionFail())
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param configId : String, configUrl: String
   * @return (StatusDeleteConfig, Error)
   */
  def deleteConfig(configId: String, configUrl: String): (StatusDeleteConfig, Error) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphConfig(graph).deleteConfig(configId, configUrl)
      case (None, Some(ODBConnectionFail())) =>
        (DeleteConfigError(), ODBConnectionFail())
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
  private def addConfig(configUrl: String): (Option[OrientVertex], Option[Error]) = {
    try {
      val vConfig: OrientVertex = graph.addVertex(
        "class:" + PropertyKeys.VERTEX_CONFIG,
        PropertyKeys.CONFIG_URL, configUrl)
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
   * @return (StatusDeleteConfig, Status)
   */
  private def deleteConfig(configId: String, configUrl: String): (StatusDeleteConfig, Error) = {
    try {
      val sql: String = s"DELETE VERTEX Config where @rid=$configId and configUrl='$configUrl'"
      val res: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit()
      res match {
        case 1 => (DeleteConfigSuccess(), Success())
        case _ => (DeleteConfigDefectID(), Error())
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (DeleteConfigError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (DeleteConfigError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (DeleteConfigError(), ODBWriteError())
    }
  }
}