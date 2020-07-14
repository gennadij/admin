package org.genericConfig.admin.models.persistence.orientdb

import com.orientechnologies.orient.core.sql.{OCommandSQL, OCommandSQLParsingException}
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.impls.orient.{OrientDynaElementIterable, OrientElement, OrientGraph, OrientVertex}
import org.genericConfig.admin.models.common.{DefectRIdError, Error, ODBClassCastError, ODBConnectionFail, ODBRecordDuplicated, ODBWriteError}
import org.genericConfig.admin.models.persistence.Database
import play.api.Logger


/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 10.04.2018
  */
object GraphCommon {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param outRid : String, inRid: String. label : String
    * @return Option[Error)
    */
  def appendTo(outRid: String, inRid: String, label: String): Option[Error] = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphCommon(graph).appendTo(outRid, inRid, label)
      case (None, Some(ODBConnectionFail())) =>
        Some(ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param rId : String, clazz : String
    * @return Option[Error)
    */
  def deleteVertex(rId: String, clazz: String): Option[Error] = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) =>
        val graph: OrientGraph = dbFactory.getTx
        new GraphCommon(graph).deleteVertex(rId, clazz)
      case (None, Some(ODBConnectionFail())) =>
        Some(ODBConnectionFail())
    }
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param rId : String
    * @return Option[List[OrientElement]], Option[Error]
    */
  def traverse(rId: String) : (Option[List[OrientElement]], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) => new GraphCommon(dbFactory.getTx).traverse(rId)
      case (None, Some(ODBConnectionFail())) => (None, Some(ODBConnectionFail()))
    }
  }

  def getVertex(rId: String): (Option[OrientVertex], Option[Error]) = {
    (Database.getFactory(): @unchecked) match {
      case (Some(dbFactory), None) => new GraphCommon(dbFactory.getTx).getVertex(rId)
      case (None, Some(ODBConnectionFail())) =>
        (None, Some(ODBConnectionFail()))
    }
  }

//  def addEdge(outRid : String, inRid : String, edgeLabel : String) : (Option[OrientEdge], Option[Error]) = {
//    (Database.getFactory(): @unchecked) match {
//      case (Some(dbFactory), None) => new GraphCommon(dbFactory.getTx).addEdge(outRid, inRid, edgeLabel)
//      case (None, Some(ODBConnectionFail())) =>
//        (None, Some(ODBConnectionFail()))
//    }
//  }
}

class GraphCommon(graph: OrientGraph) {

  private def appendTo(outRid: String, inRid: String, label : String) : Option[Error] = {
    try {
      val vOut : OrientVertex = graph.getVertex(outRid)
      val vIn : OrientVertex = graph.getVertex(inRid)
      graph.addEdge(PropertyKeys.CLASS + label, vOut, vIn, label)
      graph.commit()
      None
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        Some(ODBRecordDuplicated())
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        Some(ODBClassCastError())
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        Some(ODBWriteError())
    }
  }

 private def deleteVertex(rId : String, clazz : String) : Option[Error]  = {
   try {
     val sql: String = s"DELETE VERTEX $clazz where @rid=$rId"
     val res: Int = graph.command(new OCommandSQL(sql)).execute()
     graph.commit()
     res match {
       case 1 => None
       case _ => Some(DefectRIdError())
     }
   } catch {
     case e : OCommandSQLParsingException =>
       Logger.error(e.printStackTrace().toString)
       graph.rollback()
       Some(ODBRecordDuplicated())
     case e: ORecordDuplicatedException =>
       Logger.error(e.printStackTrace().toString)
       graph.rollback()
       Some(ODBRecordDuplicated())
     case e: ClassCastException =>
       graph.rollback()
       Logger.error(e.printStackTrace().toString)
       Some(ODBClassCastError())
     case e: Exception =>
       graph.rollback()
       Logger.error(e.printStackTrace().toString)
       Some(ODBWriteError())
   }
 }

  private def traverse(rId: String): (Option[List[OrientElement]], Option[Error]) = {
    try {
      import scala.collection.JavaConverters._
      val sql : String = s"traverse * from $rId"
      val res: OrientDynaElementIterable = graph.command(new OCommandSQL(sql)).execute()
      (Some(res.asScala.toList.map(_.asInstanceOf[OrientElement])), None)
    } catch {
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, Some(ODBRecordDuplicated()))
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBClassCastError()))
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBWriteError()))
    }
  }

  private def getVertex(rId : String) : (Option[OrientVertex], Option[Error]) = {
    try {
      val v : OrientVertex = graph.getVertex(rId)
      (Some(v), None)
    } catch {
      case e : OCommandSQLParsingException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, Some(ODBRecordDuplicated()))
      case e: ORecordDuplicatedException =>
        Logger.error(e.printStackTrace().toString)
        graph.rollback()
        (None, Some(ODBRecordDuplicated()))
      case e: ClassCastException =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBClassCastError()))
      case e: Exception =>
        graph.rollback()
        Logger.error(e.printStackTrace().toString)
        (None, Some(ODBWriteError()))
    }
  }
//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param
//    * @return Int count of deleted Vertexes
//    */
//  private def deleteStepAppendedToComponent(componentId: String): Int = {
//    val res: Int = graph
//      .command(new OCommandSQL(s"DELETE VERTEX Step where @rid IN (SELECT out() from Component where @rid='$componentId')")).execute()
//    graph.commit()
//    res
//  }

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


//  /**
//    * Loescht alle Steps und Components die zu der Config gehoeren
//    *
//    * @author Gennadi Heimann
//    * @version 0.1.0
//    * @param configId : String
//    * @return Count of deleted Vertexes
//    */

//  private def deleteAllStepsAndComponent(configId: String): Int = {
//    val sql: String = s"DELETE VERTEX V where @rid IN (traverse out() from (select out('hasFirstStep') " +
//      s"from Config where @rid='$configId'))"
//    val res: Int = graph
//      .command(new OCommandSQL(sql)).execute()
//    graph.commit()
//    res
//  }
//
//  private def deleteDependency(nameToShow: String): Int = {
//    // DELETE EDGE hasDependency WHERE nameToShow="dependency_C2_C3_user28_v015"
//    val sqlQuery = s"DELETE EDGE hasDependency WHERE nameToShow='$nameToShow'"
//    val res: Int = graph.command(new OCommandSQL(sqlQuery)).execute()
//    graph.commit
//    res
//  }
}