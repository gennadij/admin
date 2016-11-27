package org.admin.persistence.db

import org.specs2.Specification
import org.persistence.db.orientdb.OrientDB

class DBConnection extends Specification{
  
  def is = 
  
  s2"""
         Specification zum DB Connection testen
       connection                   $e1
          
    """
  
  
  def e1 = OrientDB.getGraph().toString() must_== "orientgraph[remote:localhost/testDB]"
  
}