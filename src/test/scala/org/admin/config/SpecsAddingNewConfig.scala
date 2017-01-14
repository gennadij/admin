package org.admin.adminUser

import org.specs2.mutable.Specification
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.persistence.db.orientdb.AdminUserVertex
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SpecsAddingNewConfig extends Specification
                          with AdminWeb
                          with BeforeAfterAll{
  
  def beforeAll() = {
  }
  
  def afterAll() = {
    
  }
  
  "Diese Spezifikation erzeugt neue Konfiguration für die Admin" >> {
     val loginCS = Json.obj(
        "dtoId" -> DTOIds.LOGIN,
        "dto" -> DTONames.LOGIN
        ,"params" -> Json.obj(
            "username" -> "user3",
            "password" -> "user3"
        )
    )
    
    val loginSC = handelMessage(loginCS)
    "Login" >> {
      (loginSC \ "result" \ "status").asOpt[Boolean].get === true
    }
    "erzeuge neue Konfiguration" >> {
      val createConfigCS = Json.obj(
          "jsonId" -> DTOIds.CREATE_CONFIG,
          "dto" -> DTONames.CREATE_CONFIG
          , "params" -> Json.obj(
              "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
              "configUrl" -> "//http://contig1/user3"
          )
      )
      val createConfigSC = handelMessage(createConfigCS)
      "result \\ id" >> {
        (createConfigSC \ "result" \ "configId").asOpt[String].get === ""
      }
      "result \\ status" >> {
    	  (createConfigSC \ "result" \ "status").asOpt[Boolean].get === true
        
      }
      "result \\ message" >> {
    	  (createConfigSC \ "result" \ "message").asOpt[String].get === "Die Konfiguration wurde erfolgreich erzeugt"
      }
    }
  }
}