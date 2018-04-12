package org.genericConfig.admin

import org.scalajs.jquery.jQuery

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