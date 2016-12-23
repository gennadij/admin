package org.main

import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import org.admin.AdminWeb
import play.api.libs.json.JsValue

class Config extends AdminWeb{
  
  //###Login
  val loginCS = Json.obj(
      "jsonId" -> DTOIds.login,
      "dto" -> DTONames.login
      ,"params" -> Json.obj(
          "username" -> "config_3",
          "password" -> "config_3"
      )
  )
    
  val loginSC = handelMessage(loginCS)
  
  println(s"Login        Client -> Server: $loginCS")
  println(s"Login        Server -> Client: $loginSC")
  
  //###First Step
  val firstStepCS = Json.obj(
      "jsonId" -> DTOIds.firstStep,
      "dto" -> DTONames.firstStep 
      ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "first"
      )
  )
  
  val firstStepSC = handelMessage(firstStepCS)
  println(s"FirstStep    Client -> Server: $firstStepCS")
  println(s"FirstStep    Server -> Client: $firstStepSC")
  
  // ###Component 1 1
  val componentCS_1_1 = Json.obj(
      "jsonId" -> DTOIds.component,
      "dto" -> DTONames.component
      ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "immutable"
      )
  )
  
  val componentSC_1_1 = handelMessage(componentCS_1_1)
  println(s"Component_1_1    Client -> Server: $componentCS_1_1")
  println(s"Component_1_1    Server -> Client: $componentSC_1_1")
  
  //###Connect Step 1 -> Component 1
  val connStepToComponnetCS_1_1_1: JsValue = Json.obj(
      "jsonId" -> DTOIds.connStepToComponen,
      "dto" -> DTONames.connSteptoComponent,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "outStepId" -> (firstStepSC \ "result" \ "stepId").asOpt[String].get,
          "inComponentId" -> (componentSC_1_1 \ "result" \ "componentId").asOpt[String].get
      )
  )
  
  val connStepToComponentSC_1_1_1 = handelMessage(connStepToComponnetCS_1_1_1)
  println(s"ConnStepToComponent_1_1_1   Client -> Server: $connStepToComponnetCS_1_1_1")
  println(s"ConnStepToComponent_1_1_1   Server -> Client: $connStepToComponentSC_1_1_1")
  
  // ###Component 1 2
  val componentCS_1_2 = Json.obj(
      "jsonId" -> DTOIds.component,
      "dto" -> DTONames.component
      ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "immutable"
      )
  )
  
  val componentSC_1_2 = handelMessage(componentCS_1_2)
  println(s"Component_1_2    Client -> Server: $componentCS_1_2")
  println(s"Component_1_2    Server -> Client: $componentSC_1_2")
  
  //###Connect Step 1 -> Component 2
  val connStepToComponnetCS_1_1_2: JsValue = Json.obj(
      "jsonId" -> DTOIds.connStepToComponen,
      "dto" -> DTONames.connSteptoComponent,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "outStepId" -> (firstStepSC \ "result" \ "stepId").asOpt[String].get,
          "inComponentId" -> (componentSC_1_2 \ "result" \ "componentId").asOpt[String].get
      )
  )
  
  val connStepToComponentSC_1_1_2 = handelMessage(connStepToComponnetCS_1_1_2)
  println(s"ConnStepToComponent_1_1_2   Client -> Server: $connStepToComponnetCS_1_1_2")
  println(s"ConnStepToComponent_1_1_2   Server -> Client: $connStepToComponentSC_1_1_2")
  
  // ###Component 1 3
  val componentCS_1_3 = Json.obj(
      "jsonId" -> DTOIds.component,
      "dto" -> DTONames.component
      ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "immutable"
      )
  )
  
  val componentSC_1_3 = handelMessage(componentCS_1_3)
  println(s"Component_1_3    Client -> Server: $componentCS_1_3")
  println(s"Component_1_3    Server -> Client: $componentSC_1_3")
  
  //###Connect Step 1 -> Component 3
  val connStepToComponnetCS_1_1_3: JsValue = Json.obj(
      "jsonId" -> DTOIds.connStepToComponen,
      "dto" -> DTONames.connSteptoComponent,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "outStepId" -> (firstStepSC \ "result" \ "stepId").asOpt[String].get,
          "inComponentId" -> (componentSC_1_3 \ "result" \ "componentId").asOpt[String].get
      )
  )
  
  val connStepToComponentSC_1_1_3 = handelMessage(connStepToComponnetCS_1_1_3)
  println(s"ConnStepToComponent_1_1_3   Client -> Server: $connStepToComponnetCS_1_1_3")
  println(s"ConnStepToComponent_1_1_3   Server -> Client: $connStepToComponentSC_1_1_3")
  
  //###Step 2
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

  //###Connect Component 1 1 -> Step 2
  val connComponentToStepCS_1_1_2 = Json.obj(
      "jsonId" -> DTOIds.connComponentToStep,
      "dto" -> DTONames.connComponentToStep,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "inStepId" -> (stepSC_2 \ "result" \ "stepId").asOpt[String].get,
          "outComponentId" -> (componentSC_1_1 \ "result" \ "componentId").asOpt[String].get
      )
  )
  val connComponentToStepSC_1_1_2 = handelMessage(connComponentToStepCS_1_1_2)
  println(s"ConnComponentToStep_1_1_2    Client -> Server: $connComponentToStepCS_1_1_2")
  println(s"ConnComponentToStep_1_1_2    Server -> Client: $connComponentToStepSC_1_1_2")
  
  //###Connect Component 1 2 -> Step 2
  val connComponentToStepCS_1_2_2 = Json.obj(
      "jsonId" -> DTOIds.connComponentToStep,
      "dto" -> DTONames.connComponentToStep,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "inStepId" -> (stepSC_2 \ "result" \ "stepId").asOpt[String].get,
          "outComponentId" -> (componentSC_1_2 \ "result" \ "componentId").asOpt[String].get
      )
  )
    
  val connComponentToStepSC_1_2_2 = handelMessage(connComponentToStepCS_1_2_2)
  println(s"ConnComponentToStep_1_2_2    Client -> Server: $connComponentToStepCS_1_2_2")
  println(s"ConnComponentToStep_1_2_2    Server -> Client: $connComponentToStepSC_1_2_2")
  
  //###Step 3
  val stepCS_3 = Json.obj(
      "jsonId" -> DTOIds.step,
      "dto" -> DTONames.step,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "default"
      )
  )
        
  val stepSC_3 = handelMessage(stepCS_3)
  println(s"Step 3    Client -> Server: $stepCS_3")
  println(s"Step 3    Server -> Client: $stepSC_3")

  //###Connect Component 1 3 -> Step 3
  val connComponentToStepCS_1_3_3 = Json.obj(
      "jsonId" -> DTOIds.connComponentToStep,
      "dto" -> DTONames.connComponentToStep,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "inStepId" -> (stepSC_3 \ "result" \ "stepId").asOpt[String].get,
          "outComponentId" -> (componentSC_1_3 \ "result" \ "componentId").asOpt[String].get
      )
  )
  val connComponentToStepSC_1_3_3 = handelMessage(connComponentToStepCS_1_3_3)
  println(s"ConnComponentToStep_1_3_3    Client -> Server: $connComponentToStepCS_1_3_3")
  println(s"ConnComponentToStep_1_3_3    Server -> Client: $connComponentToStepSC_1_3_3")

  // ###Component 2 1
  val componentCS_2_1 = Json.obj(
      "jsonId" -> DTOIds.component,
      "dto" -> DTONames.component
      ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "immutable"
      )
  )
   val componentSC_2_1 = handelMessage(componentCS_2_1)
  println(s"Component_2_1    Client -> Server: $componentCS_2_1")
  println(s"Component_2_1    Server -> Client: $componentSC_2_1")
  
  //###Connect Step 2  -> Component 2 1
  val connStepToComponnetCS_2_2_1: JsValue = Json.obj(
      "jsonId" -> DTOIds.connStepToComponen,
      "dto" -> DTONames.connSteptoComponent,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "outStepId" -> (stepSC_2 \ "result" \ "stepId").asOpt[String].get,
          "inComponentId" -> (componentSC_2_1 \ "result" \ "componentId").asOpt[String].get
      )
  )
  
  val connStepToComponentSC_2_2_1 = handelMessage(connStepToComponnetCS_2_2_1)
  println(s"ConnStepToComponent_2_2_1   Client -> Server: $connStepToComponnetCS_2_2_1")
  println(s"ConnStepToComponent_2_2_1   Server -> Client: $connStepToComponentSC_2_2_1")

  // ###Component 2 2
  val componentCS_2_2 = Json.obj(
      "jsonId" -> DTOIds.component,
      "dto" -> DTONames.component
      ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "immutable"
      )
  )
   val componentSC_2_2 = handelMessage(componentCS_2_2)
  println(s"Component_2_2    Client -> Server: $componentCS_2_2")
  println(s"Component_2_2    Server -> Client: $componentSC_2_2")
  
  //###Connect Step 2  -> Component 2 1
  val connStepToComponnetCS_2_2_2: JsValue = Json.obj(
      "jsonId" -> DTOIds.connStepToComponen,
      "dto" -> DTONames.connSteptoComponent,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "outStepId" -> (stepSC_2 \ "result" \ "stepId").asOpt[String].get,
          "inComponentId" -> (componentSC_2_2 \ "result" \ "componentId").asOpt[String].get
      )
  )
  
  val connStepToComponentSC_2_2_2 = handelMessage(connStepToComponnetCS_2_2_2)
  println(s"ConnStepToComponent_2_2_2   Client -> Server: $connStepToComponnetCS_2_2_2")
  println(s"ConnStepToComponent_2_2_2   Server -> Client: $connStepToComponentSC_2_2_2")
  
  //###Connect Component 2 1 -> Step 3
  val connComponentToStepCS_2_1_3 = Json.obj(
      "jsonId" -> DTOIds.connComponentToStep,
      "dto" -> DTONames.connComponentToStep,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "inStepId" -> (stepSC_3 \ "result" \ "stepId").asOpt[String].get,
          "outComponentId" -> (componentSC_2_1 \ "result" \ "componentId").asOpt[String].get
      )
  )
  val connComponentToStepSC_2_1_3 = handelMessage(connComponentToStepCS_2_1_3)
  println(s"ConnComponentToStep_2_1_3    Client -> Server: $connComponentToStepCS_2_1_3")
  println(s"ConnComponentToStep_2_1_3    Server -> Client: $connComponentToStepSC_2_1_3")
  
  //###Connect Component 2 2 -> Step 3
  val connComponentToStepCS_2_2_3 = Json.obj(
      "jsonId" -> DTOIds.connComponentToStep,
      "dto" -> DTONames.connComponentToStep,
      "params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "inStepId" -> (stepSC_3 \ "result" \ "stepId").asOpt[String].get,
          "outComponentId" -> (componentSC_2_2 \ "result" \ "componentId").asOpt[String].get
      )
  )
  val connComponentToStepSC_2_2_3 = handelMessage(connComponentToStepCS_2_2_3)
  println(s"ConnComponentToStep_2_2_3    Client -> Server: $connComponentToStepCS_2_2_3")
  println(s"ConnComponentToStep_2_2_3    Server -> Client: $connComponentToStepSC_2_2_3")
  
}