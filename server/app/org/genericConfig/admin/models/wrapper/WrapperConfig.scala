package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.shared.common.json.{JsonConfig, JsonStatus}
import org.genericConfig.admin.shared.common.status.Status
import org.genericConfig.admin.shared.config.bo.{ConfigBO, Configuration}
import org.genericConfig.admin.shared.config.json._
import play.api.Logger

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 25.05.2018
  */
class WrapperConfig {


  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param jsonConfigIn : JsonAddConfigIn
    * @return ConfigBO
    */

  private[wrapper] def toAddConfigBO(jsonConfigIn: JsonAddConfigIn): ConfigBO = {

    ConfigBO(
      userId = Some(jsonConfigIn.params.userId),
      configs = Some(List(Configuration(configUrl = Some(jsonConfigIn.params.configUrl))))
    )
  }


  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configBO : ConfigBO
    * @return JsonAddConfigOut
    */
  private[wrapper] def toJsonAddConfigOut(configBO: ConfigBO): JsonAddConfigOut = {

    JsonAddConfigOut(
      result = JsonAddConfigResult(
        Some(configBO.userId.get),
        configId = configBO.configs match {
          case Some(configs) => Some(configs.head.configId.get)
          case None => None
        },
        status = JsonConfigStatus(
          setStatus(configBO.status.get.addConfig),
          setStatus(configBO.status.get.getConfigs),
          setStatus(configBO.status.get.deleteConfig),
          setStatus(configBO.status.get.updateConfig),
          setStatus(configBO.status.get.common)
        )
      )
    )
  }

  private[wrapper] def toGetConfigsBO(jsonGetConfigsIn: JsonGetConfigsIn): ConfigBO = {

    ConfigBO(
      userId = Some(jsonGetConfigsIn.params.userId)
    )
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configBO : ConfigBO
    * @return JsonGetConfigsOut
    */
  private[wrapper] def toJsonGetConfigsOut(configBO: ConfigBO): JsonGetConfigsOut = {

    val cBOs: List[JsonConfig] = configBO.configs match {
      case Some(configs) => configs map { config => {
        JsonConfig(
          config.configId.get,
          config.configUrl.get
        )
      }}
      case None => List()
    }
    JsonGetConfigsOut(
      result = JsonGetConfigsResult(
        configBO.userId.get,
        cBOs,
        JsonConfigStatus(
          setStatus(configBO.status.get.addConfig),
          setStatus(configBO.status.get.getConfigs),
          setStatus(configBO.status.get.deleteConfig),
          setStatus(configBO.status.get.updateConfig),
          setStatus(configBO.status.get.common)
        )
      )
    )
  }

  private[wrapper] def toDeleteConfigBO(jsonDeleteConfigIn: JsonDeleteConfigIn): ConfigBO = {

    ConfigBO(
      configs = Some(List(Configuration(
        Some(jsonDeleteConfigIn.params.configId),
        Some(jsonDeleteConfigIn.params.configUrl))))
    )
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param  configBO : ConfigBO
    * @return ConfigBO
    */
  private[wrapper] def toJsonDeleteConfigOut(configBO: ConfigBO): JsonDeleteConfigOut = {
    Logger.info("ConfigBO" + configBO)
    JsonDeleteConfigOut(
      result = JsonDeleteConfigsResult(
        configBO.userId.get,
        JsonConfigStatus(
          setStatus(configBO.status.get.addConfig),
          setStatus(configBO.status.get.getConfigs),
          setStatus(configBO.status.get.deleteConfig),
          setStatus(configBO.status.get.updateConfig),
          setStatus(configBO.status.get.common)
        )
      )
    )
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param jsonUpdateConfigIn : JsonUpdateConfigIn
    * @return ConfigBO
    */
  private[wrapper] def toUpdateConfigBO(jsonUpdateConfigIn: JsonUpdateConfigIn): ConfigBO = {
    ConfigBO(
      configs = Some(List(Configuration(
        Some(jsonUpdateConfigIn.params.configId),
        Some(jsonUpdateConfigIn.params.configUrl)
      ))))
  }


  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configBO : ConfigBO
    * @return JsonUpdateConfigOut
    */
  private[wrapper] def toJsonUpdateConfigOut(configBO: ConfigBO): JsonUpdateConfigOut = {
    JsonUpdateConfigOut(
      result = JsonUpdateConfigResult(
        configBO.userId.get,
        JsonConfigStatus(
          setStatus(configBO.status.get.addConfig),
          setStatus(configBO.status.get.getConfigs),
          setStatus(configBO.status.get.deleteConfig),
          setStatus(configBO.status.get.updateConfig),
          setStatus(configBO.status.get.common)
        )
      )
    )
  }

  private def setStatus(status: Option[Status]): Option[JsonStatus] = status match {
    case Some(s) =>
      Some(JsonStatus(
        s.status,
        s.message
      ))
    case None => None
  }
}