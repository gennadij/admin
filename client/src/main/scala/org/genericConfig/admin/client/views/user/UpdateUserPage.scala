package org.genericConfig.admin.client.views.user

import org.genericConfig.admin.client.controllers.listner.Mouse
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.UserDTO
import org.scalajs.jquery.{JQuery, jQuery}
import util.CommonFunction

class UpdateUserPage() extends CommonFunction{

  def drawUpdateUserPage(userDTO: UserDTO): Unit = {


    cleanPage

    val main : JQuery = HtmlElementText.mainPage(s"Benutzer : ${userDTO.result.get.username.get} bearbeiten" )
    val jQueryInputFieldUsername : JQuery = HtmlElementText.drawInputField(
      HtmlElementIds.inputFieldUpdateUsernameHtml,
      "Benutzername",
      defaultText = userDTO.result.get.username.get
    )
    val jQueryButtonUpdateUserSave : JQuery = HtmlElementText.drawButton("updateUserSave", "Speichern")
    val jQueryButtonCancel : JQuery = HtmlElementText.drawButton("cancel", "Abbrechen")

    main.appendTo(jQuery(HtmlElementIds.section))

    jQueryInputFieldUsername.appendTo(main)
    jQueryButtonUpdateUserSave.appendTo(main)
    jQueryButtonCancel.appendTo(main)

    new Mouse().mouseClick(jQueryButtonUpdateUserSave, Actions.UPDATE_USER, Some(userDTO))
    new Mouse().mouseClick(jQueryButtonCancel, Actions.USER_PAGE, Some(userDTO))
  }
}
