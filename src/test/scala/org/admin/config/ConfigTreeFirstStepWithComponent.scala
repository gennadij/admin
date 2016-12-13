package org.admin.config

import org.admin.AdminWeb
import org.specs2.mutable.Specification
import play.api.libs.json.{JsValue, Json}
import org.persistence.db.orientdb.StepVertex
import org.specs2.mutable.Before
import org.specs2.specification.BeforeAfterAll

class ConfigTreeFirstStepWithComponent 
    extends Specification
    with AdminWeb 
    with BeforeAfterAll{

    val loginCS = Json.obj(
    "jsonId" -> 2,
    "dto" -> "Login"
    ,"params" -> Json.obj(
      "username" -> "firstStepConfigTree",
      "password" -> "firstStepConfigTree"
    )
  )

  val loginSC = handelMessage(loginCS)
  
  val firstStepConfigTreeCS = Json.obj(
    "jsonId" -> 4,
    "dto" -> "FirstStep" 
    ,"params" -> Json.obj(
      "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
      "kind" -> "first",
      "loginStatus" -> (loginSC \ "result" \ "status").asOpt[Boolean].get
    )
  )
  
  val firstStepConfigTreeSC = handelMessage(firstStepConfigTreeCS)
  
  val configTreeCS = Json.obj(
    "jsonId" -> 6,
    "dto" -> "ConfigTree"
    ,"params" -> Json.obj(
      "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
      "loginStatus" -> (loginSC \ "result" \ "status").asOpt[Boolean].get
    )
  )
  
    val componentClientServer = Json.obj(
      "jsonId" -> 5,
      "dto" -> "Component"
      ,"params" -> Json.obj(
          "adminId" -> (loginSC \ "result" \ "adminId").asOpt[String].get,
          "kind" -> "immutable"
          ,"stepId" -> (firstStepConfigTreeSC \ "result" \ "id").asOpt[String].get
      )
  )
  
  def beforeAll = {
    StepVertex.removerSteps((loginSC \ "result" \ "adminId").asOpt[String].get)
  }
  
  def afterAll = {
    
  }
  
//  def is = s2"""
//    Diese Specification prueft die Erzeugung eines neuen Steps
//      1. Login -> loginStatus=true                                              $e1
//      12. FirstStep -> Delete                                                   
//      2. FirstStep -> jsonId = 4                                                $e2
//      3. FirstStep -> dto = ConfigTree                                          $e3
//      4. FirstStep -> adminId                                                   $e4
//      5. FirstStep -> kind = Immutable                                          $e5
//      6. ConfigTree -> jsonId = 6                                               $e6
//      7. ConfigTree -> dto = ConfigTree                                         $e7
//      8. ConfigTree -> configTree = FirstStep                                   $e8
//      9. ConfigTree -> step -> adminId                                          $e9
//      10. ConfigTree -> step -> kind=first                                      $e10
//      11. ConfigTree -> step -> components.size=0                               $e11
//      13. Component -> component.jsonId=5                                       $e13
//      14. Component -> component.dto=Component                                  $e14
//      15. Component -> component.result.adminId                                 $e15
//      16. Component -> component.result.kind                                    $e16
//      17. Component -> component.result.stepId                                  $e17
//      18. ConfigTree -> jsonId = 6                                               $e18
//      19. ConfigTree -> dto = ConfigTree                                         $e19
//      20. ConfigTree -> configTree = FirstStep                                   $e20
//      21. ConfigTree -> step -> adminId                                          $e21
//      22. ConfigTree -> step -> kind=first                                      $e22
//      23. ConfigTree -> step -> components.size=1                               $e23
//      24. ConfigTree -> step -> components.result.id                            $e24
//      25. ConfigTree -> step -> components.result.componentId                   $e24
//      26. ConfigTree -> step -> components.result.adminId                       $e24
//      27. ConfigTree -> step -> components.result.kind                          $e24
//      28. ConfigTree -> step -> components.result.stepId                        $e24
//    """
// TODO Löschen der vorherigen Step bevor der Schritt hinzugefügt wird


  

  
  "Diese Specification prueft die Erzeugung eines neuen Steps mit Componenten" >> {
    "Login" >> {
      (loginSC \ "result" \ "status").asOpt[Boolean].get === true
    }
    "FirstStep" >> {
      "jsonId" >> {
    	  (firstStepConfigTreeSC \ "jsonId").asOpt[Int].get === 4
      }
      "dto" >> {
    	  (firstStepConfigTreeSC\ "dto").asOpt[String].get === "FirstStep"
      }
      "result \\ adminId" >> {
    	  (firstStepConfigTreeSC \ "result" \ "adminId").asOpt[String].get ===
    	    (loginSC \ "result" \ "adminId").asOpt[String].get
      }
       "result \\ kind" >> {
    	  (firstStepConfigTreeSC \ "result" \ "kind").asOpt[String].get === "first"
      }
    }
      "ConfigTree" >> {
        val configTreeSC = handelMessage(configTreeCS)
      "jsonId" >> {
        (configTreeSC \ "jsonId").asOpt[Int].get === 6
      }
      "dto" >> {
        (configTreeSC \ "dto").asOpt[String].get === "ConfigTree"
      }
      "" >> {
        (configTreeSC \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
      }
      "" >> {
        (((configTreeSC \ "result" \ "steps")(0)) \ "adminId").asOpt[String].get === 
      (loginSC \ "result" \ "adminId").asOpt[String].get
      }
      "" >> {
        (((configTreeSC \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
      }
      "" >> {
        (((configTreeSC \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 1
      }
    }
  }
  
  
//{"jsonId": 5, "method": "addComponent", 
//   *      "result": {"id": "#13:1", "componentId": "C#13:1", "adminId": "AU#40:0", "kind": "immutable", "stepId": "#12:1"}}
  
  val componentServerClient = handelMessage(componentClientServer)
  
  def e13 = (componentServerClient \ "jsonId").asOpt[Int].get === 5
  def e14 = (componentServerClient \ "dto").asOpt[String].get === "Component"
  def e15 = (componentServerClient \ "result" \ "adminId").asOpt[String].get === 
    (loginServerClient \ "result" \ "adminId").asOpt[String].get
  def e16 = (componentServerClient \ "result" \ "kind").asOpt[String].get === "immutable" 
  def e17 = (componentServerClient \ "result" \ "stepId").asOpt[String].get === 
    (firstStepConfigTreeServerClient \ "result" \ "id").asOpt[String].get
  
//  ============================================================================
    
  val configTreeClientServer1 = Json.obj(
    "jsonId" -> 6,
    "dto" -> "ConfigTree"
    ,"params" -> Json.obj(
      "adminId" -> (loginServerClient \ "result" \ "adminId").asOpt[String].get,
      "loginStatus" -> (loginServerClient \ "result" \ "status").asOpt[Boolean].get
    )
  )
  
  val configTreeServerClient1 = handelMessage(configTreeClientServer1)
  
  def e18 = (configTreeServerClient1 \ "jsonId").asOpt[Int].get === 6
  def e19 = (configTreeServerClient1 \ "dto").asOpt[String].get === "ConfigTree"
  def e20 = (configTreeServerClient1 \ "result" \ "steps").asOpt[List[JsValue]].get.size === 1
  def e21 = (((configTreeServerClient1 \ "result" \ "steps")(0)) \ "adminId").asOpt[String].get === 
    (loginServerClient \ "result" \ "adminId").asOpt[String].get
  def e22 = (((configTreeServerClient1 \ "result" \ "steps")(0)) \ "kind").asOpt[String].get === "first"
  def e23 = (((configTreeServerClient1 \ "result" \ "steps")(0)) \ "components").asOpt[List[JsValue]].get.size === 1
  def e24 = (((((configTreeServerClient1 \ "result" \ "steps")(0)) \ "components")(0)) \ "id").asOpt[String].get === 
    (componentServerClient \ "result" \ "id").asOpt[String].get
  def e25 = (((((configTreeServerClient1 \ "result" \ "steps")(0)) \ "components")(0)) \ "componentId").asOpt[String].get === 
    (componentServerClient \ "result" \ "componentId").asOpt[String].get
  def e26 = (((((configTreeServerClient1 \ "result" \ "steps")(0)) \ "components")(0)) \ "adminId").asOpt[String].get === 
    (componentServerClient \ "result" \ "adminId").asOpt[String].get
  def e27 = (((((configTreeServerClient1 \ "result" \ "steps")(0)) \ "components")(0)) \ "kind").asOpt[String].get === 
    (componentServerClient \ "result" \ "kind").asOpt[String].get
  def e28 = (((((configTreeServerClient1 \ "result" \ "steps")(0)) \ "components")(0)) \ "stepId").asOpt[String].get === 
    (componentServerClient \ "result" \ "stepId").asOpt[String].get
  //Daten Ausgabe
  
  println(s"Login        Client -> Server: $loginClientServer")
  println(s"Login        Server -> Client: $loginServerClient")
  println(s"FirstStep    Client -> Server: $firstStepConfigTreeClientServer")
  println(s"FirstStep    Server -> Client: $firstStepConfigTreeServerClient")
  println(s"ConfigTree   Cleint -> Server: $configTreeClientServer")
  println(s"ConfigTree   Server -> Client: $configTreeServerClient")
  println(s"Component    Client -> Server: $componentClientServer")
  println(s"Component    Server -> Client: $componentServerClient")
  println(s"ConfigTree   Client -> Server: $configTreeClientServer1")
  println(s"ConfigTree   Server -> Client: $configTreeServerClient1")
}