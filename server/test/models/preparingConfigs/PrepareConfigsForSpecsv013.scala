package models.preparingConfigs

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientGraph, OrientVertex}
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.models.persistence.OrientDB
import scala.collection.JavaConverters._
import play.api.Logger
import org.genericConfig.admin.controllers.websocket.WebClient

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 24.05.2017
	*/

object PrepareConfigsForSpecsv013 extends GeneralFunctionToPrepareConfigs with AdminWeb{

  val userWarningNotificationByDependency =           "user17"
  val userFirstStepConfigIdFaulty =                   "user18"
  val userStepComponentIdFaulty =                     "user19"
  
	def prepareWarningNotificationByDependency(wC: WebClient) = {
    
    val graph: OrientGraph = OrientDB.getFactory().getTx
		val sql: String = s"select count(username) from AdminUser where username like '$userWarningNotificationByDependency'"
		
		val result: Any = try {
		  val count: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
		  graph.commit()
		  count
		}catch{
		  case e: Exception => 
		    graph.rollback()
		    e
		}finally {
		  graph.shutdown()
		}
		
		result match {
		  case e: Exception => Logger.error(e.printStackTrace().toString())
		  case res: OrientDynaElementIterable => {
		    val count = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
		    if(count == 1 ) {
      			Logger.info(s"Der User $userWarningNotificationByDependency ist schon erstellt worden")
      		}else {
      			registerNewUser(userWarningNotificationByDependency, wC)
      
      			val adminId = login(userWarningNotificationByDependency, wC)
      
      			Logger.info("adminId " + adminId)
      
      			val configId = createNewConfig(adminId, s"http://contig/$userWarningNotificationByDependency", wC)
      
      			Logger.info("ConfigId" + configId)
      
      			val firstStepId : String = addFirstStep(configId, webClient = wC)
      
      			Logger.info("FirstStep " + firstStepId)
      
      			//FirstStep -> 3 Components
      
      			val componentId_1_1 = addComponentToStep(firstStepId, "Component_user17_1" , wC)
      
      			Logger.info("Component 1 1 " + componentId_1_1)
      
      			val componentId_1_2 = addComponentToStep(firstStepId, "Component_user17_2", wC)
      
      			Logger.info("Component 1 2 " + componentId_1_2)
      
      			val componentId_1_3 = addComponentToStep(firstStepId, "Component_user17_3", wC)
      
      			Logger.info("Component 1 3 " + componentId_1_3)
      
      			// Component 1 1 -> Step 2
      			val step2Id: String = addStep(componentId_1_1, "default", 1, 1, "Step_user17 2", wC)
      
      			Logger.info("Step 2 " + step2Id)
      
      			// Component 1 2 -> Step 2
      			connectComponentToStep(step2Id, componentId_1_2, wC)
      		}
		    }
		  case _ => Logger.error("Some Error")
		}
    
    
		
		
	}
	
	def prepareFirstStepConfigIdFaulty(wC: WebClient) = {
	  val graph: OrientGraph = OrientDB.getFactory().getTx
		val sql: String = s"select count(username) from AdminUser where username like '$userFirstStepConfigIdFaulty'"
		
		val result: Any = try {
		  val count: OrientDynaElementIterable  = graph.command(new OCommandSQL(sql)).execute()
		  graph.commit()
		  count
		}catch{
		  case e: Exception => 
		    graph.rollback()
		    e
		}finally {
		  graph.shutdown()
		}
		result match {
		  case e: Exception => Logger.error(e.printStackTrace().toString())
		  case res: OrientDynaElementIterable => {
		    val count = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
		    if(count == 1 ) {
			    Logger.info(s"Der User $userFirstStepConfigIdFaulty ist schon erstellt worden")
		    }else {
			    registerNewUser(userFirstStepConfigIdFaulty, wC)

			    val adminId = login(userFirstStepConfigIdFaulty, wC)

			    Logger.info("adminId " + adminId)

			    val configId = createNewConfig(adminId, s"http://contig/$userFirstStepConfigIdFaulty", wC)

			    Logger.info("ConfigId" + configId)
		    }
		  }
		  case _ => Logger.error("Some Error")
		}
		
	}
	
	def prepareStepComponentIdFaulty(wC : WebClient) = {
	  val graph: OrientGraph = OrientDB.getFactory().getTx
		val sql: String = s"select count(username) from AdminUser where username like '$userStepComponentIdFaulty'"
		
		val result: Any = try {
		  val count: OrientDynaElementIterable  = graph.command(new OCommandSQL(sql)).execute()
		  graph.commit()
		  count
		}catch{
		  case e: Exception => 
		    graph.rollback()
		    e
		}finally {
		  graph.shutdown()
		}
		result match {
		  case e: Exception => Logger.error(e.printStackTrace().toString())
		  case res: OrientDynaElementIterable => {
		    val count = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
		    if(count == 1 ) {
			    Logger.info(s"Der User $userStepComponentIdFaulty ist schon erstellt worden")
		    }else {
			    registerNewUser(userStepComponentIdFaulty, wC)

    			val adminId = login(userStepComponentIdFaulty, wC)
    
    			Logger.info("adminId " + adminId)
    
    			val configId = createNewConfig(adminId, s"http://contig/$userStepComponentIdFaulty", wC)
    
    			Logger.info("ConfigId" + configId)
    			
    			val firstStepId = addFirstStep(configId, 1, 1, "FirstStep", wC)
    			
    			Logger.info("FirstStepId " + firstStepId)
    			
    			val componentId = addComponentToStep(firstStepId, "Component_1_1", wC)
    			
    			Logger.info("Component_1_1 " + componentId)
		    }
		  }
		  case _ => Logger.error("Some Error")
		}
	}
}
