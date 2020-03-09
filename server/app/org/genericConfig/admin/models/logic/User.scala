package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.user.UserDTO
import org.genericConfig.admin.shared.user.bo.UserBO
import org.genericConfig.admin.shared.user.status.{AddUserError, GetUserSuccess}

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
   * @param userBO: UserBO
   * 
   * @return UserBO
   */
  def addUser(user: UserDTO): UserDTO = {
    new User(user).addUser
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param userBO: UserBO
   * 
   * @return UserBO
   */
  
  def getUser(userBO: UserBO): UserBO = {
    new User(userBO).getUser
  }
  
}

class User(user: UserDTO) {
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @return UserBO
   */
  private def addUser: UserDTO = {
    val userBOOut: UserDTO = Persistence.addUser(user.params.username, user.params.password)
    userBOOut.status.get.addUser match {
      case Some(AddUserError()) => userBOOut
      case _ => userBOOut.copy(userId = Some(RidToHash.setIdAndHash(userBOOut.userId.get)._2))
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @return UserBO
   */
  
  private def getUser: UserBO = {
    val userBOOut: UserBO = Persistence.getUser(userBO.username.get, userBO.password.get)
    userBOOut.status.get.getUser match {
      case Some(GetUserSuccess()) => userBOOut.copy(userId = Some(RidToHash.setIdAndHash(userBOOut.userId.get)._2))
      case _ => userBOOut
    }
  }
}