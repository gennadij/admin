package org.genericConfig.admin.models.wrapper.configTree

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 20.09.2017
 */
case class Component (
    componentId: String,
    kind: String,
    nextStepId: Option[String],
    nextStep: Option[Step] // None wenn das Objekt schon exsetiert
)