package org.admin.config

import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json
import org.persistence.db.orientdb.ConfigVertex

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 16.01.2017
 */

@RunWith(classOf[JUnitRunner])
class SpecsAddingNewConfig extends Specification 
                           with AdminWeb
                           with BeforeAfterAll{
  def beforeAll() = {}
  
  def afterAll() = {
    val count: Int = ConfigVertex.deleteConfigVertex("user5")
    require(count == 1, "Anzahl der geloeschten ConfigVertexes " + count)
    
  }
  
  "Diese Spezifikation erzeugt neue Konfiguration für die Admin" >> {
     val loginCS = Json.obj(
        "dtoId" -> DTOIds.LOGIN,
        "dto" -> DTONames.LOGIN
        ,"params" -> Json.obj(
            "username" -> "user5",
            "password" -> "user5"
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
      "result \\ status" >> {
    	  (createConfigSC \ "result" \ "status").asOpt[Boolean].get === true
        
      }
      "result \\ message" >> {
    	  (createConfigSC \ "result" \ "message").asOpt[String].get === "Die Konfiguration wurde erfolgreich erzeugt"
      }
    }
  }
}