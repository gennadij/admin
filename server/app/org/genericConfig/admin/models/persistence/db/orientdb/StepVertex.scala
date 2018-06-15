package org.genericConfig.admin.models.persistence.db.orientdb

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.Direction
import org.genericConfig.admin.models.persistence.OrientDB
import play.api.Logger

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */

object StepVertex {
  
//    /**
//   * @author Gennadi Heimann
//   *
//   * @version 0.1.0
//   * @version 0.1.1
//   * @version 0.1.3
//   * @version 0.1.5
//   *
//   * @param
//   *
//   * @return
//   */
//  def step(stepCS: StepIn): StepOut = {
//    // TODO v 016 Erzeugen einen Step und Verbinden zu diesem Step in separaten Anfragen bearbeiten
//    //Pruefen wenn Stepp schon exestiert nur Component zu der vorhandenen Step verbinden.
//    // Es soll geprueft werden, nach dem der FirstStep erzeugt wurde,
//    // wenn keine HasStep erstellt wurde sollte eine exception geworfen
//    val graph: OrientGraph = OrientDB.getFactory().getTx
//
//
//    val componentId: String = stepCS.componentId
//
//    try{
//      val vComponent = graph.getVertex(componentId)
//
//      vComponent match {
//        case null => getErrorStepSC(StatusErrorFaultyComponentId.status, StatusErrorFaultyComponentId.message)
//        case _ => {
//          val countsOfSteps: Int = vComponent.getEdges(Direction.OUT, PropertyKey.EDGE_HAS_STEP).asScala.toList.size
//          countsOfSteps match {
//            case count if count > 0 => {
//              getErrorStepSC(StatusErrorStepExist.status, StatusErrorStepExist.message)
//            }
//            case _ => {
//              val vStep: Option[OrientVertex] = writeStepToDB(stepCS)
//              vStep match {
//                case Some(vStep) => {
//                  val step = getSuccessfulStepSC(vStep.getIdentity.toString,
//                		  StatusSuccessfulStepCreated.status, StatusSuccessfulStepCreated.message)
//                		  step
//                }
//                case None => getErrorStepSC(StatusErrorGeneral.status, StatusErrorGeneral.message)
//              }
//
//            }
//          }
//        }
//      }
//    }catch{
//      case e: Exception => {
//        graph.rollback()
//        getErrorStepSC(StatusErrorWriteToDB.status, StatusErrorWriteToDB.message)
//      }
//    }
//  }
  
  
//  /**
//   * @author Gennadi Heimann
//   *
//   * @version 0.1.3
//   *
//   * @param
//   *
//   * @return
//   */

//  def firstStep(firstStepCS: FirstStepIn): FirstStepOut = {
//    
//    // Es soll geprueft werden, nach dem der FirstStep erzeugt wurde, 
//    // wenn keine HasStep erstellt wurde sollte eine exception geworfen werden
//    val graph: OrientGraph = OrientDB.getFactory().getTx
//    val configId = firstStepCS.configId
//    
//    val firstStep: Any = try{
//      val vConfig: OrientVertex = graph.getVertex(configId)
//      val countOfFirstSteps: Int = vConfig.getEdges(Direction.OUT, PropertyKey.EDGE_HAS_FIRST_STEP).asScala.toList.size
//        countOfFirstSteps match {
//          case count if count > 0 => {
//            getErrorFirstStepSC(StatusErrorFirstStepExist.status, StatusErrorFirstStepExist.message)
//          }
//          case _=> {
//            val vFirstStep: Option[OrientVertex] = writeFirstStepToDB(firstStepCS)
//            vFirstStep match {
//              case Some(vFirstStep) => {
//                getSuccessfulFirstStepSC(vFirstStep.getIdentity.toString, 
//                    StatusSuccessfulFirstStepCreated.status, StatusSuccessfulFirstStepCreated.message)
//              }
//              case None => getErrorFirstStepSC(
//                  StatusErrorFaultyConfigId.status, StatusErrorFaultyConfigId.message)
//            }
//          }
//        }
//    }catch{
//      case e: Exception => {
//        graph.rollback()
//        e
//      }
//    }
//    
//    firstStep match {
//      case firstStep: FirstStepOut => {
//        firstStep
//      }
//      case e : Exception => getErrorFirstStepSC(StatusErrorFaultyConfigId.status, StatusErrorFaultyConfigId.message)
//    }
//  }
  
//  /**
//   * @author Gennadi Heimann
//   *
//   * @version 0.1.0
//   *
//   * @param
//   *
//   * @return
//   */
  
