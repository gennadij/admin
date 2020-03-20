package org.genericConfig.admin.client.registration

import org.scalajs.jquery.jQuery
import scala.scalajs.js.Any.fromFunction0

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 12.04.2018
 */

class Registration {
  
  def getRegistrationPage = {
    jQuery("#registrationButton").on("click", () => selectRegistrationButton())
  }
  
  private def selectRegistrationButton() = {
    println("Registration")
  }
}