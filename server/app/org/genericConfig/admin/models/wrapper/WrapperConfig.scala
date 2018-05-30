package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.config.json._
import org.genericConfig.admin.shared.common.json.JsonStatus
import org.genericConfig.admin.shared.config.bo.ConfigBO
import org.genericConfig.admin.shared.common.json.JsonConfig
import org.genericConfig.admin.shared.config.bo.Configuration
import org.genericConfig.admin.shared.common.status.Status
import org.genericConfig.admin.models.logic.RidToHash
import play.api.LoggerLike
import play.api.Logger

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.05.2018
 */
class WrapperConfig {
  
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  
  private[wrapper] def toAddConfigBO(jsonConfigIn: JsonAddConfigIn): ConfigBO = {
    
//    val (id, hash) = setIdAndHash(jsonConfigIn.params.userId)
    Logger.info("userId " + jsonConfigIn.params.userId)
    val id = RidToHash.getId(jsonConfigIn.params.userId)
    Logger.info("id " + id)
    ConfigBO(
        userId = Some(id),
        configs = Some(List(Configuration(configUrl = Some(jsonConfigIn.params.configUrl))))
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
  private[wrapper] def toJsonAddConfigOut(configBO: ConfigBO): JsonAddConfigOut = {
    
    val userIdHash = RidToHash.getHash(configBO.userId.get)
    
    val (id, configIdHash) = RidToHash.setIdAndHash(configBO.configs.get.head.configId.get)
    
    JsonAddConfigOut(
        result = JsonAddConfigResult(
            Some(userIdHash),
            configBO.configs match {
              case Some(List()) => None
              case _ => {
                Some(configIdHash)
              }
            },
            JsonConfigStatus(
                setStatus(configBO.status.get.addConfig) ,
                setStatus(configBO.status.get.getConfigs),
                setStatus(configBO.status.get.deleteConfig),
                setStatus(configBO.status.get.updateConfig),
                setStatus(configBO.status.get.common)
            )
        )
    )
  }
  
  private[wrapper] def toGetConfigsBO(jsonGetConfigsIn: JsonGetConfigsIn): ConfigBO = {
    
    val uId = RidToHash.getId(jsonGetConfigsIn.params.userId)
    ConfigBO(
        userId = Some(uId)
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
  private[wrapper] def toJsonGetConfigsOut(configBO: ConfigBO): JsonGetConfigsOut = {
    
    val userIdHash = RidToHash.getHash(configBO.userId.get)
    
    val cBOs: List[JsonConfig] = configBO.configs.get map { config => {
      JsonConfig(
          RidToHash.getHash(config.configId.get),
          config.configUrl.get
      )
    }}
    JsonGetConfigsOut(
        result = JsonGetConfigsResult(
            userIdHash,
            cBOs,
            JsonConfigStatus(
                setStatus(configBO.status.get.addConfig) ,
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
            Some(RidToHash.getId(jsonDeleteConfigIn.params.configId)), 
            Some(jsonDeleteConfigIn.params.configUrl))))
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
  private[wrapper] def toJsonDeleteConfigOut(configBO: ConfigBO): JsonDeleteConfigOut = {
    JsonDeleteConfigOut(
        result = JsonDeleteConfigsResult(
            RidToHash.getHash(configBO.userId.get),
            JsonConfigStatus(
                setStatus(configBO.status.get.addConfig) ,
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
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  private[wrapper] def toUpdateConfigBO(jsonUpdateConfigIn: JsonUpdateConfigIn): ConfigBO = {
    ConfigBO(
        configs = Some(List(Configuration(
            Some(RidToHash.getId(jsonUpdateConfigIn.params.configId)),
            Some(jsonUpdateConfigIn.params.configUrl)
        ))))
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
  private[wrapper] def toJsonUpdateConfigOut(configBO: ConfigBO): JsonUpdateConfigOut = {
    JsonUpdateConfigOut(
        result = JsonUpdateConfigResult(
            RidToHash.getHash(configBO.userId.get),
            JsonConfigStatus(
                setStatus(configBO.status.get.addConfig) ,
                setStatus(configBO.status.get.getConfigs),
                setStatus(configBO.status.get.deleteConfig),
                setStatus(configBO.status.get.updateConfig),
                setStatus(configBO.status.get.common)
            )
        )
    )
  }
  
  private def setStatus(status: Option[Status]): Option[JsonStatus] = {
    status match {
      case Some(status) => 
        Some(JsonStatus(
            status.status,
            status.message
        ))
      case None => None
    }
  }
}