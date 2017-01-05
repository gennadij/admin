package org.admin.adminUser

import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import org.persistence.db.orientdb.AdminUserVertex

class AdminUserWithConfigUrl extends Specification 
                          with AdminWeb
                          with BeforeAfterAll{
  //TODO status false testen
  def beforeAll() = {
//    AdminUserVertex.removeAdmin("AdminUserConfigUri")
  }
  
  def afterAll() = {
    
  }
  
  "Diese Specification prueft das Hinzufuegen von ConfigUri zu der AdminUser" >> {
    val loginCS = Json.obj(
        "jsonId" -> 2,
        "dto" -> "Login"
        ,"params" -> Json.obj(
            "username" -> "AdminUserConfigUri",
            "password" -> "AdminUserConfigUri"
        )
    )
    
    val loginSC = handelMessage(loginCS)
    
    println(loginSC)
    "Login" >> {
      (loginSC \ "result" \ "status").asOpt[Boolean].get === true
    }
    "ConfigUri" >> {
      val configUriCS = Json.obj(
          "jsonId" -> DTOIds.configUri,
          "dto" -> DTONames.configUri,
          "params" -> Json.obj(
              "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
              "configUri" -> "http://contig/AdminUserConfigUri"
          )
      )
      val configUriSC = handelMessage(configUriCS)
      println("=> " + configUriSC)
      "result \\ status" >> {
        (configUriSC \ "result" \ "status").asOpt[Boolean].get === true
      }
      "result \\ message" >> {
        (configUriSC \ "result" \ "message").asOpt[String].get ===
          "Die URI = " + (configUriCS \ "params" \ "configUri").asOpt[String].get + " wurde fuer der AdminUser=" + 
          (loginSC \ "result" \ "adminId").asOpt[String].get + " hinzugefuegt"
      }
    }
  }
}