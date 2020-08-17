package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.dependency.DependencyDTO
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 19.05.2020
 */
trait ConverterJsonDTOForDependency extends ConverterJsonDTOForCommon{
  private[converter] def addConfig(receivedMessage: JsValue): JsValue = {
    Json.fromJson[DependencyDTO](receivedMessage) match {
      case addDependencyParams : JsSuccess[DependencyDTO] => Json.toJson(Dependency.addDependency(addDependencyParams.value))
      case e: JsError => jsonError(Actions.DELETE_DEPENDENCY, e)
    }
  }
  //  private def createDependency(receivedMessage: JsValue, admin: Admin): JsValue = {
  //    val dependencyIn: JsResult[JsonDependencyIn] = Json.fromJson[JsonDependencyIn](receivedMessage)
  //    dependencyIn match {
  //      case s: JsSuccess[JsonDependencyIn] => s.get
  //      case e: JsError => Logger.error("Errors -> CREATE_DEPENDENCY: " + JsError.toJson(e).toString())
  //    }
  //
  //    val dependencyOut: JsonDependencyOut  = admin.createDependency(dependencyIn.get)
  //    Json.toJson(dependencyOut)
  //   }
  //  private def visualProposalForAdditionalStepsInOneLevel(
  //      receivedMessage: JsValue, admin: Admin): JsValue = {
  //    val visualProposalForAdditionalStepsInOneLevelIn: JsResult[JsonVisualProposalForAdditionalStepsInOneLevelIn] =
  //      Json.fromJson[JsonVisualProposalForAdditionalStepsInOneLevelIn](receivedMessage)
  //    visualProposalForAdditionalStepsInOneLevelIn match {
  //      case s: JsSuccess[JsonVisualProposalForAdditionalStepsInOneLevelIn] => s.get
  //      case e: JsError =>
  //        Logger.error("Errors -> VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL: " +
  //            JsError.toJson(e).toString())
  //    }
  //
  //    val stepOut = admin.visualProposalForAdditionalStepsInOneLevel(
  //        visualProposalForAdditionalStepsInOneLevelIn.get)
  //    Json.toJson(stepOut)
  //  }
}
