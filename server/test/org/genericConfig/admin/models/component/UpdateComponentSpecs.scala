package org.genericConfig.admin.models.component

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.logic.RidToHash
import org.genericConfig.admin.models.persistence.orientdb.PropertyKeys
import org.genericConfig.admin.models.{CommonFunction, common}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.component.{ComponentConfigPropertiesDTO, ComponentDTO, ComponentParamsDTO, ComponentUserPropertiesDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}


/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 18.06.2018
  */
class UpdateComponentSpecs extends Specification
                           with BeforeAfterAll
                           with CommonFunction{

  val wC: WebClient = WebClient.init
  var updateCROnlyNameToShow : JsResult[ComponentDTO] = _

  def beforeAll() : Unit = {
    before()
  }

  def afterAll(): Unit = {
    val error : Option[common.Error] = deleteVertex(RidToHash.getRId(
      updateCROnlyNameToShow.get.result.get.configProperties.get.componentId.get).get,
      PropertyKeys.VERTEX_COMPONENT
    )

    require(error == None, "Beim Loeschen des Components ein Fehler aufgetretten")
  }

  "Der Benutzer veraendert die Komponente" >> {
    "Es wird nur der Name geaendert" >> {
      "action = ADD_COMPONENT" >> {updateCROnlyNameToShow.get.action === Actions.UPDATE_COMPONENT}
      "componentId < 32 && > 10" >> {updateCROnlyNameToShow.get.result.get.configProperties.get.componentId.get.length must (be_<=(32) and be_>(10))}
      "stepId = None" >> { updateCROnlyNameToShow.get.result.get.configProperties.get.stepId === None}
      "nameToShow = ComponentUpdated" >> {updateCROnlyNameToShow.get.result.get.userProperties.get.nameToShow.get === "ComponentUpdated"}
      "errors = None" >> {updateCROnlyNameToShow.get.result.get.errors === None}
    }
  }

  def before(): Unit = {

    val wC: WebClient = WebClient.init
    val username = "userUpdateComponent"
    val userId = createUser(username, wC)
    val configId = createConfig(userId, s"http://contig/$username")
    val nameToShow: Option[String] = Some(s"FirstStep$username")
    val kind : Option[String] = Some("first")
    val stepId = addStep(nameToShow, Some(configId), kind, 1, 1, wC)
    val componentId : Option[String] = createComponent(wC, stepId, Some("ComponentToUpdate"))

    val updateComponent: JsValue = Json.toJson(ComponentDTO(
      action = Actions.UPDATE_COMPONENT,
      params = Some(ComponentParamsDTO(
        configProperties = Some(ComponentConfigPropertiesDTO(
          stepId = stepId,
          componentId = componentId
        )),
        userProperties = Some(ComponentUserPropertiesDTO(
          nameToShow = Some("ComponentUpdated")
        ))
      ))
    ))

    Logger.info("UPDATE_COMPONENT -> " + updateComponent)
    val resultJsValue : JsValue = wC.handleMessage(updateComponent)
    Logger.info("UPDATE_COMPONENT <- " + resultJsValue)
    updateCROnlyNameToShow = Json.fromJson[ComponentDTO](resultJsValue)
  }

}
