package org.genericConfig.admin.models.user

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.converter.MessageHandler
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsValue, Json}

import scala.collection.JavaConverters._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 12.03.2020
 */

class DeleteExistingUserSpecs extends Specification
  with MessageHandler
  with BeforeAfterAll
  with CommonFunction {

  val wC: WebClient = WebClient.init
  val userToDelete = "userToDelete"
  var userResult : JsValue = null

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

    val userParams = Json.obj(
      "action" -> Actions.DELETE_USER
      ,"params" -> Json.obj(
        "username" -> userToDelete,
        "password"-> userToDelete,
        "update" -> Json.obj(
          "newUsername" -> "",
          "oldUsername" -> "",
          "newPassword" -> "",
          "oldPassword" -> ""
        )
      ), "result" -> Json.obj(
        "userId" -> "",
        "username" -> "",
        "errors" -> Json.arr()
      )
    )

    userResult = wC.handleMessage(userParams)

  }

  def afterAll() = {

  }

  "Ein schon erstellter Benutzer wird geloescht. Es soll kein Fehler geben" >> {
    "Client sendet action = DELETE_USER, username = userToDelete" >> {
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
