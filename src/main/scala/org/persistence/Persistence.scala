/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.persistence

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable

import scala.collection.JavaConversions._
import com.orientechnologies.orient.core.sql.OCommandSQL
import org.persistence.db.orientdb.StepVertex
import org.persistence.db.orientdb.ComponentVertex
import org.persistence.db.orientdb.HasComponentEdge
import org.persistence.db.orientdb.NextStepEdge
import org.persistence.db.orientdb.AdminUserVertex
import org.persistence.db.orientdb.OrientDB
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import org.dto.login.LoginSC
import org.dto.login.LoginCS
import org.persistence.db.orientdb.ConfigTree
import org.dto.registration.RegistrationCS
import org.dto.registration.RegistrationSC
import org.dto.configTree.ConfigTreeSC
import org.dto.configTree.ConfigTreeCS
import org.dto.component._
import org.dto.connStepToComponent.ConnStepToComponentCS
import org.dto.connStepToComponent.ConnStepToComponentSC
import org.dto.step.StepCS
import org.dto.step.StepSC
import org.dto.connComponentToStep.ConnComponentToStepSC
import org.dto.connComponentToStep.ConnComponentToStepCS
import org.dto.config.CreateConfigCS
import org.dto.config.CreateConfigSC
import org.persistence.db.orientdb.ConfigVertex
import org.persistence.db.orientdb.HasConfigEdge
import org.dto.config.CreateConfigResult
import org.dto.step.FirstStepCS
import org.persistence.db.orientdb.HasFirstStepEdge
import org.dto.step.FirstStepSC
import org.dto.step.FirstStepResult

/**
 * Created by Gennadi Heimann 1.1.2017
 */

