package org.genericConfig.admin.models.common

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.04.2018
 */
abstract class Error  {
  def name: String
  def message: String
  def code : String
}

case class UnknownError() extends Error {
  def name: String = "UNKNOWN_ERROR"
  def message: String = "Unbekannter Fehler"
  def code : String = ""
}

case class AddUserAlreadyExist() extends Error {
  def name: String = "ADD_USER_ALREADY_EXIST"
  def message: String = ""
  def code : String = ""
}

case class GetUserNotExist() extends Error {
  def name: String = "GET_USER_NOT_EXIST"
  def message: String = ""
  def code :String = ""
}

case class UserIdHashNotExist() extends Error {
  def name: String = "ADD_ID_HASH_NOT_EXIST"
  def message: String = ""
  def code :String = ""
}

case class DeleteConfigDefectID() extends Error {
  def name: String = "DELETE_CONFIG_DEFECT_ID"
  def message: String = ""
  def code :String = ""
}

case class ConfigIdHashNotExist() extends Error {
  def name: String = "CONFIG_ID_HASH_NOT_EXIST"
  def message: String = ""
  def code :String = ""
}

case class ConfigNothingToUpdate() extends Error {
  def name: String = "CONFIG_NOTHING_TO_UPDATE"
  def message: String = ""
  def code :String = ""
}

case class StepAlreadyExistError() extends Error {
  def name: String = "STEP_ALREADY_EXIST_ERROR"
  def message: String = ""
  def code :String = ""
}

case class IdHashNotExistError() extends Error{
  def name: String = "ID_HASH_NOT_EXIST"
  def message: String = ""
  def code :String = ""
}

case class AddStepError() extends Error {
  def name: String = "ADD_STEP_ERROR"
  def message: String = ""
  def code :String = ""
}

case class AppendToError() extends Error {
  def name: String = "APPEND_TO_ERROR"
  def message: String = ""
  def code :String = ""
}

case class ODBClassCastError() extends Error{
  def name: String = "CLASS_CAST_ERROR"
  def message: String = "Interner Fehler des Configurators"
  def code : String = ""
}

case class ODBReadError() extends Error{
  def name: String = "ODB_READE_ERROR"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}

case class ODBWriteError() extends Error{
  def name: String = "ODB_WRITE_ERROR"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}

case class ODBNullPointer() extends Error{
  def name: String = "ODB_NULL_POINTER"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}

case class ODBValidationException() extends Error{
  def name: String = "ODB_VALIDATION_EXCEPTION"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}


case class ODBRecordDuplicated() extends Error{
  def name: String = "ODB_RECORD_DUPLICATED"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}

case class ODBRecordIdDefect() extends Error{
  def name: String = "ODB_RECORD_ID_DEFECT"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}

case class ODBConnectionFail() extends Error {
  def name: String = "ODB_CONNECTION_FAIL"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
  def code : String = ""
}
