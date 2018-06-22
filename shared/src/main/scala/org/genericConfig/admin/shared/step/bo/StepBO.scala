package org.genericConfig.admin.shared.step.bo

import org.genericConfig.admin.shared.step.status.StatusStep

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2018
 */
case class StepBO (
    json: Option[String] = None,
    appendToId: Option[String] = None,
    nameToShow: Option[String] = None,
    kind: Option[String] = None,
    selectionCriteriumMin: Option[Int] = None,
    selectionCriteriumMax: Option[Int] = None,
    stepId: Option[String] = None,
    status: Option[StatusStep] = None
)