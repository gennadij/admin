//package models.v015
//
//import org.junit.runner.RunWith
//import org.specs2.runner.JUnitRunner
//import org.specs2.mutable.Specification
//import models.tempConfig.TempConfigurations
//import play.api.Logger
//import models.json.step.JsonStepIn
//import models.json.step.JsonStepParams
//import models.json.step.JsonSelectionCriterium
//import models.wrapper.Wrapper
//import models.wrapper.step.StepIn
//import org.specs2.specification.BeforeAfterAll
//
///**
//	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
//	*
//	* Created by Gennadi Heimann 28.08.2017
//	*/
//@RunWith(classOf[JUnitRunner])
//class GetTempStepForAdditionalStepInLevelCSSpecs extends Specification with Wrapper with BeforeAfterAll{
//  
//  def afterAll(): Unit = {}
//  def beforeAll(): Unit = {
//    Logger.info("GetTempStepForAdditionalStepInLevelCSSpecs")
//    TempConfigurations.getAndRemoveAdditionalStepInLevelCS
//  }
//  
//  
//  
//  "Create two TempSteps" >> {
//    "Create twice TempStep" >> {
//      
//      val stepCS_1 = JsonStepIn(
//          params = JsonStepParams(
//              "", "step 1", "",
//              JsonSelectionCriterium(1, 1)
//          )
//      )
//      
//      val exception = try{
//        TempConfigurations.getAndRemoveAdditionalStepInLevelCS
//      }catch {
//				case e: Exception => e
//				case _: Throwable => Logger.error("Got some other kind of exception")
//			}
//      
//      exception.toString() === "java.lang.Exception: Der temporaerer Step wurde noch nicht gesetzt"
//      
//      TempConfigurations.setAdditionalStepInLevelCS(Some(toStepIn(stepCS_1)))
//      
//      val stepCS: Any = try{
//        TempConfigurations.getAndRemoveAdditionalStepInLevelCS
//      }catch {
//				case e: Exception => e
//				case _: Throwable => Logger.error("Got some other kind of exception")
//			}
//      
//      stepCS.asInstanceOf[Some[StepIn]].get.nameToShow === "step 1"
//    }
//  }
//  
//}