package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.common.status._
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.config.bo._
import org.genericConfig.admin.shared.configTree.bo.ConfigTreeBO

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
   * @param
   * 
   * @return
   */
  def createConfig(userId: String, configUrl: String): ConfigBO = {
    new Config(userId).createConfig(configUrl)
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
  def getConfigs(userId: String): ConfigBO = {
    new Config(userId).getConfigs
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
  def deleteConfig(userId: String, configId: String) = {
    new Config(userId).deleteConfig(configId)
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
  def getConfigTree(configId: String) = {
    new Config("").getConfigTree(configId)
  }
  
}

class Config(userId: String) {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param
   * 
   * @return
   */
  private def createConfig(configUrl: String): ConfigBO = {
    
    val configBO: ConfigBO = Persistence.createConfig(userId, configUrl)
    configBO.status.addConfig match {
      case Some(AddConfigAdded()) => {
        val statusConfigAppend : Status = Persistence.appendConfigTo(userId, configBO.configs.head.configId)
        statusConfigAppend match {
          case Success() => {
            configBO
          }
          case _ => {
            ConfigBO(
                status = StatusConfig(Some(AddConfigError()),
                    None,
                    None,
                    None,
                    Some(statusConfigAppend)
                )
            )
            //TODO erzeugte Config soll geloescht werden
          }
        }
      }
      case Some(AddConfigAlreadyExist()) => {
        configBO
      }
      case Some(AddConfigError()) => {
        configBO
      }
    }
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
  private def getConfigs: ConfigBO = {
    Persistence.getConfigs(userId)
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
  private def deleteConfig(configId: String): ConfigBO = {
    ???
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
  def getConfigTree(configId: String): ConfigTreeBO = {
    Persistence.getConfigTree(configId)
  }
}