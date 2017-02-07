package org.persistence.db.orientdb

import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import com.orientechnologies.orient.core.metadata.schema.OType
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.dto.login.LoginSC
import org.dto.login.LoginCS
import org.dto.login.LoginResult
import org.dto.registration.RegistrationCS
import org.dto.registration.RegistrationSC
import org.dto.registration.RegistrationResult
import org.dto.login.Config
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
 */
object AdminUserVertex {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param RegistrationCS
   * 
   * @return RegistrationSC
   */
  def register(registrationCS: RegistrationCS): RegistrationSC = {
    val graph: OrientGraph = OrientDB.getGraph
    if(graph.getVertices(PropertyKey.USERNAME, registrationCS.params.username).size == 0){
      val vAdminUser: OrientVertex = graph.addVertex(
          "class:" + PropertyKey.VERTEX_ADMIN_USER, 
          PropertyKey.USERNAME, registrationCS.params.username, 
          PropertyKey.PASSWORD, registrationCS.params.password)
      graph.commit
      new RegistrationSC(result = new RegistrationResult(
          vAdminUser.getIdentity.toString(),
          vAdminUser.getProperty(PropertyKey.USERNAME).toString(),
          true,
          "Registrierung war erfolgreich"
      ))
    }else{
      new RegistrationSC(result = new RegistrationResult(
          "", 
          registrationCS.params.username,
          false,
          "Registrierung war nicht erfolgreich. Username existiert bereits"
      ))
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param 
   * 
   * @return 
   */
  def update = ???

  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param LoginCS
   * 
   * @return LoginSC
   */
  def login(loginCS: LoginCS): LoginSC = {
    val graph: OrientGraph = OrientDB.getGraph
    val username: String = loginCS.params.username
    val password : String = loginCS.params.password
    val res: OrientDynaElementIterable = graph
      .command(new OCommandSQL(s"SELECT FROM AdminUser WHERE username='$username' and password='$password'")).execute()
    val adminUsers = res.toList
    if(adminUsers.size == 1){
      val loginSC: List[LoginSC] = adminUsers.map(login => {
        val vAdminUser: OrientVertex = login.asInstanceOf[OrientVertex]
        
        val eHasConfigs: List[Edge] = vAdminUser.getEdges(Direction.OUT, "hasConfig").toList
        
        val vConfigs: List[Vertex] = eHasConfigs.map(eHasConfig => {
          eHasConfig.getVertex(Direction.IN)
        })
        
        val configs: List[Config] = vConfigs.map(vConfig => {
          Config(
              vConfig.getId.toString,
              vConfig.getProperty("configUrl")
              
          )
        })
        new LoginSC(result = new LoginResult(
          login.asInstanceOf[OrientVertex].getIdentity.toString(),
          login.asInstanceOf[OrientVertex].getProperty("username"),
          configs,
          true,
          "Anmeldung mit Username " + loginCS.params.username + " war erfolgreich"
        ))
      })
    loginSC(0)
    }else{
      new LoginSC(result = new LoginResult(
          "", 
          loginCS.params.username, 
          List.empty,
          false, 
          "Anmeldung mit Username " + loginCS.params.username + " war nicht erfolgreich"))
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param username
   * 
   * @return Unit
   */
  def deleteAdmin(username: String): Int = {
    val graph: OrientGraph = OrientDB.getGraph
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX AdminUser where username='$username'")).execute()
    graph.commit
    res
  }
}