package org.genericConfig.admin.models.persistence.orientdb

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.genericConfig.admin.models.persistence.Database
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import play.api.Logger
import org.genericConfig.admin.shared.registration.status._
import org.genericConfig.admin.shared.common.status._
import org.genericConfig.admin.shared.login.bo.LoginBO
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.Vertex
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.configTree.bo._
import org.genericConfig.admin.shared.registration.bo.RegistrationBO
import org.genericConfig.admin.shared.configTree.status.StatusGetConfigTree
import org.genericConfig.admin.shared.configTree.status._
import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.step.status._


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
  def addUser(username: String, password: String): RegistrationBO = {
    val graph: OrientGraph = Database.getFactory().getTx()
    new Graph(graph).writeUser(username, password)
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
  def readUser(username: String, password: String): (Option[OrientVertex], Status) = {
    new Graph(Database.getFactory().getTx()).readUser(username, password)
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
    new Graph(Database.getFactory().getTx()).addConfig(configUrl)
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
    new Graph(Database.getFactory().getTx()).deleteConfig(configId, configUrl)
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
    new Graph(Database.getFactory().getTx()).appendConfigTo(userId, configId)
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
    new Graph(Database.getFactory.getTx).deleteAllConfigs(username)
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
    new Graph(Database.getFactory.getTx).getConfigs(userId)
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
  
  def getConfigTree(configId: String) = {
    new Graph(Database.getFactory.getTx).getConfigTree(configId)
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
    new Graph(Database.getFactory.getTx).getAdminUserId(configId)
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
    new Graph(Database.getFactory.getTx).updateConfig(configId, configUrl)
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
  def addStep(stepBO: StepBO): (Option[OrientVertex], StatusCreateStep, Status) = {
    new Graph(Database.getFactory.getTx).addStep(stepBO)
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
  def appendStepTo(id: String, stepId: String): (StatusAppendStep, Status) = {
    new Graph(Database.getFactory.getTx).appendStepTo(id, stepId)
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
    new Graph(Database.getFactory.getTx).deleteStep(stepId)
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
  
  private def readUser(username: String, password: String): (Option[OrientVertex], Status) = {
    
    try {
      val dynElemUsers: OrientDynaElementIterable = 
        graph.command(new OCommandSQL(s"SELECT FROM AdminUser WHERE username='$username' and password='$password'")).execute()
      graph.commit
      val vUsers: List[OrientVertex] = dynElemUsers.asScala.toList map (_.asInstanceOf[OrientVertex])
      
      vUsers match {
        case userCount if userCount.size == 1 => (Some(vUsers.head), Success())
        case _ => (None, Error())
      }
    }catch{
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, ODBClassCastError())
      }
      case e1: Exception => {
        graph.rollback()
        Logger.error(e1.printStackTrace().toString)
        (None, ODBReadError())
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
        case _      => (Some(vConfigs), GetConfigsGot(), Success())
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
   * @return RegistrationBO
   */
  private def writeUser(username: String, password: String): RegistrationBO = {
    val vUser: (Option[OrientVertex], Status)  = try {
      val vAdminUseres : List[Vertex] = graph.getVertices(PropertyKeys.USERNAME, username).asScala.toList
      if(vAdminUseres.size == 0){
        val vAdminUser: OrientVertex = graph.addVertex("class:" + PropertyKeys.VERTEX_ADMIN_USER, 
          PropertyKeys.USERNAME, username, 
          PropertyKeys.PASSWORD, password)
        graph.commit
        (Some(vAdminUser), AddedUser())
      }else if(vAdminUseres.size == 1){
        (Some(vAdminUseres.head.asInstanceOf[OrientVertex]), AlredyExistUser())
      }else{
        (None, Error())
      }
    }catch{
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, ODBClassCastError())
      }
      case e1: Exception => {
        graph.rollback()
        Logger.error(e1.printStackTrace().toString)
        (None, ODBReadError())
      }
    }
    vUser match {
      case (Some(vUser), AddedUser()) => {
        RegistrationBO(
            username, "",
            vUser.getIdentity.toString(), 
            StatusRegistration(Some(AddedUser()), Some(Success()))
        )
      }
      case (Some(vUser), AlredyExistUser()) => {
        RegistrationBO(
            username, "", 
            vUser.getIdentity.toString, 
            status = StatusRegistration(Some(AlredyExistUser()), Some(Success())))
      }
      case _ => {
        RegistrationBO("", "", "", StatusRegistration(None, Some(Error())))
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
   * @param String
   * 
   * @return
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
      val v = graph.getVertex(id)
      val vStep = graph.getVertex(stepId)
      graph.addEdge(
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
      val sql: String  = s"DELETE VERTEX Config where @rid=$stepId"
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
          val configTree = Some(StepForConfigTreeBO(
              firstSteps.head.getIdentity.toString,
              firstSteps.head.getProperty(PropertyKeys.KIND),
              components
          ))
          (configTree, GetConfigTreeGot(), Success())
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
   * @param String
   * 
   * @return
   */
  def addStep(stepBO: StepBO): (Option[OrientVertex], StatusCreateStep, Status) = {
    
    try{
      stepBO.componentId match {
        case Some(componentId) => {
          //create Step
          (None, CreateStepSuccess(), Success())
        }
        case None => {
          //create FirstStep
          stepBO.configId match {
            case Some(configId) => 
              val vConfig: OrientVertex = graph.getVertex(stepBO.configId)
              val countOfFirstSteps: Int = vConfig.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_FIRST_STEP).asScala.toList.size
              countOfFirstSteps match {
                case count if count > 0 => (None, CreateStepAlreadyExist(), Error()) 
                case _=> {
                  val vFirstStep: OrientVertex = graph.addVertex(
                      "class:" + PropertyKeys.VERTEX_STEP,
                      PropertyKeys.NAME_TO_SHOW, stepBO.nameToShow,
                      PropertyKeys.KIND, stepBO.kind,
                      PropertyKeys.SELECTION_CRITERIUM_MIN, stepBO.selectionCriteriumMin.toString,
                      PropertyKeys.SELECTION_CRITERIUM_MAX, stepBO.selectionCriteriumMax.toString
                  )
                  (Some(vFirstStep), CreateStepSuccess(), Success())
                }
              }
            case None => (None, CreateStepDefectComponentOrConfigId(), Error())
          }
        }
      }
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (None, CreateStepError(), ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, CreateStepError(), ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (None, CreateStepError(), ODBWriteError())
      }
    }
  }
}