package org.v012

import org.admin.AdminWeb
import org.specs2.specification.BeforeAfterAll
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification

/**
	* Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
	*
	* Created by Gennadi Heimann 05.05.2017
	*
	* Username = user15
	* Username = user16
	*/
@RunWith(classOf[JUnitRunner])
class SpecsTwoFirstStepsForOneConfig extends Specification
	with AdminWeb
	with BeforeAfterAll {
  
  def beforeAll = {}
	def afterAll = {}
	
	"Test" >> {
	  "Test" >> {
	    "" === ""
	  }
	}
}