package org.main

import org.admin.AdminWeb
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json
import play.api.libs.json.JsValue

class TestConfig_v011 extends AdminWeb{
  
  /*
   * Linux
   */
//I am generic configurator
//Generic configurator started
//Run Test Scenarios
//adminId #22:14
//configId #42:5
//firstStepId = #42:5 -> #25:61
//componentId_1_1 = #25:61 -> #29:54
//componentId_1_2 = #25:61 -> #30:47
//componentId_1_3 = #25:61 -> #31:45
//stepId_2 #29:54 -> #26:53
//Connect #30:47 -> #26:53
//componentId_2_1 = #26:53 -> #32:41
//componentId_2_2 = #26:53 -> #29:55
//stepId_3 #31:45 -> #27:45
//Connect #32:41 -> #27:45
//Connect #29:55 -> #27:45
//componentId_3_1 = #27:45 -> #30:48
//componentId_3_2 = #27:45 -> #31:46
//componentId_3_3 = #27:45 -> #32:42
//componentId_3_4 = #27:45 -> #29:56
//stepId_4 #30:48 -> #28:42
//Connect #31:46 -> #28:42
//Connect #32:42 -> #28:42
//Connect #29:56 -> #28:42
//componentId_4_1 = #28:42 -> #30:49
//componentId_4_2 = #28:42 -> #31:47
//componentId_4_3 = #28:42 -> #32:43
//stepId_5 #30:49 -> #25:62
//Connect #31:47 -> #25:62
//componentId_5_1 = #25:62 -> #29:57
//componentId_5_2 = #25:62 -> #30:50
//componentId_5_3 = #25:62 -> #31:48
//componentId_5_4 = #25:62 -> #32:44
//componentId_5_5 = #25:62 -> #29:58
//stepId_6 #32:43 -> #26:54
//componentId_6_1 = #26:54 -> #30:51
//componentId_6_2 = #26:54 -> #31:49
//componentId_6_3 = #26:54 -> #32:45
//stepId_7 #32:44 -> #27:46
//Connect #29:58 -> #27:46
//componentId_7_1 = #27:46 -> #29:59
//componentId_7_2 = #27:46 -> #30:52
//stepId_8 #29:57 -> #28:43
//Connect #30:50 -> #28:43
//Connect #31:48 -> #28:43
//componentId_8_1 = #28:43 -> #31:50
//componentId_8_2 = #28:43 -> #32:46
//componentId_8_3 = #28:43 -> #29:60
//END
  
  /*
   * Windows
   */

//  I am generic configurator
//Generic configurator started
//Run Test Scenarios
//adminId #21:8
//configId #41:7
//firstStepId = #41:7 -> #25:11
//componentId_1_1 = #25:11 -> #29:22
//componentId_1_2 = #25:11 -> #30:20
//componentId_1_3 = #25:11 -> #31:19
//stepId_2 #29:22 -> #26:8
//Connect #30:20 -> #26:8
//componentId_2_1 = #26:8 -> #32:17
//componentId_2_2 = #26:8 -> #29:23
//stepId_3 #31:19 -> #27:7
//Connect #32:17 -> #27:7
//Connect #29:23 -> #27:7
//componentId_3_1 = #27:7 -> #30:21
//componentId_3_2 = #27:7 -> #31:20
//componentId_3_3 = #27:7 -> #32:18
//componentId_3_4 = #27:7 -> #29:24
//stepId_4 #30:21 -> #28:7
//Connect #31:20 -> #28:7
//Connect #32:18 -> #28:7
//Connect #29:24 -> #28:7
//componentId_4_1 = #28:7 -> #30:22
//componentId_4_2 = #28:7 -> #31:21
//componentId_4_3 = #28:7 -> #32:19
//stepId_5 #30:22 -> #25:12
//Connect #31:21 -> #25:12
//componentId_5_1 = #25:12 -> #29:25
//componentId_5_2 = #25:12 -> #30:23
//componentId_5_3 = #25:12 -> #31:22
//componentId_5_4 = #25:12 -> #32:20
//componentId_5_5 = #25:12 -> #29:26
//stepId_6 #32:19 -> #26:9
//componentId_6_1 = #26:9 -> #30:24
//componentId_6_2 = #26:9 -> #31:23
//componentId_6_3 = #26:9 -> #32:21
//stepId_7 #32:20 -> #27:8
//Connect #29:26 -> #27:8
//componentId_7_1 = #27:8 -> #29:27
//componentId_7_2 = #27:8 -> #30:25
//stepId_8 #29:25 -> #28:8
//Connect #30:23 -> #28:8
//Connect #31:22 -> #28:8
//componentId_8_1 = #28:8 -> #31:24
//componentId_8_2 = #28:8 -> #32:22
//componentId_8_3 = #28:8 -> #29:28
//END
  
  
  
  
  def config_v011 = {
    
    val userPassword: String = "user12"
    
    registerNewUser(userPassword)
    
    val adminId: String = login(userPassword)
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId,  "http://contig/user12")
    
