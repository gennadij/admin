/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */


package org.admin

import scala.xml._
import org.status.Status
import org.status.SuccessfulStatus
import org.status.ErrorStatus
import org.persistence.Persistence
import org.persistence.db.orientdb.AdminUserVertex
import org.status.WarningStatus
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
import org.dto.nextStep.NextStepCS
import org.dto.nextStep.NextStepSC
import org.dto.connStepToComponent.ConnStepToComponentCS
import org.dto.connStepToComponent.ConnStepToComponentSC

/**
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
  
  def register(registrationCS: RegistrationCS): RegistrationSC = {
    Persistence.register(registrationCS)
  }
  
  def login(loginCS: LoginCS): LoginSC = {
    Persistence.login(loginCS: LoginCS)
  }
  
  def logout(adminId: String): Boolean = ???
  
  def configTree(configTreeCS: ConfigTreeCS): ConfigTreeSC = {
    Persistence.getConfigTree(configTreeCS)
  }
  
  def addFirstStep(firstStepCS: FirstStepCS): FirstStepSC = {
    Persistence.addFirstStep(firstStepCS)
  }
  
   /**
   * fuegt Vertex Component zu ConfigTree hinzu
   */
  
  def addComponent(componentCS: ComponentCS): ComponentSC = {
    Persistence.addComponent(componentCS)
  }
  
   /**
   * fuegt Edge hasComponent zu ConfigTree hinzu, dadurch wird Vertex Step mit 
   * Vertex Component verbunden
   */
  
  def addHasComponent(connStepToComponentSC: ConnStepToComponentCS): ConnStepToComponentSC = {
    Persistence.addHasComponent(connStepToComponentSC)
  }
  
  def addStep(nextStepCS: NextStepCS): NextStepSC = {
    null
  }
  
  def addNextStep(nextStepCS: NextStepCS): NextStepSC = {
    null
  }
  
    /**
   * fuegt Edge NextStep zu ConfigTree hinzu, dadurch wird Vertex Component mit 
   * Vertex Step erbunden
   */
//  def addNextStep(adminId: String, outComponent: String, inStep: String) = {
//    Persistence.addNextStep(adminId, outComponent, inStep)
//  }
  
//  def addStep(adminStep: AdminNextStep): AdminNextStep = {
//    Persistence.addStep(adminStep)
//  }
  
//  def addStep(adminStep: AdminStep): AdminStep = {
//    Persistence.addStep(adminStep)
//  }
}