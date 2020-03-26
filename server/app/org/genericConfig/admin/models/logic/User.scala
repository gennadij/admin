package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.common.Error
import org.genericConfig.admin.models.persistence.orientdb.{GraphUser, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.user.{UserDTO, UserResultDTO}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */

object User {
  
  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param user: UserDTO
   * @return UserDTO
   */
  def addUser(user: UserDTO): UserDTO = {
    new User(user).addUser()
  }

  /**
   * @author Gennadi Heimann
   *
   * @version 0.1.6
   *
   * @param user: UserDTO
   *
   * @return UserDTO
   */
  def deleteUser(user: UserDTO): UserDTO = {
    new User(user).deleteUser()
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

  /**
    * @author Gennadi Heimann
    *
    * @version 0.1.6
    *
    * @param userParams: UserBO
    *
    * @return UserBO
    */

  def updateUser(userParams: UserDTO): UserDTO = {
    new User(userParams).updateUser()
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
  private def addUser(): UserDTO = {

    val (vUser: Option[OrientVertex], error : Option[Error]) =
      GraphUser.addUser(userParam.params.get.username, userParam.params.get.password)

    createUserDTO(
      action = Actions.ADD_USER,
      userId = Some(RidToHash.setIdAndHash(vUser.get.getIdentity.toString())._2),
      username = Some(vUser.get.getProperty(PropertyKeys.USERNAME).toString),
      error = error
    )
  }

  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @return UserBO
   */
  private def deleteUser(): UserDTO = {
    val (vUser: Option[OrientVertex], error : Option[Error]) =
      GraphUser.deleteUser(userParam.params.get.username, userParam.params.get.password)

    createUserDTO(
      action = Actions.DELETE_USER,
      username = Some(userParam.params.get.username),
      error = error
    )
  }
  
  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @return UserBO
   */
  
  private def getUser : UserDTO = {
    val (vUser: Option[OrientVertex], error: Option[Error]) =
      GraphUser.getUser(userParam.params.get.username, userParam.params.get.password)

    val userId : Option[String] = vUser match {
      case None => None
      case Some(vU) => Some(RidToHash.setIdAndHash(vU.getIdentity.toString)._2)
    }
    val username : Option[String] = vUser match {
      case None => None
      case Some(vU) => Some(vUser.get.getProperty(PropertyKeys.USERNAME).toString)
    }

    createUserDTO(
      action = Actions.GET_USER,
      userId = userId,
      username = username,
      error = error
    )
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @return UserBO
    */

  private def updateUser() : UserDTO = {
    (userParam.params.get.update.get.oldUsername, userParam.params.get.update.get.oldPassword) match {
      case (username, "") =>
        val (vUser: Option[OrientVertex], error: Option[Error]) =
          GraphUser.updateUserName(userParam.params.get.update.get.oldUsername, userParam.params.get.update.get.newUsername)
        createUserDTO(
          action = Actions.UPDATE_USER,
          username = Some(userParam.params.get.update.get.newUsername),
          error = error
        )
      case ("", password) => ???
      case (username, password) => ???
    }
  }
  private def createUserDTO(
                             action : String,
                             userId : Option[String] = None,
                             username: Option[String] = None,
                             error : Option[Error] = None): UserDTO = {
    error match {
      case None =>
        UserDTO(
          action = action,
          params = None,
          result = Some(UserResultDTO(
            userId = userId,
            username = username,
            errors = None
          ))
        )
      case _ =>
        UserDTO(
          action = action,
          params = None,
          result = Some(UserResultDTO(
            userId = None,
            username = None,
            errors = Some(List(ErrorDTO(
              name = error.get.name,
              message = error.get.message,
              code = error.get.code
            )))
          ))
        )
    }

  }
}