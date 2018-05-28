package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.login.bo._
import org.genericConfig.admin.shared.user.bo.UserBO

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
  def addUser(userBO: UserBO): UserBO = {
    new User().addUser(userBO)
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
  private def addUser(userBO: UserBO): UserBO = {
    Persistence.addUser(userBO.username.get, userBO.password.get)
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