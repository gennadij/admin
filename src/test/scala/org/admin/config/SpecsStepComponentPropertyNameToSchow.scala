package org.admin.config

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.JsValue
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import play.api.libs.json.Json
import org.main.PrepareConfigForSpecs2
import org.persistence.db.orientdb.ComponentVertex
import org.persistence.db.orientdb.StepVertex
import preparingConfigs.PreparingConfigsForTests

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 05.03.2017
 * 
 * Username = user11
 * 
 * Linux
 * adminId #21:29
 * configId #41:14
 */

@RunWith(classOf[JUnitRunner])
class SpecsStepComponentPropertyNameToSchow extends Specification 
                          with AdminWeb
                          with BeforeAfterAll {
  var componentId = ""
  
  def afterAll(): Unit = {
    val firstStepId = preparingConfigs.PreparingConfigsForTests.getFirstStep("user11")
//    println("Component " + componentId)
    val countStep = StepVertex.deleteStepFromComponent(componentId)
    require(countStep == 1, "Anzahl der geloeschten Steps " + countStep)
    val countComponent = ComponentVertex.deleteComponents(firstStepId)
    require(countComponent == 3, "Anzahl der geloeschten Components " + countComponent)
    val countFirstStep = StepVertex.removeStep(login)
    require(countFirstStep == 1, "Anzahl der geloeschten Steps " + countFirstStep)
    
  }
  
  def beforeAll(): Unit = {
//    PreparingConfigsForTests.prepareSpecsStepComponentPropertyNameToSchow
  }
  
  "Specifikation spezifiziert neu Property <NameToShow>" >> {
    "FirstStep" >> {
      val firstStepCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_FIRST_STEP,
        "dto" -> DTONames.CREATE_FIRST_STEP
        ,"params" -> Json.obj(
          "configId" -> login,
          "nameToShow" -> "S_1",
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
//      println(firstStepCS)
      val firstStepSC: JsValue = handelMessage(firstStepCS)
//      println(firstStepSC)
      "dtoId" >> {
        (firstStepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_FIRST_STEP
      }
      "dto" >> {
        (firstStepSC \ "dto").asOpt[String].get === DTONames.CREATE_FIRST_STEP
      }
      "result \\ status" >> {
        (firstStepSC \ "result" \ "status").asOpt[Boolean].get === true
      }
      "result \\ message" >> {
        (firstStepSC \ "result" \ "message").asOpt[String].get === 
          "Der erste Step wurde zu der Konfiguration hinzugefuegt"
      }
      
      val firstStepId = (firstStepSC \ "result" \ "stepId").asOpt[String].get
      
//      println(firstStepId)
      "Component 1 fuer FirstStep hinzufuegen" >> {
        val componentCS = Json.obj(
          "dtoId" -> DTOIds.CREATE_COMPONENT,
          "dto" -> DTONames.CREATE_COMPONENT
          ,"params" -> Json.obj(
            "stepId" -> firstStepId,
            "nameToShow" -> "C_1_1",
            "kind" -> "immutable"
          )
        )
        val componentSC: JsValue = handelMessage(componentCS)
        "dtoId" >> {
          (componentSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
        }
        "dto" >> {
          (componentSC \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
        }
        "result \\ status" >> {
          (componentSC \ "result" \ "status").asOpt[Boolean].get === true
        }
        "result \\ message" >> {
          (componentSC \ "result" \ "message").asOpt[String].get === 
            "Die Komponente wurde hinzugefuegt"
        }
        
        componentId = (componentSC \ "result" \ "componentId").asOpt[String].get

        val stepCS = Json.obj(
            "dtoId" -> DTOIds.CREATE_STEP,
            "dto" -> DTONames.CREATE_STEP,
            "params" -> Json.obj(
                "componentId" -> componentId,
                "nameToShow" -> "S_2",
                "kind" -> "default",
                "selectionCriterium" -> Json.obj(
                    "min" -> 1,
                    "max" -> 1
                )
            )
        )
//        println(stepCS)
        val stepSC = handelMessage(stepCS)
//        println(stepSC)
        "stepCS \\ dtoId" >> {
          (stepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
        }
        "stepSC \\ dto" >> {
          (stepSC \ "dto").asOpt[String].get === DTONames.CREATE_STEP 
        }
        "stepSC \\ result \\ status" >> {
          (stepSC \ "result" \ "status").asOpt[Boolean].get === true
        }
        "stepSC \\ result \\ message" >> {
          (stepSC \ "result" \ "message").asOpt[String].get === 
            "Der Step wurde zu der Komponente hinzugefuegt"
        }
        "component(1) -> step_2" >> {
          val connectionComponentToStepCS = Json.obj(
              "dtoId" -> DTOIds.CONNECTION_COMPONENT_TO_STEP,
              "dto" -> DTONames.CONNECTION_COMPONENT_TO_STEP,
              "params" -> Json.obj(
                  "componentId" -> componentId,
                  "stepId" -> (stepSC \ "result" \ "stepId").asOpt[String].get
              )
          )
//          println(connectionComponentToStepCS)
          val connectionComponentToStepSC = handelMessage(connectionComponentToStepCS)
//          println(connectionComponentToStepSC)
          "connectionComponentToStepSC \\ dtoId" >> {
            (connectionComponentToStepSC \ "dtoId").asOpt[Int].get === DTOIds.CONNECTION_COMPONENT_TO_STEP
          }
          "connectionComponentToStepSC \\ dto" >> {
            (connectionComponentToStepSC \ "dto").asOpt[String].get === DTONames.CONNECTION_COMPONENT_TO_STEP
          }
          "connectionComponentToStepSC \\ result \\ status" >> {
            (connectionComponentToStepSC \ "result" \ "status").asOpt[Boolean].get === true
          }
          "connectionComponentToStepSC \\ result \\ message" >> {
            (connectionComponentToStepSC \ "result" \ "message").asOpt[String].get === 
              "Component mit dem Step wurde Verbunden"
          }
        }
      }
      "Component 2 fuer FirstStep hinzufuegen" >> {
        val componentCS_1 = Json.obj(
          "dtoId" -> DTOIds.CREATE_COMPONENT,
          "dto" -> DTONames.CREATE_COMPONENT
          ,"params" -> Json.obj(
            "stepId" -> firstStepId,
            "nameToShow" -> "C_1_2",
            "kind" -> "immutable"
          )
        )
        val componentSC_1: JsValue = handelMessage(componentCS_1)
        "dtoId" >> {
          (componentSC_1 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
        }
        "dto" >> {
          (componentSC_1 \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
        }
        "result \\ status" >> {
          (componentSC_1 \ "result" \ "status").asOpt[Boolean].get === true
        }
        "result \\ message" >> {
          (componentSC_1 \ "result" \ "message").asOpt[String].get === 
            "Die Komponente wurde hinzugefuegt"
        }
      }
      
      "Component 3 fuer FirstStep hinzufuegen" >> {
        val componentCS_2 = Json.obj(
          "dtoId" -> DTOIds.CREATE_COMPONENT,
          "dto" -> DTONames.CREATE_COMPONENT
          ,"params" -> Json.obj(
            "stepId" -> firstStepId,
            "nameToShow" -> "C_1_3",
            "kind" -> "immutable"
          )
        )
        val componentSC_2: JsValue = handelMessage(componentCS_2)
        "dtoId" >> {
          (componentSC_2 \ "dtoId").asOpt[Int].get === DTOIds.CREATE_COMPONENT
        }
        "dto" >> {
          (componentSC_2 \ "dto").asOpt[String].get === DTONames.CREATE_COMPONENT
        }
        "result \\ status" >> {
          (componentSC_2 \ "result" \ "status").asOpt[Boolean].get === true
        }
        "result \\ message" >> {
          (componentSC_2 \ "result" \ "message").asOpt[String].get === 
            "Die Komponente wurde hinzugefuegt"
        }
      }
      
    }
  }
    def login(): String = {
      val userPassword = "user11"
      val jsonClientServer = Json.obj(
          "dtoId" -> DTOIds.LOGIN,
          "dto" -> DTONames.LOGIN
          ,"params" -> Json.obj(
              "username" -> userPassword,
              "password"-> userPassword
           )
      )
      val jsonServerClient: JsValue = handelMessage(jsonClientServer)
      require((jsonServerClient \ "result" \ "status").asOpt[Boolean].get == true)
      ((jsonServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get
  }
}