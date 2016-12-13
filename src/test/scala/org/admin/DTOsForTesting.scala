package org.admin

import play.api.libs.json.Json

object DTOsForTesting {
  
    val registerCS_1 = Json.obj(
      "jsonId" -> 1,
      "dto" -> "Registration"
      ,"params" -> Json.obj(
          "username" -> "userexist",
          "password"-> "userexist"))
          
    val registerSC_1 = Json.obj(
        "jsonId" -> 1,
	      "dto" -> "Registration",
	      "result" -> 
	        Json.obj(
	            "adminId" -> "",
	            "username" -> "userexist",
	            "status" -> false,
	            "message" -> "Registrierung war nicht erfolgreich. Username existiert bereits"
	       )
    )
    
  val registerCS_2 = Json.obj(
    "jsonId" -> 1,
    "dto" -> "Registration"
    ,"params" -> Json.obj(
      "username" -> "userNotExist",
      "password"-> "userNotExist"))
      
  val registerSC_2 = Json.obj(
    "jsonId" -> 1,
    "dto" -> "Registration",
    "result" -> 
      Json.obj(
          "adminId" -> "",
          "username" -> "userNotExist",
          "status" -> true,
          "message" -> "Registrierung war erfolgreich"
     )
    )
    
    val userNotExist = "userNotExist"
}