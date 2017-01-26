package org.admin.config

import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.main.PrepareConfigForSpecs2
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.JsObject

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
      val stepSC = handelMessage(stepCS)
      "stepCS \\ dtoId" >> {
        (stepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
      }
      "stepSC \\ dto" >> {
        (stepSC \ "dto").asOpt[String] === DTONames.CREATE_STEP 
      }
      "stepSC \\ result \\ status" >> {
        (stepSC \ "result" \ "status").asOpt[Boolean].get === true
      }
      "stepSC \\ result \\ message" >> {
        (stepSC \ "result" \ "message").asOpt[String].get === ""
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
        val connectionComponentToStepSC = handelMessage(connectionComponentToStepCS)
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
      val stepSC = handelMessage(stepCS)
      "stepCS \\ dtoId" >> {
        (stepSC \ "dtoId").asOpt[Int].get === DTOIds.CREATE_STEP
      }
      "stepSC \\ dto" >> {
        (stepSC \ "dto").asOpt[String] === DTONames.CREATE_STEP 
      }
      "stepSC \\ result \\ status" >> {
        (stepSC \ "result" \ "status").asOpt[Boolean].get === true
      }
      "stepSC \\ result \\ message" >> {
        (stepSC \ "result" \ "message").asOpt[String].get === ""
      }
    }
  }
}