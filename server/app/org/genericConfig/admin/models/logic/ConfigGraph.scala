package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.{OrientEdge, OrientElement, OrientVertex}
import org.genericConfig.admin.models.common.Error
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.component.ComponentUserPropertiesDTO
import org.genericConfig.admin.shared.configGraph._
import org.genericConfig.admin.shared.step.{SelectionCriterionDTO, StepPropertiesDTO}
import play.api.Logger

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

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

  case class D3Node(
                     id : String,
                     nameToShow : String = "",
                     level : MutableInt,
                     orderNumber : Int,
                     x : Double,
                     y : Double
                   )

  implicit class MutableInt(var value : Int){
    def get(): Int = value
    def +(x : Int): Unit = {value = value + x}
  }

  def configGraph(configGraphDTO: ConfigGraphDTO) : ConfigGraphDTO = {

    val configRid : String = RidToHash.getRId(configGraphDTO.params.get.configId).get
    val screenHeight : Int = (configGraphDTO.params.get.screenHeight.toDouble * 0.66).toInt
    //TODO Den Faktor globalisieren
    val screenWidth : Int  = (configGraphDTO.params.get.screenWidth.toDouble * 0.69).toInt
    val (orientElems, error) : (Option[List[OrientElement]], Option[Error]) = GraphCommon.traverseOut(configRid)

    error match {
      case None =>
        val eSteps : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.VERTEX_STEP)
        val eComponents : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.VERTEX_COMPONENT)
