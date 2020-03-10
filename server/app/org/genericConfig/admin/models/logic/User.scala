package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.Persistence
import org.genericConfig.admin.shared.user.UserDTO
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
   * @param userBO: UserBO
   * 
   * @return UserBO
   */
  
  def getUser(userBO: UserBO): UserBO = {
    ???
    //new User(userBO).getUser
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
    val userResult: UserDTO = Persistence.addUser(userParam.params.username, userParam.params.password)
    userResult.result.errors match {
      case None =>
        userResult.copy(result = userResult.result.copy(userId = Some(RidToHash.setIdAndHash(userResult.result.userId.get)._2)))
      case _ => userParam
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
    ???
//    val userBOOut: UserBO = Persistence.getUser(userBO.username.get, userBO.password.get)
//    userBOOut.status.get.getUser match {
//      case Some(GetUserSuccess()) => userBOOut.copy(userId = Some(RidToHash.setIdAndHash(userBOOut.userId.get)._2))
//      case _ => userBOOut
//    }
  }
}