  def removeStep(configId: String): Int = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX Step where @rid IN (SELECT out() from Config where @rid='$configId')")).execute()
    graph.commit
    res
  }
  
    def deleteStepFromComponent(componentId: String): Int = {
      
    
      val graph: OrientGraph = OrientDB.getFactory().getTx
    
      val sql = s"delete vertex Step where @rid in (select out(hasStep) from Component where @rid='$componentId')"
    
      val res: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit()
      res
  }
    
    
    
    def deleteStep(componentNameToSchow: String): Int = {
      
    
      val graph: OrientGraph = OrientDB.getFactory().getTx
      
      val sqlRid = s"select @rid from Component where nameToShow='$componentNameToSchow'"
      
      val resForComponentId: OrientDynaElementIterable = graph.command(new OCommandSQL(sqlRid)).execute()
      val componentId = 
        resForComponentId.asScala.toList.head.asInstanceOf[OrientVertex].getProperty("rid").asInstanceOf[OrientVertex].getIdentity.toString()
      val sql = s"delete vertex Step where @rid in (select out(hasStep) from Component where @rid='$componentId')"
    
      val res: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit()
      res
  }
//  private def getErrorFirstStepSC(status: String, message: String): FirstStepOut = {
//    FirstStepOut(
//        "",
//        status,
//        message
//        )
//  }
  
//  private def getErrorStepSC(status: String, message: String): StepOut = {
//    StepOut(
//        "",
//        status,
//        message,
//        Set.empty,
//        Set.empty
//    )
//  }
  
//  private def getSuccessfulFirstStepSC(stepId: String, status: String, message: String): FirstStepOut = {
//    FirstStepOut(
//        stepId,
//        status,
//        message
//    )
//  }
  
//  private def getSuccessfulStepSC(stepId: String, status: String, message: String) = {
//    StepOut(
//        stepId,
//        status,
//        message,
//        Set.empty,
//        Set.empty
//    )
//  }
  
//  private def writeFirstStepToDB(firstStepCS: FirstStepIn): Option[OrientVertex] = {
//    val graph: OrientGraph = OrientDB.getFactory().getTx
//    
//    val vFirstStep: Any = try{
//      val vFirstStep: OrientVertex = graph.addVertex(
//          "class:" + PropertyKey.VERTEX_STEP,
//          PropertyKey.NAME_TO_SHOW, firstStepCS.nameToShow,
//          PropertyKey.KIND, firstStepCS.kind,
//          PropertyKey.SELECTION_CRITERIUM_MIN, firstStepCS.selectionCriteriumMin.toString,
//          PropertyKey.SELECTION_CRITERIUM_MAX, firstStepCS.selectionCriteriumMax.toString
//      )
//      graph.commit
//      vFirstStep
//    }catch{
//      case e: Exception => graph.rollback()
//    }
//    
//    vFirstStep match {
//      case vFirstStep: OrientVertex => Some(vFirstStep)
//      case e: Exception => None
//    }
//  }
  
//  private def writeStepToDB(stepCS: StepIn): Option[OrientVertex] = {
//    val graph: OrientGraph = OrientDB.getFactory().getTx
//    try{
//      val vStep: OrientVertex = graph.addVertex(
//        "class:" + PropertyKey.VERTEX_STEP,
//        PropertyKey.KIND, stepCS.kind,
//        PropertyKey.NAME_TO_SHOW, stepCS.nameToShow,
//        PropertyKey.SELECTION_CRITERIUM_MIN, stepCS.selectionCriteriumMin.toString,
//        PropertyKey.SELECTION_CRITERIUM_MAX, stepCS.selectionCriteriumMax.toString
//      )
//      graph.commit
//      Some(vStep)
//    }catch{
//      case e: Exception => {
//        graph.rollback()
//        None
//      }
//    }
//
//  }
  
//  /**
//   * @author Gennadi Heimann
//   *
//   * @version 0.1.5
//   *
//   * @param
//   *
//   * @return
//   */
//
//  def getStep(stepId: String): Option[StepIn] = {
//     val graph: OrientGraph = OrientDB.getFactory().getTx
//      try{
//      val vStep = graph.getVertex(stepId)
//
//      Some(
//          StepIn(
//              "",
//              vStep.getProperty(PropertyKey.NAME_TO_SHOW),
//              vStep.getProperty(PropertyKey.KIND),
//              vStep.getProperty(PropertyKey.SELECTION_CRITERIUM_MIN),
//              vStep.getProperty(PropertyKey.SELECTION_CRITERIUM_MAX)
//          )
//      )
//    }catch{
//      case e: Exception => {
//        graph.rollback()
//        None
//      }
//    }
//   }
}