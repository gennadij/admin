package org.genericConfig.admin.models.config

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.persistence.orientdb.PropertyKeys
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO, ConfigUpdateDTO}
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
  var updateConfigResultOnlyConfigUrl : JsResult[ConfigDTO] = _
  var updateConfigResultOnlyConfigurationCourse : JsResult[ConfigDTO] = _
  var updateConfigResultBoth : JsResult[ConfigDTO] = _
  var updateConfigResultNothing : JsResult[ConfigDTO] = _
  var updateConfigResultDefectId : JsResult[ConfigDTO] = _

  var configIdForOnlyConfigUrl : String = _
  var configIdForOnlyConfigurationsCourse : String = _
  var configIdForBoth : String = _
  var configIdForNothing : String = _

  val configUrlOnlyConfigUrlUpdated = "//http://contig1/user_v016_6_updated_only_confgUrl"

  def beforeAll(): Unit = {
  }

  def afterAll(): Unit = {
  }

  "Der Benutzer aendert die Parameter der Konfiguration" >> {
    "Der Benutzer aendert nur configUrl" >> {
      "action = UPDATE_CONFIG" >> {
        val action = updateConfigResultOnlyConfigUrl.asOpt.get.action
        action === Actions.UPDATE_CONFIG
      }
      "result.configs(0).configId = " >> {
        val configId = updateConfigResultOnlyConfigUrl.asOpt.get.result.get.configs.get(0).configId.get
        configId === configIdForOnlyConfigUrl
      }
      "result.configs(0).configUrl = " >> {
        updateConfigResultOnlyConfigUrl.asOpt.get.result.get.configs.get(0).configUrl.get === configUrlOnlyConfigUrlUpdated
      }
      "result.configs(0).configurationCourse = " >> {
        updateConfigResultOnlyConfigUrl.asOpt.get.result.get.configs.get(0).configurationCourse.get === PropertyKeys.CONFIGURATION_COURSE_SEQUENCE
      }
      "result.errors = " >> {updateConfigResultOnlyConfigUrl.asOpt.get.result.get.errors === None}
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

    configIdForOnlyConfigUrl =
      createConfig(userId, "//http://contig1/user_v016_6_update_only_confgUrl")
    configIdForOnlyConfigurationsCourse =
      createConfig(userId, "//http://contig1/user_v016_6_update_only_configurationCourse")
    configIdForBoth =
      createConfig(userId, "//http://contig1/user_v016_6_update_both")
    configIdForNothing =
      createConfig(userId, "//http://contig1/user_v016_6_update_nothing")

    val updateConfigParamsOnlyConfigUrl = Json.toJson(ConfigDTO(
      action = Actions.UPDATE_CONFIG,
      params = Some(ConfigParamsDTO(
        configId = Some(configIdForOnlyConfigUrl),
        update = Some(ConfigUpdateDTO(
          configUrl = Some(configUrlOnlyConfigUrlUpdated),
          configurationCourse = None
        ))
      )),
      result = None
    ))

    val updateConfigParamsOnlyConfigurationCourse = Json.toJson(ConfigDTO(
      action = Actions.UPDATE_CONFIG,
      params = Some(ConfigParamsDTO(
        configId = Some(configIdForOnlyConfigurationsCourse),
        update = Some(ConfigUpdateDTO(
          configUrl = None,
          configurationCourse = Some(PropertyKeys.CONFIGURATION_COURSE_SUBSTITUTE)
        ))
      )),
      result = None
    ))

    val updateConfigParamsBoth = Json.toJson(ConfigDTO(
      action = Actions.UPDATE_CONFIG,
      params = Some(ConfigParamsDTO(
        configId = Some(configIdForBoth),
        update = Some(ConfigUpdateDTO(
          configUrl = Some("//http://contig1/user_v016_6_updated_both"),
          configurationCourse = Some(PropertyKeys.CONFIGURATION_COURSE_SUBSTITUTE)
        ))
      )),
      result = None
    ))

    val updateConfigParamsNothing = Json.toJson(ConfigDTO(
      action = Actions.UPDATE_CONFIG,
      params = Some(ConfigParamsDTO(
        configId = Some(configIdForNothing),
        update = Some(ConfigUpdateDTO(
          configUrl = None,
          configurationCourse = None
        ))
      )),
      result = None
    ))

    val updateConfigParamsDefectId = Json.toJson(ConfigDTO(
      action = Actions.UPDATE_CONFIG,
      params = Some(ConfigParamsDTO(
        configId = Some("11111"),
        update = Some(ConfigUpdateDTO(
          configUrl = None,
          configurationCourse = None
        ))
      )),
      result = None
    ))

    Logger.info("<- " + updateConfigParamsOnlyConfigUrl)

    updateConfigResultOnlyConfigUrl = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsOnlyConfigUrl))
    updateConfigResultOnlyConfigurationCourse = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsOnlyConfigurationCourse))
    updateConfigResultBoth = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsBoth))
    updateConfigResultNothing = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsNothing))
    updateConfigResultDefectId = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsDefectId))

    Logger.info("-> " + updateConfigResultOnlyConfigUrl)
  }
}