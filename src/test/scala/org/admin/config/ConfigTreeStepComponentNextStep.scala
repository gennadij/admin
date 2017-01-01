package org.admin.config

import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import play.api.libs.json.Json
import play.api.libs.json.JsValue
import org.dto.DTONames
import org.dto.DTOIds
import org.persistence.db.orientdb.StepVertex
import org.persistence.db.orientdb.ComponentVertex

class ConfigTreeStepComponentNextStep 
    extends Specification
    with AdminWeb 
    with BeforeAfterAll{

  def beforeAll = {
    println("delete Step, Component and NextStep")
    val loginCS = Json.obj(
      "jsonId" -> DTOIds.login,
      "dto" -> DTONames.login
      ,"params" -> Json.obj(
        "username" -> "firstStep3ComponentNextStep",
        "password" -> "firstStep3ComponentNextStep"
      )
    )

    val loginSC = handelMessage(loginCS)
    StepVertex.removerSteps((loginSC \ "result" \ "adminId").asOpt[String].get)
    ComponentVertex.deleteComponent((loginSC \ "result" \ "adminId").asOpt[String].get)
  }
  
  def afterAll = {
  }
  
  
  
 "Diese Specification prueft die Erzeugung eines neuen Steps mit Componenten und zwei nextSteps" >> {
    val loginCS = Json.obj(
      "jsonId" -> DTOIds.login,
      "dto" -> DTONames.login
      ,"params" -> Json.obj(
        "username" -> "firstStep3ComponentNextStep",
        "password" -> "firstStep3ComponentNextStep"
      )
    )
    val loginSC = handelMessage(loginCS)
    println(s"Login        Client -> Server: $loginCS")
    println(s"Login        Server -> Client: $loginSC")
    "Login" >> {
      (loginSC \ "result" \ "status").asOpt[Boolean].get === true
    }
    "ConfigTree" >> {
      //      {jsonId: 7, dto : FirstStep, params : {adminId : #40:0, kind  : first}}
      val firstStepCS = Json.obj(
        "jsonId" -> DTOIds.step,
        "dto" -> DTONames.step 
        ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "first"
        )
      )
      val firstStepSC = handelMessage(firstStepCS)
      println(s"FirstStep    Client -> Server: $firstStepCS")
      println(s"FirstStep    Server -> Client: $firstStepSC")
      
      "firstStep -> jsonId" >> {
    	  (firstStepSC \ "jsonId").asOpt[Int].get === DTOIds.step
      }
      "firstStep -> dto" >> {
    	  (firstStepSC\ "dto").asOpt[String].get === DTONames.step
      }
      "firstStep -> result \\ status" >> {
        (firstStepSC \ "result" \ "status").asOpt[Boolean].get === true
      }
      "firstStep -> result \\ message" >> {
        (firstStepSC \ "result" \ "message").asOpt[String].get === 
          "Der Step wurde hinzugefuegt"
      }
      "Create 3 Components for FirstStep" >> {
        //{jsonId : 8, dto : Component, params : {adminId : #40:0, kind : immutable}
        val componentCS_1 = Json.obj(
          "jsonId" -> DTOIds.component,
          "dto" -> DTONames.component
          ,"params" -> Json.obj(
            "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
            "kind" -> "immutable"
          )
        )
        val componentSC_1 = handelMessage(componentCS_1)
        println(s"Component    Client -> Server: $componentCS_1")
        println(s"Component    Server -> Client: $componentSC_1")
        "component 1 -> jsonId" >> {
          (componentSC_1 \ "jsonId").asOpt[Int].get === DTOIds.component
        }
        "component 1 -> dto" >> {
          (componentSC_1 \ "dto").asOpt[String].get === DTONames.component
        }
        "component 1 -> result \\ status" >> {
          (componentSC_1 \ "result" \ "status").asOpt[Boolean].get === true
        }
        "component 1 -> result \\ message" >> {
          (componentSC_1 \ "result" \ "message").asOpt[String].get === 
            "Die Komponente wurde hinzugefuegt"
        }
        "Connect firstStep with Component 1" >> {
          //{jsonId : 9, dto : ConnStepToComponent, params : {adminId : 40:0, outStepId : #40:0, inComponentId : #40:0}}
          val connStepToComponnetCS_1: JsValue = Json.obj(
              "jsonId" -> DTOIds.connStepToComponen,
              "dto" -> DTONames.connSteptoComponent,
              "params" -> Json.obj(
                  "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
                  "outStepId" -> (firstStepSC \ "result" \ "stepId").asOpt[String].get,
                  "inComponentId" -> (componentSC_1 \ "result" \ "componentId").asOpt[String].get
              )
          )
          val connStepToComponentSC_1 = handelMessage(connStepToComponnetCS_1)
          println(s"ConnStepToComponent   Client -> Server: $connStepToComponnetCS_1")
          println(s"ConnStepToComponent   Server -> Client: $connStepToComponentSC_1")
          
          val stepOut_1 = (firstStepSC \ "result" \ "stepId").asOpt[String].get
          val componentOut_1 = (componentSC_1 \ "result" \ "componentId").asOpt[String].get
          "jsonId" >> {
            (connStepToComponentSC_1 \ "jsonId").asOpt[Int].get === DTOIds.connStepToComponen
          }
          "dto" >> {
            (connStepToComponentSC_1 \ "dto").asOpt[String].get === DTONames.connSteptoComponent
          }
          "result \\ status" >> {
            (connStepToComponentSC_1 \ "result" \ "status").asOpt[Boolean].get === true
          }
          "result \\ message" >> {
            (connStepToComponentSC_1 \ "result" \ "message").asOpt[String].get === 
              s"Der Step mit id=$stepOut_1 wurde mit der Komponente mit id=$componentOut_1 verbunden"
          }
        }
        "ConfigTree for Component_1" >> {
          //      {jsonId : 6, dto : ConfigTree, params: {adminId : #40:0}}
           val configTreeCS_2 = Json.obj(
             "jsonId" -> DTOIds.configTree,
             "dto" -> DTONames.configTree
             ,"params" -> Json.obj(
               "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get
             )
           )
           val configTreeSC_2 = handelMessage(configTreeCS_2)
           println(s"ConfigTree   Client -> Server: $configTreeCS_2")
           println(s"ConfigTree   Server -> Client: $configTreeSC_2")
          "jsonId" >> {
            (configTreeSC_2 \ "jsonId").asOpt[Int].get === DTOIds.configTree
          }
          "dto" >> {
            (configTreeSC_2 \ "dto").asOpt[String].get === DTONames.configTree
          }
          "result \\ steps.size" >> {
            (configTreeSC_2 \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
          }
          "result \\ steps(0) \\ kind" >> {
            (((configTreeSC_2 \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
          }
          "result \\ steps(0) \\ components" >> {
            (((configTreeSC_2 \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 1
          }
//            "result \\ steps(0) \\ components(0)" >> {
//              (((((configTreeSC_2 \ "result" \ "steps")(0)) \ "components")(0)) \ "componentId").asOpt[String].get === 
//                (componentSC_1 \ "result" \ "componentId").asOpt[String].get
//            }
          "result \\ steps(0) \\ components(0) \\ kind" >> {
            (((((configTreeSC_2 \ "result" \ "steps")(0)) \ "components")(0)) \ "kind").asOpt[String].get === 
              "immutable"
          }
        }
        //COMPONENT 2
        //{jsonId : 8, dto : Component, params : {adminId : #40:0, kind : immutable}
        val componentCS_2 = Json.obj(
          "jsonId" -> DTOIds.component,
          "dto" -> DTONames.component
          ,"params" -> Json.obj(
            "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
            "kind" -> "immutable"
          )
        )
        val componentSC_2 = handelMessage(componentCS_2)
        println(s"Component    Client -> Server: $componentCS_2")
        println(s"Component    Server -> Client: $componentSC_2")
        "component 2 -> jsonId" >> {
          (componentSC_2 \ "jsonId").asOpt[Int].get === DTOIds.component
        }
        "component 2 -> dto" >> {
          (componentSC_2 \ "dto").asOpt[String].get === DTONames.component
        }
        "component 2 -> result \\ status" >> {
          (componentSC_2 \ "result" \ "status").asOpt[Boolean].get === true
        }
        "component 2 -> result \\ message" >> {
          (componentSC_2 \ "result" \ "message").asOpt[String].get === 
            "Die Komponente wurde hinzugefuegt"
        }
        "Connect firstStep with Component 2" >> {
          //{jsonId : 9, dto : ConnStepToComponent, params : {adminId : 40:0, outStepId : #40:0, inComponentId : #40:0}}
          val connStepToComponnetCS_2: JsValue = Json.obj(
              "jsonId" -> DTOIds.connStepToComponen,
              "dto" -> DTONames.connSteptoComponent,
              "params" -> Json.obj(
                  "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
                  "outStepId" -> (firstStepSC \ "result" \ "stepId").asOpt[String].get,
                  "inComponentId" -> (componentSC_2 \ "result" \ "componentId").asOpt[String].get
              )
          )
          val connStepToComponentSC_2 = handelMessage(connStepToComponnetCS_2)
          println(s"ConnStepToComponent   Client -> Server: $connStepToComponnetCS_2")
          println(s"ConnStepToComponent   Server -> Client: $connStepToComponentSC_2")
          
          val stepOut_2 = (firstStepSC \ "result" \ "stepId").asOpt[String].get
          val componentOut_2 = (componentSC_2 \ "result" \ "componentId").asOpt[String].get
          "jsonId" >> {
            (connStepToComponentSC_2 \ "jsonId").asOpt[Int].get === DTOIds.connStepToComponen
          }
          "dto" >> {
            (connStepToComponentSC_2 \ "dto").asOpt[String].get === DTONames.connSteptoComponent
          }
          "result \\ status" >> {
            (connStepToComponentSC_2 \ "result" \ "status").asOpt[Boolean].get === true
          }
          "result \\ message" >> {
            (connStepToComponentSC_2 \ "result" \ "message").asOpt[String].get === 
              s"Der Step mit id=$stepOut_2 wurde mit der Komponente mit id=$componentOut_2 verbunden"
          }
        }
        "ConfigTree for Component_2" >> {
          //      {jsonId : 6, dto : ConfigTree, params: {adminId : #40:0}}
           val configTreeCS_3 = Json.obj(
             "jsonId" -> DTOIds.configTree,
             "dto" -> DTONames.configTree
             ,"params" -> Json.obj(
               "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get
             )
           )
           val configTreeSC_3 = handelMessage(configTreeCS_3)
           println(s"ConfigTree   Client -> Server: $configTreeCS_3")
           println(s"ConfigTree   Server -> Client: $configTreeSC_3")
          "jsonId" >> {
            (configTreeSC_3 \ "jsonId").asOpt[Int].get === DTOIds.configTree
          }
          "dto" >> {
            (configTreeSC_3 \ "dto").asOpt[String].get === DTONames.configTree
          }
          "result \\ steps.size" >> {
            (configTreeSC_3 \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
          }
          "result \\ steps(0) \\ kind" >> {
            (((configTreeSC_3 \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
          }
          "result \\ steps(0) \\ components" >> {
            (((configTreeSC_3 \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 2
          }
          "result \\ steps(0) \\ components(1)" >> {
            (((((configTreeSC_3 \ "result" \ "steps")(0)) \ "components")(1)) \ "componentId").asOpt[String].get === 
              (componentSC_2 \ "result" \ "componentId").asOpt[String].get
          }
          "result \\ steps(0) \\ components(1) \\ kind" >> {
            (((((configTreeSC_3 \ "result" \ "steps")(0)) \ "components")(1)) \ "kind").asOpt[String].get === 
              "immutable"
          }
        }
        //COMPONENT 3
        //{jsonId : 8, dto : Component, params : {adminId : #40:0, kind : immutable}
        val componentCS_3 = Json.obj(
          "jsonId" -> DTOIds.component,
          "dto" -> DTONames.component
          ,"params" -> Json.obj(
            "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
            "kind" -> "immutable"
          )
        )
        val componentSC_3 = handelMessage(componentCS_3)
        println(s"Component 3   Client -> Server: $componentCS_3")
        println(s"Component 3   Server -> Client: $componentSC_3")
        "component 3 -> jsonId" >> {
          (componentSC_3 \ "jsonId").asOpt[Int].get === DTOIds.component
        }
        "component 3 -> dto" >> {
          (componentSC_3 \ "dto").asOpt[String].get === DTONames.component
        }
        "component 3 -> result \\ status" >> {
          (componentSC_3 \ "result" \ "status").asOpt[Boolean].get === true
        }
        "component 3 -> result \\ message" >> {
          (componentSC_3 \ "result" \ "message").asOpt[String].get === 
            "Die Komponente wurde hinzugefuegt"
        }
        "Connect firstStep with Component 3" >> {
          //{jsonId : 9, dto : ConnStepToComponent, params : {adminId : 40:0, outStepId : #40:0, inComponentId : #40:0}}
          val connStepToComponnetCS_3: JsValue = Json.obj(
              "jsonId" -> DTOIds.connStepToComponen,
              "dto" -> DTONames.connSteptoComponent,
              "params" -> Json.obj(
                  "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
                  "outStepId" -> (firstStepSC \ "result" \ "stepId").asOpt[String].get,
                  "inComponentId" -> (componentSC_3 \ "result" \ "componentId").asOpt[String].get
              )
          )
          val connStepToComponentSC_3 = handelMessage(connStepToComponnetCS_3)
          println(s"ConnStepToComponent   Client -> Server: $connStepToComponnetCS_3")
          println(s"ConnStepToComponent   Server -> Client: $connStepToComponentSC_3")
          
          val stepOut_3 = (firstStepSC \ "result" \ "stepId").asOpt[String].get
          val componentOut_3 = (componentSC_3 \ "result" \ "componentId").asOpt[String].get
          "jsonId" >> {
            (connStepToComponentSC_3 \ "jsonId").asOpt[Int].get === DTOIds.connStepToComponen
          }
          "dto" >> {
            (connStepToComponentSC_3 \ "dto").asOpt[String].get === DTONames.connSteptoComponent
          }
          "result \\ status" >> {
            (connStepToComponentSC_3 \ "result" \ "status").asOpt[Boolean].get === true
          }
          "result \\ message" >> {
            (connStepToComponentSC_3 \ "result" \ "message").asOpt[String].get === 
              s"Der Step mit id=$stepOut_3 wurde mit der Komponente mit id=$componentOut_3 verbunden"
          }
        }
        "ConfigTree for Component_3" >> {
          //      {jsonId : 6, dto : ConfigTree, params: {adminId : #40:0}}
           val configTreeCS_4 = Json.obj(
             "jsonId" -> DTOIds.configTree,
             "dto" -> DTONames.configTree
             ,"params" -> Json.obj(
               "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get
             )
           )
           val configTreeSC_4 = handelMessage(configTreeCS_4)
           println(s"ConfigTree 4   Client -> Server: $configTreeCS_4")
           println(s"ConfigTree 4  Server -> Client: $configTreeSC_4")
          "jsonId" >> {
            (configTreeSC_4 \ "jsonId").asOpt[Int].get === DTOIds.configTree
          }
          "dto" >> {
            (configTreeSC_4 \ "dto").asOpt[String].get === DTONames.configTree
          }
          "result \\ steps.size" >> {
            (configTreeSC_4 \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
          }
          "result \\ steps(0) \\ kind" >> {
            (((configTreeSC_4 \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
          }
          "result \\ steps(0) \\ components" >> {
            (((configTreeSC_4 \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 3
          }
          "result \\ steps(0) \\ components(2)" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(2)) \ "componentId").asOpt[String].get === 
              (componentSC_3 \ "result" \ "componentId").asOpt[String].get
          }
          "result \\ steps(0) \\ components(2) \\ kind" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(2)) \ "kind").asOpt[String].get === 
              "immutable"
          }
        }
        //STEP 1
        //{jsonId : 10, dto : Step, params : {adminId : #40:0, kind : default}
        val stepCS_1 = Json.obj(
            "jsonId" -> DTOIds.step,
            "dto" -> DTONames.step,
            "params" -> Json.obj(
                "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
                "kind" -> "default"
            )
        )
        val stepSC_1 = handelMessage(stepCS_1)
        println(s"Step 1    Client -> Server: $stepCS_1")
        println(s"Step 1    Server -> Client: $stepSC_1")
        //{jsonId : 10, dto : Step, result : {stepId : #14:1", status : true, message : Nachricht}}
        "step 1 -> jsonId" >> {
          (stepSC_1 \ "jsonId").asOpt[Int].get === DTOIds.step
        }
        "step 1 -> dto" >> {
          (stepSC_1 \ "dto").asOpt[String].get == DTONames.step
        }
        "step 1 -> result \\ status" >> {
          (stepSC_1 \ "result" \ "status").asOpt[Boolean].get === true
        }
        "step 1 -> result \\ message" >> {
          (stepSC_1 \ "result" \ "message").asOpt[String].get === "Der Step wurde hinzugefuegt"
        }
        "Connect Component 1 -> Step 1" >> {
          //{jsonId : 11, dto : ConnComponentToStep, params : {adminId : #40:0, inStepId : #40:1, outComponentId : #40:2}}
          val connComponentToStepCS = Json.obj(
              "jsonId" -> DTOIds.connComponentToStep,
              "dto" -> DTONames.connComponentToStep,
              "params" -> Json.obj(
                  "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
                  "inStepId" -> (stepSC_1 \ "result" \ "stepId").asOpt[String].get,
                  "outComponentId" -> (componentSC_1 \ "result" \ "componentId").asOpt[String].get
              )
          )
          val connComponentToStepSC = handelMessage(connComponentToStepCS)
          println(s"ConnComponent1ToStep1    Client -> Server: $stepCS_1")
          println(s"ConnComponent1ToStep1    Server -> Client: $stepSC_1")
          //{jsonId : 11, dto : ConnComponentToStep, result : {status: true, message: Nachricht}}
          val inStepId_1 = (stepSC_1 \ "result" \ "stepId").asOpt[String].get
          val outComponentId_1 = (componentSC_1 \ "result" \ "componentId").asOpt[String].get
          
          "jsonId" >> {
            (connComponentToStepSC \ "jsonId").asOpt[Int].get === DTOIds.connComponentToStep
          }
          "dto" >> {
            (connComponentToStepSC \ "dto").asOpt[String].get === DTONames.connComponentToStep
          }
          "result \\ status" >> {
            (connComponentToStepSC \ "result" \ "status").asOpt[Boolean].get === true
          }
          "result \\ message" >> {
            (connComponentToStepSC \ "result" \ "message").asOpt[String].get === 
              s"Die Componnet mit id=$outComponentId_1 wurde mit Step mit id=$inStepId_1 verbunden"
          }
        }
        "Connect Component 2 -> Step 1" >> {
          //{jsonId : 11, dto : ConnComponentToStep, params : {adminId : #40:0, inStepId : #40:1, outComponentId : #40:2}}
          val connComponentToStepCS = Json.obj(
              "jsonId" -> DTOIds.connComponentToStep,
              "dto" -> DTONames.connComponentToStep,
              "params" -> Json.obj(
                  "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
                  "inStepId" -> (stepSC_1 \ "result" \ "stepId").asOpt[String].get,
                  "outComponentId" -> (componentSC_2 \ "result" \ "componentId").asOpt[String].get
              )
          )
          val connComponentToStepSC = handelMessage(connComponentToStepCS)
          println(s"ConnComponent2ToStep1    Client -> Server: $stepCS_1")
          println(s"ConnComponent2ToStep1    Server -> Client: $stepSC_1")
          //{jsonId : 11, dto : ConnComponentToStep, result : {status: true, message: Nachricht}}
          val inStepId_1 = (stepSC_1 \ "result" \ "stepId").asOpt[String].get
          val outComponentId_2 = (componentSC_2 \ "result" \ "componentId").asOpt[String].get
          "jsonId" >> {
            (connComponentToStepSC \ "jsonId").asOpt[Int].get === DTOIds.connComponentToStep
          }
          "dto" >> {
            (connComponentToStepSC \ "dto").asOpt[String].get === DTONames.connComponentToStep
          }
          "result \\ status" >> {
            (connComponentToStepSC \ "result" \ "status").asOpt[Boolean].get === true
          }
          "result \\ message" >> {
            (connComponentToStepSC \ "result" \ "message").asOpt[String].get === 
              s"Die Componnet mit id=$outComponentId_2 wurde mit Step mit id=$inStepId_1 verbunden"
          }
        }
        //STEP 2
        //{jsonId : 10, dto : Step, params : {adminId : #40:0, kind : default}
        val stepCS_2 = Json.obj(
            "jsonId" -> DTOIds.step,
            "dto" -> DTONames.step,
            "params" -> Json.obj(
                "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
                "kind" -> "default"
            )
        )
        val stepSC_2 = handelMessage(stepCS_2)
        println(s"Step 2    Client -> Server: $stepCS_2")
        println(s"Step 2    Server -> Client: $stepSC_2")
        //{jsonId : 10, dto : Step, result : {stepId : #14:1", status : true, message : Nachricht}}{jsonId : 10, dto : Step, result : {stepId : #14:1", status : true, message : Nachricht}}
        "jsonId" >> {
          (stepSC_2 \ "jsonId").asOpt[Int].get === DTOIds.step
        }
        "dto" >> {
          (stepSC_2 \ "dto").asOpt[String].get == DTONames.step
        }
        "result \\ status" >> {
          (stepSC_2 \ "result" \ "status").asOpt[Boolean].get === true
        }
        "result \\ message" >> {
          (stepSC_2 \ "result" \ "message").asOpt[String].get === "Der Step wurde hinzugefuegt"
        }
        "Connect Component 3 -> Step 2" >> {
          //{jsonId : 11, dto : ConnComponentToStep, params : {adminId : #40:0, inStepId : #40:1, outComponentId : #40:2}}
          val connComponentToStepCS = Json.obj(
              "jsonId" -> DTOIds.connComponentToStep,
              "dto" -> DTONames.connComponentToStep,
              "params" -> Json.obj(
                  "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
                  "inStepId" -> (stepSC_2 \ "result" \ "stepId").asOpt[String].get,
                  "outComponentId" -> (componentSC_3 \ "result" \ "componentId").asOpt[String].get
              )
          )
          val connComponentToStepSC = handelMessage(connComponentToStepCS)
          println(s"ConnComponent3ToStep2    Client -> Server: $stepCS_1")
          println(s"ConnComponent3ToStep2    Server -> Client: $stepSC_1")
          //{jsonId : 11, dto : ConnComponentToStep, result : {status: true, message: Nachricht}}
          
          val inStepId_2 = (stepSC_2 \ "result" \ "stepId").asOpt[String].get
          val outComponentId_3 = (componentSC_3 \ "result" \ "componentId").asOpt[String].get
          
          "jsonId" >> {
            (connComponentToStepSC \ "jsonId").asOpt[Int].get === DTOIds.connComponentToStep
          }
          "dto" >> {
            (connComponentToStepSC \ "dto").asOpt[String].get === DTONames.connComponentToStep
          }
          "result \\ status" >> {
            (connComponentToStepSC \ "result" \ "status").asOpt[Boolean].get === true
          }
          "result \\ message" >> {
            (connComponentToStepSC \ "result" \ "message").asOpt[String].get === 
              s"Die Componnet mit id=$outComponentId_3 wurde mit Step mit id=$inStepId_2 verbunden"
          }
        }
        "ConfigTree for Component_3" >> {
          //      {jsonId : 6, dto : ConfigTree, params: {adminId : #40:0}}
           val configTreeCS_4 = Json.obj(
             "jsonId" -> DTOIds.configTree,
             "dto" -> DTONames.configTree
             ,"params" -> Json.obj(
               "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get
             )
           )
           val configTreeSC_4 = handelMessage(configTreeCS_4)
           println(s"ConfigTree 4   Client -> Server: $configTreeCS_4")
           println(s"ConfigTree 4  Server -> Client: $configTreeSC_4")
          "jsonId" >> {
            (configTreeSC_4 \ "jsonId").asOpt[Int].get === DTOIds.configTree
          }
          "dto" >> {
            (configTreeSC_4 \ "dto").asOpt[String].get === DTONames.configTree
          }
          "result \\ steps.size" >> {
            (configTreeSC_4 \ "result" \ "steps").asOpt[List[JsValue]].get.size === 3
          }
          "result \\ steps(0) \\ stepId" >> {
            (((configTreeSC_4 \ "result" \ "steps")(0)) \ "stepId").asOpt[String].get === 
              (firstStepSC \ "result" \ "stepId").asOpt[String].get
          }
          "result \\ steps(0) \\ kind" >> {
            (((configTreeSC_4 \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
          }
          
          "result \\ steps(0) \\ components(0)" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(0)) \ "componentId").asOpt[String].get === 
              (componentSC_1 \ "result" \ "componentId").asOpt[String].get
          }
          "result \\ steps(0) \\ components(0) \\ kind" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(0)) \ "kind").asOpt[String].get === 
              "immutable"
          }
          "result \\ steps(0) \\ components(0) \\ nextStep" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(0)) \ "nextStep").asOpt[String].get === 
              (stepSC_1 \ "result" \ "stepId").asOpt[String].get
          }
          
          "result \\ steps(0) \\ components(1)" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(1)) \ "componentId").asOpt[String].get === 
              (componentSC_2 \ "result" \ "componentId").asOpt[String].get
          }
          "result \\ steps(0) \\ components(1) \\ kind" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(1)) \ "kind").asOpt[String].get === 
              "immutable"
          }
          "result \\ steps(0) \\ components(1) \\ nextStep" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(1)) \ "nextStep").asOpt[String].get === 
              (stepSC_1 \ "result" \ "stepId").asOpt[String].get
          }
          
          "result \\ steps(0) \\ components(2)" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(2)) \ "componentId").asOpt[String].get === 
              (componentSC_3 \ "result" \ "componentId").asOpt[String].get
          }
          "result \\ steps(0) \\ components(2) \\ kind" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(2)) \ "kind").asOpt[String].get === 
              "immutable"
          }
          "result \\ steps(0) \\ components(0) \\ nextStep" >> {
            (((((configTreeSC_4 \ "result" \ "steps")(0)) \ "components")(2)) \ "nextStep").asOpt[String].get === 
              (stepSC_2 \ "result" \ "stepId").asOpt[String].get
          }
          
          "result \\ steps(1) \\ stepId" >> {
            (((configTreeSC_4 \ "result" \ "steps")(1)) \ "stepId").asOpt[String].get === 
              (stepSC_1 \ "result" \ "stepId").asOpt[String].get
          }
          "result \\ steps(1) \\ kind" >> {
            (((configTreeSC_4 \ "result" \ "steps")(1)) \ "kind").asOpt[String].get === "default"
          }
          "result \\ steps(1) \\ components" >> {
            (((configTreeSC_4 \ "result" \ "steps")(1)) \ "components").asOpt[List[JsValue]].get.size === 0
          }
          
          "result \\ steps(2) \\ stepId" >> {
            (((configTreeSC_4 \ "result" \ "steps")(2)) \ "stepId").asOpt[String].get === 
              (stepSC_2 \ "result" \ "stepId").asOpt[String].get
          }
          "result \\ steps(2) \\ kind" >> {
            (((configTreeSC_4 \ "result" \ "steps")(2)) \ "kind").asOpt[String].get === "default"
          }
          "result \\ steps(2) \\ components" >> {
            (((configTreeSC_4 \ "result" \ "steps")(2)) \ "components").asOpt[List[JsValue]].get.size === 0
          }
        }
      }
    }
  }
}