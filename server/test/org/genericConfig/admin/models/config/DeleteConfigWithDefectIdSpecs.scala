package org.genericConfig.admin.models.config

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.common.ConfigIdHashNotExist
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 02.05.2018
 */
//@RunWith(classOf[JUnitRunner])
class DeleteConfigWithDefectIdSpecs extends Specification
                           with BeforeAfterAll
                           with CommonFunction{
  
  val wC: WebClient = WebClient.init
  val username = "user_v016_5"
  var deleteConfigResult : JsResult[ConfigDTO] = _
  
  def beforeAll(): Unit = {
    before()
  }
  
  def afterAll(): Unit = {
//    Logger.info("Deleting Configs : " + deleteAllConfigs(this.username))
  }
  
  "Der Binutzer versucht die Konfiguration mit defkten Id zu loeschen" >> {
    "action = DELETE_CONFIG" >> {
      deleteConfigResult.get.action === Actions.DELETE_CONFIG
    }
    "result.errors = ConfigIdHashNotExist" >> {
      deleteConfigResult.get.result.get.errors.get.head.name === ConfigIdHashNotExist().name
    }
  }

  private def before(): Unit = {

    val userId : String = createUser(username, wC)
    Logger.info(userId)
    val configId : String = createConfig(userId,"//http://contig1/user_v016_5")

    val deleteConfigParams = Json.toJson(
      ConfigDTO(
        action = Actions.DELETE_CONFIG,
        params = Some(ConfigParamsDTO(
          userId = None,
          configId = Some("1111"),
          configUrl = None,
          configurationCourse = None,
          update = None
        )),
        result = None
      )
    )
    Logger.info("<- " + deleteConfigParams)

    deleteConfigResult = Json.fromJson[ConfigDTO](wC.handleMessage(deleteConfigParams))

    Logger.info("-> " + deleteConfigResult)
  }
}