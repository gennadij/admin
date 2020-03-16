package org.genericConfig.admin.shared.user

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 16.03.2020
 */
case class UserUpdateDTO(
                          oldUsername : String,
                          newUsername : String,
                          oldPassword : String,
                          newPassword : String
                        )

object UserUpdateDTO {
  implicit  val format = Json.format[UserUpdateDTO]
}
