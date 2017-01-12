package org.admin.config

import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds
import play.api.libs.json.JsValue
import org.persistence.db.orientdb.StepVertex
import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith


@RunWith(classOf[JUnitRunner])
class SpecsAddingFromFirstStep extends Specification 
                          with AdminWeb
                          with BeforeAfterAll{
  
  def beforeAll() = {
  }
  
  def afterAll() = {
    val count = StepVertex.removeStep("#43:1")
    println("Anzahl der geloeschten Steps " + count)
    count.pp
  }

  
  "Diese Specification spezifiziert das Hinzufügen von dem Step zu der Konfiguration" >> {
    "FirstStep hinzufuegen" >> {
      val firstStepCS = Json.obj(
        "dtoId" -> DTOIds.FIRST_STEP,
        "dto" -> DTONames.FIRST_STEP
        ,"params" -> Json.obj(
          "configId" -> "#43:1",
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
      
    }
    "Der zweite FirstStep hinzufuegen" >> {
      val twiceStepCS = Json.obj(
        "dtoId" -> DTOIds.FIRST_STEP,
        "dto" -> DTONames.FIRST_STEP
        ,"params" -> Json.obj(
          "configId" -> "#43:1",
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      val twiceStepConfigTreeSC: JsValue = handelMessage(twiceStepCS)
      "dtoId" >> {
        (twiceStepConfigTreeSC \ "dtoId").asOpt[Int].get === DTOIds.FIRST_STEP
      }
      "dto" >> {
        (twiceStepConfigTreeSC \ "dto").asOpt[String].get === DTONames.FIRST_STEP
      }
      "result \\ status" >> {
        (twiceStepConfigTreeSC \ "result" \ "status").asOpt[Boolean].get === false
      }
      "result \\ message" >> {
        (twiceStepConfigTreeSC \ "result" \ "message").asOpt[String].get === 
          "Der FirstStep exstiert bereits"
      }
    }
//    "ConfigTree" >> {
////      {jsonI : 6, dto : ConfigTree, params: {adminId : #40:0}}
//        val configTreeCS = Json.obj(
//          "jsonId" -> 6,
//          "dto" -> "ConfigTree"
//          ,"params" -> Json.obj(
//            "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get
//          )
//        )
//      val configTreeSC = handelMessage(configTreeCS)
//      "jsonId" >> {
//        (configTreeSC \ "jsonId").asOpt[Int].get === 6
//      }
//      "dto" >> {
//        (configTreeSC \ "dto").asOpt[String].get === "ConfigTree"
//      }
//      "result \\ steps(size)" >> {
//        (configTreeSC \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
//      }
//      "result \\ steps(0) \\ kind" >> {
//        (((configTreeSC \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
//      }
//      "result \\ steps(0) \\ components" >> {
//        (((configTreeSC \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 0
//      }
//    }
  }
}