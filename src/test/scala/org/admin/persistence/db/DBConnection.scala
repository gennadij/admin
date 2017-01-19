package org.admin.persistence.db

import org.specs2.Specification
import org.persistence.db.orientdb.OrientDB
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 15.01.2017
 */
@RunWith(classOf[JUnitRunner])
class DBConnection extends Specification{
  
  def is = 
  
  s2"""
         Specification zum DB Connection testen
       connection                   $e1
          
    """
  
  
  def e1 = OrientDB.getGraph().toString() must_== "orientgraph[remote:localhost/testDB]"
  
}