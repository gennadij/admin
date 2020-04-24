package org.genericConfig.admin.client.models

import org.genericConfig.admin.shared.config.ConfigDTO
import org.genericConfig.admin.shared.step.StepDTO
import org.genericConfig.admin.shared.user.UserDTO

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann ${date}
 */
object Progress {
  var states : List[State] = _

  def addState(state : State) = {
    states.size match {
      case size if size > 10 => ??? //Lösche alle Einträge außer letzte 10
      case _ => this.states = this.states.::(state)
    }
  }

  def getStates(state: State) : Option[List[State]] = {
    states match {
      case List() => None
      case _ => Some(this.states)
    }
  }

  def getLastStates(state: State) : Option[State] = {
    states match {
      case List() => None
      case _ => Some(this.states.head)
    }
  }
}

case class State(
                      userDTO : Option[UserDTO] = None,
                      configDTO: Option[ConfigDTO] = None,
                      stepDTO: Option[StepDTO] = None
                    )