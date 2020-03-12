package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.configTree.bo.{ComponentForConfigTreeBO, ConfigTreeBO, StepForConfigTreeBO}
import org.genericConfig.admin.shared.configTree.json.{JsonConfigTreeComponent, JsonConfigTreeIn, JsonConfigTreeOut, JsonConfigTreeStep}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 12.06.2018
  */
class WrapperConfigTree {

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param jsonConfigTreeIn : JsonConfigTreeIn
    * @return ConfigTreeBO
    */
  def toConfigTreeBO(jsonConfigTreeIn: JsonConfigTreeIn): ConfigTreeBO = {
    ConfigTreeBO(
      configId = Some(jsonConfigTreeIn.params.configId)
    )
  }

  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param configTreeBO : ConfigTreeBO
    * @return JsonConfigTreeOut
    */
  private[wrapper] def toJsonConfigTreeOut(configTreeBO: ConfigTreeBO): JsonConfigTreeOut = {
    ???

//    val debug = configTreeBO.status.get.getConfigTree match {
//      case GetConfigTreeSuccess() =>
//        JsonConfigTreeOut(
//          result = JsonConfigTreeResult(
//            Some(configTreeBO.userId.get),
//            Some(configTreeBO.configId.get),
//            Some(JsonConfigTreeStep(
//              configTreeBO.configTree.get.stepId,
//              configTreeBO.configTree.get.nameToShow,
//              configTreeBO.configTree.get.kind,
//              getNextSteps(configTreeBO.configTree.get),
//              getJsonConfigTreeComponents(configTreeBO.configTree.get.components)
//            )),
//            JsonConfigTreeStatus(
//              Some(JsonStatus(
//                configTreeBO.status.get.getConfigTree.status,
//                configTreeBO.status.get.getConfigTree.message
//              )),
//              Some(JsonStatus(
//                configTreeBO.status.get.common.status,
//                configTreeBO.status.get.common.message
//              ))
//            )
//          )
//        )
//      case GetConfigTreeEmpty() =>
//        JsonConfigTreeOut(
//          result = JsonConfigTreeResult(
//            Some(configTreeBO.userId.get),
//            Some(configTreeBO.configId.get),
//            None,
//            JsonConfigTreeStatus(
//              Some(JsonStatus(
//                configTreeBO.status.get.getConfigTree.status,
//                configTreeBO.status.get.getConfigTree.message
//              )),
//              Some(JsonStatus(
//                configTreeBO.status.get.common.status,
//                configTreeBO.status.get.common.message
//              ))
//            )
//          )
//        )
//      case GetConfigTreeError() =>
//        JsonConfigTreeOut(
//          result = JsonConfigTreeResult(
//            None,
//            None,
//            None,
//            JsonConfigTreeStatus(
//              Some(JsonStatus(
//                configTreeBO.status.get.getConfigTree.status,
//                configTreeBO.status.get.getConfigTree.message
//              )),
//              Some(JsonStatus(
//                configTreeBO.status.get.common.status,
//                configTreeBO.status.get.common.message
//              ))
//            )
//          )
//        )
//    }
//
//    Logger.info("jsonConfigTreeOut " + debug)
//    debug
  }



  private def getNextSteps(nextStep: StepForConfigTreeBO): Set[JsonConfigTreeStep] = {
    val nextSteps: List[JsonConfigTreeStep] = nextStep.nextSteps.toList map (
      nS =>
        JsonConfigTreeStep(
          nS.stepId,
          nS.nameToShow,
          nS.kind,
          {
            val nSSS: List[JsonConfigTreeStep] = nS.nextSteps.toList.map(nSS =>
              JsonConfigTreeStep(
                nSS.stepId,
                nSS.nameToShow,
                nSS.kind,
                getNextSteps(nSS),
                getJsonConfigTreeComponents(nSS.components)
              ))
            nSSS.toSet
          },
          getJsonConfigTreeComponents(nS.components)
        )
    )
    nextSteps.toSet
  }
  /**
    * @author Gennadi Heimann
    * @version 0.1.6
    * @param components : Set[Option[ComponentForConfigTreeBO\]\]
    * @return Set[JsonConfigTreeComponent]
    */
  private def getJsonConfigTreeComponents(components: Set[ComponentForConfigTreeBO]): Set[JsonConfigTreeComponent] = {

    components.map {
      component => {
        JsonConfigTreeComponent(
          component.componentId,
          component.nameToShow,
          component.kind,
          component.nextStepId
        )
      }
    }
  }

//  /**
//    * @author Gennadi Heimann
//    * @version 0.1.6
//    * @param components : Set[Option[ComponentForConfigTreeBO\]\]
//    * @return Set[JsonConfigTreeComponent]
//    */
//  private def getJsonConfigTreeComponents(components: Set[Option[ComponentForConfigTreeBO]]): Set[JsonConfigTreeComponent] = {
//
//    components.map {
//      component => {
//        component.get.nextStep match {
//          case Some(step) => {
//            JsonConfigTreeComponent(
//              component.get.componentId,
//              component.get.nameToShow,
//              component.get.kind,
//              Some(JsonConfigTreeStep(
//                component.get.nextStep.get.stepId,
//                component.get.nextStep.get.nameToShow,
//                component.get.nextStep.get.kind,
//                getJsonConfigTreeComponents(component.get.nextStep.get.components)
//              ))
//            )
//          }
//          case None => {
//            JsonConfigTreeComponent(
//              component.get.componentId,
//              component.get.nameToShow,
//              component.get.kind,
//              None
//            )
//          }
//        }
//      }
//    }
//  }
}