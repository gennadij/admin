package org.genericConfig.admin.models.wrapper.dependency

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.09.2017
 */
case class DependencyOut (
    componentFromId: String,
    componentToId: String,
    dependencyId: String,
    dependencyType: String,
    visualization: String,
    nameToShow: String,
    status: String,
    message: String
)