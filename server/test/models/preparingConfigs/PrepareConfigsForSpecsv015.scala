package models.preparingConfigs

import scala.collection.JavaConverters._

import org.genericConfig.admin.controllers.converter.MessageHandler
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import org.genericConfig.admin.models.persistence.OrientDB
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import play.api.Logger
import org.genericConfig.admin.controllers.websocket.WebClient

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 29.07.2017
	*/

object PrepareConfigsForSpecsv015 extends MessageHandler with GeneralFunctionToPrepareConfigs{

  val userConfigurationForWebClient = "user20_v015"
  val userConfigurationForCollisionWith2Steps = "user21_v015"

  def configurationForWebClient(wC: WebClient) = {
//    val graph: OrientGraph = OrientDB.getFactory.getTx
//
//    val sql: String = s"select count(username) from AdminUser where username like '$userConfigurationForWebClient'"
//
//    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
//    graph.commit
//		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
//    if(count == 1 ) {
//      Logger.info(s"Der User $userConfigurationForWebClient ist schon erstellt worden")
//    }else{
//      registerNewUser(userConfigurationForWebClient, wC)
//
//      val adminId = login(userConfigurationForWebClient, wC)
//
//      val configUrl = s"http://contig1/$userConfigurationForWebClient"
//
//      val configId = createNewConfig(adminId, configUrl, wC)
//
//      val firstStepId = addFirstStep(configId, 1, 1, "firstStep", wC)
//
//      val component_1_1 = addComponentToStep(firstStepId, "component_1_1", wC)
//
//      val component_1_2 = addComponentToStep(firstStepId, "component_1_2", wC)
//
//      val component_1_3 = addComponentToStep(firstStepId, "component_1_3", wC)
//
//      val step_2 = addStep(component_1_1, "immutable", 1, 1, "step_2", wC)
//
//      connectComponentToStep(step_2, component_1_2, wC)
//
//      connectComponentToStep(step_2, component_1_3, wC)
//
//      val component_2_1 = addComponentToStep(step_2, "component_2_1", wC)
//
//      val component_2_2 = addComponentToStep(step_2, "component_2_2", wC)
//
//      val component_2_3 = addComponentToStep(step_2, "component_2_3", wC)
//    }
  }
  
  def configurationForCollisionWith2Steps(wC: WebClient) = {
    
//    val sql: String = s"select count(username) from AdminUser where username like '$userConfigurationForCollisionWith2Steps'"
//
//    val result: Any = OrientDB.executeSQLQuery(sql)
//
//    result match {
//		  case e: Exception => Logger.error(e.printStackTrace().toString())
//		  case res: OrientDynaElementIterable => {
//        val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
//        if(count == 1 ) {
//          Logger.info(s"Der User $userConfigurationForCollisionWith2Steps ist schon erstellt worden")
//        }else{
//          registerNewUser(userConfigurationForCollisionWith2Steps, wC)
//
//          val adminId = login(userConfigurationForCollisionWith2Steps, wC)
//
//          val configUrl = s"http://config/$userConfigurationForCollisionWith2Steps"
//
//          val configId = createNewConfig(adminId, configUrl, wC)
//
//          val firstStepId = addFirstStep(configId, 1, 2, "firstStep_user21_v015", wC)
//
//          val component_1_1 = addComponentToStep(firstStepId, "component_1_1_user21_v015", wC)
//
//          val component_1_2 = addComponentToStep(firstStepId, "component_1_2_user21_v015", wC)
//
//          val component_1_3 = addComponentToStep(firstStepId, "component_1_3_user21_v015", wC)
//
//          val step_2 = addStep(component_1_1, "immutable", 1, 1, "step_2_user21_v015", wC)
//
//          connectComponentToStep(step_2, component_1_2, wC)
//        }
//      }
//		  case _ => Logger.error("Some Error")
//		}
  }
  
