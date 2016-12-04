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
import org.admin.configTree.AdminStep
import org.admin.configTree.AdminComponent
import org.admin.configTree.AdminNextStep
import play.api.libs.json.Writes
import play.api.libs.json.Json
import org.dto.Login.LoginCS
import org.dto.Login.LoginSC
import org.dto.firstStep.FirstStepSC
import org.dto.firstStep.FirstStepCS
import org.dto.configTree.ConfigTreeCS
import org.dto.configTree.ConfigTreeSC
import org.dto.registration.RegistrationSC
import org.dto.registration.RegistrationCS

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
  
  def configTree(configTreeCS: ConfigTreeCS): ConfigTreeSC = {
    Persistence.getConfigTree(configTreeCS)
  }
  
  def addFirstStep(firstStepCS: FirstStepCS): FirstStepSC = {
    Persistence.addFirstStep(firstStepCS)
  }
  
  
  
  
  
  def addStep(adminStep: AdminNextStep): AdminNextStep = {
    Persistence.addStep(adminStep)
  }
  
  def addStep(adminStep: AdminStep): AdminStep = {
    Persistence.addStep(adminStep)
  }
  
  /**
   * create ConfigTree
   * 
   * 
   */
  
  /**
   * 
   * fuegt Vertex Step zu ConfigTree hinzu
   * 
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param AdminStep
   * 
   * @return Status
   */
  

  
  /**
   * 
   * fuegt Vertex Step zu ConfigTree hinzu
   * 
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param AdminStep
   * 
   * @return Status
   */
  
  /**
   * fuegt Vertex Component zu ConfigTree hinzu
   */
  def addComponent(adminComponent: AdminComponent): AdminComponent = {
    Persistence.addComponent(adminComponent)
  }
  /**
   * fuegt Edge hasComponent zu ConfigTree hinzu, dadurch wird Vertex Step mit 
   * Vertex Component verbunden
   */
  def addHasComponent(adminId: String, outStep: String, inComponents: List[String]) = {
    Persistence.addHasComponent(adminId, outStep, inComponents)
  }
  def addHasComponent(adminId: String, outStep: String, inComponent: String): Status = {
    Persistence.addHasComponent(adminId, outStep, inComponent)
  }
  
  /**
   * fuegt Edge NextStep zu ConfigTree hinzu, dadurch wird Vertex Component mit 
   * Vertex Step erbunden
   */
  def addNextStep(adminId: String, outComponent: String, inStep: String) = {
    Persistence.addNextStep(adminId, outComponent, inStep)
  }
  
//  def setStep(user: String, isConnected: Boolean, step: Step, kind: String): Status = {
//    
//      val stepId = List.empty
//    
//      Persistence.setStep(user, isConnected, step, kind)
//  }

  
  def component(id: String): AdminComponent = {
    Persistence.component(id)
  }
  
  /*
   * return aktualisierte Liste von Steps
   * 
   * action 
   *  - add
   *  - remove
   *  - update
   */
  
//  def updateConfig(adminId: String, step: Step, action: String): List[Step] = ???
  
  def logout(adminId: String): Boolean = ???
  
  
  /**
   * END
   */
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  /**
   * Anmeldung von Admin
   * 
   * Cleint -> Anmeldung mit regestrierten Daten (adminId, password)
   * 
   * Server -> true
   *        -> false --> adminId existiert nicht, Falsches Password
   */
  
  def connect(username: String, password: String): Status = {
    
//    val admins: Seq[AdminId] = InterfaceAdminPersistence.admin(adminId, password)
//    
//    findAndCheckAdmin(adminId, password, admins)
    val adminId = AdminUserVertex.adminId(username, password)
    
    if (adminId == "") new WarningStatus("The User with thisusername and password not exist", "")
      else new SuccessfulStatus(s"user with id = $adminId is logged", "")
  }
  
//  def findAndCheckAdmin(adminId: String, password: String, admins: Seq[AdminId]): Status = {
//    
//    if(admins.exists { admin => admin.adminId == adminId && admin.password == password })
//      SuccessfulStatus("Anmeldung ist erfolgreich", "")
//    else
//      ErrorStatus("Administrator Id oder Passwort falsch", "")
//  }
//  
//  def setConnectPathForConfigClient(clientId: String, configPath: String) = {
//    
//    val pathToConfigID = "config_ids/client_" + configPath.split("/").last + ".xml"
//    
//    val configFile = "config_" + configPath.split("/").last + ".xml"
//    
//    val client = new ConfigID(clientId, configPath, configFile)
//    
//    val clientXML: Node = client.toXML
//    
//    scala.xml.XML.save(pathToConfigID, clientXML, "UTF-8", true, null)
//  }
}