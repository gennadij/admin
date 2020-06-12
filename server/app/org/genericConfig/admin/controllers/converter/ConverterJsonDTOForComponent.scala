package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.models.logic.Component
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.component.ComponentDTO
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 19.05.2020
 */
trait ConverterJsonDTOForComponent extends ConverterJsonDTOForCommon{

    private[converter] def addComponent(receivedMessage: JsValue): JsValue = {
      Json.fromJson[ComponentDTO](receivedMessage) match {
        case componentIn : JsSuccess[ComponentDTO] => Json.toJson(Component.addComponent(componentIn.get))
        case e : JsError => jsonError(Actions.ADD_COMPONENT, e)
      }
    }

    private[converter] def deleteComponent(receivedMessage: JsValue): JsValue = {
      Json.fromJson[ComponentDTO](receivedMessage) match {
        case s: JsSuccess[ComponentDTO] => Json.toJson(Component.deleteComponent(s.get))
        case e: JsError => jsonError(Actions.DELETE_COMPONENT, e)
      }
    }

    private[converter] def updateComponent(receivedMessage: JsValue): JsValue = {
      Json.fromJson[ComponentDTO](receivedMessage) match {
        case s: JsSuccess[ComponentDTO] => Json.toJson(Component.updateComponent(s.get))
        case e: JsError => jsonError(Actions.UPDATE_COMPONENT, e)
      }
    }
    private[converter] def connectComponentToStep(receivedMessage: JsValue): JsValue = {
      Json.fromJson[ComponentDTO](receivedMessage) match {
        case s : JsSuccess[ComponentDTO] => Json.toJson(Component.connectComponentToStep(s.get))
        case e : JsError => jsonError(Actions.CONNECT_COMPONENT_TO_STEP, e)
      }
    }
  //
  ////  private def connectComponentToStep(receivedMessage: JsValue, admin: Admin): JsValue = {
  ////    val connectionComponentToStepIn: JsResult[JsonConnectionComponentToStepIn] =
  ////      Json.fromJson[JsonConnectionComponentToStepIn](receivedMessage)
  ////    connectionComponentToStepIn match {
  ////      case s : JsSuccess[JsonConnectionComponentToStepIn] => s.get
  ////      case e : JsError => Logger.error("Errors -> CONNECTION_COMPONENT_TO_STEP: " + JsError.toJson(e).toString())
  ////    }
  ////    val connectionComponentToStepOut: JsonConnectionComponentToStepOut =
  ////      admin.connectComponentToStep(connectionComponentToStepIn.get)
  ////    Json.toJson(connectionComponentToStepOut)
  ////  }


  //

}
