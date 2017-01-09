package org.persistence.db.orientdb

import org.dto.Config.CreateConfigCS

object HasConfigEdge {
  
  def hasConfig(createConfigCS: CreateConfigCS): Boolean = {
    val adminId: String = createConfigCS.params.adminId
    true
  }
  
}