package org.genericConfig.admin.models.tempConfig

import play.api.Logger


/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 28.08.2017
 * 
 * Implementierung von einer Temporäre Configuration.
 * 
 * Hier werden die Daten abgelegt, die bei der naechsten Client-Abfragen verarbeitet muessen.
 */
object TempConfigurations {
  
  /**
   * Temporaerer Step 
   */
  private var tempStep: Option[/*StepIn*/ Any] = None
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * Wenn der temporaerer Step belegt ist wird eine Exeption geworfen
   * 
   * @param
   * 
   * @return Unit
   */
  def setAdditionalStepInLevelCS(step: Option[/*StepIn*/ Any]): Unit = {
    tempStep = tempStep match {
      case None => step
      case Some(tempStep) => throw new Exception("Der temporaerer Step wurde schon fruer mit einem Step belegt!")
    }
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * Wenn der temporaerer Step None ist wird eine Exeption geworfen
   * 
   * @param
   * 
   * @return Option[StepSC]
   */
  def getAndRemoveAdditionalStepInLevelCS: Option[/*StepIn*/ Any] = {
    val step = tempStep match {
      case None => throw new Exception("Der temporaerer Step wurde noch nicht gesetzt")
      case Some(tempStep) => Some(tempStep)
    }
    tempStep = None
    step
  }
}