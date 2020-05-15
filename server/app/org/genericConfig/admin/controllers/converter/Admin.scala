package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.models.json.dependency.{JsonDependencyIn, JsonDependencyOut}
import org.genericConfig.admin.models.logic._
import org.genericConfig.admin.models.wrapper.Wrapper
import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentOut}
import org.genericConfig.admin.shared.configTree.json.{JsonConfigTreeIn, JsonConfigTreeOut}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann on 13.11.2016
  */


class Admin extends Wrapper {

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param user : UserDTO
//    * @return UserDTO
//    */
//  def addUser(user: UserDTO): UserDTO = {
//    User.addUser(user)
//  }

//  /**
//   * @author Gennadi Heimann
//   * @version 0.1.6
//   * @param userParams : UserDTO
//   * @return UserDTO
//   */
//  def deleteUser(userParams: UserDTO): UserDTO = {
//    User.deleteUser(userParams)
//  }
//
//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param userParams : JsonUserIn
//    * @return JsonUserOut
//    */
//  def getUser(userParams: UserDTO): UserDTO = {
//    User.getUser(userParams)
//  }
//
//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param userParams : JsonUserIn
//    * @return JsonUserOut
//    */
//  def updateUser(userParams: UserDTO): UserDTO = {
//    User.updateUser(userParams)
//  }
//
//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param configDTO : ConfigDTO
//    * @return ConfigDTO
//    */
//  def addConfig(configDTO: ConfigDTO): ConfigDTO = {
//      Config.addConfig(configDTO)
//  }
//
//  /**
//   * @author Gennadi Heimann
//   * @version 0.1.6
//   * @param configDTO : ConfigDTO
//   * @return ConfigDTO
//   */
//  def deleteConfig(configDTO: ConfigDTO): ConfigDTO = {
//      Config.deleteConfig(configDTO)
//  }
//
//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param configDTO : ConfigDTO
//    * @return ConfigDTO
//    */
//  def getConfigs(configDTO: ConfigDTO): ConfigDTO = {
//    Config.getConfigs(configDTO)
//  }
//
//
//
//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param jsonUpdateConfigIn : JsonUpdateConfigIn
//    * @return JsonUpdateConfigOut
//    */
//  def updateConfig(jsonUpdateConfigIn: JsonUpdateConfigIn): JsonUpdateConfigOut = {
//    toJsonUpdateConfigOut(
//      Config.updateConfig(toUpdateConfigBO(jsonUpdateConfigIn))
//    )
//  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param jsonConfigTreeIn : JsonConfigTreeIn
    * @return JsonConfigTreeOut
    */
  def getConfigTree(jsonConfigTreeIn: JsonConfigTreeIn): JsonConfigTreeOut = {

    toJsonConfigTreeOut(Config.getConfigTree(toConfigTreeBO(jsonConfigTreeIn)))
  }


//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param jsonFirstStepIn : JsonStepIn
//    * @return JsonStepOut
//    */
//  def addStep(jsonFirstStepIn: JsonStepIn): JsonStepOut = {
//    toJsonStepOut(Step.addStep(toStepBO(jsonFirstStepIn)))
//  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param jsonFirstStepIn : JsonStepIn
//    * @return JsonStepOut
//    */
//  def deleteFirstStep(jsonFirstStepIn: JsonStepIn): JsonStepOut = {
//    toJsonStepOut(Step.deleteFirstStep(toStepBO(jsonFirstStepIn)))
//  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param jsonStepIn : JsonStepIn
//    * @return JsonStepOut
//    */
//  def updateStep(jsonStepIn: JsonStepIn): JsonStepOut = {
//    toJsonStepOut(Step.updateStep(toStepBO(jsonStepIn)))
//  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param jsonStepIn: JsonstepIn
//    * @return JsonStepOut
//    */
//  def connectComponentToStep(jsonStepIn: JsonStepIn): JsonStepOut = {
//    toJsonStepOut(Step.connectComponentToStep(toStepBO(jsonStepIn = jsonStepIn)))
//  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param jsonComponentIn : JsonComponentIn
    * @return JsonComponentOut
    */
  def addComponent(jsonComponentIn: JsonComponentIn): JsonComponentOut = {
    toJsonComponentOut(Component.addComponent(toComponentBO(jsonComponentIn)))
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param jsonComponentIn : JsonComponentIn
    * @return JsonComponentOut
    */
  def deleteComponent(jsonComponentIn: JsonComponentIn): JsonComponentOut = {
    toJsonComponentOut(Component.deleteComponent(toComponentBO(jsonComponentIn)))
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param jsonComponentIn : JsonComponentIn
    * @return JsonComponentOut
    */
  def updateComponent(jsonComponentIn: JsonComponentIn): JsonComponentOut = {
    toJsonComponentOut(Component.updateComponent(toComponentBO(jsonComponentIn)))
  }

  //  /**
  //   * @author Gennadi Heimann
  //   *
  //   * @version 0.1.2
  //   *
  //   * @param dependencyCS
  //   *
  //   * @return ConfigTreeSC
  //   */
  def createDependency(jsonDependencyIn: JsonDependencyIn): JsonDependencyOut = {
    //    toJsonDependencyOut(Persistence.createDependency(toDependencyIn(jsonDependencyIn)))
    ???
  }

  //  /**
  //   * @author Gennadi Heimann
  //   *
  //   * @version 0.1.5
  //   *
  //   * @param VisualProposalForAdditionalStepsInOneLevelCS
  //   *
  //   * @return StepSC
  //   */

//  def visualProposalForAdditionalStepsInOneLevel(
//                                                  jsonVisualProposalForAdditionalStepsInOneLevelIn: JsonVisualProposalForAdditionalStepsInOneLevelIn): JsonStepOut = {

    //    val visualProposalForAdditionalStepsInOneLevel: VisualProposalForAdditionalStepsInOneLevelIn =
    //      toVisualProposalForAdditionalStepsInOneLevelIn(jsonVisualProposalForAdditionalStepsInOneLevelIn)

    //hole Step aus der Temp
    //    val stepCS: Option[StepIn] = TempConfigurations.getAndRemoveAdditionalStepInLevelCS

    //    val dependencies: Set[JsonDependencyForAdditionalStepsInOneLevel] = Persistence.createDependenciesForAdditionalStepInLevelCS(
    //        stepCS, visualProposalForAdditionalStepsInOneLevel)

    //    val tempStep = Persistence.createAditionalStepInLevelCS(stepCS.get)

    //    JsonStepOut(
    //        result = JsonStepResult(
    //            tempStep.stepId,
    //            StatusSuccessfulAdditionalStepInLevelCSCreated.status,
    //            StatusSuccessfulAdditionalStepInLevelCSCreated.message,
    //            Set.empty,
    //            dependencies
    //        )
    //    )
    ???
//  }
}