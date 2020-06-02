package models.preparingConfigs

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.{Direction, Edge, Vertex}
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.converter.MessageHandler
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.persistence.OrientDB
import org.genericConfig.admin.models.persistence.orientdb.PropertyKeys

import scala.collection.JavaConverters._

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 12.06.2017
	*/

trait GeneralFunctionToPrepareConfigs extends MessageHandler {
  
  def getComponentsFromFirstStep(stepId: String): List[String] = {
    val graph: OrientGraph = OrientDB.getFactory().getTx
    
    val vStep: OrientVertex = graph.getVertex(stepId)
    
    val eHasComponents: List[Edge] = vStep.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList
    
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
  
  
//  def registerNewUser(userPassword: String, webClient: WebClient) = {
//    val registerCS = Json.obj(
//        "json" -> JsonNames.ADD_USER
//        ,"params" -> Json.obj(
//            "username" -> userPassword,
//            "password"-> userPassword
//        )
//      )
//    val registerSC = webClient.handleMessage(registerCS)
//
//    Logger.info("registerCS " + registerCS)
//    Logger.info("registerSC " + registerSC)
//    require((registerSC \ "result" \ "username").asOpt[String].get == userPassword, s"Username: $userPassword")
//  }
//
//  def login (userPassword: String, webClient: WebClient): String = {
//    val loginCS = Json.obj(
//        "json" -> JsonNames.GET_USER
//        ,"params" -> Json.obj(
//            "username" -> userPassword,
//            "password" -> userPassword
//        )
//    )
//
//    val loginSC = webClient.handleMessage(loginCS)
//
//    (loginSC \ "result" \ "adminId").asOpt[String].get
//  }
  
//	def loginForConfigId(user: String, webClient: WebClient): String = {
//		val jsonClientServer = Json.obj(
//			"json" -> JsonNames.GET_USER
//			,"params" -> Json.obj(
//				"username" -> user,
//				"password"-> user
//			)
//		)
//		val jsonServerClient: JsValue = webClient.handleMessage(jsonClientServer)
//		val configId = ((jsonServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get
//		configId
//	}
  

  
  def addFirstStep(configId: String, min: Int = 1, max: Int = 1, nameToShow: String = "FirstStep", webClient: WebClient): String = {
//    val firstStepCS = Json.obj(
//        "json" -> JsonNames.ADD_FIRST_STEP
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
//      val firstStepSC: JsValue = webClient.handleMessage(firstStepCS)
//
//      (firstStepSC \ "result" \ "stepId").asOpt[String].get
		???
  }
  
//  def addComponentToStep(stepId: String, nameToShow: String = "Component", wC: WebClient): String = {
//    val componentCS = Json.obj(
//        "json" -> JsonNames.ADD_COMPONENT
//        ,"params" -> Json.obj(
//            "stepId" -> stepId,
//            "nameToShow" -> nameToShow,
//            "kind" -> "immutable"
//        )
//    )
//    val componentSC: JsValue = wC.handleMessage(componentCS)
//
//    (componentSC \ "result" \ "componentId").asOpt[String].get
//  }

//	def addStep(componentId: String, kind: String, min: Int, max: Int, nameToShow: String, webClient: WebClient): String = {
//
//	  val stepCS = Json.obj(
//			"json" -> JsonNames.ADD_STEP,
//			"params" -> Json.obj(
//				"componentId" -> componentId,
//				"nameToShow" -> nameToShow,
//				"kind" -> kind,
//				"selectionCriterium" -> Json.obj(
//					"min" -> min,
//					"max" -> max
//				)
//			)
//		)
//		val stepSC = webClient.handleMessage(stepCS)
////		require((stepSC \ "result" \ "status").asOpt[String].get == StatusSuccessfulStepCreated.status)
//		(stepSC \ "result" \ "stepId").asOpt[String].get
//	}
	
//	def visualProposal(visualProposal: String, wC: WebClient): String = {
//	  val visualProposal = Json.obj(
//				  "json" -> JsonNames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL,
//				  "params" -> Json.obj(
//				          "selectedVisualProposal" -> "remove"
//				  )
//			)
//		val stepSCWithDependencies = wC.handleMessage(visualProposal)
//
//		(stepSCWithDependencies \ "result" \ "stepId").asOpt[String].get
//	}

//	def connectComponentToStep(stepId: String, componentId: String, wC: WebClient): Unit = {
//		val connectionComponentToStepCS = Json.obj(
//			"json" -> JsonNames.CONNECTION_COMPONENT_TO_STEP,
//			"params" -> Json.obj(
//				"componentId" -> componentId,
//				"stepId" -> stepId
//			)
//		)
//		val connectionComponentToStepSC = wC.handleMessage(connectionComponentToStepCS)
//
//	}

	def getComponentId(nameToShow: String): String = {
		val graph: OrientGraph = OrientDB.getFactory().getTx
		val sql: String = s"select from Component where nameToShow LIKE '$nameToShow'"
		val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
		res.asScala.toList.map(_.asInstanceOf[OrientVertex].getIdentity).head.toString()
	}
}