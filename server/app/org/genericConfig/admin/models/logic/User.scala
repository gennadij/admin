package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.user.UserDTO

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
   * @param user: UserDTO
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
   * @param userParams: UserBO
   * 
   * @return UserBO
   */
  
  def getUser(userParams: UserDTO): UserDTO = {
    new User(userParams).getUser
  }
  
}

class User(userParam: UserDTO) {

  /**
   * @author Gennadi Heimann
   *
   * @version 0.1.6
   *
   * @return UserBO
   */
  private def addUser: UserDTO = {
    val userResult: UserDTO = Persistence.addUser(userParam.params.get.username, userParam.params.get.password)
    userResult.result.get.errors match {
      case None =>
        userResult.copy(result = Some(userResult.result.get.copy(userId = Some(RidToHash.setIdAndHash(userResult.result.get.userId.get)._2))))
      case _ => userResult
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @return UserBO
   */
  
  private def getUser : UserDTO = {
    val userResult: UserDTO = Persistence.getUser(userParam.params.get.username, userParam.params.get.password)
    userResult.result.get.errors match {
      case None =>
        userResult.copy(result = Some(userResult.result.get.copy(userId = Some(RidToHash.setIdAndHash(userResult.result.get.userId.get)._2))))
      case _ => userResult
    }
  }
}