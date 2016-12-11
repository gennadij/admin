package org.admin.register

import play.api.libs.json._
import org.admin.AdminWeb
import org.admin.DTOsForTesting
import org.specs2.specification.BeforeAfterAll
import org.persistence.db.orientdb.AdminUserVertex

class Registration extends org.specs2.mutable.Specification with AdminWeb with BeforeAfterAll{

  def beforeAll() = {
    println("before")
  }
  
  def afterAll() = {
    val res =AdminUserVertex.removeAdmin(DTOsForTesting.userNotExist)
    println("clean users " + DTOsForTesting.userNotExist)
  }
  
  "Specification Registrierung" >> {
    "Specification fuer die Pruefung der Registrierung schon exestierenden Admin" >> {
      handelMessage(DTOsForTesting.registerCS_1) === DTOsForTesting.registerSC_1
    }
    "Specification fuer die Pruefung der Registrierung nicht exestierenden/neuen Admin" >> {
      val registerSC_2 = handelMessage(DTOsForTesting.registerCS_2)
      "jsonId" >> {
        (registerSC_2 \ "jsonId").asOpt[Int].get === 1
      }
      "dto" >> {
        (registerSC_2 \ "dto").asOpt[String].get === "Registration"
      }
      "username" >> {
        (registerSC_2 \ "result" \ "username").asOpt[String].get must_== DTOsForTesting.userNotExist
      }
      "status" >> {
        (registerSC_2 \ "result" \ "status").asOpt[Boolean].get must_== true
      }
      "message" >> {
        (registerSC_2 \ "result" \ "message").asOpt[String].get must_== "Registrierung war erfolgreich"
      }
    }
  }
}