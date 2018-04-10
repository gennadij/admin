package org.genericConfig.admin.models.persistence.db.orientdb

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.Direction
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.models.wrapper.config.CreateConfigIn
import org.genericConfig.admin.models.wrapper.configTree.ConfigTreeIn
import org.genericConfig.admin.models.wrapper.configTree.ConfigTreeOut
import org.genericConfig.admin.models.wrapper.configTree.Step
import org.genericConfig.admin.models.wrapper.configTree.Component
import org.genericConfig.admin.models.json.StatusErrorConfig
import org.genericConfig.admin.models.json.StatusErrorConfigTree
import org.genericConfig.admin.models.json.StatusSuccessfulConfig
import org.genericConfig.admin.models.json.StatusSuccessfulConfigTree
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import org.genericConfig.admin.models.json.StatusWarningConfigTreeEmpty
import play.api.Logger
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import org.genericConfig.admin.models.json.StatusErrorWriteToDB
import org.genericConfig.admin.models.json.StatusErrorDuplicateConfigUrl
import com.orientechnologies.common.exception.OException

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */
object ConfigVertex {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param CreateConfig
   * 
   * @return RegistrationSC
   */
  def createConfig(createConfigCS: CreateConfigIn): (Option[OrientVertex], String) = {
    
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
    
    val vConfig: Any = try {
      val vConfig: OrientVertex = graph.addVertex(
          "class:" + PropertyKey.VERTEX_CONFIG,
          PropertyKey.CONFIG_URL, createConfigCS.configUrl)
        graph.commit
        vConfig
    }catch{
      case e2: ORecordDuplicatedException => {
        graph.rollback()
        e2
      }
      case e1: Exception => {
        graph.rollback()
        e1
      }
    }
    
    vConfig match {
      case vConfig: OrientVertex => {
        (Some(vConfig), StatusSuccessfulConfig.status)
      }
      case e2 : ORecordDuplicatedException => {
        (None, StatusErrorDuplicateConfigUrl.status)
      }
      case e1 : Exception => {
        (None, StatusErrorWriteToDB.status)
      }
    }
  }
  
  /**
   * Loescht alle Steps und Components die zu der Config gehoeren
   * 
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param configId
   * 
   * @return Count from deleted Vertexes
   */
  
    def deleteAllStepsAndComponent(configId: String) = {
      val sql: String = s"DELETE VERTEX V where @rid IN (traverse out() from (select out('hasFirstStep') " + 
        s"from Config where @rid='$configId'))"
      val graph: OrientGraph = OrientDB.getFactory().getTx
      val res: Int = graph
        .command(new OCommandSQL(sql)).execute()
      graph.commit
      res
  }
    
  def deleteConfigVertex(username: String): Int = {
    val sql: String = s"DELETE VERTEX Config where @rid IN (SELECT OUT('hasConfig') FROM AdminUser WHERE username='$username')"
      val graph: OrientGraph = OrientDB.getFactory().getTx
      val res: Int = graph
        .command(new OCommandSQL(sql)).execute()
      graph.commit
      res
  }
    
  /**
   * 
   * 
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param ConfigTreeCS
   * 
   * @return ConfigTreeSC
   */
  def getConfigTree(configTreeIn: ConfigTreeIn): ConfigTreeOut = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val configId: String = configTreeIn.configId
    
    val firstStep: Option[Step] = try{
      val firstSteps: List[OrientVertex] = 
          graph.getVertex(configId)
          .getEdges(Direction.OUT, "hasFirstStep")
          .asScala.toList.map(eHasFirstStep => {
            eHasFirstStep.getVertex(Direction.IN).asInstanceOf[OrientVertex]
          })
      firstSteps.size match {
        case count if count == 1 => {
          val components: Set[Option[Component]] = getComponents(Some(firstSteps.head))
          Some(Step(
              firstSteps.head.getIdentity.toString,
              firstSteps.head.getProperty(PropertyKey.KIND),
              components
          ))
        }
        case _ => None
      }
    }catch{
      case e: Exception => graph.rollback()
      None
    }
    val configTree: ConfigTreeOut = firstStep match {
      case Some(firstStep) => {
        ConfigTreeOut(
            Some(firstStep),
            StatusSuccessfulConfigTree.status,
            StatusSuccessfulConfigTree.message
        )
      }
      case None => {
        ConfigTreeOut(
            None,
            StatusWarningConfigTreeEmpty.status,
            StatusWarningConfigTreeEmpty.message
        )
      }
    }
    configTree
  }
  
  def getComponents(step: Option[OrientVertex]): Set[Option[Component]] = {
    
    val components: Set[Option[Component]] = step match {
      case Some(step) => {
        val eHasComponents: List[Edge] = step.getEdges(Direction.OUT, PropertyKey.EDGE_HAS_COMPONENT).asScala.toList
        val components: List[Option[Component]] = eHasComponents.map{ eHasComponent => {
          val vComponent: OrientVertex = eHasComponent.getVertex(Direction.IN).asInstanceOf[OrientVertex]
          val eHasSteps : List[Edge] = vComponent.getEdges(Direction.OUT, PropertyKey.EDGE_HAS_STEP).asScala.toList
          val component: Option[Component] = eHasSteps match {
            case List() => {
              val lastComponent: Option[Component] = Some(
                  Component(
                      vComponent.getIdentity.toString(),
                      vComponent.getProperty(PropertyKey.KIND),
                      None,
                      None
                  )
              )
              lastComponent
            }
            case eHasSteps => {
              val nextSteps: List[Step] = eHasSteps.map{
                eHasStep => {
                  val vStep: OrientVertex = eHasStep.getVertex(Direction.IN).asInstanceOf[OrientVertex]
                  val components: Set[Option[Component]] = getComponents(Some(vStep))
                  Step(
                      vStep.getIdentity.toString,
                      vStep.getProperty(PropertyKey.KIND),
                      components
                  )
                }
              }
              val defaultComponent: Option[Component] = nextSteps.size match {
                case count if count == 1 => {
                  Some(Component(
                      vComponent.getIdentity.toString(),
                      vComponent.getProperty(PropertyKey.KIND),
                      Some(nextSteps.head.stepId),
                      Some(nextSteps.head)
                  ))
                }
                case _ => None // Fehler eine Komponente kann nicht 2 Steps haben
              }
              defaultComponent
            }
          }
          component
        }} //end eHasComponents.map
        val componentsWithoutDuplicate = findDuplicate(components)
        componentsWithoutDuplicate.toSet
      }
      case None => Set.empty
    }
    components
  }
  
  def findDuplicate(
      components: List[Option[Component]]) : List[Option[Component]] = components match {
    
    case List() => List()
    case x :: xs => insert(x, findDuplicate(xs))
  }
  
  def insert(
      x: Option[Component], 
      xs: List[Option[Component]]): List[Option[Component]] = xs match {
    
    case List() => List(x)
    case y :: ys =>   if(x.get.nextStepId == y.get.nextStepId) 
      Some(x.get.copy(nextStep = None)) :: xs
    else y :: insert(x, ys)
  }
}