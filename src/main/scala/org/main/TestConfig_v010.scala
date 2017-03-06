package org.main

import org.admin.AdminWeb
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.JsValue

class TestConfig_v010 extends AdminWeb{
  
  /*
   * Linux
   */
//  I am generic configurator
//Generic configurator started
//Run Test Scenarios
//adminId #21:28
//configId #41:13
//firstStepId = #41:13 -> #25:55
//componentId_1_1 = #25:55 -> #29:41
//componentId_1_2 = #25:55 -> #30:35
//componentId_1_3 = #25:55 -> #31:33
//stepId_2 #29:41 -> #26:47
//Connect #30:35 -> #26:47
//componentId_2_1 = #26:47 -> #32:29
//componentId_2_2 = #26:47 -> #29:42
//stepId_3 #31:33 -> #27:39
//Connect #32:29 -> #27:39
//Connect #29:42 -> #27:39
//componentId_3_1 = #27:39 -> #30:36
//componentId_3_2 = #27:39 -> #31:34
//componentId_3_3 = #27:39 -> #32:30
//componentId_3_4 = #27:39 -> #29:43
//stepId_4 #30:36 -> #28:36
//Connect #31:34 -> #28:36
//Connect #32:30 -> #28:36
//Connect #29:43 -> #28:36
//componentId_4_1 = #28:36 -> #30:37
//componentId_4_2 = #28:36 -> #31:35
//componentId_4_3 = #28:36 -> #32:31
//stepId_5 #30:37 -> #25:56
//Connect #31:35 -> #25:56
//componentId_5_1 = #25:56 -> #29:44
//componentId_5_2 = #25:56 -> #30:38
//componentId_5_3 = #25:56 -> #31:36
//componentId_5_4 = #25:56 -> #32:32
//componentId_5_5 = #25:56 -> #29:45
//stepId_6 #32:31 -> #26:48
//componentId_6_1 = #26:48 -> #30:39
//componentId_6_2 = #26:48 -> #31:37
//componentId_6_3 = #26:48 -> #32:33
//stepId_7 #32:32 -> #27:40
//Connect #29:45 -> #27:40
//componentId_7_1 = #27:40 -> #29:46
//componentId_7_2 = #27:40 -> #30:40
//stepId_8 #29:44 -> #28:37
//Connect #30:38 -> #28:37
//Connect #31:36 -> #28:37
//componentId_8_1 = #28:37 -> #31:38
//componentId_8_2 = #28:37 -> #32:34
//componentId_8_3 = #28:37 -> #29:47
//END
  
  /*
   * Windows
   */
  
//adminId #21:7
//configId #41:6
//firstStepId = #41:6 -> #25:9
//componentId_1_1 = #25:9 -> #29:15
//componentId_1_2 = #25:9 -> #30:14
//componentId_1_3 = #25:9 -> #31:13
//stepId_2 #29:15 -> #26:6
//Connect #30:14 -> #26:6
//componentId_2_1 = #26:6 -> #32:11
//componentId_2_2 = #26:6 -> #29:16
//stepId_3 #31:13 -> #27:5
//Connect #32:11 -> #27:5
//Connect #29:16 -> #27:5
//componentId_3_1 = #27:5 -> #30:15
//componentId_3_2 = #27:5 -> #31:14
//componentId_3_3 = #27:5 -> #32:12
//componentId_3_4 = #27:5 -> #29:17
//stepId_4 #30:15 -> #28:5
//Connect #31:14 -> #28:5
//Connect #32:12 -> #28:5
//Connect #29:17 -> #28:5
//componentId_4_1 = #28:5 -> #30:16
//componentId_4_2 = #28:5 -> #31:15
//componentId_4_3 = #28:5 -> #32:13
//stepId_5 #30:16 -> #25:10
//Connect #31:15 -> #25:10
//componentId_5_1 = #25:10 -> #29:18
//componentId_5_2 = #25:10 -> #30:17
//componentId_5_3 = #25:10 -> #31:16
//componentId_5_4 = #25:10 -> #32:14
//componentId_5_5 = #25:10 -> #29:19
//stepId_6 #32:13 -> #26:7
//componentId_6_1 = #26:7 -> #30:18
//componentId_6_2 = #26:7 -> #31:17
//componentId_6_3 = #26:7 -> #32:15
//stepId_7 #32:14 -> #27:6
//Connect #29:19 -> #27:6
//componentId_7_1 = #27:6 -> #29:20
//componentId_7_2 = #27:6 -> #30:19
//stepId_8 #29:18 -> #28:6
//Connect #30:17 -> #28:6
//Connect #31:16 -> #28:6
//componentId_8_1 = #28:6 -> #31:18
//componentId_8_2 = #28:6 -> #32:16
//componentId_8_3 = #28:6 -> #29:21

  
  
  
  
  
  def configUser10 = {
    
    val userPassword: String = "user10"
    
    registerNewUser(userPassword)
    
    val adminId: String = login(userPassword)
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId,  "http://contig/user10")
    
