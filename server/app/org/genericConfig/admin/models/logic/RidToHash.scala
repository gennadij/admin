package org.genericConfig.admin.models.logic

import play.api.Logger
import org.genericConfig.admin.models.persistence.orientdb.Graph

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.05.2018
 */
object RidToHash {
  
  
  var map = scala.collection.mutable.Map[String, String]()
  
  
  def setIdAndHash(id: String): Unit = {
    
    val hash = calculateHash(id)
    
    (map.exists(_._1 == id): @unchecked) match {
      case false => 
        map += (id -> hash)
      case true => 
    }
    
    map.foreach(item => Logger.info("Item" + item.toString))
  }
  
  def getId(hash: String): String = {
    val item = map.find(_._2 == hash )
    item.get._1
  }
  
  def getHash(id: String): String = {
    val item = map.find(_._1 == id)
    item.get._2
  }
  
  def cleanMap = {
    map.empty
  }
  
  def calculateHash(id: String) = {
    import java.security.MessageDigest
    import java.math.BigInteger
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(id.getBytes)
    val bigInt = new BigInteger(1, digest)
    bigInt.toString(16)
  }
  
  def initUser = {
    val (vUser, status) = Graph.readUser("user_v016_4_client" , "user_v016_4_client")
    
    Logger.info(vUser.get.getIdentity.toString)
    
    RidToHash.setIdAndHash(vUser.get.getIdentity.toString)
  }
}