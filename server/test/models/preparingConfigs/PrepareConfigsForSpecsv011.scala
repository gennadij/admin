package models.preparingConfigs

import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import play.api.libs.json.Json
import org.genericConfig.admin.controllers.admin.AdminWeb
import org.specs2.Specification
import play.api.libs.json.JsValue
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.persistence.OrientDB

import scala.collection.JavaConverters._
import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.Direction
import org.genericConfig.admin.models.persistence.db.orientdb.PropertyKey

import scala.collection.JavaConversions._
import play.api.Logger
import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.persistence.Database

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 15.05.2017
	*/

object PrepareConfigsForSpecsv011 extends AdminWeb with GeneralFunctionToPrepareConfigs {
  
  val userWithAlredyExistingUser =         "userExist"
  val userLogin =                          "user2"
  val userAddingNewConfig =                "user3"
  val userAddingFirstStep =                "user4"
  val userAddingComponentWithFirstStep =   "user5"
  val userAddingNewComponent =             "user6"
  val userConfigTreeFirstStep3Components = "user7"
  val userAddingStep =                     "user8"
  val userConfigTreeEmpty =                "user9"
  val userStepComponentPropertyNameToSchow = "user11"
  val userDefectAddingStep =               "user12_v0.1.5"
  val userAddingDefectComponent =             "user6_v0.1.5"
  
  
  def prepareAddingNewConfig(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userAddingNewConfig'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userAddingNewConfig ist schon erstellt worden")
    }else{
      registerNewUser(userAddingNewConfig, wC)
    
      login(userAddingNewConfig, wC)
    }
  }
  
  def prepareLogin(wC : WebClient) = {
    
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userLogin'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userLogin ist schon erstellt worden")
    }else{
      registerNewUser(userLogin, wC)
    
      val adminId: String = login(userLogin, wC)
    
      println("adminId " + adminId)
    
      val configId: String = createNewConfig(adminId, "http://contig1/user2", wC)
    
      println("configId " + configId)
    }
  }
  
  def prepareWithAlredyExistingUser(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userWithAlredyExistingUser'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userWithAlredyExistingUser ist schon erstellt worden")
    }else {
      registerNewUser(userWithAlredyExistingUser, wC)
    }
  }
  
  def prepareAddingComponentWithFirstStep(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userAddingComponentWithFirstStep'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userAddingComponentWithFirstStep ist schon erstellt worden")
    }else {
      registerNewUser(userAddingComponentWithFirstStep, wC)
    
      val adminId = login(userAddingComponentWithFirstStep, wC)
    
      println("adminId " + adminId)
    
      val configId = createNewConfig(adminId, "http://contig/user5", wC)
    
      println("ConfigId" + configId)
    }
  }
  
  def prepareAddingFirstStep(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userAddingFirstStep'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userAddingFirstStep ist schon erstellt worden")
    }else {
      registerNewUser(userAddingFirstStep, wC)
    
      val adminId: String = login(userAddingFirstStep, wC)
    
      println("adminId " + adminId)
    
      val configId = createNewConfig(adminId, s"http://contig/$userAddingFirstStep", wC)
    
      println("ConfigId" + configId)
    }
  }
  
  def prepareAddingNewComponent(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userAddingNewComponent'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userAddingNewComponent ist schon erstellt worden")
    }else {
      registerNewUser(userAddingNewComponent, wC)
    
    val adminId = login(userAddingNewComponent, wC)
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, s"http://contig/$userAddingNewComponent", wC)
    
    println("ConfigId" + configId)
    
    val firstStep: String = addFirstStep(configId, webClient = wC)
    
    println("FirstStep " + firstStep)
    }
    
    
  }
  
  def prepareAddingDefectComponent(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userAddingDefectComponent'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userAddingDefectComponent ist schon erstellt worden")
    }else {
      registerNewUser(userAddingDefectComponent, wC)
    
    val adminId = login(userAddingDefectComponent, wC)
    
    println("adminId " + adminId)
    
    val configId: String = createNewConfig(adminId, s"http://contig/$userAddingDefectComponent", wC)
    
    println("ConfigId" + configId)
    
    val firstStep: String = addFirstStep(configId, webClient = wC)
    
    println("FirstStep " + firstStep)
    }
    
    
  }
  
  def prepareAddStep(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userAddingStep'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userAddingStep ist schon erstellt worden")
    }else {
    
      registerNewUser(userAddingStep, wC)
      
      val adminId = login(userAddingStep, wC)
      
      println("adminId " + adminId)
      
      val configId: String = createNewConfig(adminId, s"http://contig/$userAddingStep", wC)
      
      println("configId " + configId)
      
      val firstStepId : String = addFirstStep(configId, webClient = wC)
      
      println("FirstStep " + firstStepId)
      
      //FirstStep -> 3 Components
      
      val componentId_1_1 = addComponentToStep(firstStepId, wC = wC)
      
      println("Component 1 1 " + componentId_1_1)
      
      val componentId_1_2 = addComponentToStep(firstStepId, wC = wC)
      
      println("Component 1 2 " + componentId_1_2)
      
      val componentId_1_3 = addComponentToStep(firstStepId, wC = wC)
      
      println("Component 1 3 " + componentId_1_3)
    }
  }
  
  def prepareDefectAddStep(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userDefectAddingStep'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userDefectAddingStep ist schon erstellt worden")
    }else {
    
      registerNewUser(userDefectAddingStep, wC)
      
      val adminId = login(userDefectAddingStep, wC)
      
      println("adminId " + adminId)
      
      val configId: String = createNewConfig(adminId, s"http://contig/$userDefectAddingStep", wC)
      
      println("configId " + configId)
      
      val firstStepId : String = addFirstStep(configId, webClient = wC)
      
      println("FirstStep " + firstStepId)
      
      //FirstStep -> 3 Components
      
      val componentId_1_1 = addComponentToStep(firstStepId, wC = wC)
      
      println("Component 1 1 " + componentId_1_1)
      
      val componentId_1_2 = addComponentToStep(firstStepId, wC = wC)
      
      println("Component 1 2 " + componentId_1_2)
      
      val componentId_1_3 = addComponentToStep(firstStepId, wC = wC)
      
      println("Component 1 3 " + componentId_1_3)
    }
  }
  
  def prepareConfigTreeEmpty(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userConfigTreeEmpty'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userConfigTreeEmpty ist schon erstellt worden")
    }else {
      registerNewUser(userConfigTreeEmpty, wC)
    
      val adminId = login(userConfigTreeEmpty, wC)
    
      Logger.info("adminId " + adminId)
    
      val configId: String = createNewConfig(adminId, s"http://contig/$userConfigTreeEmpty", wC)
    
      Logger.info("configId " + configId)
    }
    
  }
  
  def prepareConfigTreeFirstStep3Components(wC: WebClient) = {
    val graph: OrientGraph = OrientDB.getFactory.getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userConfigTreeFirstStep3Components'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userConfigTreeFirstStep3Components ist schon erstellt worden")
    }else {
      registerNewUser(userConfigTreeFirstStep3Components, wC)
    
      val adminId = login(userConfigTreeFirstStep3Components, wC)
      
      println("adminId " + adminId)
      
      val configId: String = createNewConfig(adminId, s"http://contig/$userConfigTreeFirstStep3Components", wC)
      
      println("ConfigId" + configId)
      
      val firstStepId : String = addFirstStep(configId, webClient = wC)
      
      println("FirstStep " + firstStepId)
      
      //FirstStep -> 3 Components
      
      val componentId_1_1 = addComponentToStep(firstStepId, wC = wC)
      
      println("Component 1 1 " + componentId_1_1)
      
      val componentId_1_2 = addComponentToStep(firstStepId, wC = wC)
      
      println("Component 1 2 " + componentId_1_2)
      
      val componentId_1_3 = addComponentToStep(firstStepId, wC = wC)
      
      println("Component 1 3 " + componentId_1_3)
    }
  }
  
  def prepareStepComponentPropertyNameToSchow(wC: WebClient) = {
    
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val sql: String = s"select count(username) from AdminUser where username like '$userStepComponentPropertyNameToSchow'"
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
		val count = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $userStepComponentPropertyNameToSchow ist schon erstellt worden")
    }else {
    
      registerNewUser(userStepComponentPropertyNameToSchow, wC)
      
      val adminId = login(userStepComponentPropertyNameToSchow, wC)
      
      println("adminId " + adminId)
      
      val configId: String = createNewConfig(adminId, s"http://contig/$userStepComponentPropertyNameToSchow", wC)
      
      println("configId " + configId)
    }
  }
  
  def deleteConfigVertex(username: String): Int = {
    val sql: String = s"DELETE VERTEX Config where @rid IN (SELECT OUT('hasConfig') FROM AdminUser WHERE username='$username')"
      val graph: OrientGraph = Database.getFactory().getTx()
      val res: Int = graph
        .command(new OCommandSQL(sql)).execute()
      graph.commit
      res
  }
  
  def deleteAdmin(username: String): Int = {
    val graph: OrientGraph = Database.getFactory().getTx()
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX AdminUser where username='$username'")).execute()
    graph.commit
    res
  }
}