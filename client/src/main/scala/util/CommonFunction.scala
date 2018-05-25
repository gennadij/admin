package util

import org.scalajs.jquery.jQuery
import scala.util.matching.Regex

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 25.05.2018
 */
trait CommonFunction {
  
  
  def cleanPage = {
    jQuery(HtmlElementIds.main).remove
    jQuery(HtmlElementIds.status).remove()
  }
  
  def drawNewStatus(status: String) {
    val htmlHeaderStatus = 
      "<dev id='" + HtmlElementIds.status + "' class='" + HtmlElementIds.status + "status'>" + 
          status + 
      "</dev>"
  
    jQuery(htmlHeaderStatus).appendTo(jQuery("header"))
  }
  
  def drawNewMain(html: String) = {
    
  }
  
  
  def prepareIdForHtml(id: String): String = {
   "[0-9]+".r.findAllIn(id).toArray.mkString
  }
}