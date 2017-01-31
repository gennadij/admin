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
class SpecsConfigTreeFirstStep3Components extends Specification with AdminWeb with BeforeAfterAll{
  
  
  def beforeAll() = {}
  
  def afterAll() = {}
  
  // ConfigId #41:6
  "Specification spezifiziert die Erzeugung von der ConfigTree" >> {
    "Login" >> {
      val loginCS = Json.obj(
          "dtoId" -> DTOIds.LOGIN,
          "dto" -> DTONames.LOGIN
          ,"params" -> Json.obj(
              "username" -> "user7",
              "password"-> "user7"
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
      
      "ConfigTree" >> {
        val configTreeCS = Json.obj(
            "dtoId" -> DTOIds.CONFIG_TREE,
            "dto" -> DTONames.CONFIG_TREE
            ,"params" -> Json.obj(
                "configId" -> configId
            )
        )
        val configTreeSC = handelMessage(configTreeCS)
        println(s"ConfigTree   Client -> Server: $configTreeCS")
        println(s"ConfigTree   Server -> Client: $configTreeSC")
        "configTree \\ dtoId" >> {
          (configTreeSC \ "dtoId").asOpt[Int].get === DTOIds.CONFIG_TREE
        }
        "configTree \\ dto" >> {
          (configTreeSC \ "dto").asOpt[String].get === DTONames.CONFIG_TREE
        }
        "configTree \\ result \\ steps.size" >> {
//          (((configTreeSC_2 \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 1
          (configTreeSC \ "result" \ "steps").asOpt[Set[JsValue]].get.size === 1
        }
        "configTree \\ result \\ steps(0)" >> {
//          "stepId" >> {
//            (((configTreeSC \ "result" \ "steps")(0)) \ "stepId").asOpt[String].get === "#25:51"
//          }
          "kind" >> {
            (((configTreeSC \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
          }
          "components.size" >> {
            (((configTreeSC \ "result" \ "steps")(0)) \ "components").asOpt[Set[JsValue]].get.size === 3
          }
          "components(0)" >> {
//            "componentId" >> {
//              (((((configTreeSC \ "result" \ "steps")(0)) \ "components")(0)) \ "componentId").asOpt[String].get === "#29:39"
//            }
            "kind" >> {
              (((((configTreeSC \ "result" \ "steps")(0)) \ "components")(0)) \ "kind").asOpt[String].get === "immutable"
            }
            "nextStep" >> {
              (((((configTreeSC \ "result" \ "steps")(0)) \ "components")(0)) \ "nextStep").asOpt[String].get === "no nextStep"
            }
            "components(1)" >> {
//              "componentId" >> {
//                (((((configTreeSC \ "result" \ "steps")(0)) \ "components")(1)) \ "componentId").asOpt[String].get === "#30:33"
//              }
              "kind" >> {
                (((((configTreeSC \ "result" \ "steps")(0)) \ "components")(1)) \ "kind").asOpt[String].get === "immutable"
              }
              "nextStep" >> {
                (((((configTreeSC \ "result" \ "steps")(0)) \ "components")(1)) \ "nextStep").asOpt[String].get === "no nextStep"
              }
            }
            "components(2)" >> {
//              "componentId" >> {
//                (((((configTreeSC \ "result" \ "steps")(0)) \ "components")(2)) \ "componentId").asOpt[String].get === "#31:31"
//              }
              "kind" >> {
                (((((configTreeSC \ "result" \ "steps")(0)) \ "components")(2)) \ "kind").asOpt[String].get === "immutable"
              }
              "nextStep" >> {
                (((((configTreeSC \ "result" \ "steps")(0)) \ "components")(2)) \ "nextStep").asOpt[String].get === "no nextStep"
              }
            }
          }
        }
        "configTree \\ result \\ message" >> {
          (configTreeSC \ "result" \ "message").asOpt[String].get === ""
        }
      }
    }
  }
}