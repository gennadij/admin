package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.models.common.{Error, ODBClassCastError, ODBConnectionFail, ODBRecordDuplicated, ODBWriteError}
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.component.{ComponentConfigPropertiesDTO, ComponentUserPropertiesDTO}
import play.api.Logger
import scala.collection.JavaConverters._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 19.05.2020
 */

object GraphComponent{
  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param userProperties : ComponentUserPropertiesDTO
    * @return (Option[OrientVertex], Option[Error])
    */
  def addComponent(userProperties : ComponentUserPropertiesDTO) : (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphComponent(graph).addComponent(userProperties)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.0
   * @param userProp : ComponentUserPropertiesDTO
   * @return (Option[OrientVertex], Option[Error])
   */
  def updateComponent(
                       userProp : ComponentUserPropertiesDTO,
                       configProp : ComponentConfigPropertiesDTO) : (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphComponent(graph).updateComponent(userProp, configProp)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }
}

class GraphComponent(graph: OrientGraph) {

    private def addComponent(userProperties: ComponentUserPropertiesDTO): (Option[OrientVertex], Option[Error]) = {
      try {
        //TODO der Eigenschaft Kind bei der Komponente muss definiert werden.
        val vComponent: OrientVertex = graph.addVertex(
          "class:" + PropertyKeys.VERTEX_COMPONENT,
          PropertyKeys.NAME_TO_SHOW, userProperties.nameToShow.get,
          PropertyKeys.KIND, "UNDEFINED"
        )
        graph.commit()
        (Some(vComponent), None)
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

  private def updateComponent(
                               userProp: ComponentUserPropertiesDTO,
                               configProp : ComponentConfigPropertiesDTO
                             ): (Option[OrientVertex], Option[Error]) = {
    try {
      //TODO leere String nicht erlauben -> Fehlermeldung
      val nameToShow : String = userProp.nameToShow.get
      val componentRId : String = RidToHash.getRId(configProp.componentId.get).get

      val updateParams : List[(String, Option[String])] = List(
        (PropertyKeys.NAME_TO_SHOW, Some(nameToShow))
      )

      val sql: String = s"update ${PropertyKeys.VERTEX_COMPONENT} set ${assembleProp(updateParams)} return after where @rid=$componentRId"

      val dbRes: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
      val vUpdatedComponent : OrientVertex = dbRes.asScala.toList.map(_.asInstanceOf[OrientVertex]).head

      graph.commit()

      (Some(vUpdatedComponent), None)
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
  //TODO Function verallgemeinern
  def assembleProp(params : List[(String, Option[String])]) : String = params match {
    case param :: rest => param._2 match {
      case Some(p) =>
        s"${param._1}='$p'${detectComa(rest)} ${assembleProp(rest)}"
      case None => assembleProp(rest)
    }
    case Nil => ""
  }
  //TODO Function verallgemeinern
  def detectComa(rest : List[(String, Option[String])]) : String = rest match {
    case Nil => ""
    case param :: rest  =>  param._2 match {
      case Some(p) => ","
      case None => ""
    }
  }
}
