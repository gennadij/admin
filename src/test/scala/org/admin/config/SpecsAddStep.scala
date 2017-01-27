package org.admin.config

import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.main.PrepareConfigForSpecs2
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.JsObject
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.01.2016
 */
@RunWith(classOf[JUnitRunner])
class SpecsAddStep extends Specification with AdminWeb with BeforeAfterAll{
  
  def afterAll(): Unit = ???
  def beforeAll(): Unit = ???
  
  "Specification spezifiziert die Erzeugung von der Step" >> {
    val stepId: String = PrepareConfigForSpecs2.getFirstStep("user8")
    val componentIds: List[String] = PrepareConfigForSpecs2.getComponentsFromFirstStep(stepId)
  
    "component(0) -> step_1" >> {
      val stepCS = Json.obj(
          "dtoId" -> DTOIds.CREATE_STEP,
          "dto" -> DTONames.CREATE_STEP,
          "params" -> Json.obj(
              "componentId" -> componentIds(0),
              "kind" -> "default",
              "selectionCriterium" -> Json.obj(
                  "min" -> 1,
                  "max" -> 1
              )
              
          )
      )
      println(stepCS)
      val stepSC = handelMessage(stepCS)
      println(stepSC)
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
      "component(1) -> step_1" >> {
        val connectionComponentToStepCS = Json.obj(
            "dtoId" -> DTOIds.CONNECTION_COMPONENT_TO_STEP,
            "dto" -> DTONames.CONNECTION_COMPONENT_TO_STEP,
            "params" -> Json.obj(
                "componentId" -> componentIds(1),
                "stepId" -> (stepSC \ "result" \ "stepId").asOpt[String].get
            )
        )
        println(connectionComponentToStepCS)
        val connectionComponentToStepSC = handelMessage(connectionComponentToStepCS)
        println(connectionComponentToStepSC)
        "connectionComponentToStepSC \\ dtoId" >> {
          (connectionComponentToStepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
        }
        "connectionComponentToStepSC \\ dto" >> {
          (connectionComponentToStepSC \ "dto").asOpt[String] === DTONames.CREATE_STEP 
        }
        "connectionComponentToStepSC \\ result \\ status" >> {
          (connectionComponentToStepSC \ "result" \ "status").asOpt[Boolean].get === true
        }
        "connectionComponentToStepSC \\ result \\ message" >> {
          (connectionComponentToStepSC \ "result" \ "message").asOpt[String].get === ""
        }
      }
    }
    "component(2) -> step_1" >> {
      val stepCS = Json.obj(
          "dtoId" -> DTOIds.CREATE_STEP,
          "dto" -> DTONames.CREATE_STEP,
          "params" -> Json.obj(
              "componentId" -> componentIds(2),
              "kind" -> "default",
              "selectionCriterium" -> Json.obj(
                  "min" -> 1,
                  "max" -> 1
              )
              
          )
      )
      println(stepCS)
      val stepSC = handelMessage(stepCS)
      println(stepSC)
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
        (stepSC \ "result" \ "message").asOpt[String].get === "Der Step wurde zu der Komponente hinzugefuegt"
      }
    }
  }
}