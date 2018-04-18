package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.json.registration.JsonRegistrationOut
import org.genericConfig.admin.shared.bo.RegistrationBO
import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.status.Error
import org.genericConfig.admin.shared.status.registration.StatusRegistration
import org.genericConfig.admin.shared.status.registration.AlredyExistUser
import org.genericConfig.admin.shared.status.registration.AddedUser
import org.genericConfig.admin.shared.status.Success
import org.genericConfig.admin.models.json.registration.RegistrationResult
import org.genericConfig.admin.shared.bo.LoginBO

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */

object User {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  def registUser(username: String, password: String): RegistrationBO = {
    new User().registUser(username, password)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  
  def login(username: String, password: String): LoginBO = {
    new User().login(username, password)
  }
  
}

class User {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  private def registUser(username: String, password: String): RegistrationBO = {
    Persistence.addUser(username, password)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  
  private def login(username: String, password: String): LoginBO = {
    Persistence.login(username, password)
  }
}