package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.common.status._
import org.genericConfig.admin.shared.config.bo.{ConfigBO, _}
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.configTree.bo.ConfigTreeBO
import org.genericConfig.admin.models.wrapper.RidToHash

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 20.04.2018
  */

object Config {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configBO : ConfigBO
    * @return ConfigBO
    */
  def addConfig(configBO: ConfigBO): ConfigBO = {
    new Config(configBO).addConfig()
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configBO : ConfigBO
    * @return ConfigBO
    */
  def getConfigs(configBO: ConfigBO): ConfigBO = {
    new Config(configBO).getConfigs
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configBO : ConfigBO
    * @return ConfigBO
    */
  def deleteConfig(configBO: ConfigBO): ConfigBO = {
    new Config(configBO).deleteConfig()
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String
    * @return ConfigTreeBO
    */
  def getConfigTree(configId: String): ConfigTreeBO = {
    new Config(null).getConfigTree(configId)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configBO : ConfigBO
    * @return ConfigBO
    */
  def updateConfig(configBO: ConfigBO): ConfigBO = {
    new Config(configBO).updateConfig()
  }

}

class Config(configBO: ConfigBO) {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @return ConfigBO
    */
  private def addConfig(): ConfigBO = {

    val configUrl = configBO.configs.get.head.configUrl.get
    val userIdHash = configBO.userId.get

    RidToHash.getId(userIdHash) match {
      case Some(id) =>
        val cBO: ConfigBO = Persistence.addConfig(id, configUrl)
        cBO.status.get.addConfig.get match {
          case AddConfigSuccess() =>
            val statusConfigAppend: Status =
              Persistence.appendConfigTo(id, cBO.configs.get.head.configId.get)
            statusConfigAppend match {
              case Success() =>
                val (_, configIdHash) = RidToHash.setIdAndHash(cBO.configs.get.head.configId.get)

                val configuration = Configuration(
                  Some(configIdHash),
                  cBO.configs.get.head.configUrl
                )

                cBO.copy(configs = Some(List(configuration)))
              case _ =>

                val configBODelete: ConfigBO = Persistence.deleteConfig(cBO.configs.get.head.configId.get, cBO.configs.get.head.configUrl.get)

                ConfigBO(
                  status = Some(StatusConfig(
                    addConfig = Some(AddConfigError()),
                    deleteConfig = configBODelete.status.get.deleteConfig,
                    common = Some(statusConfigAppend)
                  )
                  ))
            }
          case AddConfigAlreadyExist() =>
            cBO
          case AddConfigError() =>
            cBO
        }
      case None => ConfigBO(status = Some(StatusConfig(addConfig = Some(AddConfigidHashNotExist()))))
    }
    //    val userId = configBO.userId.get


  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @return ConfigBO
    */
  private def getConfigs: ConfigBO = {

    RidToHash.getId(this.configBO.userId.get) match {
      case Some(rId) =>
        val configBOOut = Persistence.getConfigs(rId)

        val userIdHash: Option[String] = RidToHash.getHash(configBOOut.userId.get)


        val configurations: List[Configuration] = configBOOut.configs match {
          case Some(configs) =>
            configs.map(config => {
              val configIdHash: String = RidToHash.getHash(config.configId.get) match {
                case Some(idHash) => idHash
                case None => RidToHash.setIdAndHash(config.configId.get)._2
              }
              Configuration(
                Some(configIdHash),
                config.configUrl
              )
            })
          case None => List()
        }

        configBOOut.copy(userId = userIdHash, configs = Some(configurations))

      case None => ConfigBO(
        status = Some(StatusConfig(getConfigs = Some(GetConfigsIdHashNotExist())))
      )
    }

  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @return ConfigBO
    */
  private def deleteConfig(): ConfigBO = {
    RidToHash.getId(configBO.configs.get.head.configId.get) match {
      case Some(rId) =>
        val configBOOut: ConfigBO = Persistence.deleteConfig(
          rId,
          configBO.configs.get.head.configUrl.get
        )

        val userIdHash: Option[String] = RidToHash.getHash(configBOOut.userId.get) match {
          case Some(idHash) => Some(idHash)
          case None => Some("")
        }

        configBOOut.copy(userId = userIdHash)
      case None => ConfigBO(
        userId = Some(""),
        status = Some(StatusConfig(deleteConfig = Some(DeleteConfigIdHashNotExist()), common = Some(ODBRecordIdDefect())))
      )
    }
  }

  private def updateConfig(): ConfigBO = {
    RidToHash.getId(configBO.configs.get.head.configId.get) match {
      case Some(rId) =>
        val configBOOut: ConfigBO = Persistence.updateConfig(
          rId,
          configBO.configs.get.head.configUrl.get
        )
        val userIdHash: Option[String] = RidToHash.getHash(configBOOut.userId.get)


        val configuration: Option[List[Configuration]] = configBOOut.configs match {
          case Some(configs) => Some(List(Configuration(
            RidToHash.getHash(configs.head.configId.get),
            configs.head.configUrl
          )))
          case None => None
        }
        configBOOut.copy(userId = userIdHash, configs = configuration)
      case None => ConfigBO(
        status = Some(StatusConfig(updateConfig = Some(UpdateConfigIdHashNotExist())))
      )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @return ConfigBO
    */
  def getConfigTree(configId: String): ConfigTreeBO = {
    Persistence.getConfigTree(configId)
  }
}