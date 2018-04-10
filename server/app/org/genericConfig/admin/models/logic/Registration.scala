package org.genericConfig.admin.models.logic

import org.genericConfig.admin.shared.bo.RegistrationBO
import org.genericConfig.admin.models.json.registration.JsonRegistrationOut

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */

object Registration{
  
  def registUser(username: String, password: String): JsonRegistrationOut = {
    new Registration().registUser(username, password)
  }
  
}

class Registration {
  
  
  
  def registUser(username: String, password: String): JsonRegistrationOut = {
    
    
    
    
    ???
  }
}