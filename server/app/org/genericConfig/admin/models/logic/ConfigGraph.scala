package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.{OrientEdge, OrientElement, OrientVertex}
import org.genericConfig.admin.models.common.Error
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.configGraph.{ConfigGraphComponentDTO, ConfigGraphD3DTO, ConfigGraphD3LinkDTO, ConfigGraphD3NodeDTO, ConfigGraphDTO, ConfigGraphEdgeDTO, ConfigGraphResultDTO, ConfigGraphStepDTO}
import play.api.Logger

import scala.annotation.tailrec
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

        val nodes : List[D3Node] = calcPosition(configRid)

        val levels : List[Int] = nodes.map(_.level.value)

        val maxLevel = levels.sortWith(_ < _).last

        val width : Int = 1000

        val nodesWithY = nodes.map(n => {
          val x : Int = (width.toDouble * (n.level.value.toDouble) / (maxLevel + 1)).toInt
          n.copy(x = x)
        })

        val configGraphD3NodesDTO : List[ConfigGraphD3NodeDTO] = nodesWithY.map(e => {
          ConfigGraphD3NodeDTO(
            id = RidToHash.setIdAndHash(e.id)._2,
            x = e.x.toInt,
            y = e.y.toInt
          )
        })



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

  /*
  ID : #43:78  | level : 1 | OrderNumber:  1 -> S1
  ID : #52:94  | level : 2 | OrderNumber:  1 -> S1_C1
  ID : #49:106 | level : 2 | OrderNumber:  2 -> S1_C2
  ID : #50:105 | level : 2 | OrderNumber:  3 -> S1_C3
  ID : #44:71  | level : 3 | OrderNumber:  1 -> S2
  ID : #51:101 | level : 4 | OrderNumber:  1 -> S2_C1
  ID : #52:95  | level : 4 | OrderNumber:  2 -> S2_C2
  ID : #41:93  | level : 4 | OrderNumber:  1 -> S3
  ID : #49:107 | level : 5 | OrderNumber:  1 -> S3_C1
  ID : #50:106 | level : 5 | OrderNumber:  2 -> S3_C2
  ID : #51:102 | level : 5 | OrderNumber:  3 -> S3_C3
  ID : #52:96  | level : 5 | OrderNumber:  4 -> S3_C4


   */

  private def calcPosition(configRid : String): List[D3Node] = {

    val height : Int = 600


    val (vConfig, error) : (Option[OrientVertex], Option[Error]) = GraphCommon.getVertex(rId = configRid)

    val eHasStep : List[OrientEdge] =
      vConfig.get.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList.map(_.asInstanceOf[OrientEdge])

    val vFirstStep : OrientVertex = eHasStep.head.getVertex(Direction.IN)

    val allElem = ListBuffer[D3Node]()
    val res = calcPositionRecursive(allElem, Nil, vFirstStep, height, MutableInt(1))

    allElem.foreach(r => {
      println("ID : " + r.id  + " | level : " + r.level.value + " | OrderNumber:  " + r.orderNumber)
    })

    allElem.toList
  }

  private def calcPositionRecursive(allElem : ListBuffer[D3Node], n : List[D3Node], v : OrientVertex, height : Int, level : MutableInt): List[D3Node] = {
    val vComponents: List[OrientVertex] = getComponents(v)

    val vNextSteps : Set[OrientVertex] = getNextStep(vComponents)

    val componentsNode : List[D3Node] = getConfigGraphD3Nodes(vComponents, v, height, level, 0)

    val currentNodes = componentsNode ++ n

    val cN: Set[List[D3Node]] = for (i <- vNextSteps) yield {
      val res = calcPositionRecursive(allElem, currentNodes, i, height, level)
      res
    }

    val list = cN.flatten.to[ListBuffer]

    allElem ++= list

    currentNodes
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
                                     level : MutableInt,
                                     orderNumber : Int
                                   ) : List[D3Node] = {

    if (level.value != 1) {
      level + 1
    }

    val step = D3Node(
      id = vStep.getIdentity.toString(),
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

//  private def calcPosition(
//                    steps : Option[List[OrientElement]] = None,
//                    components : Option[List[OrientElement]] = None
//                  ): (List[ConfigGraphComponentDTO], List[ConfigGraphStepDTO]) = {
//    val height : Int = 1000
//    val width : Int = 1000
//    var level : Int = 1
//    val levelWidth : Int = width / steps.get.length
//
//    val configGraphStepDTO : List[ConfigGraphStepDTO] = steps.get.map(step => {
//      val y = height / 2
//      val x = level * levelWidth
//      level += 2
//      ConfigGraphStepDTO(
//        id = step.getIdentity.toString,
//        x = x,
//        y = y
//      )
//    })
//    import scala.collection.JavaConverters._
//
//    val componentsPerLevel : List[Int] = steps.get.map(step => {
//      val edges : java.lang.Iterable[Edge] = step.asInstanceOf[OrientVertex].getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT)
//      edges.asScala.toList.length
//    })
//
//    level = 2
//    var count : Int = 0
//    //TODO componentsPerLevel = 3 components = 9 funktioneirt nicht (Statische Graph wird spaeter gemacht)
//    val configGraphComponentDTO : List[ConfigGraphComponentDTO] = components.get.map(component => {
//      val x = level * levelWidth
//      val y = height / componentsPerLevel(count)
//      level += 2
//      count += 1
//      ConfigGraphComponentDTO(
//        id = component.getIdentity.toString,
//        x = x,
//        y = y
//      )
//    })
//
//    (configGraphComponentDTO, configGraphStepDTO)
//  }
}