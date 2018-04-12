package models.preparingConfigs

import scala.collection.JavaConverters._

import org.genericConfig.admin.controllers.admin.AdminWeb
import org.genericConfig.admin.controllers.websocket.WebClient
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import org.genericConfig.admin.models.persistence.OrientDB
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import play.api.Logger

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 20.10.2017
	*/

object PrepareConfigsForSpecsv016 extends AdminWeb with GeneralFunctionToPrepareConfigs{
  
  def configurationForWebClient(wC: WebClient) = {
    
    val username = UsernamesForSpecs.createConfigForConfigurator
    
    val graph: OrientGraph = OrientDB.getFactory.getTx
    
    val sql: String = s"select count(username) from AdminUser where username like '$username'"
    
    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    graph.commit
		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
    if(count == 1 ) {
      Logger.info(s"Der User $username ist schon erstellt worden")
    }else{
      registerNewUser(username, wC)
    
      val adminId = login(username, wC)
      
      val configUrl = s"http://contig1/$username"
      
      val configId = createNewConfig(adminId, configUrl, wC)
      
      val firstStepId = addFirstStep(configId, 1, 2, s"S1_$username", wC)
      
      val component_1_1 = addComponentToStep(firstStepId, s"C_1_1_$username", wC)
      
      val component_1_2 = addComponentToStep(firstStepId, s"C_1_2_$username", wC)
      
      val component_1_3 = addComponentToStep(firstStepId, s"C_1_3_$username", wC)
      
      val step_2 = addStep(component_1_1, "default", 1, 1, s"S2_$username", wC)
      
      connectComponentToStep(step_2, component_1_2, wC)
      
      val component_2_1 = addComponentToStep(step_2, s"C_2_1_$username", wC)
      
      val component_2_2 = addComponentToStep(step_2, s"C_2_2_$username", wC)
      
      val s_3_withoutStepId = addStep(component_1_3, "default", 1, 1, s"S3_$username", wC)
      
      val stepId_3_withDependencies = visualProposal("remove", wC)
      
      connectComponentToStep(stepId_3_withDependencies, component_2_1, wC)
      
      connectComponentToStep(stepId_3_withDependencies, component_2_2, wC)
      
      val component_3_1 = addComponentToStep(stepId_3_withDependencies, s"C_3_1_$username", wC)
      
      val component_3_2 = addComponentToStep(stepId_3_withDependencies, s"C_3_2_$username", wC)
      
      val component_3_3 = addComponentToStep(stepId_3_withDependencies, s"C_3_3_$username", wC)
      
      val component_3_4 = addComponentToStep(stepId_3_withDependencies, s"C_3_4_$username", wC)
    
    }
  }
}