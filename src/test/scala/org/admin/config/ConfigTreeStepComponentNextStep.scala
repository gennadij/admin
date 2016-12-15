package org.admin.config

import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import play.api.libs.json.Json
import play.api.libs.json.JsValue

class ConfigTreeStepComponentNextStep 
    extends Specification
    with AdminWeb 
    with BeforeAfterAll{

  def beforeAll = {
    println("delete Step and Component")
//    StepVertex.removerSteps((loginSC \ "result" \ "adminId").asOpt[String].get)
//    ComponentVertex.deleteComponent((loginSC \ "result" \ "adminId").asOpt[String].get)
  }
  
  def afterAll = {
  }
  
  
  
    val loginCS = Json.obj(
    "jsonId" -> 2,
    "dto" -> "Login"
    ,"params" -> Json.obj(
      "username" -> "firstStepComponentNextStep",
      "password" -> "firstStepComponentNextStep"
    )
  )

  val loginSC = handelMessage(loginCS)
  
  val firstStepConfigTreeCS = Json.obj(
    "jsonId" -> 4,
    "dto" -> "FirstStep" 
    ,"params" -> Json.obj(
      "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
      "kind" -> "first",
      "loginStatus" -> (loginSC \ "result" \ "status").asOpt[Boolean].get
    )
  )
  
  val firstStepConfigTreeSC = handelMessage(firstStepConfigTreeCS)
  
  val configTreeCS = Json.obj(
    "jsonId" -> 6,
    "dto" -> "ConfigTree"
    ,"params" -> Json.obj(
      "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
      "loginStatus" -> (loginSC \ "result" \ "status").asOpt[Boolean].get
    )
  )
  
  val configTreeSC = handelMessage(configTreeCS)
  
    val componentCS = Json.obj(
      "jsonId" -> 5,
      "dto" -> "Component"
      ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "immutable"
          ,"stepId" -> (firstStepConfigTreeSC \ "result" \ "id").asOpt[String].get
      )
  )
  val componentSC = handelMessage(componentCS)
  
  val configTreeCS_1 = Json.obj(
    "jsonId" -> 6,
    "dto" -> "ConfigTree"
    ,"params" -> Json.obj(
      "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
      "loginStatus" -> (loginSC \ "result" \ "status").asOpt[Boolean].get
    )
  )
  
  val nextStepCS = Json.obj("" -> "")
  
  val nextStepSC = handelMessage(nextStepCS)
  
  val configTreeSC_1 = handelMessage(configTreeCS_1)
  
  println(s"Login        Client -> Server: $loginCS")
  println(s"Login        Server -> Client: $loginSC")
  println(s"FirstStep    Client -> Server: $firstStepConfigTreeCS")
  println(s"FirstStep    Server -> Client: $firstStepConfigTreeSC")
  println(s"ConfigTree   Cleint -> Server: $configTreeCS")
  println(s"ConfigTree   Server -> Client: $configTreeSC")
  println(s"Component    Client -> Server: $componentCS")
  println(s"Component    Server -> Client: $componentSC")
  println(s"ConfigTree   Client -> Server: $configTreeCS_1")
  println(s"ConfigTree   Server -> Client: $configTreeSC_1")
  println(s"NextStep     Client -> Server: ")
  println(s"NextStep     Server -> Client: ")
  
  
  
  "Diese Specification prueft die Erzeugung eines neuen Steps mit Componenten" >> {
    "Login" >> {
      (loginSC \ "result" \ "status").asOpt[Boolean].get === true
    }
    "FirstStep" >> {
      "jsonId" >> {
    	  (firstStepConfigTreeSC \ "jsonId").asOpt[Int].get === 4
      }
      "dto" >> {
    	  (firstStepConfigTreeSC\ "dto").asOpt[String].get === "FirstStep"
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
      
      "jsonId" >> {
        (configTreeSC \ "jsonId").asOpt[Int].get === 6
      }
      "dto" >> {
        (configTreeSC \ "dto").asOpt[String].get === "ConfigTree"
      }
      "result \\ steps.size" >> {
        (configTreeSC \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
      }
      "result \\ steps(0)" >> {
        (((configTreeSC \ "result" \ "steps")(0)) \ "adminId").asOpt[String].get === 
          (loginSC \ "result" \ "adminId").asOpt[String].get
      }
      "result \\ steps(0) \\ kind" >> {
        (((configTreeSC \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
      }
      "result \\ steps(0) \\ components.size" >> {
        (((configTreeSC \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 0
      }
    }
    "Component" >> {
      "jsonId" >> {
        (componentSC \ "jsonId").asOpt[Int].get === 5
      }
      "dto" >> {
        (componentSC \ "dto").asOpt[String].get === "Component"
      }
      "result \\ adminId" >> {
        (componentSC \ "result" \ "adminId").asOpt[String].get === 
          (loginSC \ "result" \ "adminId").asOpt[String].get
      }
      "result \\ kind" >> {
        (componentSC \ "result" \ "kind").asOpt[String].get === "immutable"
      }
      "result \\ stepId" >> {
        (componentSC \ "result" \ "stepId").asOpt[String].get === 
          (firstStepConfigTreeSC \ "result" \ "id").asOpt[String].get
      }
    }
    "ConfigTree" >> {
      "jsonId" >> {
        (configTreeSC_1 \ "jsonId").asOpt[Int].get === 6
      }
      "dto" >> {
        (configTreeSC_1 \ "dto").asOpt[String].get === "ConfigTree"
      }
      "result \\ steps.size" >> {
        (configTreeSC_1 \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
      }
      "result \\ steps(0) \\ adminId" >> {
        (((configTreeSC_1 \ "result" \ "steps")(0)) \ "adminId").asOpt[String].get === 
          (loginSC \ "result" \ "adminId").asOpt[String].get
      }
      "result \\ steps(0) \\ kind" >> {
        (((configTreeSC_1 \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
      }
      "result \\ steps(0) \\ components" >> {
        (((configTreeSC_1 \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 1
      }
      "result \\ steps(0) \\ components(0)" >> {
        (((((configTreeSC_1 \ "result" \ "steps")(0)) \ "components")(0)) \ "id").asOpt[String].get === 
          (componentSC \ "result" \ "id").asOpt[String].get
      }
      "result \\ steps(0) \\ components(0) \\ componentId" >> {
        (((((configTreeSC_1 \ "result" \ "steps")(0)) \ "components")(0)) \ "componentId").asOpt[String].get === 
          (componentSC \ "result" \ "componentId").asOpt[String].get
      }
      "result \\ steps(0) \\ components(0) \\ adminId" >> {
        (((((configTreeSC_1 \ "result" \ "steps")(0)) \ "components")(0)) \ "adminId").asOpt[String].get === 
          (componentSC \ "result" \ "adminId").asOpt[String].get
      }
      "result \\ steps(0) \\ components(0) \\ kind" >> {
        (((((configTreeSC_1 \ "result" \ "steps")(0)) \ "components")(0)) \ "kind").asOpt[String].get === 
          (componentSC \ "result" \ "kind").asOpt[String].get
      }
    }
    
  }
  
  
}