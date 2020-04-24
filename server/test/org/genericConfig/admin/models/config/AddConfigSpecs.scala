package org.genericConfig.admin.models.config

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 16.01.2017
  *
  * Username = user3
  */

class AddConfigSpecs extends Specification with BeforeAfterAll with CommonFunction {

  var configDTO :JsResult[ConfigDTO] = _

  val username = "user3"

  def beforeAll(): Unit = {

    val configResult : JsValue = before()

    Logger.info("<- " + configResult)

    configDTO = Json.fromJson[ConfigDTO](configResult)
  }

  def afterAll(): Unit = {
    deleteConfigVertex(username)
  }

  "Der Benutzer fuegt eine neue Konfiguration hinzu" >> {
    "result.userId" >> {
      configDTO.asOpt.get.result.get.userId.get.size must be_<=(32)
    }
    "result.configs(0).configId" >> {
      configDTO.asOpt.get.result.get.configs.get.head.configId.get.size must be_<=(32)
    }
    "result.configs(0).configUrl" >> {
      configDTO.asOpt.get.result.get.configs.get.head.configUrl.get === "http://contig1/user3"
    }
    "result.errors" >> {
      configDTO.asOpt.get.result.get.errors === None
    }
  }

  def before(): JsValue = {

    val wC: WebClient = WebClient.init

    val userId: String = createUser(username, wC)

    val configParams = Json.toJson[ConfigDTO](ConfigDTO (
      action = Actions.ADD_CONFIG,
      params = Some (ConfigParamsDTO (
        userId = Some (userId),
        configUrl = Some ("http://contig1/user3"),
        configurationCourse = Some ("sequence")
      ))
    ))

    Logger.info ("-> " + configParams)

    wC.handleMessage(configParams)
  }
}