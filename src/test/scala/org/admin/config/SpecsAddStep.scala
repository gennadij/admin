package org.admin.config

import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.main.PrepareConfigForSpecs2
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames

class SpecsAddStep extends Specification with AdminWeb with BeforeAfterAll{
  
  def afterAll(): Unit = ???
  def beforeAll(): Unit = ???
  
  "Specification spezifiziert die Erzeugung von der Step" >> {
    val stepId: String = PrepareConfigForSpecs2.getFirstStep("user8")
    val componentIds: List[String] = PrepareConfigForSpecs2.getComponentsFromFirstStep(stepId)
  
    "Add Step" >> {
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
        "" === ""
      }
      "stepSC \\ dto" >> {
        "" === "" 
      }
      "stepSC \\ result \\ stepId" >> {
        "" === ""
      }
      "stepSC \\ result \\ status" >> {
        "" === ""
      }
      "stepSC \\ result \\ message" >> {
        "" === ""
      }
    }
  
  }
  
}