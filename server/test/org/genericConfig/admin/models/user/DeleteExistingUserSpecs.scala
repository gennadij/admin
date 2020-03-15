package org.genericConfig.admin.models.user

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.models.wrapper.Wrapper
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.ErrorDTO
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.Json

import scala.collection.JavaConverters._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 12.03.2020
 */

class DeleteExistingUserSpecs extends Specification
  with AdminWeb
  with BeforeAfterAll
  with CommonFunction
  with Wrapper{

  sequential

  val wC: WebClient = WebClient.init
  val userToDelete = "userToDelete"

  def beforeAll() = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userToDelete'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit()
    val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString.toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userToDelete ist schon erstellt worden")
    }else {
      addUser(userToDelete, wC)
    }
  }

  def afterAll() = {

  }

  "Ein schon erstellter Benutzer wird geloescht. Es soll kein Fehler geben" >> {
    "Client sendet action = DELETE_USER, username = userToDelete" >> {
      val userParams = Json.obj(
        "action" -> Actions.DELETE_USER
        ,"params" -> Json.obj(
          "username" -> userToDelete,
          "password"-> userToDelete
        ), "result" -> Json.obj(
          "userId" -> "",
          "username" -> "",
          "errors" -> Json.arr()
        )
      )
      val wC = WebClient.init
      val userResult = wC.handleMessage(userParams)
//      Logger.info("<- " + userParams)
//      Logger.info("-> " + userResult)

      "result.username = userToDelete" >> {
        (userResult \ "result" \ "username").asOpt[String].get === userToDelete
      }
      "result.userId = None" >> {
        (userResult \ "result" \ "userId").asOpt[String] must beNone
      }
      "result.errors = None" >> {
        (userResult \ "result" \ "errors").asOpt[List[ErrorDTO]] must beNone
      }
    }
  }
}
