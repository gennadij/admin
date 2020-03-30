package org.genericConfig.admin.models.config

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 24.04.2018
 */

class GetEmptyConfigsSpecs extends Specification
                           with BeforeAfterAll
                           with CommonFunction{

  val wC: WebClient = WebClient.init
  val username = "user_v016_7"
  var getConfigsResult : JsResult[ConfigDTO] = _

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {}

  "Der Benutzer ruft die Konfiguration auf " >> {
    "action = GET_CONFIGS" >> {getConfigsResult.asOpt.get.action === Actions.GET_CONFIGS}
    "result.configs = Empty" >> {getConfigsResult.asOpt.get.result.get.configs.get.isEmpty must beTrue}
    "result.errors = None" >> {getConfigsResult.asOpt.get.result.get.errors must beNone}
  }
  private def before(): Unit = {
    val userId : String = createUser(this.username, wC)

    val getConfigParams = Json.toJson(ConfigDTO(
      action = Actions.GET_CONFIGS,
      params = Some(ConfigParamsDTO(
        userId = Some(userId)
      )),
      result = None
    ))

    Logger.info("getConfigParams " + getConfigParams)

    getConfigsResult = Json.fromJson[ConfigDTO](wC.handleMessage(getConfigParams))

    Logger.info("getConfigsOut " + getConfigsResult)
  }
}