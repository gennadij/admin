package org.genericConfig.admin.shared.configTree

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 02.06.2020
 */
case class ConfigTreeDTO (
                            action : String,
                            params : Option[ConfigTreeParamsDTO],
                            result : Option[ConfigTreeResultDTO]
                         )
