package org.genericConfig.admin.models.persistence.orientdb

import org.genericConfig.admin.shared.bo.RegistrationBO
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.genericConfig.admin.models.persistence.Database
import com.tinkerpop.blueprints.impls.orient.OrientVertex

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */
object Graph{
  
}

class Graph {
  
  
  private def writeUser(username: String, password: String): RegistrationBO = {
    val graph: OrientGraph = Database.getFactory().getTx()
    
    
    val vUser: (Option[OrientVertex], ) = try {
      ???
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
    
    if(graph.getVertices(PropertyKey.USERNAME, registrationIn.username).asScala.size == 0){
      
        val vAdminUser: Any = try {
          val vAdminUser: OrientVertex = graph.addVertex("class:" + PropertyKey.VERTEX_ADMIN_USER, 
          PropertyKey.USERNAME, registrationIn.username, 
          PropertyKey.PASSWORD, registrationIn.password)
          graph.commit
          vAdminUser
        }catch {
          case e : Exception => graph.rollback()
        }
        
        vAdminUser match {
          case adminUser : OrientVertex => {
            RegistrationOut(
                adminUser.getIdentity.toString(),
                adminUser.getProperty(PropertyKey.USERNAME).toString(),
                StatusSuccessfulRegist.status,
                StatusSuccessfulRegist.message
            )
          }
          case e : Exception => {
            new RegistrationOut(
                "",
                registrationIn.username,
                StatusErrorRegistGeneral.status,
                StatusErrorRegistGeneral.message
            )
          }
        }
    }else{
      new RegistrationOut(
          "", 
          registrationIn.username,
          StatusErrorRegistUserAlreadyExist.status,
          StatusErrorRegistUserAlreadyExist.message
      )
    }
    ???
  }
}