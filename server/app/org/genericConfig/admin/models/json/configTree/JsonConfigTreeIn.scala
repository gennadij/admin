package org.genericConfig.admin.models.json.configTree

import play.api.libs.json.Json
import org.genericConfig.admin.shared.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016.
 * 
{
	jsonId : 6, 
	dto : ConfigTree, 
	params: 
		{
			configId : #10:0
		}
	}
}
 */
case class JsonConfigTreeIn (
    Json: String = JsonNames.CONFIG_TREE,
    params: JsonConfigTreeParams
)

object JsonConfigTreeIn {
  implicit val format = Json.reads[JsonConfigTreeIn]
}