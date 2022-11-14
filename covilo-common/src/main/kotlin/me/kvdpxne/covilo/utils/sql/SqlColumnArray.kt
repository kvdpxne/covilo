package me.kvdpxne.covilo.utils.sql

class SqlColumnArray internal constructor(val table: String, capacity: Int) {

  val columns: Array<String?> = arrayOfNulls(capacity)

  @Suppress("UNCHECKED_CAST")
  fun extend(): Array<String> {
    return columns as Array<String>
  }

  internal operator fun get(key: String): String {
    val columnWithTable = "$table.$key"
    return columns.find { it.equals(columnWithTable) }
      ?: throw NullPointerException("")
  }

  internal operator fun set(index: Int, value: String) {
    if (value.contains('.')) {
      columns[index] = value
      return
    }
    val columnWithTable = "$table.$value"
    columns[index] = columnWithTable
  }

  operator fun contains(value: String): Boolean {
    return columns.contains(value)
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is SqlColumnArray) return false

    if (table != other.table) return false
    if (!columns.contentEquals(other.columns)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = table.hashCode()
    result = 31 * result + columns.contentHashCode()
    return result
  }

  override fun toString(): String {
    return columns.toString()
  }
}