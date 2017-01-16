package org.admin.register

import play.api.libs.json._
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.persistence.db.orientdb.AdminUserVertex
import org.dto.DTOIds
import org.dto.DTONames
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import org.specs2.mutable.Specification

/**
 * userExist -> (nicht nach dem Test geloescht) in SpecsAddingAlredyExistingAdminUser
 * user1 -> new User (nach dem Test geloescht) in SpecsAddingNewAdminUser
 * user2 -> login (AdminUser nicht geloescht) in SpecsLogin
 * user -> login (nicht exsistiert in DB) in SpecsLoginWithNotExistUser
 * user5 -> new Config (AdminUser nicht geloescht, Config wird geloescht) in SpecsAddingNewConfig
 * user2 -> login (Config nicht geloescht, FirstStep geloescht) in SpecsAddingFirstStep
 * user3 -> new Component (Adminuser nicht geloescht, Config nicht geloescht, FirstStep geloescht, Component geloescht) in SpecsAddingComponent
 * user4 -> ConfigTree () in SpecsConfigTree
 * user6 -> new 3 Components () in SpecsAddingNewComponent
 * 
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.01.2017
 */

@RunWith(classOf[JUnitRunner])
class SpecsAddingNewAdminUser extends Specification 
                                with AdminWeb 
                                with BeforeAfterAll{

  def beforeAll() = {
  }
  
  def afterAll() = {
    val count = AdminUserVertex.deleteAdmin("user1")
    require(count == 1, "Anzahl der geloescten AdminUserVertexes " + count)
  }
  
  "Specification spezifiziert die Registrierung des neuen Users" >> {
    "Das Hinzufuegen des nicht exstierenden/neuen User" >> {
      val registerCS = Json.obj(
          "dtoId" -> DTOIds.REGISTRATION,
          "dto" -> DTONames.REGISTRATION
          ,"params" -> Json.obj(
               "username" -> "user1",
               "password"-> "user1"
           )
      )
      val registerSC = handelMessage(registerCS)
      "dtoId" >> {
        (registerSC \ "dtoId").asOpt[Int].get === DTOIds.REGISTRATION
      }
      "dto" >> {
        (registerSC \ "dto").asOpt[String].get === DTONames.REGISTRATION
      }
      "username" >> {
        (registerSC \ "result" \ "username").asOpt[String].get must_== "user1"
      }
      "status" >> {
        (registerSC \ "result" \ "status").asOpt[Boolean].get must_== true
      }
      "message" >> {
        (registerSC \ "result" \ "message").asOpt[String].get must_== "Registrierung war erfolgreich"
      }
    }
  }
}