package org.genericConfig.admin.models.persistence.orientdb

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.genericConfig.admin.models.persistence.Database
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import play.api.Logger
import org.genericConfig.admin.shared.common.status._
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.Vertex
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.configTree.bo._
import org.genericConfig.admin.shared.configTree.status._
import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.step.status._
import com.orientechnologies.orient.core.exception.OStorageException
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.shared.user.status._


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */
object Graph{
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String, String
   * 
   * @return RegistrationBO
   */
  def addUser(username: String, password: String): (Option[OrientVertex], StatusAddUser, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).addUser(username, password)
      case (None, ODBConnectionFail()) => 
        (None, AddUserError(), ODBConnectionFail())
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String, String
   * 
   * @return LoginBO
   */
  def getUser(username: String, password: String): (Option[OrientVertex], StatusGetUser, Status) = {
    
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).getUser(username, password)
      case (None, ODBConnectionFail()) => 
        (None, GetUserError(), ODBConnectionFail())
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  def addConfig(configUrl: String): (Option[OrientVertex], StatusAddConfig, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).addConfig(configUrl)
      case (None, ODBConnectionFail()) => 
        (None, AddConfigError(), ODBConnectionFail())
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  def deleteConfig(configId: String, configUrl: String): (StatusDeleteConfig, Status) = {
    
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).deleteConfig(configId, configUrl)
      case (None, ODBConnectionFail()) => 
        (DeleteConfigError(), ODBConnectionFail())
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  def appendConfigTo(userId: String, configId: String): Status = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).appendConfigTo(userId, configId)
      case (None, ODBConnectionFail()) => 
        ODBConnectionFail()
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  def deleteAllConfigs(username: String): Int = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).deleteAllConfigs(username)
      case (None, ODBConnectionFail()) => 
        0
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  def getConfigs(userId: String): (Option[List[OrientVertex]], StatusGetConfigs, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).getConfigs(userId)
      case (None, ODBConnectionFail()) => 
        (None, GetConfigsError(), ODBConnectionFail())
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  
  def getConfigTree(configId: String): (Option[StepForConfigTreeBO], StatusGetConfigTree, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).getConfigTree(configId)
      case (None, ODBConnectionFail()) => 
        (None, GetConfigTreeError(), ODBConnectionFail())
    }
  }
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  def getAdminUserId(configId: String): (String, Status) = {
    
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).getAdminUserId(configId)
      case (None, ODBConnectionFail()) => 
        ("", ODBConnectionFail())
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  def updateConfig(configId: String, configUrl: String): (StatusUpdateConfig, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).updateConfig(configId, configUrl)
      case (None, ODBConnectionFail()) => 
        (UpdateConfigError(), ODBConnectionFail())
    }
  }

  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param stepBO: StepBO
   * 
   * @return (Option[OrientVertex], StatusAddStep, Status)
   */
  def addStep(stepBO: StepBO): (Option[OrientVertex], StatusAddStep, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).addStep(stepBO)
      case (None, ODBConnectionFail()) => 
        (None, AddStepError(), ODBConnectionFail())
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param id: String, stepId: String
   * 
   * @return (StatusAppendStep, Status)
   */
  def appendStepTo(id: String, stepId: String): (StatusAppendStep, Status) = {
    
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).appendStepTo(id, stepId)
      case (None, ODBConnectionFail()) => 
        (AppendStepError(), ODBConnectionFail())
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return
   */
  
  def deleteStep(stepId: String): (StatusDeleteStep, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).deleteStep(stepId)
      case (None, ODBConnectionFail()) => 
        (DeleteStepError(), ODBConnectionFail())
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configId
   * 
   * @return Count of deleted Vertexes
   */
  def deleteStepAppendedToConfig(configId: String): Int = {
    ((Database.getFactory(): @unchecked): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).deleteStepAppendedToConfig(configId)
      case (None, ODBConnectionFail()) => 
        0
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  def updateStep(stepBO: StepBO): (StatusUpdateStep, Status) = {
    ((Database.getFactory(): @unchecked): @unchecked) match {
      case (Some(dbFactory), Success()) => 
        val graph: OrientGraph = dbFactory.getTx()
        new Graph(graph).updateStep(stepBO)
      case (None, ODBConnectionFail()) => 
        (UpdateStepError(), ODBConnectionFail())
    }
  }
}



class Graph(graph: OrientGraph) {

  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String, String
   * 
   * @return LoginBO
   */
  
