package org.genericConfig.admin.models.component

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.models.persistence.orientdb.PropertyKeys
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.component.{ComponentConfigPropertiesDTO, ComponentDTO, ComponentParamsDTO, ComponentUserPropertiesDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 16.01.2017
 *
 * Username = user5
 */

class AddComponentSpecs extends Specification
                                        with BeforeAfterAll
                                        with CommonFunction{
  var addCR : JsResult[ComponentDTO] = _
  def beforeAll() : Unit = {
    before()
  }

  def afterAll(): Unit = {
    deleteVertex(RidToHash.getRId(
      addCR.get.result.get.configProperties.get.componentId.get).get,
      PropertyKeys.VERTEX_COMPONENT
    )
  }

  "Der User fuegt zu dem Schritt eine neue Komponente" >> {
    "Erste Komponente zu dem erstem Schritt wird hinzugefuegt" >> {
      "action = ADD_COMPONENT" >> {addCR.get.action === Actions.ADD_COMPONENT}
      "componentId < 32 && > 10" >> {addCR.get.result.get.configProperties.get.componentId.get.length must (be_<=(32) and be_>(10))}
      "stepId < 32 && > 10" >> { addCR.get.result.get.configProperties.get.stepId.get.length must (be_<=(32) and be_>(10))}
      "nameToShow = Component_1_1" >> {addCR.get.result.get.userProperties.get.nameToShow.get === "Component_1_1"}
      "errors = None" >> {addCR.get.result.get.errors === None}
    }
  }

  def before(): Unit = {
    val wC: WebClient = WebClient.init
    val username = "userAddStep"
    val userId = createUser(username, wC)
    val configId = createConfig(userId, s"http://contig/$username")
    val nameToShow: Option[String] = Some(s"FirstStep$username")
    val kind : Option[String] = Some("first")
    val stepId = addStep(nameToShow, Some(configId), kind, 1, 1, wC)

    val addComponent: JsValue = Json.toJson(ComponentDTO(
      action = Actions.ADD_COMPONENT,
      params = Some(ComponentParamsDTO(
        configProperties = Some(ComponentConfigPropertiesDTO(
          stepId = stepId
        )),
        userProperties = Some(ComponentUserPropertiesDTO(
          nameToShow = Some("Component_1_1")
        ))
      ))
    ))

    Logger.info("ADD_COMPONENT -> " + addComponent)
    val resultJsValue : JsValue = wC.handleMessage(addComponent)
    Logger.info("ADD_COMPONENT <- " + resultJsValue)
    addCR = Json.fromJson[ComponentDTO](resultJsValue)
  }
}