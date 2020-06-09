package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.impls.orient.{OrientElement, OrientVertex}
import com.tinkerpop.blueprints.{Direction, Edge}
import org.genericConfig.admin.models.common.Error
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.configGraph.{ConfigGraphComponentDTO, ConfigGraphDTO, ConfigGraphEdgeDTO, ConfigGraphResultDTO, ConfigGraphStepDTO}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 08.06.2020
 */
object ConfigGraph {
  /**
   * @author Gennadi Heimann
   * @version 0.1.6
   * @param configGraphDTO: ConfigGraphDTO
   * @return ConfigGraphDTO
   */

  def configGraph(configGraphDTO: ConfigGraphDTO) : ConfigGraphDTO = {
    ???
  }
}

class ConfigGraph() {

  def configGraph(configGraphDTO: ConfigGraphDTO) : ConfigGraphDTO = {

    val configRid : String = RidToHash.getRId(configGraphDTO.params.get.configId).get
    val (orientElems, error) : (Option[List[OrientElement]], Option[Error]) = GraphCommon.traverse(configRid)

    error match {
      case None =>
        val steps : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == "Step")
        val components : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == "Component")
        val hasStep : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == "hasStep")
        val hasComponent : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == "hasComponent")
        val (configGraphComponentDTO : List[ConfigGraphComponentDTO], configGraphStepDTO : List[ConfigGraphStepDTO]) = calcPosition(
          Some(steps), Some(components)
        )

        val edgesHasStep : List[ConfigGraphEdgeDTO] = hasStep.map(hasStep => {
          ???
      })

        ConfigGraphDTO(
          action = Actions.CONFIG_GRAPH,
          result = Some(ConfigGraphResultDTO(
            steps = Some(configGraphStepDTO),
            components = Some(configGraphComponentDTO),
            edges = Some(List()),
            errors = None
          )
        ))
      case Some(error) => ConfigGraphDTO(
        action = Actions.CONFIG_GRAPH,
        result = Some(ConfigGraphResultDTO(
          errors = Some(List(ErrorDTO(message = error.message, name = error.name, code = error.code)))
        )
      ))
    }
  }

  private def calcPosition(
                    steps : Option[List[OrientElement]] = None,
                    components : Option[List[OrientElement]] = None
                  ): (List[ConfigGraphComponentDTO], List[ConfigGraphStepDTO]) = {
    val height : Int = 1000
    val width : Int = 1000
    var level : Int = 1
    val levelWidth : Int = width / steps.get.length

    val configGraphStepDTO : List[ConfigGraphStepDTO] = steps.get.map(step => {
      val y = height / 2
      val x = level * levelWidth
      level += 2
      ConfigGraphStepDTO(
        id = step.getIdentity.toString,
        x = x,
        y = y
      )
    })
    import scala.collection.JavaConverters._

    val componentsPerLevel : List[Int] = steps.get.map(step => {
      val edges : java.lang.Iterable[Edge] = step.asInstanceOf[OrientVertex].getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT)
      edges.asScala.toList.length
    })

    level = 2
    var count : Int = 0
    val configGraphComponentDTO : List[ConfigGraphComponentDTO] = components.get.map(component => {
      val x = level * levelWidth
      val y = height / componentsPerLevel(count)
      level += 2
      count += 1
      ConfigGraphComponentDTO(
        id = component.getIdentity.toString,
        x = x,
        y = y
      )
    })

    (configGraphComponentDTO, configGraphStepDTO)
  }
}