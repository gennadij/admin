/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
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
import org.dto.login.LoginResultSC
import org.dto.registration.RegistrationCS
import org.dto.registration.RegistrationSC
import org.dto.registration.RegistrationResultSC
import org.dto.configUri.ConfigUriCS
import org.dto.configUri.ConfigUriSC
import org.dto.configUri.ConfigUriResultSC

/**
 * Created by Gennadi Heimann 1.1.2017
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
          "class:" + PropertyKey.ADMIN_USER, 
          PropertyKey.USERNAME, registrationCS.params.username, 
          PropertyKey.PASSWORD, registrationCS.params.password)
      graph.commit
      new RegistrationSC(result = new RegistrationResultSC(
          vAdminUser.getIdentity.toString(),
          vAdminUser.getProperty(PropertyKey.USERNAME).toString(),
          true,
          "Registrierung war erfolgreich"
      ))
    }else{
      new RegistrationSC(result = new RegistrationResultSC(
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
   * @param 
   * 
   * @return 
   */
//  def configUri(configUriCS: ConfigUriCS): ConfigUriSC = {
//    val graph: OrientGraph = OrientDB.getGraph
//    
//    val adminId = configUriCS.params.adminId
//    val configUri = configUriCS.params.configUri
//    //UPDATE AdminUser SET configUri='test' where @rid='#21:3'
//    val res: Int = graph
//      .command(new OCommandSQL(s"UPDATE AdminUser SET configUri='$configUri' where @rid='$adminId'")).execute()
//    
//    if(res == 1) {
//      new ConfigUriSC(result = new ConfigUriResultSC(
//            true,
//            s"Die URI = $configUri wurde fuer der AdminUser=$adminId hinzugefuegt"
//          )
//      )
//    }else{
//      new ConfigUriSC(result = new ConfigUriResultSC(
//            false,
//            s"Die URI = $configUri wurde nicht fuer der AdminUser=$adminId hinzugefuegt"
//          )
//      )
//    }
//  }
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
        new LoginSC(result = new LoginResultSC(
          login.asInstanceOf[OrientVertex].getIdentity.toString(),
          login.asInstanceOf[OrientVertex].getProperty("username"),
          true,
          "Anmeldung mit Username " + loginCS.params.username + " war erfolgreich"
        ))
      })
    loginSC(0)
    }else{
      new LoginSC(result = new LoginResultSC(
          "", 
          loginCS.params.username, 
          false, 
          "Anmeldung mit Username " + loginCS.params.username + " war nicht erfolgreich"))
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param username, adminPassword
   * 
   * @return String
   */
  def adminId(username: String, adminPassword: String): String = {
    val graph: OrientGraph = OrientDB.getGraph
    val res: OrientDynaElementIterable = graph
      .command(new OCommandSQL(s"SELECT FROM AdminUser WHERE username='$username' and password='$adminPassword'")).execute()
    val adminId = res.toList.map(_.asInstanceOf[OrientVertex].getIdentity)
    if(adminId.size == 1) adminId.head.toString else ""
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
  def removeAdmin(username: String) = {
    val graph: OrientGraph = OrientDB.getGraph
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX AdminUser where username='$username'")).execute()
    graph.commit
  }
}