    println("configId " + configId)
    
    //STEP 1
    
    val firstStepId: String = addFirstStep(configId, 1, 1)
    
    println(s"firstStepId = $configId -> $firstStepId")
    
    val componentId_1_1: String = addComponent(firstStepId, "immutable")
    
    println(s"componentId_1_1 = $firstStepId -> $componentId_1_1")
    
    val componentId_1_2: String = addComponent(firstStepId, "immutable")
    
    println(s"componentId_1_2 = $firstStepId -> $componentId_1_2")
    
    val componentId_1_3: String = addComponent(firstStepId, "immutable")
    
    println(s"componentId_1_3 = $firstStepId -> $componentId_1_3")
    
    //STEP 2
    
    val stepId_2: String = addStep(componentId_1_1, "default", 1, 1)
    
    println("stepId_2 " + componentId_1_1 + " -> " + stepId_2 )
    
    connectComponentToStep(stepId_2, componentId_1_2)
    
    println("Connect " + componentId_1_2 + " -> " + stepId_2)
    
    val componentId_2_1: String = addComponent(stepId_2, "immutable")
    
    println(s"componentId_2_1 = $stepId_2 -> $componentId_2_1")
    
    val componentId_2_2: String = addComponent(stepId_2, "immutable")
    
    println(s"componentId_2_2 = $stepId_2 -> $componentId_2_2")
    
    // STEP 3
    
    val stepId_3: String = addStep(componentId_1_3, "default", 2, 4)
    
    println("stepId_3 " + componentId_1_3 + " -> " + stepId_3)
    
    connectComponentToStep(stepId_3, componentId_2_1)
    
    println("Connect " + componentId_2_1 + " -> " + stepId_3)
    
    connectComponentToStep(stepId_3, componentId_2_2)
    
    println("Connect " + componentId_2_2 + " -> " + stepId_3)
    
    val componentId_3_1 = addComponent(stepId_3, "immutable")
    
    println(s"componentId_3_1 = $stepId_3 -> $componentId_3_1")
    
    val componentId_3_2 = addComponent(stepId_3, "immutable")
    
    println(s"componentId_3_2 = $stepId_3 -> $componentId_3_2")
    
    val componentId_3_3 = addComponent(stepId_3, "mutable")
    
    println(s"componentId_3_3 = $stepId_3 -> $componentId_3_3")
    
    val componentId_3_4 = addComponent(stepId_3, "mutable")
    
    println(s"componentId_3_4 = $stepId_3 -> $componentId_3_4")
    
    //STEP 4
    
    val stepId_4 = addStep(componentId_3_1, "default", 1, 2)
    
    println(s"stepId_4 $componentId_3_1 -> $stepId_4")
    
    connectComponentToStep(stepId_4, componentId_3_2)
    
    println(s"Connect $componentId_3_2 -> $stepId_4")
    
    connectComponentToStep(stepId_4, componentId_3_3)
    
    println(s"Connect $componentId_3_3 -> $stepId_4")
    
    connectComponentToStep(stepId_4, componentId_3_4)
    
    println(s"Connect $componentId_3_4 -> $stepId_4")
    
    val componentId_4_1 = addComponent(stepId_4, "immutable")
    
    println(s"componentId_4_1 = $stepId_4 -> $componentId_4_1")
    
    val componentId_4_2 = addComponent(stepId_4, "immutable")
    
