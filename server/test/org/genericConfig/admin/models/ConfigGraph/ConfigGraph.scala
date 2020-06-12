package org.genericConfig.admin.models.ConfigGraph

import org.genericConfig.admin.controllers.websocket.WebClient
import org.genericConfig.admin.models.CommonFunction
import org.genericConfig.admin.shared.component.ComponentDTO
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAfterAll
import play.api.libs.json.JsResult

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.06.2020
 */
class ConfigGraph extends Specification
                    with BeforeAfterAll
                    with CommonFunction{
  //#33:60 http://config/client_013  user_v016_4_client
  val wC: WebClient = WebClient.init
  var updateCROnlyNameToShow : JsResult[ComponentDTO] = _

  def beforeAll() : Unit = {
  }

  def afterAll(): Unit = {
  }

  "Der Benutzer veraendert die Komponente" >> {
    "Es wird nur der Name geaendert" >> {
      "action = ADD_COMPONENT" >> {"" === ""}
      "componentId < 32 && > 10" >> {"" === ""}
      "stepId = None" >> {"" === ""}
      "nameToShow = ComponentUpdated" >> {"" === ""}
      "errors = None" >> {"" === ""}
    }
  }

  def before() = {
    val wC: WebClient = WebClient.init
    val username = "configGraph"
    val userId = createUser(username, wC)
    val configId = createConfig(userId, s"http://contig/$username")
    val nameToShow: Option[String] = Some(s"S1")
    val kind : Option[String] = Some("undefined")

    val stepIdS1 : Option[String] = addStep(nameToShow, Some(configId), kind, 1, 1, wC)
    val componentIdS1C1 : Option[String] = createComponent(wC, stepIdS1, Some("S1_C1"))
    val componentIdS1C2 : Option[String] = createComponent(wC, stepIdS1, Some("S1_C2"))
    val componentIdS1C3 : Option[String] = createComponent(wC, stepIdS1, Some("S1_C3"))

    val stepIdS2 = addStep(Some(s"S2"), componentIdS1C1, kind, 1, 1, wC)
    val componentIdS2C1 : Option[String] = createComponent(wC, stepIdS2, Some("C1"))
    val componentIdS2C2 : Option[String] = createComponent(wC, stepIdS2, Some("C2"))
  }
}
