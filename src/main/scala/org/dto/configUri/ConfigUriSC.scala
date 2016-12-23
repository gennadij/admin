/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.configUri

import org.dto.DTOIds
import org.dto.DTONames

/**
 * {jsond : 3, dto : ConfigUri, params : {status : true, message : Nachricht}
 */
case class ConfigUriSC (
    jsonId: Int = DTOIds.configUri,
    dto: String = DTONames.configUri
)