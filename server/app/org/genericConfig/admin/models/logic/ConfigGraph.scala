package org.genericConfig.admin.models.logic

import com.orientechnologies.orient.core.sql.OCommandSQL
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientEdge, OrientElement, OrientGraph, OrientVertex}
import com.typesafe.config.impl.ConfigNodeComment
import org.genericConfig.admin.models.common.Error
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.models.persistence.orientdb.GraphCommon
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.{ConfigGraphComponentDTO, ConfigGraphDTO, ConfigGraphEdgeDTO, ConfigGraphStepDTO}

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

  def getLevel(stepRId: String) : (Set[ConfigGraphStepDTO], Set[ConfigGraphComponentDTO], List[ConfigGraphEdgeDTO]) = {
    val (eHasComponents, errorEdges) : (Option[List[OrientEdge]], Option[Error]) = GraphCommon.getEdgesOut(stepRId)

//    val (vComponents, errorComponents) : (Option[List[OrientVertex]], Option[Error]) = eHasComponents.get.map(_.getInVertex)
//
    ???
  }

  def calcPosition(
                    countOfElem : Int,
                    steps : Option[List[ConfigGraphStepDTO]] = None,
                    components : Option[List[ConfigGraphComponentDTO]] = None) = {
    val height : Int = 1000
    val width : Int = 1000
    //alle Elem auf einmal berechnet werden.
    //zugehörige Level definieren
  }

//  def getComponents(stepRid : String) : (Option[OrientVertex], Option[Error]) = {
//    val (eHasComponent, error) : (Option[OrientEdge], Option[Error]) = GraphCommon.getEdgesOut(stepRid)
//  }
}