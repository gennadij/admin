package org.genericConfig.admin.models.wrapper

import org.genericConfig.admin.shared.component.bo.ComponentBO
import org.genericConfig.admin.shared.component.json.{JsonComponentIn, JsonComponentOut}
import org.genericConfig.admin.shared.configTree.bo._
import org.genericConfig.admin.shared.configTree.json._
import org.genericConfig.admin.shared.step.bo._
import org.genericConfig.admin.shared.step.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 13.09.2017
 */



trait Wrapper{
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param
   * 
   * @return UserBO
   */
//  def toAddUserBO(jsonUserIn: JsonUserIn) = {
//    ???
//        new WrapperUser().toAddUserBO(jsonUserIn)
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param
   * 
   * @return JsonUserOut
   */
//  def toJsonAddUserOut(userBO: UserDTO): JsonUserOut = {
//    ???
//    new WrapperUser().toJsonAddUserOut(userBO)
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param
   * 
   * @return UserBO
   */
//  def toGetUserBO(jsonUserIn: JsonUserIn) = {
//    ???
//    new WrapperUser().toGetUserBO(jsonUserIn)
//  }
  
   /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param
   * 
   * @return JsonUserOut
   */
//  def toJsonGetUserOut(userBO: UserDTO): JsonUserOut = {
//    ???
    ///new WrapperUser().toJsonGetUserOut(userBO)
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param configTreeBO: ConfigTreeBO
   * 
   * @return JsonConfigTreeOut
   */
  def toJsonConfigTreeOut(configTreeBO: ConfigTreeBO): JsonConfigTreeOut = {
    new WrapperConfigTree().toJsonConfigTreeOut(configTreeBO)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonConfigTreeIn: JsonConfigTreeIn
   * 
   * @return ConfigTreeBO
   */
  def toConfigTreeBO(jsonConfigTreeIn: JsonConfigTreeIn): ConfigTreeBO = {
    new WrapperConfigTree().toConfigTreeBO(jsonConfigTreeIn)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param jsonStepIn: JsonStepCS
   * 
   * @return StepCS
   */
  def toStepBO(jsonStepIn: JsonStepIn): StepBO = {
    new WrapperStep().toStepBO(jsonStepIn)
  }
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param stepBO: StepBO
   * 
   * @return JsonStepSC
   */
  def toJsonStepOut(stepBO: StepBO): JsonStepOut = {
    new WrapperStep().toJsonStepOut(stepBO)
  }

  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param jsonComponentIn : JsonComponentIn
   * 
   * @return ComponentBO
   */
  def toComponentBO(jsonComponentIn : JsonComponentIn): ComponentBO = {
    new WrapperComponent().toComponentBO(jsonComponentIn)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.6
   * 
   * @param componentBO: ComponentBO
   * 
   * @return JsonComponentOut
   */
  def toJsonComponentOut(componentBO: ComponentBO): JsonComponentOut = {
    new WrapperComponent().toJsonComponentOut(componentBO)
  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param
   * 
   * @return ConnectionComponentToStepCS
   */
//  def toConnectionComponentToStepIn(
//      jsonConnectionComponentToStepIn: JsonConnectionComponentToStepIn): ConnectionComponentToStepIn = {
//    ConnectionComponentToStepIn(
//        jsonConnectionComponentToStepIn.params.componentId
//        , jsonConnectionComponentToStepIn.params.stepId
//    )
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param
   * 
   * @return JsonConnectionComponentToStepSC
   */
//  def toJsonConnectionComponentToStepOut(
//          connectionComponentToStepOut: ConnectionComponentToStepOut
//      ): JsonConnectionComponentToStepOut = {
//    JsonConnectionComponentToStepOut(
//        result = ConnectionComponentToStepResult(
//            connectionComponentToStepOut.status,
//            connectionComponentToStepOut.message
//        )
//    )
//  }
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param
   * 
   * @return JsonDependencyOut
   */
//  def toJsonDependencyOut(dependencyOut: DependencyOut): JsonDependencyOut = {
//    JsonDependencyOut(
//        result = JsonDependencyResult(
//            dependencyOut.componentFromId,
//            dependencyOut.componentToId,
//            dependencyOut.dependencyId,
//            dependencyOut.dependencyType,
//            dependencyOut.visualization,
//            dependencyOut.nameToShow,
//            dependencyOut.status,
//            dependencyOut.message
//        )
//    )
//  }
  
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param
   * 
   * @return DependencyIn
   */
//  def toDependencyIn(jsonDependencyIn: JsonDependencyIn): DependencyIn = {
//    DependencyIn(
//        jsonDependencyIn.params.componentFromId,
//        jsonDependencyIn.params.componentToId,
//        jsonDependencyIn.params.dependencyType,
//        jsonDependencyIn.params.visualization,
//        jsonDependencyIn.params.nameToShow
//    )
//  }
  
  /**
   * @author Gennadi Heimann
   * 
   * @version 0.1.5
   * 
   * @param
   * 
   * @return DependencyIn
   */
//  def toVisualProposalForAdditionalStepsInOneLevelIn(
//      jsonVisualProposalForAdditionalStepsInOneLevelIn: JsonVisualProposalForAdditionalStepsInOneLevelIn): 
//      VisualProposalForAdditionalStepsInOneLevelIn = {
//    
//     VisualProposalForAdditionalStepsInOneLevelIn(
//         jsonVisualProposalForAdditionalStepsInOneLevelIn.params.selectedVisualProposal
//     )
//  }
}
