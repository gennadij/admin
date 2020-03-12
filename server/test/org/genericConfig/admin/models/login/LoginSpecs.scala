package org.genericConfig.admin.models.login

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.ErrorDTO
import org.specs2.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.json.{JsValue, Json}

import scala.collection.JavaConverters._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 13.01.2017
 *
 * Username = user2
 */

//@RunWith(classOf[JUnitRunner])
class LoginSpecs extends Specification with BeforeAfterAll with CommonFunction {

  val wC: WebClient = WebClient.init

  def afterAll(): Unit = {}
  def beforeAll(): Unit = {
    val userLogin =                          "user2"

    def prepareLogin(wC : WebClient): Unit = {

      val graph: OrientGraph = OrientDB.getFactory().getTx
      val sql: String = s"select count(username) from AdminUser where username like '$userLogin'"
      val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
      graph.commit()
      val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString.toInt).head
      if(count == 1 ) {
        Logger.info(s"Der User $userLogin ist schon erstellt worden")
      }else{
        registerNewUser(userLogin, wC)

        val adminId: String = getUserId(userLogin, wC)

        println("adminId " + adminId)

        val (configId, _) = addConfig(adminId, "http://contig1/user2")

        println("configId " + configId)
      }
    }
  }

  def is =


  s2"""
       Specification fuer die Pruefung der Registrierung eines neuen Admins
          json                                                            $e2
          username                                                        $e4
          userId                                                          $e5
          commonStatus                                                    $e7
    """
  val user = "user2"

  val userParams = Json.obj(
      "action" -> Actions.GET_USER
      ,"params" -> Json.obj(
          "username" -> user,
          "password"-> user),
      "result" -> Json.obj(
        "userId" -> "",
        "username" -> "",
        "errors" -> Json.arr()
      ))


  val userResult: JsValue = wC.handleMessage(userParams)

  Logger.info("jsonClientServer: " + userParams)
  Logger.info("jsonServerClient: " + userResult)

  def e2 = (userResult \ "action").asOpt[String].get must_== Actions.GET_USER
  def e4 = (userResult \ "result" \ "username").asOpt[String].get must_== user
  def e5 = (userResult \ "result" \ "userId").asOpt[String].get.size must_== 32
  def e7 = (userResult \ "result" \ "errors").asOpt[List[ErrorDTO]] must_== None
}