  def selectionCriterium13ForAdditionalStepInLevelCSSpec(wC: WebClient) = {
    
//    val username = UsernamesForSpecs.userSelectionCriterium13ForAdditionalStepInLevelCS
//
//    val sql: String = s"select count(username) from AdminUser where username like '$username'"
//
//    val result: Any = OrientDB.executeSQLQuery(sql)
//
//    result match {
//		  case e: Exception => Logger.error(e.printStackTrace().toString())
//		  case res: OrientDynaElementIterable => {
//        val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
//        if(count == 1 ) {
//          Logger.info(s"Der User $username ist schon erstellt worden")
//        }else{
//          registerNewUser(username, wC)
//
//          val adminId = login(username, wC)
//
//          val configUrl = s"http://config/$username"
//
//          val configId = createNewConfig(adminId, configUrl, wC)
//
//          val firstStepId = addFirstStep(configId, 1, 3, "firstStep_user22_v015", wC)
//
//          val component_1_1 = addComponentToStep(firstStepId, "component_1_1_user22_v015", wC)
//
//          val component_1_2 = addComponentToStep(firstStepId, "component_1_2_user22_v015", wC)
//
//          val component_1_3 = addComponentToStep(firstStepId, "component_1_3_user22_v015", wC)
//
//          val step_2 = addStep(component_1_1, "immutable", 1, 1, "step_2_user22_v015", wC)
//
//          connectComponentToStep(step_2, component_1_2, wC)
//        }
//      }
//		  case _ => Logger.error("Some Error")
//		}
  }
  
  def selectionCriterium31ForAdditionalStepInLevelCSSpec(wC: WebClient) = {
    
//    val username = UsernamesForSpecs.userSelectionCriterium31ForAdditionalStepInLevelCS
//
//    val sql: String = s"select count(username) from AdminUser where username like '$username'"
//
//    val result: Any = OrientDB.executeSQLQuery(sql)
//
//    result match {
//		  case e: Exception => Logger.error(e.printStackTrace().toString())
//		  case res: OrientDynaElementIterable => {
//        val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
//        if(count == 1 ) {
//          Logger.info(s"Der User $username ist schon erstellt worden")
//        }else{
//          registerNewUser(username, wC)
//
//          val adminId = login(username, wC)
//
//          val configUrl = s"http://config/$username"
//
//          val configId = createNewConfig(adminId, configUrl, wC)
//
//          val firstStepId = addFirstStep(configId, 3, 1, s"firstStep_$username", wC)
//
//          val component_1_1 = addComponentToStep(firstStepId, s"component_1_1_$username", wC)
//
//          val component_1_2 = addComponentToStep(firstStepId, s"component_1_2_$username", wC)
//
//          val component_1_3 = addComponentToStep(firstStepId, s"component_1_3_$username", wC)
//
//          val step_2 = addStep(component_1_1, "immutable", 1, 1, s"step_2_$username", wC)
//
//          connectComponentToStep(step_2, component_1_2, wC)
//        }
//      }
//		  case _ => Logger.error("Some Error")
//		}
  }
  
  def selectionCriterium22ForAdditionalStepInLevelCSSpec(wC: WebClient) = {
    
//    val username = UsernamesForSpecs.userSelectionCriterium22ForAdditionalStepInLevelCS
//
//    val sql: String = s"select count(username) from AdminUser where username like '$username'"
//
//    val result: Any = OrientDB.executeSQLQuery(sql)
//
//    result match {
//		  case e: Exception => Logger.error(e.printStackTrace().toString())
//		  case res: OrientDynaElementIterable => {
//        val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
//        if(count == 1 ) {
//          Logger.info(s"Der User $username ist schon erstellt worden")
//        }else{
//          registerNewUser(username, wC)
//
//          val adminId = login(username, wC)
//
//          val configUrl = s"http://config/$username"
//
//          val configId = createNewConfig(adminId, configUrl, wC)
//
//          val firstStepId = addFirstStep(configId, 2, 2, s"firstStep_$username", wC)
//
//          val component_1_1 = addComponentToStep(firstStepId, s"component_1_1_$username", wC)
//
//          val component_1_2 = addComponentToStep(firstStepId, s"component_1_2_$username", wC)
//
//          val component_1_3 = addComponentToStep(firstStepId, s"component_1_3_$username", wC)
//
//          val step_2 = addStep(component_1_1, "immutable", 1, 1, s"step_2_$username", wC)
//
//          connectComponentToStep(step_2, component_1_2, wC)
//        }
//      }
//		  case _ => Logger.error("Some Error")
//		}
  }
  
