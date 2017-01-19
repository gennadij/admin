package org.main

import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import org.admin.AdminWeb
import com.sun.org.glassfish.external.arc.Stability
import play.api.libs.json.JsValue

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
*/
object PrepareConfigForSpecs2 extends AdminWeb{
  //TODO schon exestierenden User prüfen
  /*
   * userExist -> SpecsAddingAlredyExistingAdminUser
   * user -> SpecsLoginWithNotExistUser
   * user1 -> SpecsAddingNewAdminUser
   * user2 -> SpecsLogin
   * user3 -> SpecsAddingNewConfig
   * user4 -> SpecsAddingFirstStep
   * user5 -> SpecsAddingComponentWithFirstStep
   * user6 -> SpecsAddingNewComponent
   * 
   */
  
  def prepareWithAlredyExistingUser = {
    registerNewUser("userExist")
  }
  
  def prepareLogin = {
    
    registerNewUser("user2")
    
    val adminId: String = login("user2")
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, "http://contig1/user2")
    
    println("configId " + configId)
  }
  
  def prepareAddingNewConfig = {
//    registerNewUser("user3")
    
    val adminId: String = login("user5")
  }
  
  def prepareAddingFirstStep = {
//    registerNewUser("user4")
    
    val adminId: String = login("user4")
    
    println("adminId " + adminId)
    
    val configId = createNewConfig(adminId, "http://contig/user4")
    
    println("ConfigId" + configId)
    
  }
  
  def prepareAddingComponentWithFirstStep = {
//    registerNewUser("user5")
    
    val adminId = login("user5")
    
    println("adminId " + adminId)
    
    val configId = createNewConfig(adminId, "http://contig/user5")
    
    println("ConfigId" + configId)
  }
  
  def prepareAddingNewComponent = {
    registerNewUser("user6")
    
    val adminId = login("user6")
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, "http://contig/user6")
    
    println("ConfigId" + configId)
    
    val firstStep: String = addFirstStep(configId)
    
    println("FirstStep " + firstStep)
    
  }
  
  
  def registerNewUser(userPassword: String) = {
    
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
  
  def createNewConfig(adminId: String, configUrl: String) = {
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
  
  def login (userPassword: String): String = {
    val loginCS = Json.obj(
        "dtoId" -> DTOIds.LOGIN,
        "dto" -> DTONames.LOGIN
        ,"params" -> Json.obj(
            "username" -> userPassword,
            "password" -> userPassword
        )
    )
    
    val loginSC = handelMessage(loginCS)
    
    require((loginSC \ "result" \ "status").asOpt[Boolean].get == true)

    (loginSC \ "result" \ "adminId").asOpt[String].get
  }
  
  
  def addFirstStep(configId: String): String = {
    val firstStepCS = Json.obj(
        "dtoId" -> DTOIds.FIRST_STEP,
        "dto" -> DTONames.FIRST_STEP
        ,"params" -> Json.obj(
          "configId" -> configId,
          "kind" -> "first",
          "selectionCriterium" -> Json.obj(
              "min" -> 1,
              "max" -> 1
          )
        )
      )
      val firstStepSC: JsValue = handelMessage(firstStepCS)
      
      require((firstStepSC \ "result" \ "status").asOpt[Boolean].get == true)
      
      require((firstStepSC \ "result" \ "message").asOpt[String].get == 
          "Der erste Step wurde zu der Konfiguration hinzugefuegt")
      
      (firstStepSC \ "result" \ "stepId").asOpt[String].get
  }
  def deleteAllVertex = {
//    delete vertex V where @rid in (select @rid from AdminUser)
//    delete vertex V where @rid in (select @rid from Config)
//    delete vertex V where @rid in (select @rid from Step)
//    delete vertex V where @rid in (select @rid from Component)
  }
  
}