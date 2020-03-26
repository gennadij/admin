package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.common.{AddUserIdHashNotExist, Error}
import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.models.persistence.orientdb.GraphConfig
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.config.bo.ConfigBO
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigResultDTO, UserConfigDTO}
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
    new Config(configDTO).addConfig
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configBO : ConfigBO
    * @return ConfigBO
    */
  def getConfigs(configBO: ConfigBO): ConfigBO = {
    ??? //new Config(Some(configBO)).getConfigs
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configBO : ConfigBO
    * @return ConfigBO
    */
  def deleteConfig(configBO: ConfigBO): ConfigBO = {
    ??? //new Config(Some(configBO)).deleteConfig
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configTreeBO: ConfigTreeBOs
    * @return ConfigTreeBO
    */
  def getConfigTree(configTreeBO: ConfigTreeBO): ConfigTreeBO = {
    ??? //new Config().getConfigTree(configTreeBO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configBO : ConfigBO
    * @return ConfigBO
    */
  def updateConfig(configBO: ConfigBO): ConfigBO = {
   ??? // new Config(Some(configBO)).updateConfig
  }

}

class Config(configDTO: ConfigDTO) {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @return ConfigBO
    */
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
                  GraphConfig.deleteConfig(vConfig.get.getIdentity.toString(), configUrl)

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
          errors = Some(List(AddUserIdHashNotExist()))
        )
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @return ConfigBO
    */
  private def getConfigs: ConfigBO = {
  ???
//    RidToHash.getRId(this.configBO.get.userId.get) match {
//      case Some(rId) =>
//        val configBOOut = Persistence.getConfigs(rId)
//
//        val userIdHash: Option[String] = RidToHash.getHash(configBOOut.userId.get)
//
//
//        val configurations: List[Configuration] = configBOOut.configs match {
//          case Some(configs) =>
//            configs.map(config => {
//              val configIdHash: String = RidToHash.getHash(config.configId.get) match {
//                case Some(idHash) => idHash
//                case None => RidToHash.setIdAndHash(config.configId.get)._2
//              }
//              Configuration(
//                Some(configIdHash),
//                config.configUrl
//              )
//            })
//          case None => List()
//        }
//
//        configBOOut.copy(userId = userIdHash, configs = Some(configurations))
//
//      case None => ConfigBO(
//        status = Some(StatusConfig(getConfigs = Some(GetConfigsIdHashNotExist())))
//      )
//    }

  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @return ConfigBO
    */
  private def deleteConfig: ConfigBO = {
    ???
//    RidToHash.getRId(configBO.get.configs.get.head.configId.get) match {
//      case Some(rId) =>
//        val configBOOut: ConfigBO = Persistence.deleteConfig(
//          rId,
//          configBO.get.configs.get.head.configUrl.get
//        )
//
//        val userIdHash: Option[String] = RidToHash.getHash(configBOOut.userId.get) match {
//          case Some(idHash) => Some(idHash)
//          case None => Some("")
//        }
//
//        configBOOut.copy(userId = userIdHash)
//      case None => ConfigBO(
//        userId = Some(""),
//        status = Some(StatusConfig(deleteConfig = Some(DeleteConfigIdHashNotExist()), common = Some(ODBRecordIdDefect())))
//      )
//    }
  }

  private def updateConfig: ConfigBO = {
    ???
//    RidToHash.getRId(configBO.get.configs.get.head.configId.get) match {
//      case Some(rId) =>
//        val configBOOut: ConfigBO = Persistence.updateConfig(
//          rId,
//          configBO.get.configs.get.head.configUrl.get
//        )
//        val userIdHash: Option[String] = RidToHash.getHash(configBOOut.userId.get)
//
//
//        val configuration: Option[List[Configuration]] = configBOOut.configs match {
//          case Some(configs) => Some(List(Configuration(
//            RidToHash.getHash(configs.head.configId.get),
//            configs.head.configUrl
//          )))
//          case None => None
//        }
//        configBOOut.copy(userId = userIdHash, configs = configuration)
//      case None => ConfigBO(
//        status = Some(StatusConfig(updateConfig = Some(UpdateConfigIdHashNotExist())))
//      )
//    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @return ConfigBO
    */
  private def getConfigTree(configTreeBO: ConfigTreeBO): ConfigTreeBO = {
    
    val configTreeBOOut: ConfigTreeBO = 
      Persistence.getConfigTree(configTreeBO.copy(configId = RidToHash.getRId(configTreeBO.configId.get)))

    val debug = configTreeBOOut.copy(
        userId = RidToHash.getHash(configTreeBOOut.userId.get), 
        configId = RidToHash.getHash(configTreeBOOut.configId.get),
        configTree = setHashForConfigTree(configTreeBOOut.configTree)
    )
//    Logger.info("configTreeBOOut" + debug)
    debug
  }

  private def setHashForConfigTree(configTree: Option[StepForConfigTreeBO]): Option[StepForConfigTreeBO] = {
    configTree match {
      case Some(cT) => Some(cT.copy(
            stepId = RidToHash.setIdAndHash(cT.stepId)._2,
            nextSteps = setHashForNextSteps(cT.nextSteps),
            components = setHashForComponents(cT.components)
          ))
      case None => None
    }
  }

  private def setHashForComponents(components: Set[ComponentForConfigTreeBO]): Set[ComponentForConfigTreeBO] = {
    components map(c => {
      c.copy(componentId = RidToHash.setIdAndHash(c.componentId)._2, nextStepId = getHashForNextStepId(c.nextStepId))
    })
  }

  private def setHashForNextSteps(nextSteps: Set[StepForConfigTreeBO]): Set[StepForConfigTreeBO] = {
    nextSteps map { nSs =>
      nSs.copy(
        stepId = RidToHash.setIdAndHash(nSs.stepId)._2,
        nextSteps = setHashForNextSteps(nSs.nextSteps),
        components = setHashForComponents(nSs.components))
    }
  }

  private def getHashForNextStepId(nextStepId: Option[String]): Option[String] = {
    nextStepId match {
      case Some(nsId) => Some(RidToHash.setIdAndHash(nsId)._2)
      case None => None
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
}

