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
    val loginSC = handelMessage(DTOConfigTree.loginCS)
    println("remove Steps")
    StepVertex.removerSteps((loginSC \ "result" \ "adminId").asOpt[String].get)
  }
  
  def afterAll() = {
    
  }

  
  "Diese Specification prueft die Erzeugung eines neuen Steps" >> {
    val loginSC = handelMessage(DTOConfigTree.loginCS)
    "Login" >> {
      (loginSC \ "result" \ "status").asOpt[Boolean].get === true
    }
    "FirstStep anlegen" >> {
//      {jsonId: 7, dto : FirstStep, params : {adminId : #40:0, kind  : first}}
      val firstStepConfigTreeCS = Json.obj(
        "jsonId" -> 7,
        "dto" -> "Step" 
        ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      val firstStepConfigTreeSC: JsValue = handelMessage(firstStepConfigTreeCS)
      "jsonId" >> {
        (firstStepConfigTreeSC \ "jsonId").asOpt[Int].get === DTOIds.step
      }
      "dto" >> {
        (firstStepConfigTreeSC \ "dto").asOpt[String].get === DTONames.step
      }
      "result \\ status" >> {
        (firstStepConfigTreeSC \ "result" \ "status").asOpt[Boolean].get === true
      }
      "result \\ message" >> {
        (firstStepConfigTreeSC \ "result" \ "message").asOpt[String].get === 
          "Der Step wurde hinzugefuegt"
      }
    }
    "ConfigTree" >> {
//      {jsonId : 6, dto : ConfigTree, params: {adminId : #40:0}}
        val configTreeCS = Json.obj(
          "jsonId" -> 6,
          "dto" -> "ConfigTree"
          ,"params" -> Json.obj(
            "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get
          )
        )
      val configTreeSC = handelMessage(configTreeCS)
      "jsonId" >> {
        (configTreeSC \ "jsonId").asOpt[Int].get === 6
      }
      "dto" >> {
        (configTreeSC \ "dto").asOpt[String].get === "ConfigTree"
      }
      "result \\ steps(size)" >> {
        (configTreeSC \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
      }
      "result \\ steps(0) \\ kind" >> {
        (((configTreeSC \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
      }
      "result \\ steps(0) \\ components" >> {
        (((configTreeSC \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 0
      }
    }
  }
}
