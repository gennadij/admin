package org.genericConfig.admin.models.user

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.admin.MessageHandler
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.models.wrapper.Wrapper
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
class UpdateUserNameSpecs extends Specification
  with MessageHandler
  with BeforeAfterAll
  with CommonFunction
  with Wrapper{

  val wC: WebClient = WebClient.init
  val userToUpdate = "userToUpdate"
  val userUpdated = "userUpdated"
  var userResult : JsValue = null

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

    val userParams = Json.obj(
      "action" -> Actions.UPDATE_USER
      ,"params" -> Json.obj(
        "username" -> "",
        "password"-> "",
        "update" -> Json.obj(
          "newUsername" -> userUpdated,
          "oldUsername" -> userToUpdate,
          "newPassword" -> "",
          "oldPassword" -> ""
        )
      ), "result" -> Json.obj(
        "userId" -> "",
        "username" -> "",
        "errors" -> Json.arr()
      )
    )

    Logger.info("<- " + userParams)

    userResult = wC.handleMessage(userParams)

    Logger.info("-> " + userResult)

  }

  def afterAll: Unit = {
    val count = deleteUser(userUpdated)
    require(count == 1, "Anzahl der geloescten AdminUserVertexes " + count)
  }

  "Beim schon erstelltem Benutzer wird die Benutzername geaendert. Es soll kein Fehler geben" >> {
    "action = userToDelete" >> {
      (userResult \ "action" ).asOpt[String].get === Actions.UPDATE_USER
    }
    "result.username = userUpdated" >> {
      (userResult \ "result" \ "username").asOpt[String].get === userUpdated
    }
    "result.errors = None" >> {
      (userResult \ "result" \ "errors").asOpt[List[ErrorDTO]] must beNone
    }
  }

}
