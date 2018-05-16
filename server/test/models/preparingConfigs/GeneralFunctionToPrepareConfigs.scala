package models.preparingConfigs

import play.api.libs.json.Json
import play.api.libs.json.JsValue
import org.genericConfig.admin.controllers.admin.AdminWeb
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.genericConfig.admin.models.persistence.OrientDB
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.Direction
import org.genericConfig.admin.models.persistence.db.orientdb.PropertyKey
import org.genericConfig.admin.models.json.StatusSuccessfulFirstStepCreated
import play.api.Logger
import org.genericConfig.admin.models.json.StatusSuccessfulRegist
import org.genericConfig.admin.models.json.StatusSuccessfulLogin
import org.genericConfig.admin.models.json.StatusSuccessfulConfig
import org.genericConfig.admin.models.json.StatusSuccessfulComponentCreated
import org.genericConfig.admin.models.json.StatusSuccessfulStepCreated
import org.genericConfig.admin.models.json.StatusSuccessfulConnectionComponentToStep
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.wrapper.step.StepIn
import org.genericConfig.admin.models.json.StatusSuccessfulAdditionalStepInLevelCSCreated
import org.genericConfig.admin.shared.common.json.JsonNames

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 12.06.2017
	*/

trait GeneralFunctionToPrepareConfigs extends AdminWeb {
  
  def getComponentsFromFirstStep(stepId: String): List[String] = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
    val vStep: OrientVertex = graph.getVertex(stepId)
    
    val eHasComponents: List[Edge] = vStep.getEdges(Direction.OUT, PropertyKey.EDGE_HAS_COMPONENT).asScala.toList
    
    val vComponents: List[Vertex] = eHasComponents map {_.getVertex(Direction.IN)}
    
