package me.kvdpxne.covilo

import me.kvdpxne.covilo.utils.sql.ResultSetParser
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

fun ResultSetParser.token(): String =
  parse(TOKEN_COLUMN)!!

fun ResultSetParser.expiration(): Instant =
  parseInstant(EXPIRATION_COLUMN)

fun ResultSetParser.password(): String =
  parse(PASSWORD_COLUMN)!!

fun ResultSetParser.email(): String =
  parse(EMAIL_COLUMN)!!

fun ResultSetParser.createdDate(): LocalDateTime =
  parseDateTime(CREATED_DATE_COLUMN)!!

fun ResultSetParser.lastModifiedDate(): LocalDateTime? =
  parseDateTime(LAST_MODIFIED_DATE_COLUMN)

fun ResultSetParser.birthDate(): LocalDate =
  parseDate(BIRTH_DATE_COLUMN)

fun ResultSetParser.enabled(): Boolean =
  parseBoolean(ENABLED_COLUMN)