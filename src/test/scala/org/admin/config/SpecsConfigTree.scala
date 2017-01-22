package org.admin.config

import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.JsValue

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 16.01.2017
 */

@RunWith(classOf[JUnitRunner])
class SpecsConfigTree extends Specification with AdminWeb with BeforeAfterAll{
  
  
  def beforeAll() = {}
  
  def afterAll() = {}
  
  // ConfigId #41:6
  "Specification spezifiziert die Erzeugung von der ConfigTree" >> {
    "Login" >> {
      val loginCS = Json.obj(
          "dtoId" -> DTOIds.LOGIN,
          "dto" -> DTONames.LOGIN
          ,"params" -> Json.obj(
              "username" -> "user4",
              "password"-> "user4"
          )
      )
      val loginSC: JsValue = handelMessage(loginCS)
      val configId = ((loginSC \ "result" \ "configs")(0) \ "configId").asOpt[String].get
      "Login -> Status" >> {
        (loginSC \ "result" \ "status").asOpt[Boolean].get === true
      }
      "Login -> configs.size" >> {
        (loginSC \ "result" \ "configs").asOpt[List[JsValue]].get.size === 1
      }
      "Login -> config(0) -> configId" >> {
        configId === "#41:6"
      }
      "ConfigTree" >> {
        val configTreeCS = Json.obj(
            "jsonId" -> DTOIds.CONFIG_TREE,
            "dto" -> DTONames.CONFIG_TREE
            ,"params" -> Json.obj(
                "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get
            )
        )
        val configTreeSC = handelMessage(configTreeCS)
        println(s"ConfigTree   Client -> Server: $configTreeCS")
        println(s"ConfigTree   Server -> Client: $configTreeSC")
        "" === ""
        
      }
    }
    
    
  }
}