    vComponents map {_.getId.toString()}
  }
  
  def getFirstStep(username: String): String = {
//    select out('hasConfig').out('hasFirstStep') from AdminUser where username='user6'
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val sql: String = s"select expand(out('hasConfig').out('hasFirstStep')) from AdminUser where username='$username'"
    val res: OrientDynaElementIterable = graph
        .command(new OCommandSQL(sql)).execute()
      graph.commit
    
      val firstStep = res.asScala.toList.head
      firstStep.asInstanceOf[OrientVertex].getIdentity.toString()
  }
  
  
  def registerNewUser(userPassword: String, webClient: WebClient) = {
    val registerCS = Json.obj(
        "json" -> JsonNames.REGISTRATION
        ,"params" -> Json.obj(
            "username" -> userPassword,
            "password"-> userPassword
        )
      )
    val registerSC = webClient.handleMessage(registerCS)
    
    Logger.info("registerCS " + registerCS)
    Logger.info("registerSC " + registerSC)
    require((registerSC \ "result" \ "username").asOpt[String].get == userPassword, s"Username: $userPassword")
  }
  
  def login (userPassword: String, webClient: WebClient): String = {
    val loginCS = Json.obj(
        "json" -> JsonNames.LOGIN
        ,"params" -> Json.obj(
            "username" -> userPassword,
            "password" -> userPassword
        )
    )
    
    val loginSC = webClient.handleMessage(loginCS)
    
    (loginSC \ "result" \ "adminId").asOpt[String].get
  }
  
	def loginForConfigId(user: String, webClient: WebClient): String = {
		val jsonClientServer = Json.obj(
			"json" -> JsonNames.LOGIN
			,"params" -> Json.obj(
				"username" -> user,
				"password"-> user
			)
		)
		val jsonServerClient: JsValue = webClient.handleMessage(jsonClientServer)
		    require((jsonServerClient \ "result" \ "status").asOpt[String].get == StatusSuccessfulLogin.status)
		val configId = ((jsonServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get
		configId
	}
  
  def createNewConfig(adminId: String, configUrl: String, webClient: WebClient): String = {
    val createConfigCS = Json.obj(
          "json" -> JsonNames.ADD_CONFIG
          , "params" -> Json.obj(
              "adminId" -> adminId,
              "configUrl" -> configUrl
          )
      )
      val createConfigSC = webClient.handleMessage(createConfigCS)
      require((createConfigSC \ "result" \ "status").asOpt[String].get == StatusSuccessfulConfig.status, (createConfigSC \ "result" \ "status").asOpt[String].get.toString())
      require((createConfigSC \ "result" \ "message").asOpt[String].get == StatusSuccessfulConfig.message)
      
      (createConfigSC \ "result" \ "configId").asOpt[String].get
  }
  
  def addFirstStep(configId: String, min: Int = 1, max: Int = 1, nameToShow: String = "FirstStep", webClient: WebClient): String = {
    val firstStepCS = Json.obj(
        "json" -> JsonNames.ADD_FIRST_STEP
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
      val firstStepSC: JsValue = webClient.handleMessage(firstStepCS)
      
      require((firstStepSC \ "result" \ "status").asOpt[String].get == StatusSuccessfulFirstStepCreated.status)
      
      require((firstStepSC \ "result" \ "message").asOpt[String].get == StatusSuccessfulFirstStepCreated.message)
      
      (firstStepSC \ "result" \ "stepId").asOpt[String].get
  }
  
  def addComponentToStep(stepId: String, nameToShow: String = "Component", wC: WebClient): String = {
    val componentCS = Json.obj(
        "json" -> JsonNames.CREATE_COMPONENT
        ,"params" -> Json.obj(
            "stepId" -> stepId,
            "nameToShow" -> nameToShow,
            "kind" -> "immutable"
        )
    )
    val componentSC: JsValue = wC.handleMessage(componentCS)
    require((componentSC \ "result" \ "status").asOpt[String].get == StatusSuccessfulComponentCreated.status)
    require((componentSC \ "result" \ "message").asOpt[String].get == StatusSuccessfulComponentCreated.message)
    
    (componentSC \ "result" \ "componentId").asOpt[String].get
  }

	def addStep(componentId: String, kind: String, min: Int, max: Int, nameToShow: String, webClient: WebClient): String = {
		//TODO erweitern auf die Dependencies
	  
	  val stepCS = Json.obj(
			"json" -> JsonNames.ADD_STEP,
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
		val stepSC = webClient.handleMessage(stepCS)
//		require((stepSC \ "result" \ "status").asOpt[String].get == StatusSuccessfulStepCreated.status)
		(stepSC \ "result" \ "stepId").asOpt[String].get
	}
	
	def visualProposal(visualProposal: String, wC: WebClient): String = {
	  val visualProposal = Json.obj(
				  "json" -> JsonNames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL,
				  "params" -> Json.obj(
				          "selectedVisualProposal" -> "remove"
				  )
			)
		val stepSCWithDependencies = wC.handleMessage(visualProposal)
		
		require((stepSCWithDependencies \ "result" \ "status").asOpt[String].get == StatusSuccessfulAdditionalStepInLevelCSCreated.status)
		(stepSCWithDependencies \ "result" \ "stepId").asOpt[String].get
	}

	def connectComponentToStep(stepId: String, componentId: String, wC: WebClient): Unit = {
		val connectionComponentToStepCS = Json.obj(
			"json" -> JsonNames.CONNECTION_COMPONENT_TO_STEP,
			"params" -> Json.obj(
				"componentId" -> componentId,
				"stepId" -> stepId
			)
		)
		val connectionComponentToStepSC = wC.handleMessage(connectionComponentToStepCS)

		require((connectionComponentToStepSC \ "result" \ "status").asOpt[String].get == 
		  StatusSuccessfulConnectionComponentToStep.status)
	}

	def getComponentId(nameToShow: String): String = {
		val graph: OrientGraph = OrientDB.getFactory().getTx
		val sql: String = s"select from Component where nameToShow LIKE '$nameToShow'"
		val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
		res.asScala.toList.map(_.asInstanceOf[OrientVertex].getIdentity).head.toString()
	}
}