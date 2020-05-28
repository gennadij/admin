package org.genericConfig.admin.models.component

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.component.{ComponentConfigPropertiesDTO, ComponentDTO, ComponentParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 18.06.2018
  */
class DeleteComponentSpecs extends Specification
                           with BeforeAfterAll
                           with CommonFunction {

  val wC: WebClient = WebClient.init
  var deleteCR : JsResult[ComponentDTO] = _

  def beforeAll() : Unit = {
    before()
  }

  def afterAll(): Unit = {}

  "Der Benutzer entfernt der Komponente aus der Konfiguration" >> {
    "Die Komponente im Abgang keinen Schritt hat S -> C" >> {
      "action = DELETE_COMPONENT" >> {deleteCR.get.action === Actions.DELETE_COMPONENT}
      "componentId = None" >> {deleteCR.get.result.get.configProperties.get.componentId === None}
      "stepId = None" >> { deleteCR.get.result.get.configProperties.get.stepId === None}
      "nameToShow = None" >> {deleteCR.get.result.get.userProperties.get.nameToShow === None}
      "errors = None" >> {deleteCR.get.result.get.errors === None}
    }
  }

  def before(): Unit = {
    val wC: WebClient = WebClient.init
    val username = "userDeleteComponent"
    val userId = createUser(username, wC)
    val configId = createConfig(userId, s"http://contig/$username")
    val nameToShow: Option[String] = Some(s"FirstStep$username")
    val kind : Option[String] = Some("first")
    val stepId = addStep(nameToShow, Some(configId), kind, 1, 1, wC)
    val componentId : Option[String] = createComponent(wC, stepId, Some("ComponentToDelete"))

    val deleteComponent: JsValue = Json.toJson(ComponentDTO(
      action = Actions.DELETE_COMPONENT,
      params = Some(ComponentParamsDTO(
        configProperties = Some(ComponentConfigPropertiesDTO(
          stepId = stepId,
          componentId = componentId
        ))
      ))
    ))

    Logger.info("DELETE_COMPONENT -> " + deleteComponent)
    val resultJsValue : JsValue = wC.handleMessage(deleteComponent)
    Logger.info("ADD_COMPONENT <- " + resultJsValue)
    deleteCR = Json.fromJson[ComponentDTO](resultJsValue)
  }
}
