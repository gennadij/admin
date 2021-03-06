package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.common.{ConfigIdHashNotExist, ConfigNothingToUpdate, Error, UserIdHashNotExist}
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, GraphConfig, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigResultDTO, UserConfigDTO}
import org.genericConfig.admin.shared.configGraph.ConfigGraphDTO
import org.genericConfig.admin.shared.configTree.bo.{ComponentForConfigTreeBO, ConfigTreeBO, StepForConfigTreeBO}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 20.04.2018
  */

object Config {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configDTO : ConfigDTO
    * @return ConfigDTO
    */
  def addConfig(configDTO: ConfigDTO): ConfigDTO = {
    new Config(configDTO).addConfig()
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param configDTO : ConfigDTO
   * @return ConfigDTO
   */
  def deleteConfig(configDTO: ConfigDTO): ConfigDTO = {
    new Config(configDTO).deleteConfig
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configDTO : ConfigDTO
    * @return ConfigDTO
    */
  def getConfigs(configDTO: ConfigDTO): ConfigDTO = {
    new Config(configDTO).getConfigs
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configDTO : ConfigDTO
    * @return ConfigDTO
    */
  def updateConfig(configDTO: ConfigDTO): ConfigDTO = {
   new Config(configDTO).updateConfig()
  }

}

class Config(configDTO: ConfigDTO) {

  private def addConfig(): ConfigDTO = {

    val configUrl : String = configDTO.params.get.configUrl.get
    val userIdHash : String = configDTO.params.get.userId.get
    val configurationCourse : String = configDTO.params.get.configurationCourse.get
    RidToHash.getRId(userIdHash) match {
      case Some(userId) =>
        val (vConfig, errorAddConfig): (Option[OrientVertex], Option[Error]) =
          GraphConfig.addConfig(configUrl, configurationCourse)
        errorAddConfig match {
          case None =>

            val errorAppendConfig : Option[Error] = GraphConfig.appendConfigTo(userId, vConfig.get.getIdentity.toString())

            errorAppendConfig match {
              case None =>
                createConfigDTO(action = Actions.ADD_CONFIG, userId = Some(userIdHash),
                  configs = Some(List(UserConfigDTO(
                    configId = Some(RidToHash.setIdAndHash(vConfig.get.getIdentity.toString())_2),
                    configUrl = Some(configUrl)
                  ))),
                  errors = None
                )
              case _ =>

                val errorDeleteConfig : Option[Error] =
                  GraphCommon.deleteVertex(vConfig.get.getIdentity.toString(), PropertyKeys.VERTEX_CONFIG)

                errorDeleteConfig match {
                  case None =>
                    createConfigDTO(action = Actions.ADD_CONFIG, userId = Some(userIdHash),
                      configs = None, errors = Some(List(errorAppendConfig.get))
                    )
                  case _ =>
                    createConfigDTO(action = Actions.ADD_CONFIG, userId = Some(userIdHash),
                      configs = None, errors = Some(List(errorAppendConfig.get, errorDeleteConfig.get))
                    )
                }
            }
          case _ =>
            createConfigDTO(action = Actions.ADD_CONFIG, userId = Some(userIdHash),
              configs = None, errors = Some(List(errorAddConfig.get))
            )
        }
      case None =>
        createConfigDTO(action = Actions.ADD_CONFIG, userId = None, configs = None,
          errors = Some(List(UserIdHashNotExist()))
        )
    }
  }


  private def getConfigs: ConfigDTO = {
    RidToHash.getRId(this.configDTO.params.get.userId.get) match {
      case Some(userRid) =>
        val (vConfigs : Option[List[OrientVertex]], error: Option[Error]) = GraphConfig.getConfigs(userRid)

        error match {
          case None =>
            val configs = Some(vConfigs.get.map(vConfig => {
              UserConfigDTO(
                configId = Some(RidToHash.setIdAndHash(vConfig.getIdentity.toString())._2),
                configUrl = Some(vConfig.getProperty(PropertyKeys.CONFIG_URL))
              )
            }))
            createConfigDTO(action = Actions.GET_CONFIGS, userId = this.configDTO.params.get.userId, configs, None)
          case _ =>
            createConfigDTO(action = Actions.GET_CONFIGS, None, None, Some(List(error.get)))
        }
      case None => createConfigDTO(action = Actions.GET_CONFIGS, None, None, Some(List(UserIdHashNotExist())))
    }
  }

  private def deleteConfig(): ConfigDTO = {
    RidToHash.getRId(configDTO.params.get.configId.get) match {
      case Some(configId) =>
        val errorDeleteConfig : Option[Error] = GraphCommon.deleteVertex(configId, PropertyKeys.VERTEX_CONFIG)

        errorDeleteConfig match {
          case None => createConfigDTO(Actions.DELETE_CONFIG, None, None, None)
          case _ => createConfigDTO(Actions.DELETE_CONFIG, None, None, Some(List(errorDeleteConfig.get)))
        }
      case None => createConfigDTO(Actions.DELETE_CONFIG, None, None, Some(List(ConfigIdHashNotExist())))
    }
  }

  private def updateConfig(): ConfigDTO = {
    RidToHash.getRId(configDTO.params.get.configId.get) match {
      case Some(configRid) =>

        val (vUpdatedConfig, error): (Option[OrientVertex],  Option[Error]) = GraphConfig.updateConfig(
            configRid,
            configDTO.params.get.update.get.configUrl,
            configDTO.params.get.update.get.configurationCourse
        )
        error match {
          case None =>
            val configs : Option[List[UserConfigDTO]] = Some(List(UserConfigDTO(
              configId = Some(RidToHash.setIdAndHash(vUpdatedConfig.get.getIdentity.toString())._2),
              configUrl = Some(vUpdatedConfig.get.getProperty(PropertyKeys.CONFIG_URL)),
              configurationCourse = Some(vUpdatedConfig.get.getProperty(PropertyKeys.CONFIGURATION_COURSE))
            )))
            createConfigDTO(action = Actions.UPDATE_CONFIG, None, configs, None)
          case Some(ConfigNothingToUpdate()) =>
            createConfigDTO(action = Actions.UPDATE_CONFIG, None, None, Some(List(ConfigNothingToUpdate())))
          case _ => createConfigDTO(action = Actions.UPDATE_CONFIG, None, None, Some(List(error.get)))
        }
      case None => createConfigDTO(action = Actions.UPDATE_CONFIG, None, None, Some(List(ConfigIdHashNotExist())))
    }
  }

  private def createConfigDTO (
                                action : String,
                                userId : Option[String],
                                configs : Option[List[UserConfigDTO]],
                                errors : Option[List[Error]]
                              ) : ConfigDTO = {

    val e =  errors match {
      case None => None
      case Some(e) => Some(
        e.map(error => {
          ErrorDTO(
            name = error.name,
            message = error.message,
            code = error.code
          )
        })
      )
    }

    ConfigDTO(
      action = action,
      params = None,
      result = Some(ConfigResultDTO(
        userId = userId,
        configs = configs,
        errors = e
      ))
    )

  }

  private def getHashForNextStepId(nextStepId: Option[String]): Option[String] = {
    nextStepId match {
      case Some(nsId) => Some(RidToHash.setIdAndHash(nsId)._2)
      case None => None
    }
  }
}

