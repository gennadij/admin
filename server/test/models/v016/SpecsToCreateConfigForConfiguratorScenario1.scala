//package models.v016
//
//import org.specs2.mutable.Specification
//import org.specs2.specification.BeforeAfterAll
//import models.preparingConfigs.GeneralFunctionToPrepareConfigs
//import models.preparingConfigs.PrepareConfigsForSpecsv015
//import models.preparingConfigs.PrepareConfigsForSpecsv016
//import models.websocket.WebClient
//import play.api.Logger
//import org.specs2.runner.JUnitRunner
//import org.junit.runner.RunWith
//
//
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 20.10.2017
// */
//@RunWith(classOf[JUnitRunner])
//class SpecsToCreateConfigForConfiguratorScenario1
//        extends Specification
//        with BeforeAfterAll
//        with GeneralFunctionToPrepareConfigs {
//  
//  val wC = WebClient.init
//  
//  def afterAll(): Unit = {}
//  def beforeAll(): Unit = {
//    Logger.info("SpecsToCreateConfigForConfigurator")
//    PrepareConfigsForSpecsv016.configurationForWebClient(wC)
//  }
//  
//  
//  "Vorbereite die Konfiguration fuer den Konfigurator" >> {
//    "" >> {
//      "" === ""
//    }
//  }
//}