package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import org.genericConfig.admin.models.common.{Error, ODBClassCastError, ODBConnectionFail, ODBRecordDuplicated, ODBWriteError}
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.component.ComponentUserPropertiesDTO
import play.api.Logger

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
    * @return (Option[OrientVertex], StatusAddComponent, Status)
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
}
