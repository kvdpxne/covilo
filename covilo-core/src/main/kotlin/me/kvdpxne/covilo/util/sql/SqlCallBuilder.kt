package me.kvdpxne.covilo.util.sql

import me.kvdpxne.covilo.util.Buildable

class SqlCallBuilder : Buildable<String> {

  private var query: String = ""

  private var tableName: String = ""

  val parameters: MutableMap<String, String> = mutableMapOf()

  fun select(columnArray: SqlColumnArray): SqlCallBuilder {
    var part = "SELECT "
    val columns = columnArray.columns
    for (column in columns) {
      if (null == column) {
        continue
      }
      part += "$column,"
    }
    part = part.removeSuffix(",")
    query += part
    return this
  }

  fun count(column: String = "*"): SqlCallBuilder {
    val part = "SELECT COUNT($column)"
    query += part
    return this
  }

  fun from(table: String): SqlCallBuilder {
    val part = " FROM $table"
    query += part
    tableName = table
    return this
  }

  fun join(relation: SqlRelation): SqlCallBuilder {
    val firstTable = relation.firstTable
    val secondTable = relation.secondTable
    val firstIdentifier = relation.firstColumn
    val secondIdentifier = relation.secondColumn
    var part = " INNER JOIN $firstTable ON"
    part += " $firstTable.$secondIdentifier = $secondTable.$firstIdentifier"
    query += part
    return this
  }

  fun join(relations: Array<SqlRelation>): SqlCallBuilder {
    relations.forEach {
      join(it)
    }
    return this
  }

  fun where(column: String, key: Any?): SqlCallBuilder {
    val columnWithTable = "$tableName.$column"
    val part = "WHERE $columnWithTable = :$columnWithTable"
    parameters.putIfAbsent(columnWithTable, key.toString())
    query += " $part"
    return this
  }

  override fun build(): String {
    return "$query;"
  }
}