    println("configId " + configId)
    
    //STEP 1
    
    val firstStepId: String = addFirstStep(configId, 1, 1, "S_1")
    
    println(s"firstStepId = $configId -> $firstStepId")
    
    val componentId_1_1: String = addComponent(firstStepId, "immutable", "C_1_1")
    
    println(s"componentId_1_1 = $firstStepId -> $componentId_1_1")
    
    val componentId_1_2: String = addComponent(firstStepId, "immutable", "C_1_2")
    
    println(s"componentId_1_2 = $firstStepId -> $componentId_1_2")
    
    val componentId_1_3: String = addComponent(firstStepId, "immutable", "C_1_3")
    
    println(s"componentId_1_3 = $firstStepId -> $componentId_1_3")
    
    //STEP 2
    
    val stepId_2: String = addStep(componentId_1_1, "default", 1, 1, "S_2")
    
    println("stepId_2 " + componentId_1_1 + " -> " + stepId_2 )
    
    connectComponentToStep(stepId_2, componentId_1_2)
    
    println("Connect " + componentId_1_2 + " -> " + stepId_2)
    
    val componentId_2_1: String = addComponent(stepId_2, "immutable", "C_2_1")
    
    println(s"componentId_2_1 = $stepId_2 -> $componentId_2_1")
    
    val componentId_2_2: String = addComponent(stepId_2, "immutable", "C_2_1")
    
    println(s"componentId_2_2 = $stepId_2 -> $componentId_2_2")
    
    // STEP 3
    
    val stepId_3: String = addStep(componentId_1_3, "default", 2, 4, "S_3")
    
    println("stepId_3 " + componentId_1_3 + " -> " + stepId_3)
    
    connectComponentToStep(stepId_3, componentId_2_1)
    
    println("Connect " + componentId_2_1 + " -> " + stepId_3)
    
    connectComponentToStep(stepId_3, componentId_2_2)
    
    println("Connect " + componentId_2_2 + " -> " + stepId_3)
    
    val componentId_3_1 = addComponent(stepId_3, "immutable", "C_3_1")
    
    println(s"componentId_3_1 = $stepId_3 -> $componentId_3_1")
    
    val componentId_3_2 = addComponent(stepId_3, "immutable", "C_3_2")
    
    println(s"componentId_3_2 = $stepId_3 -> $componentId_3_2")
    
    val componentId_3_3 = addComponent(stepId_3, "mutable", "C_3_3")
    
    println(s"componentId_3_3 = $stepId_3 -> $componentId_3_3")
    
    val componentId_3_4 = addComponent(stepId_3, "mutable", "C_3_4")
    
    println(s"componentId_3_4 = $stepId_3 -> $componentId_3_4")
    
    //STEP 4
    
    val stepId_4 = addStep(componentId_3_1, "default", 1, 2, "S_4")
    
    println(s"stepId_4 $componentId_3_1 -> $stepId_4")
    
    connectComponentToStep(stepId_4, componentId_3_2)
    
    println(s"Connect $componentId_3_2 -> $stepId_4")
    
    connectComponentToStep(stepId_4, componentId_3_3)
    
    println(s"Connect $componentId_3_3 -> $stepId_4")
    
    connectComponentToStep(stepId_4, componentId_3_4)
    
    println(s"Connect $componentId_3_4 -> $stepId_4")
    
    val componentId_4_1 = addComponent(stepId_4, "immutable", "C_4_1")
    
    println(s"componentId_4_1 = $stepId_4 -> $componentId_4_1")
    
    val componentId_4_2 = addComponent(stepId_4, "immutable", "C_4_2")
    
    println(s"componentId_4_2 = $stepId_4 -> $componentId_4_2")
    
    val componentId_4_3 = addComponent(stepId_4, "immutable", "C_4_3")
    
    println(s"componentId_4_3 = $stepId_4 -> $componentId_4_3")
    
    // STEP 5
    
    val stepId_5 = addStep(componentId_4_1, "default", 2, 4, "S_5")
    
    println(s"stepId_5 $componentId_4_1 -> $stepId_5")
    
    connectComponentToStep(stepId_5, componentId_4_2)
    
    println(s"Connect $componentId_4_2 -> $stepId_5")
    
