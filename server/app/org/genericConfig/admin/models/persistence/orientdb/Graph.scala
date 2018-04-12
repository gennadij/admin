package org.genericConfig.admin.models.persistence.orientdb

import scala.collection.JavaConverters._
import org.genericConfig.admin.shared.bo.RegistrationBO
import org.genericConfig.admin.shared.status.Status
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.genericConfig.admin.models.persistence.Database
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import play.api.Logger
import org.genericConfig.admin.models.wrapper.registration.RegistrationOut
import org.genericConfig.admin.shared.status.registration.AddedUser
import org.genericConfig.admin.shared.status.ODBClassCastError
import org.genericConfig.admin.shared.status.ODBReadError
import org.genericConfig.admin.shared.status.Error
import org.genericConfig.admin.shared.status.registration.StatusRegistration
import org.genericConfig.admin.shared.status.Success
import org.genericConfig.admin.shared.status.registration.AlredyExistUser


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */
object Graph{
  def addUser(username: String, password: String): RegistrationBO = {
    val graph: OrientGraph = Database.getFactory().getTx()
    new Graph(graph).writeUser(username, password)
  }
}

class Graph(graph: OrientGraph) {

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
}