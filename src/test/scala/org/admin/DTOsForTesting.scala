package org.admin

import play.api.libs.json.Json

object DTOsForTesting {
    val registerCS_1 = Json.obj(
      "jsonId" -> 1,
      "dto" -> "Registration"
      ,"params" -> Json.obj(
          "username" -> "test8",
          "password"-> "test8"))
          
    val registerSC_1 = Json.obj(
        "jsonId" -> 1,
	      "dto" -> "Registration",
	      "result" -> 
	        Json.obj(
	            "adminId" -> "",
	            "username" -> "test8",
	            "status" -> false,
	            "message" -> "Registrierung war nicht erfolgreich. Username existiert bereits"
	       )
    )
          
//{"jsonId":1,"dto":"Registration","result":{"adminId":"","username":"test8","stat
//us":false,"message":"Registrierung war nicht erfolgreich. Username existiert ber
//eits"}}
}