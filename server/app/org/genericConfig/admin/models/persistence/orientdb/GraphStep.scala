package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import org.genericConfig.admin.models.common.{Error, ODBClassCastError, ODBConnectionFail, ODBRecordDuplicated, ODBWriteError}
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.step.StepDTO
import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.step.status.{StatusAddStep, StatusAppendStep}
import play.api.Logger

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 14.04.2020
 */

object GraphStep {

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param stepDTO : StepDTO
   * @return (Option[OrientVertex], Option[Error])
   */
  def addStep(stepDTO: StepDTO): (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphStep(graph).addStep(stepDTO)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param id : String, stepId: String
   * @return (StatusAppendStep, Status)
   */
  def appendStepTo(id: String, stepId: String): (StatusAppendStep, Error) = {
    ???
    //    (Database.getFactory(): @unchecked) match {
    //      case (Some(dbFactory), Success()) =>
    //        val graph: OrientGraph = dbFactory.getTx
    //        new Graph(graph).appendStepTo(id, stepId)
    //      case (None, ODBConnectionFail()) =>
    //        (AppendStepError(), ODBConnectionFail())
    //    }
  }


  def addStep(/*stepDTO: StepDTO*/) = {}
  def appendConfigTo(fromId : String, toId : String) = {}
}

class GraphStep(graph : OrientGraph) {

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param stepDTO : StepDTO
   * @return (Option[OrientVertex], Option[Error])
   */
    private def addStep(stepDTO: StepDTO): (Option[OrientVertex], Option[Error]) = {
      try {
        stepDTO.appendToId match {
          case Some(appendToId) =>
            addStepTo(stepBO = stepBO, rId = appendToId)
          case None => (None, AddStepDefectComponentOrConfigId(), ODBRecordIdDefect())
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
          (None, Some(ODBWriteError()))
      }
    }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param stepBO : StepBO, rId: String
   * @return (Option[OrientVertex], StatusAddStep, Status)
   */
  //  private def addStepTo(stepBO: StepBO, rId: String): (Option[OrientVertex], StatusAddStep, Error) = {
  //    val v: OrientVertex = graph.getVertex(rId)
  //    v match {
  //      case null => (None, AddStepDefectComponentOrConfigId(), ODBRecordIdDefect())
  //      case _ =>
  //        val countOfSteps: Int = v.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList.size
  //        countOfSteps match {
  //          case count if count > 0 => (None, AddStepAlreadyExist(), Error())
  //          case _ =>
  //            val vStep: OrientVertex = graph.addVertex(
  //              PropertyKeys.CLASS + PropertyKeys.VERTEX_STEP,
  //              PropertyKeys.NAME_TO_SHOW, stepBO.nameToShow.get,
  //              PropertyKeys.KIND, stepBO.kind.get,
  //              PropertyKeys.SELECTION_CRITERIUM_MIN, stepBO.selectionCriteriumMin.get.toString,
  //              PropertyKeys.SELECTION_CRITERIUM_MAX, stepBO.selectionCriteriumMax.get.toString
  //            )
  //            graph.commit()
  //            (Some(vStep), AddStepSuccess(), Success())
  //        }
  //    }
  //  }


  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param
   * @return (StatusAppendStep, Status)
   */
  //  private def appendStepTo(id: String, stepId: String): (StatusAppendStep, Error) = {
  //    try {
  //      val v: OrientVertex = graph.getVertex(id)
  //      val vStep: OrientVertex = graph.getVertex(stepId)
  //      graph.addEdge(
  //        PropertyKeys.CLASS + PropertyKeys.EDGE_HAS_STEP, v, vStep,
  //        PropertyKeys.EDGE_HAS_STEP
  //      )
  //      graph.commit()
  //      (AppendStepSuccess(), Success())
  //    } catch {
  //      case e: ORecordDuplicatedException =>
  //        Logger.error(e.printStackTrace().toString)
  //        graph.rollback()
  //        (AppendStepError(), ODBRecordDuplicated())
  //      case e: ClassCastException =>
  //        graph.rollback()
  //        Logger.error(e.printStackTrace().toString)
  //        (AppendStepError(), ODBClassCastError())
  //      case e: Exception =>
  //        graph.rollback()
  //        Logger.error(e.printStackTrace().toString)
  //        (AppendStepError(), ODBWriteError())
  //    }
  //  }

}
