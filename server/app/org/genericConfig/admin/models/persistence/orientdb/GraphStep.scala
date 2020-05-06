package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.exception.OValidationException
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import com.tinkerpop.blueprints.{Direction, Edge}
import org.genericConfig.admin.models.common.{Error, ODBClassCastError, ODBConnectionFail, ODBRecordDuplicated, ODBValidationException, ODBWriteError, StepAlreadyExistError}
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.step.StepDTO
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
   * @param stepDTO : StepDTO
   * @return (Option[OrientVertex], Option[Error])
   */
  def updateStep(stepRId : String, stepDTO: StepDTO) : (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphStep(graph).updateStep(stepRId, stepDTO)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }
}

class GraphStep(graph : OrientGraph) {

  private def isStepAlone(rId : String) : Option[Error] = {
    try {
      val eHasSteps : List[Edge] =
        graph.getVertex(rId).getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList
      graph.commit()
      eHasSteps.size match {
        case size if size > 0 => Some(StepAlreadyExistError())
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
      case e: OValidationException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, Some(ODBValidationException()))
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

  def updateStep(stepRId : String, stepDTO: StepDTO): (Option[OrientVertex], Option[Error]) = {
    try {

      val sCMin : Option[String] = stepDTO.params.get.selectionCriterion.get.min match {
        case Some(min) => Some(min.toString)
        case None => None
      }

      val sCMax : Option[String] = stepDTO.params.get.selectionCriterion.get.max match {
        case Some(max) => Some(max.toString)
        case None => None
      }

      val updateParams : List[(String, Option[String])] = List(
        (PropertyKeys.NAME_TO_SHOW, stepDTO.params.get.nameToShow),
        (PropertyKeys.SELECTION_CRITERION_MIN, sCMin),
        (PropertyKeys.SELECTION_CRITERION_MAX, sCMax)
      )

      val sql: String = s"update Step set " +
        assembleSQLForUpdateStep(updateParams) +
        s"return after where @rid=${stepRId}"

      //      val sql: String = s"update Config set " +
//        s"${PropertyKeys.CONFIG_URL}='$cU', " +
//        s"${PropertyKeys.CONFIGURATION_COURSE}='$cC' " +
//        s"return after where @rid=$configId"
//      val dbRes: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
//      val vUpdatedConfig : OrientVertex = dbRes.asScala.toList.map(_.asInstanceOf[OrientVertex]).head
//      graph.commit()
//      (Some(vUpdatedConfig), None)

//      val vStep: OrientVertex = graph.addVertex(
//        PropertyKeys.CLASS + PropertyKeys.VERTEX_STEP,
//        PropertyKeys.NAME_TO_SHOW, stepDTO.params.get.nameToShow.get,
//        PropertyKeys.KIND, stepDTO.params.get.kind.get,
//        PropertyKeys.SELECTION_CRITERION_MIN, stepDTO.params.get.selectionCriterion.get.min.toString,
//        PropertyKeys.SELECTION_CRITERION_MAX, stepDTO.params.get.selectionCriterion.get.max.toString
//      )

      graph.commit()
      (Some(graph.getVertex(stepDTO.params.get.stepId.get)), None)
    } catch {
      case e: OValidationException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, Some(ODBValidationException()))
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

  def assembleSQLForUpdateStep(params : List[(String, Option[String])]) : String = params match {
    case param :: rest => param._2 match {
      case Some(p) => s"${param._1}=${p}" + assembleSQLForUpdateStep(rest)
      case None => "" + assembleSQLForUpdateStep(rest)
    }
    case Nil => ""
  }
}
