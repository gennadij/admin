package org.genericConfig.admin

import org.scalajs.jquery.jQuery
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