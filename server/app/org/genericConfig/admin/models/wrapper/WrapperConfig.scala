package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.config.json._
import org.genericConfig.admin.shared.common.json.JsonStatus
import org.genericConfig.admin.shared.config.bo.ConfigBO
import org.genericConfig.admin.shared.common.json.JsonConfig

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.05.2018
 */
class WrapperConfig(configBO: ConfigBO) {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  private[wrapper] def toJsonAddConfigOut: JsonAddConfigOut = {
    JsonAddConfigOut(
        result = JsonAddConfigResult(
            configBO.userId,
            configBO.configs match {
              case List() => ""
              case _ => {
                configBO.configs.head.configId
              }
            },
            JsonConfigStatus(
                configBO.status.addConfig match {
                  case Some(addConfig) => 
                    Some(JsonStatus(
                      addConfig.status,
                      addConfig.message
                    ))
                  case None => None
                },
                configBO.status.getConfigs match {
                  case Some(getConfigs) => 
                    Some(JsonStatus(
                      getConfigs.status,
                      getConfigs.message
                    ))
                  case None => None
                },
                configBO.status.deleteConfig match {
                  case Some(deleteConfig) => 
                    Some(JsonStatus(
                      deleteConfig.status,
                      deleteConfig.message
                    ))
                  case None => None
                },
                configBO.status.updateConfig match {
                  case Some(updateConfig) => 
                    Some(JsonStatus(
                      updateConfig.status,
                      updateConfig.message
                    ))
                  case None => None
                },
                configBO.status.common match {
                  case Some(common) => 
                    Some(JsonStatus(
                      common.status,
                      common.message
                    ))
                  case None => None
                }
            )
        )
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  private[wrapper] def toJsonGetConfigsOut: JsonGetConfigsOut = {
    val cBOs: List[JsonConfig] = configBO.configs map { configBO => {
      JsonConfig(
          configBO.configId,
          configBO.configUrl
      )
    }}
    JsonGetConfigsOut(
        result = JsonGetConfigsResult(
            configBO.userId,
            cBOs,
            JsonConfigStatus(
                configBO.status.addConfig match {
                  case Some(status) => 
                    Some(JsonStatus(
                        status.status,
                        status.message
                    ))
                  case None => None
                },
                configBO.status.getConfigs match {
                  case Some(getConfigs) => 
                    Some(JsonStatus(
                      getConfigs.status,
                      getConfigs.message
                    ))
                  case None => None
                },
                configBO.status.deleteConfig match {
                  case Some(deleteConfig) => 
                    Some(JsonStatus(
                      deleteConfig.status,
                      deleteConfig.message
                    ))
                  case None => None
                },
                configBO.status.updateConfig match {
                  case Some(updateConfig) => 
                    Some(JsonStatus(
                      updateConfig.status,
                      updateConfig.message
                    ))
                  case None => None
                },
                configBO.status.common match {
                  case Some(status) => 
                    Some(JsonStatus(
                        status.status,
                        status.message
                    ))
                  case None => None
                }
            )
        )
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  private[wrapper] def toJsonDeleteConfigOut: JsonDeleteConfigOut = {
    JsonDeleteConfigOut(
        result = JsonDeleteConfigsResult(
            configBO.userId,
            JsonConfigStatus(
                configBO.status.addConfig match {
                  case Some(addConfig) => 
                    Some(JsonStatus(
                      addConfig.status,
                      addConfig.message
                    ))
                  case None => None
                },
                configBO.status.getConfigs match {
                  case Some(getConfigs) => 
                    Some(JsonStatus(
                      getConfigs.status,
                      getConfigs.message
                    ))
                  case None => None
                },
                configBO.status.deleteConfig match {
                  case Some(deleteConfig) => 
                    Some(JsonStatus(
                      deleteConfig.status,
                      deleteConfig.message
                    ))
                  case None => None
                },
                configBO.status.updateConfig match {
                  case Some(updateConfig) => 
                    Some(JsonStatus(
                      updateConfig.status,
                      updateConfig.message
                    ))
                  case None => None
                },
                configBO.status.common match {
                  case Some(common) => 
                    Some(JsonStatus(
                      common.status,
                      common.message
                    ))
                  case None => None
                }
            )
        )
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  private[wrapper] def toJsonUpdateConfigOut: JsonUpdateConfigOut = {
    JsonUpdateConfigOut(
        result = JsonUpdateConfigResult(
            configBO.userId,
            JsonConfigStatus(
                configBO.status.addConfig match {
                  case Some(addConfig) => 
                    Some(JsonStatus(
                      addConfig.status,
                      addConfig.message
                    ))
                  case None => None
                },
                configBO.status.getConfigs match {
                  case Some(getConfigs) => 
                    Some(JsonStatus(
                      getConfigs.status,
                      getConfigs.message
                    ))
                  case None => None
                },
                configBO.status.deleteConfig match {
                  case Some(deleteConfig) => 
                    Some(JsonStatus(
                      deleteConfig.status,
                      deleteConfig.message
                    ))
                  case None => None
                },
                configBO.status.updateConfig match {
                  case Some(updateConfig) => 
                    Some(JsonStatus(
                      updateConfig.status,
                      updateConfig.message
                    ))
                  case None => None
                },
                configBO.status.common match {
                  case Some(common) => 
                    Some(JsonStatus(
                      common.status,
                      common.message
                    ))
                  case None => None
                }
            )
        )
    )
  }
}