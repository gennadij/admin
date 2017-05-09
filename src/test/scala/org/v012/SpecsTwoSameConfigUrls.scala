package org.v012

import com.orientechnologies.orient.core.db.record.OIdentifiable
import com.orientechnologies.orient.core.id.ORID
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import org.admin.AdminWeb
import org.junit.runner.RunWith
import org.persistence.db.orientdb.{AdminUserVertex, OrientDB}
import org.persistence.{GlobalConfigForDB, TestDBv012}
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import preparingConfigs.PreparingConfigsForTests

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 05.05.2017
	*
	* Username = user13
	* Username = user14
	*/

@RunWith(classOf[JUnitRunner])
class SpecsTwoSameConfigUrls extends Specification
	with AdminWeb
	with BeforeAfterAll{

	def beforeAll = {
		GlobalConfigForDB.setDb(new TestDBv012)
		PreparingConfigsForTests.prepareSpecsTwoSameConfigUrls
		val count = AdminUserVertex.deleteAdmin("user14")
		require(count == 1, count.toString)
	}
	def afterAll = {
//		val username = "user14"
//		val sql_1: String = s"DELETE VERTEX AdminUser where username='$username'"
//		val graph: OrientGraph = OrientDB.getGraph
//		graph.commit
	}

	"Hier wird die Erzeugung von zwei verschiedenen AdminUser mit gleicher ConfigUrl spezifiziert" >> {
		"ORecordDuplicatedException" >> {
			PreparingConfigsForTests.registerNewUser("user14")

			val user14 = PreparingConfigsForTests.login("user14")
			val exception = try{
				PreparingConfigsForTests.createNewConfig(user14, "http://contig/user13")
			}catch {
				case oRecordDuplicatedException: ORecordDuplicatedException => "ORecordDuplicatedException"
				case _: Throwable => println("Got some other kind of exception")
			}
			exception === "ORecordDuplicatedException"
		}
	}
  
}