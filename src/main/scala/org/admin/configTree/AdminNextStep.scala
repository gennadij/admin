package org.admin.configTree

case class AdminNextStep (
    status: Boolean,
    id:String,
    stepId: String,
    adminId: String,
    //first, default, final
    kind: String,
//    selectionCriterium: SelectionCriterium,
    componentId: String
)