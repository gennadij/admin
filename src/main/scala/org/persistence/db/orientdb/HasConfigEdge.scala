package org.persistence.db.orientdb

import org.dto.Config.CreateConfigCS

object HasConfigEdge {
  
  def hasConfig(createConfigCS: CreateConfigCS): Bolean = {
    val adminId: String = createConfigCS.params.adminId
    
  }
  
}