package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.status.Success
import org.genericConfig.admin.shared.status.Status
import org.genericConfig.admin.shared.status.config.StatusConfig
import org.genericConfig.admin.shared.status.ODBRecordDuplicated
import org.genericConfig.admin.shared.status.ODBClassCastError
import org.genericConfig.admin.shared.status.ODBWriteError
import org.genericConfig.admin.shared.status.Error
import org.genericConfig.admin.shared.bo.config.ConfigBO
import org.genericConfig.admin.shared.status.config.AddConfigAdded
import org.genericConfig.admin.shared.status.config.AddConfigError
import org.genericConfig.admin.shared.status.config.AddConfigAlreadyExist

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
  
}