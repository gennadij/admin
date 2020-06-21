package org.genericConfig.admin.client.views.configGraph

import org.genericConfig.admin.shared.configGraph.{ConfigGraphComponentDTO, ConfigGraphEdgeDTO, ConfigGraphResultDTO}
import play.api.libs.json.{JsObject, JsValue, Json}
import util.CommonFunction

import scala.scalajs.js.Dynamic.{global => g}
/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.06.2020
 */
class ConfigGraphPage extends CommonFunction {
  def drawConfigGraph(configGraphResultDTO: ConfigGraphResultDTO): Unit = {
    cleanPage
    val jsonData : String = Json.toJson(configGraphResultDTO.d3Data.get).toString()
    g.runGraphD3(jsonData)
  }
}
