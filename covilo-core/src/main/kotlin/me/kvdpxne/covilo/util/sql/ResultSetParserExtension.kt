package me.kvdpxne.covilo.util.sql

import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_CREATED_DATE
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_DOMESTIC_NAME
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_IDENTIFIER
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_KEY
import me.kvdpxne.covilo.infrastructure.jdbc.COLUMN_LAST_MODIFIED_DATE
import java.sql.SQLException
import java.time.LocalDateTime
import java.util.UUID

@Throws(SQLException::class)
fun ResultSetParser.parseIdentifier(): UUID {
  val content = parse(COLUMN_IDENTIFIER)
  return UUID.fromString(content)
}

@Throws(SQLException::class)
fun ResultSetParser.parseKey(): String {
  return parse(COLUMN_KEY)!!
}

@Throws(SQLException::class)
fun ResultSetParser.parseDomesticName(): String {
  return parse(COLUMN_DOMESTIC_NAME)!!
}

@Throws(SQLException::class)
fun ResultSetParser.parseCreatedDate(): LocalDateTime {
  return parseDateTime(COLUMN_CREATED_DATE) as LocalDateTime
}

@Throws(SQLException::class)
fun ResultSetParser.parseLastModifiedDate(): LocalDateTime? {
  return parseDateTime(COLUMN_LAST_MODIFIED_DATE)
}