//        val eHasSteps : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.EDGE_HAS_STEP)
//        val eHasComponents : List[OrientElement] = orientElems.get.filter(_.getRecord.getClassName == PropertyKeys.EDGE_HAS_COMPONENT)
        val eHasSteps : List[OrientEdge] = eComponents.flatMap(sC => {
          sC.asInstanceOf[OrientVertex].getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList.map(_.asInstanceOf[OrientEdge])
        })

        val eHasComponents : List[OrientEdge] = eSteps.flatMap(sC => {
          sC.asInstanceOf[OrientVertex].getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList.map(_.asInstanceOf[OrientEdge])
        })

        val configGraphSteps : List[ConfigGraphStepDTO] = getConfigGraphStepsDTO(eSteps)

        val configGraphComponents : List[ConfigGraphComponentDTO] = getConfigGraphComponentsDTO(eComponents)

        val edges : List[ConfigGraphEdgeDTO] = getConfigGraphEdgesDTO(eHasSteps, eHasComponents, configRid)

        val configGraphD3LinksDTO : List[ConfigGraphD3LinkDTO] = getConfigGraphD3LinksDTO(edges)
        Logger.info("eSteps : " + eSteps)
        Logger.info("eHasSteps" + eHasSteps)
        Logger.info("eHasComponents" + eHasComponents)

        val configGraphD3NodesDTO : List[ConfigGraphD3NodeDTO] = if(eSteps.isEmpty) {
          List()
        }else {
          getConfigGraphD3NodesDTO(configRid, screenHeight, screenWidth)
        }
          ConfigGraphDTO(
          action = Actions.CONFIG_GRAPH,
          result = Some(ConfigGraphResultDTO(
            steps = Some(configGraphSteps),
            components = Some(configGraphComponents),
            d3Data = Some(ConfigGraphD3DTO(
              nodes = configGraphD3NodesDTO,
              links = configGraphD3LinksDTO,
              properties = ConfigGraphD3PropertiesDTO(
                svgHeight = screenHeight,
                svgWidth = screenWidth
              )
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

  private def getConfigGraphD3NodesDTO(configRid : String, screenHeight : Int, screenWidth : Int) : List[ConfigGraphD3NodeDTO] = {
    val nodes : List[D3Node] = calcPosition(configRid, screenHeight)

    Logger.info("NODES " + nodes)

    val levels : List[Int] = nodes.map(_.level.value)

    Logger.info("LEVELS " + levels)

    val maxLevel = levels.sortWith(_ < _).last

    val nodesWithY = nodes.map(n => {
      val x : Int = (screenWidth.toDouble * n.level.value.toDouble / (maxLevel + 1)).toInt
      n.copy(x = x)
    })

    nodesWithY.map(e => {
      ConfigGraphD3NodeDTO(
        id = RidToHash.setIdAndHash(e.id)._2,
        nameToShow = e.nameToShow,
        x = e.x.toInt,
        y = e.y.toInt
      )
    })
  }

  private def calcPosition(configRid : String, screenHeight: Int): List[D3Node] = {

    val (vConfig, error) : (Option[OrientVertex], Option[Error]) = GraphCommon.getVertex(rId = configRid)

    val eHasStep : List[OrientEdge] =
      vConfig.get.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList.map(_.asInstanceOf[OrientEdge])

    val vFirstStep : OrientVertex = eHasStep.head.getVertex(Direction.IN)

    val allElem = ListBuffer[D3Node]()

    calcPositionRecursive(allElem, Nil, vFirstStep, screenHeight, MutableInt(1))

    allElem.toList
  }

  private def calcPositionRecursive(allElem : ListBuffer[D3Node], n : List[D3Node], v : OrientVertex, height : Int, level : MutableInt): List[D3Node] = {
    val vComponents: List[OrientVertex] = getComponents(v)

    val vNextSteps : Set[OrientVertex] = getNextStep(vComponents)

    val componentsNode : List[D3Node] = getConfigGraphD3Nodes(vComponents, v, height, level)

    Logger.info("calcPositionRecursive " + vComponents + "  " + vNextSteps + "   " + componentsNode)

    val currentNodes = componentsNode ++ n

    if(componentsNode.length == 1){
      allElem ++= currentNodes
      currentNodes
    }else {
      val cN: Set[List[D3Node]] = for (i <- vNextSteps) yield {
        val res = calcPositionRecursive(allElem, currentNodes, i, height, level)
        res
      }

      val list = cN.flatten.to[ListBuffer]

      allElem ++= list

      currentNodes
    }
  }

  private def getComponents(step : OrientVertex) : List[OrientVertex] = {
    val eHasComponentsFromFirstStep: List[OrientEdge] =
      step.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList.map(_.asInstanceOf[OrientEdge])

    eHasComponentsFromFirstStep.map(_.getVertex(Direction.IN))
  }

  private def getConfigGraphD3Nodes(
                                     vComponents : List[OrientVertex],
                                     vStep : OrientVertex,
                                     height : Int,
                                     level : MutableInt
                                   ) : List[D3Node] = {

    if (level.value != 1) {
      level + 1
    }

    val step = D3Node(
      id = vStep.getIdentity.toString(),
      nameToShow = vStep.getProperty(PropertyKeys.NAME_TO_SHOW),
      level = level.value,
      orderNumber = 1,
      x = 0,
      y = height / 2
    )

    var xPosNumber = 1
    level + 1
    val components : List[D3Node] = for(c <- vComponents) yield {

      val node : D3Node = D3Node(
        id = c.getIdentity.toString,
        nameToShow = c.getProperty(PropertyKeys.NAME_TO_SHOW),
        level = level.value,
        orderNumber = xPosNumber,
        x = 0,
        y = calcYPos(height, xPosNumber, vComponents.length)
      )

      xPosNumber = xPosNumber + 1

      node
    }
     step :: components
  }

  private def calcYPos(height : Int, xPosNumber : Int, allElem : Int) : Int = {
    val res : Double = height.toDouble * (xPosNumber.toDouble / (allElem + 1).toDouble)
    res.toInt
  }

  private def getNextStep(components : List[OrientVertex]) : Set[OrientVertex] = {
    val eNextHasSteps: List[List[OrientEdge]] =
      components.map(vC => {
        vC.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList.map(
          _.asInstanceOf[OrientEdge]
        )
      })

    eNextHasSteps.flatten.map(_.getVertex(Direction.IN)).toSet
  }

  private def getConfigGraphStepsDTO(eSteps : List[OrientElement]) : List[ConfigGraphStepDTO] = {
    eSteps.map(step => {
      ConfigGraphStepDTO(
        stepId = RidToHash.setIdAndHash(step.getIdentity.toString)._2,
        properties = StepPropertiesDTO(
          nameToShow = Some(step.getProperty(PropertyKeys.NAME_TO_SHOW).toString),
          selectionCriterion = Some(SelectionCriterionDTO(
            min = Some(step.getProperty(PropertyKeys.SELECTION_CRITERION_MIN).toString.toInt),
            max = Some(step.getProperty(PropertyKeys.SELECTION_CRITERION_MAX).toString.toInt)
          ))
        )
      )
    })
  }

  private def getConfigGraphComponentsDTO(eComponents : List[OrientElement]) : List[ConfigGraphComponentDTO] = {
    eComponents.map(c => {
      ConfigGraphComponentDTO(
        componentId = RidToHash.setIdAndHash(c.getIdentity.toString)._2,
        properties = ComponentUserPropertiesDTO(
          nameToShow = Some(c.getProperty(PropertyKeys.NAME_TO_SHOW).toString),
        )
      )
    })
  }

  private def getConfigGraphEdgesDTO(
                                      eHasSteps : List[OrientEdge],
                                      eHasComponents : List[OrientEdge],
                                      configRid : String) : List[ConfigGraphEdgeDTO] = {
    val edgesHasStepsWithoutConfig =
      eHasSteps.filterNot(_.getOutVertex.getIdentity.toString().equals(configRid))

    val edgesHasSteps : List[ConfigGraphEdgeDTO] = edgesHasStepsWithoutConfig.map(hasStep => {
      val target : String = RidToHash.setIdAndHash(
        hasStep.getInVertex.getIdentity.toString
      )._2
      val source : String = RidToHash.setIdAndHash(
        hasStep.getOutVertex.getIdentity.toString
      )._2
      ConfigGraphEdgeDTO(
        source = source,
        target = target
      )
    })

    val edgesHasComponents : List[ConfigGraphEdgeDTO] = eHasComponents.map(hasComponent => {
      val target : String = RidToHash.setIdAndHash(
        hasComponent.getInVertex.getIdentity.toString
      )._2
      val source : String = RidToHash.setIdAndHash(
        hasComponent.getOutVertex.getIdentity.toString
      )._2
      ConfigGraphEdgeDTO(
        source = source,
        target = target
      )
    })

    edgesHasSteps ::: edgesHasComponents
  }

  private def getConfigGraphD3LinksDTO(edges : List[ConfigGraphEdgeDTO]) : List[ConfigGraphD3LinkDTO] = {
    edges.map(e => {
      ConfigGraphD3LinkDTO(
        source = e.source,
        target = e.target
      )
    })
  }
}