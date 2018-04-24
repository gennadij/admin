package org.genericConfig.admin.controllers.admin

import org.genericConfig.admin.models.persistence.Persistence
import play.api.libs.json.Writes
import play.api.libs.json.Json
import org.genericConfig.admin.models.tempConfig.TempConfigurations
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import org.genericConfig.admin.models.persistence.db.orientdb.StepVertex
import play.api.LoggerLike
import play.api.Logger
import org.genericConfig.admin.shared.json.login.JsonLoginOut
import org.genericConfig.admin.models.json.login.JsonLoginIn
import org.genericConfig.admin.models.json.step.JsonFirstStepIn
import org.genericConfig.admin.models.json.component.JsonComponentOut
import org.genericConfig.admin.models.json.component.JsonComponentIn
import org.genericConfig.admin.models.json.step.JsonStepIn
import org.genericConfig.admin.models.json.step.JsonStepOut
import org.genericConfig.admin.models.json.connectionComponentToStep.JsonConnectionComponentToStepIn
import org.genericConfig.admin.models.json.connectionComponentToStep.JsonConnectionComponentToStepOut
import org.genericConfig.admin.models.json.dependency.JsonDependencyIn
import org.genericConfig.admin.models.json.dependency.JsonDependencyOut
import org.genericConfig.admin.models.json.step.JsonVisualProposalForAdditionalStepsInOneLevelIn
import org.genericConfig.admin.models.json.step.JsonDependencyForAdditionalStepsInOneLevel
import org.genericConfig.admin.models.json.step.JsonStepResult
import org.genericConfig.admin.models.wrapper.Wrapper
import org.genericConfig.admin.models.wrapper.step.StepIn
import org.genericConfig.admin.models.json.StatusSuccessfulAdditionalStepInLevelCSCreated
import org.genericConfig.admin.models.json.configTree.JsonConfigTreeIn
import org.genericConfig.admin.models.json.configTree.JsonConfigTreeOut
import org.genericConfig.admin.models.wrapper.step.VisualProposalForAdditionalStepsInOneLevelIn
import org.genericConfig.admin.shared.json.config.JsonCreateConfigOut
import org.genericConfig.admin.models.json.step.JsonFirstStepOut
import org.genericConfig.admin.shared.json.config.JsonCreateConfigIn
import org.genericConfig.admin.models.logic.User
import org.genericConfig.admin.shared.json.config.JsonGetConfigsIn
import org.genericConfig.admin.shared.json.config.JsonGetConfigsOut
import org.genericConfig.admin.models.logic.Config
import org.genericConfig.admin.shared.json.registration.JsonRegistrationOut

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann on 13.11.2016
 * 
 * Administrator definiert und zusammenstellt sein einegen Konfiguration.
 * Auf der Webseite von Administrator werden alle notwendigen Einstellungen 
 * zu dem ConfigServer, ConfigClient getätigt. Danach kann der User alle 
 * Schritte der Konfiguration difenieren. 
 * 
 * 1. Administrator soll sich zuerst Registrieren mit einem Username und Password. 
 * 2. Nach der Regestrierung kann der Admin mit Username und Passwort sich in 
 * ---- der Administrationsbereich anmelden und eine eigen Konfiguration 
 * ---- erstellen.
 */


