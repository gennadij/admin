package org.genericConfig.admin.shared.common.status

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.04.2018
 */
abstract class Status {
  def status: String
  def message: String
}

case class Success() extends Status {
  def status: String = "SUCCESS"
  def message: String = "Die Aktion ist erfolgreich"
}

case class Error() extends Status {
  def status: String = "ERROR"
  def message: String = "Die Aktion ist nicht erfolgreich"
}

case class ODBClassCastError() extends Status{
  def status: String = "CLASS_CAST_ERROR"
  def message: String = "Interner Fehler des Configurators"
}

case class ODBReadError() extends Status{
  def status: String = "ODB_READE_ERROR"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
}

case class ODBWriteError() extends Status{
  def status: String = "ODB_WRITE_ERROR"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
}

case class ODBRecordDuplicated() extends Status{
  def status: String = "ODB_RECORD_DUPLICATED"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
}

case class ODBRecordIdDefect() extends Status{
  def status: String = "ODB_RECORD_ID_DEFECT"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
}

case class ODBConnectionFail() extends Status {
  def status: String = "ODB_CONNECTION_FAIL"
  def message: String = "Beim Laden hat einen Fehler in Datenbank aufgetreten"
}
