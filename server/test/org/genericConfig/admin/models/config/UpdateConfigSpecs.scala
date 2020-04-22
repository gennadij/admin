package org.genericConfig.admin.models.config

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.common.{ConfigIdHashNotExist, ConfigNothingToUpdate}
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, GraphConfig, PropertyKeys}
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
    before()
  }

  def afterAll(): Unit = {
    after()
  }

  "Der Benutzer aendert die Parameter der Konfiguration" >> {
    "Der Benutzer aendert nur configUrl" >> {
      "action = UPDATE_CONFIG" >> {
        val action = updateConfigResultOnlyConfigUrl.asOpt.get.action
        action === Actions.UPDATE_CONFIG
      }
      "result.configs(0).configId = " + configIdForOnlyConfigUrl >> {
        val configId = updateConfigResultOnlyConfigUrl.asOpt.get.result.get.configs.get.head.configId.get
        configId === configIdForOnlyConfigUrl
      }
      "result.configs(0).configUrl = " + configUrlOnlyConfigUrlUpdated >> {
        updateConfigResultOnlyConfigUrl.asOpt.get.result.get.configs.get.head.configUrl.get === configUrlOnlyConfigUrlUpdated
      }
      "result.configs(0).configurationCourse = " + PropertyKeys.CONFIGURATION_COURSE_SEQUENCE>> {
        updateConfigResultOnlyConfigUrl.asOpt.get.result.get.configs.get.head.configurationCourse.get ===
          PropertyKeys.CONFIGURATION_COURSE_SEQUENCE
      }
      "result.errors = None" >> {updateConfigResultOnlyConfigUrl.asOpt.get.result.get.errors === None}
    }
    "Der Benutzer aendert nur configurationCourse" >> {
      "action = UPDATE_CONFIG" >> {
        updateConfigResultOnlyConfigurationCourse.asOpt.get.action === Actions.UPDATE_CONFIG
      }
      "result.configs(0).configId = " + configIdForOnlyConfigurationsCourse >> {
        val configId = updateConfigResultOnlyConfigurationCourse.asOpt.get.result.get.configs.get.head.configId.get
        configId === configIdForOnlyConfigurationsCourse
      }
      "result.configs(0).configUrl = //http://contig1/user_v016_6_update_only_configurationCourse" >> {
        val configUrl = updateConfigResultOnlyConfigurationCourse.asOpt.get.result.get.configs.get.head.configUrl.get
        configUrl === "//http://contig1/user_v016_6_update_only_configurationCourse"}
      "result.configs(0).configurationCourse = " + PropertyKeys.CONFIGURATION_COURSE_SUBSTITUTE >> {
        val configurationCourse =
          updateConfigResultOnlyConfigurationCourse.asOpt.get.result.get.configs.get.head.configurationCourse.get
        configurationCourse === PropertyKeys.CONFIGURATION_COURSE_SUBSTITUTE}
      "result.errors = None" >> {
        updateConfigResultOnlyConfigurationCourse.asOpt.get.result.get.errors === None}
    }
    "Der Benutzer aendert sowohl configUrl als auch configurationCourse" >> {
      "action = UPDATE_CONFIG" >> {
        updateConfigResultBoth.asOpt.get.action === Actions.UPDATE_CONFIG
      }
      "result.configs(0).configId = " + configIdForBoth >> {
        val configId = updateConfigResultBoth.asOpt.get.result.get.configs.get.head.configId.get
        configId === configIdForBoth}
      "result.configs(0).configUrl = //http://contig1/user_v016_6_updated_both" >> {
        val configUrl = updateConfigResultBoth.asOpt.get.result.get.configs.get.head.configUrl.get
        configUrl === "//http://contig1/user_v016_6_updated_both"}
      "result.configs(0).configurationCourse = " + PropertyKeys.CONFIGURATION_COURSE_SUBSTITUTE >> {
        val configurationCourse =
          updateConfigResultBoth.asOpt.get.result.get.configs.get.head.configurationCourse.get
        configurationCourse === PropertyKeys.CONFIGURATION_COURSE_SUBSTITUTE}
      "result.errors = " >> {updateConfigResultBoth.asOpt.get.result.get.errors === None}
    }
    "Der Benutzer aendert nichts" >> {
      "action = UPDATE_CONFIG" >> {updateConfigResultNothing.asOpt.get.action ==== Actions.UPDATE_CONFIG}
      "result.configs = None" >> {updateConfigResultNothing.asOpt.get.result.get.configs === None}
      "result.errors = " >> {updateConfigResultNothing.asOpt.get.result.get.errors.get.head.name === ConfigNothingToUpdate().name}
    }
    "Der Benutzer sendet UPDATE_CONFIG mit defekten ID" >> {
      "action = UPDATE_CONFIG" >> {updateConfigResultDefectId.asOpt.get.action === Actions.UPDATE_CONFIG}
      "result.errors = " >> {updateConfigResultDefectId.asOpt.get.result.get.errors.get.head.name === ConfigIdHashNotExist().name}
    }
  }

  private def before(): Unit = {
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
    Logger.info("<- " + updateConfigParamsOnlyConfigurationCourse)
    Logger.info("<- " + updateConfigParamsBoth)
    Logger.info("<- " + updateConfigParamsNothing)
    Logger.info("<- " + updateConfigParamsDefectId)

    updateConfigResultOnlyConfigUrl = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsOnlyConfigUrl))
    updateConfigResultOnlyConfigurationCourse = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsOnlyConfigurationCourse))
    updateConfigResultBoth = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsBoth))
    updateConfigResultNothing = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsNothing))
    updateConfigResultDefectId = Json.fromJson[ConfigDTO](wC.handleMessage(updateConfigParamsDefectId))

    Logger.info("-> " + updateConfigResultOnlyConfigUrl)
    Logger.info("-> " + updateConfigResultOnlyConfigurationCourse)
    Logger.info("-> " + updateConfigResultBoth)
    Logger.info("-> " + updateConfigResultNothing)
    Logger.info("-> " + updateConfigResultDefectId)
  }

  private def after() = {
    Logger.info("Alle erstellte Configs werden geloescht")
    GraphCommon.deleteVertex(RidToHash.getRId(configIdForOnlyConfigUrl).get, PropertyKeys.VERTEX_CONFIG)
    GraphCommon.deleteVertex(RidToHash.getRId(configIdForOnlyConfigurationsCourse).get, PropertyKeys.VERTEX_CONFIG)
    GraphCommon.deleteVertex(RidToHash.getRId(configIdForBoth).get, PropertyKeys.VERTEX_CONFIG)
    GraphCommon.deleteVertex(RidToHash.getRId(configIdForNothing).get, PropertyKeys.VERTEX_CONFIG)
  }
}