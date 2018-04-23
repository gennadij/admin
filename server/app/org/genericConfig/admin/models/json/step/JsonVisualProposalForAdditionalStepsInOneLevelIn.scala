package org.genericConfig.admin.models.json.step

import play.api.libs.json.Json
import org.genericConfig.admin.shared.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 17.08.2017
 */
case class JsonVisualProposalForAdditionalStepsInOneLevelIn (
    json: String = JsonNames.VISUAL_PROPOSAL_FOR_ADDITIONAL_STEPS_IN_ON_LEVEL,
    params: JsonVisualProposal
)

object JsonVisualProposalForAdditionalStepsInOneLevelIn {
  implicit val format = Json.reads[JsonVisualProposalForAdditionalStepsInOneLevelIn]
}