    val componentId_5_1 = addComponent(stepId_5, "immutable", "C_5_1")
    
    println(s"componentId_5_1 = $stepId_5 -> $componentId_5_1")
    
    val componentId_5_2 = addComponent(stepId_5, "immutable", "C_5_2")
    
    println(s"componentId_5_2 = $stepId_5 -> $componentId_5_2")
    
    val componentId_5_3 = addComponent(stepId_5, "immutable", "C_5_3")
    
    println(s"componentId_5_3 = $stepId_5 -> $componentId_5_3")
    
    val componentId_5_4 = addComponent(stepId_5, "immutable", "C_5_4")
    
    println(s"componentId_5_4 = $stepId_5 -> $componentId_5_4")
    
    val componentId_5_5 = addComponent(stepId_5, "immutable", "C_5_5")
    
    println(s"componentId_5_5 = $stepId_5 -> $componentId_5_5")
    
    //STEP 6
    
    val stepId_6 = addStep(componentId_4_3, "final", 1, 1, "S_6")
    
    println(s"stepId_6 $componentId_4_3 -> $stepId_6")
    
    val componentId_6_1 = addComponent(stepId_6, "immutable", "C_6_1")
    
    println(s"componentId_6_1 = $stepId_6 -> $componentId_6_1")
    
    val componentId_6_2 = addComponent(stepId_6, "immutable", "C_6_2")
    
    println(s"componentId_6_2 = $stepId_6 -> $componentId_6_2")
    
    val componentId_6_3 = addComponent(stepId_6, "immutable", "C_6_3")
    
    println(s"componentId_6_3 = $stepId_6 -> $componentId_6_3")
    
    //STEP 7
    
    val stepId_7 = addStep(componentId_5_4, "final", 1, 1, "S_7")
    
    println(s"stepId_7 $componentId_5_4 -> $stepId_7")
    
    connectComponentToStep(stepId_7, componentId_5_5)
    
    println(s"Connect $componentId_5_5 -> $stepId_7")
    
    val componentId_7_1 = addComponent(stepId_7, "immutable", "C_7_1")
    
    println(s"componentId_7_1 = $stepId_7 -> $componentId_7_1")
    
    val componentId_7_2 = addComponent(stepId_7, "immutable", "C_7_2")
    
    println(s"componentId_7_2 = $stepId_7 -> $componentId_7_2")
    
    //STEP 8
    
    val stepId_8 = addStep(componentId_5_1, "final", 1, 1, "S_8")
    
    println(s"stepId_8 $componentId_5_1 -> $stepId_8")
    
    connectComponentToStep(stepId_8, componentId_5_2)
    
    println(s"Connect $componentId_5_2 -> $stepId_8")
    
    connectComponentToStep(stepId_8, componentId_5_3)
    
    println(s"Connect $componentId_5_3 -> $stepId_8")
    
    val componentId_8_1 = addComponent(stepId_8, "immutable", "C_8_1")
    
    println(s"componentId_8_1 = $stepId_8 -> $componentId_8_1")
    
    val componentId_8_2 = addComponent(stepId_8, "immutable", "C_8_2")
    
    println(s"componentId_8_2 = $stepId_8 -> $componentId_8_2")
    
    val componentId_8_3 = addComponent(stepId_8, "immutable", "C_8_3")
    
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
  
  private def addFirstStep(configId: String, min: Int, max: Int, nameToShow: String): String = {
    val firstStepCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_FIRST_STEP,
        "dto" -> DTONames.CREATE_FIRST_STEP
        ,"params" -> Json.obj(
          "configId" -> configId,
          "nameToShow" -> nameToShow,
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
  
  private def addComponent(stepId: String, kind: String, nameToShow: String): String = {
    val componentCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_COMPONENT,
        "dto" -> DTONames.CREATE_COMPONENT
        ,"params" -> Json.obj(
            "stepId" -> stepId,
            "nameToShow" -> nameToShow,
            "kind" -> kind
        )
    )
    val componentSC: JsValue = handelMessage(componentCS)
    require((componentSC \ "result" \ "status").asOpt[Boolean].get == true)
    require((componentSC \ "result" \ "message").asOpt[String].get == "Die Komponente wurde hinzugefuegt")
    
    (componentSC \ "result" \ "componentId").asOpt[String].get
  }
  
  private def addStep(componentId: String, kind: String, min: Int, max: Int, nameToShow: String): String = {
    val stepCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_STEP,
        "dto" -> DTONames.CREATE_STEP,
        "params" -> Json.obj(
            "componentId" -> componentId,
            "nameToShow" -> nameToShow,
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