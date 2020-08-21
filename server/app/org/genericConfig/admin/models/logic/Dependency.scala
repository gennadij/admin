package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, PropertyKeys}
import org.genericConfig.admin.shared.dependency.DependencyDTO

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann ${date}
 */
object Dependency {
  def addDependency(dependencyDTO: DependencyDTO): DependencyDTO = {
    new Dependency().addDependency(dependencyDTO)
  }

  def deleteDependency(dependencyDTO: DependencyDTO): DependencyDTO = {
    ??? //new Dependency().addDependency(dependencyDTO)
  }

  def updateDependency(dependencyDTO: DependencyDTO): DependencyDTO = {
    ??? //new Dependency().addDependency(dependencyDTO)
  }

}

class Dependency {
  private def addDependency(dependencyDTO: DependencyDTO) : DependencyDTO = {
    val outRId : String = RidToHash.getRId(dependencyDTO.params.get.configProperties.get.outId.get).get
    val inRId : String = RidToHash.getRId(dependencyDTO.params.get.configProperties.get.inId.get).get
    val visualization : String = dependencyDTO.params.get.userProperties.get.visualization.get
    val dependencyType : String = dependencyDTO.params.get.userProperties.get.visualization.get

    val properties : List[(String, Option[String])] = List(
      (PropertyKeys.VISUALIZATION, Some(visualization)),
      (PropertyKeys.DEPENDENCY_TYPE, Some(dependencyType))
    )

    GraphCommon.addEdge_2(outRId,inRId, PropertyKeys.EDGE_HAS_DEPENDENCY, Some(properties))

    ???
  }
}
