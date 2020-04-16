package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import com.tinkerpop.blueprints.{Direction, Edge}
import org.genericConfig.admin.models.common.{AddStepAlreadyExist, Error, ODBClassCastError, ODBConnectionFail, ODBRecordDuplicated, ODBWriteError}
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.step.StepDTO
import org.genericConfig.admin.shared.step.status.StatusAppendStep
import play.api.Logger

import scala.collection.JavaConverters._
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
   * @param rId : String
   * @return Option[Error]
   */
  def isStepAlone(rId : String): Option[Error] = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphStep(graph).isStepAlone(rId)
      case (None, Some(ODBConnectionFail())) =>
        Some(ODBConnectionFail())
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
   * @param rId : String
   * @return Option[Error]
   */
  private def isStepAlone(rId : String) : Option[Error] = {
    try {

      val eHasSteps : List[Edge] =
        graph.getVertex(rId).getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList
      graph.commit()

      eHasSteps.size match {
        case size if size > 0 => Some(AddStepAlreadyExist())
        case _ => None
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
   * @param stepDTO : StepDTO
   * @return (Option[OrientVertex], Option[Error])
   */
    private def addStep(stepDTO: StepDTO): (Option[OrientVertex], Option[Error]) = {
      try {
        val vStep: OrientVertex = graph.addVertex(
          PropertyKeys.CLASS + PropertyKeys.VERTEX_STEP,
          PropertyKeys.NAME_TO_SHOW, stepDTO.params.get.nameToShow.get,
          PropertyKeys.KIND, stepDTO.params.get.kind.get,
          PropertyKeys.SELECTION_CRITERION_MIN, stepDTO.params.get.selectionCriterion.get.min.toString,
          PropertyKeys.SELECTION_CRITERION_MAX, stepDTO.params.get.selectionCriterion.get.max.toString
        )
        graph.commit()
        (Some(vStep), None)
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
