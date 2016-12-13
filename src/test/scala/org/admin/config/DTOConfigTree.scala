package org.admin.config

import play.api.libs.json.Json

object DTOConfigTree {
  
  val loginCS = Json.obj(
    "jsonId" -> 2,
    "dto" -> "Login"
    ,"params" -> Json.obj(
      "username" -> "firstStepConfigTree",
      "password" -> "firstStepConfigTree"
    )
  )
  
}