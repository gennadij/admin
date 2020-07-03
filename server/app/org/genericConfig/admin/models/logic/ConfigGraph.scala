package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.impls.orient.{OrientEdge, OrientElement, OrientVertex}
import com.tinkerpop.blueprints.{Direction, Edge}
import org.genericConfig.admin.models.common.Error
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.configGraph.{ConfigGraphComponentDTO, ConfigGraphD3DTO, ConfigGraphD3LinkDTO, ConfigGraphD3NodeDTO, ConfigGraphDTO, ConfigGraphEdgeDTO, ConfigGraphResultDTO, ConfigGraphStepDTO}
import play.api.Logger

import scala.collection.JavaConverters._

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
        val eSteps : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.VERTEX_STEP)
        val eComponents : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.VERTEX_COMPONENT)
        val eHasSteps : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.EDGE_HAS_STEP)
        val eHasComponents : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.EDGE_HAS_COMPONENT)

        Logger.info("Steps: " + eSteps)
        Logger.info("Components: " + eComponents)
        Logger.info("EDGE_HAS_STEP: " + eHasSteps)
        Logger.info("EDGE_HAS_COMPONENT: " + eHasComponents)


        val configGraphSteps : List[ConfigGraphStepDTO] = eSteps.map(step => {
          ConfigGraphStepDTO(
            id = RidToHash.setIdAndHash(step.getIdentity.toString)._2,
            x = 0,
            y = 0
          )
        })

        val configGraphComponents : List[ConfigGraphComponentDTO] = eComponents.map(c => {
          ConfigGraphComponentDTO(
            id = RidToHash.setIdAndHash(c.getIdentity.toString)._2,
            x = 0,
            y = 0
          )
        })
        val edgesHasStepsWithoutConfig =
          eHasSteps.filterNot(_.asInstanceOf[OrientEdge].getOutVertex.getIdentity.toString().equals(configRid))

        val edgesHasSteps : List[ConfigGraphEdgeDTO] = edgesHasStepsWithoutConfig.map(hasStep => {
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

        val edgesHasComponents : List[ConfigGraphEdgeDTO] = eHasComponents.map(hasComponent => {
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

        //D3
        val eStepsAndComponents : List[OrientElement] = eSteps ::: eComponents

        val configGraphD3NodesDTO : List[ConfigGraphD3NodeDTO] = eStepsAndComponents.map(e => {
          ConfigGraphD3NodeDTO(
            id = RidToHash.setIdAndHash(e.getIdentity.toString)._2,
            x = 0,
            y = 0
          )
        })

//        val configGraphD3LinksDTO : List[ConfigGraphD3LinkDTO] = edges.map(e => {
//          val source : Option[ConfigGraphD3NodeDTO] = configGraphD3NodesDTO.find(_.id == e.source)
//          val target : Option[ConfigGraphD3NodeDTO] = configGraphD3NodesDTO.find(_.id == e.target)
//          ConfigGraphD3LinkDTO(
//            source = source.get,
//            target = target.get
//          )
//        })

        val configGraphD3LinksDTO : List[ConfigGraphD3LinkDTO] = edges.map(e => {
          ConfigGraphD3LinkDTO(
            source = e.source,
            target = e.target
          )
        })

        calcPosition2(eSteps, eComponents, eHasSteps, eHasComponents, configRid)
        // TODO
        // edges wird zurzeit nicht mehr gebraucht
        // ConfigGraphStepDTO benoetigt nameToShow, selectionCriterion
        // ConfigGraphComponentDTO benoetigt nameToShow
        ConfigGraphDTO(
          action = Actions.CONFIG_GRAPH,
          result = Some(ConfigGraphResultDTO(
            steps = Some(configGraphSteps),
            components = Some(configGraphComponents),
            edges = Some(List()),
            d3Data = Some(ConfigGraphD3DTO(
              nodes = configGraphD3NodesDTO,
              links = configGraphD3LinksDTO
            )),
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

  private def calcPosition2(
                             eSteps : List[OrientElement],
                             eComponents : List[OrientElement],
                             eHasSteps : List[OrientElement],
                             eHasComponents : List[OrientElement],
                             configRid : String
                           ): Unit = {

    val height : Int = 1000
    val width : Int = 1000

    val (vConfig, error) : (Option[OrientVertex], Option[Error]) = GraphCommon.getVertex(rId = configRid)

    val eHasStep : List[OrientEdge] =
      vConfig.get.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList.map(_.asInstanceOf[OrientEdge])

    val vFirstStep : OrientVertex = eHasStep.head.getVertex(Direction.IN)

    val yStep: Int = height / 2

    val eHasComponents: List[OrientEdge] =
      vFirstStep.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList.map(_.asInstanceOf[OrientEdge])

    val vComponents: List[OrientVertex] = eHasComponents.map(_.getVertex(Direction.IN))

    val eNextHasSteps: List[List[OrientEdge]] =
      vComponents.map(vC => {
        vC.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList.map(
          _.asInstanceOf[OrientEdge]
        )
      })

    val vNextSteps: Set[OrientVertex] = eNextHasSteps.flatten.map(_.getVertex(Direction.IN)).toSet

    val length = vComponents.length

//    val yComponent: IndexedSeq[Double] = for (teiler <- 1 to length) yield height.toDouble * (teiler.toDouble / (length + 1).toDouble)

    val componentsNode = for(c <- vComponents) yield {
      var counter = 1
      val y = height.toDouble * (counter.toDouble / (length + 1).toDouble)
      counter = counter + 1
      ConfigGraphD3NodeDTO(
        id = c.getIdentity.toString,
        x = 0,
        y = y.toInt
      )
    }

    val step = ConfigGraphD3NodeDTO(
      id = vFirstStep.getIdentity.toString(),
      x = 0,
      y = yStep
    )

//    val components: List[ConfigGraphD3NodeDTO] = yComponent.map(yC => {
//      ConfigGraphD3NodeDTO(
//        id = "",
//        x = 0,
//        y = yC.toInt
//      )
//    }).toList

    val currentNodes = step :: componentsNode

    val cN: Set[List[ConfigGraphD3NodeDTO]] = for (i <- vNextSteps) yield calcPositionRecursive(currentNodes, i, height, width)

//    val calcedNodes = calcPositionRecursive(Nil, vFirstStep, height, width)

    val res = cN.flatten.toList
    Logger.info("Position y : " + res.length)

    res.foreach(println(_))
  }

  private def calcPositionRecursive(n : List[ConfigGraphD3NodeDTO], v : OrientVertex, height : Int, width : Int): List[ConfigGraphD3NodeDTO] = {
    Logger.info("calcPositionRecursive")

    val yStep: Int = height / 2

    val eHasComponents: List[OrientEdge] =
      v.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList.map(_.asInstanceOf[OrientEdge])

    val vComponents: List[OrientVertex] = eHasComponents.map(_.getVertex(Direction.IN))

    val eNextHasSteps: List[List[OrientEdge]] =
      vComponents.map(vC => {
        vC.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList.map(
          _.asInstanceOf[OrientEdge]
        )
      })

    val vNextSteps: Set[OrientVertex] = eNextHasSteps.flatten.map(_.getVertex(Direction.IN)).toSet

    val length = vComponents.length

//    val yComponent: IndexedSeq[Double] = for (teiler <- 1 to length) yield height.toDouble * (teiler.toDouble / (length + 1).toDouble)

    val componentsNode = for(c <- vComponents) yield {
      var counter = 1
      val y = height.toDouble * (counter.toDouble / (length + 1).toDouble)
      counter = counter + 1
      ConfigGraphD3NodeDTO(
        id = c.getIdentity.toString,
        x = 0,
        y = y.toInt
      )
    }
    val step = ConfigGraphD3NodeDTO(
      id = v.getIdentity.toString(),
      x = 0,
      y = yStep
    )

//    val components: List[ConfigGraphD3NodeDTO] = yComponent.map(yC => {
//      ConfigGraphD3NodeDTO(
//        id = "",
//        x = 0,
//        y = yC.toInt
//      )
//    }).toList


    val currentNodes = step :: componentsNode ::: n
    Logger.info("Before -> n = " + n.length + " | currentNodes = " + currentNodes.length)
    val cN: Set[List[ConfigGraphD3NodeDTO]] = for (i <- vNextSteps) yield calcPositionRecursive(currentNodes, i, height, width)

    //    val recNodes : Set[List[ConfigGraphD3NodeDTO]] = vNextSteps.map(vNS => {
    //      calcPositionRecursive(currentNodes, vNS, height, width)
    //    })

    Logger.info("After -> n = " + n.length + " | currentNodes = " + currentNodes.length + " | cN = " + cN.flatten.toList.length)

    currentNodes
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