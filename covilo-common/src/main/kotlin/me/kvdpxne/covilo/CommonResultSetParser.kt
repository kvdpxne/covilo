package me.kvdpxne.covilo

import me.kvdpxne.covilo.utils.sql.ResultSetParser
import java.sql.SQLException
import java.util.UUID

@Throws(SQLException::class)
fun ResultSetParser.identifier(): UUID {
  val content = parse(IDENTIFIER_COLUMN)
  return UUID.fromString(content)
}