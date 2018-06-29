package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.exception.OValidationException
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.{Direction, Edge, Vertex}
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientEdge, OrientGraph, OrientVertex}
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.models.persistence.Database
import org.genericConfig.admin.shared.common.status._
import org.genericConfig.admin.shared.config.status._
import org.genericConfig.admin.shared.configTree.bo._
import org.genericConfig.admin.shared.configTree.status._
import org.genericConfig.admin.shared.step.bo.StepBO
import org.genericConfig.admin.shared.step.status._
import org.genericConfig.admin.shared.user.status._
import play.api.Logger

import scala.collection.JavaConverters._
import org.genericConfig.admin.shared.component.bo.ComponentBO
import org.genericConfig.admin.shared.component.status._


/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 10.04.2018
  */
object Graph {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param username : String, password: String
    * @return (Option[OrientVertex], StatusAddUser, Status)
    */
  def addUser(username: String, password: String): (Option[OrientVertex], StatusAddUser, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).addUser(username, password)
      case (None, ODBConnectionFail()) =>
        (None, AddUserError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param username : String, password: String
    * @return (Option[OrientVertex], StatusGetUser, Status)
    */
  def getUser(username: String, password: String): (Option[OrientVertex], StatusGetUser, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).getUser(username, password)
      case (None, ODBConnectionFail()) =>
        (None, GetUserError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String
    * @return (String, Status)
    */
  def getUserId(configId: String): (String, Status) = {

    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).getUserId(configId)
      case (None, ODBConnectionFail()) =>
        ("", ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configUrl : String
    * @return (Option[OrientVertex], StatusAddConfig, Status)
    */
  def addConfig(configUrl: String): (Option[OrientVertex], StatusAddConfig, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).addConfig(configUrl)
      case (None, ODBConnectionFail()) =>
        (None, AddConfigError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId : String, configId: String
    * @return Status
    */
  def appendConfigTo(userId: String, configId: String): Status = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).appendConfigTo(userId, configId)
      case (None, ODBConnectionFail()) =>
        ODBConnectionFail()
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId : String
    * @return (Option[List[OrientVertex\]\], StatusGetConfigs, Status)
    */
  def getConfigs(userId: String): (Option[List[OrientVertex]], StatusGetConfigs, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).getConfigs(userId)
      case (None, ODBConnectionFail()) =>
        (None, GetConfigsError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String, configUrl: String
    * @return (StatusDeleteConfig, Status)
    */
  def deleteConfig(configId: String, configUrl: String): (StatusDeleteConfig, Status) = {

    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).deleteConfig(configId, configUrl)
      case (None, ODBConnectionFail()) =>
        (DeleteConfigError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param username : String
    * @return Int
    */
  def deleteAllConfigs(username: String): Int = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).deleteAllConfigs(username)
      case (None, ODBConnectionFail()) =>
        0
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String, configUrl: String
    * @return (Option[OrientVertex], StatusUpdateConfig, Status)
    */
  def updateConfig(configId: String, configUrl: String): (Option[OrientVertex], StatusUpdateConfig, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).updateConfig(configId, configUrl)
      case (None, ODBConnectionFail()) =>
        (None, UpdateConfigError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String
    * @return (Option[StepForConfigTreeBO], StatusGetConfigTree, Status)
    */
  def getConfigTree(configId: String): (Option[StepForConfigTreeBO], StatusGetConfigTree, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).getConfigTree(configId)
      case (None, ODBConnectionFail()) =>
        (None, GetConfigTreeError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return (Option[OrientVertex], StatusAddStep, Status)
    */
  def addStep(stepBO: StepBO): (Option[OrientVertex], StatusAddStep, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).addStep(stepBO)
      case (None, ODBConnectionFail()) =>
        (None, AddStepError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param id : String, stepId: String
    * @return (StatusAppendStep, Status)
    */
  def appendStepTo(id: String, stepId: String): (StatusAppendStep, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).appendStepTo(id, stepId)
      case (None, ODBConnectionFail()) =>
        (AppendStepError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepId : String
    * @return (StatusDeleteStep, Status)
    */
  def deleteStep(stepId: String): (StatusDeleteStep, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).deleteStep(stepId)
      case (None, ODBConnectionFail()) =>
        (DeleteStepError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String
    * @return Int
    */
  def deleteStepAppendedToConfig(configId: String): Int = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).deleteStepAppendedToConfig(configId)
      case (None, ODBConnectionFail()) =>
        0
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return (StatusUpdateStep, Status)
    */
  def updateStep(stepBO: StepBO): (StatusUpdateStep, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).updateStep(stepBO)
      case (None, ODBConnectionFail()) =>
        (UpdateStepError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param componentBO : ComponentBO
    * @return (Option[OrientVertex], StatusAddComponent, Status)
    */
  def addComponent(componentBO: ComponentBO): (Option[OrientVertex], StatusAddComponent, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).addComponent(componentBO)
      case (None, ODBConnectionFail()) =>
        (None, AddComponentError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param componentBO : ComponentBO
    * @return (StatusAppendComponent, Status)
    */
  def appendComponentToStep(componentBO: ComponentBO): (StatusAppendComponent, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).appendComponentTo(componentBO)
      case (None, ODBConnectionFail()) =>
        (AppendComponentError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param componentBO : ComponentBO
    * @return (StatusDeleteComponent, Status)
    */
  def deleteComponent(componentBO: ComponentBO): (StatusDeleteComponent, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).deleteComponent(componentBO)
      case (None, ODBConnectionFail()) =>
        (DeleteComponentError(), ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param componentBO : ComponentBO
    * @return (Option[OrientVertex], StatusAddComponent, Status)
    */
  def updateComponent(componentBO: ComponentBO): (Option[OrientVertex], StatusUpdateComponent, Status) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), Success()) =>
        val graph: OrientGraph = dbFactory.getTx
        new Graph(graph).updateComponent(componentBO)
      case (None, ODBConnectionFail()) =>
        (None, UpdateComponentError(), ODBConnectionFail())
    }
  }
}


class Graph(graph: OrientGraph) {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param username : String, password: String
    * @return (Option[OrientVertex], StatusAddUser, Status)
    */
  private def addUser(username: String, password: String): (Option[OrientVertex], StatusAddUser, Status) = {

    try {
      val vAdminUseres: List[Vertex] = graph.getVertices(PropertyKeys.USERNAME, username).asScala.toList
      if (vAdminUseres.isEmpty) {
        val vAdminUser: OrientVertex = graph.addVertex("class:" + PropertyKeys.VERTEX_ADMIN_USER,
          PropertyKeys.USERNAME, username,
          PropertyKeys.PASSWORD, password)
        graph.commit()
        (Some(vAdminUser), AddUserSuccess(), Success())
      } else if (vAdminUseres.size == 1) {
        (Some(vAdminUseres.head.asInstanceOf[OrientVertex]), AddUserAlreadyExist(), Error())
      } else {
        (None, AddUserError(), Error())
      }
    } catch {
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(message = e.printStackTrace().toString)
        (None, AddUserError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, AddUserError(), ODBReadError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param username : String, password: String
    * @return (Option[OrientVertex], StatusGetUser, Status)
    */
  private def getUser(username: String, password: String): (Option[OrientVertex], StatusGetUser, Status) = {
    //TODO OCommandExecutionException

    try {
      val dynElemUsers: OrientDynaElementIterable =
        graph.command(new OCommandSQL(s"SELECT FROM AdminUser WHERE username='$username' and password='$password'")).execute()
      graph.commit()
      val vUsers: List[OrientVertex] = dynElemUsers.asScala.toList map (_.asInstanceOf[OrientVertex])

      vUsers match {
        case userCount if userCount.size == 1 => (Some(vUsers.head), GetUserSuccess(), Success())
        case _ => (None, GetUserNotExist(), Error())
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, GetUserAlreadyExist(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, GetUserError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, GetUserError(), ODBReadError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String
    * @return (String, Status)
    */
  private def getUserId(configId: String): (String, Status) = {
    try {
      val userId: List[String] =
        graph.getVertex(configId)
          .getEdges(Direction.IN, PropertyKeys.EDGE_HAS_CONFIG).asScala.toList map (eHasConfig => {
          eHasConfig.getVertex(Direction.OUT).asInstanceOf[OrientVertex].getIdentity.toString()
        })
      userId.size match {
        case size if size == 1 => (userId.head, Success())
        case _ => ("", Error())
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        ("", ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        ("", ODBClassCastError())
      case e: NullPointerException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        ("", ODBNullPointer())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        ("", ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configUrl : String
    * @return (Option[OrientVertex], StatusAddConfig, Status)
    */
  private def addConfig(configUrl: String): (Option[OrientVertex], StatusAddConfig, Status) = {
    try {
      val vConfig: OrientVertex = graph.addVertex(
        "class:" + PropertyKeys.VERTEX_CONFIG,
        PropertyKeys.CONFIG_URL, configUrl)
      graph.commit()
      (Some(vConfig), AddConfigSuccess(), Success())
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, AddConfigAlreadyExist(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, AddConfigError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, AddConfigError(), ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId : String, configId: String
    * @return Status
    */
  private def appendConfigTo(userId: String, configId: String): Status = {
    try {
      val vUser: OrientVertex = graph.getVertex(userId)
      val vConfig: OrientVertex = graph.getVertex(configId)
      graph.addEdge(
        "class:" + PropertyKeys.EDGE_HAS_CONFIG,
        vUser,
        vConfig,
        PropertyKeys.EDGE_HAS_CONFIG
      )
      graph.commit()
      Success()
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        ODBRecordDuplicated()
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        ODBClassCastError()
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        ODBWriteError()
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param userId : String
    * @return (Option[List[OrientVertex\]\], StatusGetConfigs, Status)
    */
  private def getConfigs(userId: String): (Option[List[OrientVertex]], StatusGetConfigs, Status) = {
    try {
      val vConfigs: List[OrientVertex] =
        graph.getVertex(userId).getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_CONFIG).asScala.toList map (
          _.asInstanceOf[OrientEdge].getVertex(Direction.IN))
      vConfigs match {
        case List() => (Some(vConfigs), GetConfigsEmpty(), Success())
        case _ => (Some(vConfigs), GetConfigsSuccess(), Success())
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, GetConfigsError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, GetConfigsError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, GetConfigsError(), ODBReadError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String, configUrl: String
    * @return (StatusDeleteConfig, Status)
    */
  private def deleteConfig(configId: String, configUrl: String): (StatusDeleteConfig, Status) = {
    try {
      val sql: String = s"DELETE VERTEX Config where @rid=$configId and configUrl='$configUrl'"
      val res: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit()
      res match {
        case 1 => (DeleteConfigSuccess(), Success())
        case _ => (DeleteConfigDefectID(), Error())
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (DeleteConfigError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (DeleteConfigError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (DeleteConfigError(), ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param username : String
    * @return Int
    */
  private def deleteAllConfigs(username: String): Int = {
    val sql: String = s"delete vertex Config where @rid in (traverse out('hasConfig') from (select out() from AdminUser where username='$username'))"
    val res: Int = graph.command(new OCommandSQL(sql)).execute()
    graph.commit()
    res
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String, configUrl: String
    * @return (Option[OrientVertex], StatusUpdateConfig, Status)
    */
  private def updateConfig(configId: String, configUrl: String): (Option[OrientVertex], StatusUpdateConfig, Status) = {
    try {
      graph.getVertex(configId).setProperty(PropertyKeys.CONFIG_URL, configUrl)

      graph.commit()

      val vUpdatedConfig = graph.getVertex(configId)

      (Some(vUpdatedConfig), UpdateConfigUpdated(), Success())
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, UpdateConfigError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, UpdateConfigError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, UpdateConfigError(), ODBWriteError())
    }
  }

  /**
    * Converting of the rid to hash from steps and components is here
    *
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String
    * @return (Option[StepForConfigTreeBO], StatusGetConfigTree, Status)
    */
  private def getConfigTree(configId: String): (Option[StepForConfigTreeBO], StatusGetConfigTree, Status) = {
    try {
      val vFirstStep: List[OrientVertex] =
        graph.getVertex(configId)
          .getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP)
          .asScala.toList.map(eHasFirstStep => {
          eHasFirstStep.getVertex(Direction.IN).asInstanceOf[OrientVertex]
        })
      vFirstStep.size match {
        case count if count == 1 =>
          val configTree = Some(StepForConfigTreeBO(
            stepId = vFirstStep.head.getIdentity.toString,
            nameToShow = vFirstStep.head.getProperty(PropertyKeys.NAME_TO_SHOW),
            kind =  vFirstStep.head.getProperty(PropertyKeys.KIND),
            nextSteps = getNextSteps(vFirstStep.head),
            components = getComponents(vFirstStep.head)
          ))
          (configTree, GetConfigTreeSuccess(), Success())
        case _ => (None, GetConfigTreeEmpty(), Success())
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, GetConfigTreeError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, GetConfigTreeError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, GetConfigTreeError(), ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param step : Option[OrientVertex]
    * @return Set[Option[ComponentForConfigTreeBO\]\]
    */
  private def getComponents(step: OrientVertex): Set[ComponentForConfigTreeBO] = {
    val componentForConfigTreeBOs: List[ComponentForConfigTreeBO] =
      step.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList map {
        eHasComponent =>
          val vComponent: OrientVertex = eHasComponent.getVertex(Direction.IN).asInstanceOf[OrientVertex]
          val eHasSteps: List[Edge] = vComponent.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList
          val nextStepId: String = eHasSteps match {
            case List() => "last"
            case _ => eHasSteps.head.getVertex(Direction.IN).asInstanceOf[OrientVertex].getIdentity.toString()
          }

          ComponentForConfigTreeBO(
            componentId = vComponent.getIdentity.toString(),
            nameToShow = vComponent.getProperty(PropertyKeys.NAME_TO_SHOW),
            kind = vComponent.getProperty(PropertyKeys.KIND),
            nextStepId = nextStepId
          )
      }
    componentForConfigTreeBOs.toSet
  }

  private def getNextSteps(step: OrientVertex): Set[StepForConfigTreeBO] = {
    val stepForConfigTreeBOs: List[StepForConfigTreeBO] =
      step.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList map {
      eHasComponent =>
        val vComponent: OrientVertex = eHasComponent.getVertex(Direction.IN).asInstanceOf[OrientVertex]
        val eHasSteps: List[Edge] = vComponent.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList
        eHasSteps match {
          case eHSs :: _ =>
            val vStep: OrientVertex = eHSs.getVertex(Direction.IN).asInstanceOf[OrientVertex]
                StepForConfigTreeBO(
                  stepId = vStep.getIdentity.toString,
                  nameToShow = vStep.getProperty(PropertyKeys.NAME_TO_SHOW),
                  kind = vStep.getProperty(PropertyKeys.KIND),
                  nextSteps = getNextSteps(vStep),
                  components = getComponents(vStep)
                )
          case List() =>
            StepForConfigTreeBO(
              stepId = "",
              nameToShow = "",
              kind = "",
              nextSteps = Set(),
              components = Set()
            )
        }
    }
    stepForConfigTreeBOs.filter(_.stepId != "").toSet
  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param step : Option[OrientVertex]
//    * @return Set[Option[ComponentForConfigTreeBO\]\]
//    */
//  private def getComponents(step: OrientVertex): Set[Option[ComponentForConfigTreeBO]] = {
//
//    val componentsForConfigTreeBO: List[Option[ComponentForConfigTreeBO]] =
//      step.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_COMPONENT).asScala.toList map {
//        eHasComponent =>
//          val vComponent: OrientVertex = eHasComponent.getVertex(Direction.IN).asInstanceOf[OrientVertex]
//          val eHasSteps: List[Edge] = vComponent.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList
//          val componentForConfigTreeBO: Option[ComponentForConfigTreeBO] = eHasSteps match {
//            case List() =>
//              //create last componentForConfigTreeBO
//              Some(ComponentForConfigTreeBO(
//                componentId = vComponent.getIdentity.toString(),
//                nameToShow = vComponent.getProperty(PropertyKeys.NAME_TO_SHOW),
//                kind = vComponent.getProperty(PropertyKeys.KIND),
//                nextStep = None
//              ))
//            case _ =>
//              //get nestStep of componentForConfigTreeBO
//              val nextSteps: List[StepForConfigTreeBO] = eHasSteps.map {
//                eHasStep => {
//                  val vStep: OrientVertex = eHasStep.getVertex(Direction.IN).asInstanceOf[OrientVertex]
//                  val components: Set[Option[ComponentForConfigTreeBO]] = getComponents(vStep)
//                  StepForConfigTreeBO(
//                    stepId = vStep.getIdentity.toString,
//                    nameToShow = vStep.getProperty(PropertyKeys.NAME_TO_SHOW),
//                    kind = vStep.getProperty(PropertyKeys.KIND),
//                    components = components
//                  )
//                }
//              }
//              // get all componentForConfigTreeBO form nextStep
//              val defaultComponent: Option[ComponentForConfigTreeBO] = nextSteps.size match {
//                case count if count == 1 =>
//                  Some(ComponentForConfigTreeBO(
//                    componentId = vComponent.getIdentity.toString(),
//                    nameToShow = vComponent.getProperty(PropertyKeys.NAME_TO_SHOW),
//                    kind = vComponent.getProperty(PropertyKeys.KIND),
//                    nextStep = Some(nextSteps.head)
//                  ))
//                case _ => None // Fehler eine Komponente kann nicht 2 Steps haben
//              }
//              defaultComponent
//          }
//          componentForConfigTreeBO
//      }
//
//    //    val componentsWithoutDuplicate = findDuplicate(componentsForConfigTreeBO)
//    //
//    //    componentsWithoutDuplicate.toSet
//    //TODO findDuplicate implement in logik
//    componentsForConfigTreeBO.toSet
//  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return (Option[OrientVertex], StatusAddStep, Status)
    */
  private def addStep(stepBO: StepBO): (Option[OrientVertex], StatusAddStep, Status) = {
    try {
      stepBO.appendToId match {
        case Some(appendToId) =>
          addStepTo(stepBO = stepBO, rId = appendToId)
        case None => (None, AddStepDefectComponentOrConfigId(), ODBRecordIdDefect())
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, AddStepError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, AddStepError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, AddStepError(), ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO, rId: String
    * @return (Option[OrientVertex], StatusAddStep, Status)
    */
  private def addStepTo(stepBO: StepBO, rId: String): (Option[OrientVertex], StatusAddStep, Status) = {
    val v: OrientVertex = graph.getVertex(rId)
    v match {
      case null => (None, AddStepDefectComponentOrConfigId(), ODBRecordIdDefect())
      case _ =>
        val countOfSteps: Int = v.getEdges(Direction.OUT, PropertyKeys.EDGE_HAS_STEP).asScala.toList.size
        countOfSteps match {
          case count if count > 0 => (None, AddStepAlreadyExist(), Error())
          case _ =>
            val vStep: OrientVertex = graph.addVertex(
              PropertyKeys.CLASS + PropertyKeys.VERTEX_STEP,
              PropertyKeys.NAME_TO_SHOW, stepBO.nameToShow.get,
              PropertyKeys.KIND, stepBO.kind.get,
              PropertyKeys.SELECTION_CRITERIUM_MIN, stepBO.selectionCriteriumMin.get.toString,
              PropertyKeys.SELECTION_CRITERIUM_MAX, stepBO.selectionCriteriumMax.get.toString
            )
            graph.commit()
            (Some(vStep), AddStepSuccess(), Success())
        }
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param id : String, stepId: String
    * @return (StatusAppendStep, Status)
    */
  private def appendStepTo(id: String, stepId: String): (StatusAppendStep, Status) = {
    try {
      val v: OrientVertex = graph.getVertex(id)
      val vStep: OrientVertex = graph.getVertex(stepId)
      graph.addEdge(
        PropertyKeys.CLASS + PropertyKeys.EDGE_HAS_STEP, v, vStep,
        PropertyKeys.EDGE_HAS_STEP
      )
      graph.commit()
      (AppendStepSuccess(), Success())
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (AppendStepError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (AppendStepError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (AppendStepError(), ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepId : String
    * @return (StatusDeleteStep, Status)
    */
  private def deleteStep(stepId: String): (StatusDeleteStep, Status) = {
    try {
      val sql: String = s"DELETE VERTEX Step where @rid=$stepId"
      val res: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit()
      res match {
        case 1 => (DeleteStepSuccess(), Success())
        case _ => (DeleteStepDefectID(), Error())
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (DeleteStepError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (DeleteStepError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (DeleteStepError(), ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configId : String
    * @return Int count of deleted Vertexes
    */
  private def deleteStepAppendedToConfig(configId: String): Int = {
    val res: Int = graph
      .command(new OCommandSQL(s"DELETE VERTEX Step where @rid IN (SELECT out() from Config where @rid='$configId')")).execute()
    graph.commit()
    res
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StatusUpdateStep, Status
    */
  private def updateStep(stepBO: StepBO): (StatusUpdateStep, Status) = {
    try {
      val vStep: OrientVertex = graph.getVertex(stepBO.stepId.get)
      vStep.setProperty(PropertyKeys.NAME_TO_SHOW, stepBO.nameToShow.get)
      vStep.setProperty(PropertyKeys.KIND, stepBO.kind.get)
      vStep.setProperty(PropertyKeys.SELECTION_CRITERIUM_MIN, stepBO.selectionCriteriumMin.get.toString)
      vStep.setProperty(PropertyKeys.SELECTION_CRITERIUM_MAX, stepBO.selectionCriteriumMax.get.toString)
      graph.commit()
      (UpdateStepSuccess(), Success())
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (UpdateStepError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (UpdateStepError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (UpdateStepError(), ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentBO : ComponentBO
    * @return (Option[OrientVertex], StatusAddComponent, Status)
    */
  private def addComponent(componentBO: ComponentBO): (Option[OrientVertex], StatusAddComponent, Status) = {
    try {
      val vComponent: OrientVertex = graph.addVertex(
        "class:" + PropertyKeys.VERTEX_COMPONENT,
        PropertyKeys.NAME_TO_SHOW, componentBO.nameToShow.get,
        PropertyKeys.KIND, componentBO.kind.get
      )
      graph.commit()
      (Some(vComponent), AddComponentSuccess(), Success())
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, AddComponentError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, AddComponentError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, AddComponentError(), ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentBO : ComponentBO
    * @return OrientEdge
    */
  private def appendComponentTo(componentBO: ComponentBO): (StatusAppendComponent, Status) = {
    try {
      graph.addEdge(
        "class:" + PropertyKeys.EDGE_HAS_COMPONENT,
        graph.getVertex(componentBO.stepId.get),
        graph.getVertex(componentBO.componentId.get),
        PropertyKeys.EDGE_HAS_COMPONENT
      )
      graph.commit()

      (AppendComponentSuccess(), Success())
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (AppendComponentError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (AppendComponentError(), ODBClassCastError())
      case e: NullPointerException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (AppendComponentError(), ODBNullPointer())
      case e: OValidationException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (AppendComponentError(), ODBValidationException())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (AppendComponentError(), ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentBO : ComponentBO
    * @return OrientEdge
    */
  private def deleteComponent(componentBO: ComponentBO): (StatusDeleteComponent, Status) = {
    try {
      val sql: String = s"DELETE VERTEX Component where @rid=${componentBO.componentId.get}"
      val res: Int = graph.command(new OCommandSQL(sql)).execute()
      graph.commit()
      res match {
        case 1 => (DeleteComponentSuccess(), Success())
        case _ => (DeleteComponentDefectID(), Error())
      }
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (DeleteComponentError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (DeleteComponentError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (DeleteComponentError(), ODBWriteError())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param componentBO : ComponentBO
    * @return (Option[OrientVertex], StatusUpdateComponent, Status)
    */
  private def updateComponent(componentBO: ComponentBO): (Option[OrientVertex], StatusUpdateComponent, Status) = {
    try {
      val vComponent: OrientVertex = graph.getVertex(componentBO.componentId.get)
      vComponent.setProperty(PropertyKeys.NAME_TO_SHOW, componentBO.nameToShow.get)
      vComponent.setProperty(PropertyKeys.KIND, componentBO.kind.get)
      graph.commit()
      (Some(vComponent), UpdateComponentSuccess(), Success())
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, UpdateComponentError(), ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, UpdateComponentError(), ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, UpdateComponentError(), ODBWriteError())
    }
  }

  //  /**
  //    * @author Gennadi Heimann
  //    * @version 0.1.6
  //    * @param components : List[Option[ComponentForConfigTreeBO\]\]
  //    * @return List[Option[ComponentForConfigTreeBO\]\]
  //    */
  //  def findDuplicate(
  //                     components: List[Option[ComponentForConfigTreeBO]]): List[Option[ComponentForConfigTreeBO]] = components match {
  //    case List() => List()
  //    case x :: xs => insert(x, findDuplicate(xs))
  //  }
  //
  //  /**
  //    * @author Gennadi Heimann
  //    * @version 0.1.6
  //    * @param x : Option[ComponentForConfigTreeBO],
  //    *          xs: List[Option[ComponentForConfigTreeBO]
  //    * @return List[Option[ComponentForConfigTreeBO\]\]
  //    */
  //  def insert(
  //              x: Option[ComponentForConfigTreeBO],
  //              xs: List[Option[ComponentForConfigTreeBO]]): List[Option[ComponentForConfigTreeBO]] = xs match {
  //    case List() => List(x)
  //    case y :: ys => if (x.get.nextStepId == y.get.nextStepId)
  //      Some(x.get.copy(nextStep = None)) :: xs
  //    else y :: insert(x, ys)
  //  }


  /**
    * Loescht alle Steps und Components die zu der Config gehoeren
    *
    * @author Gennadi Heimann
    * @version 0.1.0
    * @param configId : String
    * @return Count of deleted Vertexes
    */

  private def deleteAllStepsAndComponent(configId: String): Int = {
    val sql: String = s"DELETE VERTEX V where @rid IN (traverse out() from (select out('hasFirstStep') " +
      s"from Config where @rid='$configId'))"
    val res: Int = graph
      .command(new OCommandSQL(sql)).execute()
    graph.commit()
    res
  }
}