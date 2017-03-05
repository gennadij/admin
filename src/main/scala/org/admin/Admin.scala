package org.admin

import org.persistence.Persistence
import org.persistence.db.orientdb.AdminUserVertex
import play.api.libs.json.Writes
import play.api.libs.json.Json
import org.dto.login.LoginCS
import org.dto.login.LoginSC
import org.dto.configTree.ConfigTreeCS
import org.dto.configTree.ConfigTreeSC
import org.dto.registration.RegistrationSC
import org.dto.registration.RegistrationCS
import org.dto.component._
import org.dto.step.StepCS
import org.dto.step.StepSC
import org.dto.config.CreateConfigCS
import org.dto.config.CreateConfigSC
import org.dto.step.FirstStepCS
import org.dto.step.FirstStepSC
import org.dto.connectionComponentToStep.ConnectionComponentToStepCS
import org.dto.connectionComponentToStep.ConnectionComponentToStepSC

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


object Admin {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param RegistrationCS
   * 
   * @return RegistrationSC
   */
  def register(registrationCS: RegistrationCS): RegistrationSC = {
    Persistence.register(registrationCS)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param LoginSC
   * 
   * @return LoginCS
   */
  def login(loginCS: LoginCS): LoginSC = {
    Persistence.login(loginCS)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param CreateConfigCS
   * 
   * @return CreateConfigSC
   */
  def createConfig(createConfigCS: CreateConfigCS): CreateConfigSC = {
    Persistence.createConfig(createConfigCS)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.0
   * 
   * @param FirstStepCS
   * 
   * @return FirstStepSC
   */
  def createFirstStep(firstStepCS: FirstStepCS): FirstStepSC = {
    Persistence.createFirstStep(firstStepCS)
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
  def createComponent(componentCS: ComponentCS): ComponentSC = {
    Persistence.createComponent(componentCS)
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
  
  def createStep(stepCS: StepCS): StepSC = {
    Persistence.createStep(stepCS)
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
      connectionComponentToStep: ConnectionComponentToStepCS
      ): ConnectionComponentToStepSC = {
    Persistence.connectComponentToStep(connectionComponentToStep)
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
  def configTree(configTreeCS: ConfigTreeCS): ConfigTreeSC = {
    Persistence.getConfigTree(configTreeCS)
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