package org.genericConfig.admin.models.logic

import com.tinkerpop.blueprints.impls.orient.OrientVertex
import org.genericConfig.admin.models.common.{AddStepError, AppendToError, Error, IdHashNotExistError, StepAlreadyExistError}
import org.genericConfig.admin.models.persistence.orientdb.{GraphCommon, GraphStep, PropertyKeys}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.common.ErrorDTO
import org.genericConfig.admin.shared.step.{SelectionCriterionDTO, StepDTO, StepPropertiesDTO, StepResultDTO}

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
    * @param stepDTO : StepDTO
    * @return StepDTO
    */
  def deleteStep(stepDTO: StepDTO): StepDTO = {
    new Step().deleteStep(stepDTO)
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param stepDTO : StepDTO
    * @return StepDTO
    */
  def updateStep(stepDTO: StepDTO): StepDTO = {
    new Step().updateStep(stepDTO)
  }
}

class Step {

  private def addStep(stepDTO: StepDTO): StepDTO = {

    RidToHash.getRId(stepDTO.params.get.outId.get) match {
      case Some(outRid) =>
        GraphStep.isStepAlone(outRid) match {
          case None => (GraphStep.addStep(stepDTO) : @unchecked) match {
            case (Some(vStep), None) => GraphCommon.appendTo(
              outRid, vStep.getIdentity.toString(), PropertyKeys.EDGE_HAS_STEP
            ) match {
              case None => createStepDTO(
                Actions.ADD_STEP,
                Some(vStep),
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

  private def deleteStep(stepDTO: StepDTO): StepDTO = {
      val stepRId : Option[String] = RidToHash.getRId(stepDTO.params.get.stepId.get)
      val deleteError : Option[Error] = GraphCommon.deleteVertex(stepRId.get, PropertyKeys.VERTEX_STEP)
      deleteError match {
        case None =>  createStepDTO(Actions.DELETE_STEP, None, None)
        case Some(e) => createStepDTO(Actions.DELETE_STEP, None, Some(List(e)))
      }
    }

  private def updateStep(stepDTO: StepDTO) : StepDTO = {

    val stepRId : Option[String] = RidToHash.getRId(stepDTO.params.get.stepId.get)

    GraphStep.updateStep(stepRId.get, stepDTO) match {
      case (None, Some(error)) => createStepDTO(
        action = Actions.UPDATE_STEP,
        None,
        Some(List(error))
      )
      case (Some(vStep), None) => createStepDTO(
        action = Actions.UPDATE_STEP,
        vStep = Some(vStep),
        errors = None)
    }
  }

  private def createStepDTO(
                             action : String,
                             vStep : Option[OrientVertex],
                             errors : Option[List[Error]]) = {

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

    val properties : Option[StepPropertiesDTO] = (errors, vStep) match {
      case (None, Some(vS)) => Some(StepPropertiesDTO(
        nameToShow = Some(vS.getProperty(PropertyKeys.NAME_TO_SHOW).toString),
        selectionCriterion = Some(SelectionCriterionDTO(
          min = Some(vS.getProperty(PropertyKeys.SELECTION_CRITERION_MIN).toString.toInt),
          max = Some(vS.getProperty(PropertyKeys.SELECTION_CRITERION_MAX).toString.toInt)
        ))))
      case (Some(_), None) => None
      case _ => None
    }

    val stepId : Option[String] = (errors, vStep) match {
      case (None, Some(vS)) => Some(vS.getIdentity.toString)
      case (Some(_), None) => None
      case _ => None
    }

    StepDTO(
      action = action,
      result = Some(StepResultDTO(
        properties = properties,
        stepId = stepId,
        errors = e
      ))
    )
  }

















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

