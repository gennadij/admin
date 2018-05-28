package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.login.bo.LoginBO
import org.genericConfig.admin.shared.common.json.JsonStatus
import org.genericConfig.admin.shared.user.json._
import org.genericConfig.admin.shared.user.bo.UserBO
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.shared.common.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 28.05.2018
 */
class WrapperUser() extends RidToHash{
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param 
   * 
   * @return 
   */
  
  def toUserBO(jsonUserIn: JsonUserIn): UserBO = {
    UserBO(
        username = Some(jsonUserIn.params.username),
        password = Some(jsonUserIn.params.password),
    )
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
  def toJsonUserOut(userBO: UserBO): JsonUserOut = {
    
    val userRid = userBO.userId.get
    
    val userIdHash = setIdAndHash(userRid)
    
    JsonUserOut(
        json = JsonNames.ADD_USER,
        result = JsonUserResult(
          userIdHash,
          userBO.username.get,
          JsonUserStatus(
              addUser = userBO.status.get.addUser match {
                case Some(adduser) => Some(JsonStatus(adduser.status, adduser.message))
                case None => None
              },
              common = userBO.status.get.common match {
                case Some(common) => Some(JsonStatus(common.status, common.message))
                case None => None
              }
          )
        )
    )
  }
  
  
}