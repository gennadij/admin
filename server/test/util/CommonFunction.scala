package util

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.logic.{Config, User}
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.models.persistence.orientdb.Graph
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.config.bo.{ConfigBO, Configuration}
import org.genericConfig.admin.shared.config.json.{JsonAddConfigIn, JsonAddConfigOut, JsonAddConfigParams}
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

  def addAdminUser(username: String): (String, String) = {

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

    (addUserOut.get.result.username.get, addUserOut.get.result.userId.get)
  }

  def deleteAllConfigs(username: String): Int = {
    Graph.deleteAllConfigs(username)
  }

  def createConfig(uId: String, cUrl: String, wC: WebClient): JsonAddConfigOut = {
    val newConfigIn = Json.toJsObject(
      JsonAddConfigIn(
        json = JsonNames.ADD_CONFIG,
        params = JsonAddConfigParams(
          userId = uId,
          configUrl = cUrl
        )
      )
    )

    Logger.info("IN " + newConfigIn)

    val newConfigOut = wC.handleMessage(newConfigIn)

    Logger.info("OUT " + newConfigOut)

    val addConfigOut = Json.fromJson[JsonAddConfigOut](newConfigOut).get

    addConfigOut
  }

  def deleteStepAppendedToConfig(configId: String) = {
    Graph.deleteStepAppendedToConfig(configId)
  }

  def getConfigs(userId: String, wC: WebClient) = {
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
    val graph: OrientGraph = Database.getFactory()._1.getOrElse(null).getTx
    val res: Int = graph
      .command(new OCommandSQL(sql)).execute()
    graph.commit
    Logger.info("Deleting count: " + res)
    res
  }

  def deleteAdmin(username: String): Int = {
    val graph: OrientGraph = Database.getFactory()._1.getOrElse(null).getTx
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX AdminUser where username='$username'")).execute()
    graph.commit
    res
  }

  def registerNewUser(userPassword: String, webClient: WebClient) = {
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

  def login(userPassword: String, webClient: WebClient): String = {
    //    val jsonGerUserIn = Json.toJsObject(JsonUserIn(
    //      json = JsonNames.GET_USER,
    //      params = JsonUserParams(
    //        username = userPassword,
    //        password = userPassword
    //      )
    //    ))

    val userBO = UserBO(
      username = Some(userPassword),
      password = Some(userPassword)
    )

    val userBOOut = User.getUser(userBO)


    //    val loginSC = webClient.handleMessage(jsonGerUserIn)

    //    val jsonGetUserOut = Json.fromJson[JsonUserOut](loginSC)
    //
    //    jsonGetUserOut.getOrElse(null).result.userId.get
    userBOOut.userId.get
  }

  def createNewConfig(userId: String, configUrl: String, webClient: WebClient): String = {

    val addConfigIn = ConfigBO(
      Some(userId),
      Some(List(new Configuration(
        None,
        Some(configUrl)
      ))),
      None
    )

    val addConfigOut: ConfigBO = Config.addConfig(addConfigIn)

    addConfigOut.configs.get.head.configId.get
  }
}