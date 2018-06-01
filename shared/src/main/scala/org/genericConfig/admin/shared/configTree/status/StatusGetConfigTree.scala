package org.genericConfig.admin.shared.configTree.status

import org.genericConfig.admin.shared.common.status.Status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.04.2018
 */
sealed abstract class StatusGetConfigTree extends Status


case class GetConfigTreeError() extends StatusGetConfigTree{
  def status: String = "GET_CONFIGTREE_ERROR"
  def message: String = "Bei der Erstellung von ConfigTree ist ein Fehler aufgetretten"
}

case class GetConfigTreeSuccess() extends StatusGetConfigTree{
  def status: String = "GET_CONFIGTREE_SUCCESS"
  def message: String = "Das ConfigTree wurde erfolgreich erzeugt"
}

case class GetConfigTreeEmpty() extends StatusGetConfigTree {
  def status: String = "GET_CONFIGTREE_EMPTY"
  def message: String = "Es exestiert keine Konfigutration fuer diesen Benutzer"
}