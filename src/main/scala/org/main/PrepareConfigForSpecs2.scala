package org.main

import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import org.admin.AdminWeb
import com.sun.org.glassfish.external.arc.Stability
import play.api.libs.json.JsValue
import com.orientechnologies.orient.core.sql.OCommandSQL
import org.persistence.db.orientdb.OrientDB
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.impls.orient.OrientElementIterable

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
   * user7 -> SpecsConfigTree
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
    
    val userPassword = "user3"
    registerNewUser(userPassword)
    
    val adminId: String = login(userPassword)
  }
  
  def prepareAddingFirstStep = {
    registerNewUser("user4")
    
    val adminId: String = login("user4")
    
    println("adminId " + adminId)
    
    val configId = createNewConfig(adminId, "http://contig/user4")
    
    println("ConfigId" + configId)
    
  }
  
  def prepareAddingComponentWithFirstStep = {
    registerNewUser("user5")
    
    val adminId = login("user5")
    
    println("adminId " + adminId)
    
    val configId = createNewConfig(adminId, "http://contig/user5")
    
    println("ConfigId" + configId)
  }
  
  def prepareAddingNewComponent = {
    registerNewUser("user6")
    
    val adminId = login("user7")
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, "http://contig/user7")
    
    println("ConfigId" + configId)
    
    val firstStep: String = addFirstStep(configId)
    
    println("FirstStep " + firstStep)
  }
  
  def prepareConfigTree = {
    /*
     * Linux
     * adminId #21:25
     * ConfigId#41:10
     * FirstStep #25:51
     * Component 1 1 #29:39
     * Component 1 2 #30:33
     * Component 1 3 #31:31
     */
    registerNewUser("user7")
    
    val adminId = login("user7")
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, "http://contig/user7")
    
    println("ConfigId" + configId)
    
    val firstStepId : String = addFirstStep(configId)
    
    println("FirstStep " + firstStepId)
    
    //FirstStep -> 3 Components
    
    val componentId_1_1 = addComponentToStep(firstStepId)
    
    println("Component 1 1 " + componentId_1_1)
    
    val componentId_1_2 = addComponentToStep(firstStepId)
    
    println("Component 1 2 " + componentId_1_2)
    
    val componentId_1_3 = addComponentToStep(firstStepId)
    
    println("Component 1 3 " + componentId_1_3)
  }
  
  
  private def addComponentToStep(stepId: String): String = {
    val componentCS = Json.obj(
        "dtoId" -> DTOIds.COMPONENT,
        "dto" -> DTONames.COMPONENT
        ,"params" -> Json.obj(
            "stepId" -> stepId,
            "kind" -> "immutable"
        )
    )
    val componentSC: JsValue = handelMessage(componentCS)
    require((componentSC \ "result" \ "status").asOpt[Boolean].get == true)
    require((componentSC \ "result" \ "message").asOpt[String].get == "Die Komponente wurde hinzugefuegt")
    
    (componentSC \ "result" \ "componentId").asOpt[String].get
  }
  
  def getFirstStep(username: String): String = {
//    select out('hasConfig').out('hasFirstStep') from AdminUser where username='user6'
    val graph: OrientGraph = OrientDB.getGraph
    val sql: String = s"select expand(out('hasConfig').out('hasFirstStep')) from AdminUser where username='$username'"
    println(sql)
    val res: OrientDynaElementIterable = graph
        .command(new OCommandSQL(sql)).execute()
      graph.commit
      
    res.toList.get(0).asInstanceOf[OrientVertex].getIdentity.toString()
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
  
  
  private def addFirstStep(configId: String): String = {
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