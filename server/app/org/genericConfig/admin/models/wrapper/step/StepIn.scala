package org.genericConfig.admin.models.wrapper.step

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.09.2017
 */
case class StepIn (
    componentId: String,
    nameToShow: String,
    kind: String,
    selectionCriteriumMin: Int,
    selectionCriteriumMax: Int
)