package org.admin.config

import org.specs2.specification.BeforeAfterAll
import org.admin.AdminWeb
import org.specs2.mutable.Specification
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import org.persistence.db.orientdb.ConfigVertex
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 16.01.2017
 */

@RunWith(classOf[JUnitRunner])
class SpecsAddingComponentWithFirstStep extends Specification 
                          with AdminWeb
                          with BeforeAfterAll{
  
  def beforeAll() = {}
  
  def afterAll() = {
    val count = ConfigVertex.deleteAllStepsAndComponent("#44:2")
    require(count == 2, "Anzahl der geloeschten Vertexes " + count)
  }
  
  "Diese Specification spezifiziert das Hinzufügen von der Component zu dem FirstStep" >> {
    "FirstStep hinzufuegen" >> {
      val firstStepCS = Json.obj(
        "dtoId" -> DTOIds.FIRST_STEP,
        "dto" -> DTONames.FIRST_STEP
        ,"params" -> Json.obj(
          "configId" -> "#44:2",
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      val firstStepSC: JsValue = handelMessage(firstStepCS)
      "dtoId" >> {
        (firstStepSC \ "dtoId").asOpt[Int].get === DTOIds.FIRST_STEP
      }
      "dto" >> {
        (firstStepSC \ "dto").asOpt[String].get === DTONames.FIRST_STEP
      }
      "result \\ status" >> {
        (firstStepSC \ "result" \ "status").asOpt[Boolean].get === true
      }
      "result \\ message" >> {
        (firstStepSC \ "result" \ "message").asOpt[String].get === 
          "Der erste Step wurde zu der Konfiguration hinzugefuegt"
      }
      "Component hinzufuegen" >> {
        val componentCS = Json.obj(
          "dtoId" -> DTOIds.COMPONENT,
          "dto" -> DTONames.COMPONENT
          ,"params" -> Json.obj(
            "stepId" -> (firstStepSC \ "result" \ "stepId").asOpt[String].get,
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
    }
  }
}