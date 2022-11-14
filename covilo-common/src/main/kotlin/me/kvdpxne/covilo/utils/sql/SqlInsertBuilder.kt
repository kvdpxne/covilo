package me.kvdpxne.covilo.utils.sql

class SqlInsertBuilder(table: String): SqlQueryBuilder {

  override val parameters: MutableMap<String, String?> = mutableMapOf()
  private var request: String = "INSERT INTO $table"
  private var columns: String = ""
  private var values: String = ""

  operator fun set(key: String, value: Any?) {
    val stringValue = value.toString()
    parameters[key] = stringValue
    columns += "`$key`,"
    values += "'$stringValue',"
  }

  override fun build(): String {
    columns = columns.removeSuffix(",")
    values = values.removeSuffix(",")
    request += " ($columns) VALUES ($values);"
    return request
  }
}