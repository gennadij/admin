package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.common.status._
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.config.bo.{ConfigBO, _}
import org.genericConfig.admin.shared.configTree.bo.ConfigTreeBO
import org.genericConfig.admin.models.persistence.orientdb.Graph
import play.api.Logger

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 20.04.2018
 */

object Config{
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configBO: ConfigBO
   * 
   * @return ConfigBO
   */
  def addConfig(configBO: ConfigBO): ConfigBO = {
    new Config(configBO).addConfig
  }
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configBO: ConfigBO
   * 
   * @return ConfigBO
   */
  def getConfigs(configBO: ConfigBO): ConfigBO = {
    new Config(configBO).getConfigs
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configBO: ConfigBO
   * 
   * @return ConfigBO
   */
  def deleteConfig(configBO: ConfigBO): ConfigBO = {
    new Config(configBO).deleteConfig
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configId: String
   * 
   * @return ConfigTreeBO
   */
  def getConfigTree(configId: String): ConfigTreeBO = {
    new Config(null).getConfigTree(configId)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configBO: ConfigBO
   * 
   * @return ConfigBO
   */
  def updateConfig(configBO: ConfigBO): ConfigBO = {
    new Config(configBO).updateConfig
  }
  
}

class Config(configBO: ConfigBO) {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @return ConfigBO
   */
  private def addConfig: ConfigBO = {
    
    val configUrl = configBO.configs.get.head.configUrl.get
    val userId = configBO.userId.get
    
    val cBO: ConfigBO = Persistence.addConfig(userId, configUrl)
    cBO.status.get.addConfig.get match {
      case AddConfigAdded() => {
        Logger.info(configBO.toString())
        
        val statusConfigAppend : Status = 
          Persistence.appendConfigTo(userId, cBO.configs.get.head.configId.get)
        statusConfigAppend match {
          case Success() => {
            cBO
          }
          case _ => {
            ConfigBO(
                status = Some(StatusConfig(Some(AddConfigError()),
                    None,
                    None,
                    None,
                    Some(statusConfigAppend)
                )
            ))
            //TODO erzeugte Config soll geloescht werden
          }
        }
      }
      case AddConfigAlreadyExist() => {
        cBO
      }
      case AddConfigError() => {
        cBO
      }
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @return ConfigBO
   */
  private def getConfigs: ConfigBO = {
    Persistence.getConfigs(configBO.userId.get)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @return ConfigBO
   */
  private def deleteConfig: ConfigBO = {
    Persistence.deleteConfig(
        configBO.configs.get.head.configId.get, 
        configBO.configs.get.head.configUrl.get)
  }
  
  private def updateConfig: ConfigBO = {
    Persistence.updateConfig(
        configBO.configs.get.head.configId.get, 
        configBO.configs.get.head.configUrl.get)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @return ConfigBO
   */
  def getConfigTree(configId: String): ConfigTreeBO = {
    Persistence.getConfigTree(configId)
  }
}