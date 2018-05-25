package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.common.status._
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.config.bo._
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
   * @param
   * 
   * @return
   */
  def addConfig(userId: String, configUrl: String): ConfigBO = {
    new Config(userId).addConfig(configUrl)
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
  def deleteConfig(configId: String, configUrl: String): ConfigBO = {
    new Config("").deleteConfig(configId, configUrl)
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
    new Config("").getConfigTree(configId)
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
  def updateConfig(configId: String, configUrl: String): ConfigBO = {
    new Config("").updateConfig(configId, configUrl)
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
  private def addConfig(configUrl: String): ConfigBO = {
    
    val configBO: ConfigBO = Persistence.addConfig(userId, configUrl)
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
    
    RidToHash.initUser
    
//    val userIdRaw = RidToHash.getId(userId)
    
    Persistence.getConfigs(userId)
    
//    configBO.copy(userId = RidToHash.getHash(configBO.userId))
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
  private def deleteConfig(configId: String, configUrl: String): ConfigBO = {
    Persistence.deleteConfig(configId, configUrl)
  }
  
  private def updateConfig(configId: String, configUrl: String): ConfigBO = {
    Persistence.updateConfig(configId, configUrl)
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