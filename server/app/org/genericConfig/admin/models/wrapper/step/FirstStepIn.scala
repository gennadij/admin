package org.genericConfig.admin.models.wrapper.step

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.09.2017
 */
case class FirstStepIn (
    configId: String,
    nameToShow: String,
    kind: String,
    selectionCriteriumMin: Int,
    selectionCriteriumMax: Int
    
)