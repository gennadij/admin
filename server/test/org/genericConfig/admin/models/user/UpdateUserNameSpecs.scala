package org.genericConfig.admin.models.user

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.models.wrapper.Wrapper
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
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
class UpdateUserNameSpecs extends Specification
  with AdminWeb
  with BeforeAfterAll
  with CommonFunction
  with Wrapper{

  val wC: WebClient = WebClient.init
  val userToUpdate = "userToUpdate"
  val userToUpdate_1 = "userToUpdate_1"

  def beforeAll(): Unit = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userToUpdate'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit()
    val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString.toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userToUpdate ist schon erstellt worden")
    }else {
      addUser(userToUpdate, wC)
    }
  }

  def afterAll: Unit = {}

  "Beim schon erstelltem Benutzer wird die Benutzername geaendert. Es soll kein Fehler geben" >> {
    "Client sendet action = UPDATE_USER, username = userToUpdate_1" >> {
      val userParams = Json.obj(
        "action" -> Actions.UPDATE_USERNAME
        ,"params" -> Json.obj(
          "username" -> userToUpdate_1,
          "password"-> userToUpdate_1
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
        (userResult \ "result" \ "username").asOpt[String].get === userToUpdate_1
      }
      "result.userId = None" >> {
        (userResult \ "result" \ "userId").asOpt[String].get.size must be_<=(32)
      }
      "result.password = None" >> {
        (userResult \ "result" \ "userId").asOpt[String].get == userToUpdate
      }
      "result.errors = None" >> {
        (userResult \ "result" \ "errors").asOpt[List[ErrorDTO]] must beNone
      }
    }
  }

}
