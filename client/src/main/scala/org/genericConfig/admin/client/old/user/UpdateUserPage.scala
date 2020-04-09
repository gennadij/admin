package org.genericConfig.admin.client.old.user

import org.genericConfig.admin.client.views.StartPage
import org.genericConfig.admin.client.views.html.HtmlElementIds
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO, UserUpdateDTO}
import org.scalajs.dom.raw.WebSocket
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json
import util.CommonFunction

class UpdateUserPage(webSocket: WebSocket) extends CommonFunction{
  def drawUpdateUser(userDTO: UserDTO) = {
    cleanPage
    val htmlMain : String =
      "<dev id='main' class='main'>" +
        "<p>Update User</p> </br>" +
        "<p> userId: " + userDTO.result.get.userId.get.subSequence(0, 6) + "</p>" +
        drawInputField(HtmlElementIds.inputFieldUpdateUsernameHtml, "Username") + "</br>" +
        drawButton(HtmlElementIds.buttonActionUpdateUsernameHtml, "Update Username") +
        drawButton(HtmlElementIds.startPageHtml, "startPage") +
      "</dev>"

    drawNewMain(htmlMain)

    jQuery(HtmlElementIds.buttonActionUpdateUsernameJQuery).on("click", () => updateUsername(userDTO))
    jQuery(HtmlElementIds.startPageJQuery).on("click", () => startPage)
  }

  private def startPage = {
    new StartPage().drawStartPage()
  }

  private def updateUsername(userDTO: UserDTO): Unit = {
    val updateUsername = Json.toJson(
      UserDTO(
        action = Actions.UPDATE_USER,
        params = Some(UserParamsDTO(
          username = "",
          password = "",
          update = Some(UserUpdateDTO(
            oldUsername = userDTO.result.get.username.get,
            newUsername = jQuery(HtmlElementIds.inputFieldUpdateUsernameJQuery).value().toString,
            oldPassword = "",
            newPassword = ""
          )),
        )),
        result = None
      )
    ).toString
    println("OUT -> " + updateUsername)
    webSocket.send(updateUsername)
    new StartPage().drawStartPage()

  }
}
