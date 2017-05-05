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
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.01.2017
 * 
 * Username = userExist
 */

@RunWith(classOf[JUnitRunner])
class SpecsAddingAlredyExistingAdminUser extends Specification with AdminWeb with BeforeAfterAll{

  def beforeAll() = {
//    preparingConfigs.PreparingConfigsForTests.prepareWithAlredyExistingUser
  }
  
  def afterAll() = {
  }
  
  "Specification spezifiziert die Registrierung des exestierenden Users" >> {
    "schon exstierenden User hinzufuegen" >> {
      val registerCS = Json.obj(
          "dtoId" -> DTOIds.REGISTRATION,
          "dto" -> DTONames.REGISTRATION
          ,"params" -> Json.obj(
               "username" -> "userExist",
               "password"-> "userExist"
           )
      )
      val registerSC = handelMessage(registerCS)
      println(registerCS)
      println(registerSC)
      "dtoId" >> {
        (registerSC \ "dtoId").asOpt[Int].get === DTOIds.REGISTRATION
      }
      "dto" >> {
        (registerSC \ "dto").asOpt[String].get === DTONames.REGISTRATION
      }
      "username" >> {
        (registerSC \ "result" \ "username").asOpt[String].get must_== "userExist"
      }
      "status" >> {
        (registerSC \ "result" \ "status").asOpt[Boolean].get must_== false
      }
      "message" >> {
        (registerSC \ "result" \ "message").asOpt[String].get must_== 
          "Registrierung war nicht erfolgreich. Username existiert bereits"
      }
    }
  }
}