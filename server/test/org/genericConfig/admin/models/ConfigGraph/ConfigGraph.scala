package org.genericConfig.admin.models.ConfigGraph

import org.genericConfig.admin.controllers.websocket.WebClient
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
                    with BeforeAfterAll{
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

}
