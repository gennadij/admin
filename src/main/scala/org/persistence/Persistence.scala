package org.persistence

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable

import scala.collection.JavaConversions._
import com.orientechnologies.orient.core.sql.OCommandSQL
import org.persistence.db.orientdb.StepVertex
import org.persistence.db.orientdb.ComponentVertex
import org.persistence.db.orientdb.HasComponentEdge
import org.persistence.db.orientdb.AdminUserVertex
import org.persistence.db.orientdb.OrientDB
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.OrientEdge
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import org.dto.login.LoginSC
import org.dto.login.LoginCS
import org.dto.registration.RegistrationCS
import org.dto.registration.RegistrationSC
import org.dto.configTree.ConfigTreeSC
import org.dto.configTree.ConfigTreeCS
import org.dto.component._
import org.dto.step.StepCS
import org.dto.step.StepSC
import org.dto.config.CreateConfigCS
import org.dto.config.CreateConfigSC
import org.persistence.db.orientdb.ConfigVertex
import org.persistence.db.orientdb.HasConfigEdge
import org.dto.config.CreateConfigResult
import org.dto.step.FirstStepCS
import org.persistence.db.orientdb.HasFirstStepEdge
import org.dto.step.FirstStepSC
import org.dto.step.FirstStepResult
import org.persistence.db.orientdb.HasStepEdge
import org.dto.step.StepResult
import org.dto.connectionComponentToStep.ConnectionComponentToStepSC
import org.dto.connectionComponentToStep.ConnectionComponentToStepCS

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.11.2016
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
   * @param CreateConfigSC
   * 
   * @return CreateConfigCS
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
   * @param FirstStepCS
   * 
   * @return FirstStepSC
   */
  
  def createFirstStep(firstStepCS: FirstStepCS): FirstStepSC = {
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
  def createComponent(componentCS: ComponentCS): ComponentSC = {
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
   * @param StepSC
   * 
   * @return StepCS
   */
  def createStep(stepCS: StepCS): StepSC = {
    val stepSC: StepSC = StepVertex.step(stepCS)
    if(stepSC != null ) {
      if (stepSC.result.status) {
        val eHasStep: OrientEdge = HasStepEdge.hasStep(stepCS, stepSC)
        if(eHasStep != null) {
          stepSC
        }else{
          StepSC(
              result = StepResult(
                  "",
                  false,
                  "Es ist einen Fehler aufgetreten"
              )
          )
        }
      }else{
        stepSC
      }
    }else{
      StepSC(
          result = StepResult(
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
   * @param ConnectionComponentToStepCS
   * 
   * @return ConnectionComponentToStepSC
   */
  
  def connectComponentToStep(
      connectionComponentToStepCS: ConnectionComponentToStepCS
      ): ConnectionComponentToStepSC = {
    HasStepEdge.hasStep(connectionComponentToStepCS)
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
    ConfigVertex.getConfigTree(configTreeCS)
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
}