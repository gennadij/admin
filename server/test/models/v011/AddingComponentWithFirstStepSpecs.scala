package models.v011

import org.specs2.specification.BeforeAfterAll
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.specs2.mutable.Specification
import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import org.genericConfig.admin.models.persistence.db.orientdb.ConfigVertex
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.genericConfig.admin.models.persistence.GlobalConfigForDB
import org.genericConfig.admin.models.persistence.TestDB
import play.api.libs.json.JsLookupResult.jsLookupResultToJsLookup
import play.api.libs.json.JsValue.jsValueToJsLookup
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import org.genericConfig.admin.models.json.StatusSuccessfulFirstStepCreated
import org.genericConfig.admin.models.json.StatusSuccessfulComponentCreated
import play.api.Logger
import org.genericConfig.admin.models.json.StatusSuccessfulLogin
import models.preparingConfigs.PrepareConfigsForSpecsv011
import org.genericConfig.admin.controllers.websocket.WebClient

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 16.01.2017
 * 
 * Username = user5
 */

@RunWith(classOf[JUnitRunner])
class AddingComponentWithFirstStepSpecs extends Specification 
                          with BeforeAfterAll{
  
  var userId = ""
  val wC = WebClient.init
  def beforeAll() = {
    userId = login
    PrepareConfigsForSpecsv011.prepareAddingComponentWithFirstStep(wC)
  }
  
  def afterAll() = {
    val count = ConfigVertex.deleteAllStepsAndComponent(userId)
    require(count == 2, "Anzahl der geloeschten Vertexes " + count)
  }
  
  "Diese Specification spezifiziert das Hinzufügen von der Component zu dem FirstStep user5" >> {
    "FirstStep -> Components hinzufuegen" >> {
      val firstStepCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_FIRST_STEP,
        "dto" -> DTONames.CREATE_FIRST_STEP
        ,"params" -> Json.obj(
          "configId" -> userId,
          "nameToShow" -> "FirstStep",
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      val firstStepSC: JsValue = wC.handleMessage(firstStepCS)
      
      (firstStepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_FIRST_STEP
      (firstStepSC \ "dto").asOpt[String].get === DTONames.CREATE_FIRST_STEP
      (firstStepSC \ "result" \ "status").asOpt[String].get === StatusSuccessfulFirstStepCreated.status
      (firstStepSC \ "result" \ "message").asOpt[String].get === StatusSuccessfulFirstStepCreated.message
      
      val componentCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_COMPONENT,
        "dto" -> DTONames.CREATE_COMPONENT
        ,"params" -> Json.obj(
          "stepId" -> (firstStepSC \ "result" \ "stepId").asOpt[String].get,
          "nameToShow" -> "Component",
          "kind" -> "immutable"
        )
      )
      val componentSC: JsValue = wC.handleMessage(componentCS)
      
      (componentSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
      (componentSC \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
      (componentSC \ "result" \ "status").asOpt[String].get === StatusSuccessfulComponentCreated.status
      (componentSC \ "result" \ "message").asOpt[String].get === StatusSuccessfulComponentCreated.message
    }
  }
  def login(): String = {
    val user = "user5"
      val jsonClientServer = Json.obj(
          "dtoId" -> DTOIds.LOGIN,
          "dto" -> DTONames.LOGIN
          ,"params" -> Json.obj(
              "username" -> user,
              "password"-> user
           )
      )
      val jsonServerClient: JsValue = wC.handleMessage(jsonClientServer)
      require((jsonServerClient \ "result" \ "status").asOpt[String].get == StatusSuccessfulLogin.status)
      ((jsonServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get
  }
}