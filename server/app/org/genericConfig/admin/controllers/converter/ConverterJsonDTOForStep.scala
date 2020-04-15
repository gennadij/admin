package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.models.logic.Step
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.step.StepDTO
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 15.04.2020
 */
trait ConverterJsonDTOForStep extends WrapperCommon {
  private[converter] def addStep(receivedMessage: JsValue): JsValue = {
    Json.fromJson[StepDTO](receivedMessage) match {
      case addStepResult: JsSuccess[StepDTO] => Json.toJson[StepDTO](Step.addStep(addStepResult.value))
      case e: JsError => jsonError(Actions.ADD_STEP, e)
    }
  }
}
