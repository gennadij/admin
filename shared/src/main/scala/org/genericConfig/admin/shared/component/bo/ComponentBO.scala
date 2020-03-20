package org.genericConfig.admin.shared.component.bo

import org.genericConfig.admin.shared.component.status.StatusComponent

case class ComponentBO(
    json: Option[String] = None,
    stepId: Option[String] = None,
    componentId: Option[String] = None,
    nameToShow: Option[String] = None,
    kind: Option[String] = None,
    status: Option[StatusComponent] = None
)