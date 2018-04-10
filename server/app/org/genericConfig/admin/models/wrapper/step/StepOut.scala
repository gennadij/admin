package org.genericConfig.admin.models.wrapper.step

import org.genericConfig.admin.models.wrapper.dependency.DependencyOut

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.09.2017
 */
case class StepOut (
    stepId: String,
    status: String,
    message: String,
    visualProposalForAdditionalStepInOneLevel: Set[String],
    dependenciesForAdditionalStepsInOneLevel: Set[DependencyOut]
    
)