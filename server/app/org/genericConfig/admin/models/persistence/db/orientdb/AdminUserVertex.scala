package org.genericConfig.admin.models.persistence.db.orientdb

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import play.api.Logger
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.models.wrapper.registration.RegistrationIn
import org.genericConfig.admin.models.wrapper.registration.RegistrationOut
import org.genericConfig.admin.models.json.StatusSuccessfulRegist
import org.genericConfig.admin.models.json.StatusErrorRegistUserAlreadyExist
import org.genericConfig.admin.models.json.StatusErrorRegistGeneral
import org.genericConfig.admin.models.wrapper.login.LoginIn
import org.genericConfig.admin.models.wrapper.login.LoginOut
import org.genericConfig.admin.models.wrapper.login.Config
import org.genericConfig.admin.models.json.StatusSuccessfulLogin
import org.genericConfig.admin.models.json.StatusErrorLogin

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
 */
object AdminUserVertex {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param RegistrationCS
   * 
   * @return RegistrationSC
   */
  def register(registrationIn: RegistrationIn): RegistrationOut = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
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
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param 
   * 
   * @return 
   */
  def update = ???

  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param LoginCS
   * 
   * @return LoginSC
   */
  def login(loginIn: LoginIn): LoginOut = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val username: String = loginIn.username
    val password : String = loginIn.password
    val res: OrientDynaElementIterable = graph
      .command(new OCommandSQL(s"SELECT FROM AdminUser WHERE username='$username' and password='$password'")).execute()
    graph.commit
    val adminUsers = res.asScala.toList
    if(adminUsers.size == 1){
      val loginSC: List[LoginOut] = adminUsers.map(login => {
        val vAdminUser: OrientVertex = login.asInstanceOf[OrientVertex]
        
        val eHasConfigs: List[Edge] = vAdminUser.getEdges(Direction.OUT, PropertyKey.EDGE_HAS_CONFIG).asScala.toList
        
        val vConfigs: List[Vertex] = eHasConfigs.map(eHasConfig => {
          eHasConfig.getVertex(Direction.IN)
        })
        
        val configs: List[Config] = vConfigs.map(vConfig => {
          Config(
              vConfig.getId.toString,
              vConfig.getProperty("configUrl")
              
          )
        })
        LoginOut(
            login.asInstanceOf[OrientVertex].getIdentity.toString,
            login.asInstanceOf[OrientVertex].getProperty("username").toString,
            configs,
            StatusSuccessfulLogin.status,
            StatusSuccessfulLogin.message
        )
      })
    loginSC(0)
    }else{
      LoginOut(
              "", 
              loginIn.username, 
              List.empty,
              StatusErrorLogin.status,
              StatusErrorLogin.message
      )
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param username
   * 
   * @return Unit
   */
  def deleteAdmin(username: String): Int = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX AdminUser where username='$username'")).execute()
    graph.commit
    res
  }
}