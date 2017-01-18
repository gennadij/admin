package org.main

import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import org.admin.AdminWeb
import com.sun.org.glassfish.external.arc.Stability

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
*/
class PrapairConfigForSpecs2 extends AdminWeb{
  
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
    require((registerSC \ "result" \ "status").asOpt[Boolean].get == true, "Status: " + true)
  }
  
  def createNewConfig(adminId: String) = {
    val createConfigCS = Json.obj(
          "jsonId" -> DTOIds.CREATE_CONFIG,
          "dto" -> DTONames.CREATE_CONFIG
          , "params" -> Json.obj(
              "adminId" -> adminId,
              "configUrl" -> "//http://contig1/user3"
          )
      )
      val createConfigSC = handelMessage(createConfigCS)
        require((createConfigSC \ "result" \ "status").asOpt[Boolean].get == true, "Status: " + true)
        require((createConfigSC \ "result" \ "message").asOpt[String].get == "Die Konfiguration wurde erfolgreich erzeugt")
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
  
  def deleteAllVertex = {
//    delete vertex V where @rid in (select @rid from AdminUser)
//    delete vertex V where @rid in (select @rid from Config)
//    delete vertex V where @rid in (select @rid from Step)
//    delete vertex V where @rid in (select @rid from Component)
  }
  
  def scenario1 = {
    /*
     * AdminUser -> Config
     * 
     * AdminUser
     * - username = user6
     * - password = user6
     * 
     * Config
     * - configUrl = http://config2/user6
     * 
     * 
     */
    
    
  }
  
  def prepareAddingNewConfig = {
//    user5 -> new Config (AdminUser nicht geloescht, Config wird geloescht) in SpecsAddingNewConfig
//    registerNewUser("user5")
    
    val adminId: String = login("user5")
  }
  
  def prepareAddingFirstStep = {
//    user2 -> login (Config nicht geloescht, FirstStep geloescht) in SpecsAddingFirstStep
    registerNewUser("user2")
    
    val adminId: String = login("user2")
    
    println("adminId " + adminId)
    
    val configId = createNewConfig(adminId)
    
    println("ConfigId" + configId)
    
  }
  
//  def prepareWithAlredyExistingUser = {
//    registerNewUser("")
//  }
  
}