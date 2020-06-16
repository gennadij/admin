package org.genericConfig.admin.models.component

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.models.persistence.orientdb.PropertyKeys
import org.genericConfig.admin.models.{CommonFunction, common}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.component.{ComponentConfigPropertiesDTO, ComponentDTO, ComponentParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}


/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 12.06.2020
  */
class ConnectComponentToStep extends Specification
  with BeforeAfterAll
  with CommonFunction {

  val wC: WebClient = WebClient.init
  var rConnectComponentToStep: JsResult[ComponentDTO] = _
  var stepId_S1 : Option[String] = _
  var stepId_S2 : Option[String] = _
  var componentId_C1 : Option[String] = _
  var componentId_C2: Option[String] = _

  def beforeAll(): Unit = {
    before()
  }

  def afterAll(): Unit = {
    val errorComponent_C1: Option[common.Error] = deleteVertex(RidToHash.getRId(componentId_C1.get).get, PropertyKeys.VERTEX_COMPONENT)
    require(errorComponent_C1 == None, "Beim Loeschen des Components ein Fehler aufgetretten")

    val errorComponent_C2: Option[common.Error] = deleteVertex(RidToHash.getRId(componentId_C2.get).get, PropertyKeys.VERTEX_COMPONENT)
    require(errorComponent_C2 == None, "Beim Loeschen des Components ein Fehler aufgetretten")

    val errorStep_S1: Option[common.Error] = deleteVertex(RidToHash.getRId(stepId_S1.get).get, PropertyKeys.VERTEX_STEP)
    require(errorStep_S1 == None, "Beim Loeschen des Components ein Fehler aufgetretten" + errorStep_S1.get.name)

    val errorStep_S2: Option[common.Error] = deleteVertex(RidToHash.getRId(stepId_S2.get).get, PropertyKeys.VERTEX_STEP)
    require(errorStep_S2 == None, "Beim Loeschen des Components ein Fehler aufgetretten")
  }

  "Der Benutzer veraendert die Komponente" >> {
    "Es wird nur der Name geaendert" >> {
      "action = ADD_COMPONENT" >> {
        rConnectComponentToStep.get.action === Actions.CONNECT_COMPONENT_TO_STEP
      }
      "componentId < 32 && > 10" >> {
        rConnectComponentToStep.get.result.get.configProperties.get.componentId.get.length must (be_<=(32) and be_>(10))
      }
      "stepId = None" >> {
        rConnectComponentToStep.get.result.get.configProperties.get.stepId.get.length must (be_<=(32) and be_>(10))
      }
      "nameToShow = ComponentUpdated" >> {
        rConnectComponentToStep.get.result.get.userProperties.get.nameToShow === None
      }
      "errors = None" >> {
        rConnectComponentToStep.get.result.get.errors === None
      }
    }
  }

  def before(): Unit = {

    val wC: WebClient = WebClient.init
    val username = "connectComponentToStep"
    val userId = createUser(username, wC)
    val configId = createConfig(userId, s"http://contig/$username")
    stepId_S1 = addStep(nameToShow = Some(s"S1_$username"), outId = Some(configId), min = 1, max = 1, wC = wC)
    componentId_C1 = createComponent(wC, stepId_S1, Some(s"C1_$username"))
    componentId_C2 = createComponent(wC, stepId_S1, Some(s"C2_$username"))
    stepId_S2 = addStep(nameToShow = Some(s"S2_$username"), outId = componentId_C1, min = 1, max = 1, wC = wC)

    val connectComponent: JsValue = Json.toJson(ComponentDTO(
      action = Actions.CONNECT_COMPONENT_TO_STEP,
      params = Some(ComponentParamsDTO(
        configProperties = Some(ComponentConfigPropertiesDTO(
          stepId = stepId_S2,
          componentId = componentId_C2
        ))
      ))
    ))

    Logger.info("CONNECT_COMPONENT_TO_STEP -> " + connectComponent)
    val resultJsValue: JsValue = wC.handleMessage(connectComponent)
    Logger.info("CONNECT_COMPONENT_TO_STEP <- " + resultJsValue)
    rConnectComponentToStep = Json.fromJson[ComponentDTO](resultJsValue)
  }
}