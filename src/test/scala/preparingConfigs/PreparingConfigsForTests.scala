package preparingConfigs

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json
import org.admin.AdminWeb
import org.specs2.Specification
import play.api.libs.json.JsValue
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.persistence.db.orientdb.OrientDB
import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.Direction
import org.persistence.db.orientdb.PropertyKey

object PreparingConfigsForTests extends AdminWeb {
  
  def getComponentsFromFirstStep(stepId: String): List[String] = {
    val graph: OrientGraph = OrientDB.getGraph
    
    val vStep: OrientVertex = graph.getVertex(stepId)
    
    val eHasComponents: List[Edge] = vStep.getEdges(Direction.OUT, PropertyKey.EDGE_HAS_COMPONENT).toList
    
    val vComponents: List[Vertex] = eHasComponents map {_.getVertex(Direction.IN)}
    
    vComponents map {_.getId.toString()}
  }
  
  def prepareAddingNewConfig = {
    
    val userPassword = "user3"
    registerNewUser(userPassword)
    
    val adminId: String = login(userPassword)
  }
  
  def prepareLogin = {
    
    registerNewUser("user2")
    
    val adminId: String = login("user2")
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, "http://contig1/user2")
    
    println("configId " + configId)
  }
  def prepareWithAlredyExistingUser = {
    registerNewUser("userExist")
  }
  
  def prepareAddingComponentWithFirstStep = {
    registerNewUser("user5")
    
    val adminId = login("user5")
    
    println("adminId " + adminId)
    
    val configId = createNewConfig(adminId, "http://contig/user5")
    
    println("ConfigId" + configId)
  }
  
  def prepareAddingFirstStep = {
    registerNewUser("user4")
    
    val adminId: String = login("user4")
    
    println("adminId " + adminId)
    
    val configId = createNewConfig(adminId, "http://contig/user4")
    
    println("ConfigId" + configId)
    
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
  
  def prepareAddingNewComponent = {
    registerNewUser("user6")
    
    val adminId = login("user6")
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, "http://contig/user6")
    
    println("ConfigId" + configId)
    
    val firstStep: String = addFirstStep(configId)
    
    println("FirstStep " + firstStep)
  }
  
  def prepareSpecsAddStep = {
    
    /*
     * Windows
     * adminId #21:5
     * configId #41:4
     * FirstStep #25:3
     * Component 1 1 #29:10
     * Component 1 2 #30:9
     * Component 1 3 #31:8
     * Linux
     * adminId #21:26
     * configId #41:11
     * FirstStep #25:52
     * Component 1 1 #29:40
     * Component 1 2 #30:34
     * Component 1 3 #31:32
     */
    
    val usePassword = "user8"
    
    registerNewUser(usePassword)
    
    val adminId = login(usePassword)
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, "http://contig/user8")
    
    println("configId " + configId)
    
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
  
  def prepareSpecsConfigTreeEmpty = {
    /*
     * Linux
     * adminId #21:27
     * configId #41:12
     * Windows
     * adminId #21:6
     * configId #41:5
     */
    val usePassword = "user9" 
    registerNewUser(usePassword)
    
    val adminId = login(usePassword)
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, "http://contig/user9")
    
    println("configId " + configId)
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
     * Windows
     * adminId #23:3
     * ConfigId#43:3
     * FirstStep #28:0
     * Component 1 1 #30:6
     * Component 1 2 #31:5
     * Component 1 3 #32:5
     * 
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
  
  def prepareSpecsStepComponentPropertyNameToSchow = {
    
    /*
     * Linux
     * adminId #21:29
     * configId #41:14
     */
    val userPassword = "user11"
    
    registerNewUser(userPassword)
    
    val adminId = login(userPassword)
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, "http://contig/user11")
    
    println("configId " + configId)
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
  
  private def addFirstStep(configId: String): String = {
    val firstStepCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_FIRST_STEP,
        "dto" -> DTONames.CREATE_FIRST_STEP
        ,"params" -> Json.obj(
          "configId" -> configId,
          "nameToShow" -> "FirstStep",
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
  
  private def addComponentToStep(stepId: String): String = {
    val componentCS = Json.obj(
        "dtoId" -> DTOIds.CREATE_COMPONENT,
        "dto" -> DTONames.CREATE_COMPONENT
        ,"params" -> Json.obj(
            "stepId" -> stepId,
            "nameToShow" -> "Component",
            "kind" -> "immutable"
        )
    )
    val componentSC: JsValue = handelMessage(componentCS)
    require((componentSC \ "result" \ "status").asOpt[Boolean].get == true)
    require((componentSC \ "result" \ "message").asOpt[String].get == "Die Komponente wurde hinzugefuegt")
    
    (componentSC \ "result" \ "componentId").asOpt[String].get
  }
}