package me.kvdpxne.covilo.util.sql

import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_KEY
import me.kvdpxne.covilo.util.dateFormatter
import me.kvdpxne.covilo.util.dateTimeFormatter
import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class ResultSetParser(private val table: String, private val result: ResultSet) {

  @Throws(SQLException::class)
  fun parse(column: String): String? {
    return result.getString("$table.$column")
  }

  @Throws(SQLException::class, IllegalArgumentException::class)
  fun parseIdentifier(): UUID {
    val content = parse(COLUMN_IDENTIFIER)
    return UUID.fromString(content)
  }

  fun parseKey(): String {
    return parse(COLUMN_KEY)!!
  }

  fun parseDomesticName(): String {
    return parse(COLUMN_DOMESTIC_NAME)!!
  }

  fun parseInt(column: String): Int {
    return result.getInt("$table.$column")
  }

  fun parseBoolean(column: String): Boolean {
    return result.getBoolean("$table.$column")
  }

  fun <T : Enum<T>> parseEnum(column: String, values: Array<T>): T? {
    val content = parse(column)
    return values.find {
      it.name.equals(column, true)
    }
  }

  fun parseDateTime(column: String): LocalDateTime {
    return LocalDateTime.parse(parse(column), dateTimeFormatter)
  }

  fun parseDate(column: String): LocalDate {
    return LocalDate.parse(parse(column), dateFormatter)
  }
}