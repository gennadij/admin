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
 */
class AddAlreadyExistingUserSpecs
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
      addUser(userWithAlreadyExistingUser, wC)
    }
  }

  "Ein neuer Benutzer wird mit schon exestierendem Namen erstellt. Es soll einen Fehler geben" >> {
    "Sende Action ADD_USER mit username = userExist" >> {
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

//      Logger.info("<- " + userParams)
//      Logger.info("-> " + userResult)
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
}