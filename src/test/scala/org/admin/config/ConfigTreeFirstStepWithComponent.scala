package org.admin.config

import org.admin.AdminWeb
import org.specs2.mutable.Specification
import play.api.libs.json.{JsValue, Json}
import org.persistence.db.orientdb.StepVertex
import org.specs2.mutable.Before
import org.specs2.specification.BeforeAfterAll
import org.persistence.db.orientdb.ComponentVertex
import org.dto.DTOIds
import org.dto.DTONames

class ConfigTreeFirstStepWithComponent 
    extends Specification
    with AdminWeb 
    with BeforeAfterAll{

  def beforeAll = {
    println("delete Step and Component")
    val loginCS = Json.obj(
      "jsonId" -> 2,
      "dto" -> "Login"
      ,"params" -> Json.obj(
        "username" -> "firstStepComponent",
        "password" -> "firstStepComponent"
      )
    )

    val loginSC = handelMessage(loginCS)
    StepVertex.removerSteps((loginSC \ "result" \ "adminId").asOpt[String].get)
    ComponentVertex.deleteComponent((loginSC \ "result" \ "adminId").asOpt[String].get)
  }
  
  def afterAll = {}
  
  "Diese Specification prueft die Erzeugung eines neuen Steps mit Componenten" >> {
    val loginCS = Json.obj(
      "jsonId" -> DTOIds.login,
      "dto" -> DTONames.login
      ,"params" -> Json.obj(
        "username" -> "firstStepComponent",
        "password" -> "firstStepComponent"
      )
    )
    val loginSC = handelMessage(loginCS)
    println(s"Login        Client -> Server: $loginCS")
    println(s"Login        Server -> Client: $loginSC")
    "Login" >> {
      (loginSC \ "result" \ "status").asOpt[Boolean].get === true
    }
    "FirstStep" >> {
      //      {jsonId: 7, dto : FirstStep, params : {adminId : #40:0, kind  : first}}
      val firstStepConfigTreeCS = Json.obj(
        "jsonId" -> DTOIds.step,
        "dto" -> DTONames.step 
        ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      val firstStepConfigTreeSC = handelMessage(firstStepConfigTreeCS)
      println(s"FirstStep    Client -> Server: $firstStepConfigTreeCS")
      println(s"FirstStep    Server -> Client: $firstStepConfigTreeSC")
      
      "jsonId" >> {
    	  (firstStepConfigTreeSC \ "jsonId").asOpt[Int].get === DTOIds.step
      }
      "dto" >> {
    	  (firstStepConfigTreeSC\ "dto").asOpt[String].get === DTONames.step
      }
      "result \\ status" >> {
        (firstStepConfigTreeSC \ "result" \ "status").asOpt[Boolean].get === true
      }
      "result \\ message" >> {
        (firstStepConfigTreeSC \ "result" \ "message").asOpt[String].get === 
          "Der Step wurde hinzugefuegt"
      }
      "ConfigTree" >> {
  //      {jsonId : 6, dto : ConfigTree, params: {adminId : #40:0}}
        val configTreeCS = Json.obj(
          "jsonId" -> DTOIds.configTree,
          "dto" -> DTONames.configTree
          ,"params" -> Json.obj(
            "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get
          )
        )
        val configTreeSC = handelMessage(configTreeCS)
        println(s"ConfigTree   Cleint -> Server: $configTreeCS")
        println(s"ConfigTree   Server -> Client: $configTreeSC")
        
        "jsonId" >> {
          (configTreeSC \ "jsonId").asOpt[Int].get === 6
        }
        "dto" >> {
          (configTreeSC \ "dto").asOpt[String].get === "ConfigTree"
        }
        "result \\ steps.size" >> {
          (configTreeSC \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
        }
        "result \\ steps(0) \\ kind" >> {
          (((configTreeSC \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
        }
        "result \\ steps(0) \\ components.size" >> {
          (((configTreeSC \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 0
        }
      }
      "Component" >> {
  //    {jsonId : 8, dto : Component, params : {adminId : #40:0, kind : immutable}
        val componentCS = Json.obj(
          "jsonId" -> DTOIds.component,
          "dto" -> DTONames.component
          ,"params" -> Json.obj(
            "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
            "kind" -> "immutable"
          )
        )
        val componentSC = handelMessage(componentCS)
        println(s"Component    Client -> Server: $componentCS")
        println(s"Component    Server -> Client: $componentSC")
        "jsonId" >> {
          (componentSC \ "jsonId").asOpt[Int].get === DTOIds.component
        }
        "dto" >> {
          (componentSC \ "dto").asOpt[String].get === DTONames.component
        }
        "result \\ status" >> {
          (componentSC \ "result" \ "status").asOpt[Boolean].get === true
        }
        "result \\ message" >> {
          (componentSC \ "result" \ "message").asOpt[String].get === 
            "Die Komponente wurde hinzugefuegt"
        }
        "ConnSteptoComponent" >> {
    //      {jsonId : 9, dto : ConnStepToComponent, params : {adminId : 40:0, outStepId : #40:0, inComponentId : #40:0}}
          val connStepToComponnetCS: JsValue = Json.obj(
              "jsonId" -> DTOIds.connStepToComponen,
              "dto" -> DTONames.connSteptoComponent,
              "params" -> Json.obj(
                  "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
                  "outStepId" -> (firstStepConfigTreeSC \ "result" \ "stepId").asOpt[String].get,
                  "inComponentId" -> (componentSC \ "result" \ "componentId").asOpt[String].get
              )
          )
          println(connStepToComponnetCS)
          val connStepToComponentSC = handelMessage(connStepToComponnetCS)
          println(s"ConnStepToComponent   Client -> Server: $connStepToComponnetCS")
          println(s"ConnStepToComponent   Server -> Client: $connStepToComponentSC")
          
          val stepOut = (firstStepConfigTreeSC \ "result" \ "stepId").asOpt[String].get
          val componentOut = (componentSC \ "result" \ "componentId").asOpt[String].get
          "jsonId" >> {
            (connStepToComponentSC \ "jsonId").asOpt[Int].get === DTOIds.connStepToComponen
          }
          "dto" >> {
            (connStepToComponentSC \ "dto").asOpt[String].get === DTONames.connSteptoComponent
          }
          "result \\ status" >> {
            (connStepToComponentSC \ "result" \ "status").asOpt[Boolean].get === true
          }
          "result \\ message" >> {
            (connStepToComponentSC \ "result" \ "message").asOpt[String].get === 
              s"Der Step mit id=$stepOut wurde mit der Komponente mit id=$componentOut verbunden"
          }
        }
        "ConfigTree" >> {
          //      {jsonId : 6, dto : ConfigTree, params: {adminId : #40:0}}
           val configTreeCS_1 = Json.obj(
             "jsonId" -> DTOIds.configTree,
             "dto" -> DTONames.configTree
             ,"params" -> Json.obj(
               "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get
             )
           )
           val configTreeSC_1 = handelMessage(configTreeCS_1)
           println(s"ConfigTree   Client -> Server: $configTreeCS_1")
           println(s"ConfigTree   Server -> Client: $configTreeSC_1")
          "jsonId" >> {
            (configTreeSC_1 \ "jsonId").asOpt[Int].get === DTOIds.configTree
          }
          "dto" >> {
            (configTreeSC_1 \ "dto").asOpt[String].get === DTONames.configTree
          }
          "result \\ steps.size" >> {
            (configTreeSC_1 \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
          }
          "result \\ steps(0) \\ kind" >> {
            (((configTreeSC_1 \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
          }
          "result \\ steps(0) \\ components" >> {
            (((configTreeSC_1 \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 1
          }
          "result \\ steps(0) \\ components(0)" >> {
            (((((configTreeSC_1 \ "result" \ "steps")(0)) \ "components")(0)) \ "componentId").asOpt[String].get === 
              (componentSC \ "result" \ "componentId").asOpt[String].get
          }
          "result \\ steps(0) \\ components(0) \\ kind" >> {
            (((((configTreeSC_1 \ "result" \ "steps")(0)) \ "components")(0)) \ "kind").asOpt[String].get === 
              "immutable"
          }
        }
      }
    }
  }
}