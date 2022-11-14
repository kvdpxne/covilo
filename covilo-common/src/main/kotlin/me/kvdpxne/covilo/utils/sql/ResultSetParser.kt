package me.kvdpxne.covilo.utils.sql

import me.kvdpxne.covilo.utils.dateFormatter
import me.kvdpxne.covilo.utils.dateTimeFormatter
import java.sql.ResultSet
import java.sql.SQLException
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

class ResultSetParser(
  internal val table: String,
  internal val result: ResultSet
) {

  /**
   *
   */
  private fun getColumWithTable(column: String): String {
    return "$table.$column"
  }

  @Throws(SQLException::class)
  fun parse(column: String): String? {
    val columnWithTable = getColumWithTable(column)
    return result.getString(columnWithTable)
  }

  @Throws(SQLException::class)
  fun parseBoolean(column: String): Boolean {
    val columnWithTable = getColumWithTable(column)
    return result.getBoolean(columnWithTable)
  }

  @Throws(SQLException::class)
  fun parseInt(column: String): Int {
    val columnWithTable = getColumWithTable(column)
    return result.getInt(columnWithTable)
  }

  @Throws(SQLException::class)
  fun parseLong(column: String): Long {
    val columnWithTable = getColumWithTable(column)
    return result.getLong(columnWithTable)
  }

  @Throws(SQLException::class)
  fun <T : Enum<T>> parseEnum(column: String, values: Array<T>): T? {
    val content = parse(column)
    return values.find {
      it.name.equals(column, true)
    }
  }

  fun parseInstant(column: String): Instant {
    return this.result.getTimestamp(column).toInstant()
  }

  @Throws(SQLException::class)
  fun parseDateTime(column: String): LocalDateTime? {
    val content = this.parse(column)
    return runCatching {
      LocalDateTime.parse(content, dateTimeFormatter)
    }.getOrNull()
  }

  @Throws(SQLException::class)
  fun parseDate(column: String): LocalDate {
    return LocalDate.parse(parse(column), dateFormatter)
  }
}