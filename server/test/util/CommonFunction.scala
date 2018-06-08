package util

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.logic.{Config, User}
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.models.persistence.orientdb.Graph
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.config.bo.{ConfigBO, Configuration}
import org.genericConfig.admin.shared.config.status.{GetConfigsEmpty, GetConfigsError, GetConfigsSuccess, StatusAddConfig}
import org.genericConfig.admin.shared.user.bo.UserBO
import org.genericConfig.admin.shared.user.json.{JsonUserIn, JsonUserOut, JsonUserParams}
import play.api.Logger
import play.api.libs.json.{JsValue, Json}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 22.04.2018
  */
trait CommonFunction {

  def addUser(username: String): (String, String, String) = {

    val jsonAddUserIn = Json.toJsObject(JsonUserIn(
      json = JsonNames.ADD_USER,
      params = JsonUserParams(
        username = username,
        password = username
      )

    ))

    val wC = WebClient.init

    //    Logger.info("IN " + jsonAddUserIn)

    val jsonAddUserOut = wC.handleMessage(jsonAddUserIn)

    val addUserOut = Json.fromJson[JsonUserOut](jsonAddUserOut)

    //    Logger.info("OUT " + jsonAddUserOut)

    (addUserOut.get.result.username.get, addUserOut.get.result.userId.get, addUserOut.get.result.status.addUser.get.status)
  }

  def deleteAllConfigs(username: String): Int = {
    Graph.deleteAllConfigs(username)
  }

  def addConfig(userId: String, configUrl: String): (String, StatusAddConfig) = {

    val configBOIn = ConfigBO(
      userId = Some(userId),
      configs = Some(List(Configuration(configUrl = Some(configUrl))))
    )
    val configBOOut = Config.addConfig(configBOIn)

    (configBOOut.configs.get.head.configId.get, configBOOut.status.get.addConfig.get)
  }


  def deleteStepAppendedToConfig(configId: String): Int = {
    Graph.deleteStepAppendedToConfig(configId)
  }

  def getConfigs(userId: String, wC: WebClient): Set[String] = {
    val getConfigsIn = Json.obj(
      "json" -> JsonNames.GET_CONFIGS
      , "params" -> Json.obj(
        "userId" -> userId
      )
    )
    val getConfigsOut = wC.handleMessage(getConfigsIn)

    Logger.info("getConfigsIn " + getConfigsIn)
    Logger.info("getConfigsOut " + getConfigsOut)

    val jsConfigsIds: Set[JsValue] = (getConfigsOut \ "result" \ "configs").asOpt[Set[JsValue]].get

    jsConfigsIds map (jsCId => (jsCId \ "configId").asOpt[String].get)
  }

  def addStep(wC: WebClient, configId: Option[String] = None, componentId: Option[String] = None): Option[String] = {

    configId match {
      case Some(configId) =>
        val stepIn = Json.obj(
          "json" -> JsonNames.ADD_FIRST_STEP,
          "params" -> Json.obj(
            "configId" -> configId,
            "componentId" -> "",
            "stepId" -> "",
            "nameToShow" -> "FirstStep",
            "kind" -> "first",
            "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
            )
          )
        )

        Logger.info("<-" + stepIn)

        val stepOut: JsValue = wC.handleMessage(stepIn)

        Logger.info("->" + stepOut)
        (stepOut \ "result" \ "stepId").asOpt[String]
      case None =>
        componentId match {
          case Some(componentId) =>
            val stepIn = Json.obj(
              "json" -> JsonNames.ADD_FIRST_STEP,
              "params" -> Json.obj(
                "configId" -> "",
                "componentId" -> componentId,
                "nameToShow" -> "FirstStep",
                "kind" -> "first",
                "selectionCriterium" -> Json.obj(
                  "min" -> 1,
                  "max" -> 1
                )
              )
            )

            Logger.info("<-" + stepIn)

            val stepOut: JsValue = wC.handleMessage(stepIn)

            Logger.info("->" + stepOut)
            (stepOut \ "result" \ "stepId").asOpt[String]

          case None => None
        }
    }
  }

  def deleteConfigVertex(username: String): Int = {
    val sql: String = s"DELETE VERTEX Config where @rid IN (SELECT OUT('hasConfig') FROM AdminUser WHERE username='$username')"
    val graph: OrientGraph = Database.getFactory()._1.orNull.getTx
    val res: Int = graph
      .command(new OCommandSQL(sql)).execute()
    graph.commit()
    Logger.info("Deleting count: " + res)
    res
  }

  def deleteAdmin(username: String): Int = {
    val graph: OrientGraph = Database.getFactory()._1.orNull.getTx
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX AdminUser where username='$username'")).execute()
    graph.commit()
    res
  }

  def registerNewUser(userPassword: String, webClient: WebClient): Unit = {
    val registerCS = Json.obj(
      "json" -> JsonNames.ADD_USER
      , "params" -> Json.obj(
        "username" -> userPassword,
        "password" -> userPassword
      )
    )
    val registerSC = webClient.handleMessage(registerCS)

    Logger.info("registerCS " + registerCS)
    Logger.info("registerSC " + registerSC)
    require((registerSC \ "result" \ "username").asOpt[String].get == userPassword, s"Username: $userPassword")
  }

  def getUserId(userPassword: String, webClient: WebClient): String = {

    val userBO = UserBO(
      username = Some(userPassword),
      password = Some(userPassword)
    )

    val userBOOut = User.getUser(userBO)

    userBOOut.userId.get
  }



  def getConfigId(usernamePassword: String, configUrl: String = ""): String = {

    val userBOIn = UserBO(
      username = Some(usernamePassword),
      password = Some(usernamePassword)
    )

    val userBOOut = User.getUser(userBOIn)

    val configBOIn = ConfigBO(
      userId = Some(userBOOut.userId.get)
    )

    val configBOOut = Config.getConfigs(configBOIn)
    configBOOut.status.get.getConfigs.get match {
      case GetConfigsSuccess() =>
        configBOOut.configs.get.head.configId.get
      case GetConfigsEmpty() =>
        val addConfigBOIn = ConfigBO(
          userId = Some(userBOOut.userId.get),
          configs = Some(List(Configuration(configUrl = Some(configUrl))))
        )

        val addConfigBOOut = Config.addConfig(addConfigBOIn)

        addConfigBOOut.configs.get.head.configId.get
      case GetConfigsError() => {
        configBOOut.status.get.getConfigs.get.status
      }
    }


  }
}