package me.kvdpxne.covilo.util.sql

import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER

class QueryBuilder {

  private var query: String = ""
  private var rootTable: String = ""

  fun select(vararg columns: String): QueryBuilder {
    var partOfQuery = "SELECT "
    for (column in columns) {
      partOfQuery += "$column,"
    }
    val length = partOfQuery.length
    partOfQuery = partOfQuery.removeRange(length - 1, length)
    query += partOfQuery
    return this
  }

  fun count(column: String): QueryBuilder {
    val part = " COUNT($column)"
    query += part
    return this
  }

  fun from(table: String): QueryBuilder {
    val partOfQuery = " FROM $table"
    rootTable = table
    query += partOfQuery
    return this
  }

  fun join(first: String, second: String, fs: String): QueryBuilder {
    val part = " INNER JOIN $first ON $first.$COLUMN_IDENTIFIER = $second.$fs"
    query += part
    return this
  }

  fun where(value: String, column: String): QueryBuilder {
    val partOfQuery = "WHERE $value = :$column"
    query += " $partOfQuery"
    return this
  }

  fun end(): String {
    return "$query;"
  }
}