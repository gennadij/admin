package org.genericConfig.admin.models.common

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.04.2018
 */
//TODO status gegen Name ersetzen
abstract class Error  {
  def status: String
  def message: String
  def code : String
}

//case class Success() extends Error {
//  def status: String = "SUCCESS"
//  def message: String = "Die Aktion ist erfolgreich"
//  def code : String = ""
//}

case class UnknownError() extends Error {
  def status: String = "UNKNOWN_ERROR"
  def message: String = "Unbekannter Fehler"
  def code : String = ""
}

case class AddUserAlreadyExist() extends Error {
  def status: String = "ADD_USER_ALREADY_EXIST"
  def message: String = ""
  def code : String = ""
}

case class GetUserNotExist() extends Error {
  def status: String = "GET_USER_NOT_EXIST"
  def message: String = ""
  def code :String = ""
}

case class ODBClassCastError() extends Error{
  def status: String = "CLASS_CAST_ERROR"
  def message: String = "Interner Fehler des Configurators"
  def code : String = ""
}

case class ODBReadError() extends Error{
  def status: String = "ODB_READE_ERROR"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}

case class ODBWriteError() extends Error{
  def status: String = "ODB_WRITE_ERROR"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}

case class ODBNullPointer() extends Error{
  def status: String = "ODB_NULL_POINTER"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}

case class ODBValidationException() extends Error{
  def status: String = "ODB_VALIDATION_EXCEPTION"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}


case class ODBRecordDuplicated() extends Error{
  def status: String = "ODB_RECORD_DUPLICATED"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}

case class ODBRecordIdDefect() extends Error{
  def status: String = "ODB_RECORD_ID_DEFECT"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}

case class ODBConnectionFail() extends Error {
  def status: String = "ODB_CONNECTION_FAIL"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}