    println(s"componentId_4_2 = $stepId_4 -> $componentId_4_2")
    
    val componentId_4_3 = addComponent(stepId_4, "immutable")
    
    println(s"componentId_4_3 = $stepId_4 -> $componentId_4_3")
    
    
    // STEP 5
    
    val stepId_5 = addStep(componentId_4_1, "default", 2, 4)
    
    println(s"stepId_5 $componentId_4_1 -> $stepId_5")
    
    connectComponentToStep(stepId_5, componentId_4_2)
    
    println(s"Connect $componentId_4_2 -> $stepId_5")
    
    val componentId_5_1 = addComponent(stepId_5, "immutable")
    
    println(s"componentId_5_1 = $stepId_5 -> $componentId_5_1")
    
    val componentId_5_2 = addComponent(stepId_5, "immutable")
    
    println(s"componentId_5_2 = $stepId_5 -> $componentId_5_2")
    
    val componentId_5_3 = addComponent(stepId_5, "immutable")
    
    println(s"componentId_5_3 = $stepId_5 -> $componentId_5_3")
    
    val componentId_5_4 = addComponent(stepId_5, "immutable")
    
    println(s"componentId_5_4 = $stepId_5 -> $componentId_5_4")
    
    val componentId_5_5 = addComponent(stepId_5, "immutable")
    
    println(s"componentId_5_5 = $stepId_5 -> $componentId_5_5")
    
    //STEP 6
    
    val stepId_6 = addStep(componentId_4_3, "final", 1, 1)
    
    println(s"stepId_6 $componentId_4_3 -> $stepId_6")
    
    val componentId_6_1 = addComponent(stepId_6, "immutable")
    
    println(s"componentId_6_1 = $stepId_6 -> $componentId_6_1")
    
    val componentId_6_2 = addComponent(stepId_6, "immutable")
    
    println(s"componentId_6_2 = $stepId_6 -> $componentId_6_2")
    
    val componentId_6_3 = addComponent(stepId_6, "immutable")
    
    println(s"componentId_6_3 = $stepId_6 -> $componentId_6_3")
    
    //STEP 7
    
    val stepId_7 = addStep(componentId_5_4, "final", 1, 1)
    
    println(s"stepId_7 $componentId_5_4 -> $stepId_7")
    
    connectComponentToStep(stepId_7, componentId_5_5)
    
    println(s"Connect $componentId_5_5 -> $stepId_7")
    
    val componentId_7_1 = addComponent(stepId_7, "immutable")
    
    println(s"componentId_7_1 = $stepId_7 -> $componentId_7_1")
    
    val componentId_7_2 = addComponent(stepId_7, "immutable")
    
    println(s"componentId_7_2 = $stepId_7 -> $componentId_7_2")
    
    //STEP 8
    
    val stepId_8 = addStep(componentId_5_1, "final", 1, 1)
    
    println(s"stepId_8 $componentId_5_1 -> $stepId_8")
    
    connectComponentToStep(stepId_8, componentId_5_2)
    
    println(s"Connect $componentId_5_2 -> $stepId_8")
    
    connectComponentToStep(stepId_8, componentId_5_3)
    
    println(s"Connect $componentId_5_3 -> $stepId_8")
    
    val componentId_8_1 = addComponent(stepId_8, "immutable")
    
    println(s"componentId_8_1 = $stepId_8 -> $componentId_8_1")
    
    val componentId_8_2 = addComponent(stepId_8, "immutable")
    
    println(s"componentId_8_2 = $stepId_8 -> $componentId_8_2")
    
    val componentId_8_3 = addComponent(stepId_8, "immutable")
    
