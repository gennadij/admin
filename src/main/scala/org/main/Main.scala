package org.main


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */
object Main {
  def main(args : Array[String]) = {
    println("I am generic configurator")
    println("Generic configurator started")
    println("Run Test Scenarios")
    
//    PrepareConfigForSpecs2.prepareWithAlredyExistingUser
//    PrepareConfigForSpecs2.prepareLogin
//    PrepareConfigForSpecs2.prepareAddingNewConfig
//    PrepareConfigForSpecs2.prepareAddingFirstStep
//    PrepareConfigForSpecs2.prepareAddingComponentWithFirstStep
//    PrepareConfigForSpecs2.prepareAddingNewComponent
//    PrepareConfigForSpecs2.prepareSpecsConfigTreeEmpty
//    PrepareConfigForSpecs2.prepareConfigTree
//    PrepareConfigForSpecs2.prepareSpecsAddStep
    new TestConfigs().configUser10
    
    println("END")
  }
}
