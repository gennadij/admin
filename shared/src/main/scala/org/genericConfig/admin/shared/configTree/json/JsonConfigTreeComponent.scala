package org.genericConfig.admin.shared.configTree.json

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonConfigTreeComponent (
    componentId: String,
    kind: String,
    nextStepId: Option[String],
    nextStep: Option[JsonConfigTreeStep]
)

object JsonConfigTreeComponent {
   import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeStep.jsonConfigTreeStepWrites
   implicit lazy val jsonConfigTreeComponentwrites: Writes[JsonConfigTreeComponent] = (
    (JsPath \ "componentId").write[String] and
    (JsPath \ "kind").write[String] and
    (JsPath \ "nextStepId").write(Writes.optionWithNull[String]) and
    (JsPath \ "nextStep").lazyWrite(Writes.optionWithNull[JsonConfigTreeStep](jsonConfigTreeStepWrites))
  )(unlift(JsonConfigTreeComponent.unapply))
}