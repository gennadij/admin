package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import org.genericConfig.admin.models.common.{DefectRIdError, Error, ODBClassCastError, ODBConnectionFail, ODBRecordDuplicated, ODBWriteError}
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.configTree.bo._
import org.genericConfig.admin.shared.configTree.status._
import play.api.Logger


/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 10.04.2018
  */
object GraphCommon {

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param outRid : String, inId: String
   * @return Option[Error)
   */
  def appendTo(outRid: String, inRid: String, label : String): Option[Error] = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphCommon(graph).appendTo(outRid, inRid, label)
      case (None, Some(ODBConnectionFail())) =>
        Some(ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param rId : String
    * @return Option[Error)
    */
  def deleteVertex(rId: String, clazz : String): Option[Error] = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphCommon(graph).deleteVertex(rId, clazz)
      case (None, Some(ODBConnectionFail())) =>
        Some(ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String
    * @return (Option[StepForConfigTreeBO], StatusGetConfigTree, Status)
    */
  def getConfigTree(configId: String): (Option[StepForConfigTreeBO], StatusGetConfigTree, Error) = {
    ???
//    (Database.getFactory(): @unchecked) match {
//      case (Some(dbFactory), Success()) =>
//        val graph: OrientGraph = dbFactory.getTx
//        new Graph(graph).getConfigTree(configId)
//      case (None, ODBConnectionFail()) =>
//        (None, GetConfigTreeError(), ODBConnectionFail())
//    }
  }
}


class GraphCommon(graph: OrientGraph) {

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param outRid: String, inRid: String, label : String
   * @return Option[Error]
   */
  private def appendTo(outRid: String, inRid: String, label : String) : Option[Error] = {
    try {
      val vOut : OrientVertex = graph.getVertex(outRid)
      val vIn : OrientVertex = graph.getVertex(inRid)
      graph.addEdge(PropertyKeys.CLASS + label, vOut, vIn, label)
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

 private def deleteVertex(rId : String, clazz : String) : Option[Error]  = {
   try {
     val sql: String = s"DELETE VERTEX $clazz where @rid=$rId"
     val res: Int = graph.command(new OCommandSQL(sql)).execute()
     graph.commit()
     res match {
       case 1 => None
       case _ => Some(DefectRIdError())
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

//  /**
//    * Converting of the rid to hash from steps and components is here
//    *
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param
//    * @return (Option[StepForConfigTreeBO], StatusGetConfigTree, Status)
//    */
//  private def getConfigTree(configId: String): (Option[StepForConfigTreeBO], StatusGetConfigTree, Error) = {
//    try {
//      val vFirstStep: List[OrientVertex] =
//        graph.getVertex(configId)
//          .getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP)
//          .asScala.toList.map(eHasFirstStep => {
//          eHasFirstStep.getVertex(Direction.IN).asInstanceOf[OrientVertex]
//        })
//      vFirstStep.size match {
//        case count if count == 1 =>
//          val configTree = Some(StepForConfigTreeBO(
//            stepId = vFirstStep.head.getIdentity.toString,
//            nameToShow = vFirstStep.head.getProperty(PropertyKeys.NAME_TO_SHOW),
//            kind =  vFirstStep.head.getProperty(PropertyKeys.KIND),
//            nextSteps = getNextSteps(vFirstStep.head),
//            components = getComponents(vFirstStep.head)
//          ))
//          (configTree, GetConfigTreeSuccess(), Success())
//        case _ => (None, GetConfigTreeEmpty(), Success())
//      }
//    } catch {
//      case e: ORecordDuplicatedException =>
//        Logger.error(e.printStackTrace().toString)
//        graph.rollback()
//        (None, GetConfigTreeError(), ODBRecordDuplicated())
//      case e: ClassCastException =>
//        graph.rollback()
//        Logger.error(e.printStackTrace().toString)
//        (None, GetConfigTreeError(), ODBClassCastError())
//      case e: Exception =>
//        graph.rollback()
//        Logger.error(e.printStackTrace().toString)
//        (None, GetConfigTreeError(), ODBWriteError())
//    }
//  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param step : Option[OrientVertex]
//    * @return Set[Option[ComponentForConfigTreeBO\]\]
//    */
////  private def getComponents(step: OrientVertex): Set[ComponentForConfigTreeBO] = {
//    val componentForConfigTreeBOs: List[ComponentForConfigTreeBO] =
//      step.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList map {
//        eHasComponent =>
//          val vComponent: OrientVertex = eHasComponent.getVertex(Direction.IN).asInstanceOf[OrientVertex]
//          val eHasSteps: List[Edge] = vComponent.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList
//          val nextStepId: Option[String] = eHasSteps match {
//            case List() => None
//            case _ => Some(eHasSteps.head.getVertex(Direction.IN).asInstanceOf[OrientVertex].getIdentity.toString())
//          }
//
//          ComponentForConfigTreeBO(
//            componentId = vComponent.getIdentity.toString(),
//            nameToShow = vComponent.getProperty(PropertyKeys.NAME_TO_SHOW),
//            kind = vComponent.getProperty(PropertyKeys.KIND),
//            nextStepId = nextStepId
//          )
//      }
//    componentForConfigTreeBOs.toSet
//  }

//  private def getNextSteps(step: OrientVertex): Set[StepForConfigTreeBO] = {
//    val stepForConfigTreeBOs: List[StepForConfigTreeBO] =
//      step.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList map {
//      eHasComponent =>
//        val vComponent: OrientVertex = eHasComponent.getVertex(Direction.IN).asInstanceOf[OrientVertex]
//        val eHasSteps: List[Edge] = vComponent.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList
//        eHasSteps match {
//          case eHSs :: _ =>
//            val vStep: OrientVertex = eHSs.getVertex(Direction.IN).asInstanceOf[OrientVertex]
//                StepForConfigTreeBO(
//                  stepId = vStep.getIdentity.toString,
//                  nameToShow = vStep.getProperty(PropertyKeys.NAME_TO_SHOW),
//                  kind = vStep.getProperty(PropertyKeys.KIND),
//                  nextSteps = getNextSteps(vStep),
//                  components = getComponents(vStep)
//                )
//          case List() =>
//            StepForConfigTreeBO(
//              stepId = "",
//              nameToShow = "",
//              kind = "",
//              nextSteps = Set(),
//              components = Set()
//            )
//        }
//    }
//    stepForConfigTreeBOs.filter(_.stepId != "").toSet
//  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param step : Option[OrientVertex]
//    * @return Set[Option[ComponentForConfigTreeBO\]\]
//    */
//  private def getComponents(step: OrientVertex): Set[Option[ComponentForConfigTreeBO]] = {
//
//    val componentsForConfigTreeBO: List[Option[ComponentForConfigTreeBO]] =
//      step.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList map {
//        eHasComponent =>
//          val vComponent: OrientVertex = eHasComponent.getVertex(Direction.IN).asInstanceOf[OrientVertex]
//          val eHasSteps: List[Edge] = vComponent.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList
//          val componentForConfigTreeBO: Option[ComponentForConfigTreeBO] = eHasSteps match {
//            case List() =>
//              //create last componentForConfigTreeBO
//              Some(ComponentForConfigTreeBO(
//                componentId = vComponent.getIdentity.toString(),
//                nameToShow = vComponent.getProperty(PropertyKeys.NAME_TO_SHOW),
//                kind = vComponent.getProperty(PropertyKeys.KIND),
//                nextStep = None
//              ))
//            case _ =>
//              //get nestStep of componentForConfigTreeBO
//              val nextSteps: List[StepForConfigTreeBO] = eHasSteps.map {
//                eHasStep => {
//                  val vStep: OrientVertex = eHasStep.getVertex(Direction.IN).asInstanceOf[OrientVertex]
//                  val components: Set[Option[ComponentForConfigTreeBO]] = getComponents(vStep)
//                  StepForConfigTreeBO(
//                    stepId = vStep.getIdentity.toString,
//                    nameToShow = vStep.getProperty(PropertyKeys.NAME_TO_SHOW),
//                    kind = vStep.getProperty(PropertyKeys.KIND),
//                    components = components
//                  )
//                }
//              }
//              // get all componentForConfigTreeBO form nextStep
//              val defaultComponent: Option[ComponentForConfigTreeBO] = nextSteps.size match {
//                case count if count == 1 =>
//                  Some(ComponentForConfigTreeBO(
//                    componentId = vComponent.getIdentity.toString(),
//                    nameToShow = vComponent.getProperty(PropertyKeys.NAME_TO_SHOW),
//                    kind = vComponent.getProperty(PropertyKeys.KIND),
//                    nextStep = Some(nextSteps.head)
//                  ))
//                case _ => None // Fehler eine Komponente kann nicht 2 Steps haben
//              }
//              defaultComponent
//          }
//          componentForConfigTreeBO
//      }
//
//    //    val componentsWithoutDuplicate = findDuplicate(componentsForConfigTreeBO)
//    //
//    //    componentsWithoutDuplicate.toSet
//    //TODO findDuplicate implement in logik
//    componentsForConfigTreeBO.toSet
//  }
//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param
//    * @return Int count of deleted Vertexes
//    */
//  private def deleteStepAppendedToComponent(componentId: String): Int = {
//    val res: Int = graph
//      .command(new OCommandSQL(s"DELETE VERTEX Step where @rid IN (SELECT out() from Component where @rid='$componentId')")).execute()
//    graph.commit()
//    res
//  }

  //  /**
  //    * @author Gennadi Heimann
  //    * @version 0.1.6
  //    * @param components : List[Option[ComponentForConfigTreeBO\]\]
  //    * @return List[Option[ComponentForConfigTreeBO\]\]
  //    */
  //  def findDuplicate(
  //                     components: List[Option[ComponentForConfigTreeBO]]): List[Option[ComponentForConfigTreeBO]] = components match {
  //    case List() => List()
  //    case x :: xs => insert(x, findDuplicate(xs))
  //  }
  //
  //  /**
  //    * @author Gennadi Heimann
  //    * @version 0.1.6
  //    * @param x : Option[ComponentForConfigTreeBO],
  //    *          xs: List[Option[ComponentForConfigTreeBO]
  //    * @return List[Option[ComponentForConfigTreeBO\]\]
  //    */
  //  def insert(
  //              x: Option[ComponentForConfigTreeBO],
  //              xs: List[Option[ComponentForConfigTreeBO]]): List[Option[ComponentForConfigTreeBO]] = xs match {
  //    case List() => List(x)
  //    case y :: ys => if (x.get.nextStepId == y.get.nextStepId)
  //      Some(x.get.copy(nextStep = None)) :: xs
  //    else y :: insert(x, ys)
  //  }


//  /**
//    * Loescht alle Steps und Components die zu der Config gehoeren
//    *
//    * @author Gennadi Heimann
//    * @version 0.1.0
//    * @param configId : String
//    * @return Count of deleted Vertexes
//    */

//  private def deleteAllStepsAndComponent(configId: String): Int = {
//    val sql: String = s"DELETE VERTEX V where @rid IN (traverse out() from (select out('hasFirstStep') " +
//      s"from Config where @rid='$configId'))"
//    val res: Int = graph
//      .command(new OCommandSQL(sql)).execute()
//    graph.commit()
//    res
//  }
//
//  private def deleteDependency(nameToShow: String): Int = {
//    // DELETE EDGE hasDependency WHERE nameToShow="dependency_C2_C3_user28_v015"
//    val sqlQuery = s"DELETE EDGE hasDependency WHERE nameToShow='$nameToShow'"
//    val res: Int = graph.command(new OCommandSQL(sqlQuery)).execute()
//    graph.commit
//    res
//  }
}