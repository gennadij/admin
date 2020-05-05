package org.genericConfig.admin.models.config

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 24.04.2018
 */

@RunWith(classOf[JUnitRunner])
class GetSeveralConfigsSpecs extends Specification
                           with BeforeAfterAll
                           with CommonFunction{

  val wC: WebClient = WebClient.init
  val username = "user_v016_2"
  var getConfigsResult : JsResult[ConfigDTO] = _
  val configUrl_1 = "http://contig1/user_v016_2"
  val configUrl_2 = "http://contig2/user_v016_2"
  val configUrl_3 = "http://contig3/user_v016_2"
  var configId_1 : String = _
  var configId_2 : String = _
  var configId_3 : String = _

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {
    val configRId_1 = RidToHash.getRId(configId_1)
    val configRId_2 = RidToHash.getRId(configId_2)
    val configRId_3 = RidToHash.getRId(configId_3)
  }

  "Der Benutzer ruft die Konfiguration auf " >> {
    "fuer einen AdminUser" >> {
      "action = GET_CONFIGS" >> {getConfigsResult.asOpt.get.action === Actions.GET_CONFIGS}
      "result.configs(0).configUrl = //http://contig1/user_1_v016_1" >> {
        getConfigsResult.asOpt.get.result.get.configs.get.head.configId.get === configId_1
        getConfigsResult.asOpt.get.result.get.configs.get.head.configUrl.get === configUrl_1
      }
      "result.configs(0).configUrl = //http://contig1/user_1_v016_2" >> {
        getConfigsResult.asOpt.get.result.get.configs.get(1).configId.get === configId_2
        getConfigsResult.asOpt.get.result.get.configs.get(1).configUrl.get === configUrl_2
      }
      "result.configs(0).configUrl = //http://contig1/user_1_v016_3" >> {
        getConfigsResult.asOpt.get.result.get.configs.get(2).configId.get === configId_3
        getConfigsResult.asOpt.get.result.get.configs.get(2).configUrl.get === configUrl_3
      }
      "result.errors = None" >> {getConfigsResult.asOpt.get.result.get.errors must beNone}
    }
  }

  private def before(): Unit = {
    val userId: String = createUser(this.username, wC)

    configId_1 = createConfig(userId, configUrl_1)

    configId_2 = createConfig(userId, configUrl_2)

    configId_3 = createConfig(userId, configUrl_3)

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