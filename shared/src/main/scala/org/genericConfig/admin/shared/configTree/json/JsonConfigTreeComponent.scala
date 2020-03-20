package org.genericConfig.admin.shared.configTree.json

import play.api.libs.json._
import play.api.libs.functional.syntax.{unlift, _}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 19.12.2016
  */

case class JsonConfigTreeComponent(
                                    componentId: String,
                                    nameToShow: String,
                                    kind: String,
                                    nextStepId: Option[String]
                                  )

object JsonConfigTreeComponent {
  implicit lazy val jsonConfigTreeComponentWrites: Writes[JsonConfigTreeComponent] = (
    (JsPath \ "componentId").write[String] and
      (JsPath \ "nameToShow").write[String] and
      (JsPath \ "kind").write[String] and
      (JsPath \ "nextStepId").write(Writes.optionWithNull[String])
    ) (unlift(JsonConfigTreeComponent.unapply))

  implicit lazy val jsonConfigTreeComponentReads: Reads[JsonConfigTreeComponent] = (
      (JsPath \ "componentId").read[String] and
      (JsPath \ "nameToShow").read[String] and
      (JsPath \ "kind").read[String] and
      (JsPath \ "nextStepId").read(Reads.optionWithNull[String])
    ) (JsonConfigTreeComponent.apply _)
}