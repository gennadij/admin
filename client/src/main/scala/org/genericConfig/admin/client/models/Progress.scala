package org.genericConfig.admin.client.models

import org.genericConfig.admin.shared.component.ComponentDTO
import org.genericConfig.admin.shared.config.ConfigDTO
import org.genericConfig.admin.shared.step.StepDTO
import org.genericConfig.admin.shared.user.UserDTO

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 26.04.2020
 */
object Progress {
  var states : List[State] = List()

  def addState(state : State): Unit = {
    states.size match {
      case size if size > 10 => println("Lösche alle Einträge außer letzte 10")
      case _ => this.states = state ::this.states
    }
  }

  def getStates : Option[List[State]] = {
    states match {
      case List() => None
      case _ => Some(this.states)
    }
  }

  def getLastState : Option[State] = {
    states match {
      case List() => None
      case _ => Some(this.states.head)
    }
  }
}

case class State(
  userDTO : Option[UserDTO] = None,
  configDTO: Option[ConfigDTO] = None,
  stepDTO: Option[StepDTO] = None,
  componentDTO : Option[ComponentDTO] = None
)