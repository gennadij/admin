//package models.v015
//
//import org.specs2.mutable.Specification
//import org.specs2.specification.BeforeAfterAll
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import play.api.Logger
//import models.websocket.WebClient
//import models.preparingConfigs.PrepareConfigsForSpecsv015
//import models.preparingConfigs.UsernamesForSpecs
//import play.api.libs.json.Json
//import models.json.DTOIds
//import models.json.DTONames
//import models.json.StatusSuccessfulDependencyCreated
//import org.specs2.runner.JUnitRunner
//import org.junit.runner.RunWith
//import com.google.inject.spi.HasDependencies
//import models.persistence.db.orientdb.HasDependencyEdge
//
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 18.10.2017
// */
//@RunWith(classOf[JUnitRunner])
//class AddingNewDependencyBetweenTwoComponent
//  extends Specification
//	with BeforeAfterAll
//	with GeneralFunctionToPrepareConfigs {
//  
//  val wC = WebClient.init
//  val username = UsernamesForSpecs.addingNewDependencyBetweenTwoComponent
//  
//  def beforeAll = {
//    Logger.info("AddingNewDependencyBetweenTwoComponent")
//    PrepareConfigsForSpecsv015.addingNewDependencyBetweenTwoComponent(wC)
//    HasDependencyEdge.deleteDependency(s"dependency_C2_C3_$username")
//  }
//	
//  def afterAll = {
//  }
//  
//  "Specs fuer die Erzeugung einer Abhaengigkeit zwischen zwei Komponenten" >> {
//    "Erstelle Abhaengigkeit zwischen C2 -> C3" >> {
//      println("""
//                 S1
//               /   \   
//             /      \      
//          /          \          
//        C1            C2         
//         \           /            
//           \       /              
//             \   /                
//              S2                
//            /  \   \
//         /      \    \
//      /          \     \
//    C3           C4     C5
//        
//        Dependency C2 -> C3
//        
//        """)
//    
//    val componentFrom = getComponentId(s"component_1_2_$username")
//    val componentTo = getComponentId(s"component_2_1_$username")
//    
//    val dependencyIn = Json.obj(
//				"dtoId" -> DTOIds.CREATE_DEPENDENCY,
//				"dto" -> DTONames.CREATE_DEPENDENCY,
//				"params" -> Json.obj(
//					  "componentFromId" -> componentFrom,
//            "componentToId" -> componentTo,
//            "dependencyType" -> "test",
//            "visualization" -> "remove",
//            "nameToShow" -> s"dependency_C2_C3_$username"
//				)
//			)
//    
//			val dependencyOut = wC.handleMessage(dependencyIn)
//      
////			Logger.info(dependencyIn.toString())
////			Logger.info(dependencyOut.toString())
//			
//      (dependencyOut \ "dtoId").asOpt[Int].get === DTOIds.CREATE_DEPENDENCY
//			(dependencyOut \ "dto").asOpt[String].get === DTONames.CREATE_DEPENDENCY
//			(dependencyOut \ "result" \ "status").asOpt[String].get === StatusSuccessfulDependencyCreated.status
//			(dependencyOut \ "result" \ "message").asOpt[String].get === StatusSuccessfulDependencyCreated.message
//			(dependencyOut \ "result" \ "dependencyType").asOpt[String].get === "test"
//			(dependencyOut \ "result" \ "visualization").asOpt[String].get === "remove"
//			(dependencyOut \ "result" \ "nameToShow").asOpt[String].get === s"dependency_C2_C3_$username"
//    }
//  }
//}