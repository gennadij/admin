package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.common.json.{JsonNames, JsonStatus}
import org.genericConfig.admin.shared.user.UserDTO
import org.genericConfig.admin.shared.user.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 28.05.2018
 */
class WrapperUser{
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonUserIn: JsonUserIn
   * 
   * @return  UserBO
   */
  
  def toAddUserBO(jsonUserIn: JsonUserIn) = {
    ???
//    UserBO(
//        username = Some(jsonUserIn.params.username),
//        password = Some(jsonUserIn.params.password),
//    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonUserIn: JsonUserIn
   * 
   * @return UserBO
   */
  
  def toGetUserBO(jsonUserIn: JsonUserIn) = {
    ???
//    UserBO(
//        username = Some(jsonUserIn.params.username),
//        password = Some(jsonUserIn.params.password),
//    )
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param userBO: UserBO
   * 
   * @return JsonUserOut
   */
//  def toJsonAddUserOut(userBO: UserBO): JsonUserOut = {
    ???
    
//    val userIdHash = userBO.userId match {
//      case Some(userId) => 
//        Some(RidToHash.setIdAndHash(userId)._2)
//      case None => None
//    }
    
//    JsonUserOut(
//        json = JsonNames.ADD_USER,
//        result = JsonUserResult(
//          userBO.userId,
//          userBO.username,
//          JsonUserStatus(
//              addUser = userBO.status.get.addUser match {
//                case Some(adduser) => Some(JsonStatus(adduser.status, adduser.message))
//                case None => None
//              },
//              common = userBO.status.get.common match {
//                case Some(common) => Some(JsonStatus(common.status, common.message))
//                case None => None
//              }
//          )
//        )
//    )
//  }
  
  
    /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param userBO: UserBO
   * 
   * @return JsonUserOut
   */
  def toJsonGetUserOut(userBO: UserDTO): JsonUserOut = {
    ???
//    val userIdHash = userBO.userId match {
//      case Some(userId) => 
//        Some(RidToHash.setIdAndHash(userId)._2)
//      case None => None
//    } 
    
//    JsonUserOut(
//        json = JsonNames.GET_USER,
//        result = JsonUserResult(
//            userBO.userId,
//            userBO.username,
//            JsonUserStatus(
//                getUser = userBO.status.get.getUser match {
//                  case Some(getUser) => Some(JsonStatus(getUser.status, getUser.message))
//                  case None => None
//                },
//                common = userBO.status.get.common match {
//                  case Some(common) => Some(JsonStatus(common.status, common.message))
//                  case None => None
//                }
//            )
//        )
//    )
  }
}