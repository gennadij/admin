package org.dto.component

import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 * 
 * {jsonId : 8, dto : Component, params : {adminId : #40:0, kind : immutable}
 */
case class ComponentCS (
    dtoId: Int = DTOIds.CREATE_COMPONENT,
    dto: String = DTONames.CREATE_COMPONENT,
    params: ComponentParams
)

object ComponentCS {
    implicit val format = Json.reads[ComponentCS]
}