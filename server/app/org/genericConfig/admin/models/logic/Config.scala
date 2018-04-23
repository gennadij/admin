package org.genericConfig.admin.models.logic

import org.genericConfig.admin.shared.bo.ConfigBO
import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.status.Success
import org.genericConfig.admin.shared.status.Status
import org.genericConfig.admin.shared.status.config.StatusConfig
import org.genericConfig.admin.shared.status.config.ConfigAdded
import org.genericConfig.admin.shared.status.config.ConfigAlredyExist
import org.genericConfig.admin.shared.status.ODBRecordDuplicated
import org.genericConfig.admin.shared.status.ODBClassCastError
import org.genericConfig.admin.shared.status.ODBWriteError
import org.genericConfig.admin.shared.status.Error

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
  def getConfigs(userId: String): List[ConfigBO] = {
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
    configBO.status.common match {
      case Some(Success()) => {
        val statusConfigAppend : Status = Persistence.appendConfigTo(userId, configBO.configId)
        statusConfigAppend match {
          case Success() => {
            configBO.copy(status = StatusConfig(Some(ConfigAdded()), Some(Success())))
            
            ConfigBO(
                userId,
                configBO.configId,
                configBO.configUrl,
                StatusConfig(
                    Some(ConfigAdded()),
                    Some(Success())
                )
            )
          }
          case ODBRecordDuplicated() => {
            ConfigBO(
                userId,
                "",
                configUrl,
                StatusConfig(
                    Some(ConfigAlredyExist()),
                    Some(ODBRecordDuplicated())
                )
            )
          }
          case ODBClassCastError() => {
            ConfigBO(
                "", "", "",
                StatusConfig(
                    None,
                    Some(ODBClassCastError())
                )
            )
          }
          case ODBWriteError() => {
            ConfigBO(
                "", "", "",
                StatusConfig(
                    None,
                    Some(ODBWriteError())
                )
            )
          }
        }
      }
      case Some(ODBRecordDuplicated()) => {
        ConfigBO(
            userId,
            "",
            configUrl,
            StatusConfig(
                Some(ConfigAlredyExist()),
                Some(ODBRecordDuplicated())
            )
        )
      }
      case Some(ODBClassCastError()) => {
        ConfigBO(
            "", "", "",
            StatusConfig(
                None,
                Some(ODBClassCastError())
            )
        )
      }
      case Some(ODBWriteError()) => {
        ConfigBO(
            "", "", "",
            StatusConfig(
                None,
                Some(ODBWriteError())
            )
        )
      }
      case None => {
        ConfigBO(
            "", "", "",
            StatusConfig(
                None,
                Some(Error())
            )
        )
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
  private def getConfigs: List[ConfigBO] = {
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