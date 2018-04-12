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

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */

object Registration{
  
  def registUser(username: String, password: String): RegistrationBO = {
    new Registration().registUser(username, password)
  }
  
}

class Registration {
  
  def registUser(username: String, password: String): RegistrationBO = {
//    val user: RegistrationBO = 
      Persistence.addUser(username, password)
    
//    (user.status: @unchecked) match {
//      case StatusRegistration(None, Some(Error())) => {
//        JsonRegistrationOut(
//            result = RegistrationResult(
//                user.adminId,
//                user.username,
//                user.status
//            )
//        )
//      }
//      case StatusRegistration(Some(AlredyExistUser()), Some(Success())) => ???
//      case StatusRegistration(Some(AddedUser()), Some(Success())) => ???
//    }
  }
}