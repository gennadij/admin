package models.v011

import org.specs2.Specification
import org.genericConfig.admin.models.persistence.OrientDB
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 18.11.2016
 */
@RunWith(classOf[JUnitRunner])
class DBConnectionSpecs extends Specification{
  
  
  def is = sequential ^ s2"""
         Specification zum DB Connection testen
       connection                   $e1
          
    """
  
  
  def e1 = OrientDB.getFactory().getTx.toString() must_== "orientgraph[remote:localhost/testDB]"
  
}