package me.kvdpxne.covilo.utils.sql

@Throws(SqlColumnAlreadyExistsException::class)
fun sqlColumnArrayOf(table: String, vararg columns: String): SqlColumnArray {
  val size = columns.size
  val columnArray = SqlColumnArray(table, size)
  repeat(size) {
    val value = columns[it]
    if (columnArray.contains(value)) {
      throw SqlColumnAlreadyExistsException(table, value)
    }
    columnArray[it] = value
  }
  return columnArray
}