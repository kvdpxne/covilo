package me.kvdpxne.covilo.util

import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

@Suppress("SqlSignature")
fun JdbcOperations.count(by: String = "*", table: String): Long {
  return this.queryForObject(
    "SELECT COUNT($by) FROM $table;"
  ) { result, _ ->
    result.getLong(1)
  }.let {
    it ?: 0L
  }
}

fun NamedParameterJdbcTemplate.count(by: String = "*", table: String): Long {
  return this.jdbcOperations.count(by, table)
}

fun NamedParameterJdbcTemplate.insert(
  table: String,
  columns: Array<String>,
  values: Array<Any>
) {
  val length = columns.size
  var query = "INSERT INTO $table("
  repeat(length) {
    query += columns[it]
    if (length >= it) {
      query += ","
    }
  }
  query += ") VALUES("
  repeat(length) {

  }
  query += ");"
}
