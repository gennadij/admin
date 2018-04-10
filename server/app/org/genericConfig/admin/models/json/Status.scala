package org.genericConfig.admin.models.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann Jun 14, 2017
 */
 


//General

object StatusSuccessfulGeneral {
  val status: String = "SECCESSFUL_GENERAL"
  val message: String = "Die Aktion war erfolgreich"
}

object StatusErrorGeneral{
  val status: String = "ERROR_GENERAL"
  val message: String = "Allgemeiner Fehler"
}

object StatusErrorWriteToDB{
  val status: String = "ERROR_WRITE_TO_DB"
  val message: String = "Es ist einen Fehler beim Schreiben in der Datenbank aufgetreten"
}

//Regist

object StatusSuccessfulRegist {
  val status: String = "SECCESSFUL_REGIST"
  val message: String = "Registrierung war erfolgreich"
}

object StatusErrorRegistUserAlreadyExist {
  val status: String = "ERROR_REGIST_USER_ALREADY_EXIST"
  val message: String = "Registrierung war nicht erfolgreich. Username existiert bereits"
}

object StatusErrorRegistGeneral {
  val status: String = "ERROR_REGIST_GENERAL"
  val message: String = "Registrierung war nicht erfolgreich."
}

//Login

object StatusErrorLogin {
  val status: String = "ERROR_LOGIN"
  val message: String = "Anmeldung war nicht erfolgreich"
}

object StatusSuccessfulLogin {
  val status: String = "SUCCESSFUL_LOGIN"
  val message: String = "Anmeldung war erfolgreich"
}


//Config

object StatusErrorFaultyConfigId{
  val status: String = "ERROR_FAULTY_CONFIG_ID"
  val message: String = "Der ConfigId ist difekt"
}

object StatusErrorConfig{
  val status: String = "ERROR_CONFIG"
  val message: String = "Der Config konnte nicht erzeugt werden"
}

object StatusSuccessfulConfig{
  val status: String = "SUCCESSFUL_CONFIG"
  val message: String = "Der Config wurde erfolgreich erzeugt"
}

object StatusErrorDuplicateConfigUrl{
  val status: String = "ERROR_DUPLICATE_CONFIG_URL"
  val message: String = "Der ConfigUrl wurde schon bei dem andren Admin erstellt"
}

//ConfigTree

object StatusErrorConfigTree{
  val status: String = "ERROR_CONFIG_TREE"
  val message: String = "Bei der Erstellung von ConfigTree ist ein Fehler aufgetretten"
}

object StatusSuccessfulConfigTree{
  val status: String = "SUCCESSFUL_CONFIG_TREE"
  val message: String = "Das ConfigTree wurde erfolgreich erzeugt"
}

object StatusWarningConfigTreeEmpty {
  val status: String = "WARNING_CONFIG_TREE_EMPTY"
  val message: String = "Es exestiert keine Konfigutration fuer diesen Benutzer"
}


//FirstStep

object StatusSuccessfulFirstStepCreated {
  val status: String = "SUCCESSFUL_FIRSTSTEP_CREATED"
  val message: String = "Der erste Step wurde zu der Konfiguration hinzugefuegt"
}

object StatusErrorFirstStepExist {
  val status: String = "ERROR_FIRSTSTEP_EXIST"
  val message: String = "Der erste Schritt in der Konfiguration existiert bereits."
}

object StatusErrorTwoFirstStep {
  val status: String = "ERROR_TWO_FIRSTSTEP"
  val message: String = "In der Konfiguration sind zwei FirstStep"
}

object StatusErrorFaultyFirstStepId{
  val status: String = "ERROR_FAULTY_FIRSTSTEP_ID"
  val message: String = "Der FirstStepId ist defekt. Config -> FirstStep nicht möglich"
}

//Component

object StatusErrorFaultyComponentId{
  val status: String = "ERROR_FAULTY_COMPONENT_ID"
  val message: String = "Der ComponentId ist defekt. Component -> Step nicht möglich"
}

object StatusSuccessfulComponentCreated {
  val status: String = "SUCCESSFUL_COMPONENT_CREATED"
  val message: String = "Die Komponente wurde hinzugefuegt"
}

object StatusErrorComponentGeneral {
  val status: String = "ERROR_COMPONENT_GENERAL"
  val message: String = "Es ist einen Fehler bei der Komponenterzeugung aufgetreten"
}

//ConnectionComponentToStep

object StatusSuccessfulConnectionComponentToStep {
  val status: String = "SECCESSFUL_CONNECTION_COMPONENT_TO_STEP"
  val message: String = "Component mit dem Step wurde Verbunden"
}

object StatusErrorConnectionComponentToStep {
  val status: String = "ERROR_CONNECTION_COMPONENT_TO_STEP"
  val message: String = "Component mit dem Step wurde nicht Verbunden"
}

//Step

object StatusSuccessfulStepCreated{
  val status: String = "SUCCESSFUL_STEP_CREATED"
  val message: String = "Der Schritt wurde zu der Konfiguration hinzugefuegt"
}

object StatusSuccessfulAdditionalStepInLevelCSCreated {
  val status: String = "SUCCESSFUL_ADDITIONAL_STEP_IN_LEVEL_CS"
  val message: String = "Der Schritt wurde mit den Abhaengigkeiten zu der Konfiguration hinzugefuegt"
}

object StatusErrorStepExist {
  val status: String = "ERROR_STEP_EXIST"
  val message: String = "Der Schritt exstiert bereits"
}

object StatusErrorFaultyStepId{
  val status: String = "ERROR_FAULTY_STEP_ID"
  val message: String = "Der Schritt  ID ist nicht korrekt"
}

object StatusWarningAdditionalStepInLevelCS {
  val status: String = "WARNING_ADDITIONAL_STEP_IN_LEVEL_CS"
  val message: String = "Um den Konflikt zu vermeiden waelen Sie die Visualisierung oder legen Sie den Schritt nicht an"
}


//Dependency

object StatusSuccessfulDependencyCreated{
  val status: String = "SUCCESSFUL_DEPENDENCY_CREATED"
  val message: String = "Die Abhängigkeit wurde erfolgreich angelegt"
}

object StatusErrorDependencyCreated{
  val status: String = "ERROR_DEPENDENCY_CREATED"
  val message: String = "Die Abhängigkeit wurde nicht erfolgreich angelegt"
}