  def selectionCriterium11ForAdditionalStepInLevelCSSpec(wC: WebClient) = {
    
//    val username = UsernamesForSpecs.userSelectionCriterium11ForAdditionalStepInLevelCS
//
//    val sql: String = s"select count(username) from AdminUser where username like '$username'"
//
//    val result: Any = OrientDB.executeSQLQuery(sql)
//
//    result match {
//		  case e: Exception => Logger.error(e.printStackTrace().toString())
//		  case res: OrientDynaElementIterable => {
//        val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
//        if(count == 1 ) {
//          Logger.info(s"Der User $username ist schon erstellt worden")
//        }else{
//          registerNewUser(username, wC)
//
//          val adminId = login(username, wC)
//
//          val configUrl = s"http://config/$username"
//
//          val configId = createNewConfig(adminId, configUrl, wC)
//
//          val firstStepId = addFirstStep(configId, 1, 1, s"firstStep_$username", wC)
//
//          val component_1_1 = addComponentToStep(firstStepId, s"component_1_1_$username", wC)
//
//          val component_1_2 = addComponentToStep(firstStepId, s"component_1_2_$username", wC)
//
//          val component_1_3 = addComponentToStep(firstStepId, s"component_1_3_$username", wC)
//
//          val step_2 = addStep(component_1_1, "immutable", 1, 1, s"step_2_$username", wC)
//
//          connectComponentToStep(step_2, component_1_2, wC)
//        }
//      }
//		  case _ => Logger.error("Some Error")
//		}
  }
  
  
//  def severalInstancesForWebClientSpecWC1(wC: WebClient) = {
//    val username = UsernamesForSpecs.severalInstancesForWebClientWC1
//    
//    val sql: String = s"select count(username) from AdminUser where username like '$username'"
//    
//    val result: Any = OrientDB.executeSQLQuery(sql)
//    
//    result match {
//		  case e: Exception => Logger.error(e.printStackTrace().toString())
//		  case res: OrientDynaElementIterable => {
//        val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
//        if(count == 1 ) {
//          Logger.info(s"Der User $username ist schon erstellt worden")
//        }else{
//          registerNewUser(username, wC)
//        
//          val adminId = login(username, wC)
//          
//          val configUrl = s"http://config/$username"
//          
//          val configId = createNewConfig(adminId, configUrl, wC)
//          
//          val firstStepId = addFirstStep(configId, 1, 1, s"firstStep_$username", wC)
//          
//          val component_1_1 = addComponentToStep(firstStepId, s"component_1_1_$username", wC)
//          
//          val component_1_2 = addComponentToStep(firstStepId, s"component_1_2_$username", wC)
//          
//          val component_1_3 = addComponentToStep(firstStepId, s"component_1_3_$username", wC)
//        }
//      }
//		  case _ => Logger.error("Some Error")
//		}
//  }
//  
//  def severalInstancesForWebClientSpecWC2(wC: WebClient) = {
//    val username = UsernamesForSpecs.severalInstancesForWebClientWC2
//    
//    val sql: String = s"select count(username) from AdminUser where username like '$username'"
//    
//    val result: Any = OrientDB.executeSQLQuery(sql)
//    
//    result match {
//		  case e: Exception => Logger.error(e.printStackTrace().toString())
//		  case res: OrientDynaElementIterable => {
//        val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
//        if(count == 1 ) {
//          Logger.info(s"Der User $username ist schon erstellt worden")
//        }else{
//          registerNewUser(username, wC)
//        
//          val adminId = login(username, wC)
//        }
//      }
//		  case _ => Logger.error("Some Error")
//		}
//  }
}