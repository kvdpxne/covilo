package me.kvdpxne.covilo.utils.sql

class SqlDeleteBuilder(private val table: String) : SqlQueryBuilder {

  override val parameters = mutableMapOf<String, String?>()
  private var request = "DELETE FROM $table"

  fun where(column: String, key: Any?): SqlDeleteBuilder {
    val columnWithTable = "$table.$column"
    val part = "WHERE $columnWithTable = :$columnWithTable"
    parameters[columnWithTable] = key.toString()
    request += " $part"
    return this
  }

  override fun build(): String {
    return "$request;"
  }
}