    println(s"componentId_8_3 = $stepId_8 -> $componentId_8_3")
  }
  
  
  
  private def registerNewUser(userPassword: String) = {
    
    val registerCS = Json.obj(
          "dtoId" -> DTOIds.REGISTRATION,
          "dto" -> DTONames.REGISTRATION
          ,"params" -> Json.obj(
               "username" -> userPassword,
               "password"-> userPassword
           )
      )
    
    val registerSC = handelMessage(registerCS)
    
    require((registerSC \ "result" \ "username").asOpt[String].get == userPassword, s"Username: $userPassword")
    require((registerSC \ "result" \ "status").asOpt[Boolean].get == true, "Status: " + (registerSC \ "result" \ "status").asOpt[Boolean].get)
  }
  
  private def login (userPassword: String): String = {
    val loginCS = Json.obj(
        "dtoId" -> DTOIds.LOGIN,
        "dto" -> DTONames.LOGIN
        ,"params" -> Json.obj(
            "username" -> userPassword,
            "password" -> userPassword
        )
    )
    
    val loginSC = handelMessage(loginCS)
    
    require((loginSC \ "result" \ "status").asOpt[Boolean].get == true, "LoginStatus" + (loginSC \ "result" \ "status").asOpt[Boolean].get)

    (loginSC \ "result" \ "adminId").asOpt[String].get
  }
  
  private def createNewConfig(adminId: String, configUrl: String) = {
    val createConfigCS = Json.obj(
          "jsonId" -> DTOIds.CREATE_CONFIG,
          "dto" -> DTONames.CREATE_CONFIG
          , "params" -> Json.obj(
              "adminId" -> adminId,
              "configUrl" -> configUrl
          )
      )
      val createConfigSC = handelMessage(createConfigCS)
      require((createConfigSC \ "result" \ "status").asOpt[Boolean].get == true, "Status: " + true)
      require((createConfigSC \ "result" \ "message").asOpt[String].get == "Die Konfiguration wurde erfolgreich erzeugt")
      
      (createConfigSC \ "result" \ "configId").asOpt[String].get
  }
  
  private def addFirstStep(configId: String, min: Int, max: Int): String = {
    val firstStepCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_FIRST_STEP,
        "dto" -> DTONames.CREATE_FIRST_STEP
        ,"params" -> Json.obj(
          "configId" -> configId,
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> min,
              "max" -> max
          )
        )
      )
      val firstStepSC: JsValue = handelMessage(firstStepCS)
      
      require((firstStepSC \ "result" \ "status").asOpt[Boolean].get == true)
      
      require((firstStepSC \ "result" \ "message").asOpt[String].get == 
          "Der erste Step wurde zu der Konfiguration hinzugefuegt")
      
      (firstStepSC \ "result" \ "stepId").asOpt[String].get
  }
  
  private def addComponent(stepId: String, kind: String): String = {
    val componentCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_COMPONENT,
        "dto" -> DTONames.CREATE_COMPONENT
        ,"params" -> Json.obj(
            "stepId" -> stepId,
            "kind" -> kind
        )
    )
    val componentSC: JsValue = handelMessage(componentCS)
    require((componentSC \ "result" \ "status").asOpt[Boolean].get == true)
    require((componentSC \ "result" \ "message").asOpt[String].get == "Die Komponente wurde hinzugefuegt")
    
    (componentSC \ "result" \ "componentId").asOpt[String].get
  }
  
  private def addStep(componentId: String, kind: String, min: Int, max: Int): String = {
    val stepCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_STEP,
        "dto" -> DTONames.CREATE_STEP,
        "params" -> Json.obj(
            "componentId" -> componentId,
            "kind" -> kind,
            "selectionCriterium" -> Json.obj(
                "min" -> min,
                "max" -> max
            )
        )
    )
    val stepSC = handelMessage(stepCS)
    require((stepSC \ "result" \ "status").asOpt[Boolean].get == true, 
        "status" + (stepSC \ "result" \ "status").asOpt[Boolean].get)
    (stepSC \ "result" \ "stepId").asOpt[String].get
  }
  
  
  private def connectComponentToStep(stepId: String, componentId: String) = {
    val connectionComponentToStepCS = Json.obj(
        "dtoId" -> DTOIds.CONNECTION_COMPONENT_TO_STEP,
        "dto" -> DTONames.CONNECTION_COMPONENT_TO_STEP,
         "params" -> Json.obj(
             "componentId" -> componentId,
             "stepId" -> stepId
         )
    )
    val connectionComponentToStepSC = handelMessage(connectionComponentToStepCS)
        
    require((connectionComponentToStepSC \ "result" \ "status").asOpt[Boolean].get == true, 
        "Status " + (connectionComponentToStepSC \ "result" \ "status").asOpt[Boolean].get)
  }
}