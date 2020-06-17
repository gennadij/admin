package org.genericConfig.admin.models.ConfigGraph

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.{ConfigGraphDTO, ConfigGraphParamsDTO}
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.Logger
import play.api.libs.json.{JsResult, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.06.2020
 */
class ConfigGraph extends Specification
                    with BeforeAfterAll
                    with CommonFunction{

  val wC: WebClient = WebClient.init
  var resConfigGraph : JsResult[ConfigGraphDTO] = _

  def beforeAll() : Unit = {
    before()
  }

  def afterAll(): Unit = {
  }

  "Der Benutzer ruft der gesamten Konfigurationsgraph auf" >> {
    "action = CONFIG_GRAPH" >> {resConfigGraph.get.action === Actions.CONFIG_GRAPH}
    "Anzahl der Schritte = 3" >> {resConfigGraph.get.result.get.steps.get must have length 3}
    "Id Laenge des Schrittes Id < 32 && > 10" >> {resConfigGraph.get.result.get.steps.get(0).id.length must be_<=(32) and be_>(10)}
    "Anzahl der Komponenten = 9" >> {resConfigGraph.get.result.get.components.get must have length 9}
    "Id Laenge der Komponente  < 32 && > 10" >> {resConfigGraph.get.result.get.components.get(0).id.length must be_<=(32) and be_>(10)}
    "Anzahl der Kanten = 13" >> {resConfigGraph.get.result.get.edges.get must have length 13}
    "Länge der sourceId < 32 && > 10" >> {resConfigGraph.get.result.get.edges.get(0).source.length must be_<=(32) and be_>(10)}
    "Länge der targetId < 32 && > 10" >> {resConfigGraph.get.result.get.edges.get(0).target.length must be_<=(32) and be_>(10)}
    "errors = None" >> {resConfigGraph.get.result.get.errors === None}
  }

  def before(): Unit = {
    val wC: WebClient = WebClient.init
    val username = "configGraph_V0.1"
    val userId = createUser(username, wC)
    val configId = createConfig(userId, s"http://contig/$username")

    val stepIdS1 : Option[String] = addStep(nameToShow = Some(s"S1_$username"), outId = Some(configId), min = 1, max = 1, wC = wC)
    val componentIdS1C1 : Option[String] = createComponent(wC, stepIdS1, Some(s"S1_C1_$username"))
    val componentIdS1C2 : Option[String] = createComponent(wC, stepIdS1, Some(s"S1_C2_$username"))
    val componentIdS1C3 : Option[String] = createComponent(wC, stepIdS1, Some(s"S1_C3_$username"))

    val stepIdS2 = addStep(nameToShow = Some(s"S2_$username"), outId = componentIdS1C1, min = 1, max = 1, wC = wC)
    val errorS1_C2_S2 = connectComponentToStep(outId = componentIdS1C2.get, inId = stepIdS2.get, wC)

    val componentIdS2C1 : Option[String] = createComponent(wC, stepIdS2, Some(s"S2_C1_$username"))
    val componentIdS2C2 : Option[String] = createComponent(wC, stepIdS2, Some(s"S2_C2_$username"))

    val stepIdS3 = addStep(nameToShow = Some(s"S3_$username"), outId = componentIdS1C3, min = 1, max = 1, wC = wC)

    val componentIdS3C1 : Option[String] = createComponent(wC, stepIdS3, Some(s"S3_C1_$username"))
    val componentIdS3C2 : Option[String] = createComponent(wC, stepIdS3, Some(s"S3_C2_$username"))
    val componentIdS3C3 : Option[String] = createComponent(wC, stepIdS3, Some(s"S3_C3_$username"))
    val componentIdS3C4 : Option[String] = createComponent(wC, stepIdS3, Some(s"S3_C4_$username"))

    val configGraph: JsValue = Json.toJson(
      ConfigGraphDTO(
        action = Actions.CONFIG_GRAPH,
        params = Some(ConfigGraphParamsDTO(
          configId = configId
        ))
      )
    )

    Logger.info("CONFIG_GRAPH -> " + configGraph)
    val resultJsValue : JsValue = wC.handleMessage(configGraph)
    Logger.info("CONFIG_GRAPH <- " + resultJsValue)
    resConfigGraph = Json.fromJson[ConfigGraphDTO](resultJsValue)
  }
}
