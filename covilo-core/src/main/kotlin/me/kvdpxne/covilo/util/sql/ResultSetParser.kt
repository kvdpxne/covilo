package me.kvdpxne.covilo.util.sql

import me.kvdpxne.covilo.util.dateFormatter
import me.kvdpxne.covilo.util.dateTimeFormatter
import java.sql.ResultSet
import java.sql.SQLException
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

  @Throws(SQLException::class)
  fun parseDateTime(column: String): LocalDateTime {
    return LocalDateTime.parse(parse(column), dateTimeFormatter)
  }

  @Throws(SQLException::class)
  fun parseDate(column: String): LocalDate {
    return LocalDate.parse(parse(column), dateFormatter)
  }
}