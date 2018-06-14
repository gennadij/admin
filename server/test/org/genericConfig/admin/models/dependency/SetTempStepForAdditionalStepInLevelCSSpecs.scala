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
//
///**
//	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
//	*
//	* Created by Gennadi Heimann 28.08.2017
//	*/
//@RunWith(classOf[JUnitRunner])
//class SetTempStepForAdditionalStepInLevelCSSpecs extends Specification with Wrapper{
//  
//  Logger.info("SetTempStepForAdditionalStepInLevelCSSpecs")
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
//      val stepCS_2 = JsonStepIn(
//          params = JsonStepParams(
//              "", "step 1", "",
//              JsonSelectionCriterium(1, 1)
//          )
//      )
//      TempConfigurations.setAdditionalStepInLevelCS(Some(toStepIn(stepCS_1)))
//      
//      val exception = try{
//        TempConfigurations.setAdditionalStepInLevelCS(Some(toStepIn(stepCS_2)))
//      }catch {
//				case e: Exception => e
//				case _: Throwable => Logger.error("Got some other kind of exception")
//			}
//      
////      Exception("Der temporaerer Step wurde schon frueer mit einem Step belegt!")
//      exception.toString() === "java.lang.Exception: Der temporaerer Step wurde schon fruer mit einem Step belegt!"
//    }
//  }
//  
//}