  private def getUser(username: String, password: String): (Option[OrientVertex], StatusGetUser, Status) = {
    
    try {
      val dynElemUsers: OrientDynaElementIterable = 
        graph.command(new OCommandSQL(s"SELECT FROM AdminUser WHERE username='$username' and password='$password'")).execute()
      graph.commit
      val vUsers: List[OrientVertex] = dynElemUsers.asScala.toList map (_.asInstanceOf[OrientVertex])
      
      vUsers match {
        case userCount if userCount.size == 1 => (Some(vUsers.head), GetUserSuccess(), Success())
        case _ => (None, GetUserNotExist(), Error())
      }
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (None, GetUserAlreadyExist(), ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, GetUserError(), ODBClassCastError())
      }
      case e1: Exception => {
        graph.rollback()
        Logger.error(e1.printStackTrace().toString)
        (None, GetUserError(), ODBReadError())
      }
    }
  }
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  private def getConfigs(userId: String): (Option[List[OrientVertex]], StatusGetConfigs, Status) = {
    try {
      val vConfigs: List[OrientVertex] = 
        graph.getVertex(userId).getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_CONFIG).asScala.toList map (
            _.asInstanceOf[OrientEdge].getVertex(Direction.IN))
      vConfigs match {
        case List() => (Some(vConfigs), GetConfigsEmpty(), Success())
        case _      => (Some(vConfigs), GetConfigsSuccess(), Success())
      }
      
      
    }catch {
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (None, GetConfigsError(), ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, GetConfigsError(), ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (None, GetConfigsError(), ODBReadError())
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String, String
   * 
   * @return 
   */
  private def addUser(username: String, password: String): (Option[OrientVertex], StatusAddUser, Status) = {
    
   try {
      val vAdminUseres : List[Vertex] = graph.getVertices(PropertyKeys.USERNAME, username).asScala.toList
      if(vAdminUseres.size == 0){
        val vAdminUser: OrientVertex = graph.addVertex("class:" + PropertyKeys.VERTEX_ADMIN_USER, 
          PropertyKeys.USERNAME, username, 
          PropertyKeys.PASSWORD, password)
        graph.commit
        (Some(vAdminUser), AddUserSuccess(), Success())
      }else if(vAdminUseres.size == 1){
        (Some(vAdminUseres.head.asInstanceOf[OrientVertex]), AddUserAlreadyExist(), Error())
      }else{
        (None, AddUserError(), Error())
      }
    }catch{
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, AddUserError(), ODBClassCastError())
      }
      case e1: Exception => {
        graph.rollback()
        Logger.error(e1.printStackTrace().toString)
        (None, AddUserError(), ODBReadError())
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return 
   */
  def addConfig(configUrl: String): (Option[OrientVertex], StatusAddConfig, Status) = {
    
    try {
      val vConfig: OrientVertex = graph.addVertex(
          "class:" + PropertyKeys.VERTEX_CONFIG,
          PropertyKeys.CONFIG_URL, configUrl)
        graph.commit
        (Some(vConfig), AddConfigAdded(), Success())
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (None, AddConfigAlreadyExist(), ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, AddConfigError(), ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (None,AddConfigError(), ODBWriteError())
      }
    }
  }
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return 
   */
  def deleteConfig(configId: String, configUrl: String): (StatusDeleteConfig, Status) = {
    try {
      val sql: String  = s"DELETE VERTEX Config where @rid=$configId and configUrl='$configUrl'"
      val res: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit
      res match {
        case 1 => (DeleteConfigDeleted(), Success())
        case _ => (DeleteConfigDefectID(), Error())
      }
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (DeleteConfigError(), ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (DeleteConfigError(), ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (DeleteConfigError(), ODBWriteError())
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String, String
   * 
   * @return
   */
  def updateConfig(configId: String, configUrl: String): (StatusUpdateConfig, Status) = {
    try {
      graph.getVertex(configId).setProperty(PropertyKeys.CONFIG_URL, configUrl)
      graph.commit
      (UpdateConfigUpdated(), Success())
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (UpdateConfigError(), ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (UpdateConfigError(), ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (UpdateConfigError(), ODBWriteError())
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configId: String
   * 
   * @return (String, Status)
   */
  
  private def getAdminUserId(configId: String): (String, Status) = {
    try {
      val userId: List[String] = 
        graph.getVertex(configId)
        .getEdges(Direction.IN, PropertyKeys.EDGE_HAS_CONFIG).asScala.toList map ( eHasConfig => {
        eHasConfig.getVertex(Direction.OUT).asInstanceOf[OrientVertex].getIdentity.toString()
      })
      userId.size match {
        case size if size == 1 => (userId.head, Success())
        case _ => ("", Error())
      }
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        ("", ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        ("", ODBClassCastError())
      }
      case e4: NullPointerException => {
        graph.rollback()
        Logger.error(e4.printStackTrace().toString)
        ("", ODBNullPointer())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        ("", ODBWriteError())
      }

    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  def appendConfigTo(userId: String, configId: String): Status = {
    try{
      val vUser: OrientVertex = graph.getVertex(userId)
      val vConfig: OrientVertex = graph.getVertex(configId)
      val eHasConfig: OrientEdge = graph.addEdge(
        "class:" + PropertyKeys.EDGE_HAS_CONFIG, 
         vUser, 
         vConfig, 
         PropertyKeys.EDGE_HAS_CONFIG
      )
      graph.commit
      Success()
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        ODBRecordDuplicated()
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        ODBClassCastError()
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        ODBWriteError()
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  def appendStepTo(id: String, stepId: String): (StatusAppendStep, Status) = {
    
    try{
      val v: OrientVertex = graph.getVertex(id)
      val vStep: OrientVertex = graph.getVertex(stepId)
      val eHasSetep = graph.addEdge(
          PropertyKeys.CLASS + PropertyKeys.EDGE_HAS_FIRST_STEP, 
          v, 
          vStep, 
          PropertyKeys.EDGE_HAS_FIRST_STEP
      )
      graph.commit
      (AppendStepSuccess(), Success())
    }catch{
     case e1: ORecordDuplicatedException =>
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (AppendStepError(), ODBRecordDuplicated())
      case e2 : ClassCastException => 
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (AppendStepError(), ODBClassCastError())
      case e3: Exception => 
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (AppendStepError(), ODBWriteError())
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return
   */
  private def deleteStep(stepId: String): (StatusDeleteStep, Status) = {
    try {
      val sql: String  = s"DELETE VERTEX Step where @rid=$stepId"
      val res: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit
      res match {
        case 1 => (DeleteStepSuccess(), Success())
        case _ => (DeleteStepDefectID(), Error())
      }
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (DeleteStepError(), ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (DeleteStepError(), ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (DeleteStepError(), ODBWriteError())
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param
   * 
   * @return
   */
  private def updateStep(stepBO: StepBO): (StatusUpdateStep, Status) = {
    try {
      val vStep: OrientVertex = graph.getVertex(stepBO.stepId.get)
      vStep.setProperty(PropertyKeys.NAME_TO_SHOW, stepBO.nameToShow.get)
      vStep.setProperty(PropertyKeys.KIND, stepBO.kind.get)
      vStep.setProperty(PropertyKeys.SELECTION_CRITERIUM_MIN, stepBO.selectionCriteriumMin.get.toString)
      vStep.setProperty(PropertyKeys.SELECTION_CRITERIUM_MAX, stepBO.selectionCriteriumMax.get.toString)
      graph.commit
      (UpdateStepSuccess(), Success())
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (UpdateStepError(), ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (UpdateStepError(), ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (UpdateStepError(), ODBWriteError())
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  private def getConfigTree(configId: String): (Option[StepForConfigTreeBO], StatusGetConfigTree, Status) = {
    
    try{
      val firstSteps: List[OrientVertex] = 
          graph.getVertex(configId)
          .getEdges(Direction.OUT, "hasFirstStep")
          .asScala.toList.map(eHasFirstStep => {
            eHasFirstStep.getVertex(Direction.IN).asInstanceOf[OrientVertex]
          })
      firstSteps.size match {
        case count if count == 1 => {
          val components: Set[Option[ComponentForConfigTreeBO]] = getComponents(Some(firstSteps.head))
//          RidToHash.setIdAndHash(firstSteps.head.getIdentity.toString)
//          val stepIdHash = RidToHash.getHash(firstSteps.head.getIdentity.toString)
          val configTree = Some(StepForConfigTreeBO(
              firstSteps.head.getIdentity.toString,
              firstSteps.head.getProperty(PropertyKeys.KIND),
              components
          ))
          (configTree, GetConfigTreeSuccess(), Success())
        }
        case _ => (None, GetConfigTreeEmpty(), Success())
      }
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (None, GetConfigTreeError(), ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, GetConfigTreeError(), ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (None, GetConfigTreeError(), ODBWriteError())
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param
   * 
   * @return
   */
  def getComponents(step: Option[OrientVertex]): Set[Option[ComponentForConfigTreeBO]] = {
    
    val components: Set[Option[ComponentForConfigTreeBO]] = step match {
      case Some(step) => {
        val eHasComponents: List[Edge] = step.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList
        val components: List[Option[ComponentForConfigTreeBO]] = eHasComponents.map{ eHasComponent => {
          val vComponent: OrientVertex = eHasComponent.getVertex(Direction.IN).asInstanceOf[OrientVertex]
          val eHasSteps : List[Edge] = vComponent.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList
          val component: Option[ComponentForConfigTreeBO] = eHasSteps match {
            case List() => {
              val lastComponent: Option[ComponentForConfigTreeBO] = Some(
                  ComponentForConfigTreeBO(
                      vComponent.getIdentity.toString(),
                      vComponent.getProperty(PropertyKeys.KIND),
                      None,
                      None
                  )
              )
              lastComponent
            }
            case eHasSteps => {
              val nextSteps: List[StepForConfigTreeBO] = eHasSteps.map{
                eHasStep => {
                  val vStep: OrientVertex = eHasStep.getVertex(Direction.IN).asInstanceOf[OrientVertex]
                  val components: Set[Option[ComponentForConfigTreeBO]] = getComponents(Some(vStep))
                  StepForConfigTreeBO(
                      vStep.getIdentity.toString,
                      vStep.getProperty(PropertyKeys.KIND),
                      components
                  )
                }
              }
              val defaultComponent: Option[ComponentForConfigTreeBO] = nextSteps.size match {
                case count if count == 1 => {
                  Some(ComponentForConfigTreeBO(
                      vComponent.getIdentity.toString(),
                      vComponent.getProperty(PropertyKeys.KIND),
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
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param
   * 
   * @return
   */
  def findDuplicate(
      components: List[Option[ComponentForConfigTreeBO]]) : List[Option[ComponentForConfigTreeBO]] = components match {
    
    case List() => List()
    case x :: xs => insert(x, findDuplicate(xs))
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param
   * 
   * @return
   */
  def insert(
      x: Option[ComponentForConfigTreeBO], 
      xs: List[Option[ComponentForConfigTreeBO]]): List[Option[ComponentForConfigTreeBO]] = xs match {
    
    case List() => List(x)
    case y :: ys =>   if(x.get.nextStepId == y.get.nextStepId) 
      Some(x.get.copy(nextStep = None)) :: xs
    else y :: insert(x, ys)
  }
  
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param String
   * 
   * @return
   */
  private def deleteAllConfigs(username: String): Int = {
    val sql: String  = s"delete vertex Config where @rid in (traverse out('hasConfig') from (select out() from AdminUser where username='$username'))"
    val res: Int = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
    res
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
   * @return Count of deleted Vertexes
   */
  
  def deleteAllStepsAndComponent(configId: String) = {
    val sql: String = s"DELETE VERTEX V where @rid IN (traverse out() from (select out('hasFirstStep') " + 
      s"from Config where @rid='$configId'))"
    val res: Int = graph
      .command(new OCommandSQL(sql)).execute()
    graph.commit
    res
  }
    
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param
   * 
   * @return
   */
  def addStep(stepBO: StepBO): (Option[OrientVertex], StatusAddStep, Status) = {
    
    try{
      stepBO.componentId match {
        case Some(componentId) => {
          //create Step
          (None, AddStepSuccess(), Success())
        }
        case None => {
          //create FirstStep
          stepBO.configId match {
            case Some(configId) => 
              val vConfig: OrientVertex = graph.getVertex(configId)
              vConfig match {
                case null => (None, AddStepDefectComponentOrConfigId(), ODBRecordIdDefect())
                case _ => {
                  val countOfFirstSteps: Int = vConfig.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_FIRST_STEP).asScala.toList.size
                  countOfFirstSteps match {
                    case count if count > 0 => (None, AddStepAlreadyExist(), Error()) 
                    case _=> {
                      val vFirstStep: OrientVertex = graph.addVertex(
                          PropertyKeys.CLASS + PropertyKeys.VERTEX_STEP,
                          PropertyKeys.NAME_TO_SHOW, stepBO.nameToShow.get,
                          PropertyKeys.KIND, stepBO.kind.get,
                          PropertyKeys.SELECTION_CRITERIUM_MIN, stepBO.selectionCriteriumMin.get.toString,
                          PropertyKeys.SELECTION_CRITERIUM_MAX, stepBO.selectionCriteriumMax.get.toString
                      )
                      graph.commit
                      (Some(vFirstStep), AddStepSuccess(), Success())
                    }
                  }
                }
              }
              
            case None => (None, AddStepDefectComponentOrConfigId(), ODBRecordIdDefect())
          }
        }
      }
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (None, AddStepError(), ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, AddStepError(), ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (None, AddStepError(), ODBWriteError())
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configId
   * 
   * @return Count of deleted Vertexes
   */
  def deleteStepAppendedToConfig(configId: String): Int = {
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX Step where @rid IN (SELECT out() from Config where @rid='$configId')")).execute()
    graph.commit
    res
  }
}