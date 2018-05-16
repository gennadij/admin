package models.v012

import com.orientechnologies.orient.core.db.record.OIdentifiable
import com.orientechnologies.orient.core.id.ORID
import com.orientechnologies.orient.core.sql.OCommandSQL
import com.orientechnologies.orient.core.storage.ORecordDuplicatedException
import com.tinkerpop.blueprints.impls.orient.{OrientGraph, OrientVertex}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import org.specs2.specification.BeforeAfterAll
import models.preparingConfigs.PrepareConfigsForSpecsv012
import models.preparingConfigs.GeneralFunctionToPrepareConfigs
import play.api.Logger
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import org.genericConfig.admin.controllers.websocket.WebClient
import models.preparingConfigs.PrepareConfigsForSpecsv011
import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.config.status.AddConfigAlreadyExist
import org.genericConfig.admin.shared.common.status.ODBRecordDuplicated

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 05.05.2017
	*
	* Username = user13
	* Username = user14
	*/

@RunWith(classOf[JUnitRunner])
class TwoSameConfigUrlsSpecs extends Specification
	with BeforeAfterAll
	with GeneralFunctionToPrepareConfigs{
  

  val webClient = WebClient.init
  
	def beforeAll = {
		PrepareConfigsForSpecsv012.prepareTwoSameConfigUrls(webClient)
		val count = PrepareConfigsForSpecsv011.deleteAdmin("user14")
		require(count == 1, count.toString)
	}
	def afterAll = {}

	"Hier wird die Erzeugung von zwei verschiedenen AdminUser mit gleicher ConfigUrl spezifiziert" >> {
		"ORecordDuplicatedException" >> {
		  
			registerNewUser("user14", webClient)

			val user14 = login("user14", webClient)
			
			val createConfigIn = Json.obj(
          "json" -> JsonNames.ADD_CONFIG
          , "params" -> Json.obj(
              "adminId" -> user14,
              "configUrl" -> "http://config/user13"
          )
      )
			
			val jsonConfigOut: JsValue = webClient.handleMessage(createConfigIn)
//			Logger.info("createConfigIn " + createConfigIn)
//      Logger.info("jsonConfigOut " + jsonConfigOut)
    
      (jsonConfigOut \ "result" \ "status" \ "addConfig" \ "status").asOpt[String].get === AddConfigAlreadyExist().status
	    (jsonConfigOut \ "result" \ "status" \ "common" \ "status").asOpt[String].get === ODBRecordDuplicated().status
			
		}
	}
  
}