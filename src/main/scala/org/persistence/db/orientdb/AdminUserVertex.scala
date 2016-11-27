package org.persistence.db.orientdb

import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientVertexType
import org.status.SuccessfulStatus
import org.status.WarningStatus
import com.orientechnologies.orient.core.metadata.schema.OType
import org.status.Status
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.admin.AdminUser
import org.dto.RegistrationSC
import org.dto.RegistrationResultSC
import org.dto.RegistrationCS
import org.dto.LoginSC
import org.dto.LoginCS
import org.dto.LoginResultSC

object AdminUserVertex {
  
  val className = "AdminUser"
  val propKeyAdminId = "adminId"
  val propKeyAdminUsername = "username"
  val propKeyAdminUserPassword = "password"
  
//  def create(adminUsername: String, adminUserPassword: String): AdminUser = {
//    // TODO impl status autentifications
//    val graph: OrientGraph = OrientDB.getGraph
//    if(graph.getVertices(propKeyAdminUsername, adminUsername).size == 0){
//      val vAdminUser: OrientVertex = graph.addVertex(s"class:$className",
//                      propKeyAdminUsername, adminUsername, 
//                      propKeyAdminUserPassword, adminUserPassword)
//      graph.commit
//      new AdminUser("AU" + vAdminUser.getIdentity.toString(),
//                    vAdminUser.getProperty(propKeyAdminUsername).toString(), 
//                    vAdminUser.getProperty(propKeyAdminUserPassword).toString(),
//                    true)
//    }else{
//      new AdminUser("", "", "", false)
//    }
//  }
  
  def register(registrationCS: RegistrationCS): RegistrationSC = {
    // TODO impl status autentifications
    val graph: OrientGraph = OrientDB.getGraph
    if(graph.getVertices(propKeyAdminUsername, registrationCS.params.username).size == 0){
      val vAdminUser: OrientVertex = graph.addVertex(s"class:$className",
                      propKeyAdminUsername, registrationCS.params.username, 
                      propKeyAdminUserPassword, registrationCS.params.password)
      graph.commit
      new RegistrationSC(result = new RegistrationResultSC(
          "AU" + vAdminUser.getIdentity.toString(),
          vAdminUser.getProperty(propKeyAdminUsername).toString()
      ))
    }else{
      new RegistrationSC(result = null)
    }
  }
  
  def update = ???
  
  def login(loginCS: LoginCS): LoginSC = {
    val graph: OrientGraph = OrientDB.getGraph
    val username: String = loginCS.params.username
    val password : String = loginCS.params.password
    val res: OrientDynaElementIterable = graph
      .command(new OCommandSQL(s"SELECT FROM AdminUser WHERE username='$username' and password='$password'")).execute()
    val loginSC: List[LoginSC] = res.toList.map(login => {
      new LoginSC(result = new LoginResultSC(
          "AU" + login.asInstanceOf[OrientVertex].getIdentity,
          login.asInstanceOf[OrientVertex].getProperty("username"),
          true
      ))
    })
    if(loginSC.size == 1) loginSC(0) else new LoginSC(result = new LoginResultSC("", "", false))
      
      new LoginSC(result = new LoginResultSC("","",true))
  }
  
  def adminId(username: String, adminPassword: String): String = {
    val graph: OrientGraph = OrientDB.getGraph
    val res: OrientDynaElementIterable = graph
      .command(new OCommandSQL(s"SELECT FROM AdminUser WHERE username='$username' and password='$adminPassword'")).execute()
    val adminId = res.toList.map(_.asInstanceOf[OrientVertex].getIdentity)
    if(adminId.size == 1) adminId.head.toString else ""
  }
}