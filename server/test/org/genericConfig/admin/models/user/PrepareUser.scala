package org.genericConfig.admin.models.user


import scala.collection.JavaConverters._
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.json.StatusSuccessfulConfig
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.shared.common.json.JsonNames
import play.api.Logger
import play.api.libs.json.Json

class PrepareUser {

  val userWithAlredyExistingUser =         "userExist"
  val userLogin =                          "user2"

  def prepareWithAlredyExistingUser(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userWithAlredyExistingUser'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
    val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userWithAlredyExistingUser ist schon erstellt worden")
    }else {
      registerNewUser(userWithAlredyExistingUser, wC)
    }
  }

  def prepareLogin(wC : WebClient) = {

    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userLogin'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
    val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userLogin ist schon erstellt worden")
    }else{
      registerNewUser(userLogin, wC)

      val adminId: String = login(userLogin, wC)

      println("adminId " + adminId)

      val configId: String = createNewConfig(adminId, "http://contig1/user2", wC)

      println("configId " + configId)
    }
  }

  def registerNewUser(userPassword: String, webClient: WebClient) = {
    val registerCS = Json.obj(
      "json" -> JsonNames.ADD_USER
      ,"params" -> Json.obj(
        "username" -> userPassword,
        "password"-> userPassword
      )
    )
    val registerSC = webClient.handleMessage(registerCS)

    Logger.info("registerCS " + registerCS)
    Logger.info("registerSC " + registerSC)
    require((registerSC \ "result" \ "username").asOpt[String].get == userPassword, s"Username: $userPassword")
  }

  def login (userPassword: String, webClient: WebClient): String = {
    val loginCS = Json.obj(
      "json" -> JsonNames.GET_USER
      ,"params" -> Json.obj(
        "username" -> userPassword,
        "password" -> userPassword
      )
    )

    val loginSC = webClient.handleMessage(loginCS)

    (loginSC \ "result" \ "adminId").asOpt[String].get
  }

  def createNewConfig(adminId: String, configUrl: String, webClient: WebClient): String = {
    val createConfigCS = Json.obj(
      "json" -> JsonNames.ADD_CONFIG
      , "params" -> Json.obj(
        "adminId" -> adminId,
        "configUrl" -> configUrl
      )
    )
    val createConfigSC = webClient.handleMessage(createConfigCS)
    require((createConfigSC \ "result" \ "status").asOpt[String].get == StatusSuccessfulConfig.status, (createConfigSC \ "result" \ "status").asOpt[String].get.toString())
    require((createConfigSC \ "result" \ "message").asOpt[String].get == StatusSuccessfulConfig.message)

    (createConfigSC \ "result" \ "configId").asOpt[String].get
  }

}
