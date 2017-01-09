package org.admin.adminUser

import org.specs2.mutable.Specification
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll

class CreateConfig extends Specification
                          with AdminWeb
                          with BeforeAfterAll{
  
  def beforeAll() = {
//    AdminUserVertex.removeAdmin("AdminUserConfigUri")
  }
  
  def afterAll() = {
    
  }
  
  "Diese Spezifikation erzeugt neue Konfiguration für die Admin" >> {
     val loginCS = Json.obj(
        "jsonId" -> DTOIds.login,
        "dto" -> DTONames.login
        ,"params" -> Json.obj(
            "username" -> "CreateConfig",
            "password" -> "CreateConfig"
        )
    )
    
    val loginSC = handelMessage(loginCS)
    "Login" >> {
      (loginSC \ "result" \ "status").asOpt[Boolean].get === true
    }
    "erzeuge neue Konfiguration" >> {
      val createConfigCS = Json.obj(
          "jsonId" -> DTOIds.createConfig,
          "dto" -> DTONames.createConfig
          , "params" -> Json.obj(
              "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
              "configUrl" -> "//http://contig/AdminUserConfigUri"
          )
      )
      val createConfigSC = handelMessage(createConfigCS)
      "result \\ id" >> {
        (createConfigSC \ "result" \ "id").asOpt[String].get.size === 5
      }
      "result \\ status" >> {
    	  (createConfigSC \ "result" \ "status").asOpt[Boolean].get === true
        
      }
      "result \\ message" >> {
    	  (createConfigSC \ "result" \ "message").asOpt[String].get === "Die Konfiguration wurde erfolgreich erzeugt"
      }
      "verbinde AdminUser mit AdminUser" >> {
        
      }
    }
  }
}