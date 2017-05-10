package org.v012

import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import org.persistence.TestDBv012
import org.persistence.GlobalConfigForDB
import preparingConfigs.PreparingConfigsForTests
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json
import play.api.libs.json.JsValue

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 05.05.2017
	*
	* Username = user15
	*/
@RunWith(classOf[JUnitRunner])
class SpecsTwoFirstStepsForOneConfig extends Specification
	with AdminWeb
	with BeforeAfterAll {
  
  def beforeAll = {
    println("before")
    GlobalConfigForDB.setDb(new TestDBv012)
    PreparingConfigsForTests.prepareSpecsTwoFirstStepsForOneConfig
  }
	def afterAll = {}
	
	"Hier wird die Erzeugung von 2 FirstSteps innerhalb einer Config spezifiziert" >> {
    "2 FirstStep sind nicht erlaubt" >> {
      val firstStepCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_FIRST_STEP,
        "dto" -> DTONames.CREATE_FIRST_STEP
        ,"params" -> Json.obj(
          "configId" -> login,
          "nameToShow" -> "FirstStep",
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      val firstStepSC: JsValue = handelMessage(firstStepCS)
      (firstStepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_FIRST_STEP
      (firstStepSC \ "dto").asOpt[String].get === DTONames.CREATE_FIRST_STEP
      (firstStepSC \ "result" \ "status").asOpt[Boolean].get === false
      (firstStepSC \ "result" \ "message").asOpt[String].get === 
        "Der FirstStep exstiert bereits"
    }
  }
	def login(): String = {
    val user = "user15"
      val jsonClientServer = Json.obj(
          "dtoId" -> DTOIds.LOGIN,
          "dto" -> DTONames.LOGIN
          ,"params" -> Json.obj(
              "username" -> user,
              "password"-> user
           )
      )
      val jsonServerClient: JsValue = handelMessage(jsonClientServer)
      require((jsonServerClient \ "result" \ "status").asOpt[Boolean].get == true, 
          (jsonServerClient \ "result" \ "status").asOpt[Boolean].get)
      ((jsonServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get
  }
}