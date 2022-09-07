package me.kvdpxne.covilo.util.sql

import java.sql.ResultSet
import java.time.LocalDate
import java.util.UUID

fun ResultSet.getUuid(column: String): UUID {
  val stringIdentifier = this.getString(column)
    ?: throw NullPointerException("identifier is null")
  return UUID.fromString(stringIdentifier)
}

fun ResultSet.getChar(column: String): Char {
  val content = this.getString(column)
  return content[0]
}

fun ResultSet.getLocalDate(column: String): LocalDate {
  return LocalDate.parse(this.getString(column))
}