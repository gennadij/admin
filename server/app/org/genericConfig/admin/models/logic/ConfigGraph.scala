package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.Direction
import com.tinkerpop.blueprints.impls.orient.{OrientElement, OrientVertex}
import org.genericConfig.admin.models.common.Error
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.{ConfigGraphComponentDTO, ConfigGraphDTO, ConfigGraphStepDTO}

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

        steps.map(step => {
          step.asInstanceOf[OrientVertex].getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT)
        })



        ???
      case Some(error) => ???
    }


    //import scala.collection.JavaConverters._
    //val graph: OrientGraph = Database.getFactory()._1.get.getTx
    ////traverse * from (select @rid from AdminUser where username like "userUpdateComponent")
    //val sql: String = s"traverse * from (select @rid from AdminUser where username like 'userUpdateComponent')"
    //val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
    //val elementIterable = res.asScala.toList
    //val vertex = elementIterable.filter(_.isInstanceOf[OrientVertex])
    //val edges = elementIterable.filter(_.isInstanceOf[OrientEdge])
    //val vAdminUser = vertex.filter(_.asInstanceOf[OrientVertex].getRecord().getClassName == "AdminUser")
    //val vStep = vertex.filter(_.asInstanceOf[OrientVertex].getRecord().getClassName == "Step")
    //val vConfig = vertex.filter(_.asInstanceOf[OrientVertex].getRecord().getClassName == "Config")
    //val vComponent = vertex.filter(_.asInstanceOf[OrientVertex].getRecord().getClassName == "Component")
    //val eHasStep = edges.filter(_.asInstanceOf[OrientEdge].getRecord.getClassName == "hasStep")
    //val eHasComponent = edges.filter(_.asInstanceOf[OrientEdge].getRecord.getClassName == "hasComponent")
    //
    //edges.foreach(elem => {println(elem.asInstanceOf[OrientEdge].getRecord.getClassName)})
    //
    //vAdminUser.foreach(elem => {println(elem)})
    //vStep.foreach(elem => {println(elem)})
    //vConfig.foreach(elem => {println(elem)})
    //vComponent.foreach(elem => {println(elem)})
    //eHasComponent.foreach(elem => {println(elem)})
    //eHasStep.foreach(elem => {println(elem.asInstanceOf[OrientEdge].getOutVertex.getIdentity)})



    ConfigGraphDTO(
      action = Actions.CONFIG_GRAPH
    )
  }

  def calcPosition(
                    countOfElem : Int,
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
      level += 1
      ConfigGraphStepDTO(
        id = step.getIdentity.toString,
        x = x,
        y = y
      )
    })

    level = 2

    val configGraphComponentDTO : List[ConfigGraphComponentDTO] = components.get.map(component => {

      ConfigGraphComponentDTO(
        id = component.getIdentity.toString,
        x = 0,
        y = 0
      )
    })

    (configGraphComponentDTO, configGraphStepDTO)

    //alle Elem auf einmal berechnet werden.
    //zugehörige Level definieren
  }

  def calcXPos() = {

  }

//  def getComponents(stepRid : String) : (Option[OrientVertex], Option[Error]) = {
//    val (eHasComponent, error) : (Option[OrientEdge], Option[Error]) = GraphCommon.getEdgesOut(stepRid)
//  }
}