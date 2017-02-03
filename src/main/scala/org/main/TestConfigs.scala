package org.main

import org.admin.AdminWeb
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.JsValue

class TestConfigs extends AdminWeb{
  
  def configUser10 = {
    
    val userPassword: String = "user10"
    
    registerNewUser(userPassword)
    
    val adminId: String = login(userPassword)
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId,  "http://contig/user7")
    
    println("configId " + configId)
    
    val firstStepId: String = addFirstStep(configId, 1, 1)
    
    println("firstStepId " + firstStepId)
    
    val component_1_1: String = addComponent(firstStepId, "immutable")
    
    println("component_1_1 " + component_1_1)
    
    val component_1_2: String = addComponent(firstStepId, "immutable")
    
    println("component_1_2 " + component_1_2)
    
    val component_1_3: String = addComponent(firstStepId, "immutable")
    
    println("component_1_3 " + component_1_3)
    
    
    
    
    
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
    require((stepSC \ "result" \ "status").asOpt[Boolean].get == true, "status" + (stepSC \ "result" \ "status").asOpt[Boolean].get)
    (stepSC \ "result" \ "stepId").asOpt[String].get
    
    ???
  }
}