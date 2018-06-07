package models.preparingConfigs

import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.orient.OrientDynaElementIterable
import scala.collection.JavaConverters._
import org.genericConfig.admin.models.persistence.OrientDB
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.controllers.admin.AdminWeb
import play.api.Logger
import org.genericConfig.admin.controllers.websocket.WebClient

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 16.05.2017
	*/

object PrepareConfigsForSpecsv012 extends AdminWeb with GeneralFunctionToPrepareConfigs{
  
  val userChangeLog012_3 =                       "user16"

  val userTwoFirstStepsForOneConfig =            "user15"
  
  
  def deleteHasDependency(inComponent: String, outComponent: String): Int = {
//    delete Edge hasDependency where @rid in (select @rid from hasDependency where in.nameToShow='Component_1' and out.nameToShow='Component_2')
    
    val graph: OrientGraph = OrientDB.getFactory().getTx
    val sql = s"delete Edge hasDependency where @rid in (select @rid from hasDependency where in.nameToShow='$inComponent' and out.nameToShow='$outComponent')"
    val res: Int = graph
      .command(new OCommandSQL(sql)).execute()
    graph.commit
    res
  }

  def prepareChangeLog012_3(wC: WebClient) = {
//    val graph: OrientGraph = OrientDB.getFactory().getTx
//    val sql: String = s"select count(username) from AdminUser where username like '$userChangeLog012_3'"
//    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
//    graph.commit
//		val count: Int = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
//    if(count == 1 ) {
//      Logger.info(s"Der User $userChangeLog012_3 ist schon erstellt worden")
//    }else {
//			registerNewUser(userChangeLog012_3, wC)
//
//			val adminId = login(userChangeLog012_3, wC)
//
//			println("adminId " + adminId)
//
//			val configId = createNewConfig(adminId, s"http://contig/$userChangeLog012_3", wC)
//
//			println("ConfigId" + configId)
//
//			val firstStepId : String = addFirstStep(configId, webClient = wC)
//
//      println("FirstStep " + firstStepId)
//
//      //FirstStep -> 3 Components
//
//      val componentId_1_1 = addComponentToStep(firstStepId, "Component_1", wC)
//
//      println("Component 1 1 " + componentId_1_1)
//
//      val componentId_1_2 = addComponentToStep(firstStepId, "Component_2", wC)
//
//      println("Component 1 2 " + componentId_1_2)
//
//      val componentId_1_3 = addComponentToStep(firstStepId, "Component_3", wC)
//
//      println("Component 1 3 " + componentId_1_3)
//		}
  }
  
  def prepareTwoFirstStepsForOneConfig(wC: WebClient) = {
//    val graph: OrientGraph = OrientDB.getFactory().getTx
//    val sql: String = s"select count(username) from AdminUser where username like '$userTwoFirstStepsForOneConfig'"
//    val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
//		graph.commit()
//    val count = res.asScala.toList.map(_.asInstanceOf[OrientVertex].getProperty("count").toString().toInt).head
//
//    if(count == 1 ) {
//      Logger.info(s"Der User $userTwoFirstStepsForOneConfig ist schon erstellt worden")
//    }else {
//			registerNewUser(userTwoFirstStepsForOneConfig, wC)
//
//			val adminId = login(userTwoFirstStepsForOneConfig, wC)
//
//			Logger.info("adminId " + adminId)
//
//			val configId = createNewConfig(adminId, s"http://contig/$userTwoFirstStepsForOneConfig", wC)
//
//			Logger.info("ConfigId" + configId)
//
//			val firstStepId : String = addFirstStep(configId, webClient = wC)
//
//      Logger.info("FirstStep " + firstStepId)
//		}
  }
  

}