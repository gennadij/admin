package org.genericConfig.admin.shared.configTree.json

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 19.12.2016
  */

case class JsonConfigTreeStep(
                               stepId: String,
                               nameToShow: String,
                               kind: String,
                               nextSteps: Set[JsonConfigTreeStep],
                               components: Set[JsonConfigTreeComponent]
                             )

object JsonConfigTreeStep {

  import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeComponent.jsonConfigTreeComponentReads
  import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeComponent.jsonConfigTreeComponentWrites

  implicit lazy val jsonConfigTreeStepWrites: Writes[JsonConfigTreeStep] = (
    (JsPath \ "stepId").write[String] and
    (JsPath \ "nameToShow").write[String] and
    (JsPath \ "kind").write[String] and
    (JsPath \ "nextSteps").lazyWrite(Writes.set[JsonConfigTreeStep](jsonConfigTreeStepWrites)) and
    (JsPath \ "components").lazyWrite(Writes.set[JsonConfigTreeComponent](jsonConfigTreeComponentWrites))
  ) (unlift(JsonConfigTreeStep.unapply))

  implicit lazy val jsonConfigTreeStepReads: Reads[JsonConfigTreeStep] = (
    (JsPath \ "stepId").read[String] and
    (JsPath \ "nameToShow").read[String] and
    (JsPath \ "kind").read[String] and
    (JsPath \ "nextSteps").lazyRead(Reads.set[JsonConfigTreeStep](jsonConfigTreeStepReads)) and
    (JsPath \ "components").lazyRead(Reads.set[JsonConfigTreeComponent](jsonConfigTreeComponentReads))
  ) (JsonConfigTreeStep.apply _)
}