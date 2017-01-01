/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */


package org.admin

import org.persistence.Persistence
import org.persistence.db.orientdb.AdminUserVertex
import play.api.libs.json.Writes
import play.api.libs.json.Json
import org.dto.login.LoginCS
import org.dto.login.LoginSC
import org.dto.firstStep.FirstStepSC
import org.dto.firstStep.FirstStepCS
import org.dto.configTree.ConfigTreeCS
import org.dto.configTree.ConfigTreeSC
import org.dto.registration.RegistrationSC
import org.dto.registration.RegistrationCS
import org.dto.component._
import org.dto.connStepToComponent.ConnStepToComponentCS
import org.dto.connStepToComponent.ConnStepToComponentSC
import org.dto.step.StepCS
import org.dto.step.StepSC
import org.dto.connComponentToStep.ConnComponentToStepCS
import org.dto.connComponentToStep.ConnComponentToStepSC

/**
 * Created by Gennadi Heimann on 1.1.2017
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
 * 3. Die Konfiguration besteht aus der ConfigStep und Components.
 * ---- In jedem Step oder Component kann man die DB-Query difenieren um 
 * ---- die gesamte Information zu der Step mit Components aus der fremden DB zu lesen
 * 
 */


object Admin {
  
  /**
   * @author Gennadi Heimann
   */
  def register(registrationCS: RegistrationCS): RegistrationSC = {
    Persistence.register(registrationCS)
  }
  
  /**
   * @author Gennadi Heimann
   */
  def login(loginCS: LoginCS): LoginSC = {
    Persistence.login(loginCS: LoginCS)
  }
  
  /**
   * @author Gennadi Heimann
   */
  def configTree(configTreeCS: ConfigTreeCS): ConfigTreeSC = {
    Persistence.getConfigTree(configTreeCS)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * fuegt Vertex Component zu ConfigTree hinzu
   */
  def addComponent(componentCS: ComponentCS): ComponentSC = {
    Persistence.addComponent(componentCS)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * fuegt Edge hasComponent zu ConfigTree hinzu, dadurch wird Vertex Step mit 
   * Vertex Component verbunden
   */
  def addHasComponent(connStepToComponentSC: ConnStepToComponentCS): ConnStepToComponentSC = {
    Persistence.addHasComponent(connStepToComponentSC)
  }

  /**
   * @author Gennadi Heimann
   */
  def addStep(stepCS: StepCS): StepSC = {
    Persistence.addStep(stepCS)
  }

  /**
   * @author Gennadi Heimann
   */
  def addNextStep(connComponentToStepCS: ConnComponentToStepCS): ConnComponentToStepSC = {
    Persistence.addNextStep(connComponentToStepCS)
  }

  /**
   * @author Gennadi Heimann
   */
  def logout(adminId: String): Boolean = ???
}