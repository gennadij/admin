package org.genericConfig.admin.models.user

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.admin.AdminWeb
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
 *
 * Username = user2
 */

class AddingAlreadyExistingAdminUserSpecs
  extends Specification
    with AdminWeb
    with BeforeAfterAll
    with CommonFunction {

  val wC: WebClient = WebClient.init
  val userWithAlreadyExistingUser =         "userExist"

  def beforeAll(): Unit = {
    prepareWithAlreadyExistingUser(wC)
  }

  def afterAll(): Unit = {
  }

  def prepareWithAlreadyExistingUser(wC: WebClient): Unit = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userWithAlreadyExistingUser'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit()
    val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString.toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userWithAlreadyExistingUser ist schon erstellt worden")
    }else {
      registerNewUser(userWithAlreadyExistingUser, wC)
    }
  }

  "Specification spezifiziert die Registrierung des exestierenden Users" >> {
    "schon exstierenden User hinzufuegen" >> {
      val userParams = Json.obj(
          "action" -> Actions.ADD_USER
          ,"params" -> Json.obj(
          "username" -> "userExist",
            "password"-> "userExist"
           ),
        "result" -> Json.obj(
          "userId" -> "",
          "username" -> "",
          "errors" -> Json.arr()
        )
      )

      val userResult = wC.handleMessage(userParams)

      Logger.info("<- " + userParams)
      Logger.info("-> " + userResult)
      "action" >> {
        (userResult \ "action").asOpt[String].get === Actions.ADD_USER
      }
      "params" >> {
        (userResult \ "params").asOpt[String] === None
      }
      "userId" >> {
        (userResult \ "result" \ "userId").asOpt[String] === None
      }
      "errors" >> {
        ((userResult \ "result" \ "errors")(0) \ "name" ).asOpt[String].get must_== AddUserAlreadyExist().status
      }
    }
  }
}