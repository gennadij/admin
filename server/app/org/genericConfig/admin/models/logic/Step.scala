package org.genericConfig.admin.models.logic

import org.genericConfig.admin.models.common.{AddStepError, AppendToError, Error, IdHashNotExistError, StepAlreadyExistError}
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, GraphStep, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.step.{StepDTO, StepResultDTO}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 14.05.2018
  */
object Step {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepDTO : StepDTO
    * @return StepDTO
    */
  def addStep(stepDTO: StepDTO): StepDTO = {
    new Step().addStep(stepDTO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  def deleteFirstStep(stepBO: AnyRef): Unit = {
//    new Step().deleteStep(stepBO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  def updateStep(stepBO: AnyRef): Unit = {
//    new Step().updateStep(stepBO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepBO : StepBO
    * @return StepBO
    */
  def connectComponentToStep(stepBO: AnyRef): Unit = {
//    new Step().connectComponentToStep(stepBO)
  }

}

class Step {
  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepDTO : StepDTO
    * @return StepDTO
    */
  private def addStep(stepDTO: StepDTO): StepDTO = {

    RidToHash.getRId(stepDTO.params.get.outId.get) match {
      case Some(outRid) =>
        GraphStep.isStepAlone(outRid) match {
          case None => (GraphStep.addStep(stepDTO) : @unchecked) match {
            case (Some(vStep), None) => GraphCommon.appendTo(
              outRid, vStep.getIdentity().toString(), PropertyKeys.EDGE_HAS_STEP
            ) match {
              case None => createStepDTO(
                Actions.ADD_STEP,
                Some(RidToHash.setIdAndHash(vStep.getIdentity.toString)_2),
                None
              )
              case Some(appendToError) =>
                createStepDTO(Actions.ADD_STEP, None, Some(List(AppendToError(), appendToError)))
            }
            case (None, Some(addStepError)) =>
              createStepDTO(Actions.ADD_STEP, None, Some(List(AddStepError(), addStepError)))
          }
          case Some(stepNotAloneError) =>
            createStepDTO(Actions.ADD_STEP, None, Some(List(StepAlreadyExistError(), stepNotAloneError)))
        }
      case None => createStepDTO(Actions.ADD_STEP, None, Some(List(IdHashNotExistError())))
    }
  }

  private def createStepDTO(action : String, stepId : Option[String], errors : Option[List[Error]]) = {

    val e =  errors match {
      case None => None
      case Some(e) => Some(
        e.map(error => {
          ErrorDTO(
            name = error.name,
            message = error.message,
            code = error.code
          )
        })
      )
    }

    StepDTO(
      action = action,
      result = Some(StepResultDTO(
        stepId = stepId,
        errors = e
      ))
    )
  }
//        val stepBOOut: StepBO =
//          Persistence.addStep(stepBO.copy(appendToId= Some(outRid)))
//        stepBOOut.status.get.addStep match {
//          case Some(AddStepSuccess()) =>
//            val (appendStepStatus: StatusAppendStep, _) = Persistence.appendStepTo(outRid, stepBOOut.stepId.get)
//            appendStepStatus match {
//              case AppendStepSuccess() =>
//                val s = stepBOOut.copy(
//                  json = Some(JsonNames.ADD_STEP),
//                  appendToId = RidToHash.getHash(outRid),
//                  stepId = Some(RidToHash.setIdAndHash(stepBOOut.stepId.get)._2),
//                  status = Some(StatusStep(
//                    addStep = Some(AddStepSuccess()),
//                    appendStep = Some(AppendStepSuccess()),
//                    common = stepBOOut.status.get.common)))
//                s
//              case AppendStepError() =>
//                val (statusDeleteStep: StatusDeleteStep, _) = Persistence.deleteStep(stepBOOut.stepId.get)
//
//                stepBOOut.copy(json = Some(JsonNames.ADD_STEP),
//                  status = Some(StatusStep(
//                    addStep = Some(AddStepDefectComponentOrConfigId()),
//                    deleteStep = Some(statusDeleteStep),
//                    appendStep = Some(AppendStepError()),
//                    common = stepBOOut.status.get.common)))
//            }
//          case _ => stepBOOut.copy(json = Some(JsonNames.ADD_STEP))
//        }
//      case None =>
//        StepDTO(
//          action = Actions.ADD_STEP,
//          result = Some(StepResultDTO(
//            errors = Some(List(ErrorDTO(
//              name = ODBRecordIdDefect().name,
//              message = ODBRecordIdDefect().message,
//              code = ODBRecordIdDefect().code)))
//          ))
//        )
//  }

//  /**
//   * @author Gennadi Heimann
//   * @version 0.1.6
//   * @param stepBO : StepBO
//   * @return StepBO
//   */
//  def addStep(stepBO: StepBO): StepBO = {
//    ???
//    val (vStep: Option[OrientVertex], addStepStatus: StatusAddStep, commonStatus: Error) =
//      Graph.addStep(stepBO)
    //
    //    addStepStatus match {
    //      case AddStepSuccess() =>
    //        StepBO(
    //          appendToId = stepBO.appendToId,
    //          stepId = Some(vStep.get.getIdentity.toString),
    //          status = Some(StatusStep(
    //            addStep = Some(AddStepSuccess()),
    //            common = Some(Success())
    //          ))
    //        )
    //      case AddStepError() =>
    //        StepBO(
    //          stepId = None,
    //          status = Some(StatusStep(
    //            addStep = Some(AddStepError()),
    //            common = Some(commonStatus)
    //          ))
    //        )
    //      case AddStepAlreadyExist() =>
    //        StepBO(
    //          stepId = None,
    //          status = Some(StatusStep(
    //            addStep = Some(AddStepAlreadyExist()),
    //            common = Some(commonStatus)
    //          ))
    //        )
    //      case AddStepDefectComponentOrConfigId() =>
    //        StepBO(
    //          stepId = None,
    //          status = Some(StatusStep(
    //            addStep = Some(AddStepDefectComponentOrConfigId()),
    //            common = Some(commonStatus)
    //          ))
    //        )
    //    }
//  }

//  /**
//   * @author Gennadi Heimann
//   * @version 0.1.6
//   * @param id : String, stepId: String
//   * @return (StatusAppendStep, Status)
//   */
//  def appendStepTo(id: String, stepId: String): (StatusAppendStep, ErrorDTO) = {
//    ???
//    //    Graph.appendStepTo(id, stepId)
//  }
















//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param stepBO : StepBO
//    * @return StepBO
//    */
//  private def deleteStep(stepBO: StepBO): StepBO = {
//    val stepBOIn = stepBO.copy(stepId = RidToHash.getRId(stepBO.stepId.get))
//
//    val (deleteStepStatus: StatusDeleteStep, commonStatus: Error) = Persistence.deleteStep(stepBOIn.stepId.get)
//
//    deleteStepStatus match {
//      case DeleteStepSuccess() =>
//        StepBO(
//          json = Some(JsonNames.DELETE_STEP),
//          status = Some(StatusStep(deleteStep = Some(DeleteStepSuccess()), common = Some(Success())))
//        )
//      case DeleteStepError() =>
//        StepBO(
//          json = Some(JsonNames.DELETE_STEP),
//          status = Some(StatusStep(deleteStep = Some(DeleteStepError()), common = Some(commonStatus)))
//        )
//      case DeleteStepDefectID() =>
//        StepBO(
//          json = Some(JsonNames.DELETE_STEP),
//          status = Some(StatusStep(deleteStep = Some(DeleteStepDefectID()), common = Some(commonStatus)))
//        )
//    }
//  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param stepBO : StepBO
//    * @return StepBO
//    */
//  private def updateStep(stepBO: StepBO): StepBO = {
//    val stepRId = RidToHash.getRId(stepBO.stepId.get)
//
//    Persistence.updateStep(stepBO.copy(stepId = stepRId))
//  }

//  /**
//    * @author Gennadi Heimann
//    *
//    * @version 0.1.0
//    *
//    * @param stepBO: StepBO
//    *
//    * @return ComponentBO
//    */
//  def connectComponentToStep(stepBO: StepBO): StepBO = {
//  }
//    val componentRid = RidToHash.getRId(stepBO.appendToId.get)
//    val stepRid = RidToHash.getRId(stepBO.stepId.get)
//
//    val (statusAppendStep, statusCommon): (StatusAppendStep, Error) =
//      Persistence.appendStepTo(id = componentRid.get, stepId = stepRid.get)
//
//    statusAppendStep match {
//      case AppendStepSuccess() => StepBO(
//        json = Some(JsonNames.CONNECT_COMPONENT_TO_STEP),
//        status = Some(StatusStep(
//          appendStep = Some(AppendStepSuccess()),
//          common = Some(statusCommon)
//        ))
//      )
//      case AppendStepError() => StepBO(
//        json = Some(JsonNames.CONNECT_COMPONENT_TO_STEP),
//        status = Some(StatusStep(
//          appendStep = Some(AppendStepError()),
//          common = Some(statusCommon)
//        ))
//      )
//    }
//  }
}

