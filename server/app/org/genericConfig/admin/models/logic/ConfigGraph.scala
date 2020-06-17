package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.impls.orient.{OrientEdge, OrientElement, OrientVertex}
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
    new ConfigGraph().configGraph(configGraphDTO)
  }
}

class ConfigGraph() {

  def configGraph(configGraphDTO: ConfigGraphDTO) : ConfigGraphDTO = {

    val configRid : String = RidToHash.getRId(configGraphDTO.params.get.configId).get
    val (orientElems, error) : (Option[List[OrientElement]], Option[Error]) = GraphCommon.traverse(configRid)

    error match {
      case None =>
        val steps : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.VERTEX_STEP)
        val components : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.VERTEX_COMPONENT)
        val hasSteps : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.EDGE_HAS_STEP)
        val hasComponents : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.EDGE_HAS_COMPONENT)

        val configGraphSteps : List[ConfigGraphStepDTO] = steps.map(step => {
          ConfigGraphStepDTO(
            id = RidToHash.setIdAndHash(step.getIdentity.toString)._2,
            x = 0,
            y = 0
          )
        })

        val configGraphComponents : List[ConfigGraphComponentDTO] = components.map(c => {
          ConfigGraphComponentDTO(
            id = RidToHash.setIdAndHash(c.getIdentity.toString)._2,
            x = 0,
            y = 0
          )
        })

        val edgesHasSteps : List[ConfigGraphEdgeDTO] = hasSteps.map(hasStep => {
          val target : String = RidToHash.setIdAndHash(
            hasStep.asInstanceOf[OrientEdge].getInVertex.getIdentity.toString
          )._2
          val source : String = RidToHash.setIdAndHash(
            hasStep.asInstanceOf[OrientEdge].getOutVertex.getIdentity.toString
          )._2
          ConfigGraphEdgeDTO(
            source = source,
            target = target
          )
        })

        val edgesHasComponents : List[ConfigGraphEdgeDTO] = hasComponents.map(hasComponent => {
          val target : String = RidToHash.setIdAndHash(
            hasComponent.asInstanceOf[OrientEdge].getInVertex.getIdentity.toString
          )._2
          val source : String = RidToHash.setIdAndHash(
            hasComponent.asInstanceOf[OrientEdge].getOutVertex.getIdentity.toString
          )._2
          ConfigGraphEdgeDTO(
            source = source,
            target = target
          )
        })

        val edges : List[ConfigGraphEdgeDTO] = edgesHasSteps ::: edgesHasComponents

        ConfigGraphDTO(
          action = Actions.CONFIG_GRAPH,
          result = Some(ConfigGraphResultDTO(
            steps = Some(configGraphSteps),
            components = Some(configGraphComponents),
            edges = Some(edges),
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
    //TODO componentsPerLevel = 3 components = 9 funktioneirt nicht (Statische Graph wird spaeter gemacht)
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