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
  val container : List[Container]

  def getContainer : List[Container] = {

  }
}

class Progress() {

}

case class Container(
                      userDTO : Option[UserDTO],
                      configDTO: Option[ConfigDTO],
                      stepDTO: Option[StepDTO]
                    )