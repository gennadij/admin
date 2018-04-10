package org.genericConfig.admin.shared.bo

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 10.04.2018
 */
case class ComponentBO (
    componentId: String = "",
    nameToShow: String = "",
    kind: String = "",
    stepId: String = "",
    status: String = "",
    message: String = ""
)