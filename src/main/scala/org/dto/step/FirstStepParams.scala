package org.dto.step

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2016
 * 
 * params : {configId : #40:0, kind  : first}
 */


case class FirstStepParams (
    configId: String,
    kind: String,
    selectionCriterium: SelectionCriterium
)

object FirstStepParams {
  implicit val format = Json.reads[FirstStepParams]
}