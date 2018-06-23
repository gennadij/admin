package org.genericConfig.admin.shared.configTree.json

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 19.12.2016
  */

case class JsonConfigTreeComponent(
                                    componentId: String,
                                    nameTOShow: String,
                                    kind: String,
                                    nextStep: Option[JsonConfigTreeStep]
                                  )

object JsonConfigTreeComponent {

  import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeStep.jsonConfigTreeStepWrites
  import org.genericConfig.admin.shared.configTree.json.JsonConfigTreeStep.jsonConfigTreeStepReads

  implicit lazy val jsonConfigTreeComponentWrites: Writes[JsonConfigTreeComponent] = (
    (JsPath \ "componentId").write[String] and
      (JsPath \ "nameToShow").write[String] and
      (JsPath \ "kind").write[String] and
      (JsPath \ "nextStep").lazyWrite(Writes.optionWithNull[JsonConfigTreeStep](jsonConfigTreeStepWrites))
    ) (unlift(JsonConfigTreeComponent.unapply))

  implicit lazy val jsonConfigTreeComponentReads: Reads[JsonConfigTreeComponent] = (
      (JsPath \ "componentId").read[String] and
      (JsPath \ "nameToShow").read[String] and
      (JsPath \ "kind").read[String] and
      (JsPath \ "nextStep").lazyRead(Reads.optionWithNull[JsonConfigTreeStep](jsonConfigTreeStepReads))
    ) (JsonConfigTreeComponent.apply _)
}