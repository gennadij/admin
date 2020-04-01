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
 * Created by Gennadi Heimann 09.05.2018
 */
class UpdateConfigSpecs extends Specification
                           with BeforeAfterAll
                           with CommonFunction{

  val wC: WebClient = WebClient.init
  val username = "user_v016_6"
  var updateConfigResultOriginal : JsResult[ConfigDTO] = _

  def beforeAll(): Unit = {
  }

  def afterAll(): Unit = {
  }

  "Der Benutzer aendert die Parameter der Konfiguration" >> {
    "Der Benutzer aendert nur configUrl" >> {
      "action = UPDATE_CONFIG" >> {"" === ""}
      "result.configs(0).configId = " >> {"" === ""}
      "result.configs(0).configUrl = " >> {"" === ""}
      "result.configs(0).configurationCourse = " >> {"" === ""}
      "result.errors = " >> {"" === ""}
    }
    "Der Benutzer aendert nur configurationCourse" >> {
      "action = UPDATE_CONFIG" >> {"" === ""}
      "result.configs(0).configId = " >> {"" === ""}
      "result.configs(0).configUrl = " >> {"" === ""}
      "result.configs(0).configurationCourse = " >> {"" === ""}
      "result.errors = " >> {"" === ""}
    }
    "Der Benutzer aendert sowohl configUrl als auch configurationCourse" >> {
      "action = UPDATE_CONFIG" >> {"" === ""}
      "result.configs(0).configId = " >> {"" === ""}
      "result.configs(0).configUrl = " >> {"" === ""}
      "result.configs(0).configurationCourse = " >> {"" === ""}
      "result.errors = " >> {"" === ""}
    }
    "Der Benutzer aendert nichts" >> {
      "action = UPDATE_CONFIG" >> {"" === ""}
      "result.configs(0).configId = " >> {"" === ""}
      "result.configs(0).configUrl = " >> {"" === ""}
      "result.configs(0).configurationCourse = " >> {"" === ""}
      "result.errors = " >> {"" === ""}
    }
    "Der Benutzer sendet UPDATE_CONFIG mit defekten ID" >> {
      "action = UPDATE_CONFIG" >> {"" === ""}
      "result.errors = " >> {"" === ""}
    }
  }

  private def before() = {
    val userId: String = createUser(this.username, wC)

    val configId : String = addConfig(userId, "//http://contig1/user_v016_6")

    val updateConfigParamsOriginal = Json.toJson(ConfigDTO(
      action = Actions.UPDATE_CONFIG,
      params = Some(ConfigParamsDTO(
        configId = Some(configId)
      )),
      result = None
    ))

    Logger.info("<- " + updateConfigParamsOriginal)

    updateConfigResultOriginal = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsOriginal))

    Logger.info("-> " + updateConfigResultOriginal)
  }
}