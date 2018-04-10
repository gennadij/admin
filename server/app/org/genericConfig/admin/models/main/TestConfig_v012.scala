//package org.main
//
//import models.admin.AdminWeb
//import models.dto.DTOIds
//import models.dto.DTONames
//import play.api.libs.json.Json
//import play.api.libs.json.JsValue
//import models.persistence.GlobalConfigForDB
//
///**
//	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
//	*
//	* Created by Gennadi Heimann 19.05.2017
//	*
//	* Username = user17
//	*/
//
//class TestConfig_v012 extends AdminWeb{
//
//  def config_v012 = {
//    
////    GlobalConfigForDB.setDb(new TestDBv012_3)
//    
//    val userPassword: String = "user12"
//    
//    registerNewUser(userPassword)
//    
//    val adminId: String = login(userPassword)
//    
//    println("adminId " + adminId)
//    
//    val configId: String = createNewConfig(adminId,  "http://contig/user17")
//    
//    println("configId " + configId)
//    
//    //STEP 1
//    
//    val firstStepId: String = addFirstStep(configId, 1, 1, "S_1")
//    
//    println(s"firstStepId = $configId -> $firstStepId")
//    
//    val componentId_1_1: String = addComponent(firstStepId, "immutable", "C_1_1")
//    
//    println(s"componentId_1_1 = $firstStepId -> $componentId_1_1")
//    
//    val componentId_1_2: String = addComponent(firstStepId, "immutable", "C_1_2")
//    
//    println(s"componentId_1_2 = $firstStepId -> $componentId_1_2")
//    
//    val componentId_1_3: String = addComponent(firstStepId, "immutable", "C_1_3")
//    
//    println(s"componentId_1_3 = $firstStepId -> $componentId_1_3")
//    
//    //STEP 2
//    
//    val stepId_2: String = addStep(componentId_1_1, "default", 1, 1, "S_2")
//    
//    println("stepId_2 " + componentId_1_1 + " -> " + stepId_2 )
//    
//    connectComponentToStep(stepId_2, componentId_1_2)
//    
//    println("Connect " + componentId_1_2 + " -> " + stepId_2)
//    
//    val componentId_2_1: String = addComponent(stepId_2, "immutable", "C_2_1")
//    
//    println(s"componentId_2_1 = $stepId_2 -> $componentId_2_1")
//    
//    val componentId_2_2: String = addComponent(stepId_2, "immutable", "C_2_1")
//    
//    println(s"componentId_2_2 = $stepId_2 -> $componentId_2_2")
//    
//    // STEP 3
//    
//    val stepId_3: String = addStep(componentId_1_3, "default", 2, 4, "S_3")
//    
//    println("stepId_3 " + componentId_1_3 + " -> " + stepId_3)
//    
//    connectComponentToStep(stepId_3, componentId_2_1)
//    
//    println("Connect " + componentId_2_1 + " -> " + stepId_3)
//    
//    connectComponentToStep(stepId_3, componentId_2_2)
//    
//    println("Connect " + componentId_2_2 + " -> " + stepId_3)
//    
//    val componentId_3_1 = addComponent(stepId_3, "immutable", "C_3_1")
//    
//    println(s"componentId_3_1 = $stepId_3 -> $componentId_3_1")
//    
//    val componentId_3_2 = addComponent(stepId_3, "immutable", "C_3_2")
//    
//    println(s"componentId_3_2 = $stepId_3 -> $componentId_3_2")
//    
//    val componentId_3_3 = addComponent(stepId_3, "mutable", "C_3_3")
//    
//    println(s"componentId_3_3 = $stepId_3 -> $componentId_3_3")
//    
//    val componentId_3_4 = addComponent(stepId_3, "mutable", "C_3_4")
//    
//    println(s"componentId_3_4 = $stepId_3 -> $componentId_3_4")
//    
//    //STEP 4
//    
//    val stepId_4 = addStep(componentId_3_1, "default", 1, 2, "S_4")
//    
//    println(s"stepId_4 $componentId_3_1 -> $stepId_4")
//    
//    connectComponentToStep(stepId_4, componentId_3_2)
//    
//    println(s"Connect $componentId_3_2 -> $stepId_4")
//    
//    connectComponentToStep(stepId_4, componentId_3_3)
//    
//    println(s"Connect $componentId_3_3 -> $stepId_4")
//    
//    connectComponentToStep(stepId_4, componentId_3_4)
//    
//    println(s"Connect $componentId_3_4 -> $stepId_4")
//    
//    val componentId_4_1 = addComponent(stepId_4, "immutable", "C_4_1")
//    
//    println(s"componentId_4_1 = $stepId_4 -> $componentId_4_1")
//    
//    val componentId_4_2 = addComponent(stepId_4, "immutable", "C_4_2")
//    
//    println(s"componentId_4_2 = $stepId_4 -> $componentId_4_2")
//    
//    val componentId_4_3 = addComponent(stepId_4, "immutable", "C_4_3")
//    
//    println(s"componentId_4_3 = $stepId_4 -> $componentId_4_3")
//    
//    // STEP 5
//    
//    val stepId_5 = addStep(componentId_4_1, "default", 2, 4, "S_5")
//    
//    println(s"stepId_5 $componentId_4_1 -> $stepId_5")
//    
//    connectComponentToStep(stepId_5, componentId_4_2)
//    
//    println(s"Connect $componentId_4_2 -> $stepId_5")
//    
//    val componentId_5_1 = addComponent(stepId_5, "immutable", "C_5_1")
//    
//    println(s"componentId_5_1 = $stepId_5 -> $componentId_5_1")
//    
//    val componentId_5_2 = addComponent(stepId_5, "immutable", "C_5_2")
//    
//    println(s"componentId_5_2 = $stepId_5 -> $componentId_5_2")
//    
//    val componentId_5_3 = addComponent(stepId_5, "immutable", "C_5_3")
//    
//    println(s"componentId_5_3 = $stepId_5 -> $componentId_5_3")
//    
//    val componentId_5_4 = addComponent(stepId_5, "immutable", "C_5_4")
//    
//    println(s"componentId_5_4 = $stepId_5 -> $componentId_5_4")
//    
//    val componentId_5_5 = addComponent(stepId_5, "immutable", "C_5_5")
//    
//    println(s"componentId_5_5 = $stepId_5 -> $componentId_5_5")
//    
//    //STEP 6
//    
//    val stepId_6 = addStep(componentId_4_3, "final", 1, 1, "S_6")
//    
//    println(s"stepId_6 $componentId_4_3 -> $stepId_6")
//    
//    val componentId_6_1 = addComponent(stepId_6, "immutable", "C_6_1")
//    
//    println(s"componentId_6_1 = $stepId_6 -> $componentId_6_1")
//    
//    val componentId_6_2 = addComponent(stepId_6, "immutable", "C_6_2")
//    
//    println(s"componentId_6_2 = $stepId_6 -> $componentId_6_2")
//    
//    val componentId_6_3 = addComponent(stepId_6, "immutable", "C_6_3")
//    
//    println(s"componentId_6_3 = $stepId_6 -> $componentId_6_3")
//    
//    //STEP 7
//    
//    val stepId_7 = addStep(componentId_5_4, "final", 1, 1, "S_7")
//    
//    println(s"stepId_7 $componentId_5_4 -> $stepId_7")
//    
//    connectComponentToStep(stepId_7, componentId_5_5)
//    
//    println(s"Connect $componentId_5_5 -> $stepId_7")
//    
//    val componentId_7_1 = addComponent(stepId_7, "immutable", "C_7_1")
//    
//    println(s"componentId_7_1 = $stepId_7 -> $componentId_7_1")
//    
//    val componentId_7_2 = addComponent(stepId_7, "immutable", "C_7_2")
//    
//    println(s"componentId_7_2 = $stepId_7 -> $componentId_7_2")
//    
//    //STEP 8
//    
//    val stepId_8 = addStep(componentId_5_1, "final", 1, 1, "S_8")
//    
//    println(s"stepId_8 $componentId_5_1 -> $stepId_8")
//    
//    connectComponentToStep(stepId_8, componentId_5_2)
//    
//    println(s"Connect $componentId_5_2 -> $stepId_8")
//    
//    connectComponentToStep(stepId_8, componentId_5_3)
//    
//    println(s"Connect $componentId_5_3 -> $stepId_8")
//    
//    val componentId_8_1 = addComponent(stepId_8, "immutable", "C_8_1")
//    
//    println(s"componentId_8_1 = $stepId_8 -> $componentId_8_1")
//    
//    val componentId_8_2 = addComponent(stepId_8, "immutable", "C_8_2")
//    
//    println(s"componentId_8_2 = $stepId_8 -> $componentId_8_2")
//    
//    val componentId_8_3 = addComponent(stepId_8, "immutable", "C_8_3")
//    
//    println(s"componentId_8_3 = $stepId_8 -> $componentId_8_3")
//  }
//  
//  private def registerNewUser(userPassword: String) = {
//    
//    val registerCS = Json.obj(
//          "dtoId" -> DTOIds.REGISTRATION,
//          "dto" -> DTONames.REGISTRATION
//          ,"params" -> Json.obj(
//               "username" -> userPassword,
//               "password"-> userPassword
//           )
//      )
//    
//    val registerSC = handelMessage(registerCS)
//    
//    require((registerSC \ "result" \ "username").asOpt[String].get == userPassword, s"Username: $userPassword")
//    require((registerSC \ "result" \ "status").asOpt[Boolean].get == true, "Status: " + (registerSC \ "result" \ "status").asOpt[Boolean].get)
//  }
//  
//  private def login (userPassword: String): String = {
//    val loginCS = Json.obj(
//        "dtoId" -> DTOIds.LOGIN,
//        "dto" -> DTONames.LOGIN
//        ,"params" -> Json.obj(
//            "username" -> userPassword,
//            "password" -> userPassword
//        )
//    )
//    
//    val loginSC = handelMessage(loginCS)
//    
//    require((loginSC \ "result" \ "status").asOpt[Boolean].get == true, "LoginStatus" + (loginSC \ "result" \ "status").asOpt[Boolean].get)
//
//    (loginSC \ "result" \ "adminId").asOpt[String].get
//  }
//  
//  private def createNewConfig(adminId: String, configUrl: String) = {
//    val createConfigCS = Json.obj(
//          "jsonId" -> DTOIds.CREATE_CONFIG,
//          "dto" -> DTONames.CREATE_CONFIG
//          , "params" -> Json.obj(
//              "adminId" -> adminId,
//              "configUrl" -> configUrl
//          )
//      )
//      val createConfigSC = handelMessage(createConfigCS)
//      require((createConfigSC \ "result" \ "status").asOpt[Boolean].get == true, "Status: " + true)
//      require((createConfigSC \ "result" \ "message").asOpt[String].get == "Die Konfiguration wurde erfolgreich erzeugt")
//      
//      (createConfigSC \ "result" \ "configId").asOpt[String].get
//  }
//  
//  private def addFirstStep(configId: String, min: Int, max: Int, nameToShow: String): String = {
//    val firstStepCS = Json.obj(
//        "dtoId" -> DTOIds.CREATE_FIRST_STEP,
//        "dto" -> DTONames.CREATE_FIRST_STEP
//        ,"params" -> Json.obj(
//          "configId" -> configId,
//          "nameToShow" -> nameToShow,
//          "kind" -> "first",
//          "selectionCriterium" -> Json.obj(
//              "min" -> min,
//              "max" -> max
//          )
//        )
//      )
//      val firstStepSC: JsValue = handelMessage(firstStepCS)
//      
//      require((firstStepSC \ "result" \ "status").asOpt[Boolean].get == true)
//      
//      require((firstStepSC \ "result" \ "message").asOpt[String].get == 
//          "Der erste Step wurde zu der Konfiguration hinzugefuegt")
//      
//      (firstStepSC \ "result" \ "stepId").asOpt[String].get
//  }
//  
//  private def addComponent(stepId: String, kind: String, nameToShow: String): String = {
//    val componentCS = Json.obj(
//        "dtoId" -> DTOIds.CREATE_COMPONENT,
//        "dto" -> DTONames.CREATE_COMPONENT
//        ,"params" -> Json.obj(
//            "stepId" -> stepId,
//            "nameToShow" -> nameToShow,
//            "kind" -> kind
//        )
//    )
//    val componentSC: JsValue = handelMessage(componentCS)
//    require((componentSC \ "result" \ "status").asOpt[Boolean].get == true)
//    require((componentSC \ "result" \ "message").asOpt[String].get == "Die Komponente wurde hinzugefuegt")
//    
//    (componentSC \ "result" \ "componentId").asOpt[String].get
//  }
//  
//  private def addStep(componentId: String, kind: String, min: Int, max: Int, nameToShow: String): String = {
//    val stepCS = Json.obj(
//        "dtoId" -> DTOIds.CREATE_STEP,
//        "dto" -> DTONames.CREATE_STEP,
//        "params" -> Json.obj(
//            "componentId" -> componentId,
//            "nameToShow" -> nameToShow,
//            "kind" -> kind,
//            "selectionCriterium" -> Json.obj(
//                "min" -> min,
//                "max" -> max
//            )
//        )
//    )
//    val stepSC = handelMessage(stepCS)
//    require((stepSC \ "result" \ "status").asOpt[Boolean].get == true, 
//        "status" + (stepSC \ "result" \ "status").asOpt[Boolean].get)
//    (stepSC \ "result" \ "stepId").asOpt[String].get
//  }
//  
//  
//  private def connectComponentToStep(stepId: String, componentId: String) = {
//    val connectionComponentToStepCS = Json.obj(
//        "dtoId" -> DTOIds.CONNECTION_COMPONENT_TO_STEP,
//        "dto" -> DTONames.CONNECTION_COMPONENT_TO_STEP,
//         "params" -> Json.obj(
//             "componentId" -> componentId,
//             "stepId" -> stepId
//         )
//    )
//    val connectionComponentToStepSC = handelMessage(connectionComponentToStepCS)
//        
//    require((connectionComponentToStepSC \ "result" \ "status").asOpt[Boolean].get == true, 
//        "Status " + (connectionComponentToStepSC \ "result" \ "status").asOpt[Boolean].get)
//  }
//}