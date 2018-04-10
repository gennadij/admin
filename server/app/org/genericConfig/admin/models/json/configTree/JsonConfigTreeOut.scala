package org.genericConfig.admin.models.json.configTree

import play.api.libs.json.Json
import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 * 
{
	jsonId : 6, 
	dto : ConfigTree, 
	result: {
		steps : [
			{
				stepId :#19:1, 
				kind: first, 
				components: [
					{
						componentId : #21:0, 
						kind : immutable
						nextStep: #11:11
					}, ...
				]
			}, ...
		]
	}
}
 */


case class JsonConfigTreeOut (
    dtoId: Int = DTOIds.CONFIG_TREE,
    dto: String = DTONames.CONFIG_TREE,
    result: JsonConfigTreeResult
)

object JsonConfigTreeOut {
  implicit val format = Json.writes[JsonConfigTreeOut]
}