class Admin extends Wrapper{
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param RegistrationCS
   * 
   * @return RegistrationSC
   */
  def register(username: String, password: String): JsonRegistrationOut = {
    toJsonRegistrationOut(User.registUser(username, password))
  }
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param LoginSC
   * 
   * @return LoginCS
   */
  def login(jsonLoginIn: JsonLoginIn): JsonLoginOut = {
    toJsonLoginOut(Persistence.login(jsonLoginIn.params.username, jsonLoginIn.params.password))
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param CreateConfigCS
   * 
   * @return CreateConfigSC
   */
  def createConfig(jsonCreateConfigIn: JsonCreateConfigIn): JsonCreateConfigOut = {
    toJsonCreateConfigOut(
        Config.createConfig(jsonCreateConfigIn.params.adminId, jsonCreateConfigIn.params.configUrl))
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
  def getConfigs(jsonGetConfigsIn: JsonGetConfigsIn): JsonGetConfigsOut = {
    Config.getConfigs(jsonGetConfigsIn.params.userId)
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
  def deleteConfig() = ???
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param FirstStepCS
   * 
   * @return FirstStepSC
   */
  def createFirstStep(jsonFirstStepIn: JsonFirstStepIn): JsonFirstStepOut = {
    toJsonFirstStepOut(Persistence.createFirstStep(toFirstStepIn(jsonFirstStepIn)))
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param ComponentCS
   * 
   * @return ComponentSC
   */
  def createComponent(jsonComponentIn: JsonComponentIn): JsonComponentOut = {
    toJsonComponentOut(Persistence.createComponent(toComponentIn(jsonComponentIn)))
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param StepCS
   * 
   * @return StepSC
   */
  
  def createStep(jsonStepIn: JsonStepIn): JsonStepOut = {
    
    toJsonStepOut(Persistence.createStep(toStepIn(jsonStepIn)))
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * Verbindet bestehnde Component mit bestehenden Step
   * Sowohl Component alsauch Step muessen bereits exestieren
   * 
   * @param ConnectionComponentToStepCS
   * 
   * @return ConnectionComponentToStepSC
   */
  def connectComponentToStep(
      jsonConnectionComponentToStepIn: JsonConnectionComponentToStepIn
      ): JsonConnectionComponentToStepOut = {
    toJsonConnectionComponentToStepOut(
        Persistence.connectComponentToStep(
            toConnectionComponentToStepIn(jsonConnectionComponentToStepIn)
        )
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param ConfigTreeCS
   * 
   * @return ConfigTreeSC
   */
  def configTree(jsonConfigTreeIn: JsonConfigTreeIn): JsonConfigTreeOut = {
    toJsonConfigTreeOut(Persistence.getConfigTree(toConfigTreeIn(jsonConfigTreeIn)))
  }

  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.2
   * 
   * @param dependencyCS
   * 
   * @return ConfigTreeSC
   */
  def createDependency(jsonDependencyIn: JsonDependencyIn): JsonDependencyOut = {
    toJsonDependencyOut(Persistence.createDependency(toDependencyIn(jsonDependencyIn)))
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param VisualProposalForAdditionalStepsInOneLevelCS
   * 
   * @return StepSC
   */
  
  def visualProposalForAdditionalStepsInOneLevel(
      jsonVisualProposalForAdditionalStepsInOneLevelIn: JsonVisualProposalForAdditionalStepsInOneLevelIn): JsonStepOut = {
    
    val visualProposalForAdditionalStepsInOneLevel: VisualProposalForAdditionalStepsInOneLevelIn = 
      toVisualProposalForAdditionalStepsInOneLevelIn(jsonVisualProposalForAdditionalStepsInOneLevelIn)
      
    //hole Step aus der Temp
    val stepCS: Option[StepIn] = TempConfigurations.getAndRemoveAdditionalStepInLevelCS
    
    val dependencies: Set[JsonDependencyForAdditionalStepsInOneLevel] = Persistence.createDependenciesForAdditionalStepInLevelCS(
        stepCS, visualProposalForAdditionalStepsInOneLevel)
    
    //TODO v016 Prüfe auf leere Dependencies und Set.empty
    
    val tempStep = Persistence.createAditionalStepInLevelCS(stepCS.get)
    
    JsonStepOut(
        result = JsonStepResult(
            tempStep.stepId,
            StatusSuccessfulAdditionalStepInLevelCSCreated.status,
            StatusSuccessfulAdditionalStepInLevelCSCreated.message,
            Set.empty,
            dependencies
        )
    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param 
   * 
   * @return 
   */
  def logout(): Boolean = ???
}