object Persistence {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param RegistrationCS
   * 
   * @return RegistrationSC 
   */
  def register(registrationCS: RegistrationCS): RegistrationSC = {
    AdminUserVertex.register(registrationCS)
  }

  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param LoginCS
   * 
   * @return LoginSC
   */
  def login(loginCS: LoginCS): LoginSC = {
    AdminUserVertex.login(loginCS)
  }

  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param LoginCS
   * 
   * @return LoginSC
   */
//  def configUri(configUriCS: ConfigUriCS): ConfigUriSC = {
//    AdminUserVertex.configUri(configUriCS)
//  }
  
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param ConfigTreeCS
   * 
   * @return ConfigTreeSC
   */
  def createConfig(createConfigCS: CreateConfigCS): CreateConfigSC = {
    val vConfig = ConfigVertex.createConfig(createConfigCS)
    val eHasConfig = HasConfigEdge.hasConfig(createConfigCS.params.adminId, vConfig)
    if(vConfig != null && eHasConfig != null) {
      CreateConfigSC(
          result = CreateConfigResult(
              vConfig.getIdentity.toString,
              true,
              "Die Konfiguration wurde erfolgreich erzeugt"
          )
      )
    }else {
      CreateConfigSC(
          result = CreateConfigResult(
              "",
              false,
              "Beim Erzeugen der Konfiguration ist einen Fehler aufgetreten"
          )
      )
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param firstStepCS
   * 
   * @return firstStepSC
   */
  
  def firstStep(firstStepCS: FirstStepCS): FirstStepSC = {
    val firstStepSC: FirstStepSC = StepVertex.firstStep(firstStepCS)
    if(firstStepSC != null ) {
      if (firstStepSC.result.status) {
        val eHasFirstStep: OrientEdge = HasFirstStepEdge.hasFirstStep(firstStepCS, firstStepSC)
        if(eHasFirstStep != null) {
          firstStepSC
        }else{
          FirstStepSC(
              result = FirstStepResult(
                  "",
                  false,
                  "Es ist einen Fehler aufgetreten"
              )
          )
        }
      }else{
        firstStepSC
      }
    }else{
      FirstStepSC(
          result = FirstStepResult(
              "",
              false,
              "Es ist einen Fehler aufgetreten"
          )
      )
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param ConfigTreeCS
   * 
   * @return ConfigTreeSC
   */
  def getConfigTree(configTreeCS: ConfigTreeCS): ConfigTreeSC = {
    ConfigTree.getConfigTree(configTreeCS)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param ComponentCS
   * 
   * @return ComponentSC
   */
  def addComponent(componentCS: ComponentCS): ComponentSC = {
    ComponentVertex.addComponent(componentCS)
  }
  
  /**
   * creting von new Component and connect thies new Component with Step from param
   * 
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param ComponentCS
   * 
   * @return ComponentSC
   */
  def component(componentCS: ComponentCS): ComponentSC = {
    val componentSC: ComponentSC = ComponentVertex.component(componentCS)
    if(HasComponentEdge.hasComponent(componentCS, componentSC) != null) {
      componentSC
    }else{
      ComponentSC(
          result = ComponentResult(
              "",
              false,
              "Es ist einen Fehler aufgetreten"
          )
      )
    }
  }
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param ConnStepToComponentCS
   * 
   * @return ConnStepToComponentSC
   */
  def addHasComponent(connStepToComponentSC: ConnStepToComponentCS): ConnStepToComponentSC = {
    HasComponentEdge.add(connStepToComponentSC)
  }
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param ConnComponentToStepCS
   * 
   * @return ConnComponentToStepSC
   */
  def addNextStep(connComponentToStepCS: ConnComponentToStepCS): ConnComponentToStepSC = {
    NextStepEdge.add(connComponentToStepCS)
  }
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param StepCS
   * 
   * @return StepSC 
   */
  def addStep(stepCS: StepCS): StepSC = {
    StepVertex.addStep(stepCS)
  }
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param 
   * 
   * @return 
   */
  def rules() = ???
  

  
//  def addStep(adminStep: AdminStep): AdminStep = {
//    StepVertex.addStep(adminStep)
//  }
  
  
//  def authenticate(username: String, password: String): String = {
//    val adminId: String = AdminUserVertex.adminId(username, password)
//    if(adminId.isEmpty()) "" else "AU" + adminId
//  }
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
  
//  def addStep(adminStep: AdminNextStep): AdminNextStep = {
//    StepVertex.addStep(adminStep)
//  }
//  def addComponent(adminComponent: AdminComponent): AdminComponent = {
//    ComponentVertex.addComponent(adminComponent)
//  }
  
//  def getConfigTree(adminId: String): AdminConfigTree = {
//    val graph: OrientGraph = OrientDB.getGraph
//    
//    val res: OrientDynaElementIterable = graph
//      .command(new OCommandSQL("select from " + 
//          "(SELECT FROM " + 
//                "(traverse out(hasComponent) from " + 
//                      "(select from Step where kind='first') STRATEGY BREADTH_FIRST)" + 
//                 s" where @class='Step') where adminId='$adminId'")).execute()
//      
//    val vSteps: List[OrientVertex] = res.toList.map(_.asInstanceOf[OrientVertex])
//    
//    new AdminConfigTree(vSteps.map(getAdminStep(_, graph, adminId)))
//  }
  
//  def addHasComponent(adminId: String, outStep: String, inComponents: List[String]): Status = {
//    HasComponentEdge.add(adminId, outStep, inComponents)
//  }
//  
//  def addHasComponent(adminId: String, outStep: String, inComponent: String): Status = {
//    HasComponentEdge.add(adminId, outStep, inComponent)
//  }
  
  /**
   * 
   * verbindet Step und Component in ConfigTree hinzu
   * 
   * @author Gennadi Heimann
   * 
   * @version 1.0
   * 
   * @param AdminStep
   * 
   * @return Status
   */
//  
//  def addNextStep(adminId: String, outComponent: String, inStep: String): Status = {
//    NextStepEdge.add(adminId, outComponent, inStep)
//  }
//  
//  
//  def component(id: String): AdminComponent = {
//    ComponentVertex.get(id)
//  }
  
//  def setStep(adminId: String, isConnected: Boolean, step: Step, kind: String) = {
    
    
    
//    val vStep = new VertexStep(
//        step.id, step.nameToShow, step.fatherStep, step.nextStep,
//        step.components, step.dependencies)
    
//    /**
//     * 1. Erstelle die Schema 
//     * 		Vertex -> Step, Component 
//     * 		Edge -> hasComponent, nextStep
//     * 
//     * 
//     * 2. befühle die Classes mit objects
//     * 
//     * 4. update objects
//     * 
//     * 5. Step besteht aus der 
//     * 		1. HauptStep und deren Components
//     * 		2. Components mit deren NextSteps
//     * 
//     * 6. NextStep werden bei der erzeugzng von der HauptStep erzeugt
//     * 
//     * 7. Zuerst wird Config erstellt. Der Config bildet nur das Ablauf der Konfiguration ab.
//     * 
//     * 8. Wenn config erstellt wurde, nachher werden die Regeln hinzugefuegt in der config Graph
//     * 
//     * 9. Die Rules bestehen aus:
//     * 		Edge -> dependency
//     */
//    
//    /*
//     * create Step
//     * content:
//     * - stepId
//     * - kind
//     */
//    if(isConnected){
//      val propStep = Map("stepId" -> step.id, "adminId" -> adminId)
//      println(StepVertex.create(propStep).message)
//
//    }
//    /*
//     * create Components
//     * content each Component
//     * - componentId
//     * - adminId
//     */
//    
//    step.components foreach ( c => {
//      val propComponent = Map("componentId" -> c.id, "adminId" -> adminId)
//      println(ComponentVertex.create(propComponent).message)
//    } )
//    
//    /*
//     * create nextSteps
//     * content each nextStep
//     * - stepId
//     * - adminId
//     */
//    
//    step.nextStep foreach ( nS => {
//      if(nS.step != "S00000"){
//        val propNextStep = Map("stepId" -> nS.step , "adminId" -> adminId)
//        println(StepVertex.create(propNextStep).message)
//      }
//      
//    } )
//
//    /*
//     * connect Step with Components
//     * 
//     */
//    
//    val stStepToComponent = HasComponentEdge.connect(step.id, step.components)
//    
//    stStepToComponent.foreach { s => println(s.message) }
//    /*
//     * create NextStep
//     */
//    
//    val stComponentsToNextStep = NextStepEdge.connect(step.nextStep)
//    stComponentsToNextStep.foreach { s => println(s.message) }
//    
//    new SuccessfulStatus("Step created", "")
//  }
//  
//  
//  
//  def getPersisitence = {
//    val configSetting = scala.xml.XML.loadFile("persistence/config_settings.xml")
//    
//    val kindOfpersisitenceForConfigTree = configSetting \ "persistence" \ "persistenceKind" \ "forConfigTree"
//    val kindOfpersisitenceForRule = configSetting \ "persistence" \ "persistenceKind" \ "forRule"
//    
//    val configTree = configSetting \ "persistence" \ "xml" \ "config"
//    val rule = configSetting \ "persistence" \ "xml" \ "rule"
//    
//    new ConfigSetting("", configTree.text.toString(), rule.text.toString(), "presentation")
//  }
}