package org.genericConfig.admin.models.persistence.orientdb

import scala.collection.JavaConverters._
import org.genericConfig.admin.shared.bo.RegistrationBO
import org.genericConfig.admin.shared.status.Status
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.genericConfig.admin.models.persistence.Database
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import play.api.Logger
import org.genericConfig.admin.shared.status.registration.AddedUser
import org.genericConfig.admin.shared.status.ODBClassCastError
import org.genericConfig.admin.shared.status.ODBReadError
import org.genericConfig.admin.shared.status.Error
import org.genericConfig.admin.shared.status.registration.StatusRegistration
import org.genericConfig.admin.shared.status.Success
import org.genericConfig.admin.shared.status.registration.AlredyExistUser
import org.genericConfig.admin.shared.bo.LoginBO
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import org.genericConfig.admin.shared.status.ODBWriteError
import org.genericConfig.admin.shared.status.ODBRecordDuplicated
import com.tinkerpop.blueprints.Vertex
import org.genericConfig.admin.shared.status.config.StatusConfig
import org.genericConfig.admin.shared.status.config.StatusGetConfigs
import org.genericConfig.admin.shared.status.config.GetConfigsEmpty
import org.genericConfig.admin.shared.status.config.GetConfigsGot
import org.genericConfig.admin.shared.status.config.GetConfigsError
import org.genericConfig.admin.shared.status.config.StatusAddConfig
import org.genericConfig.admin.shared.status.config.AddConfigAdded
import org.genericConfig.admin.shared.status.config.AddConfigAlreadyExist
import org.genericConfig.admin.shared.status.config.AddConfigError


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
  def createConfig(configUrl: String): (Option[OrientVertex], StatusAddConfig, Status) = {
    new Graph(Database.getFactory().getTx()).createConfig(configUrl)
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
  def createConfig(configUrl: String): (Option[OrientVertex], StatusAddConfig, Status) = {
    
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
//      (Some(eHasConfig), Success())
      Success()
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
//        (None, ODBRecordDuplicated())
        ODBRecordDuplicated()
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
//        (None, ODBClassCastError())
        ODBClassCastError()
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
//        (None, ODBWriteError())
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
  private def deleteAllConfigs(username: String): Int = {
    val sql: String  = s"delete vertex Config where @rid in (traverse out('hasConfig') from (select out() from AdminUser where username='$username'))"
    val res: Int = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
    res
  }
  

}