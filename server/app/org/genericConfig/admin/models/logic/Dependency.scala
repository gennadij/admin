package org.genericConfig.admin.models.logic

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
    val outRId : String = ???
    val inRId : String = ???
    val visualization : String = ???
    val dependencyType : String = ???




    ???
  }
}
