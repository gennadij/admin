package org.admin.config

import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.JsValue
import org.main.PrepareConfigForSpecs2
import org.persistence.db.orientdb.ComponentVertex


@RunWith(classOf[JUnitRunner])
class SpecsAddingNewComponent extends Specification with AdminWeb with BeforeAfterAll{
  //TODO hinten ConfigTree ausfueren um Hinzufuegen von COmponent prüfen
  def beforeAll() = {}
  def afterAll() = {
    val firstStepId = PrepareConfigForSpecs2.getFirstStep("user6")
    val count = ComponentVertex.deleteComponents(firstStepId)
    require(count == 4, "Anzahl der geloeschten Components " + count)
  }
  
  
  "Diese Specification spezifiziert das Hinzufügen von der Component zu dem Step (user6)" >> {
    "getFirstStep" >> {
      val firstStepId = PrepareConfigForSpecs2.getFirstStep("user6")
      "Component 1 fuer FirstStep hinzufuegen" >> {
        val componentCS = Json.obj(
          "dtoId" -> DTOIds.COMPONENT,
          "dto" -> DTONames.COMPONENT
          ,"params" -> Json.obj(
            "stepId" -> firstStepId,
            "kind" -> "immutable"
          )
        )
        val componentSC: JsValue = handelMessage(componentCS)
        "dtoId" >> {
          (componentSC \ "dtoId").asOpt[Int].get === DTOIds.COMPONENT
        }
        "dto" >> {
          (componentSC \ "dto").asOpt[String].get === DTONames.COMPONENT
        }
        "result \\ status" >> {
          (componentSC \ "result" \ "status").asOpt[Boolean].get === true
        }
        "result \\ message" >> {
          (componentSC \ "result" \ "message").asOpt[String].get === 
            "Die Komponente wurde hinzugefuegt"
        }
      }
      
      "Component 2 fuer FirstStep hinzufuegen" >> {
        val componentCS_1 = Json.obj(
          "dtoId" -> DTOIds.COMPONENT,
          "dto" -> DTONames.COMPONENT
          ,"params" -> Json.obj(
            "stepId" -> firstStepId,
            "kind" -> "immutable"
          )
        )
        val componentSC_1: JsValue = handelMessage(componentCS_1)
        "dtoId" >> {
          (componentSC_1 \ "dtoId").asOpt[Int].get === DTOIds.COMPONENT
        }
        "dto" >> {
          (componentSC_1 \ "dto").asOpt[String].get === DTONames.COMPONENT
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
          "dtoId" -> DTOIds.COMPONENT,
          "dto" -> DTONames.COMPONENT
          ,"params" -> Json.obj(
            "stepId" -> firstStepId,
            "kind" -> "immutable"
          )
        )
        val componentSC_2: JsValue = handelMessage(componentCS_2)
        "dtoId" >> {
          (componentSC_2 \ "dtoId").asOpt[Int].get === DTOIds.COMPONENT
        }
        "dto" >> {
          (componentSC_2 \ "dto").asOpt[String].get === DTONames.COMPONENT
        }
        "result \\ status" >> {
          (componentSC_2 \ "result" \ "status").asOpt[Boolean].get === true
        }
        "result \\ message" >> {
          (componentSC_2 \ "result" \ "message").asOpt[String].get === 
            "Die Komponente wurde hinzugefuegt"
        }
      }
      
      "Component 4 fuer FirstStep hinzufuegen" >> {
        val componentCS_3 = Json.obj(
          "dtoId" -> DTOIds.COMPONENT,
          "dto" -> DTONames.COMPONENT
          ,"params" -> Json.obj(
            "stepId" -> firstStepId,
            "kind" -> "immutable"
          )
        )
        val componentSC_3: JsValue = handelMessage(componentCS_3)
        "dtoId" >> {
          (componentSC_3 \ "dtoId").asOpt[Int].get === DTOIds.COMPONENT
        }
        "dto" >> {
          (componentSC_3 \ "dto").asOpt[String].get === DTONames.COMPONENT
        }
        "result \\ status" >> {
          (componentSC_3 \ "result" \ "status").asOpt[Boolean].get === true
        }
        "result \\ message" >> {
          (componentSC_3 \ "result" \ "message").asOpt[String].get === 
            "Die Komponente wurde hinzugefuegt"
        }
      }
    }
  }
}