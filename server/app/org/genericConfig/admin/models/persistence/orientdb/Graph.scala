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
import org.genericConfig.admin.shared.bo.ConfigBO
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import org.genericConfig.admin.shared.status.ODBWriteError
import org.genericConfig.admin.shared.status.ODBRecordDuplicated


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
   * @param String, String
   * 
   * @return LoginBO
   */
  def readConfigs(userId: String): (Option[List[OrientVertex]], Status) = {
    new Graph(Database.getFactory().getTx()).readConfigs(userId)
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
  def createConfig(configUrl: String): (Option[OrientVertex], Status) = {
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
  def appendConfigTo(userId: String, vConfig: OrientVertex): (Option[OrientEdge], Status) = {
    new Graph(Database.getFactory().getTx()).appendConfigTo(userId, vConfig)
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
   * @param String, String
   * 
   * @return LoginBO
   */
  private def readConfigs(adminId: String): (Option[List[OrientVertex]], Status) = {
    
    try{
      val vUser: OrientVertex = graph.getVertex(adminId)
      val vConfigs: List[OrientVertex] = vUser.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_CONFIG).asScala.toList map (
          _.getVertex(Direction.IN).asInstanceOf[OrientVertex]
      )
      (Some(vConfigs), Success())
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
   * @param String, String
   * 
   * @return RegistrationBO
   */
  private def writeUser(username: String, password: String): RegistrationBO = {
    val vUser: (Option[OrientVertex], Status)  = try {
      if(graph.getVertices(PropertyKeys.USERNAME, username).asScala.size == 0){
        val vAdminUser: OrientVertex = graph.addVertex("class:" + PropertyKeys.VERTEX_ADMIN_USER, 
          PropertyKeys.USERNAME, username, 
          PropertyKeys.PASSWORD, password)
        graph.commit
        (Some(vAdminUser), AddedUser())
      }else{
        (None, AlredyExistUser())
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
      case (None, AlredyExistUser()) => {
        RegistrationBO(status = StatusRegistration(Some(AlredyExistUser()), Some(Success())))
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
  def createConfig(configUrl: String): (Option[OrientVertex], Status) = {
    
    try {
      val vConfig: OrientVertex = graph.addVertex(
          "class:" + PropertyKeys.VERTEX_CONFIG,
          PropertyKeys.CONFIG_URL, configUrl)
        graph.commit
        (Some(vConfig), Success())
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (None, ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (None, ODBWriteError())
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
  def appendConfigTo(userId: String, vConfig: OrientVertex): (Option[OrientEdge], Status) = {
    try{
      val vUser = graph.getVertex(userId)
      val eHasConfig: OrientEdge = graph.addEdge(
        "class:" + PropertyKeys.EDGE_HAS_CONFIG, 
         vUser, 
         vConfig, 
         PropertyKeys.EDGE_HAS_CONFIG
      )
      graph.commit
      (Some(eHasConfig), Success())
    }catch{
      case e1: ORecordDuplicatedException => {
        Logger.error(e1.printStackTrace().toString)
        graph.rollback()
        (None, ODBRecordDuplicated())
      }
      case e2 : ClassCastException => {
        graph.rollback()
        Logger.error(e2.printStackTrace().toString)
        (None, ODBClassCastError())
      }
      case e3: Exception => {
        graph.rollback()
        Logger.error(e3.printStackTrace().toString)
        (None, ODBWriteError())
      }
    }
  }
}