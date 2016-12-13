package org.admin.config

import org.admin.AdminWeb
import org.specs2.mutable.Specification
import play.api.libs.json.{JsValue, Json}
import org.persistence.db.orientdb.StepVertex
import org.specs2.specification.BeforeAfterAll

/**
  * Created by gennadi on 16.11.16.
  */
class ConfigTreeFirstStep extends Specification 
                          with AdminWeb
                          with BeforeAfterAll{
  
  val loginSC = handelMessage(DTOConfigTree.loginCS)
  
  val firstStepConfigTreeCS = Json.obj(
    "jsonId" -> 4,
    "dto" -> "FirstStep" 
    ,"params" -> Json.obj(
      "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
      "kind" -> "first",
      "loginStatus" -> (loginSC \ "result" \ "status").asOpt[Boolean].get
    )
  )
  
  val configTreeCS = Json.obj(
    "jsonId" -> 6,
    "dto" -> "ConfigTree"
    ,"params" -> Json.obj(
      "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
      "loginStatus" -> (loginSC \ "result" \ "status").asOpt[Boolean].get
    )
  )
  
  def beforeAll() = {
    StepVertex.removerSteps((loginSC \ "result" \ "adminId").asOpt[String].get)
  }
  
  def afterAll() = {
    
  }

  
  "Diese Specification prueft die Erzeugung eines neuen Steps" >> {
    "Login" >> {
      (loginSC \ "result" \ "status").asOpt[Boolean].get === true
    }
    "FirstStep anlegen" >> {
      val firstStepConfigTreeSC = handelMessage(firstStepConfigTreeCS)
      "jsonId" >> {
        (firstStepConfigTreeSC \ "jsonId").asOpt[Int].get === 4
      }
      "dto" >> {
        (firstStepConfigTreeSC \ "dto").asOpt[String].get === "FirstStep"
      }
      "result \\ adminId" >> {
        (firstStepConfigTreeSC \ "result" \ "adminId").asOpt[String].get ===
                        (loginSC \ "result" \ "adminId").asOpt[String].get
      }
      "result \\ kind" >> {
        (firstStepConfigTreeSC \ "result" \ "kind").asOpt[String].get === "first"
      }
    }
    "ConfigTree" >> {
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
      "result \\ steps(0) \\ adminId" >> {
        (((configTreeSC \ "result" \ "steps")(0)) \ "adminId").asOpt[String].get === 
            (loginSC \ "result" \ "adminId").asOpt[String].get
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
