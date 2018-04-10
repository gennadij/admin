package org.genericConfig.admin.models.wrapper.connectionComponentToStep

import org.genericConfig.admin.models.wrapper.dependency.DependencyOut

case class ConnectionComponentToStepOut (
    status: String,
    message: String
//    TODO v016 
//    ,
//    visualProposalForAdditionalStepInOneLevel: Set[String],
//    dependenciesForAdditionalStepsInOneLevel: Set[DependencyOut]
)