package org.genericConfig.admin.models.user

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.admin.MessageHandler
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.common.AddUserAlreadyExist
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.shared.Actions
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json._

import scala.collection.JavaConverters._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 13.01.2017
 */
class AddAlreadyExistingUserSpecs
  extends Specification
    with MessageHandler
    with BeforeAfterAll
    with CommonFunction {

  val wC: WebClient = WebClient.init
  val userWithAlreadyExistingUser =         "userExist"
  var userResult : JsValue = null

  def beforeAll(): Unit = {
    prepareAddAlreadyExistingUser(wC)

  }

  def afterAll(): Unit = {
  }

  def prepareAddAlreadyExistingUser(wC: WebClient): Unit = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userWithAlreadyExistingUser'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit()
    val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString.toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userWithAlreadyExistingUser ist schon erstellt worden")
    }else {
      addUser(userWithAlreadyExistingUser, wC)
    }

    val userParams = Json.obj(
      "action" -> Actions.ADD_USER
      ,"params" -> Json.obj(
        "username" -> "userExist",
        "password"-> "userExist",
        "update" -> Json.obj(
          "newUsername" -> "",
          "oldUsername" -> "",
          "newPassword" -> "",
          "oldPassword" -> ""
        )
      ),
      "result" -> Json.obj(
        "userId" -> "",
        "username" -> "",
        "errors" -> Json.arr()
      )
    )

    userResult = wC.handleMessage(userParams)

  }

  "Ein neuer Benutzer wird mit schon exestierendem Namen erstellt. Es soll einen Fehler geben" >> {
    "action = ADD_USER" >> {
      (userResult \ "action").asOpt[String].get === Actions.ADD_USER
    }
    "params = None" >> {
      (userResult \ "params").asOpt[String] === None
    }
    "result.userId = None" >> {
      (userResult \ "result" \ "userId").asOpt[String] === None
    }
    "result.errors = ADD_USER_ALREADY_EXIST" >> {
      ((userResult \ "result" \ "errors")(0) \ "name" ).asOpt[String].get must_== AddUserAlreadyExist().name
    }
  }
}