package org.genericConfig.admin.models.config

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.logic.Config
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, Json}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 22.04.2018
  */

class AddSeveralConfigsSpecs extends Specification
  with BeforeAfterAll
  with CommonFunction {

  var resultConfigDTO_1 : JsResult[ConfigDTO] = _
  var resultConfigDTO_2 : JsResult[ConfigDTO] = _
  var resultConfigDTO_3 : JsResult[ConfigDTO] = _

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {
    deleteConfigs()
  }


  "Ein Benutzer erstellt mehrere Configs" >> {
    "Config 1 action = ADD_CONFIG" >> {resultConfigDTO_1.get.action === Actions.ADD_CONFIG}
    "Config 1 result.error = None" >> {resultConfigDTO_1.get.result.get.errors === None}
    "Config 1 result.configs(1).configId < 32" >> {resultConfigDTO_1.get.result.get.configs.get.head.configId.size must be_<=(32) }
    "Config 2 action = ADD_CONFIG" >> {resultConfigDTO_2.get.action === Actions.ADD_CONFIG}
    "Config 2 result.error = None" >> {resultConfigDTO_2.get.result.get.errors === None}
    "Config 2 result.configs(1).configId < 32" >> {resultConfigDTO_2.get.result.get.configs.get.head.configId.size must be_<=(32) }
    "Config 3 action = ADD_CONFIG" >> {resultConfigDTO_3.get.action === Actions.ADD_CONFIG}
    "Config 3 result.error = None" >> {resultConfigDTO_3.get.result.get.errors === None}
    "Config 3 result.configs(1).configId < 32" >> {resultConfigDTO_3.get.result.get.configs.get.head.configId.size must be_<=(32) }
  }

  private def before(): Unit = {
    val wC: WebClient = WebClient.init
    val username = "user_1_v016"
    val configUrl_1 = "//http://contig1/user_1_v016_1"
    val configUrl_2 = "//http://contig1/user_1_v016_2"
    val configUrl_3 = "//http://contig1/user_1_v016_3"

    val userId : String = createUser(username, wC)

    val paramsConfig_1 = Json.toJson(ConfigDTO(
      action = Actions.ADD_CONFIG,
      params = Some(ConfigParamsDTO(
        userId = Some(userId),
        configUrl = Some(configUrl_1),
        configurationCourse = Some("sequence")
      )),
      result = None
    ))

    Logger.info("1 <- " + paramsConfig_1)

    resultConfigDTO_1 = Json.fromJson[ConfigDTO](wC.handleMessage(paramsConfig_1))

    Logger.info("1 ->" + resultConfigDTO_1)

    val paramsConfig_2 = Json.toJson(ConfigDTO(
      action = Actions.ADD_CONFIG,
      params = Some(ConfigParamsDTO(
        userId = Some(userId),
        configUrl = Some(configUrl_2),
        configurationCourse = Some("sequence")
      )),
      result = None
    ))

    Logger.info("2 <-" + paramsConfig_2)

    resultConfigDTO_2 = Json.fromJson[ConfigDTO](wC.handleMessage(paramsConfig_2))

    Logger.info("2 ->" + resultConfigDTO_2)

    val paramsConfig_3 = Json.toJson(ConfigDTO(
      action = Actions.ADD_CONFIG,
      params = Some(ConfigParamsDTO(
        userId = Some(userId),
        configUrl = Some(configUrl_3),
        configurationCourse = Some("sequence")
      )),
      result = None
    ))

    Logger.info("3 <-" + paramsConfig_3)

    resultConfigDTO_3 = Json.fromJson[ConfigDTO](wC.handleMessage(paramsConfig_3))

    Logger.info("3 ->" + resultConfigDTO_3)
  }

  private def deleteConfigs() = {
    val configDTO_1 : ConfigDTO = ConfigDTO(
      action = Actions.DELETE_CONFIG,
      params = Some(ConfigParamsDTO(
        userId = None,
        configId = Some(resultConfigDTO_1.get.result.get.configs.get.head.configId.get),
        configUrl = None,
        configurationCourse = None
      )),
      result = None
    )
    Config.deleteConfig(configDTO_1)

    val configDTO_2 : ConfigDTO = ConfigDTO(
      action = Actions.DELETE_CONFIG,
      params = Some(ConfigParamsDTO(
        userId = None,
        configId = Some(resultConfigDTO_2.get.result.get.configs.get.head.configId.get),
        configUrl = None,
        configurationCourse = None
      )),
      result = None
    )
    Config.deleteConfig(configDTO_2)

    val configDTO_3 : ConfigDTO = ConfigDTO(
      action = Actions.DELETE_CONFIG,
      params = Some(ConfigParamsDTO(
        userId = None,
        configId = Some(resultConfigDTO_3.get.result.get.configs.get.head.configId.get),
        configUrl = None,
        configurationCourse = None
      )),
      result = None
    )
    Config.deleteConfig(configDTO_3)
  }
}