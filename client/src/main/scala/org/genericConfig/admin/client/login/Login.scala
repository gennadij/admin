package org.genericConfig.admin.client.login

import org.scalajs.jquery.jQuery
import scala.scalajs.js.Any.fromFunction0
/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 12.04.2018
 */




class Login {
  
  
  def getUsername = {
    jQuery("#loginButton").on("click", () => selectLoginButton())
  }
  
  def selectLoginButton() = {
    println(jQuery("#username").value())
    println(jQuery("#password").value())
  }
}