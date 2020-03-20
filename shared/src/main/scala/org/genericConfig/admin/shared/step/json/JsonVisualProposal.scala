package org.genericConfig.admin.shared.step.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 17.08.2017
 */
case class JsonVisualProposal (
    selectedVisualProposal : String
)

object JsonVisualProposal {
  implicit val format = Json.reads[JsonVisualProposal]
}