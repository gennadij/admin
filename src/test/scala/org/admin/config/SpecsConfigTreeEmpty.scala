package org.admin.config

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.JsValue

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 29.01.2017
 */

@RunWith(classOf[JUnitRunner])
class SpecsConfigTreeEmpty extends Specification with AdminWeb with BeforeAfterAll {

  def afterAll(): Unit = {}
  
  def beforeAll(): Unit = {}
      
  "Diese Specification prueft die leere Konfiguration" >> {
    val loginClientServer = Json.obj(
        "dtoId" -> DTOIds.LOGIN,
        "dto" -> DTONames.LOGIN
        ,"params" -> Json.obj(
            "username" -> "user9",
            "password" -> "user9"
        )
    )
    val loginServerClient = handelMessage(loginClientServer)
    val configId = ((loginServerClient \ "result" \ "configs")(0) \ "configId").asOpt[String].get
    "Login" >> {
      (loginServerClient \ "result" \ "status").asOpt[Boolean].get === true
    }
    "Leere ConfigTree" >> {
      val configTreeClientServer = Json.obj(
          "dtoId" -> DTOIds.CONFIG_TREE,
          "dto" -> DTONames.CONFIG_TREE
          ,"params" -> Json.obj(
              "configId" -> configId
          )
      )
      val configTreeServerClient = handelMessage(configTreeClientServer)
      "configTree \\ dtoId" >> {
        (configTreeServerClient \ "jsonId").asOpt[Int].get === DTOIds.CONFIG_TREE
      }
      "configTree \\ dto" >> {
        (configTreeServerClient \ "dto").asOpt[String].get === DTONames.CONFIG_TREE
      }
      "configTree \\ result \\ steps" >> {
        (configTreeServerClient \ "result" \ "steps").asOpt[List[JsValue]].get === List.empty
      }
    }
  }
}
