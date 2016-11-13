package org.main

import org.persistence.db.orientdb.TestOrientdb

/**
  * 
  */
object Main {
  def main(args : Array[String]) = {
    println("I am generic configurator")
    println("Generic configurator started")
    println("Run Test Scenarios")
    
    
//    new TestScenario8().scenario8

    new TestScenario9().scenario9_1
//    new TestScenario9().scenario9_2()
    
    println("END")
  }
}


//    val currentConfig = new TestMutableCurrentConfig
////    
//    currentConfig.mutableCurrentConfig
//    val selCrit = new TestSelectionCriterium
    
//    selCrit.selectCriterium
    
//    selCrit.getStepOfComponents
    
//    selCrit.getNextStep