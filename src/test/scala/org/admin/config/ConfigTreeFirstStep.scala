package org.admin.config

import org.admin.AdminWeb
import org.specs2.mutable.Specification
import play.api.libs.json.{JsValue, Json}
import org.persistence.db.orientdb.StepVertex
import org.specs2.specification.BeforeAfterAll
import org.dto.DTOIds
import org.dto.DTONames

/**
  * Created by gennadi on 16.11.16.
  */
class ConfigTreeFirstStep extends Specification 
                          with AdminWeb
                          with BeforeAfterAll{
  
  def beforeAll() = {
//    val loginSC = handelMessage(DTOConfigTree.loginCS)
//    println("remove Steps")
//    StepVertex.removerSteps((loginSC \ "result" \ "adminId").asOpt[String].get)
  }
  
  def afterAll() = {
    
  }

  
  "Diese Specification für die Erzeugung eines neuen Steps in der Konfiguration" >> {
//    val loginCS = Json.obj(
//        "jsonId" -> 2,
//        "dto" -> "Login"
//        ,"params" -> Json.obj(
//            "username" -> "firstStepConfigTree",
//            "password" -> "firstStepConfigTree"
//    )
//  )
//    val loginSC = handelMessage(loginCS)
//    "Login" >> {
//      (loginSC \ "result" \ "status").asOpt[Boolean].get === true
//    }
    "FirstStep anlegen" >> {
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
      val firstStepConfigTreeSC: JsValue = handelMessage(firstStepCS)
      "jsonId" >> {
        (firstStepConfigTreeSC \ "dtoId").asOpt[Int].get === DTOIds.FIRST_STEP
      }
      "dto" >> {
        (firstStepConfigTreeSC \ "dto").asOpt[String].get === DTONames.FIRST_STEP
      }
      "result \\ status" >> {
        (firstStepConfigTreeSC \ "result" \ "status").asOpt[Boolean].get === true
      }
      "result \\ message" >> {
        (firstStepConfigTreeSC \ "result" \ "message").asOpt[String].get === 
          "Der Step wurde hinzugefuegt"
      }
    }
//    "ConfigTree" >> {
////      {jsonId : 6, dto : ConfigTree, params: {adminId : #40:0}}
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
