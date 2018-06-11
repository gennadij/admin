package org.genericConfig.admin.models.wrapper

import play.api.Logger

import scala.collection.mutable

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.05.2018
 */
object RidToHash {
  
  
  var map: mutable.Map[String, String] = scala.collection.mutable.Map[String, String]()
  
  
  def setIdAndHash(id: String): (String, String) = if (map.exists(_._1 == id): @unchecked) {
    map.foreach(item => Logger.info("Item" + item.toString))
    (id, calculateHash(id))
  } else {
    val hash = calculateHash(id)
    map += (id -> hash)
    map.foreach(item => Logger.info("Item" + item.toString))
    (id, hash)
  }
  
  def getId(hash: String): Option[String] = {
    val item = map.find(_._2 == hash )
    item match {
      case Some(i) => Some(i._1)
      case None => None
    }


  }
  
  def getHash(id: String): Option[String] = {
    val item = map.find(_._1 == id)
    item match {
      case Some(i) => Some(i._2)
      case None => None
    }
  }
  
  def cleanMap: mutable.Map[String, String] = {
    map.empty
  }
  
  def calculateHash(id: String): String = {
    import java.math.BigInteger
    import java.security.MessageDigest
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(id.getBytes)
    val bigInt = new BigInteger(1, digest)
    bigInt.toString(16)
  }
}