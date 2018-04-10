package org.genericConfig.admin.models.json.configTree

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 * 
 * {jsonId : 6, dto : ConfigTree, result: {message : Nachricht, steps : 
 *         [ {stepId :#19:1, kind: first, components:
 *         [{componentId : #21:0, kind : immutable}]}]}}
 */

case class JsonConfigTreeResult (
    step: Option[JsonConfigTreeStep],
    status: String,
    message: String
)

object JsonConfigTreeResult {
  import org.genericConfig.admin.models.json.configTree.JsonConfigTreeStep.jsonConfigTreeStepWrites
  
  implicit val writes: Writes[JsonConfigTreeResult] = (
    (JsPath \ "step").write(Writes.optionWithNull[JsonConfigTreeStep]) and
    (JsPath \ "status").write[String] and
    (JsPath \ "message").write[String]
  )(unlift(JsonConfigTreeResult.unapply))
}